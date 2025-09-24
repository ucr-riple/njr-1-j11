/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jdeals.libs.JTextConsole;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.view.cli.MainMenu;

/**
 * The Class JFrameCli.
 */
public class JFrameCli extends JFrame implements Runnable {

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The j button1.
     */
    private JButton jButton1;

    /**
     * The j panel1.
     */
    private JPanel jPanel1;

    /**
     * The j panel south.
     */
    private JPanel jPanelSouth;

    /**
     * The j scroll pane3.
     */
    private JScrollPane jScrollPane3;

    /**
     * The j text area1.
     */
    private JTextArea jTextArea1;

    /**
     * The j text field1.
     */
    private JTextField jTextField1;

    /**
     * Instantiates a new j frame cli.
     *
     * @param ctrl the ctrl
     */
    public JFrameCli(JDealsController ctrl) {
        this.ctrl = ctrl;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("JDeals CLI");

        //CENTER
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        jPanel1 = new JPanel();
        jPanelSouth = new JPanel();
        jTextArea1 = new JTextArea();
        jTextField1 = new JTextField();
        jTextField1.setColumns(20);
        jButton1 = new JButton();
        jScrollPane3 = new JScrollPane();

        getContentPane().add(jPanel1);

        jPanel1.setMaximumSize(new java.awt.Dimension(640, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("ClearScr");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setFocusable(false);
        jScrollPane3.setRequestFocusEnabled(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(20);
        jTextArea1.setRequestFocusEnabled(false);
        jScrollPane3.setViewportView(jTextArea1);
        jPanel1.setLayout(new BorderLayout(0, 0));

        jPanel1.add(jPanelSouth, BorderLayout.SOUTH);
        jPanelSouth.add(jTextField1);
        jPanelSouth.add(jButton1);
        jPanel1.add(jScrollPane3, BorderLayout.CENTER);

        pack();
        this.setVisible(true);

        jTextField1.requestFocus();

        try {
            PipedInputStream inPipe = new PipedInputStream();
            PipedInputStream outPipe = new PipedInputStream();

            PrintStream outStream = new PrintStream(new PipedOutputStream(outPipe), true);
            System.setIn(inPipe);
            System.setOut(outStream);
            PrintWriter inWriter = new PrintWriter(new PipedOutputStream(inPipe), true);

            JTextConsole.console(jTextArea1, jTextField1, outPipe, inWriter);

            JDealsController.scan = new Scanner(System.in);

        } catch (IOException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(ERROR);
        }
    }

    /**
     * J text field1 action performed.
     *
     * @param evt the evt
     */
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        jTextArea1.append(jTextField1.getText() + "\n");
        jTextField1.setText("");
    }

    /**
     * J button1 action performed.
     *
     * @param evt the evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jTextArea1.setText("Press enter to continue..");
        jTextField1.requestFocus();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        new MainMenu(this.ctrl).runMenu();
    }
}
