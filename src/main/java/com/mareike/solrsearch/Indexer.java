package com.mareike.solrsearch;

import com.microsoft.graph.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Indexer {

    private static final String indexFunctionURL = "http://localhost:7071/api/IndexFilesToSolr?name=";
    private static final String SharePointFunctionURL = "http://localhost:7074/api/SharePointConnector?name=";
    private static String collectionName;


    public static void indexFiles(ArrayList<String> paths, String collection){
        collectionName = collection;
        System.out.println("list from handler: ");
        for(String path : paths){
            System.out.println(path);
            indexFileOrFolder(path);
        }
        indexSharePointFiles();
    }

    public static void indexFileOrFolder(String path){
        //TODO: find better character for replacement and change it in the function as well
        path = path.replace(" ", "_");
        try{
            final URL url = new URL(indexFunctionURL + path);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(HttpMethod.POST.name());
            //TODO: add body to request
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            String st = "{\"collectionName\": \"" + collectionName + "\"}";
            osw.write(st);
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream

            Thread t = new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        System.out.println("\nSending 'POST' request to URL : " + url);
                        //con.getResponseCode();
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null)
                            System.out.println(inputLine);
                        in.close();

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

    public static void indexSharePointFiles(){
        try {
            final URL url = new URL(SharePointFunctionURL + collectionName);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        System.out.println("\nSending 'GET' request to URL : " + url);
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null)
                            System.out.println(inputLine);
                        in.close();
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
        }catch(Exception ex){
            System.out.println("Error occured while trying to send the request to the SharePointConnector function: " + ex.getMessage());
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
