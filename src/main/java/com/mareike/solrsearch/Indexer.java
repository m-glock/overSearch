package com.mareike.solrsearch;

import com.microsoft.graph.http.HttpMethod;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Indexer {

    private static final String indexFunctionURL = "http://localhost:7071/api/IndexFilesToSolr?name=";
    private static final String SharePointFunctionURL = "http://localhost:7074/api/SharePointConnector?name=";
    private static String collectionName;


    public static void indexFiles(ArrayList<String> paths, String collection){
        collectionName = collection;
        for(String path : paths){
            indexFileOrFolder(path);
        }
        //indexSharePointFiles();
    }

    public static void indexFileOrFolder(String path){
        path = path.replace(" ", "%20");
        try{
            final URL url = new URL(indexFunctionURL + path);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(HttpMethod.POST.name());
            con.setRequestProperty("collection", collectionName);
            con.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            osw.write("{}");
            osw.flush();
            osw.close();

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
            con.setRequestMethod(HttpMethod.GET.name());

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
            System.out.println("Error occurred while trying to send the request to the SharePointConnector function: " + ex.getMessage());
        }
    }

    public static void deleteFile(String fileName){
        fileName = fileName.replace(" ", "%20");
        try{
            URL url = new URL(indexFunctionURL + fileName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(HttpMethod.GET.name());
            con.setRequestProperty("collection",collectionName);

            System.out.println("Sending request to delete " + fileName + " with http method " + con.getRequestMethod());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }catch(Exception prot){
            System.out.println("Protocol Exception: " + prot.getClass().toString() + " and message " + prot.getMessage());
        }
    }

}
