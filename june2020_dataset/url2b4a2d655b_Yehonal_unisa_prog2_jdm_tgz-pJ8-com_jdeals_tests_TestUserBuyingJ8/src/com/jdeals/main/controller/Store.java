/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.Order.ItemStack;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.catalogue.Restourant;
import com.jdeals.main.entity.user.User;
import com.jdeals.main.helper.QAvailability;
import com.jdeals.main.helper.discount.DiscountInterface;
import com.jdeals.main.helper.filter.Filter;
import com.jdeals.main.helper.filter.Predicate;
import com.jdeals.main.helper.storage.DataBaseInterface;

/**
 * This class is the dataset that store all items and discounts providing all
 * methods to handle the cart, sell the items , requesting price calculation
 * etc.
 *
 * @author Giuseppe Ronca
 *
 */
public class Store {

    /**
     * The items.
     */
    private ArrayList<Item> items;

    /**
     * The discounts.
     */
    private ArrayList<DiscountInterface<Object>> discounts;

    /**
     * The main ctrl.
     */
    private final JDealsController mainCtrl;

    /**
     * Default constructor.
     *
     * @param mainCtrl the instance of JDealsController
     * @see JDealsController
     */
    public Store(JDealsController mainCtrl) {
        this.mainCtrl = mainCtrl;
        items = new ArrayList<>();
        discounts = new ArrayList<>();
    }

    /**
     * Saves store data in db.
     *
     * @param storer DataBaseInterface manager
     * @see DataBaseInterface
     */
    public void saveData(DataBaseInterface storer) {
        storer.storeList(items, "database/items.db");
        storer.storeList(discounts, "database/discounts.db");
    }

    /**
     * Loads data from db.
     *
     * @param storer DataBaseInterface manager
     * @see JDealsController
     */
    public void loadData(DataBaseInterface storer) {
        ArrayList<Item> tmp = storer.loadList("database/items.db");
        if (!tmp.isEmpty()) {
            int lastId = 0;
            for (Item i : tmp) {
                if (lastId < i.getId()) {
                    lastId = i.getId();
                }
            }

            Item.nextId.set(lastId);

            this.items = tmp;
        }

        this.discounts = storer.loadList("database/discounts.db");
    }

    /**
     * Add item to store.
     *
     * @param item instance of the Item
     * @throws Exception can throws an exception if id already exists
     * @see Item
     */
    public void addItem(Item item) throws Exception {
        if (this.getItem(item.getId()) != null) {
            throw new Exception("An item with same id already exists!");
        }

        items.add(item);
    }

    /**
     * Deletes item from store.
     *
     * @param id item id
     * @return true, if successful
     * @see Item
     */
    public boolean delItem(int id) {
        Item i = this.getItem(id);
        return this.items.remove(i);
    }

    /**
     * Gets item from store.
     *
     * @param id item id
     * @return the item
     * @see Item
     */
    public Item getItem(int id) {
        for (Item i : this.items) {
            if (i.getId() == id) {
                return i;
            }
        }

        return null;
    }

    /**
     * Gets instance of JDealsController.
     *
     * @return JDealsController
     */
    public JDealsController getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Adds discount to store.
     *
     * @param d DiscountInterface
     * @throws Exception can throw an exception in case a rule with same param.
     * already exists
     * @see DiscountInterface
     */
    public void addDiscount(DiscountInterface<Object> d) throws Exception {
        if (this.discounts.contains(d)) {
            throw new Exception("This rule already exists");
        }

        this.discounts.add(d);
    }

    /**
     * Removes discount from the store.
     *
     * @param indexId the index id
     * @see DiscountInterface
     */
    public void removeDiscount(int indexId) {
        this.discounts.remove(indexId);
    }

    /**
     * Gets discount list.
     *
     * @return ArrayList<DiscountInterface> stored discounts
     * @see DiscountInterface
     */
    public ArrayList<DiscountInterface<Object>> getDiscounts() {
        return this.discounts;
    }

