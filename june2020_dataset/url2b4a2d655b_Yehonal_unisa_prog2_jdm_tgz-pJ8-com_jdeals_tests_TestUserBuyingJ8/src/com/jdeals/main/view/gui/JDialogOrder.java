/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.jdeals.libs.Message;
import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.catalogue.Supply;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * The Class JDialogOrder.
 */
public class JDialogOrder extends JDialog {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The j table items.
     */
    private JTable jTableItems;

    /**
     * The order.
     */
    private Order order;

    /**
     * The j text total price.
     */
    private JTextField jTextTotalPrice;

    /**
     * Instantiates a new JDialogOrder.
     *
     * @param ctrl the ctrl
     * @param order the order
     * @param isCart the is cart
     */
    public JDialogOrder(JDealsController ctrl, Order order, boolean isCart) {
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.order = order;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setSize(500, 300);
        this.setMaximumSize(this.getSize());

        JPanel JPanelTop = new JPanel();
        getContentPane().add(JPanelTop, BorderLayout.NORTH);

        JPanel JPanelCenter = new JPanel();
        getContentPane().add(JPanelCenter, BorderLayout.CENTER);

        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{
                    "Item id", "Quantity", "Price", "Discounted price"
                }
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTableItems = new JTable();
        jTableItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableItems.setModel(model);

        JScrollPane scrollPane = new JScrollPane(jTableItems);
        JPanelCenter.add(scrollPane);

        JPanel jPanelBottom = new JPanel();
        getContentPane().add(jPanelBottom, BorderLayout.SOUTH);

        JLabel lblTotalPrice = new JLabel("Total Price:");
        jPanelBottom.add(lblTotalPrice);

        jTextTotalPrice = new JTextField();
        jTextTotalPrice.setEditable(false);
        jPanelBottom.add(jTextTotalPrice);
        jTextTotalPrice.setColumns(10);

        if (isCart) {
            JButton jButtonBuy = new JButton("Pay Order");
            jButtonBuy.setIcon(new ImageIcon("res/dollar.png"));
            jButtonBuy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    payOrder();
                }
            });

            JPanelTop.add(jButtonBuy);

            JButton jButtonDelete = new JButton("Delete All");
            jButtonDelete.setIcon(new ImageIcon("res/delete.gif"));
            jButtonDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteOrder();
                }
            });

            JPanelTop.add(jButtonDelete);

            JButton jButtonDelItem = new JButton("Delete Item");
            jButtonDelItem.setIcon(new ImageIcon("res/delete.gif"));
            jButtonDelItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteItem();
                }
            });

            JPanelTop.add(jButtonDelItem);
        } else {
            final JComboBox voteBox = new JComboBox<>(new Byte[]{1, 2, 3, 4, 5});
            JPanelTop.add(voteBox);

            JButton jButtonVote = new JButton("Vote");
            jButtonVote.setIcon(new ImageIcon("res/icon_vote_for.png"));
            jButtonVote.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    voteItem(voteBox);
                }
            });

            JPanelTop.add(jButtonVote);
        }

        loadItems();

        Tools.fixMinSize(this, true);
        this.setVisible(true);
    }

    /**
     * Vote an Item.
     *
     * @param votebox the votebox
     */
    protected void voteItem(JComboBox votebox) {
        int row = jTableItems.getSelectedRow();
        if (row < 0) {
            return;
        }

        int id = (int) jTableItems.getModel().getValueAt(row, 0);
        byte vote = (byte) votebox.getSelectedItem();

        Item item = this.ctrl.getStore().getItem(id);
        if (item != null && item instanceof Supply) {
            try {
                ((Supply) item).getSupplier().vote(vote, item.getId(), this.ctrl.getCurUser().getUsername());
            } catch (Message m) {
                JOptionPane.showMessageDialog(this, m, "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "You cannot vote this item", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Thanks for the feedback!");
    }

    /**
     * Load items.
     */
    private void loadItems() {
        DefaultTableModel model = (DefaultTableModel) jTableItems.getModel();
        model.getDataVector().removeAllElements();
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            ArrayList<Order.ItemStack> items = order.getItems();
            for (Order.ItemStack i : items) {
                model.addRow(new Object[]{i.item.getId(), i.quantity, i.item.getPrice(), i.getFinalPrice()});
            }
        }

        model.fireTableDataChanged();

        jTextTotalPrice.setText(String.valueOf(order.getTotalPrice(true)));
    }

    /**
     * Delete item.
     */
    protected void deleteItem() {
        int i = jTableItems.getSelectedRow();
        if (i < 0) {
            return;
        }

        int id = (int) jTableItems.getModel().getValueAt(i, 0);

        this.order.removeItem(id, false);
        loadItems();
    }

    /**
     * Delete order.
     */
    protected void deleteOrder() {
        this.ctrl.getCurUser().getCurOrder().reset(false);
        dispose();
    }

    /**
     * Pay order.
     */
    protected void payOrder() {
        try {
            this.ctrl.getStore().payOrder();
            JOptionPane.showMessageDialog(this, "Your order has been confirmed!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
