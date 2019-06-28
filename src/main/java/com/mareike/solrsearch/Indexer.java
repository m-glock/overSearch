package com.mareike.solrsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Indexer {

    private static final String indexFunctionURL = "http://localhost:7071/api/IndexFilesToSolr?name=";
    private static final String SharePointFunctionURL = "http://localhost:7074/api/SharePointConnector?name=default";


    public static void indexFiles(ArrayList<String> paths){
        System.out.println("list from handler: ");
        for(String path : paths){
            System.out.println(path);
            indexFileOrFolder(path);
        }
    }

    public static void indexFileOrFolder(String path){
        path = path.replace(" ", "_");
        try{
            final URL url;
            //TODO: else if?
            if(path == null) {
                url = new URL(SharePointFunctionURL);
            }else{
                url = new URL(indexFunctionURL + path);
            }
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //TODO: add body to request
            con.setRequestMethod("GET");

            Thread t = new Thread(new Runnable() {
                @Override
                public void run(){
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))){
                        int responseCode = con.getResponseCode();
                        //TODO: remove this when everything is working
                        System.out.println("\nSending 'GET' request to URL : " + url);
                        System.out.println("Response Code : " + responseCode);

                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            t.start();
            while(t.isAlive()){
                System.out.println(".");
                TimeUnit.SECONDS.sleep(5);
            }
        }catch(Exception prot){
            System.out.println("Protocol Exception: " + prot.getClass().toString() + " and message " + prot.getMessage());
        }
    }

    public static void deleteFile(String fileName){
        fileName = fileName.replace(" ", "_");
        try{
            //TODO: change function to choose between indexing and deleting -> if both (update) the the two methods here are called
            URL url = new URL(indexFunctionURL + fileName);
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
