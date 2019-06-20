package com.mareike.solrsearch.DirectoryChooser;

import com.mareike.solrsearch.Indexing.IndexHandler;
import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectoryChooser extends JFrame {

    private IndexHandler handler;
    private MultiSelectionTree tree;
    private String basePath;

    //TODO: if node is selected, select all children as well?
    public DirectoryChooser(IndexHandler h){
        handler = h;
        basePath = "C:/Users/mareike/Documents/Studium/2.Semester-SS16/Info2";
        final MyFile mf = new MyFile(new File(basePath));
        final FileSystemModel model = new FileSystemModel(mf);
        tree = new MultiSelectionTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());

        initComponents();
    }

    public void initComponents(){
        Container pane = getContentPane();
        JScrollPane scrollPane = new JScrollPane(tree);
        pane.add(scrollPane, BorderLayout.CENTER);
        JButton button = new JButton("Select");
        //TODO: figure out size
        button.setSize(new Dimension(200, 100));
        pane.add(button, BorderLayout.PAGE_END);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code to save paths in index handler and close frame
                saveDirectories();
                dispose();
                indexFiles();
            }
        });
    }

    private void saveDirectories(){
        TreePath[] paths = tree.getSelectionPaths();
        int endIndex = basePath.lastIndexOf("/");
        String newPath = basePath.substring(0, endIndex+1);
        for(TreePath path : paths){
            String fullPath = buildPath(path);
            handler.addIncludedDirectory(newPath + fullPath);
        }
    }

    private void indexFiles(){
        System.out.println("list from handler: ");
        for(String path : handler.getDirectoryPaths()){
            System.out.println(path);
            path = path.replace(" ", "_");
            try{
                URL url = new URL("http://localhost:7071/api/IndexFilesToSolr?name=" + path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
            }catch(Exception prot){
                System.out.println("Protocol Exception: " + prot.getClass().toString() + " and message " + prot.getMessage());
            }
        }
    }

    private String buildPath(TreePath path){
        Object[] pathElements = path.getPath();
        String fullPath = "";
        for (Object pathElement : pathElements){
            fullPath = fullPath + pathElement + "/" ;
        }
        return fullPath;
    }
}
