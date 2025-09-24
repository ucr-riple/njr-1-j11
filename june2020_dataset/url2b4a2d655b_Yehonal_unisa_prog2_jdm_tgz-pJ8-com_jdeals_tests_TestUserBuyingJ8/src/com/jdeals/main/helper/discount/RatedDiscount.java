/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.discount;

import java.io.Serializable;
import java.util.ArrayList;

import com.jdeals.libs.MyObject;
import com.jdeals.libs.Tools;
import com.jdeals.main.entity.Order;

/**
 * The Class RatedDiscount.
 *
 * @param <T> the generic type
 */
public class RatedDiscount<T extends RatedDiscount> extends MyObject implements DiscountInterface<T>, Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -121890283689461933L;

    /**
     * The rate.
     */
    protected double rate;

    /**
     * Instantiates a new rated discount.
     *
     * @param rate the rate
     */
    public RatedDiscount(double rate) {
        this.rate = (1.0 - rate / 100.0);
    }

    /**
     * @see
     * com.jdeals.main.helper.discount.DiscountInterface#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(T o) {
        return o == null || this.rate < o.rate ? 1 : -1; // less is more 0.1 (90%) > 0.9 (10%)
    }

    /**
     * @see Order
     * @see
     * com.jdeals.main.helper.discount.DiscountInterface#scan(com.jdeals.main.entity.Order,
     * boolean)
     */
    @Override
    public boolean scan(Order order, boolean apply) {
        ArrayList<Order.ItemStack> stack = order.getItems();

        if (order.getItems().isEmpty()) {
            return false;
        }

        if (apply) {
            for (Order.ItemStack is : stack) {
                if (is == null) {
                    continue;
                }
                is.finalPrice = is.item.getPrice() * this.rate;
            }
        }

        return true;
    }

    /**
     * Get rate.
     *
     * @return percentage value
     */
    public double getRate() {
        return Math.round((1.0 - rate) * 100);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Tools.printVar(this.getRate(), "Discount Rate", this.getClass().getSimpleName(), false, false);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.rate == ((RatedDiscount) obj).rate;
    }
}
