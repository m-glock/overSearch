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
        PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
        client = new HttpSolrClient.Builder(solr.getSolrUrl()).build();

        final CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(solr.getCollectionName(), configName,1, 1);

        try{
            out.println("In try");
            NamedList resp = client.request(req);
            out.println("response: " + resp.toString());
            out.close();
            collectionURL = client.getBaseURL() + "/" + solr.getCollectionName();
            client.setBaseURL(collectionURL);
            out.println("base url: " + client.getBaseURL());
        } catch(IOException e){

            out.println("IOException message: ");
            e.printStackTrace(out);
        }catch(HttpSolrClient.RemoteSolrException e){
            out.println("RemoteSolrException message: ");
            e.printStackTrace(out);
        }catch(Exception e){
            out.println("UnknownException message: ");
            e.printStackTrace(out);
        }finally{
            client.commit();

        }

    }

    public void indexFiles(String path) throws IOException, SolrServerException{
        //TODO: how tof ind out if there are new files?


        PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename1.txt");
        File folder = new File(path);
        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");
        request = addFilesToRequest(folder, request);
        /*File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for(File file : listOfFiles){
            //TODO: if file is folder, go inside and do same thing (recursive)
            //out.println("content type: " + getContentType(file));
            request.addFile(file, getContentType(file));
        }*/

        //req.setParam("literal.id", "doc1");
        //req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);


        try{
            out.println("In try");
            out.println("path:" + request.getPath());
            out.println("to string:" + request.toString());
            out.println(client.getBaseURL());
            NamedList resp = client.request(request);
            out.println(resp.toString());

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

    private ContentStreamUpdateRequest addFilesToRequest(File folder, ContentStreamUpdateRequest request) throws IOException{
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles){
            //TODO: if file is folder, go inside and do same thing (recursive)
            if(file.isDirectory()){
                request = addFilesToRequest(file, request);
            }
            //out.println("content type: " + getContentType(file));
            request.addFile(file, getContentType(file));
        }
        return request;
    }

    private String getContentType(File file){
        //TODO: add other file extensions and check for default
        String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        String type;
        switch(extension){
            case "pdf":
                type = "application/pdf";
                break;
            case "doc": case "docx":
                type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            case "xls": case "xlsx":
                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            case "ppt": case "pptx":
                type = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                break;
            default:
                //is there a basic content type?
                type = "plain/text";
                break;
        }
        return type;
    }
}
