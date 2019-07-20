package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SolrInstance {

    public HttpSolrClient client;
    private String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection) throws HttpSolrClient.RemoteSolrException {
        urlString = solrURL;
        collectionName = collection;
        startClient();
    }

    public void changeSolrInstance(String solrURL){
        urlString = solrURL;
        int index = solrURL.lastIndexOf(File.separator);
        collectionName = solrURL.substring(index+1);
        client.setBaseURL(urlString);
        System.out.println("Base url is now: " + client.getBaseURL());
    }

    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
    }
    
    public void createCollection() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        String configName = "overSearchConfig";
        CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);

        NamedList resp = client.request(req);
        System.out.println("Collection created. Response: " + resp.toString());
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

    /*public Boolean checkSolrConnection(){
        System.out.println("check solr connection.");
        Boolean canConnect = false;
        try {
            System.out.println("in try");
            SolrPing ping = new SolrPing();
            //ping.getParams().add("distrib", "true"); //To make it a distributed request against a collection
            System.out.println("created ping, sending it now");
            SolrPingResponse rsp = ping.process(client);
            System.out.println("Response is: " + rsp.toString() + " with status: " + rsp.getStatus());
            if(rsp.toString().contains("OK"))
                canConnect = true;
        }catch(Exception e){
            System.out.println("Error when pinging the Solr instance: " + e.getMessage());
        }
        return canConnect;
    }*/

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
