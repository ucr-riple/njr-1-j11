/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jdeals.libs.JTextFieldFilter;
import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;

import java.awt.BorderLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

/**
 * The Class JDialogProfile.
 */
public class JDialogProfile extends JDialog {

    /**
     * The ctrl.
     */
    JDealsController ctrl;

    /**
     * The content panel.
     */
    JPanel contentPanel;

    /**
     * The j text add credit.
     */
    JTextField jTextAddCredit;

    /**
     * The j btn close.
     */
    private JButton jBtnClose;

    /**
     * The j text credit.
     */
    private JTextField jTextCredit;

    /**
     * The j btn add credit.
     */
    private JButton jBtnAddCredit;

    /**
     * The j text name.
     */
    private JTextField jTextName;

    /**
     * The j btn save.
     */
    private JButton jBtnSave;

    /**
     * The j lbl email.
     */
    private JLabel jLblEmail;

    /**
     * The j text email.
     */
    private JTextField jTextEmail;

    /**
     * Instantiates a new j dialog profile.
     *
     * @param ctrl the ctrl
     */
    public JDialogProfile(JDealsController ctrl) {
        // INITIAL SETTINGS
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.setSize(440, 400);
        setModalityType(ModalityType.APPLICATION_MODAL);

        Insets stdInsets = new Insets(10, 10, 5, 5);

        // ADD COMPONENTS
        this.contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(this.contentPanel, BorderLayout.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 97};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        // NAME LABEL
        JLabel jLabelName = new JLabel("Name:");
        GridBagConstraints gbc_jLabelName = new GridBagConstraints();
        gbc_jLabelName.anchor = GridBagConstraints.WEST;
        gbc_jLabelName.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelName.insets = stdInsets;
        gbc_jLabelName.gridx = 0;
        gbc_jLabelName.gridy = 0;
        contentPanel.add(jLabelName, gbc_jLabelName);
        // NAME TEXT
        jTextName = new JTextField(ctrl.getCurUser().getUsername());
        jTextName.setEditable(false);
        jTextName.setColumns(10);
        GridBagConstraints gbc_jTextName = new GridBagConstraints();
        gbc_jTextName.anchor = GridBagConstraints.NORTH;
        gbc_jTextName.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextName.insets = stdInsets;
        gbc_jTextName.gridx = 2;
        gbc_jTextName.gridy = 0;
        contentPanel.add(jTextName, gbc_jTextName);
        // EMAIL LABEL
        jLblEmail = new JLabel("Email:");
        GridBagConstraints gbc_jLblEmail = new GridBagConstraints();
        gbc_jLblEmail.anchor = GridBagConstraints.WEST;
        gbc_jLblEmail.insets = stdInsets;
        gbc_jLblEmail.gridx = 0;
        gbc_jLblEmail.gridy = 1;
        contentPanel.add(jLblEmail, gbc_jLblEmail);
        // EMAIL TEXT
        jTextEmail = new JTextField(ctrl.getCurUser().getEmail());
        GridBagConstraints gbc_jTextEmail = new GridBagConstraints();
        gbc_jTextEmail.insets = stdInsets;
        gbc_jTextEmail.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextEmail.gridx = 2;
        gbc_jTextEmail.gridy = 1;
        contentPanel.add(jTextEmail, gbc_jTextEmail);
        jTextEmail.setColumns(10);
        // CREDITS LABEL
        JLabel jLabelCredits = new JLabel("Credits:");
        GridBagConstraints gbc_jLabelCredits = new GridBagConstraints();
        gbc_jLabelCredits.anchor = GridBagConstraints.NORTH;
        gbc_jLabelCredits.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelCredits.insets = stdInsets;
        gbc_jLabelCredits.gridx = 0;
        gbc_jLabelCredits.gridy = 3;
        contentPanel.add(jLabelCredits, gbc_jLabelCredits);
        // CREDITS TEXT
        jTextCredit = new JTextField(String.valueOf(this.ctrl.getCurUser().getCredit()));
        GridBagConstraints gbc_jTextCredit = new GridBagConstraints();
        gbc_jTextCredit.anchor = GridBagConstraints.NORTH;
        gbc_jTextCredit.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextCredit.insets = stdInsets;
        gbc_jTextCredit.gridx = 2;
        gbc_jTextCredit.gridy = 3;
        contentPanel.add(jTextCredit, gbc_jTextCredit);
        jTextCredit.setEditable(false);
        jTextCredit.setColumns(10);
        // ADD CREDIT TEXT
        this.jTextAddCredit = new JTextField(6);
        GridBagConstraints gbc_jTextAddCredit = new GridBagConstraints();
        gbc_jTextAddCredit.anchor = GridBagConstraints.NORTH;
        gbc_jTextAddCredit.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextAddCredit.insets = stdInsets;
        gbc_jTextAddCredit.gridx = 3;
        gbc_jTextAddCredit.gridy = 3;
        contentPanel.add(jTextAddCredit, gbc_jTextAddCredit);
        this.jTextAddCredit.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        // ADD CREDIT BUTTON
        jBtnAddCredit = new JButton("Add Credit");
        jBtnAddCredit.setIcon(new ImageIcon("res/dollar.png"));
        GridBagConstraints gbc_jBtnAddCredit = new GridBagConstraints();
        gbc_jBtnAddCredit.anchor = GridBagConstraints.NORTH;
        gbc_jBtnAddCredit.fill = GridBagConstraints.HORIZONTAL;
        gbc_jBtnAddCredit.insets = new Insets(10, 10, 5, 0);
        gbc_jBtnAddCredit.gridx = 4;
        gbc_jBtnAddCredit.gridy = 3;
        contentPanel.add(jBtnAddCredit, gbc_jBtnAddCredit);
        jBtnAddCredit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCredit();
            }
        });
        // CLOSE BUTTON 
        jBtnClose = new JButton("close");
        jBtnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        GridBagConstraints gbc_jBtnClose = new GridBagConstraints();
        gbc_jBtnClose.anchor = GridBagConstraints.SOUTH;
        gbc_jBtnClose.fill = GridBagConstraints.HORIZONTAL;
        gbc_jBtnClose.insets = new Insets(10, 10, 0, 5);
        gbc_jBtnClose.gridx = 3;
        gbc_jBtnClose.gridy = 5;
        contentPanel.add(jBtnClose, gbc_jBtnClose);
        // SAVE BUTTON 
        jBtnSave = new JButton("save");
        jBtnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });
        GridBagConstraints gbc_jBtnSave = new GridBagConstraints();
        gbc_jBtnSave.anchor = GridBagConstraints.SOUTH;
        gbc_jBtnSave.fill = GridBagConstraints.HORIZONTAL;
        gbc_jBtnSave.insets = new Insets(10, 10, 0, 0);
        gbc_jBtnSave.gridx = 4;
        gbc_jBtnSave.gridy = 5;
        contentPanel.add(jBtnSave, gbc_jBtnSave);

        // SHOW
        Tools.fixMinSize(this, true);
        this.setVisible(true);
    }

    /**
     * Save profile.
     */
    protected void saveProfile() {
        this.ctrl.getCurUser().setCredit(Double.parseDouble(this.jTextCredit.getText()));
        this.ctrl.getCurUser().setEmail(this.jTextEmail.getText());

        dispose();
    }

    /**
     * Adds the credit.
     */
    protected void addCredit() {
        double current = Double.parseDouble(this.jTextCredit.getText());
        this.jTextCredit.setText(String.valueOf(current + Double.parseDouble(this.jTextAddCredit.getText())));
        this.jTextAddCredit.setText("");
    }
}
