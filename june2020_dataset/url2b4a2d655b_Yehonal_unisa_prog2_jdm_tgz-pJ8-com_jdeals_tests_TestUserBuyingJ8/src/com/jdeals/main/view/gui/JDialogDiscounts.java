/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import javax.swing.JDialog;

import com.jdeals.libs.JTextFieldFilter;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.controller.Store;
import com.jdeals.main.helper.discount.LastDaysDiscount;
import com.jdeals.main.helper.discount.DiscountInterface;
import com.jdeals.main.helper.discount.QuantityDiscount;
import com.jdeals.main.helper.discount.RestourantDiscount;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Component;

/**
 * The Class JDialogDiscounts.
 */
public class JDialogDiscounts extends JDialog {

    /**
     * The Enum Discounts.
     */
    private enum Discounts {

        /**
         * The rest.
         */
        REST("Restourant"),
        /**
         * The quantity.
         */
        QUANTITY("Quantity"),
        /**
         * The lastdays.
         */
        LASTDAYS("Last Days");

        /**
         * The name.
         */
        private String name;

        /**
         * Instantiates a new discounts.
         *
         * @param name the name
         */
        private Discounts(String name) {
            this.name = name;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The j quantity val.
     */
    private JTextField jQuantityVal;

    /**
     * The j last days.
     */
    private JTextField jLastDays;

    /**
     * The j rest percentage.
     */
    private JTextField jRestPercentage;

    /**
     * The j rest days.
     */
    private JTextField jRestDays;

    /**
     * The j rest discount.
     */
    private JTextField jRestDiscount;

    /**
     * The j quantity discount.
     */
    private JTextField jQuantityDiscount;

    /**
     * The j l days discount.
     */
    private JTextField jLDaysDiscount;

    /**
     * The j table discounts.
     */
    private JTable jTableDiscounts;

    /**
     * The j panel left.
     */
    private JPanel jPanelLeft;

    /**
     * The combo box.
     */
    private JComboBox comboBox;

    /**
     * The btn new button.
     */
    private JButton btnNewButton;

    /**
     * The lbl new label.
     */
    private JLabel lblNewLabel;

    /**
     * The lbl new label_1.
     */
    private JLabel lblNewLabel_1;

    /**
     * The lbl new label_2.
     */
    private JLabel lblNewLabel_2;

    /**
     * The lbl quantity.
     */
    private JLabel lblQuantity;

    /**
     * The lbl discount rate.
     */
    private JLabel lblDiscountRate;

    /**
     * The lbl last days.
     */
    private JLabel lblLastDays;

    /**
     * The lbl discount rate_1.
     */
    private JLabel lblDiscountRate_1;

    /**
     * Instantiates a new j dialog discounts.
     *
     * @param ctrl the ctrl
     */
    public JDialogDiscounts(JDealsController ctrl) {
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setSize(640, 480);
        this.setMaximumSize(this.getSize());
        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{
                    "Disctounts rules"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel jPanelMain = new JPanel();
        jPanelMain.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5), // outer border
                BorderFactory.createLoweredBevelBorder()));
        getContentPane().add(jPanelMain);
        GridBagLayout gbl_jPanelMain = new GridBagLayout();
        gbl_jPanelMain.columnWeights = new double[]{1.0};
        gbl_jPanelMain.rowWeights = new double[]{1.0, 0.0};
        jPanelMain.setLayout(gbl_jPanelMain);

        jTableDiscounts = new JTable();
        jTableDiscounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableDiscounts.setModel(model);

        loadDiscounts();

        jTableDiscounts.setSize(300, 300);

        JScrollPane jScrollCenter = new JScrollPane(jTableDiscounts);
        GridBagConstraints gbc_jScrollCenter = new GridBagConstraints();
        gbc_jScrollCenter.fill = GridBagConstraints.BOTH;
        gbc_jScrollCenter.insets = new Insets(0, 0, 5, 0);
        gbc_jScrollCenter.gridx = 0;
        gbc_jScrollCenter.gridy = 0;
        jPanelMain.add(jScrollCenter, gbc_jScrollCenter);

