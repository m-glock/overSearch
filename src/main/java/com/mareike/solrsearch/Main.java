package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.UIExample;
import okhttp3.Request;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
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


        //TODO: get Sharepoint files
        /*MicrosoftConnector msc = new MicrosoftConnector();
        List<String> files = msc.getAllFiles();
        for(String st : files){
            System.out.println(st);
        }
        try {
            URL url = new URL("https://graph.microsoft.com/v1.0/groups/1c776fb9-0bc6-465c-a68d-2015fc852e34/sites/root/lists/Documents/items/1/DriveItem/content");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }*/



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



        //TODO: finish indexing function and clean up
        try{
            String path = "C:\\Users\\mareike\\Documents\\Studium\\8.Semester-SS19\\Bachelor\\english-words\\test files\\without query word";
            path = path.replace(" ", "_");

            URL url = new URL("http://localhost:7071/api/IndexFilesToSolr?name=" + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

        }catch(Exception prot){
            System.out.println("Protocol Exception: " + prot.getMessage());
        }






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