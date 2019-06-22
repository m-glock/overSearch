package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.DirectoryChooser.DirectoryChooser;
import com.mareike.solrsearch.DirectoryChooser.MultiSelectionTree;
import com.mareike.solrsearch.Indexer;
import com.mareike.solrsearch.SolrInstance;
import org.apache.solr.client.solrj.SolrServerException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mareike
 */
public class UIHandler extends javax.swing.JFrame {


    private SolrInstance solr;
    /**
     * Creates new form UIHandler
     */
    public UIHandler() {

        try {
            solr = new SolrInstance("http://localhost:8983/solr", "localDocs4");
        }catch(IOException io){
            System.out.println("IOException: " + io.getMessage());
        }catch(SolrServerException serv){
            System.out.println("SolrServerException: " + serv.getMessage());
        }catch(Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        DirectoryChooser dir = new DirectoryChooser(solr.getIndexer(), "C:/Users/mareike/Documents/Studium");
        MultiSelectionTree tree = dir.getTree();
        initComponents();
        addActionListeners(solr.getIndexer(), dir);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        startScreen = new javax.swing.JPanel();
        startWelcomeLabel = new javax.swing.JLabel();
        directoryPanel = new javax.swing.JPanel();
        directoryScrollPane = new javax.swing.JScrollPane();
        startBottomPanel = new javax.swing.JPanel();
        indexButton = new javax.swing.JButton();
        searchScreen = new javax.swing.JPanel();
        mainScreen = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1000, 700));

        mainPanel.setLayout(new java.awt.CardLayout());

        startScreen.setBackground(new java.awt.Color(255, 255, 255));
        startScreen.setLayout(new javax.swing.BoxLayout(startScreen, javax.swing.BoxLayout.Y_AXIS));

        startWelcomeLabel.setBackground(new java.awt.Color(255, 255, 255));
        startWelcomeLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        startWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startWelcomeLabel.setText("Welcome to overSearch! Please choose the directories you want to index for the search");
        startWelcomeLabel.setAlignmentX(0.5F);
        startWelcomeLabel.setPreferredSize(new java.awt.Dimension(800, 100));
        startScreen.add(startWelcomeLabel);

        directoryPanel.setLayout(new javax.swing.BoxLayout(directoryPanel, javax.swing.BoxLayout.LINE_AXIS));

        directoryScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        directoryScrollPane.setMaximumSize(new java.awt.Dimension(600, 32767));
        directoryScrollPane.setPreferredSize(new java.awt.Dimension(600, 2));
        directoryPanel.add(directoryScrollPane);

        startScreen.add(directoryPanel);

        startBottomPanel.setBackground(new java.awt.Color(255, 255, 255));
        startBottomPanel.setMaximumSize(new java.awt.Dimension(800, 100));
        startBottomPanel.setMinimumSize(new java.awt.Dimension(800, 100));
        startBottomPanel.setPreferredSize(new java.awt.Dimension(800, 100));

        indexButton.setText("index files");
        indexButton.setAlignmentX(0.5F);

        javax.swing.GroupLayout startBottomPanelLayout = new javax.swing.GroupLayout(startBottomPanel);
        startBottomPanel.setLayout(startBottomPanelLayout);
        startBottomPanelLayout.setHorizontalGroup(
            startBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startBottomPanelLayout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(indexButton)
                .addContainerGap(344, Short.MAX_VALUE))
        );
        startBottomPanelLayout.setVerticalGroup(
            startBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startBottomPanelLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(indexButton)
                .addGap(33, 33, 33))
        );

        startScreen.add(startBottomPanel);

        mainPanel.add(startScreen, "startPanel");

        javax.swing.GroupLayout searchScreenLayout = new javax.swing.GroupLayout(searchScreen);
        searchScreen.setLayout(searchScreenLayout);
        searchScreenLayout.setHorizontalGroup(
            searchScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        searchScreenLayout.setVerticalGroup(
            searchScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        mainPanel.add(searchScreen, "searchPanel");

        javax.swing.GroupLayout mainScreenLayout = new javax.swing.GroupLayout(mainScreen);
        mainScreen.setLayout(mainScreenLayout);
        mainScreenLayout.setHorizontalGroup(
            mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        mainScreenLayout.setVerticalGroup(
            mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        mainPanel.add(mainScreen, "mainPanel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void addActionListeners(final Indexer indexer, final DirectoryChooser dir){
        
        
        /*toSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked");
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel, "searchScreen");
            }
        });
        
        toStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked");
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel, "startScreen");
            }
        });*/

        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code to save paths in index handler and close frame
                /*ArrayList<String> directoryPaths = dir.listDirectories();
                for(String path : directoryPaths){
                    System.out.println(path);
                }*/
                //indexer.indexFiles(directoryPaths);
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel, "mainPanel");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel directoryPanel;
    private javax.swing.JScrollPane directoryScrollPane;
    private javax.swing.JButton indexButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainScreen;
    private javax.swing.JPanel searchScreen;
    private javax.swing.JPanel startBottomPanel;
    private javax.swing.JPanel startScreen;
    private javax.swing.JLabel startWelcomeLabel;
    // End of variables declaration//GEN-END:variables
}
