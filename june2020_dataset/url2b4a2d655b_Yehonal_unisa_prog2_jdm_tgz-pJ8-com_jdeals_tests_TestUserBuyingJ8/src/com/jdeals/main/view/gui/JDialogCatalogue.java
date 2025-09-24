/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import com.jdeals.libs.MyDate;
import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.Order.ItemStack;
import com.jdeals.main.entity.catalogue.GeneralGood;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.catalogue.Restourant;
import com.jdeals.main.entity.catalogue.Supply;
import com.jdeals.main.entity.user.User;
import com.jdeals.main.helper.filter.FilterByDateRange;
import com.jdeals.main.helper.filter.FilterByMinScore;
import com.jdeals.main.helper.filter.Predicate;
import com.jdeals.main.helper.sort.ItemDateSort;
import com.jdeals.main.helper.sort.ItemIndexSort;
import com.jdeals.main.helper.sort.SortingClass;
import com.jdeals.main.helper.sort.SortingClass.SortingDirection;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;

/**
 * The Class JDialogCatalogue.
 */
public class JDialogCatalogue extends JDialog {

    /**
     * The Enum CatalogueOrd.
     */
    public enum CatalogueOrd {

        /**
         * The by date.
         */
        BY_DATE("By Date", new ItemDateSort(SortingDirection.CRESC)),
        /**
         * The by id.
         */
        BY_ID("By ID", new ItemIndexSort(SortingDirection.CRESC));

        /**
         * The val.
         */
        private String val;

        /**
         * The c.
         */
        private SortingClass<Item> c;

        /**
         * Instantiates a new catalogue ord.
         *
         * @param val the val
         * @param c the c
         */
        private CatalogueOrd(String val, SortingClass<Item> c) {
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
        public SortingClass<Item> getComparator() {
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
     * The j panel content.
     */
    private JPanel jPanelContent;

    /**
     * The combo box.
     */
    private JComboBox comboBox;

    /**
     * The j cb direction.
     */
    private JComboBox jCbDirection;

    /**
     * The j feed back value.
     */
    private JComboBox jFeedBackValue;

    /**
     * The btn find score.
     */
    private JButton btnFindScore;

    /**
     * The predicate.
     */
    private Predicate predicate = null;

    /**
     * The end date text.
     */
    private JFormattedTextField endDateText;

    /**
     * The start date text.
     */
    private JTextField startDateText;

    /**
     * The btn clear.
     */
    private JButton btnClear;

    /**
     * The j panel center.
     */
    private JPanel jPanelCenter;

    /**
     * Instantiates a new j dialog catalogue.
     *
     * @param ctrl the ctrl
     */
    public JDialogCatalogue(JDealsController ctrl) {
        super(ctrl.getFrame());
        this.ctrl = ctrl;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setSize(640, 480);

        //CENTER
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        JPanel jPanelMain = new JPanel();
        jPanelMain.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        getContentPane().add(jPanelMain, BorderLayout.CENTER);
        jPanelMain.setLayout(new BorderLayout(0, 0));

        /**
         * TOP BAR
         */
        JPanel jPanelTopBar = new JPanel();
        jPanelMain.add(jPanelTopBar, BorderLayout.NORTH);
        GridBagLayout gbl_jPanelTopBar = new GridBagLayout();
        gbl_jPanelTopBar.columnWidths = new int[]{86, 82, 0, 112, 107, 87, 0, 92, 0};
        gbl_jPanelTopBar.rowHeights = new int[]{37, 25, 0, 0};
        gbl_jPanelTopBar.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_jPanelTopBar.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        jPanelTopBar.setLayout(gbl_jPanelTopBar);

        JButton btnMyCart = new JButton("My cart");
        btnMyCart.setIcon(new ImageIcon("res/cart.png"));
        btnMyCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCart();
            }
        });

