package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.UIHandler;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;

import java.awt.*;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args){

        setLookAndFeel();

        String solrURL = "http://localhost:8983/solr";
        String collectionName = "overSearchFiles";
        String baseDirectory = System.getProperty("user.home");
        Boolean collectionExists = false;
        try {
            SolrInstance solr = new SolrInstance(solrURL, collectionName);
            java.util.List<String> collections = CollectionAdminRequest.listCollections(solr.client);
            if(collections.contains(collectionName)){
                solr.changeSolrInstance();
                collectionExists = true;
            }

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            UIHandler ex = new UIHandler(solr, collectionName, collectionExists, baseDirectory);
            ex.setTitle("OverSearch");
            ex.setLocation(dim.width / 2 - ex.getSize().width / 2, dim.height / 2 - ex.getSize().height / 2);
            ex.setVisible(true);

            //TODO: exception handling
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }catch(SolrServerException serv){
            System.out.println("SolrServerException: " + serv.getMessage());
        }catch(HttpSolrClient.RemoteSolrException ex){
            System.out.println("RemoteSolrException with message: " + ex.getMessage());
        }catch(Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
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