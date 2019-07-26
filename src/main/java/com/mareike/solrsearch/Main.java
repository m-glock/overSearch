package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.ErrorMessage;
import com.mareike.solrsearch.UI.UIHandler;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Main {

    public static java.util.logging.Logger logger;

    public static void main(String[] args){

        createLogger();
        logger.info("application started.\n");
        setLookAndFeel();

        String solrURL = "http://localhost:8983/solr";
        String collectionName = "overSearchFiles";
        String baseDirectory = System.getProperty("user.home");
        Boolean collectionExists = false;
        try {
            SolrInstance solr = new SolrInstance(solrURL, collectionName);
            java.util.List<String> collections = CollectionAdminRequest.listCollections(solr.client);
            if(collections.contains(collectionName)){
                logger.info("Collection already exists.");
                solr.changeSolrInstance();
                collectionExists = true;
            }
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            UIHandler ex = new UIHandler(solr, collectionName, collectionExists, baseDirectory);
            ex.setTitle("OverSearch");
            ex.setLocation(dim.width / 2 - ex.getSize().width / 2, dim.height / 2 - ex.getSize().height / 2);
            ex.setVisible(true);

        }catch(Exception e){
            String message = " Error in Main method. " + e.getMessage();
            new ErrorMessage(message);
            logger.info(message);
        }
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