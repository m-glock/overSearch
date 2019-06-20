package com.mareike.solrsearch.DirectoryChooser;

import com.mareike.solrsearch.Indexer;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class DirectoryChooser extends JFrame {

    private MultiSelectionTree tree;
    private String basePath;
    private Indexer indexer;

    //TODO: if node is selected, select all children as well?
    public DirectoryChooser(Indexer indexer, String basePath){
        this.basePath = basePath;
        this.indexer = indexer;
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
                indexer.indexFiles();
            }
        });
    }

    private void saveDirectories(){
        TreePath[] paths = tree.getSelectionPaths();
        int endIndex = basePath.lastIndexOf("/");
        String newPath = basePath.substring(0, endIndex+1);
        for(TreePath path : paths){
            String fullPath = buildPath(path);
            indexer.addDirectory(newPath + fullPath);
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

    public void addDirectoryWatcher(String path){
        try{
            Thread t = new Thread(new WatchDirectory(Paths.get(path), true));
            t.start();
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }
    }
}
