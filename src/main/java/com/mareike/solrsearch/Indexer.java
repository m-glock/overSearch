package com.mareike.solrsearch;

import javax.swing.tree.TreePath;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.ArrayList;

public class Indexer {

    private ArrayList<String> includedDirectories;

    public Indexer(){
        includedDirectories = new ArrayList<>();
    }

    public void addDirectory(String path){
        includedDirectories.add(path);
    }

    public void indexFiles(){
        System.out.println("list from handler: ");
        for(String path : includedDirectories){
            System.out.println(path);
            /*path = path.replace(" ", "_");
            try{
                URL url = new URL("http://localhost:7071/api/IndexFilesToSolr?name=" + path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
            }catch(Exception prot){
                System.out.println("Protocol Exception: " + prot.getClass().toString() + " and message " + prot.getMessage());
            }*/
        }
    }


}
