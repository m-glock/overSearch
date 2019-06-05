package com.mareike.solrsearch;

import org.apache.solr.client.solrj.impl.HttpSolrClient;


public class SolrInstance {

    public HttpSolrClient client;
    public MicrosoftConnector msConnector;
    private final IndexHandler handler;
    private final String urlString;
    private String collectionName;
    

    public SolrInstance(String solrURL, String collection){
        urlString = solrURL;
        collectionName = collection;
        firstInit();
        //might not happen in initial setup in the end
        //createIndexHandler();
        startClient();
        handler = new IndexHandler(this);
        msConnector = new MicrosoftConnector();
    }

    private void firstInit(){
        //TODO: start solr as a service and add the config file
    }
    
    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
    }
    
    public void createIndex(){
        //TODO: needs to be async, in a new thread or need a loading spinner while indexing files
        try{
            System.out.println("Index handler is null: " + (handler == null));
            handler.indexFiles("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2");
            System.out.println("Index filled");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public Boolean checkSolrConnection(){
        
        return true;
    }
    
    public String getSolrUrl(){
        return urlString;
    }

    public String getCollectionName(){
        return collectionName;
    }
}
