package com.mareike.solrsearch;

import org.apache.commons.io.FilenameUtils;
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

    public void addFiles(String path) throws IOException, SolrServerException{
        client.setBaseURL(collectionURL);
        File file = new File(path);

        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        request.addFile(file, getContentType(file));

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

    private String getContentType(File file){
        //pdf, word, excel, ppt, txt, ...
        String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        String type;
        switch(extension){
            case "pdf":
                type = "application/pdf";
                break;
            default:
                //is there a basic content type?
                type = "something";
                break;
        }
        return type;
    }
}
