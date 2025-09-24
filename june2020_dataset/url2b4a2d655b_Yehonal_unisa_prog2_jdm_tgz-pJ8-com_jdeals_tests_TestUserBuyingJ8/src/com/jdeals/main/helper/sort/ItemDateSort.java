/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.sort;


import com.jdeals.main.entity.catalogue.Event;
import com.jdeals.main.entity.catalogue.Item;

/**
 * The Class ItemDateSort.
 */
public class ItemDateSort extends SortingClass<Item> {

    /**
     * Instantiates a new item date sort.
     *
     * @see SortingDirection
     * @param order the order
     */
    public ItemDateSort(SortingDirection order) {
        super(order);
    }

    /**
     * @see Event
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Item o1, Item o2) {
        if (o1 instanceof Event) {
            if (o2 instanceof Event) {
                return this.getDirection(((Event) o1).getExpiryDate().getDate().before(((Event) o2).getExpiryDate().getDate()));
            } else {
                return this.getDirection(false);
            }
        } else {
            return this.getDirection(true);
        }
    }
}
