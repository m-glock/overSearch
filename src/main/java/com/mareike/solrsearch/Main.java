package com.mareike.solrsearch;

import com.mareike.solrsearch.DirectoryChooser.DirectoryChooser;
import com.mareike.solrsearch.UI.UIExample;
import com.mareike.solrsearch.UI.UIHandler;
import java.awt.Color;
import okhttp3.Request;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.NamedList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//import java.awt.*;


public class Main {
    
    public static void main(String[] args){

        //TODO: add this to QueryHandler
        /*
        Query query = new Query();
        //happens later from UI
        query.addParameter(ParameterType.QUERY, "singleton");
        query.addParameter(ParameterType.FIELDlIST, "id,start");

        SolrDocumentList results = query.sendQuery(solr);*/



        //TODO: UI handling
        setLookAndFeel();


        //Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIHandler ex = new UIHandler();
                ex.setVisible(true);
                ex.getContentPane().setBackground(Color.white);
                System.out.println("In runnable");
            }
        });

    }
    
    public static void setLookAndFeel(){
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }
}