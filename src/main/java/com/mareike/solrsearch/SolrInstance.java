package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import java.io.IOException;
import java.util.*;

public class SolrInstance {

    public HttpSolrClient client;
    private final String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection) throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        urlString = solrURL;
        collectionName = collection;
        startClient();
        //firstInit();
        //checkSolrConnection();
    }

    public SolrInstance(String solrURL){
        urlString = solrURL;
        int index = solrURL.lastIndexOf("/");
        collectionName = solrURL.substring(index+1);
        client = new HttpSolrClient.Builder(urlString).build();
    }

    /*private void firstInit(){
        //TODO: check if Solr is running on provided URL. If this is not the case, show message that Solr is down and how to install it?
        if(checkSolrConnection())
            System.out.println("Solr is up and running");
        else
            System.out.println("Could not connect to Solr. Please check if Solr is running.");
    }*/

    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
    }
    
    public void createCollection() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        String configName = "localDocs";
        CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);

        NamedList resp = client.request(req);
        System.out.println("Collection created. Response: " + resp.toString());
        client.setBaseURL(client.getBaseURL() + "/" + collectionName);
        System.out.println("Solr instance created with URL: " + client.getBaseURL());
    }

    public void deleteCollection() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        //client.setBaseURL(urlString);
        String solrBaseURL = client.getBaseURL().replaceAll("/" + collectionName, "");
        client.setBaseURL(solrBaseURL);
        CollectionAdminRequest.Delete request = CollectionAdminRequest.Delete.deleteCollection(collectionName);
        NamedList resp = client.request(request);
        System.out.println("Collection removed. Solr instance now uses the URL: " + client.getBaseURL());
    }

    //TODO: fix ping maybe?
    /*public Boolean checkSolrConnection(){
        System.out.println("check solr connection.");
        Boolean canConnect = false;
        try {
            System.out.println("in try");
            SolrPing ping = new SolrPing();
            ping.getParams().add("distrib", "true"); //To make it a distributed request against a collection
            System.out.println("created ping, sending it now");
            SolrPingResponse rsp = ping.process(client, collectionName);
            System.out.println("Response is: " + rsp.toString() + " with status: " + rsp.getStatus());
        }catch(IOException io){

        }catch(SolrServerException serv){

        }finally{
            return canConnect;
        }
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
        FacetField contentType = response.getFacetField(fieldName);
        List<FacetField.Count> counts = contentType.getValues();
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
