package com.mareike.solrsearch;

import com.mareike.solrsearch.Indexing.FileSystemModel;
import com.mareike.solrsearch.Indexing.WatchDirectory;
import com.mareike.solrsearch.UI.UIExample;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
//import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    
    public static void main(String[] args){

        //TODO: add this to QueryHandler
        /*
        Query query = new Query();
        //happens later from UI
        query.addParameter(ParameterType.QUERY, "singleton");
        query.addParameter(ParameterType.FIELDlIST, "id,start");

        SolrDocumentList results = query.sendQuery(solr);*/


        //TODO: get Sharepoint files
        /*MicrosoftConnector msc = new MicrosoftConnector();
        List<String> files = msc.getAllFiles();*/




        //TODO: UI handling
        /*setLookAndFeel();


        //Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIExample ex = new UIExample();
                ex.setVisible(true);
                System.out.println("In runnable");
            }
        });*/


        //TODO: finish up directory watcher
        /*try{
            Thread t = new Thread(new WatchDirectory(Paths.get("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2"), true));
            t.start();
         }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
         }*/

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