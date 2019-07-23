package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.Indexer;
import com.mareike.solrsearch.Queries.SearchResultBuilder;
import com.mareike.solrsearch.localDirectories.DirectorySelector;
import com.mareike.solrsearch.localDirectories.MultiSelectionTree;
import com.mareike.solrsearch.Queries.QueryHandler;
import com.mareike.solrsearch.SolrInstance;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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
    private ArrayList<String> directoryPaths;

    /**
     * Creates new form UIHandler
     */
    public UIHandler(SolrInstance inst, String collectionName, boolean collectionExists, String baseDirectory) {
        solr = inst;
        DirectorySelector dir = new DirectorySelector(baseDirectory);
        qHandler = new QueryHandler();
        MultiSelectionTree tree = dir.getTree();
        initComponents(tree);

        if(collectionExists) {
            directoryPaths = dir.loadIndexedPaths();
            Indexer.setCollectionName(collectionName);
            CardLayout card = (CardLayout) (mainPanel.getLayout());
            card.show(mainPanel, "mainScreen");
        }

        setEditorPane();
        addActionListeners(dir);
        closeFrameActionListener();
    }

    private void closeFrameActionListener(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(directoryPaths != null && !directoryPaths.isEmpty()) {
                    String fileName = "directories.txt";
                    try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName))) {
                        for (String path : directoryPaths)
                            pw.println(path);
                    } catch (FileNotFoundException ex) {
                        System.out.println("Error when trying to save directory paths in a file.");
                    }
                    System.out.println("Frame closed");
                }
            }
        });
    }


    private void setEditorPane(){
        editorPaneResults.setEditorKit(javax.swing.JEditorPane.createEditorKitForContentType("text/html"));
        editorPaneResults.setEditable(false);

        HTMLEditorKit kit = (HTMLEditorKit)editorPaneResults.getEditorKit();
        StyleSheet ss = kit.getStyleSheet();
        ss.addRule("h2 a {font-size: 18pt; font-weight: bold; color: teal;}");
        ss.addRule("p { margin-bottom: 40px;}");
        ss.addRule("p, div{font-size: 16pt;}");
        ss.addRule("p em {font-style: normal; color: #0099CC; font-weight: bold;}");

        kit.setStyleSheet(ss);

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
        mainScreen = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        searchBarPanel = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        search = new javax.swing.JButton();
        filter = new javax.swing.JButton();
        newCollection = new javax.swing.JButton();
        scrollPaneResults = new javax.swing.JScrollPane();
        editorPaneResults = new javax.swing.JEditorPane();

        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1200, 900));
        setPreferredSize(new java.awt.Dimension(1080, 720));

        mainPanel.setLayout(new java.awt.CardLayout());

        startScreen.setBackground(new java.awt.Color(255, 255, 255));
        startScreen.setLayout(new javax.swing.BoxLayout(startScreen, javax.swing.BoxLayout.Y_AXIS));

        startWelcomeLabel.setBackground(new java.awt.Color(255, 255, 255));
        startWelcomeLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        startWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startWelcomeLabel.setText("Welcome to overSearch! Please select all directories you want to include in your search.");
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

        indexButton.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        indexButton.setText("index files");
        indexButton.setAlignmentX(0.5F);

        javax.swing.GroupLayout startBottomPanelLayout = new javax.swing.GroupLayout(startBottomPanel);
        startBottomPanel.setLayout(startBottomPanelLayout);
        startBottomPanelLayout.setHorizontalGroup(
            startBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startBottomPanelLayout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(indexButton)
                .addContainerGap(324, Short.MAX_VALUE))
        );
        startBottomPanelLayout.setVerticalGroup(
            startBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startBottomPanelLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(indexButton)
                .addGap(33, 33, 33))
        );

        startScreen.add(startBottomPanel);

        mainPanel.add(startScreen, "startScreen");

        mainScreen.setLayout(new javax.swing.BoxLayout(mainScreen, javax.swing.BoxLayout.Y_AXIS));

        Header.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Header.setMaximumSize(new java.awt.Dimension(1080, 90));
        Header.setMinimumSize(new java.awt.Dimension(900, 90));
        Header.setPreferredSize(new java.awt.Dimension(1080, 90));
        Header.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 3));

        searchBarPanel.setMinimumSize(new java.awt.Dimension(1080, 40));
        searchBarPanel.setPreferredSize(new java.awt.Dimension(1080, 40));

        searchBar.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        searchBar.setText("");
        searchBar.setMaximumSize(new java.awt.Dimension(700, 40));
        searchBar.setMinimumSize(new java.awt.Dimension(700, 40));
        searchBar.setPreferredSize(new java.awt.Dimension(700, 40));

        javax.swing.GroupLayout searchBarPanelLayout = new javax.swing.GroupLayout(searchBarPanel);
        searchBarPanel.setLayout(searchBarPanelLayout);
        searchBarPanelLayout.setHorizontalGroup(
            searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
            .addGroup(searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(searchBarPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        searchBarPanelLayout.setVerticalGroup(
            searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(searchBarPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Header.add(searchBarPanel);

        buttonPanel.setMinimumSize(new java.awt.Dimension(1080, 40));
        buttonPanel.setPreferredSize(new java.awt.Dimension(700, 40));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        search.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        search.setText("Search");
        buttonPanel.add(search);

        filter.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        filter.setText("filter");

        buttonPanel.add(filter);

        newCollection.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        newCollection.setText("Index again");
        newCollection.setActionCommand("");
        buttonPanel.add(newCollection);

        Header.add(buttonPanel);

        mainScreen.add(Header);

        scrollPaneResults.setViewportView(editorPaneResults);

        mainScreen.add(scrollPaneResults);

        mainPanel.add(mainScreen, "mainScreen");

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

    //executed when user hits enter in the search bar or when the search button is clicked
    private void executeSearch() {
        //perform query on input string
        String queryWords = searchBar.getText();
        if(queryWords != null && !queryWords.equals("") && solr.isConnected()) {
            QueryResponse queryResponse = qHandler.sendQuery(solr.client, queryWords);
            String response = SearchResultBuilder.getHTMLForResults(queryResponse);

            editorPaneResults.setText(response);
        }
    }


    
    private void addActionListeners(final DirectorySelector dir){

        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code to save paths in index handler and close frame
                try {
                    directoryPaths = dir.listDirectories();
                    solr.createCollection();
                    //Indexes all files from the paths as well as the SharePoint files
                    Indexer.indexFiles(directoryPaths, solr.getCollectionName());
                    CardLayout card = (CardLayout) (mainPanel.getLayout());
                    card.show(mainPanel, "mainScreen");
                }catch(NullPointerException ex){
                    JLabel errorMessage = new JLabel();
                    errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 24));
                    errorMessage.setText("Please select at least one Directory for indexing.");
                    JOptionPane.showMessageDialog(null, errorMessage);
                }catch(IOException ex){//TODO: exception handling
                    System.out.println("IOException: " + ex.getMessage());
                }catch(SolrServerException ex){
                    System.out.println("SolrServerException: " + ex.getMessage());
                }
            }
        });

        ActionListener searcher = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSearch();
            }
        };

        search.addActionListener(searcher);
        searchBar.addActionListener(searcher);

        newCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(solr.isConnected()) {
                    try {
                        solr.deleteCollection();
                        CardLayout card = (CardLayout) (mainPanel.getLayout());
                        card.show(mainPanel, "startScreen");
                        dir.removeDirectoryWatchers();
                        dir.startThreads();
                    } catch (Exception ex) {
                        //TODO: exception handling
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterFrame frame = new FilterFrame(solr, qHandler);
                frame.getContentPane().setBackground(Color.white);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
                frame.setVisible(true);
            }
        });

        editorPaneResults.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e){
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED && Desktop.isDesktopSupported()) {
                    try {
                        System.out.println("url: " + e.getURL().toString());
                        URI uri = e.getURL().toURI();
                        //TODO: exception handling
                        if(uri.toString().contains("https")){
                            System.out.println("URI " + uri + "is a sharepoint uri.");
                            Desktop.getDesktop().browse(uri);
                        }else{
                            System.out.println("before create file");
                            File myFile = new File(uri.toString().replace("file:","").replace("%20"," "));
                            System.out.println("after create file. path is: " + myFile.getAbsolutePath());
                            Desktop.getDesktop().open(myFile);
                        }
                    } catch (URISyntaxException ex) {
                        System.out.println("Message uri: " + ex.getMessage());
                    } catch(IOException io){
                        System.out.println("Message io: " + io.getMessage());
                    }
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton filter;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel directoryPanel;
    private javax.swing.JScrollPane directoryScrollPane;
    private javax.swing.JEditorPane editorPaneResults;
    private javax.swing.JButton indexButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainScreen;
    private javax.swing.JButton newCollection;
    private javax.swing.JScrollPane scrollPaneResults;
    private javax.swing.JButton search;
    private javax.swing.JTextField searchBar;
    private javax.swing.JPanel searchBarPanel;
    private javax.swing.JPanel startBottomPanel;
    private javax.swing.JPanel startScreen;
    private javax.swing.JLabel startWelcomeLabel;
    // End of variables declaration//GEN-END:variables
}
