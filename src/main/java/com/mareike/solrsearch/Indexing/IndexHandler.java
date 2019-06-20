package com.mareike.solrsearch.Indexing;

import com.mareike.solrsearch.DirectoryChooser.DirectoryChooser;
import com.mareike.solrsearch.SolrInstance;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;

public class IndexHandler {

    private ArrayList<String> directoryPaths;

    public IndexHandler(){
        directoryPaths = new ArrayList<>();
    }


    //TODO: index or update files depending on event from watcher; depends on where watcher is called and how directory chooser is working
    public static void updateFiles(WatchEvent event, Path path){
        System.out.println("In index handler: " + event.kind());
        switch(event.kind().name()){
            case "ENTRY_CREATE":
                //indexLocalFiles(path.toString());
            case "ENTRY_MODIFY":

            case "ENTRY_DELETE":

            default:
        }
    }

    public void addDirectorWatcher(String path){
        try{
            Thread t = new Thread(new WatchDirectory(Paths.get(path), true));
            t.start();
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }
    }


    public static void main(String[] args){
        IndexHandler handler = new IndexHandler();
        DirectoryChooser frame = new DirectoryChooser(handler);
        frame.setSize(800, 600);
        frame.setVisible(true);


    }

    public void addIncludedDirectory(String path){
        directoryPaths.add(path);
    }

    public ArrayList<String> getDirectoryPaths(){
        return directoryPaths;
    }
}
