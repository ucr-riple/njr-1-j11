/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.jdeals.libs.MyDate;
import com.jdeals.libs.MyVar;
import com.jdeals.libs.Tools;
import com.jdeals.main.entity.catalogue.Item;

/**
 * The Class to define an Order, it's the dataset of cart Frame
 */
public class Order implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 7485094500418569868L;

    /**
     * The Class ItemStack.
     */
    public class ItemStack implements Serializable, Cloneable {

        /**
         * The item.
         */
        public Item item;

        /**
         * The quantity.
         */
        public int quantity;

        /**
         * to print final price use getFinalPrice method instead it's only for
         * modifiers.
         */
        public double finalPrice;

        /**
         * Instantiates a new item stack.
         *
         * @see Item
         * @param item the item
         * @param quantity the quantity
         */
        public ItemStack(Item item, int quantity) {
            this(item, quantity, -1);
        }

        /**
         * Instantiates a new item stack.
         *
         * @see Item
         * @param item the item
         * @param quantity the quantity
         * @param finalPrice the final price
         */
        public ItemStack(Item item, int quantity, double finalPrice) {
            this.item = item;
            this.quantity = quantity;
            this.finalPrice = finalPrice;
        }

        /**
         * Get original price or discounted price if possible .
         *
         * @see Item
         * @see Tools
         * @return rounded value
         */
        public double getFinalPrice() {
            double p = this.finalPrice >= 0 ? this.finalPrice : this.item.getPrice();
            return Tools.RoundDecimalPos(p, 2);
        }
    }

    /**
     * The items.
     */
    private ArrayList<ItemStack> items;

    /**
     * The total price.
     */
    private double totalPrice;

    /**
     * The date.
     */
    private MyDate date;

    /**
     * @see ItemStack Instantiates a new order.
     */
    public Order() {
        this(new ArrayList<ItemStack>());
    }

    /**
     * Instantiates a new order.
     *
     * @see ItemStack
     * @param items the items
     */
    public Order(ArrayList<ItemStack> items) {
        this.reset(false);
        this.items = items;
    }

    /**
     * Gets the items.
     *
     * @see ItemStack
     * @return the items
     */
    public ArrayList<ItemStack> getItems() {
        //items.removeAll(Collections.singleton(null));
        return items;
    }

    /**
     * Gets the item.
     *
     * @param id the id
     * @return the item
     */
    public ItemStack getItem(int id) {
        for (ItemStack is : this.items) {
            if (is.item.getId() == id) {
                return is;
            }
        }

        return null;
    }

    /**
     * Sets the items.
     *
     * @param items the new items
     */
    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    /**
     * Adds the item.
     *
     * @see Item
     * @param item the item
     * @param quantity the quantity
     * @return total items in stacks
     */
    public int addItem(Item item, int quantity) {
        ItemStack is = this.getItem(item.getId());
        if (is != null) {
            quantity = is.quantity += quantity;
        } else {
            this.items.add(new ItemStack(item, quantity));
        }

        return quantity;
    }

    /**
     * Reset the order
     *
     * @param modSold the mod sold
     */
    public void reset(boolean modSold) {
        if (items != null && !items.isEmpty()) {
            this.removeItems(items, modSold);
        }

        this.date = new MyDate();
        this.totalPrice = 0;
    }

    /**
     * Removes the items.
     *
     * @param c the c
     * @param modSold the mod sold
     * @return true, if successful
     */
    public boolean removeItems(Collection<?> c, boolean modSold) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        for (Iterator<ItemStack> iterator = items.iterator(); iterator.hasNext();) {
            ItemStack i = iterator.next();
            if (c.contains(i)) {
                if (modSold) {
                    i.item.modSold(-i.quantity);
                }
                iterator.remove();
            }
        }

        return true;
    }

    /**
     * Removes the item.
     *
     * @param is the is
     * @param modSold the mod sold
     * @return true, if successful
     */
    public boolean removeItem(ItemStack is, boolean modSold) {
        Collection<ItemStack> c = new ArrayList<>();
        c.add(is);
        return this.removeItems(c, modSold);
    }

    /**
     * overloading removeItem
     *
     * @param item the item
     * @param modSold the mod sold
     * @return true, if successful
     */
    public boolean removeItem(Item item, boolean modSold) {
        return this.removeItem(item.getId(), modSold);
    }

    /**
     * overloading removeItem
     *
     * @param id the id
     * @param modSold the mod sold
     * @return true, if successful
     */
    public boolean removeItem(int id, boolean modSold) {
        return this.removeItem(this.getItem(id), modSold);
    }

    /**
     * Gets the date.
     *
     * @see MyDate
     * @return the date
     */
    public MyDate getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @see MyDate
     * @param date the new date
     */
    public void setDate(MyDate date) {
        this.date = date;
    }

    /**
     * "Manually" set total price.
     *
     * @param totalPrice the new total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the total price.
     *
     * @param calculate the calculate
     * @return the total price
     */
    public double getTotalPrice(boolean calculate) {
        double total = 0;

        for (ItemStack is : this.items) {
            total += is.getFinalPrice() * is.quantity;
        }

        return Tools.RoundDecimalPos(total, 2);
    }

    /**
     * If totalPrice is 0 then calculate, otherwise return previous calculated
     * value ( usefull for example for order history ) Use carefully!.
     *
     * @return price of the order
     */
    public double getTotalPrice() {
        return this.getTotalPrice(this.totalPrice == 0);
    }

    /**
     * @see Tools
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Tools.printVars(this.getClass().getSimpleName(), true, true, new MyVar(this.date.toString(), "Date"), new MyVar(this.getTotalPrice(), "Price"));
    }
}
