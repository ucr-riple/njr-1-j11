/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.jdeals.libs.JTextFieldFilter;
import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import javax.swing.ImageIcon;

/**
 * The Class JFrameLogin.
 */
public class JFrameLogin extends JFrame {

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The username.
     */
    private JTextField username;

    /**
     * The password field.
     */
    private JPasswordField passwordField;

    /**
     * The username_reg.
     */
    private JTextField username_reg;

    /**
     * The email_reg.
     */
    private JTextField email_reg;

    /**
     * The pass_reg.
     */
    private JPasswordField pass_reg;

    /**
     * The city.
     */
    private JTextField city;

    /**
     * The register.
     */
    private final JButton register = new JButton("Register");

    /**
     * Instantiates a new j frame login.
     *
     * @param ctrl the ctrl
     */
    public JFrameLogin(final JDealsController ctrl) {
        this.ctrl = ctrl;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("JDeals Login");

        //CENTER
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JMenuItem mntmCli = new JMenuItem("CLI");
        mntmCli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                (new Thread(new JFrameCli(ctrl))).start();
                dispose();
            }
        });
        mnMenu.add(mntmCli);

        getContentPane().setLayout(new GridLayout(0, 2, 7, 10));

        GridLayout gl_login = new GridLayout(5, 1);
        gl_login.setVgap(10);
        gl_login.setHgap(10);
        JPanel login = new JPanel(gl_login);
        login.setBorder(UIManager.getBorder("OptionPane.border"));
        login.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        login.getActionMap().put("Enter", new Key(KeyEvent.VK_ENTER));

        getContentPane().add(login);

        JButton btnLogin = new JButton("Login");
        btnLogin.setIcon(new ImageIcon("res/loginS.png"));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginPermormed();
            }
        });

        JLabel lblUsernameOrEmail = new JLabel("Username or Email");
        login.add(lblUsernameOrEmail);

        username = new JTextField();
        username.setDocument(new JTextFieldFilter(JTextFieldFilter.ALPHA_NUMERIC));
        login.add(username);
        username.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        login.add(lblPassword);

        passwordField = new JPasswordField();
        login.add(passwordField);
        login.add(btnLogin);

        GridLayout gl_reg = new GridLayout(5, 1);
        gl_reg.setVgap(5);
        gl_reg.setHgap(5);
        JPanel reg = new JPanel(gl_reg);
        reg.setBorder(UIManager.getBorder("OptionPane.border"));
        getContentPane().add(reg);

        JLabel lblUsername = new JLabel("Username");
        reg.add(lblUsername);

        username_reg = new JTextField();
        username_reg.setDocument(new JTextFieldFilter(JTextFieldFilter.ALPHA_NUMERIC));
        reg.add(username_reg);
        username_reg.setColumns(10);

        JLabel lblEmail = new JLabel("Email");
        reg.add(lblEmail);

        email_reg = new JTextField();
        reg.add(email_reg);
        email_reg.setColumns(10);

        JLabel lblPassword_1 = new JLabel("Password");
        reg.add(lblPassword_1);

        pass_reg = new JPasswordField();
        reg.add(pass_reg);
        pass_reg.setColumns(10);

        JLabel lblCity = new JLabel("City");
        reg.add(lblCity);

        city = new JTextField();
        reg.add(city);
        city.setColumns(10);
        register.setIcon(new ImageIcon("res/register.png"));
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regPerformed();
            }
        });
        reg.add(register);

        pack();
        this.setVisible(true);
    }

    /**
     * Run console.
     *
     * @param ctrl the ctrl
     */
    public static void runConsole(JDealsController ctrl) {
        new JFrameCli(ctrl);
    }

    /**
     * Login permormed.
     */
    public void loginPermormed() {
        try {
            ctrl.Login(this.username.getText(), new String(this.passwordField.getPassword()));

            ctrl.setFrame(new JFrameMain(ctrl));

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reg performed.
     */
    public void regPerformed() {
        try {
            this.ctrl.Register(this.username_reg.getText(), this.email_reg.getText(), this.city.getText(), new String(this.pass_reg.getPassword()));
            this.username_reg.setText("");
            this.pass_reg.setText("");
            this.city.setText("");
            this.email_reg.setText("");
            JOptionPane.showMessageDialog(this, "Correctly registered user");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The Class Key.
     */
    protected class Key extends AbstractAction {

        /**
         * The evt.
         */
        private int evt;

        /**
         * Instantiates a new key.
         *
         * @param evt the evt
         */
        public Key(int evt) {
            this.evt = evt;
        }

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (evt == KeyEvent.VK_ENTER) {
                loginPermormed();
            }
        }
    }

}
