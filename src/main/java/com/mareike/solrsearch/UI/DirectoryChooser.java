package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.Indexing.FileSystemModel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;

public class DirectoryChooser extends javax.swing.JFrame{

    public final MultiSelectionTree tree;

    public DirectoryChooser(){
        FileSystemModel fileSystemDataModel = new FileSystemModel();
        tree = new MultiSelectionTree(fileSystemDataModel);
        //tree = new JTree();
        createTreeView();
    }


    private void createTreeView() {
        //make font bigger for readability
        final Font currentFont = tree.getFont();
        tree.setFont(new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5));

        //allow multiple selection without pressing ctrl
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());

        /*tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                File node = (File) tree.getLastSelectedPathComponent();
                if (node == null) return;
                System.out.println("change detected: " + node.toString());

            }
        });*/

        //int[] rows = {1, 3};
        //tree.setSelectionRows(rows);
    }
}
