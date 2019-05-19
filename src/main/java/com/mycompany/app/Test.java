package com.mycompany.app;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Test {

    public Test(){

    }

    public void placeholder() throws IOException, SolrServerException {
     /*File folder = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke");
        File[] listOfFiles = folder.listFiles();

        HashMap<String, String> namesAndDates = getNamesAndDates(listOfFiles);

        try (PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt"))  {
            for(HashMap.Entry<String,String> mapValue : namesAndDates.entrySet()){
                out.println(mapValue.getKey() + ": " + mapValue.getValue());
            }

        }*/


        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/localDocs20").build();


        /*File folder = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke");
        File[] listOfFiles = folder.listFiles();
        ArrayList<SolrInputDocument> docList = new ArrayList<>();
        for(File file : listOfFiles){
            SolrInputDocument doc = new SolrInputDocument();
            //TODO: get data from files and write to doc

            //GenericSolrRequest request = new GenericSolrRequest(SolrRequest.METHOD.POST, "/update/extract", null);

            //if(i%100==0) client.commit();  // periodically flush
        }*/

        File file = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Netzwerke\\01 - Einführung.pdf");
        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
        req.setParam("literal.id", "doc1");
        req.setParam("commit", "true");
        req.addFile(file, "application/pdf");
        //req.setParam("extractOnly", "true");

        client.request(req);

        /*for(int i=0;i<1000;++i) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("cat", "book");
            doc.addField("id", "book-" + i);
            doc.addField("name", "The Legend of the Hobbit part " + i);
            client.add(doc);
            if(i%100==0) client.commit();  // periodically flush
        }*/

        //client.add(docList);
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
