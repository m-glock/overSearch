package com.mareike.solrsearch;

import com.microsoft.graph.http.HttpMethod;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Indexer {

    private static String collectionName;
    private static ExecutorService service;

    public static boolean indexFiles(ArrayList<String> paths, String collection){
        Main.logger.info("Start indexing...");
        collectionName = collection;
        service = Executors.newFixedThreadPool(1);
        boolean successfulLocal = false;
        boolean successfulSharePoint;
        for(String path : paths){
            Main.logger.info("Indexing request for " + path);
            String response = indexFileOrFolder(path);
            successfulLocal = response.contains("Successfully indexed");
        }
        Main.logger.info("Finished indexing local files.\nStaring to index SharePoint files.");
        String sharePointResponse = indexSharePointFiles();
        successfulSharePoint = sharePointResponse.contains("Successfully indexed");
        Main.logger.info("Finished indexing SharePoint files.");

        return successfulLocal && successfulSharePoint;
    }

    public static void setCollectionName(String collection){
        collectionName = collection;
    }

    public static String indexFileOrFolder(String path){
        try{
            path = path.replace("\\", "/");
            final URI uri = new URI("http", "localhost:7071", "/api/IndexFilesToSolr", "name=" + path, null);
            final HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod(HttpMethod.POST.name());
            con.setDoOutput(true);
            try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8)){
                osw.write("{ \"collectionName\": \"" + collectionName + "\"}");
                osw.flush();
            }

            Future task = service.submit(new FunctionCall(con, uri));
            String response = (String) task.get();
            if(response.contains("Error")){
                JLabel errorMessage = new JLabel();
                errorMessage.setText("Error occurred while preparing the request to the indexing function: " + response.replace("Error:","") + ". "
                        + "Please try to index again. If the problem persists, check the SharePoint function.");
                errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
                JOptionPane.showMessageDialog(null, errorMessage, "Indexing error", JOptionPane.ERROR_MESSAGE);
                Main.logger.info(errorMessage.getText());
                return "";
            }else {
                return response;
            }
        }catch(Exception ex){
            return "";
        }
    }

    private static String indexSharePointFiles(){
        try {
            final URI uri = new URI("http://localhost:7074/api/SharePointConnector?name=" + collectionName);
            final HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod(HttpMethod.GET.name());

            Future task = service.submit(new FunctionCall(con, uri));
            String response = (String) task.get();
            if(response.contains("Error")){
                JLabel errorMessage = new JLabel();
                errorMessage.setText("Error occurred while preparing the request to the SharePointConnector function: " + response.replace("Error:","") + ". "
                        + "Please try to index again. If the problem persists, check the SharePoint function.");
                errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
                JOptionPane.showMessageDialog(null, errorMessage, "Indexing error", JOptionPane.ERROR_MESSAGE);
                Main.logger.info(errorMessage.getText());
                return "";
            }else{
                return response;
            }
        }catch(Exception ex){
            return "";
        }
    }

    public static void deleteFile(String path){
        Main.logger.info("Send request to delete a file.");
        try{
            final URI uri = new URI("http", "localhost:7071", "/api/IndexFilesToSolr", "name=" + path, null);
            HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod(HttpMethod.GET.name());
            con.setRequestProperty("collectionName", collectionName);

            Main.logger.info("Sending " + con.getRequestMethod() + " request to delete " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                Main.logger.info(inputLine);
            in.close();
        }catch(Exception ex){
            JLabel errorMessage = new JLabel();
            errorMessage.setText("Error occurred while preparing the request for deleting a file from the index: " + ex.getMessage() + ".\n"
                    + "Please try to index again. If the problem persists, check the indexing function.");
            errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
            JOptionPane.showMessageDialog(null, errorMessage, "Indexing error", JOptionPane.ERROR_MESSAGE);
            Main.logger.info(errorMessage.getText());
        }
    }

    private static class FunctionCall implements Callable{

        private HttpURLConnection con;
        private URI uri;

        public FunctionCall(HttpURLConnection con, URI uri){
            this.con = con;
            this.uri = uri;
        }

        @Override
        public Object call() {
            try {
                System.out.println("\nSending " + con.getRequestMethod() + " request to URL : " + uri);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                String response = "";
                while ((inputLine = in.readLine()) != null)
                    response += inputLine;
                in.close();
                return response;
            } catch (IOException ex) {
                Main.logger.info(ex.getMessage());
                return "Error: " + ex.getMessage();
            }
        }
    }

}
