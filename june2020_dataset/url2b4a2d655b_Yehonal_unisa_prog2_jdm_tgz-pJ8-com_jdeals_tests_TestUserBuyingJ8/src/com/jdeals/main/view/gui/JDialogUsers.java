/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import javax.swing.JDialog;

import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.user.Manager;
import com.jdeals.main.entity.user.User;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

/**
 * The Class JDialogUsers.
 */
public class JDialogUsers extends JDialog {

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The j table users.
     */
    private JTable jTableUsers;

    /**
     * Instantiates a new j dialog users.
     *
     * @param ctrl the ctrl
     */
    public JDialogUsers(JDealsController ctrl) {
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.setSize(400, 300);
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel jPanelMain = new JPanel();
        getContentPane().add(jPanelMain, BorderLayout.NORTH);
        jPanelMain.setLayout(new BorderLayout(0, 0));

        JPanel JPanelCenter = new JPanel();
        jPanelMain.add(JPanelCenter, BorderLayout.CENTER);

        jTableUsers = new JTable();
        jTableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{
                    "Username", "Email", "Credit"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableUsers.setModel(model);

        JScrollPane scrollPane = new JScrollPane(jTableUsers);
        JPanelCenter.add(scrollPane);

        loadUsers();

        JPanel JPanelBottom = new JPanel();
        jPanelMain.add(JPanelBottom, BorderLayout.SOUTH);

        JButton btnSetAdmin = new JButton("Set Admin");
        btnSetAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAdmin();
            }
        });
        JPanelBottom.add(btnSetAdmin);

        JButton btnSetSupplier = new JButton("Set Supplier");
        btnSetSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSupplier();
            }
        });
        JPanelBottom.add(btnSetSupplier);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        JPanelBottom.add(btnDelete);

        Tools.fixMinSize(this, false);
        this.setVisible(true);
    }

    /**
     * Sets the supplier.
     */
    protected void setSupplier() {
        int i = jTableUsers.getSelectedRow();
        if (i < 0) {
            return;
        }
		// TODO IMPLEMENT
        //Manager man=new Manager(false, username, password, city, email);

        //this.ctrl.getUsers().remove(i);
    }

    /**
     * Sets the admin.
     */
    protected void setAdmin() {
        int i = jTableUsers.getSelectedRow();
        if (i < 0) {
            return;
        }
		// TODO IMPLEMENT
        //Manager man=new Manager(false, username, password, city, email);

        //this.ctrl.getUsers().remove(i);
    }

    /**
     * Delete.
     */
    protected void delete() {
        int i = jTableUsers.getSelectedRow();
        if (i < 0) {
            return;
        }

        this.ctrl.getUsers().remove(i);
        loadUsers();
    }

    /**
     * Load users.
     */
    protected void loadUsers() {
        DefaultTableModel model = (DefaultTableModel) jTableUsers.getModel();
        model.getDataVector().removeAllElements();
        ArrayList<User> users = this.ctrl.getUsers();

        for (User u : users) {
            model.addRow(new Object[]{u.getUsername(), u.getEmail(), u.getCredit()});
        }

        model.fireTableDataChanged();
    }
}
