package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.util.NamedList;
import java.io.IOException;

public class SolrInstance {

    public HttpSolrClient client;
    private final String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection) throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        urlString = solrURL;
        collectionName = collection;
        startClient();
        //firstInit();
        //createCollection();
        //checkSolrConnection();
    }

    public SolrInstance(String solrURL){
        urlString = solrURL;
        int index = solrURL.lastIndexOf("/");
        collectionName = solrURL.substring(index+1);
        client = new HttpSolrClient.Builder(urlString).build();
    }

    /*private void firstInit(){
        //TODO: start solr as a service and add the config file -> manually
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
        System.out.println("Solr instance created with url: " + client.getBaseURL());
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

    public String getSolrUrl(){ return urlString; }

    public String getCollectionName(){ return collectionName; }
}
