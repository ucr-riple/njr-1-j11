/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jdeals.libs.JTextFieldFilter;
import com.jdeals.libs.MyDate;
import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.catalogue.GeneralGood;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.catalogue.Restourant;
import com.jdeals.main.entity.catalogue.Service;
import com.jdeals.main.entity.catalogue.Travel;
import com.jdeals.main.entity.user.Manager;

/**
 * The Class JDialogAddItem.
 */
public class JDialogAddItem extends JDialog {

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * The parent.
     */
    private Window parent;

    /**
     * The j text rest name.
     */
    private JTextField jTextRestName;

    /**
     * The j text rest descr.
     */
    private JTextField jTextRestDescr;

    /**
     * The j text rest price.
     */
    private JTextField jTextRestPrice;

    /**
     * The j text rest quantity.
     */
    private JTextField jTextRestQuantity;

    /**
     * The j text rest end date.
     */
    private JFormattedTextField jTextRestEndDate;

    /**
     * The j text rest location.
     */
    private JTextField jTextRestLocation;

    /**
     * The j text travel loc.
     */
    private JTextField jTextTravelLoc;

    /**
     * The j text travel price.
     */
    private JTextField jTextTravelPrice;

    /**
     * The j text travel dep date.
     */
    private JFormattedTextField jTextTravelDepDate;

    /**
     * The j text travel exp date.
     */
    private JFormattedTextField jTextTravelExpDate;

    /**
     * The j text goods descr.
     */
    private JTextField jTextGoodsDescr;

    /**
     * The j text goods price.
     */
    private JTextField jTextGoodsPrice;

    /**
     * The j text goods quantity.
     */
    private JTextField jTextGoodsQuantity;

    /**
     * The j text service descr.
     */
    private JTextField jTextServiceDescr;

    /**
     * The j text service price.
     */
    private JTextField jTextServicePrice;

    /**
     * The j text service location.
     */
    private JTextField jTextServiceLocation;

    /**
     * The j panel center.
     */
    private JPanel jPanelCenter;

    /**
     * The j cb item type.
     */
    private JComboBox jCbItemType;

    /**
     * The Enum CatItems.
     */
    public enum CatItems {

        /**
         * The restourant.
         */
        RESTOURANT("Restourant"),
        /**
         * The travel.
         */
        TRAVEL("Travel"),
        /**
         * The goods.
         */
        GOODS("General Goods"),
        /**
         * The service.
         */
        SERVICE("Service");

        /**
         * The name.
         */
        private String name;

        /**
         * Instantiates a new cat items.
         *
         * @param name the name
         */
        private CatItems(String name) {
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
     * Instantiates a new j dialog add item.
     *
     * @see JDealsController
     * @see Item
     * @param ctrl the ctrl
     * @param parent the parent
     */
    public JDialogAddItem(JDealsController ctrl, Window parent) {
        super(parent);
        this.ctrl = ctrl;
        this.parent = parent;
        this.setSize(300, 500);
        this.setMinimumSize(this.getSize());
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel jPanelMain = new JPanel();
        getContentPane().add(jPanelMain, BorderLayout.CENTER);
        jPanelMain.setLayout(new BorderLayout(0, 0));

        JPanel jPanelTop = new JPanel();
        jPanelMain.add(jPanelTop, BorderLayout.NORTH);

        Object[] values = this.ctrl.isManagerSession(true)
                ? CatItems.values() : new Object[]{CatItems.GOODS, CatItems.SERVICE};

        jCbItemType = new JComboBox(values);
        jCbItemType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePanel();
            }
        });
        jPanelTop.add(jCbItemType);

        jPanelCenter = new JPanel();
        jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
        jPanelCenter.setLayout(new CardLayout(0, 0));

        JPanel jPanelRestourant = new JPanel();
        jPanelCenter.add(jPanelRestourant, CatItems.RESTOURANT.toString());
        GridBagLayout gbl_jPanelRestourant = new GridBagLayout();
        gbl_jPanelRestourant.columnWidths = new int[]{0, 0};
        gbl_jPanelRestourant.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_jPanelRestourant.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelRestourant.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        jPanelRestourant.setLayout(gbl_jPanelRestourant);

