/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdeals.libs.MyObject;
import com.jdeals.libs.MyVar;
import com.jdeals.libs.Tools;
import com.jdeals.main.helper.Availability;

/**
 * The Class Item.
 */
abstract public class Item extends MyObject implements Availability, Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -7755543573061499015L;

    /**
     * The next id.
     */
    public static AtomicInteger nextId = new AtomicInteger();

    /**
     * The id.
     */
    private int id;

    /**
     * The price.
     */
    private double price;

    /**
     * The sold.
     */
    private int sold;

    /**
     * Default constructor.
     *
     * @param price the price
     * @param sold the sold
     */
    public Item(double price, int sold) {
        this.id = nextId.incrementAndGet();
        this.price = price;
        this.sold = sold;
    }

    /**
     * Gets the id of the item.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets ,not discounted, price of the item.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets number of sold items.
     *
     * @return the sold
     */
    public int getSold() {
        return sold;
    }

    /**
     * Sets sold quantity.
     *
     * @param quantity the new sold
     */
    public void setSold(int quantity) {
        this.sold = quantity;
    }

    /**
     * Modify sold quantity.
     *
     * @param quantity the quantity
     */
    public void modSold(int quantity) {
        this.sold += quantity;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.toString(false, true);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Item cp = ((Item) obj);
        return this.getId() == cp.getId() && this.getPrice() == cp.getPrice() && this.getSold() == cp.getSold();
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public Item clone() throws CloneNotSupportedException {
        return (Item) super.clone();
    }

    /**
     * To string.
     *
     * @param raw the raw
     * @param newLine the new line
     * @return the string
     */
    public String toString(boolean raw, boolean newLine) {
        return Tools.printVars(this.getClass().getSimpleName(), raw, newLine,
                new MyVar(this.getId(), "ID"), new MyVar(this.getPrice(), "Price"));
    }
}
