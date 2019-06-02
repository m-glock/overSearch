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
    private String collectionURL;

    public IndexHandler(SolrInstance inst){
        solr = inst;
        try{
            createIndex();
        } catch(IOException io){

        } catch(SolrServerException serv){

        } catch (Exception e){

        }
    }

    private void createIndex() throws IOException, SolrServerException {
        //TODO: check that colection name does not already exist
        final CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(solr.getCollectionName(), configName, 1, 1);

        try{
            NamedList resp = solr.client.request(req);
            System.out.println("response: " + resp.toString());
            collectionURL = solr.client.getBaseURL() + "/" + solr.getCollectionName();
            solr.client.setBaseURL(collectionURL);
            System.out.println("base url: " + solr.client.getBaseURL());
        } catch(IOException e){
            System.out.println("IOException message: ");
        }catch(HttpSolrClient.RemoteSolrException e){
            System.out.println("RemoteSolrException message: ");
        }catch(Exception e){
            System.out.println("UnknownException message: ");
        }finally{
            solr.client.commit();
        }

    }

    public void indexFiles(String path) throws IOException, SolrServerException{
        //TODO: how to find out if there are new files?
        File folder = new File(path);
        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        request = addFilesToRequest(folder, request);
        //req.setParam("literal.id", "doc1");
        try{
            NamedList resp = solr.client.request(request);
            System.out.println(resp.toString());
        }
        catch(IOException e){
            System.out.println("IOException message: ");
        }catch(HttpSolrClient.RemoteSolrException e){
            System.out.println("RemoteSolrException message: ");
        }catch(Exception e){
            System.out.println("UnknownException message: ");
        }finally{
            solr.client.commit();
        }
    }

    private ContentStreamUpdateRequest addFilesToRequest(File folder, ContentStreamUpdateRequest request) throws IOException{
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles!= null) {
            for (File file : listOfFiles) {
                //out.println("Name: " + file.getName() + ": " + file.getAbsolutePath());
                if (file.isDirectory()) {
                    //out.println("going into directory");
                    request = addFilesToRequest(file, request);
                }else{
                    //out.println("File: " + file.getName() + " and content type: " + getContentType(file));
                    request.addFile(file, getContentType(file));
                }
            }
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

    private void findNewFiles(){

    }
}
