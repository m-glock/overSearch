package com.mareike.solrsearch;

import com.microsoft.graph.http.HttpMethod;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Indexer {

    private static String collectionName;


    public static void indexFiles(ArrayList<String> paths, String collection){
        collectionName = collection;
        for(String path : paths){
            indexFileOrFolder(path);
        }
        indexSharePointFiles();
    }

    public static void indexFileOrFolder(String path){
        try{
            path = path.replace("\\", "/");
            final URI uri = new URI("http", "localhost:7071", "/api/IndexFilesToSolr", "name=" + path, null);
            final HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod(HttpMethod.POST.name());
            con.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            osw.write("{ \"collectionName\": \"" + collectionName + "\"}");
            osw.flush();
            osw.close();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        System.out.println("\nSending 'POST' request to URL : " + uri);
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
            System.out.println("Error occurred while preparing the request to the indexing function: " + ex.getMessage());
        }
    }

    private static void indexSharePointFiles(){
        try {
            final URI url = new URI("http://localhost:7074/api/SharePointConnector?name=" + collectionName);
            final HttpURLConnection con = (HttpURLConnection) url.toURL().openConnection();
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
            System.out.println("Error occurred while preparing the request to the SharePointConnector function: " + ex.getMessage());
        }
    }

    public static void deleteFile(String path){
        try{
            final URI uri = new URI("http", "localhost:7071", "/api/IndexFilesToSolr", "name=" + path, null);
            HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod(HttpMethod.GET.name());
            con.setRequestProperty("collection",collectionName);

            System.out.println("Sending request to delete " + path + " with http method " + con.getRequestMethod());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }catch(Exception ex){
            System.out.println("Error occurred while preparing the request for deleting a file from the index: " + ex.getMessage());
        }
    }

}
