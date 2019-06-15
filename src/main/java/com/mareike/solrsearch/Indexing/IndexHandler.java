package com.mareike.solrsearch.Indexing;

import com.mareike.solrsearch.SolrInstance;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;

public class IndexHandler {

    private SolrInstance solr;
    private String configName = "localDocs";
    private String collectionURL;
    private FileHandler indexer;

    public IndexHandler(SolrInstance inst){
        System.out.println("create index handler");
        solr = inst;
        indexer = new FileHandler();

        //TODO: exception handling
        try{
            createIndex();
        } catch(IOException io){
            System.out.println("IOException message: " + io.getMessage());
        } catch(SolrServerException serv){
            System.out.println("SolrServerException message: " + serv.getMessage());
        } catch(HttpSolrClient.RemoteSolrException rem){
            System.out.println("RemoteSolrException message: " + rem.getMessage());
        } catch (Exception e){
            System.out.println("UnknownException message: " + e.getMessage());
        }
    }

    private void createIndex() throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        //TODO: check that collection name does not already exist
        try{
            CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(solr.getCollectionName(), configName, 1, 1);
            NamedList resp = solr.client.request(req);
            System.out.println(resp.toString());
        } catch(HttpSolrClient.RemoteSolrException rem){
            System.out.println("RemoteSolrException caught: " + rem.getMessage());
        }
        collectionURL = solr.client.getBaseURL() + "/" + solr.getCollectionName();
        solr.client.setBaseURL(collectionURL);
        solr.client.commit();
    }

    public void indexLocalFiles(String path) throws IOException, SolrServerException, HttpSolrClient.RemoteSolrException {
        System.out.println("starting to index local files");
        File folder = new File(path);
        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        request = indexer.addFilesToRequest(folder, request);
        NamedList resp = solr.client.request(request);
        //TODO: do something if http error is returned
        System.out.println(resp.toString());
        solr.client.commit();
    }

    public void indexSharepointFiles() throws IOException, SolrServerException {
        System.out.println("starting to index SharePoint files");
        List<String> fileURLs = solr.msConnector.getAllFiles();
        //ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");
        for(String url : fileURLs){
            System.out.println("URL of file is: " + url);
        }
        //TODO: index files
        //TODO: do something if http error is returned

        /*for(int i=0;i<10;++i) {
            SolrInputDocument doc = FileHandler.createSolrDocs(i);
            solr.client.add(doc);
            if(i%100==0) solr.client.commit();  // periodically flush
         }
         solr.client.commit();*/
    }

    //TODO: index or update files depending on event from watcher; depends on where watcher is called and how directory chooser is working
    public static void updateFiles(WatchEvent event, Path path){
        System.out.println("In index handler: " + event.kind());
        switch(event.kind().name()){
            case "ENTRY_CREATE":
                //indexLocalFiles(path.toString());
            case "ENTRY_MODIFY":

            case "ENTRY_DELETE":

            default:
        }
    }
}
