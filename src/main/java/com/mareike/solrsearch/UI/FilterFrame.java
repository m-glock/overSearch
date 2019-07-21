package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.ContentTypes;
import com.mareike.solrsearch.Queries.Filter;
import com.mareike.solrsearch.Queries.QueryHandler;
import com.mareike.solrsearch.SolrInstance;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class FilterFrame extends javax.swing.JFrame {

    private ButtonGroup buttonGroupSort;
    private Box.Filler filler1, filler2, filler3, filler4, filler5, filler6, filler7;
    private JPanel preferencePanel, filterPanel, creatorFilterPanel, preferencesContentPane, creatorPreferencesPanel, datePreferencesPanel, formatPreferencesPanel, filterContentPane,
            dateFilterPanel, fileFilterPanel, sortPanel, filterContentPane1, footer;
    private JLabel sortLabel, preferencesLabel, filterLabel;
    private JCheckBox creatorPreferencesCheckBox, datePreferencesCheckBox, formatPreferencesCheckBox, creatorFilterCheckBox, dateFilterCheckBox, formatFilterCheckBox;
    private JComboBox creatorPreferencesComboBox, datePreferencesComboBox, formatPreferencesComboBox, creatorFilterComboBox, dateFilterComboBox, formatFilterComboBox;
    private JRadioButton relevanceRadioButton, creationDateRadioButton;
    private JButton finishButton;
    private JSeparator jSeparator1, jSeparator2;
    private javax.swing.DefaultComboBoxModel<String> creatorFilterComboBoxModel, creatorPreferenceComboBoxModel, dateFilterComboBoxModel, datePreferenceComboBoxModel,
            formatFilterComboBoxModel, formatPreferenceComboBoxModel;
    private QueryHandler qHandler;
    private HashMap<Filter, String> filters;
    private HashMap<Filter, Component[]> filterAndPreferenceComponents;
    private HashMap<JCheckBox, JComboBox> preferenceComponents;

    /**
     * Creates new form NewJFrame
     */
    public FilterFrame(SolrInstance solr, QueryHandler qHandler) {
        this.qHandler = qHandler;
        filters = new HashMap<>();
        if(solr.isConnected()) {
            setBoxModels(solr);
        }
        initComponents();
        groupComponents();
        createActionListeners();
        setInitialStates();
        if(!qHandler.getFilters().isEmpty()){
            setPreviousStates(qHandler.getFilters());
        }
    }

    private void groupComponents(){
        filterAndPreferenceComponents = new HashMap<>();
        filterAndPreferenceComponents.put(Filter.CREATORFILTER, new Component[] {creatorFilterCheckBox, creatorFilterComboBox});
        filterAndPreferenceComponents.put(Filter.CREATORPREFERENECE, new Component[] {creatorPreferencesCheckBox, creatorPreferencesComboBox});
        filterAndPreferenceComponents.put(Filter.DATEFILTER, new Component[] {dateFilterCheckBox, dateFilterComboBox});
        filterAndPreferenceComponents.put(Filter.DATEPREFERENECE, new Component[] {datePreferencesCheckBox, datePreferencesComboBox});
        filterAndPreferenceComponents.put(Filter.FORMATFILTER, new Component[] {formatFilterCheckBox, formatFilterComboBox});
        filterAndPreferenceComponents.put(Filter.FORMATPREFERENECE, new Component[] {formatPreferencesCheckBox, formatPreferencesComboBox});
        filterAndPreferenceComponents.put(Filter.SORTRELEVANCE, new Component[] {relevanceRadioButton, creationDateRadioButton});

        preferenceComponents = new HashMap<>();
        preferenceComponents.put(creatorPreferencesCheckBox, creatorPreferencesComboBox);
        preferenceComponents.put(datePreferencesCheckBox, datePreferencesComboBox);
        preferenceComponents.put(formatPreferencesCheckBox, formatPreferencesComboBox);
    }

    private void setInitialStates(){
        for(Component[] c : filterAndPreferenceComponents.values()){
            c[1].setEnabled(false);
        }
        creationDateRadioButton.setEnabled(true);
        relevanceRadioButton.setSelected(true);
        addFilter(Filter.SORTRELEVANCE, "true");
    }

    private void setPreviousStates(HashMap<Filter, String> previousFilters){
        for(Filter filter : previousFilters.keySet()){
            if(previousFilters.get(filter).equals("creation date")){
                creationDateRadioButton.setSelected(true);
                //Disable all preference check boxes and dropdowns
                for(JCheckBox c : preferenceComponents.keySet()){
                    c.setEnabled(false);
                    preferenceComponents.get(c).setEnabled(false);
                }
            }else if(!filter.type.equals("sort")){
                setComponentState(filter);
            }
        }
    }

    private void setComponentState(Filter filter){
        ((JCheckBox) filterAndPreferenceComponents.get(filter)[0]).setSelected(true);
        filterAndPreferenceComponents.get(filter)[1].setEnabled(true);
        ((JComboBox) filterAndPreferenceComponents.get(filter)[1]).setSelectedItem(filter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        buttonGroupSort = new javax.swing.ButtonGroup();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        preferencePanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        preferencesLabel = new javax.swing.JLabel();
        preferencesContentPane = new javax.swing.JPanel();
        creatorPreferencesPanel = new javax.swing.JPanel();
        creatorPreferencesCheckBox = new javax.swing.JCheckBox();
        creatorPreferencesComboBox = new javax.swing.JComboBox<>();
        datePreferencesPanel = new javax.swing.JPanel();
        datePreferencesCheckBox = new javax.swing.JCheckBox();
        datePreferencesComboBox = new javax.swing.JComboBox<>();
        formatPreferencesPanel = new javax.swing.JPanel();
        formatPreferencesCheckBox = new javax.swing.JCheckBox();
        formatPreferencesComboBox = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        filterPanel = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        filterLabel = new javax.swing.JLabel();
        filterContentPane = new javax.swing.JPanel();
        creatorFilterPanel = new javax.swing.JPanel();
        creatorFilterCheckBox = new javax.swing.JCheckBox();
        creatorFilterComboBox = new javax.swing.JComboBox<>();
        dateFilterPanel = new javax.swing.JPanel();
        dateFilterCheckBox = new javax.swing.JCheckBox();
        dateFilterComboBox = new javax.swing.JComboBox<>();
        fileFilterPanel = new javax.swing.JPanel();
        formatFilterCheckBox = new javax.swing.JCheckBox();
        formatFilterComboBox = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        sortPanel = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        sortLabel = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0));
        filterContentPane1 = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        relevanceRadioButton = new javax.swing.JRadioButton();
        creationDateRadioButton = new javax.swing.JRadioButton();
        footer = new javax.swing.JPanel();
        finishButton = new javax.swing.JButton();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        filler2.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(filler2);

        preferencePanel.setBackground(new java.awt.Color(255, 255, 255));
        preferencePanel.setMinimumSize(new java.awt.Dimension(104, 100));
        preferencePanel.setPreferredSize(new java.awt.Dimension(1296, 100));
        preferencePanel.setLayout(new javax.swing.BoxLayout(preferencePanel, javax.swing.BoxLayout.LINE_AXIS));
        preferencePanel.add(filler4);

        preferencesLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        preferencesLabel.setText("preferences");
        preferencesLabel.setMaximumSize(new java.awt.Dimension(104, 249));
        preferencesLabel.setPreferredSize(new java.awt.Dimension(150, 249));
        preferencePanel.add(preferencesLabel);

        preferencesContentPane.setBackground(new java.awt.Color(255, 255, 255));
        preferencesContentPane.setPreferredSize(new java.awt.Dimension(500, 200));
        preferencesContentPane.setLayout(new java.awt.GridLayout(4, 1));

        creatorPreferencesPanel.setBackground(new java.awt.Color(255, 255, 255));
        creatorPreferencesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        creatorPreferencesCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        creatorPreferencesCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        creatorPreferencesCheckBox.setText("creator");
        creatorPreferencesCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        creatorPreferencesPanel.add(creatorPreferencesCheckBox);

        creatorPreferencesComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        creatorPreferencesComboBox.setModel(creatorPreferenceComboBoxModel);
        creatorPreferencesPanel.add(creatorPreferencesComboBox);

        preferencesContentPane.add(creatorPreferencesPanel);

        datePreferencesPanel.setBackground(new java.awt.Color(255, 255, 255));
        datePreferencesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        datePreferencesCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        datePreferencesCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        datePreferencesCheckBox.setText("creation date");
        datePreferencesCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        datePreferencesPanel.add(datePreferencesCheckBox);

        datePreferencesComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        datePreferencesComboBox.setModel(datePreferenceComboBoxModel);
        datePreferencesPanel.add(datePreferencesComboBox);

        preferencesContentPane.add(datePreferencesPanel);

        formatPreferencesPanel.setBackground(new java.awt.Color(255, 255, 255));
        formatPreferencesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        formatPreferencesCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        formatPreferencesCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        formatPreferencesCheckBox.setText("file format");
        formatPreferencesCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        formatPreferencesPanel.add(formatPreferencesCheckBox);

        formatPreferencesComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        formatPreferencesComboBox.setModel(formatPreferenceComboBoxModel);
        formatPreferencesPanel.add(formatPreferencesComboBox);

        preferencesContentPane.add(formatPreferencesPanel);

        preferencePanel.add(preferencesContentPane);

        getContentPane().add(preferencePanel);

        jSeparator1.setMaximumSize(new java.awt.Dimension(32767, 10));
        getContentPane().add(jSeparator1);

        filler1.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(filler1);

        filterPanel.setBackground(new java.awt.Color(255, 255, 255));
        filterPanel.setMinimumSize(new java.awt.Dimension(68, 100));
        filterPanel.setPreferredSize(new java.awt.Dimension(742, 100));
        filterPanel.setLayout(new javax.swing.BoxLayout(filterPanel, javax.swing.BoxLayout.LINE_AXIS));
        filterPanel.add(filler3);

        filterLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        filterLabel.setText("filter by");
        filterLabel.setToolTipText("");
        filterLabel.setPreferredSize(new java.awt.Dimension(150, 249));
        filterPanel.add(filterLabel);

        filterContentPane.setBackground(new java.awt.Color(255, 255, 255));
        filterContentPane.setPreferredSize(new java.awt.Dimension(500, 200));
        filterContentPane.setLayout(new java.awt.GridLayout(4, 1));

        creatorFilterPanel.setBackground(new java.awt.Color(255, 255, 255));
        creatorFilterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        creatorFilterCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        creatorFilterCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        creatorFilterCheckBox.setText("creator");
        creatorFilterCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        creatorFilterPanel.add(creatorFilterCheckBox);

        creatorFilterComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        creatorFilterComboBox.setModel(creatorFilterComboBoxModel);
        creatorFilterPanel.add(creatorFilterComboBox);

        filterContentPane.add(creatorFilterPanel);

        dateFilterPanel.setBackground(new java.awt.Color(255, 255, 255));
        dateFilterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        dateFilterCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        dateFilterCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dateFilterCheckBox.setText("creation date");
        dateFilterCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        dateFilterPanel.add(dateFilterCheckBox);

        dateFilterComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        dateFilterComboBox.setModel(dateFilterComboBoxModel);
        dateFilterPanel.add(dateFilterComboBox);

        filterContentPane.add(dateFilterPanel);

        fileFilterPanel.setBackground(new java.awt.Color(255, 255, 255));
        fileFilterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        formatFilterCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        formatFilterCheckBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        formatFilterCheckBox.setText("file format");
        formatFilterCheckBox.setPreferredSize(new java.awt.Dimension(180, 33));
        fileFilterPanel.add(formatFilterCheckBox);

        formatFilterComboBox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        formatFilterComboBox.setModel(formatFilterComboBoxModel);
        fileFilterPanel.add(formatFilterComboBox);

        filterContentPane.add(fileFilterPanel);

        filterPanel.add(filterContentPane);

        getContentPane().add(filterPanel);

        jSeparator2.setMaximumSize(new java.awt.Dimension(32767, 5));
        getContentPane().add(jSeparator2);

        sortPanel.setBackground(new java.awt.Color(255, 255, 255));
        sortPanel.setMinimumSize(new java.awt.Dimension(0, 50));
        sortPanel.setPreferredSize(new java.awt.Dimension(547, 50));
        sortPanel.setLayout(new javax.swing.BoxLayout(sortPanel, javax.swing.BoxLayout.LINE_AXIS));
        sortPanel.add(filler5);

        sortLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        sortLabel.setText("sort by");
        sortLabel.setMaximumSize(new java.awt.Dimension(150, 249));
        sortLabel.setPreferredSize(new java.awt.Dimension(150, 249));
        sortLabel.setVerifyInputWhenFocusTarget(false);
        sortLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        sortPanel.add(sortLabel);
        sortPanel.add(filler7);

        filterContentPane1.setBackground(new java.awt.Color(255, 255, 255));
        filterContentPane1.setPreferredSize(new java.awt.Dimension(500, 200));
        filterContentPane1.setLayout(new java.awt.GridLayout(4, 1));
        filterContentPane1.add(filler6);

        relevanceRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        relevanceRadioButton.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        relevanceRadioButton.setText("relevance");
        buttonGroupSort.add(relevanceRadioButton);
        filterContentPane1.add(relevanceRadioButton);

        creationDateRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        creationDateRadioButton.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        creationDateRadioButton.setText("creation date");
        buttonGroupSort.add(creationDateRadioButton);
        filterContentPane1.add(creationDateRadioButton);

        sortPanel.add(filterContentPane1);

        getContentPane().add(sortPanel);

        footer.setBackground(new java.awt.Color(255, 255, 255));
        footer.setMaximumSize(new java.awt.Dimension(32767, 50));
        footer.setPreferredSize(new java.awt.Dimension(821, 50));
        footer.setLayout(new java.awt.GridBagLayout());

        finishButton.setText("Apply");
        footer.add(finishButton, new java.awt.GridBagConstraints());

        getContentPane().add(footer);

        pack();
    }// </editor-fold>

    private void createActionListeners(){
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readAndSaveFilters();
                qHandler.setFilters(filters);
                FilterFrame.this.dispose();
            }
        });

        relevanceRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFilter(Filter.SORTRELEVANCE, "relevance");
                for(JCheckBox c : preferenceComponents.keySet()){
                    c.setEnabled(true);
                    if(c.isSelected())
                        preferenceComponents.get(c).setEnabled(true);
                }
            }
        });

        creationDateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFilter(Filter.SORTRELEVANCE, "creation date");
                for(JCheckBox c : preferenceComponents.keySet()){
                    c.setEnabled(false);
                    preferenceComponents.get(c).setEnabled(false);
                }
            }
        });

        creatorFilterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatorFilterComboBox.setEnabled(!creatorFilterComboBox.isEnabled());
            }
        });

        dateFilterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFilterComboBox.setEnabled(!dateFilterComboBox.isEnabled());
            }
        });

        formatFilterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatFilterComboBox.setEnabled(!formatFilterComboBox.isEnabled());
            }
        });

        creatorPreferencesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatorPreferencesComboBox.setEnabled(!creatorPreferencesComboBox.isEnabled());
            }
        });

        datePreferencesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePreferencesComboBox.setEnabled(!datePreferencesComboBox.isEnabled());
            }
        });

        formatPreferencesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatPreferencesComboBox.setEnabled(!formatPreferencesComboBox.isEnabled());
            }
        });
    }

    private void setBoxModels(SolrInstance solr){
        String[] dates = new String[]{"24 hours", "last week", "last month", "last year"};
        datePreferenceComboBoxModel = new javax.swing.DefaultComboBoxModel<>(dates);
        dateFilterComboBoxModel = new javax.swing.DefaultComboBoxModel<>(dates);
        try {
            String[] contentTypes = solr.getFilterOptions("content_type");
            for(int i = 0; i < contentTypes.length; i++){
                contentTypes[i] = ContentTypes.getSimpleName(contentTypes[i]);
            }
            String[] creators = solr.getFilterOptions("owner");
            formatPreferenceComboBoxModel = new javax.swing.DefaultComboBoxModel<>(contentTypes);
            formatFilterComboBoxModel = new javax.swing.DefaultComboBoxModel<>(contentTypes);
            creatorFilterComboBoxModel = new javax.swing.DefaultComboBoxModel<>(creators);
            creatorPreferenceComboBoxModel = new javax.swing.DefaultComboBoxModel<>(creators);
        }catch(Exception ex){
            System.out.println("Error when retrieving filter options: " + ex.getMessage());
        }
    }

    private void readAndSaveFilters(){
        if(creatorFilterComboBox.isEnabled()){
            String item = creatorFilterComboBox.getSelectedItem().toString();
            addFilter(Filter.CREATORFILTER, item);
        }
        if(dateFilterComboBox.isEnabled()){
            String item = dateFilterComboBox.getSelectedItem().toString();
            addFilter(Filter.DATEFILTER, item);
        }
        if(formatFilterComboBox.isEnabled()){
            String item = formatFilterComboBox.getSelectedItem().toString();
            addFilter(Filter.FORMATFILTER, item);
        }
        if(creatorPreferencesComboBox.isEnabled()){
            String item = creatorPreferencesComboBox.getSelectedItem().toString();
            addFilter(Filter.CREATORPREFERENECE, item);
        }
        if(datePreferencesComboBox.isEnabled()){
            String item = datePreferencesComboBox.getSelectedItem().toString();
            addFilter(Filter.DATEPREFERENECE, item);
        }
        if(formatPreferencesComboBox.isEnabled()){
            String item = formatPreferencesComboBox.getSelectedItem().toString();
            addFilter(Filter.FORMATPREFERENECE, item);
        }
    }

    private void addFilter(Filter filterType, String value){
        filters.put(filterType, value);
    }
}