        btnFindScore = new JButton("Find");
        btnFindScore.setIcon(new ImageIcon("res/find.png"));
        btnFindScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterByScore();
            }
        });

        comboBox = new JComboBox(CatalogueOrd.values());
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadItems();
            }
        });

        JLabel jLblSort = new JLabel("Sort By:");
        GridBagConstraints gbc_jLblSort = new GridBagConstraints();
        gbc_jLblSort.anchor = GridBagConstraints.SOUTHEAST;
        gbc_jLblSort.insets = new Insets(0, 0, 5, 5);
        gbc_jLblSort.gridx = 0;
        gbc_jLblSort.gridy = 0;
        jPanelTopBar.add(jLblSort, gbc_jLblSort);
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.anchor = GridBagConstraints.SOUTH;
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        jPanelTopBar.add(comboBox, gbc_comboBox);

        jFeedBackValue = new JComboBox(new Byte[]{0, 1, 2, 3, 4});
        GridBagConstraints gbc_jFeedBackValue = new GridBagConstraints();
        gbc_jFeedBackValue.anchor = GridBagConstraints.SOUTH;
        gbc_jFeedBackValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jFeedBackValue.insets = new Insets(0, 0, 5, 5);
        gbc_jFeedBackValue.gridx = 4;
        gbc_jFeedBackValue.gridy = 0;
        jPanelTopBar.add(jFeedBackValue, gbc_jFeedBackValue);
        GridBagConstraints gbc_btnFindScore = new GridBagConstraints();
        gbc_btnFindScore.anchor = GridBagConstraints.SOUTH;
        gbc_btnFindScore.insets = new Insets(0, 0, 5, 5);
        gbc_btnFindScore.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnFindScore.gridx = 5;
        gbc_btnFindScore.gridy = 0;
        jPanelTopBar.add(btnFindScore, gbc_btnFindScore);

        GridBagConstraints gbc_btnMyCart = new GridBagConstraints();
        gbc_btnMyCart.insets = new Insets(0, 0, 5, 0);
        gbc_btnMyCart.fill = GridBagConstraints.BOTH;
        gbc_btnMyCart.gridx = 7;
        gbc_btnMyCart.gridy = 0;
        jPanelTopBar.add(btnMyCart, gbc_btnMyCart);

        jCbDirection = new JComboBox(SortingDirection.values());
        jCbDirection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadItems();
            }
        });
        GridBagConstraints gbc_jCbDirection = new GridBagConstraints();
        gbc_jCbDirection.fill = GridBagConstraints.BOTH;
        gbc_jCbDirection.insets = new Insets(0, 0, 5, 5);
        gbc_jCbDirection.gridx = 1;
        gbc_jCbDirection.gridy = 1;
        jPanelTopBar.add(jCbDirection, gbc_jCbDirection);

        startDateText = new JFormattedTextField(Tools.formattedMask(MyDate.defMask));
        GridBagConstraints gbc_startDateText = new GridBagConstraints();
        gbc_startDateText.fill = GridBagConstraints.BOTH;
        gbc_startDateText.insets = new Insets(0, 0, 5, 5);
        gbc_startDateText.gridx = 3;
        gbc_startDateText.gridy = 1;
        jPanelTopBar.add(startDateText, gbc_startDateText);

        endDateText = new JFormattedTextField(Tools.formattedMask(MyDate.defMask));
        //endDateText.setDocument(new JTextFieldFilter(JTextFieldFilter.DATE));
        GridBagConstraints gbc_endDateText = new GridBagConstraints();
        gbc_endDateText.fill = GridBagConstraints.BOTH;
        gbc_endDateText.insets = new Insets(0, 0, 5, 5);
        gbc_endDateText.gridx = 4;
        gbc_endDateText.gridy = 1;
        jPanelTopBar.add(endDateText, gbc_endDateText);

        JButton btnFindDate = new JButton("Find");
        btnFindDate.setIcon(new ImageIcon("res/find.png"));
        btnFindDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterByDate();
            }
        });
        GridBagConstraints gbc_btnFindDate = new GridBagConstraints();
        gbc_btnFindDate.fill = GridBagConstraints.BOTH;
        gbc_btnFindDate.insets = new Insets(0, 0, 5, 5);
        gbc_btnFindDate.gridx = 5;
        gbc_btnFindDate.gridy = 1;
        jPanelTopBar.add(btnFindDate, gbc_btnFindDate);

        btnClear = new JButton("Clear Filters");
        btnClear.setIcon(new ImageIcon("res/stock-edit-clear-16.png"));
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
        GridBagConstraints gbc_btnClear = new GridBagConstraints();
        gbc_btnClear.insets = new Insets(0, 0, 5, 0);
        gbc_btnClear.fill = GridBagConstraints.BOTH;
        gbc_btnClear.gridx = 7;
        gbc_btnClear.gridy = 1;
        jPanelTopBar.add(btnClear, gbc_btnClear);

        /*
         * CONTENT
         */
        jPanelCenter = new JPanel();
        jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
        jPanelCenter.setLayout(new GridLayout(0, 1, 0, 0));
        this.jPanelContent = new JPanel(new GridLayout(0, 2, 5, 5));
        JScrollPane scrollPane = new JScrollPane(this.jPanelContent);
        scrollPane.setMaximumSize(new Dimension(600, 600));
        jPanelCenter.add(scrollPane);

        loadItems();

        /**
         * BOTTOM BAR
         */
        JPanel jPanelBottom = new JPanel();
        jPanelMain.add(jPanelBottom, BorderLayout.SOUTH);
        jPanelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        if (this.ctrl.isManagerSession(false)) {
            JButton jBtnAddItem = new JButton("Add Item");
            jBtnAddItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openAddItem();
                }
            });
            jPanelBottom.add(jBtnAddItem);
        }

        //Tools.fixMinSize(this, true);
        this.setVisible(true);
    }

    /**
     * Clear filters.
     */
    protected void clearFilters() {
        startDateText.setText(MyDate.defFormat);
        endDateText.setText(MyDate.defFormat);
        jFeedBackValue.setSelectedIndex(0);
        this.predicate = null;
        loadItems();
    }

    /**
     * Open the add item.
     */
    protected void openAddItem() {
        new JDialogAddItem(this.ctrl, this);
        loadItems();
    }

    /**
     * Filter by score.
     */
    protected void filterByScore() {
        this.predicate = new FilterByMinScore((Byte) jFeedBackValue.getSelectedItem());
        loadItems();
    }

    /**
     * Filter by date.
     */
    protected void filterByDate() {
        if (startDateText.getText().equals(MyDate.defFormat)
                || endDateText.getText().equals(MyDate.defFormat)) {
            this.predicate = null;
        } else {
            try {
                this.predicate = new FilterByDateRange(
                        new MyDate().fromString(startDateText.getText()),
                        new MyDate().fromString(endDateText.getText())
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        loadItems();
    }

    /**
     * Load items.
     *
     * @see User
     * @see Item
     * @param sort the sort
     */
    private void loadItems(SortingClass<Item> sort) {
        this.jPanelContent.removeAll();
        this.jPanelContent.repaint();
        ArrayList<Item> items = ctrl.getStore().getFilteredItems(false, sort, this.predicate);

        if (items == null || items.isEmpty()) {
            return;
        }

        for (final Item item : items) {
            if (item == null) {
                continue;
            }
            JPanel jPanelItem = new JPanel();
            jPanelItem.setLayout(new BorderLayout());
            jPanelItem.setSize(200, 200);
            //TODO find better way to show correct quantity
            Item clone = item;
            User cUser = this.ctrl.getCurUser();
            Order cOrder = cUser.getCurOrder();
            ItemStack is = cOrder.getItem(item.getId());
            if (is != null) {
                try {
                    clone = item.clone();
                    clone.modSold(is.quantity);
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }

            JTextArea jTxtItem = new JTextArea(clone.toString(false, true));
            jTxtItem.setEditable(false);
            jPanelItem.add(jTxtItem, BorderLayout.CENTER);

            JPanel jPanelItemBtn = new JPanel();
            jPanelItemBtn.setLayout(new BoxLayout(jPanelItemBtn, BoxLayout.X_AXIS));
            jPanelItem.add(jPanelItemBtn, BorderLayout.SOUTH);

            boolean isOwnItem = (item instanceof Supply && ((Supply) item).getSupplier().equals(this.ctrl.getCurUser()));

            if (this.ctrl.isManagerSession(true) || isOwnItem) {
                JButton jBtnDel = new JButton("Delete");
                jBtnDel.setIcon(new ImageIcon("res/delete.gif"));
                jBtnDel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteItem(item);
                    }
                });
                jPanelItemBtn.add(jBtnDel);
            }

            int max = 1000;
            if (this.ctrl.getSettings().isExtrasEnabled()) {
                if (item instanceof GeneralGood) {
                    max = ((GeneralGood) clone).getAvailableItems();
                } else if (item instanceof Restourant) {
                    max = ((Restourant) clone).getAvailableItems();
                }
            }

            if (!isOwnItem || ctrl.isManagerSession(true)) {
                final JSpinner jQuantity = new JSpinner(new SpinnerNumberModel(1, 1, max, 1));
                jPanelItemBtn.add(jQuantity);

                JButton jBtnBuy = new JButton("Buy");
                jBtnBuy.setIcon(new ImageIcon("res/dollar.png"));
                jBtnBuy.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buyItem(item, (int) jQuantity.getValue());
                    }
                });
                jPanelItemBtn.add(jBtnBuy);
            }

            this.jPanelContent.add(jPanelItem);
        }

        this.jPanelContent.revalidate();
    }

    /**
     * Load items.
     */
    protected void loadItems() {
        CatalogueOrd val = (CatalogueOrd) comboBox.getSelectedItem();
        ArrayList<Item> items = this.ctrl.getStore().getItems();
        val.getComparator().setDirection((SortingDirection) jCbDirection.getSelectedItem());
        loadItems(val.getComparator());
    }

    /**
     * Delete item.
     *
     * @param item the item
     */
    protected void deleteItem(Item item) {
        this.ctrl.getStore().delItem(item.getId());
        loadItems();
    }

    /**
     * Buy item.
     *
     * @param item the item
     * @param quantity the quantity
     */
    protected void buyItem(Item item, int quantity) {
        try {
            if (this.ctrl.getStore().addItemToCart(item.getId(), quantity)) {
                JOptionPane.showMessageDialog(this, "Item with id: " + item.getId() + " added to Cart!");
            }
            loadItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            loadItems();
        }
    }

    /**
     * Open the cart.
     */
    protected void openCart() {
        if (this.ctrl.getCurUser().getCurOrder() != null) {
            new JDialogOrder(this.ctrl, this.ctrl.getCurUser().getCurOrder(), true);
        }
        // RELOAD ITEMS AFTER DISPOSE MODAL DIALOG
        loadItems();
    }

}
