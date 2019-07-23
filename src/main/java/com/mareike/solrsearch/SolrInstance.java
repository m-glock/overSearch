package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.util.NamedList;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SolrInstance {

    public HttpSolrClient client;
    private String urlString;
    private String collectionName;

    public SolrInstance(String solrURL, String collection) throws HttpSolrClient.RemoteSolrException {
        urlString = solrURL;
        collectionName = collection;
        startClient();
    }

    public void changeSolrInstance(){

        client.setBaseURL(urlString + "/" + collectionName);
        System.out.println("Base url is now: " + client.getBaseURL());
        //isConnected();
    }

    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
    }
    
    public void createCollection() throws IOException, SolrServerException {
        String configName = "overSearchConfig";
        CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);
        NamedList resp = client.request(req);
        System.out.println("Response: " + resp.toString());

        client.setBaseURL(client.getBaseURL() + "/" + collectionName);
        System.out.println("Solr instance created with URL: " + client.getBaseURL());

    }

    public void deleteCollection() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        String solrBaseURL = client.getBaseURL().replaceAll("/" + collectionName, "");
        client.setBaseURL(solrBaseURL);
        CollectionAdminRequest.Delete request = CollectionAdminRequest.Delete.deleteCollection(collectionName);
        NamedList resp = client.request(request);
        System.out.println("Response to request: " + resp);
        System.out.println("Collection removed. Solr instance now uses the URL: " + client.getBaseURL());
    }

    public boolean isConnected(){
        boolean canConnect = true;
        try {
            SolrPing ping = new SolrPing();
            SolrPingResponse rsp = ping.process(client);
            System.out.println(rsp);
            if (!rsp.toString().contains("status=OK")){
                canConnect = false;
                connectionLostMessage();
            }
        } catch (Exception e) {
            System.out.println("Connection to Solr lost. ");
            canConnect = false;
            connectionLostMessage();
        }
        System.out.println(canConnect);
        return canConnect;
    }

    private void connectionLostMessage(){
        JLabel errorMessage = new JLabel();
        errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
        errorMessage.setText("<html>No connection to Solr possible. This can happen when Solr is down or the specified collectionName cannot be found.<br>"
                + "- check if Solr is running on the URL " + urlString.replace("/" + collectionName, "") + ".<br>"
                + "- check on http://localhost:8983/solr/#/~collections if the collection \'" + collectionName + "\' exists. "
                + "If this is not the case, use the \'Index again\' button to create a new Index.<br><br>"
                + "After restarting the instance or adding the collection name it might take a moment for the application to recognize the change.</html>");
        JOptionPane.showMessageDialog(null, errorMessage, "Connection lost", JOptionPane.ERROR_MESSAGE);
    }

    public String[] getFilterOptions(String fieldName) throws IOException, SolrServerException{
        SolrQuery query = buildQuery(fieldName);
        QueryResponse response = client.query(query);
        return getListOfValues(response, fieldName);
    }

    private SolrQuery buildQuery(String fieldName){
        SolrQuery query = new SolrQuery();
        query.setFacet(true);
        query.addFacetField(fieldName);
        query.setFacetLimit(-1);
        query.setRows(0);
        query.setQuery("*");
        return query;
    }

    private String[] getListOfValues(QueryResponse response, String fieldName){
        List<FacetField.Count> counts = response.getFacetField(fieldName).getValues();
        String[] formats = new String[counts.size()];
        int i = 0;
        for(FacetField.Count c : counts){
            formats[i] = c.getName();
            i++;
        }
        return formats;
    }

    public String getCollectionName(){ return collectionName; }
}
