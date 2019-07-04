package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.UIHandler;

import java.awt.*;

public class Main {
    
    public static void main(String[] args){

        setLookAndFeel();

        //TODO: what does invoke later do?
        //Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIHandler ex = new UIHandler();
                ex.setVisible(true);
                ex.getContentPane().setBackground(Color.white);
            }
        });


        /*try{
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            File file = new File("C:\\Users\\mareike\\Documents\\Studium\\8.Semester-SS19\\Bachelor\\english-words\\test files\\with query word\\cump.txt");
            InputStream content = new FileInputStream(file);
            parser.parse(content, handler, metadata, new ParseContext());
            for (String name: metadata.names()) {
                System.out.println(name + ": " + metadata.get(name));
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }*/


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