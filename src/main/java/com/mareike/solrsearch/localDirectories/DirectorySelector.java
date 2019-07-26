package com.mareike.solrsearch.localDirectories;

import com.mareike.solrsearch.Main;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirectorySelector extends JFrame {

    private MultiSelectionTree tree;
    private String basePath;
    private ExecutorService executorService;

    public DirectorySelector(String basePath){
        Main.logger.info("prepare directory selection...");
        this.basePath = basePath;
        startThreads();

        final MyFile mf = new MyFile(new File(basePath));
        final FileSystemModel model = new FileSystemModel(mf);
        tree = new MultiSelectionTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setSelectionModel(new MLTreeSelectionModel());

        //increase default font size
        final Font currentFont = tree.getFont();
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5);
        tree.setFont(bigFont);
        Main.logger.info("Directory selection ready.");
    }
    
    public MultiSelectionTree getTree(){
        return tree;
    }

    public void startThreads(){executorService = Executors.newFixedThreadPool(4);}

    public ArrayList<String> listDirectories(){
        Main.logger.info("create list of all directories that have been indexed.");
        try {
           TreePath[] paths = tree.getSelectionPaths();
            ArrayList<String> fullPaths = new ArrayList<>();
            int endIndex = basePath.lastIndexOf(File.separator);
            String newBasePath = basePath.substring(0, endIndex + 1);
            for (TreePath path : paths) {
                String fullPath = newBasePath + buildPath(path);
                Main.logger.info("Path " + fullPath + "is added to list.");
                addDirectoryWatcher(fullPath);
                fullPaths.add(fullPath);
            }
            return fullPaths;
        } catch(NullPointerException ex){
           JLabel errorMessage = new JLabel();
           errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 24));
           errorMessage.setText("Please select at least one Directory for indexing.");
           JOptionPane.showMessageDialog(null, errorMessage);
           return null;
        }
    }

    public ArrayList<String> loadIndexedPaths(){
        ArrayList<String> directories = new ArrayList<>();
        String directoryPath;
        Main.logger.info("load previously indexed paths from file directories.txt");
        if(new File("directories.txt").exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("directories.txt"))) {
                while ((directoryPath = bufferedReader.readLine()) != null) {
                    directories.add(directoryPath);
                    addDirectoryWatcher(directoryPath);
                }
            } catch (Exception e) {
                Main.logger.info("Error when reading out directories. " + e.getMessage());
            }
        }
        return directories;
    }

    private String buildPath(TreePath path){
        Object[] pathElements = path.getPath();
        String fullPath = "";
        for (Object pathElement : pathElements){
            fullPath = fullPath + pathElement + File.separator ;
        }
        return fullPath;
    }

    private void addDirectoryWatcher(String path){
        Main.logger.info("start directory watcher on: " + path);
        try{
            executorService.submit(new DirectoryWatchService(Paths.get(path), true));
        }catch(IOException io){
            Main.logger.info("Error when adding directory watcher. " + io.getMessage());
        }
    }

    public void removeDirectoryWatchers(){
        Main.logger.info("remove directory watchers.");
        executorService.shutdownNow();
    }
}
