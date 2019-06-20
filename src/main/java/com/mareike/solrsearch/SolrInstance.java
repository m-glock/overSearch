package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import com.mareike.solrsearch.Indexing.IndexHandler;

import java.io.IOException;
import java.util.ArrayList;


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
        startClient();
        handler = new IndexHandler();
        //TODO: does solr instance need a connector?
        msConnector = new MicrosoftConnector();
    }

    private void firstInit(){
        //TODO: start solr as a service and add the config file
        //TODO: check if Solr is running on provided URL. If this is not the case, show message that Solr is down and how to install it?
    }
    
    private void startClient(){ client = new HttpSolrClient.Builder(urlString).build(); }

    //TODO: exception handling
    public void fillIndex(){
        String filePath = "C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2";
        /*try{
            handler.indexLocalFiles(filePath);
            handler.indexSharepointFiles();
            //TODO: remove after watcher is created in filehandler
            //handler.addDirectorWatcher(filePath);
        } catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        } catch(SolrServerException serv){
            System.out.println("SolrServerException: " + serv.getMessage());
        } catch(HttpSolrClient.RemoteSolrException rem){
            System.out.println("RemoteSolrException: " + rem.getMessage());
        } catch(Exception e){
            System.out.println("Unknown Exception: " + e.getMessage());
        }*/
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

    public IndexHandler getIndexHandler(){return handler;}
}
