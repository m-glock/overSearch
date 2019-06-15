package com.mareike.solrsearch.Indexing;

import com.mareike.solrsearch.SolrInstance;
import org.apache.commons.io.FilenameUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexHandler {

    private SolrInstance solr;
    private String configName = "localDocs";
    private String collectionURL;
    private ArrayList<String> excludedDir;

    public IndexHandler(SolrInstance inst){
        System.out.println("create index handler");

        solr = inst;
        excludedDir = new ArrayList<>();

        //TODO: exception handling
        try{
            createIndex();
        } catch(IOException io){
            System.out.println("IOException message: " + io.getMessage());
        } catch(SolrServerException serv){
            System.out.println("SolrServerException message: " + serv.getMessage());
        } catch (Exception e){
            System.out.println("UnknownException message: " + e.getMessage());
        }
    }

    private void createIndex() throws IOException, SolrServerException {
        //TODO: check that colection name does not already exist
        final CollectionAdminRequest.Create req = CollectionAdminRequest.Create.createCollection(solr.getCollectionName(), configName, 1, 1);

        //TODO: exception handling
        try{
            NamedList resp = solr.client.request(req);
            System.out.println("response: " + resp.toString());
            collectionURL = solr.client.getBaseURL() + "/" + solr.getCollectionName();
            solr.client.setBaseURL(collectionURL);
            System.out.println("base url: " + solr.client.getBaseURL());
        } catch(IOException e){
            System.out.println("IOException message: "+ e.getMessage());
        }catch(HttpSolrClient.RemoteSolrException e){
            System.out.println("RemoteSolrException message: "+ e.getMessage());
        }catch(Exception e){
            System.out.println("UnknownException message: " + e.getMessage());
        }finally{
            solr.client.commit();
        }
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

    public void indexLocalFiles(String path) throws IOException, SolrServerException{
        File folder = new File(path);
        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        request = addFilesToRequest(folder, request);
        //req.setParam("literal.id", "doc1");

        //TODO: exception handling
        try{
            NamedList resp = solr.client.request(request);
            System.out.println(resp.toString());
        }
        catch(IOException e){
            System.out.println("IOException message: " + e.getMessage());
        }catch(HttpSolrClient.RemoteSolrException e){
            System.out.println("RemoteSolrException message: " + e.getMessage());
        }catch(Exception e){
            System.out.println("UnknownException message: " + e.getMessage());
        }finally{
            solr.client.commit();
            //createDirectoryWatcher(path, true);
        }
    }

    public void indexSharepointFiles() throws IOException, SolrServerException {
        System.out.println("starting to index SharePoint files");
        List<String> fileURLs = solr.msConnector.getAllFiles();
        //ArrayList<File> files = new ArrayList<>();
        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");
        for(String url : fileURLs){
            File file = new File(url);
            request.addFile(file, getContentType(file));
        }
        System.out.println("before sending request");
        NamedList resp = solr.client.request(request);
        System.out.println("response: " + resp.toString());
        solr.client.commit();

        /*try{
            for(int i=0;i<10;++i) {
                SolrInputDocument doc = createSolrDocs(i);
                solr.client.add(doc);
                if(i%100==0) solr.client.commit();  // periodically flush
            }
            solr.client.commit();
        }catch(SolrServerException ex){
            System.out.println("IOException message: " + ex.getMessage());
        }catch(IOException ex){
            System.out.println("RemoteSolrException message: " + ex.getMessage());
        }catch(Exception ex){
            System.out.println("UnknownException message: " + ex.getMessage());
        }*/

    }

    private SolrInputDocument createSolrDocs(int i){
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "book-" + i);
        doc.addField("name", "The Legend of the Hobbit part " + i);
        doc.addField("stream_size","1");
        doc.addField("content_type", "text/plain");
        doc.addField("content_type", "text/plain");
        return doc;
    }

    private ContentStreamUpdateRequest addFilesToRequest(final File folder, ContentStreamUpdateRequest request) throws IOException{
        //TODO: include/exclude directories
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
