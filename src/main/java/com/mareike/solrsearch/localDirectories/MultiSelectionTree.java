package com.mareike.solrsearch.localDirectories;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class MultiSelectionTree extends JTree {

    public MultiSelectionTree(FileSystemModel model){

        super(model);
    }

    @Override
    public void setSelectionPath(TreePath path) {

        System.out.println("MLDebugJTree: setSelectionPath(" + path + ")");
        TreePath[] alreadySelectedPaths = getSelectionModel().getSelectionPaths();
        String pathToAdd = buildPath(path);
        for(TreePath tPath : alreadySelectedPaths){
            String alreadySelected = buildPath(tPath);
            if(pathToAdd.contains(alreadySelected) && !pathToAdd.equals(alreadySelected)){
                System.out.println("in if query");
                JOptionPane.showMessageDialog(null, "This directory is already being indexed together with its parent directory " + buildPath(tPath));
                return;
            }else if(alreadySelected.contains(pathToAdd)&& !alreadySelected.equals(pathToAdd)){
                removeSelectionPath(tPath);
                System.out.println("removed " + buildPath(tPath) + " from list of selected paths");
            }
        }
        addSelectionPath(path);
        System.out.println(path + " added");

        return;
        //super.setSelectionPath(path);
    }

    private String buildPath(TreePath path){
        Object[] pathElements = path.getPath();
        String fullPath = "";
        for (Object pathElement : pathElements){
            fullPath = fullPath + pathElement + "/" ;
        }
        return fullPath;
    }

    @Override
    public void setSelectionPaths(TreePath[] paths) {

        System.out.println("MLDebugJTree: setSelectionPaths(" + paths + ")");

        addSelectionPaths(paths);

        return;
    }

    @Override
    public void setSelectionRow(int row) {

        System.out.println("MLDebugJTree: setSelectionRow(" + row + ")");

        addSelectionRow(row);

        return;
        //super.setSelectionRow(row);
    }

    @Override
    public void setSelectionRows(int[] rows) {

        System.out.println("MLDebugJTree: setSelectionRows(" + rows + ")");

        addSelectionRows(rows);

        return;
        //super.setSelectionRows(rows);
    }
}
