package com.mareike.solrsearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Indexer {

    /*private ArrayList<String> includedDirectories;

    public Indexer(){
        includedDirectories = new ArrayList<>();
    }

    public void addDirectory(String path){
        includedDirectories.add(path);
    }*/

    public static void indexFiles(ArrayList<String> paths){
        /*if(paths == null){
            paths = includedDirectories;
        }*/
        System.out.println("list from handler: ");
        for(String path : paths){
            System.out.println(path);
            indexSingleFile(path);
        }
    }

    public static void indexSingleFile(String path){
        path = path.replace(" ", "_");
        try{
            URL url = new URL("http://localhost:7071/api/IndexFilesToSolr?name=" + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            //TODO: remove this when everything is working
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception prot){
            System.out.println("Protocol Exception: " + prot.getClass().toString() + " and message " + prot.getMessage());
        }
    }

}