        JPanel jPanelSouth = new JPanel();
        GridBagConstraints gbc_jPanelSouth = new GridBagConstraints();
        gbc_jPanelSouth.anchor = GridBagConstraints.NORTH;
        gbc_jPanelSouth.fill = GridBagConstraints.BOTH;
        gbc_jPanelSouth.gridx = 0;
        gbc_jPanelSouth.gridy = 1;
        jPanelMain.add(jPanelSouth, gbc_jPanelSouth);

        comboBox = new JComboBox(Discounts.values());
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePanel();
            }
        });
        jPanelSouth.setLayout(new BoxLayout(jPanelSouth, BoxLayout.X_AXIS));
        jPanelSouth.add(comboBox);

        jPanelLeft = new JPanel();
        jPanelSouth.add(jPanelLeft);
        jPanelLeft.setLayout(new CardLayout(5, 0));

        JPanel jPanelRestourant = new JPanel();
        jPanelLeft.add(jPanelRestourant, Discounts.REST.toString());
        GridBagLayout gbl_jPanelRestourant = new GridBagLayout();
        gbl_jPanelRestourant.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        gbl_jPanelRestourant.rowWeights = new double[]{0.0, 0.0};
        jPanelRestourant.setLayout(gbl_jPanelRestourant);

        lblNewLabel = new JLabel("\"To sell\" rate:");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        jPanelRestourant.add(lblNewLabel, gbc_lblNewLabel);

        lblNewLabel_1 = new JLabel("Last # Days:");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 0;
        jPanelRestourant.add(lblNewLabel_1, gbc_lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Discount Rate:");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 2;
        gbc_lblNewLabel_2.gridy = 0;
        jPanelRestourant.add(lblNewLabel_2, gbc_lblNewLabel_2);

        jRestPercentage = new JTextField();
        jRestPercentage.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jRestPercentage = new GridBagConstraints();
        gbc_jRestPercentage.fill = GridBagConstraints.BOTH;
        gbc_jRestPercentage.insets = new Insets(0, 0, 0, 5);
        gbc_jRestPercentage.gridx = 0;
        gbc_jRestPercentage.gridy = 1;
        jPanelRestourant.add(jRestPercentage, gbc_jRestPercentage);
        jRestPercentage.setText("");
        jRestPercentage.setColumns(5);

        JButton btnAddRest = new JButton("Add");
        btnAddRest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRestDiscount();
            }
        });

        jRestDays = new JTextField();
        jRestDays.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jRestDays = new GridBagConstraints();
        gbc_jRestDays.fill = GridBagConstraints.BOTH;
        gbc_jRestDays.insets = new Insets(0, 0, 0, 5);
        gbc_jRestDays.gridx = 1;
        gbc_jRestDays.gridy = 1;
        jPanelRestourant.add(jRestDays, gbc_jRestDays);
        jRestDays.setColumns(5);

        jRestDiscount = new JTextField();
        jRestDiscount.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jRestDiscount = new GridBagConstraints();
        gbc_jRestDiscount.fill = GridBagConstraints.BOTH;
        gbc_jRestDiscount.insets = new Insets(0, 0, 0, 5);
        gbc_jRestDiscount.gridx = 2;
        gbc_jRestDiscount.gridy = 1;
        jPanelRestourant.add(jRestDiscount, gbc_jRestDiscount);
        jRestDiscount.setText("");
        jRestDiscount.setColumns(5);
        GridBagConstraints gbc_btnAddRest = new GridBagConstraints();
        gbc_btnAddRest.fill = GridBagConstraints.BOTH;
        gbc_btnAddRest.gridx = 3;
        gbc_btnAddRest.gridy = 1;
        jPanelRestourant.add(btnAddRest, gbc_btnAddRest);

        JPanel jPanelQuantity = new JPanel();
        jPanelLeft.add(jPanelQuantity, Discounts.QUANTITY.toString());
        GridBagLayout gbl_jPanelQuantity = new GridBagLayout();
        gbl_jPanelQuantity.columnWidths = new int[]{56, 114, 114, 61, 0};
        gbl_jPanelQuantity.rowHeights = new int[]{0, 25, 0};
        gbl_jPanelQuantity.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_jPanelQuantity.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        jPanelQuantity.setLayout(gbl_jPanelQuantity);

        lblQuantity = new JLabel("Quantity:");
        GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
        gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
        gbc_lblQuantity.gridx = 1;
        gbc_lblQuantity.gridy = 0;
        jPanelQuantity.add(lblQuantity, gbc_lblQuantity);

        lblDiscountRate = new JLabel("Discount rate:");
        GridBagConstraints gbc_lblDiscountRate = new GridBagConstraints();
        gbc_lblDiscountRate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDiscountRate.gridx = 2;
        gbc_lblDiscountRate.gridy = 0;
        jPanelQuantity.add(lblDiscountRate, gbc_lblDiscountRate);

        jQuantityVal = new JTextField();
        jQuantityVal.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jQuantityVal = new GridBagConstraints();
        gbc_jQuantityVal.anchor = GridBagConstraints.WEST;
        gbc_jQuantityVal.insets = new Insets(0, 0, 0, 5);
        gbc_jQuantityVal.gridx = 1;
        gbc_jQuantityVal.gridy = 1;
        jPanelQuantity.add(jQuantityVal, gbc_jQuantityVal);
        jQuantityVal.setColumns(10);

        jQuantityDiscount = new JTextField();
        jQuantityDiscount.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        jQuantityDiscount.setText("");
        GridBagConstraints gbc_jQuantityDiscount = new GridBagConstraints();
        gbc_jQuantityDiscount.anchor = GridBagConstraints.WEST;
        gbc_jQuantityDiscount.insets = new Insets(0, 0, 0, 5);
        gbc_jQuantityDiscount.gridx = 2;
        gbc_jQuantityDiscount.gridy = 1;
        jPanelQuantity.add(jQuantityDiscount, gbc_jQuantityDiscount);
        jQuantityDiscount.setColumns(10);

        JButton btnQuantityAdd = new JButton("Add");
        btnQuantityAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addQuantityDiscount();
            }
        });
        GridBagConstraints gbc_btnQuantityAdd = new GridBagConstraints();
        gbc_btnQuantityAdd.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnQuantityAdd.gridx = 3;
        gbc_btnQuantityAdd.gridy = 1;
        jPanelQuantity.add(btnQuantityAdd, gbc_btnQuantityAdd);

        JPanel jPanelLastDays = new JPanel();
        jPanelLeft.add(jPanelLastDays, Discounts.LASTDAYS.toString());
        GridBagLayout gbl_jPanelLastDays = new GridBagLayout();
        gbl_jPanelLastDays.columnWidths = new int[]{33, 114, 114, 61, 0};
        gbl_jPanelLastDays.rowHeights = new int[]{0, 25, 0};
        gbl_jPanelLastDays.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_jPanelLastDays.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        jPanelLastDays.setLayout(gbl_jPanelLastDays);

        lblLastDays = new JLabel("Last Days:");
        GridBagConstraints gbc_lblLastDays = new GridBagConstraints();
        gbc_lblLastDays.insets = new Insets(0, 0, 5, 5);
        gbc_lblLastDays.gridx = 1;
        gbc_lblLastDays.gridy = 0;
        jPanelLastDays.add(lblLastDays, gbc_lblLastDays);

        lblDiscountRate_1 = new JLabel("Discount rate:");
        GridBagConstraints gbc_lblDiscountRate_1 = new GridBagConstraints();
        gbc_lblDiscountRate_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblDiscountRate_1.gridx = 2;
        gbc_lblDiscountRate_1.gridy = 0;
        jPanelLastDays.add(lblDiscountRate_1, gbc_lblDiscountRate_1);

        jLastDays = new JTextField();
        jLastDays.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        jLastDays.setText("");
        GridBagConstraints gbc_jLastDays = new GridBagConstraints();
        gbc_jLastDays.anchor = GridBagConstraints.WEST;
        gbc_jLastDays.insets = new Insets(0, 0, 0, 5);
        gbc_jLastDays.gridx = 1;
        gbc_jLastDays.gridy = 1;
        jPanelLastDays.add(jLastDays, gbc_jLastDays);
        jLastDays.setColumns(10);

        jLDaysDiscount = new JTextField();
        jLDaysDiscount.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        jLDaysDiscount.setColumns(10);
        GridBagConstraints gbc_jLDaysDiscount = new GridBagConstraints();
        gbc_jLDaysDiscount.anchor = GridBagConstraints.WEST;
        gbc_jLDaysDiscount.insets = new Insets(0, 0, 0, 5);
        gbc_jLDaysDiscount.gridx = 2;
        gbc_jLDaysDiscount.gridy = 1;
        jPanelLastDays.add(jLDaysDiscount, gbc_jLDaysDiscount);

        JButton btnLastDays = new JButton("Add");
        btnLastDays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addLastDaysDiscount();
            }
        });
        GridBagConstraints gbc_btnLastDays = new GridBagConstraints();
        gbc_btnLastDays.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnLastDays.gridx = 3;
        gbc_btnLastDays.gridy = 1;
        jPanelLastDays.add(btnLastDays, gbc_btnLastDays);

        btnNewButton = new JButton("Delete");
        btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelDiscount();
            }
        });
        jPanelSouth.add(btnNewButton);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Adds the last days discount.
     */
    protected void addLastDaysDiscount() {
        int days = Integer.valueOf(jLastDays.getText());
        int disc = Integer.valueOf(jLDaysDiscount.getText());
        this.addDiscount(new LastDaysDiscount(disc, days));
        loadDiscounts();
        jLastDays.setText("");
        jLDaysDiscount.setText("");
    }

    /**
     * Adds the quantity discount.
     */
    protected void addQuantityDiscount() {
        int disc = Integer.valueOf(jQuantityDiscount.getText());
        int quantity = Integer.valueOf(jQuantityVal.getText());
        this.addDiscount(new QuantityDiscount(quantity, disc));
        loadDiscounts();
        jQuantityDiscount.setText("");
        jQuantityVal.setText("");
    }

    /**
     * Adds the rest discount.
     */
    protected void addRestDiscount() {
        int days = Integer.valueOf(jRestDays.getText());
        int disc = Integer.valueOf(jRestDiscount.getText());
        int perc = Integer.valueOf(jRestPercentage.getText());
        this.addDiscount(new RestourantDiscount(perc, disc, days));
        loadDiscounts();

        jRestDays.setText("");
        jRestDiscount.setText("");
        jRestPercentage.setText("");
    }

    /**
     * Delete sel discount.
     */
    protected void deleteSelDiscount() {
        int i = jTableDiscounts.getSelectedRow();
        if (i < 0) {
            return;
        }

        Store store = this.ctrl.getStore();
        store.removeDiscount(i);
        loadDiscounts();
    }

    /**
     * Load discounts.
     */
    protected void loadDiscounts() {
        final Store store = this.ctrl.getStore();
        DefaultTableModel model = (DefaultTableModel) jTableDiscounts.getModel();
        model.getDataVector().removeAllElements();
        if (store.getDiscounts() != null && !store.getDiscounts().isEmpty()) {
            for (final DiscountInterface<Object> d : store.getDiscounts()) {
                model.addRow(new Object[]{d});
            }
        }
        model.fireTableDataChanged();
    }

    /**
     * Adds the discount.
     *
     * @param d the d
     */
    protected void addDiscount(DiscountInterface<Object> d) {
        try {
            this.ctrl.getStore().addDiscount(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Change panel.
     */
    protected void changePanel() {
        CardLayout cl = (CardLayout) (jPanelLeft.getLayout());
        cl.show(jPanelLeft, ((Discounts) comboBox.getSelectedItem()).toString());
    }
}
