package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.Environment;
import com.mareike.solrsearch.UI.ErrorMessage;
import com.mareike.solrsearch.UI.UIHandler;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import java.awt.*;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Main {

    public static java.util.logging.Logger logger;
    private static String startDirectory;
    private static Dimension dim;
    private static String collectionName;
    private static SolrInstance solr;
    private static boolean collectionExists;

    public static void main(String[] args){
        createLogger();
        setLookAndFeel();

        String solrURL = "http://localhost:8983/solr";
        collectionName = "overSearchFiles";
        startDirectory = System.getProperty("user.dir") + File.separator + "test_files";
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        collectionExists = false;

        try {
            solr = new SolrInstance(solrURL, collectionName);
            java.util.List<String> collections = CollectionAdminRequest.listCollections(solr.client);
            if(collections.contains(collectionName)){
                logger.info("Collection already exists.");
                solr.changeSolrInstance();
                collectionExists = true;
                startApplcation();
            }else{
                Environment env = new Environment();
                env.setLocation(dim.width / 2 - env.getSize().width / 2, dim.height / 2 - env.getSize().height / 2);
                env.setVisible(true);
            }
        }catch(Exception e){
            String message = " Error in Main method. " + e.getMessage();
            new ErrorMessage(message);
            logger.info(message);
        }

    }

    public static void startApplcation(){
        logger.info("Start application\n");
        UIHandler ex = new UIHandler(solr, collectionName, collectionExists, startDirectory);
        ex.setTitle("OverSearch");
        ex.setLocation(dim.width / 2 - ex.getSize().width / 2, dim.height / 2 - ex.getSize().height / 2);
        ex.setVisible(true);
    }

    private static void createLogger(){
        logger = java.util.logging.Logger.getLogger("log");
        FileHandler fh;

        try {
            fh = new FileHandler(System.getProperty("user.dir") + "/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Staring log.");

        } catch (Exception e) {
            logger.info("Error when creating logger: " + e.getMessage());
        }
    }

    public static void setStartDirectory(String start){
        startDirectory = start;
    }

    private static void setLookAndFeel(){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UIHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }
}