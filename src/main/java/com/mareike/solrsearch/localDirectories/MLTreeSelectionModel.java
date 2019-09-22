package com.mareike.solrsearch.localDirectories;

import com.mareike.solrsearch.Main;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

public class MLTreeSelectionModel extends DefaultTreeSelectionModel {

    @Override
    public void addSelectionPath(TreePath path) {
        // Don't do overriding logic here because addSelectionPaths is ultimately called.
        super.addSelectionPath(path);
    }

    @Override
    public void addSelectionPaths(TreePath[] paths) {
        if (paths != null) {
            for (TreePath path : paths) {

                TreePath[] toAdd = new TreePath[1];
                toAdd[0] = path;

                if (isPathSelected(path)) {
                    // If path has been previously selected REMOVE THE SELECTION.
                    super.removeSelectionPaths(toAdd);
                    Main.logger.info("Path has been removed from the selection");
                } else {
                    // Else we really want to add the selection...
                    super.addSelectionPaths(toAdd);
                    Main.logger.info("Path has been added to the selection");
                }
            }
        }
    }
}
