package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.common.util.NamedList;
import java.io.IOException;

public class SolrInstance {

    public HttpSolrClient client;
    private final String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection) throws IOException, SolrServerException{
        urlString = solrURL;
        collectionName = collection;
        firstInit();
        startClient();
    }

    private void firstInit(){
        //TODO: start solr as a service and add the config file -> manually
        //TODO: check if Solr is running on provided URL. If this is not the case, show message that Solr is down and how to install it?
        if(checkSolrConnection())
            System.out.println("Solr is up and running");
        else
            System.out.println("Could not connect to Slr. Please ...");
    }
    
    private void startClient() throws IOException, SolrServerException {
        client = new HttpSolrClient.Builder(urlString).build();
        String configName = "localDocs";
        try{
            CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);
            NamedList resp = client.request(req);
            System.out.println(resp.toString());
        }catch(HttpSolrClient.RemoteSolrException ex){ //TODO: need to do something if 'collection already exists'?
            System.out.println("RemoteSolrException with message: " + ex.getMessage());
        }
        client.setBaseURL(client.getBaseURL() + "/" + collectionName);
        System.out.println("Solr instance created with url: " + client.getBaseURL());
    }
    
    public Boolean checkSolrConnection(){
        Boolean canConnect = false;

        return canConnect;
    }

    public String getSolrUrl(){ return urlString; }

    public String getCollectionName(){ return collectionName; }
}
