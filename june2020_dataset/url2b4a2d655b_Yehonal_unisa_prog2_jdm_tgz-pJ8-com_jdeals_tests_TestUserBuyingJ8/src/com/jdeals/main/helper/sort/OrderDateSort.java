/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.sort;


import com.jdeals.main.entity.Order;

/**
 * The Class OrderDateSort.
 */
public class OrderDateSort extends SortingClass<Order> {

    /**
     * Instantiates a new order date sort.
     *
     * @see SortingDirection
     * @param order the order
     */
    public OrderDateSort(SortingDirection order) {
        super(order);
    }

    /**
     * Order
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Order o1, Order o2) {
        return this.getDirection(o1.getDate().getDate().before(o2.getDate().getDate()));
    }
}
