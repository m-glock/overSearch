package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.Indexer;
import com.mareike.solrsearch.Queries.SearchResultBuilder;
import com.mareike.solrsearch.localDirectories.DirectoryChooser;
import com.mareike.solrsearch.localDirectories.MultiSelectionTree;
import com.mareike.solrsearch.ParameterType;
import com.mareike.solrsearch.Queries.QueryHandler;
import com.mareike.solrsearch.SolrInstance;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

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
public class UIHandler extends javax.swing.JFrame{


    private SolrInstance solr;
    private QueryHandler qHandler;

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
        //TODO: let user choose start directory?
        DirectoryChooser dir = new DirectoryChooser("C:/Users/mareike/Documents/Studium");
        qHandler = new QueryHandler();
        MultiSelectionTree tree = dir.getTree();
        initComponents(tree);
        setEditorPane();
        addActionListeners(dir);
    }


    private void setEditorPane(){
        editorPaneResults.setEditorKit(javax.swing.JEditorPane.createEditorKitForContentType("text/html"));
        editorPaneResults.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(MultiSelectionTree tree) {

        mainPanel = new javax.swing.JPanel();
        startScreen = new javax.swing.JPanel();
        startWelcomeLabel = new javax.swing.JLabel();
        directoryPanel = new javax.swing.JPanel();
        directoryScrollPane = new javax.swing.JScrollPane(tree);
        startBottomPanel = new javax.swing.JPanel();
        indexButton = new javax.swing.JButton();
        searchScreen = new javax.swing.JPanel();
        mainScreen = new javax.swing.JPanel();
        searchBarPanel = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        Filter = new javax.swing.JButton();
        scrollPaneResults = new javax.swing.JScrollPane();
        editorPaneResults = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(815, 600));

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
            .addGap(0, 815, Short.MAX_VALUE)
        );
        searchScreenLayout.setVerticalGroup(
            searchScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        mainPanel.add(searchScreen, "searchPanel");

        mainScreen.setLayout(new javax.swing.BoxLayout(mainScreen, javax.swing.BoxLayout.Y_AXIS));

        searchBarPanel.setMaximumSize(new java.awt.Dimension(800, 60));
        searchBarPanel.setMinimumSize(new java.awt.Dimension(800, 60));
        searchBarPanel.setPreferredSize(new java.awt.Dimension(800, 60));

        searchBar.setText("Search");
        searchBar.setMaximumSize(new java.awt.Dimension(700, 40));
        searchBar.setMinimumSize(new java.awt.Dimension(700, 40));
        searchBar.setPreferredSize(new java.awt.Dimension(700, 40));
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        searchBarPanel.add(searchBar);

        Filter.setText("Filter");
        Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterActionPerformed(evt);
            }
        });
        searchBarPanel.add(Filter);

        mainScreen.add(searchBarPanel);

        scrollPaneResults.setViewportView(editorPaneResults);

        mainScreen.add(scrollPaneResults);

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

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        //perform query on input string
        String response = "";
        String queryWord = searchBar.getText();
        //TODO: what happens if query word has weird characters
        //TODO: show all results? or somehow only load more when user is scrolling?
        qHandler.addParameter(ParameterType.QUERY, queryWord);
        SolrDocumentList results = qHandler.sendQuery(solr.client);
        System.out.println("results received");
        response = SearchResultBuilder.getHTMLForResults(results);

        editorPaneResults.setText(response);
    }//GEN-LAST:event_searchBarActionPerformed

    private void FilterActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_FilterActionPerformed
        // TODO: open file from link or folder?
        /*File file = new File("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2\\folder\\Mappe1.xlsx");
        try {
            System.out.println("file url: " + file.toURI().toURL());
            editorPaneResults.setText("<a href=\"" + file.toURI().toURL() + "\">this is suppose to be a file</a>");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        editorPaneResults.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e){
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    // Do something with e.getURL() here
                    if (Desktop.isDesktopSupported()) {
                        try {
                            System.out.println("URL: " + e.getURL().toURI().toString().replace("file:",""));
                            File myFile = new File(e.getURL().toURI().toString().replace("file:",""));
                            Desktop.getDesktop().open(myFile);
                        } catch (Exception ex) {
                            // no application registered for PDFs
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        });*/
    }//GEN-LAST:event_FilterActionPerformed

    
    private void addActionListeners(final DirectoryChooser dir){

        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code to save paths in index handler and close frame
                /*ArrayList<String> directoryPaths = dir.listDirectories();
                for(String path : directoryPaths){
                    System.out.println(path);
                }
                Indexer.indexFiles(directoryPaths);*/
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel, "mainPanel");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Filter;
    private javax.swing.JPanel directoryPanel;
    private javax.swing.JScrollPane directoryScrollPane;
    private javax.swing.JEditorPane editorPaneResults;
    private javax.swing.JButton indexButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainScreen;
    private javax.swing.JScrollPane scrollPaneResults;
    private javax.swing.JTextField searchBar;
    private javax.swing.JPanel searchBarPanel;
    private javax.swing.JPanel searchScreen;
    private javax.swing.JPanel startBottomPanel;
    private javax.swing.JPanel startScreen;
    private javax.swing.JLabel startWelcomeLabel;
    // End of variables declaration//GEN-END:variables
}
