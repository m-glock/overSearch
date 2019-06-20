package com.mareike.solrsearch.DirectoryChooser;

import com.mareike.solrsearch.Indexing.IndexHandler;
import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DirectoryChooser extends JFrame {

    private IndexHandler handler;
    private MultiSelectionTree tree;
    private String basePath;

    //TODO: if node is selected, select all children as well?
    public DirectoryChooser(IndexHandler h){
        handler = h;
        basePath = "C:/Users/mareike/Documents/Studium";
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
                TreePath[] paths = tree.getSelectionPaths();
                for(TreePath path : paths){
                    String fullPath = buildPath(path);
                    handler.addIncludedDirectory(basePath + fullPath);
                }
                dispose();
                System.out.println("list from handler: ");
                for(String st : handler.getDirectoryPaths()){
                    System.out.println(st);
                }
            }
        });
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
