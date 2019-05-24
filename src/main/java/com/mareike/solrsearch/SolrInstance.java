package com.mareike.solrsearch;

public class SolrInstance {

    final private String urlString;
    private String collectionName;

    public SolrInstance(String solrURL, String collection){
        urlString = solrURL;
        collectionName = collection;
    }

    public String getSolrUrl(){
        return urlString;
    }

    public String getCollectionName(){
        return collectionName;
    }
}
