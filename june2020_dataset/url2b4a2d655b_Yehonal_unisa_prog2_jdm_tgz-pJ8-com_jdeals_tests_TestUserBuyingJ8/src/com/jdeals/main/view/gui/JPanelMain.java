/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.user.Manager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

/**
 * The Class JPanelMain.
 */
public class JPanelMain extends javax.swing.JPanel {

    /**
     * The ctrl.
     */
    private final JDealsController ctrl;

    /**
     * The panel.
     */
    private JPanel panel;

    /**
     * The j btn admin.
     */
    private JButton jBtnAdmin;

    /**
     * Instantiates a new j panel main.
     *
     * @param ctrl the ctrl
     */
    public JPanelMain(JDealsController ctrl) {
        setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        //CENTER
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem jBtnDisconnect = new JMenuItem("Disconnect");
        jBtnDisconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        mnFile.add(jBtnDisconnect);

        JMenuItem jBtnSettings = new JMenuItem("Settings");
        jBtnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        mnFile.add(jBtnSettings);

        JMenuItem jBtnExit = new JMenuItem("Exit");
        jBtnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        mnFile.add(jBtnExit);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        JScrollPane scrollPane = new JScrollPane(panel);
        this.add(scrollPane);

        JButton jBtnCatalogue = new JButton("Catalogue");
        jBtnCatalogue.setIcon(new ImageIcon("res/negozio.jpg"));
        jBtnCatalogue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCatalogue();
            }
        });
        panel.add(jBtnCatalogue);

        JButton jBtnHistory = new JButton("Orders History");
        jBtnHistory.setIcon(new ImageIcon("res/order.png"));
        jBtnHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOrders();
            }
        });
        panel.add(jBtnHistory);

        JButton jBtnProfile = new JButton("My Profile");
        jBtnProfile.setIcon(new ImageIcon("res/profile_b48.png"));
        jBtnProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProfile();
            }
        });
        panel.add(jBtnProfile);

        if (ctrl.isManagerSession(true)) {
            jBtnAdmin = new JButton("User List");
            jBtnAdmin.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    openUserList();
                }
            });

            loadExtra();

            JButton jBtnDiscounts = new JButton("Discounts");
            jBtnDiscounts.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    openDiscounts();
                }
            });
            panel.add(jBtnDiscounts);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Component#repaint()
     */
    @Override
    public void repaint() {
        loadExtra();
        super.repaint();
    }

    /**
     * Load extra.
     */
    protected void loadExtra() {
        if (panel == null || jBtnAdmin == null) {
            return;
        }

        if (ctrl.getCurUser() instanceof Manager) {
            if (ctrl.getSettings().isExtrasEnabled()) {
                panel.add(jBtnAdmin);
                return;
            } else {
                panel.remove(jBtnAdmin);
                return;
            }
        }
    }

    /**
     * Open the settings.
     */
    protected void openSettings() {
        new JDialogSettings(this.ctrl);
    }

    /**
     * Exit.
     */
    protected void exit() {
        this.ctrl.getFrame().dispose();
    }

    /**
     * Disconnect.
     */
    protected void disconnect() {
        new JFrameLogin(this.ctrl);
        exit();
    }

    /**
     * Open the catalogue.
     */
    protected void openCatalogue() {
        new JDialogCatalogue(this.ctrl);
    }

    /**
     * Open the orders.
     */
    protected void openOrders() {
        new JDialogOrders(this.ctrl);
    }

    /**
     * Open the profile.
     */
    protected void openProfile() {
        new JDialogProfile(this.ctrl);
    }

    /**
     * Open the user list.
     */
    protected void openUserList() {
        new JDialogUsers(this.ctrl);
    }

    /**
     * Open the discounts.
     */
    protected void openDiscounts() {
        new JDialogDiscounts(this.ctrl);
    }
}
