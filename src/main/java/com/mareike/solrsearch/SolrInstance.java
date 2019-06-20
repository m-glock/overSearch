package com.mareike.solrsearch;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.common.util.NamedList;


public class SolrInstance {

    public HttpSolrClient client;
    //public MicrosoftConnector msConnector;
    private final Indexer indexer;
    private final String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection){
        urlString = solrURL;
        collectionName = collection;

        indexer = new Indexer();
        firstInit();
        startClient();
        //TODO: does solr instance need a connector?
        //msConnector = new MicrosoftConnector();
    }

    private void firstInit(){
        //TODO: start solr as a service and add the config file
        //TODO: check if Solr is running on provided URL. If this is not the case, show message that Solr is down and how to install it?
    }
    
    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
        String configName = "localDocs";
        try{
            CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);
            NamedList resp = client.request(req);
            System.out.println(resp.toString());
            client.setBaseURL(client.getBaseURL() + "/" + collectionName);
            System.out.println("Solr instance created with url: " + client.getBaseURL());
        }catch(Exception ex){
            System.out.println(ex.getClass().toString() + " with message: " + ex.getMessage());
        }
    }
    
    public Boolean checkSolrConnection(){
        
        return true;
    }

    public String getSolrUrl(){ return urlString; }

    public String getCollectionName(){ return collectionName; }

    public Indexer getIndexer() {return indexer; }
}
