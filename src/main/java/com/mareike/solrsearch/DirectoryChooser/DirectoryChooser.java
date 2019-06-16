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

    public DirectoryChooser(IndexHandler h){
        handler = h;
        final MyFile mf = new MyFile(new File("C:\\Users\\mareike\\Documents\\Studium"));
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
