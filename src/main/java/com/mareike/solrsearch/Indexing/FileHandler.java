package com.mareike.solrsearch.Indexing;

import org.apache.commons.io.FilenameUtils;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;

public class FileHandler {

    public FileHandler(){

    }

    public ContentStreamUpdateRequest addFilesToRequest(final File folder, ContentStreamUpdateRequest request) throws IOException{
        //TODO: include/exclude directories
        //TODO: add directory watcher here for each directory that is included
        //if file in list of allowed -> continue with recursion
        //else break/return
        File[] listOfFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                System.out.println("folder name: " + folder.getName());
                if(folder.getName().equals("folder3")){
                    return false;
                }
                return true;
            }
        });
        if(listOfFiles!= null) {
            for (File file : listOfFiles) {
                System.out.println("Name: " + file.getName() + ": " + file.getAbsolutePath());
                if (file.isFile()) {
                    System.out.println("File: " + file.getName() + " and content type: " + getContentType(file));
                    request.addFile(file, getContentType(file));
                }else{
                    System.out.println("going into directory");
                    request = addFilesToRequest(file, request);
                }
            }
        }
        return request;
    }

    public SolrInputDocument createSolrDocs(int i){
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "book-" + i);
        doc.addField("name", "The Legend of the Hobbit part " + i);
        doc.addField("stream_size","1");
        doc.addField("content_type", "text/plain");
        return doc;
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

    //TODO: make sure that only allowed directories are being watched
    //TODO: check that it does not interfere with any other action
    public void addDirectorWatcher(String path){
        try{
            Thread t = new Thread(new WatchDirectory(Paths.get(path), true));
            t.start();
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }
    }
}
