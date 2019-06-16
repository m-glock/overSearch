package com.mareike.solrsearch.DirectoryChooser;

import com.mareike.solrsearch.Indexing.IndexHandler;
import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;

public class DirectoryChooser extends JFrame {

    private IndexHandler handler;

    public DirectoryChooser(IndexHandler h){
        handler = h;

        final File file = new File("C:\\Users\\mareike\\Documents\\Studium");
        final MyFile mf = new MyFile(file);
        final FileSystemModel model = new FileSystemModel(mf);

        final MultiSelectionTree tree = new MultiSelectionTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());

        add(new JScrollPane(tree));
    }
}
