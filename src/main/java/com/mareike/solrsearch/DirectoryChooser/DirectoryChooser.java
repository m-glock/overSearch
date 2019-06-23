package com.mareike.solrsearch.DirectoryChooser;

import com.mareike.solrsearch.Indexer;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DirectoryChooser extends JFrame {

    private MultiSelectionTree tree;
    private String basePath;

    //TODO: if node is selected, select all children as well?
    public DirectoryChooser(String basePath){
        this.basePath = basePath;
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

    public ArrayList<String> listDirectories(){
        TreePath[] paths = tree.getSelectionPaths();
        ArrayList<String> fullPaths = new ArrayList<>();
        int endIndex = basePath.lastIndexOf("/");
        String newBasePath = basePath.substring(0, endIndex+1);
        for(TreePath path : paths){
            String fullPath = newBasePath + buildPath(path);
            addDirectoryWatcher(fullPath);
            fullPaths.add(fullPath);
        }
        return fullPaths;
    }

    private String buildPath(TreePath path){
        Object[] pathElements = path.getPath();
        String fullPath = "";
        for (Object pathElement : pathElements){
            fullPath = fullPath + pathElement + "/" ;
        }
        return fullPath;
    }

    //TODO: where to run this so that it does not only run when the application is open (service?)
    public void addDirectoryWatcher(String path){
        System.out.println("start directory watcher on: " + path);
        try{
            Thread t = new Thread(new WatchDirectory(Paths.get(path), true));
            t.start();
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }
    }
}
