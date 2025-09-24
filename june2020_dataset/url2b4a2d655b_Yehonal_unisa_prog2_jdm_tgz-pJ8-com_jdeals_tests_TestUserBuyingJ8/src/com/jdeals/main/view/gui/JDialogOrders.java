/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.Order;
import com.jdeals.main.helper.sort.OrderCostSort;
import com.jdeals.main.helper.sort.OrderDateSort;
import com.jdeals.main.helper.sort.SortingClass;
import com.jdeals.main.helper.sort.SortingClass.SortingDirection;

import javax.swing.JScrollPane;

/**
 * The Class JDialogOrders.
 */
public class JDialogOrders extends JDialog {

    /**
     * The Enum ListOrder.
     */
    public enum ListOrder {

        /**
         * The by date.
         */
        BY_DATE("By Date", new OrderDateSort(SortingDirection.CRESC)),
        /**
         * The by price.
         */
        BY_PRICE("By Price", new OrderCostSort(SortingDirection.CRESC));

        /**
         * The val.
         */
        private String val;

        /**
         * The c.
         */
        private SortingClass<Order> c;

        /**
         * Instantiates a new list order.
         *
         * @param val the val
         * @param c the c
         */
        private ListOrder(String val, SortingClass<Order> c) {
            this.val = val;
            this.c = c;
        }

        /**
         * Gets the val.
         *
         * @return the val
         */
        public String getVal() {
            return val;
        }

        /**
         * Gets the comparator.
         *
         * @return the comparator
         */
        public SortingClass<Order> getComparator() {
            return this.c;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.val;
        }
    }

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The j table orders.
     */
    private JTable jTableOrders;

    /**
     * The j cb direction.
     */
    private JComboBox jCbDirection;

    /**
     * The combo box.
     */
    private JComboBox comboBox;

    /**
     * Instantiates a new j dialog orders.
     *
     * @param ctrl the ctrl
     */
    public JDialogOrders(JDealsController ctrl) {
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.setSize(400, 300);
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel jPanelMain = new JPanel();
        getContentPane().add(jPanelMain, BorderLayout.NORTH);
        jPanelMain.setLayout(new BorderLayout(0, 0));

        JPanel jPanelTop = new JPanel();
        jPanelMain.add(jPanelTop, BorderLayout.NORTH);

        JLabel jLblSort = new JLabel("Sort By:");
        jPanelTop.add(jLblSort);

        comboBox = new JComboBox(ListOrder.values());
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        jPanelTop.add(comboBox);

        jCbDirection = new JComboBox(SortingDirection.values());
        jCbDirection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        jPanelTop.add(jCbDirection);

        JButton btnOpenOrder = new JButton("Open Order");
        btnOpenOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOrder();
            }
        });
        jPanelTop.add(btnOpenOrder);

        JPanel JPanelCenter = new JPanel();
        jPanelMain.add(JPanelCenter, BorderLayout.CENTER);

        jTableOrders = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{"Date", "Price"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
        jTableOrders.setModel(model);
        loadTable();

        JScrollPane scrollPane = new JScrollPane(jTableOrders);
        JPanelCenter.add(scrollPane);

        Tools.fixMinSize(this, true);
        this.setVisible(true);
    }

    /**
     * Open the order.
     */
    protected void openOrder() {
        int i = jTableOrders.getSelectedRow();
        if (i < 0) {
            return;
        }

        Order order = this.ctrl.getCurUser().getOrders().get(i);

        new JDialogOrder(this.ctrl, order, false);
    }

    /**
     * Load table.
     *
     * @param orders the orders
     */
    protected void loadTable(ArrayList<Order> orders) {
        SortingClass<Order> sc = ((ListOrder) comboBox.getSelectedItem()).getComparator();
        sc.setDirection((SortingDirection) jCbDirection.getSelectedItem());
        Collections.sort(orders, sc);

        DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();
        model.getDataVector().removeAllElements();

        for (Order o : orders) {
            model.addRow(new Object[]{o.getDate().toString(), o.getTotalPrice(), o});
        }

        model.fireTableDataChanged();
    }

    /**
     * Load table.
     */
    protected void loadTable() {
        ArrayList<Order> orders = this.ctrl.getCurUser().getOrders();
        this.loadTable(orders);
    }
}