    /**
     * Add item to cart.
     *
     * @param item the item
     * @param quantity the quantity
     * @return true, if successful
     * @throws Exception the exception
     * @see Item
     * @see Order
     * @see User
     */
    public boolean addItemToCart(Item item, int quantity) throws Exception {
        User cUser = this.mainCtrl.getCurUser();
        Order cOrder = cUser.getCurOrder();

        checkQuantity(item, cOrder, quantity, true);

        cOrder.addItem(item, quantity);

        try {
            this.discountOrderCost(cOrder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            cUser.getCurOrder().removeItem(item, false);
            throw new Exception("Sorry! An error in discount process has occurred");
        }

        creditCheck(cUser, item, true);

        return true;
    }

    /**
     * Overloading addItemToCart.
     *
     * @param id the id
     * @param quantity the quantity
     * @return true, if successful
     * @throws Exception the exception
     */
    public boolean addItemToCart(int id, int quantity) throws Exception {
        return this.addItemToCart(this.getItem(id), quantity);
    }

    /**
     * Check if requested item quantity is available.
     *
     * @param item the item
     * @param cOrder the c order
     * @param quantity number of requested items
     * @param checkCart specify if we've to check also in cart
     * @return true if success
     * @throws Exception can throw an exception if quantity is not available
     * @see Item
     * @see Order
     */
    private boolean checkQuantity(Item item, Order cOrder, int quantity, boolean checkCart) throws Exception {
        ItemStack is = cOrder.getItem(item.getId());
        int checkQt = quantity;
        if (is != null && checkCart) {
            checkQt += is.quantity; // we have to check quantity summing items already in cart
        }
        if (checkQt <= 0) {
            return false;
        } else if (checkQt == 1 || !(item instanceof QAvailability)) {
            if (!item.isAvailable()) {
                throw new Exception("This item is not available!");
            }
        } else {
            if (!((QAvailability) item).isAvailable(checkQt)) {
                throw new Exception("This quantity is not available!");
            }
        }

        return true;
    }

    /**
     * Checks if User has credit to buy this item.
     *
     * @param cUser the c user
     * @param item the item
     * @param removeItem removes item from order if credit is not enough
     * @throws Exception throws an exception if credit is not enough
     * @see User
     * @see Item
     */
    public void creditCheck(User cUser, Item item, boolean removeItem) throws Exception {
        if (!cUser.checkCredit(cUser.getCurOrder().getTotalPrice(true))) {
            if (removeItem) {
                cUser.getCurOrder().removeItem(item, false);
            }

            throw new Exception("You don't have enough credit to buy this item!");
        }
    }

    /**
     * Pay the Order.
     *
     * @return true on success
     * @throws Exception the exception
     * @see User
     * @see Item
     * @see Order
     * @see ItemStack
     */
    public boolean payOrder() throws Exception {
        User cUser = this.mainCtrl.getCurUser();
        /**
         * The order price is calculated when user fill the cart since would be
         * impolite to change the price as soon as we would pay our cart items
         * Instead we should implement a timeout for cart, after which it will
         * be resetted if not payed
         */
        double totalPrice = cUser.getCurOrder().getTotalPrice();
        // Check user credit
        if (!cUser.checkCredit(totalPrice)) {
            throw new Exception("You don't have enough credit to pay this order!");
        }
        // Really sell items and clone to final order
        Order finalOrder = new Order();
        finalOrder.setTotalPrice(totalPrice);
        for (Order.ItemStack is : cUser.getCurOrder().getItems()) {
            this.sellItem(is.item, is.quantity);
            finalOrder.addItem(is.item.clone(), is.quantity);
        }

        cUser.modCredit(-finalOrder.getTotalPrice());
        cUser.getCurOrder().reset(false);
        cUser.addOrder(finalOrder); // add to history

        return true;
    }

    /**
     * Sell an item ( Used by PayOrder ).
     *
     * @param item Item object
     * @param quantity to sell
     * @return true, if successful
     * @throws Exception the exception
     * @see User
     * @see Order
     */
    private boolean sellItem(Item item, int quantity) throws Exception {
        User cUser = this.mainCtrl.getCurUser();
        Order cOrder = cUser.getCurOrder();

        checkQuantity(item, cOrder, quantity, false);

        // Paying case
        creditCheck(cUser, item, false);
        item.modSold(quantity);

        return true;
    }

    /**
     * Discount an order processing activated rules.
     *
     * @param order the order
     * @see DiscountInterface
     * @see Order
     */
    public void discountOrderCost(Order order) {
        // VARIOUS INITIAL CHECKS
        if (this.discounts.isEmpty() || order == null
                || order.getItems() == null || order.getItems().isEmpty()
                || order.getItems().contains(null)) {
            return;
        }

        Collections.sort(this.discounts, Collections.reverseOrder()); // from highest rated

        Map<Class, Boolean> map = new HashMap<Class, Boolean>();

        for (DiscountInterface<Object> d : this.discounts) {
            if (d == null) {
                continue;
            }

            try {
                if (map.get(d.getClass())) {
                    continue;
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }

            if (d.scan(order, true)) {
                map.put(d.getClass(), true);
            }
        }
    }

    /**
     * Gets item list.
     *
     * @return the items
     * @see Item
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Overloading getFilteredItems.
     *
     * @param expired the expired
     * @return the filtered items
     */
    public ArrayList<Item> getFilteredItems(boolean expired) {
        return this.getFilteredItems(expired, null);
    }

    /**
     * Overloading getFilteredItems.
     *
     * @param expired the expired
     * @param sortPolicy the sort policy
     * @return the filtered items
     */
    public ArrayList<Item> getFilteredItems(boolean expired, Comparator<Item> sortPolicy) {
        return this.getFilteredItems(expired, sortPolicy, null);
    }

    /**
     * Gets filtered and ordered item list.
     *
     * @param expired the expired
     * @param sortPolicy the sort policy
     * @param predicate the predicate
     * @return the filtered items
     * @see Predicate
     * @see Filter
     * @see Item
     */
    public ArrayList<Item> getFilteredItems(boolean expired, Comparator<Item> sortPolicy, Predicate predicate) {
        ArrayList<Item> resList = new ArrayList<>();
        for (Item item : this.items) {
            if (item.isAvailable() ^ expired) { // XOR condition
                if (!this.getMainCtrl().isManagerSession(true) && item instanceof Restourant
                        && !((Restourant) item).getLocation().toLowerCase().equals(this.mainCtrl.getCurUser().getCity().toLowerCase())) {
                    continue;
                }

                resList.add(item);
            }
        }

        if (predicate != null) {
            resList = (ArrayList<Item>) Filter.run(resList, predicate);
        }

        if (sortPolicy != null) {
            Collections.sort(resList, sortPolicy);
        }

        return resList;
    }
}