        JLabel label = new JLabel("Name");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        jPanelRestourant.add(label, gbc_label);

        jTextRestName = new JTextField();
        jTextRestName.setColumns(10);
        GridBagConstraints gbc_jTextRestName = new GridBagConstraints();
        gbc_jTextRestName.insets = new Insets(0, 0, 5, 0);
        gbc_jTextRestName.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestName.gridx = 0;
        gbc_jTextRestName.gridy = 1;
        jPanelRestourant.add(jTextRestName, gbc_jTextRestName);

        JLabel label_1 = new JLabel("Description");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 0, 5, 0);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 2;
        jPanelRestourant.add(label_1, gbc_label_1);

        jTextRestDescr = new JTextField();
        jTextRestDescr.setColumns(10);
        GridBagConstraints gbc_jTextRestDescr = new GridBagConstraints();
        gbc_jTextRestDescr.insets = new Insets(0, 0, 5, 0);
        gbc_jTextRestDescr.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestDescr.gridx = 0;
        gbc_jTextRestDescr.gridy = 3;
        jPanelRestourant.add(jTextRestDescr, gbc_jTextRestDescr);

        JLabel label_2 = new JLabel("Price");
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.insets = new Insets(0, 0, 5, 0);
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 4;
        jPanelRestourant.add(label_2, gbc_label_2);

        jTextRestPrice = new JTextField();
        jTextRestPrice.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        jTextRestPrice.setColumns(10);
        GridBagConstraints gbc_jTextRestPrice = new GridBagConstraints();
        gbc_jTextRestPrice.insets = new Insets(0, 0, 5, 0);
        gbc_jTextRestPrice.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestPrice.gridx = 0;
        gbc_jTextRestPrice.gridy = 5;
        jPanelRestourant.add(jTextRestPrice, gbc_jTextRestPrice);

        JLabel label_3 = new JLabel("Quantity");
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.insets = new Insets(0, 0, 5, 0);
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 6;
        jPanelRestourant.add(label_3, gbc_label_3);

        jTextRestQuantity = new JTextField();
        jTextRestQuantity.setColumns(10);
        jTextRestQuantity.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jTextRestQuantity = new GridBagConstraints();
        gbc_jTextRestQuantity.insets = new Insets(0, 0, 5, 0);
        gbc_jTextRestQuantity.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestQuantity.gridx = 0;
        gbc_jTextRestQuantity.gridy = 7;
        jPanelRestourant.add(jTextRestQuantity, gbc_jTextRestQuantity);

        JLabel label_4 = new JLabel("End date");
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.insets = new Insets(0, 0, 5, 0);
        gbc_label_4.gridx = 0;
        gbc_label_4.gridy = 8;
        jPanelRestourant.add(label_4, gbc_label_4);

        jTextRestEndDate = new JFormattedTextField(Tools.formattedMask(MyDate.defMask));
        jTextRestEndDate.setColumns(10);
        GridBagConstraints gbc_jTextRestEndDate = new GridBagConstraints();
        gbc_jTextRestEndDate.insets = new Insets(0, 0, 5, 0);
        gbc_jTextRestEndDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestEndDate.gridx = 0;
        gbc_jTextRestEndDate.gridy = 9;
        jPanelRestourant.add(jTextRestEndDate, gbc_jTextRestEndDate);

        JLabel label_5 = new JLabel("Location");
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 5, 0);
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 10;
        jPanelRestourant.add(label_5, gbc_label_5);

        jTextRestLocation = new JTextField();
        jTextRestLocation.setColumns(10);
        GridBagConstraints gbc_jTextRestLocation = new GridBagConstraints();
        gbc_jTextRestLocation.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextRestLocation.gridx = 0;
        gbc_jTextRestLocation.gridy = 11;
        jPanelRestourant.add(jTextRestLocation, gbc_jTextRestLocation);

        JPanel jPanelTravel = new JPanel();
        jPanelCenter.add(jPanelTravel, CatItems.TRAVEL.toString());
        GridBagLayout gbl_jPanelTravel = new GridBagLayout();
        gbl_jPanelTravel.columnWidths = new int[]{0, 0};
        gbl_jPanelTravel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_jPanelTravel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelTravel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        jPanelTravel.setLayout(gbl_jPanelTravel);

        JLabel lblLocation = new JLabel("Location");
        GridBagConstraints gbc_lblLocation = new GridBagConstraints();
        gbc_lblLocation.insets = new Insets(0, 0, 5, 0);
        gbc_lblLocation.gridx = 0;
        gbc_lblLocation.gridy = 0;
        jPanelTravel.add(lblLocation, gbc_lblLocation);

        jTextTravelLoc = new JTextField();
        jTextTravelLoc.setColumns(10);
        GridBagConstraints gbc_jTextTravelLoc = new GridBagConstraints();
        gbc_jTextTravelLoc.insets = new Insets(0, 0, 5, 0);
        gbc_jTextTravelLoc.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextTravelLoc.gridx = 0;
        gbc_jTextTravelLoc.gridy = 1;
        jPanelTravel.add(jTextTravelLoc, gbc_jTextTravelLoc);

        JLabel label_8 = new JLabel("Price");
        GridBagConstraints gbc_label_8 = new GridBagConstraints();
        gbc_label_8.insets = new Insets(0, 0, 5, 0);
        gbc_label_8.gridx = 0;
        gbc_label_8.gridy = 2;
        jPanelTravel.add(label_8, gbc_label_8);

        jTextTravelPrice = new JTextField();
        jTextTravelPrice.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        jTextTravelPrice.setColumns(10);
        GridBagConstraints gbc_jTextTravelPrice = new GridBagConstraints();
        gbc_jTextTravelPrice.insets = new Insets(0, 0, 5, 0);
        gbc_jTextTravelPrice.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextTravelPrice.gridx = 0;
        gbc_jTextTravelPrice.gridy = 3;
        jPanelTravel.add(jTextTravelPrice, gbc_jTextTravelPrice);

        JLabel lblDepartureDate = new JLabel("Departure date");
        GridBagConstraints gbc_lblDepartureDate = new GridBagConstraints();
        gbc_lblDepartureDate.insets = new Insets(0, 0, 5, 0);
        gbc_lblDepartureDate.gridx = 0;
        gbc_lblDepartureDate.gridy = 4;
        jPanelTravel.add(lblDepartureDate, gbc_lblDepartureDate);

        jTextTravelDepDate = new JFormattedTextField(Tools.formattedMask(MyDate.defMask));
        jTextTravelDepDate.setColumns(10);
        GridBagConstraints gbc_jTextTravelDepDate = new GridBagConstraints();
        gbc_jTextTravelDepDate.insets = new Insets(0, 0, 5, 0);
        gbc_jTextTravelDepDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextTravelDepDate.gridx = 0;
        gbc_jTextTravelDepDate.gridy = 5;
        jPanelTravel.add(jTextTravelDepDate, gbc_jTextTravelDepDate);

        JLabel lblExpiryDate = new JLabel("Expiry date");
        GridBagConstraints gbc_lblExpiryDate = new GridBagConstraints();
        gbc_lblExpiryDate.insets = new Insets(0, 0, 5, 0);
        gbc_lblExpiryDate.gridx = 0;
        gbc_lblExpiryDate.gridy = 6;
        jPanelTravel.add(lblExpiryDate, gbc_lblExpiryDate);

        jTextTravelExpDate = new JFormattedTextField(Tools.formattedMask(MyDate.defMask));
        jTextTravelExpDate.setColumns(10);
        GridBagConstraints gbc_jTextTravelExpDate = new GridBagConstraints();
        gbc_jTextTravelExpDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextTravelExpDate.gridx = 0;
        gbc_jTextTravelExpDate.gridy = 7;
        jPanelTravel.add(jTextTravelExpDate, gbc_jTextTravelExpDate);

        JPanel jPanelGoods = new JPanel();
        jPanelCenter.add(jPanelGoods, CatItems.GOODS.toString());
        GridBagLayout gbl_jPanelGoods = new GridBagLayout();
        gbl_jPanelGoods.columnWidths = new int[]{0, 0};
        gbl_jPanelGoods.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_jPanelGoods.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelGoods.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        jPanelGoods.setLayout(gbl_jPanelGoods);

        JLabel label_13 = new JLabel("Description");
        GridBagConstraints gbc_label_13 = new GridBagConstraints();
        gbc_label_13.insets = new Insets(0, 0, 5, 0);
        gbc_label_13.gridx = 0;
        gbc_label_13.gridy = 0;
        jPanelGoods.add(label_13, gbc_label_13);

        jTextGoodsDescr = new JTextField();
        jTextGoodsDescr.setColumns(10);
        GridBagConstraints gbc_jTextGoodsDescr = new GridBagConstraints();
        gbc_jTextGoodsDescr.insets = new Insets(0, 0, 5, 0);
        gbc_jTextGoodsDescr.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextGoodsDescr.gridx = 0;
        gbc_jTextGoodsDescr.gridy = 1;
        jPanelGoods.add(jTextGoodsDescr, gbc_jTextGoodsDescr);

        JLabel label_14 = new JLabel("Price");
        GridBagConstraints gbc_label_14 = new GridBagConstraints();
        gbc_label_14.insets = new Insets(0, 0, 5, 0);
        gbc_label_14.gridx = 0;
        gbc_label_14.gridy = 2;
        jPanelGoods.add(label_14, gbc_label_14);

        jTextGoodsPrice = new JTextField();
        jTextGoodsPrice.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        jTextGoodsPrice.setColumns(10);
        GridBagConstraints gbc_jTextGoodsPrice = new GridBagConstraints();
        gbc_jTextGoodsPrice.insets = new Insets(0, 0, 5, 0);
        gbc_jTextGoodsPrice.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextGoodsPrice.gridx = 0;
        gbc_jTextGoodsPrice.gridy = 3;
        jPanelGoods.add(jTextGoodsPrice, gbc_jTextGoodsPrice);

        JLabel label_15 = new JLabel("Quantity");
        GridBagConstraints gbc_label_15 = new GridBagConstraints();
        gbc_label_15.insets = new Insets(0, 0, 5, 0);
        gbc_label_15.gridx = 0;
        gbc_label_15.gridy = 4;
        jPanelGoods.add(label_15, gbc_label_15);

        jTextGoodsQuantity = new JTextField();
        jTextGoodsQuantity.setColumns(10);
        jTextGoodsQuantity.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        GridBagConstraints gbc_jTextGoodsQuantity = new GridBagConstraints();
        gbc_jTextGoodsQuantity.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextGoodsQuantity.gridx = 0;
        gbc_jTextGoodsQuantity.gridy = 5;
        jPanelGoods.add(jTextGoodsQuantity, gbc_jTextGoodsQuantity);

        JPanel JPanelService = new JPanel();
        jPanelCenter.add(JPanelService, CatItems.SERVICE.toString());
        GridBagLayout gbl_JPanelService = new GridBagLayout();
        gbl_JPanelService.columnWidths = new int[]{0, 0};
        gbl_JPanelService.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_JPanelService.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_JPanelService.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        JPanelService.setLayout(gbl_JPanelService);

        JLabel label_19 = new JLabel("Description");
        GridBagConstraints gbc_label_19 = new GridBagConstraints();
        gbc_label_19.insets = new Insets(0, 0, 5, 0);
        gbc_label_19.gridx = 0;
        gbc_label_19.gridy = 0;
        JPanelService.add(label_19, gbc_label_19);

        jTextServiceDescr = new JTextField();
        jTextServiceDescr.setColumns(10);
        GridBagConstraints gbc_jTextServiceDescr = new GridBagConstraints();
        gbc_jTextServiceDescr.insets = new Insets(0, 0, 5, 0);
        gbc_jTextServiceDescr.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextServiceDescr.gridx = 0;
        gbc_jTextServiceDescr.gridy = 1;
        JPanelService.add(jTextServiceDescr, gbc_jTextServiceDescr);

        JLabel label_20 = new JLabel("Price");
        GridBagConstraints gbc_label_20 = new GridBagConstraints();
        gbc_label_20.insets = new Insets(0, 0, 5, 0);
        gbc_label_20.gridx = 0;
        gbc_label_20.gridy = 2;
        JPanelService.add(label_20, gbc_label_20);

        jTextServicePrice = new JTextField();
        jTextServicePrice.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        jTextServicePrice.setColumns(10);
        GridBagConstraints gbc_jTextServicePrice = new GridBagConstraints();
        gbc_jTextServicePrice.insets = new Insets(0, 0, 5, 0);
        gbc_jTextServicePrice.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextServicePrice.gridx = 0;
        gbc_jTextServicePrice.gridy = 3;
        JPanelService.add(jTextServicePrice, gbc_jTextServicePrice);

        JLabel label_23 = new JLabel("Location");
        GridBagConstraints gbc_label_23 = new GridBagConstraints();
        gbc_label_23.insets = new Insets(0, 0, 5, 0);
        gbc_label_23.gridx = 0;
        gbc_label_23.gridy = 4;
        JPanelService.add(label_23, gbc_label_23);

        jTextServiceLocation = new JTextField();
        jTextServiceLocation.setColumns(10);
        GridBagConstraints gbc_jTextServiceLocation = new GridBagConstraints();
        gbc_jTextServiceLocation.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextServiceLocation.gridx = 0;
        gbc_jTextServiceLocation.gridy = 5;
        JPanelService.add(jTextServiceLocation, gbc_jTextServiceLocation);

        JPanel jPanelBottom = new JPanel();
        jPanelMain.add(jPanelBottom, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        jPanelBottom.add(btnAdd);

        this.setVisible(true);
    }

    /**
     * Adds the item.
     */
    protected void addItem() {
        Item item = null;
        CatItems sel = (CatItems) jCbItemType.getSelectedItem();
        switch (sel) {
            case GOODS:
                String gDescr = jTextGoodsDescr.getText();
                double gPrice = Double.valueOf(jTextGoodsPrice.getText());
                int gAvaibleItems = Integer.valueOf(jTextGoodsQuantity.getText());

                item = new GeneralGood(gDescr, gPrice, 0, gAvaibleItems, ((Manager) this.ctrl.getCurUser()));
                break;
            case RESTOURANT:
                String rDescr = jTextRestDescr.getText();
                String rName = jTextRestName.getText();
                String rLocation = jTextRestLocation.getText();
                MyDate rExpiryDate;
                try {
                    rExpiryDate = new MyDate().fromString(jTextRestEndDate.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double rPrice = Double.valueOf(jTextRestPrice.getText());
                int rAvailableEvents = Integer.valueOf(jTextRestQuantity.getText());

                item = new Restourant(rName, rDescr, rPrice, 0, rExpiryDate, rLocation, rAvailableEvents);
                break;
            case SERVICE:
                String sDescr = jTextServiceDescr.getText();
                double sPrice = Double.valueOf(jTextServicePrice.getText());
                String sLocation = jTextServiceLocation.getText();

                item = new Service(sDescr, sLocation, sPrice, 0, ((Manager) this.ctrl.getCurUser()));
                break;
            case TRAVEL:
                double tPrice = Double.valueOf(jTextTravelPrice.getText());
                MyDate tExpiryDate;
                MyDate tDepartureDate;
                try {
                    tExpiryDate = new MyDate().fromString(jTextTravelExpDate.getText());
                    tDepartureDate = new MyDate().fromString(jTextTravelDepDate.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String tLocation = jTextTravelLoc.getText();

                item = new Travel(tPrice, 0, tExpiryDate, tLocation, tDepartureDate);
                break;
        }

        try {
            this.ctrl.getStore().addItem(item);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
    }

    /**
     * Change panel.
     */
    protected void changePanel() {
        CardLayout cl = (CardLayout) (jPanelCenter.getLayout());
        cl.show(jPanelCenter, jCbItemType.getSelectedItem().toString());
    }
}
