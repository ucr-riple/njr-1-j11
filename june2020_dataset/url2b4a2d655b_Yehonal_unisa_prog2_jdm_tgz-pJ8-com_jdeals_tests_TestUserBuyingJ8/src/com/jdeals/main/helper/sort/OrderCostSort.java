/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.sort;

import com.jdeals.main.entity.Order;

/**
 * The Class OrderCostSort.
 */
public class OrderCostSort extends SortingClass<Order> {

    /**
     * Instantiates a new order cost sort.
     *
     * @see SortingDirection
     * @param order the order
     */
    public OrderCostSort(SortingDirection order) {
        super(order);
    }

    /**
     * @see Order
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Order o1, Order o2) {
        return this.getDirection(o1.getTotalPrice() >= o2.getTotalPrice());
    }
}
