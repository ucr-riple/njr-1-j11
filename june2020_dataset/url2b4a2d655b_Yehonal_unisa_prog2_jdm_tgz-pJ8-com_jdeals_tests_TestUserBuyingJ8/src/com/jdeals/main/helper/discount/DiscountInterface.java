/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.discount;

import com.jdeals.main.entity.Order;

/**
 * The Interface DiscountInterface.
 *
 * @param <T> the generic type
 */
public interface DiscountInterface<T extends Object> extends Comparable<T> {

    /**
     * Scan for discountable items
     *
     * @see Order
     * @param order the order
     * @param apply the apply
     * @return true, if successful
     */
    public boolean scan(Order order, boolean apply);

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(T o);

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString();

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj);
}
