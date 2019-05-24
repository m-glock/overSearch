package com.mareike.solrsearch;

public class SolrInstance {

    final private String urlString;
    private String collectionName;

    public SolrInstance(String solrURL, String collection){
        urlString = solrURL;
        collectionName = collection;
    }

    private void firstInit(){
        //TODO: start solr as a service and add the config file
    }

    public String getSolrUrl(){
        return urlString;
    }

    public String getCollectionName(){
        return collectionName;
    }
}
