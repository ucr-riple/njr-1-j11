/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.cli;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Callable;

import com.jdeals.main.controller.Store;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.user.User;
import com.jdeals.main.helper.sort.ItemDateSort;
import com.jdeals.main.helper.sort.ItemIndexSort;
import com.jdeals.main.helper.sort.SortingClass.SortingDirection;

/**
 * The Class UserMenu.
 */
public class UserMenu extends ViewCli {

    /**
     * Instantiates a new user menu.
     *
     * @param sysCtrl the sys ctrl
     */
    public UserMenu(JDealsController sysCtrl) {
        super("User Menu", sysCtrl);

        this.addItem("Buy Credits",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return BuyCredit();
                    }
                }
        );

        this.addItem("Show Profile",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return ShowProfile();
                    }
                }
        );

        this.addItem("List current offers - by Date",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return ListOffer(new ItemDateSort(SortingDirection.DECR));
                    }
                }
        );

        this.addItem("List current offers - by Id",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return ListOffer(new ItemIndexSort(SortingDirection.DECR));
                    }
                }
        );

        this.addItem("Buy Now",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return BuyItem();
                    }
                }
        );

        this.addItem("Orders History",
                new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return OrderHistory();
                    }
                }
        );
    }

    /**
     * Order history.
     *
     * @return true, if successful
     */
    public boolean OrderHistory() {
        for (Order order : this.getSysCtrl().getCurUser().getOrders()) {
            System.out.println(order);
        }

        return true;
    }

    /**
     * Buy item.
     *
     * @return true, if successful
     */
    public boolean BuyItem() {
        System.out.println("Item id to buy:");
        int id = JDealsController.scan.nextInt();
        JDealsController.scan.nextLine();

        Item item = this.getSysCtrl().getStore().getItem(id);
        if (item != null && item.isAvailable()) {
            System.out.println("Quantity:");
            int quantity = JDealsController.scan.nextInt();
            JDealsController.scan.nextLine();

            try {
                Store store = this.getSysCtrl().getStore();
                store.addItemToCart(item, quantity);
                store.payOrder();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Item " + id + " is not avaible!");
        }

        return false;
    }

    /**
     * Buy credit.
     *
     * @return true, if successful
     */
    public boolean BuyCredit() {
        System.out.println("Credit to buy:");
        double credit = JDealsController.scan.nextDouble();
        JDealsController.scan.nextLine();

        this.getSysCtrl().getCurUser().modCredit(credit);
        return true;
    }

    /**
     * Show profile.
     *
     * @return true, if successful
     */
    public boolean ShowProfile() {
        User user = this.getSysCtrl().getCurUser();
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("City: " + user.getCity());
        System.out.println("Current credit: " + user.getCredit());
        return true;
    }

    /**
     * List offer.
     *
     * @param sortPolicy the sort policy
     * @return true, if successful
     */
    public boolean ListOffer(Comparator sortPolicy) {
        ArrayList<Item> list = this.getSysCtrl().getStore().getFilteredItems(false, sortPolicy);
        for (Item item : list) {
            System.out.println("\n===========\n" + item);
        }
        System.out.println("Press enter to continue...");
        JDealsController.scan.nextLine();
        return true;
    }
}
