package com.mareike.solrsearch.localDirectories;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirectorySelector extends JFrame {

    private MultiSelectionTree tree;
    private String basePath;
    private ExecutorService executorService;

    //TODO: if node is selected, select all children as well?
    public DirectorySelector(String basePath){
        this.basePath = basePath;
        startThreads();

        final MyFile mf = new MyFile(new File(basePath));
        final FileSystemModel model = new FileSystemModel(mf);
        tree = new MultiSelectionTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());

        //increase default font size
        final Font currentFont = tree.getFont();
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5);
        tree.setFont(bigFont);
    }
    
    public MultiSelectionTree getTree(){
        return tree;
    }

    public void startThreads(){executorService = Executors.newFixedThreadPool(4);}

    public ArrayList<String> listDirectories() throws NullPointerException{
        TreePath[] paths = tree.getSelectionPaths();
        ArrayList<String> fullPaths = new ArrayList<>();
        int endIndex = basePath.lastIndexOf(File.separator);
        String newBasePath = basePath.substring(0, endIndex+1);
        for(TreePath path : paths){
            String fullPath = newBasePath + buildPath(path);
            addDirectoryWatcher(fullPath);
            fullPaths.add(fullPath);
        }
        return fullPaths;
    }

    public ArrayList<String> loadIndexedPaths(){
        //load paths in ArrayList
        ArrayList<String> directories = new ArrayList<>();
        String directoryPath;
        if(new File("directories.txt").exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("directories.txt"))) {
                while ((directoryPath = bufferedReader.readLine()) != null) {
                    directories.add(directoryPath);
                    addDirectoryWatcher(directoryPath);
                }
            } catch (Exception e) {
                System.out.println("Error when reading out directories. " + e.getMessage());
            }
        }
        return directories;
    }

    private String buildPath(TreePath path){
        Object[] pathElements = path.getPath();
        String fullPath = "";
        for (Object pathElement : pathElements){
            fullPath = fullPath + pathElement + File.separator ;
        }
        return fullPath;
    }

    private void addDirectoryWatcher(String path){
        System.out.println("start directory watcher on: " + path);
        try{
            executorService.submit(new DirectoryWatchService(Paths.get(path), true));
        }catch(IOException io){
            System.out.println("Error when leading indexed paths: " + io.getMessage());
        }
    }

    public void removeDirectoryWatchers(){
        executorService.shutdownNow();
        System.out.println("directory watchers removed.");
    }
}
