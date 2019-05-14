package com.mycompany.app;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.LinkedList;


public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {
        /*File folder = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke");
        File[] listOfFiles = folder.listFiles();

        HashMap<String, String> namesAndDates = getNamesAndDates(listOfFiles);

        try (PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt"))  {
            for(HashMap.Entry<String,String> mapValue : namesAndDates.entrySet()){
                out.println(mapValue.getKey() + ": " + mapValue.getValue());
            }

        }*/


        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/localDocs20").build();


        File folder = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke");
        File[] listOfFiles = folder.listFiles();
        ArrayList<SolrInputDocument> docList = new ArrayList<>();
        for(File file : listOfFiles){
            SolrInputDocument doc = new SolrInputDocument();
            //TODO: get data from files and write to doc
            SolrRequest req = new SolrRequest() {
                @Override
                public SolrParams getParams() {
                    return null;
                }

                @Override
                protected SolrResponse createResponse(SolrClient solrClient) {
                    return null;
                }
            }
            client.request();

            docList.add(doc);
            //if(i%100==0) client.commit();  // periodically flush
        }

        /*for(int i=0;i<1000;++i) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("cat", "book");
            doc.addField("id", "book-" + i);
            doc.addField("name", "The Legend of the Hobbit part " + i);
            client.add(doc);
            if(i%100==0) client.commit();  // periodically flush
        }*/

        client.add(docList);
        client.commit();
    }

    public static HashMap<String, String> getNamesAndDates(File[] listOfFiles){
        HashMap<String, String> fileList = new HashMap<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String modifiedDate = sdf.format(listOfFiles[i].lastModified());

                fileList.put(fileName, modifiedDate);
            } else if (listOfFiles[i].isDirectory()) {
                String fileName = "Directory: " + listOfFiles[i].getName();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String modifiedDate = sdf.format(listOfFiles[i].lastModified());

                fileList.put(fileName, modifiedDate);
            }
        }
        return fileList;
    }
}