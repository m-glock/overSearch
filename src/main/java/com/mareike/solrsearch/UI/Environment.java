package com.mareike.solrsearch.UI;

import com.mareike.solrsearch.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Environment extends JFrame {

    private javax.swing.JLabel information;
    private javax.swing.JLabel information1;
    private javax.swing.JButton ownFiles;
    private javax.swing.JButton testFiles;

    public Environment() {
        initComponents();
        addActionListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        information = new javax.swing.JLabel();
        information1 = new javax.swing.JLabel();
        testFiles = new javax.swing.JButton();
        ownFiles = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(600, 300));
        setMinimumSize(new java.awt.Dimension(600, 300));
        setPreferredSize(new java.awt.Dimension(600, 300));

        information.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        information.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        information.setText("Would you like to use the test files");
        information.setMaximumSize(new java.awt.Dimension(600, 20));
        information.setMinimumSize(new java.awt.Dimension(600, 20));
        information.setPreferredSize(new java.awt.Dimension(600, 20));

        information1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        information1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        information1.setText("or your own files for the application?");
        information1.setMaximumSize(new java.awt.Dimension(600, 20));
        information1.setMinimumSize(new java.awt.Dimension(600, 20));
        information1.setPreferredSize(new java.awt.Dimension(600, 20));

        testFiles.setText("test files");
        testFiles.setFont(new java.awt.Font("Tahoma", 0, 16));

        ownFiles.setText("own files");
        ownFiles.setFont(new java.awt.Font("Tahoma", 0, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(testFiles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ownFiles)
                                .addGap(100, 100, 100))
                        .addComponent(information1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(information, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(information, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(information1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ownFiles)
                                        .addComponent(testFiles))
                                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>

    private void addActionListeners() {
        testFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.startApplcation();
            }
        });

        ownFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setStartDirectory(System.getProperty("user.home"));
                Main.startApplcation();
            }
        });
    }




}
