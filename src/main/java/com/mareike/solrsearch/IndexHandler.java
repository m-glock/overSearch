package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexHandler {

    private SolrInstance solr;
    private String configName = "localDocs";
    private HttpSolrClient client;
    private String collectionURL;

    public IndexHandler(SolrInstance inst){
        solr = inst;
        try{
            createIndex();
        }catch(IOException io){

        }catch(SolrServerException serv){

        }catch (Exception e){

        }
    }

    private void createIndex() throws IOException, SolrServerException {
        client = new HttpSolrClient.Builder(solr.getSolrUrl()).build();

        final CollectionAdminRequest.Create req = CollectionAdminRequest.Create
                .createCollection(solr.getCollectionName(), configName, 1, 1);
        NamedList resp = client.request(req);
        collectionURL = solr.getSolrUrl()+ "/" + solr.getCollectionName();
    }

    public void addFiles() throws IOException, SolrServerException{
        client.setBaseURL(collectionURL);
        File file = new File("C:\\Users\\mareike\\Desktop\\Bachelorarbeit.pdf");

        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        request.addFile(file, "application/pdf");

        //req.setParam("literal.id", "doc1");
        //req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
        PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");

        try{
            out.println("In try");
            client.request(request);
        }
        catch(IOException e){
            e.printStackTrace(out);
            out.println("IOException message: ");
        }catch(HttpSolrClient.RemoteSolrException e){
            out.println("RemoteSolrException message: ");
            e.printStackTrace(out);
        }catch(Exception e){
            out.println("UnknownException message: ");
            e.printStackTrace(out);
        }finally{
            client.commit();
            out.close();
        }
    }
}
