/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.filter;

import com.jdeals.libs.MyDate;
import com.jdeals.main.entity.catalogue.Event;
import com.jdeals.main.entity.catalogue.Item;

/**
 * The Class FilterByDateRange.
 */
public class FilterByDateRange implements Predicate<Item> {

    /**
     * The start date.
     */
    private MyDate startDate;

    /**
     * The end date.
     */
    private MyDate endDate;

    /**
     * Instantiates a new filter by date range.
     *
     * @see MyDate
     * @param startDate the start date
     * @param endDate the end date
     */
    public FilterByDateRange(MyDate startDate, MyDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Apply the filter
     *
     * @see Event
     * @see Item
     * @see com.jdeals.main.helper.filter.Predicate#apply(java.lang.Object)
     */
    @Override
    public boolean apply(Item item) {
        return item instanceof Event
                && ((Event) item).getExpiryDate().getDate().after(this.startDate.getDate())
                && ((Event) item).getExpiryDate().getDate().before(this.endDate.getDate());
    }

}
