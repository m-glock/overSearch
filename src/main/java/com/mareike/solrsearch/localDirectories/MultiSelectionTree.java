package com.mareike.solrsearch.localDirectories;

import com.mareike.solrsearch.Main;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class MultiSelectionTree extends JTree {

    public MultiSelectionTree(FileSystemModel model){

        super(model);
    }

    @Override
    public void setSelectionPath(TreePath path) {

        Main.logger.info("Path " + buildPath(path) + " has been selected.");
        TreePath[] alreadySelectedPaths = getSelectionModel().getSelectionPaths();
        String pathToAdd = buildPath(path);
        for(TreePath tPath : alreadySelectedPaths){
            String alreadySelected = buildPath(tPath);
            if(pathToAdd.contains(alreadySelected) && !pathToAdd.equals(alreadySelected)){
                Main.logger.info("Selected path is an ancestor of another already selected path");
                JOptionPane.showMessageDialog(null, "This directory is already being indexed together with its parent directory " + buildPath(tPath));
                return;
            }else if(alreadySelected.contains(pathToAdd)&& !alreadySelected.equals(pathToAdd)){
                removeSelectionPath(tPath);
                Main.logger.info("removed " + buildPath(tPath) + " from list of selected paths because it is an ancestor of " + buildPath(path));
            }
        }
        addSelectionPath(path);
        Main.logger.info(buildPath(path) + " has been added to the selection");

        return;
    }

    @Override
    public void setSelectionPaths(TreePath[] paths) {

        Main.logger.info("MLDebugJTree: setSelectionPaths(" + paths + ")");

        addSelectionPaths(paths);

        return;
    }

    @Override
    public void setSelectionRow(int row) {

        Main.logger.info("MLDebugJTree: setSelectionRow(" + row + ")");

        addSelectionRow(row);

        return;
        //super.setSelectionRow(row);
    }

    @Override
    public void setSelectionRows(int[] rows) {

        Main.logger.info("MLDebugJTree: setSelectionRows(" + rows + ")");

        addSelectionRows(rows);

        return;
        //super.setSelectionRows(rows);
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
