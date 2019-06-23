package com.mareike.solrsearch;

import com.mareike.solrsearch.UI.UIExample;
import com.mareike.solrsearch.UI.UIHandler;

import java.awt.Color;

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
            java.util.logging.Logger.getLogger(UIExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }
}