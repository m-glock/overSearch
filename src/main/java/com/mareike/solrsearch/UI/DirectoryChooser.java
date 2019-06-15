package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.Indexing.FileSystemModel;
import com.mareike.solrsearch.Indexing.IndexHandler;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirectoryChooser extends javax.swing.JFrame{

    public MultiSelectionTree tree;
    IndexHandler handler;

    public DirectoryChooser(IndexHandler ind){
        System.out.println("in constructor");
        handler = ind;
        FileSystemModel fileSystemDataModel = new FileSystemModel();
        tree = new MultiSelectionTree(fileSystemDataModel);
        System.out.println("before init components");
        initComponents();
        System.out.println("before create tree view");
        createTreeView();
    }

    //TODO: if directory is selected, then automatically select all children
    private void createTreeView() {
        //make font bigger for readability
        final Font currentFont = tree.getFont();
        tree.setFont(new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5));

        //allow multiple selection without pressing ctrl
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());
    }

    public void initComponents(){
        Container pane = getContentPane();
        JScrollPane scrollPane = new JScrollPane(tree);
        pane.add(scrollPane, BorderLayout.CENTER);
        JButton button = new JButton("bla");
        //TODO: figure out size
        button.setSize(new Dimension(200, 100));
        pane.add(button, BorderLayout.PAGE_END);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] paths = tree.getSelectionPaths();
                for(TreePath path : paths){
                    System.out.println("Path: " + path.getLastPathComponent().toString());
                    handler.addIncludedDirectory(path.getLastPathComponent().toString());
                }
                dispose();
            }
        });
    }
}
