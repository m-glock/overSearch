package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

    public Test(){

    }

    public void placeholder() throws IOException, SolrServerException {
        //try this again when the authentication is working

        PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
        try {
            URL url = new URL("https://imiba.sharepoint.com/sites/testwebsite2/_layouts/15/Doc.aspx?sourcedoc=%7B6DE924F2-8BAD-48CC-B9BF-828126DE06B5%7D&file=title_and_description.docx&action=default&mobileredirect=true");
            //Scanner s = new Scanner(url.openStream());
            // read from your scanner
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println("after buffered reader");
            out.println("after input reader");
            String inputLine;
            System.out.println("before while");
            while ((inputLine = in.readLine()) != null){
                out.println(inputLine);
                System.out.println("in while");
            }

            System.out.println("after while");
            in.close();
            out.println("after while loop");
        }catch(IOException ex) {
            // there was some connection problem, or the file did not exist on the server,
            // or your URL was not in the right format.
            // think about what to do now, and put it here.
            out.println(ex.getMessage()); // for now, simply output it.
        }catch (Exception ex){
            out.println(ex.getClass().toString());
            out.println(ex.getMessage());
        }

        out.close();


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
