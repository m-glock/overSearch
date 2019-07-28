package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.ErrorMessage;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import java.io.IOException;
import java.util.List;

public class SolrInstance {

    public HttpSolrClient client;
    private String urlString;
    private String collectionName;

    public SolrInstance(String solrURL, String collection) throws HttpSolrClient.RemoteSolrException {
        urlString = solrURL;
        collectionName = collection;
        startClient();
        Main.logger.info("Solr instance created with URL: " + urlString);
    }

    public void changeSolrInstance(){
        client.setBaseURL(urlString + "/" + collectionName);
        Main.logger.info("Solr base URL changed to: " + client.getBaseURL() + "\n");
    }

    private void startClient(){
        client = new HttpSolrClient.Builder(urlString).build();
    }
    
    public boolean createCollection() throws IOException, SolrServerException {
        Main.logger.info("Create collection ...");
        String configName = "overSearchConfig";
        boolean success;
        if(client.getBaseURL().contains(collectionName)){
            success = true;
        }else{
            CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(collectionName, configName, 1, 1);
            NamedList resp = client.request(req);
            Main.logger.info("Response to request: " + resp.toString());
            success = resp.toString().contains("success");
            if(success){
                Main.logger.info("Creating the collection was successful.");
                client.setBaseURL(client.getBaseURL() + "/" + collectionName);
                Main.logger.info("Collection named " + collectionName + " created.\nSolr instance URL has been changed to: " + client.getBaseURL());
            } else {
                String message = "Collection could not be created.";
                new ErrorMessage(message);
                Main.logger.info(message);
            }
        }
        return success;
    }

    public boolean deleteCollection() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        Main.logger.info("Delete collection ...");
        String solrBaseURL = client.getBaseURL().replaceAll("/" + collectionName, "");
        client.setBaseURL(solrBaseURL);
        CollectionAdminRequest.Delete request = CollectionAdminRequest.Delete.deleteCollection(collectionName);
        NamedList resp = client.request(request);
        System.out.println("Response to request: " + resp);
        if(resp.toString().contains("success")){
            System.out.println("Collection has been removed. Solr instance now uses the URL: " + client.getBaseURL());
            return true;
        }else{
            String message = "Collection could not be deleted.";
            solrBaseURL = solrBaseURL + "/" + collectionName;
            client.setBaseURL(solrBaseURL);
            new ErrorMessage(message);
            Main.logger.info(message);
            return false;
        }
    }

    public String[] getFilterOptions(String fieldName) throws IOException, SolrServerException{
        SolrQuery query = buildQuery(fieldName);
        QueryResponse response = client.query(query);
        return getListOfValues(response, fieldName);
    }

    private SolrQuery buildQuery(String fieldName){
        Main.logger.info("Built facet query.");
        SolrQuery query = new SolrQuery();
        query.setFacet(true);
        query.addFacetField(fieldName);
        query.setFacetLimit(-1);
        query.setRows(0);
        query.setQuery("*");
        Main.logger.info("Query is: " + query.toString());
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
