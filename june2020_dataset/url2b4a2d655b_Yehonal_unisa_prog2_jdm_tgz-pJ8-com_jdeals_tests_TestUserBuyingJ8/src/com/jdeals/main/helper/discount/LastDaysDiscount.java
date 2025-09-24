/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.discount;

import com.jdeals.libs.MyDate;
import com.jdeals.libs.Tools;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.catalogue.Event;

/**
 * The Class LastDaysDiscount.
 */
public class LastDaysDiscount extends RatedDiscount {

    /**
     *
     */
    private static final long serialVersionUID = -923108757933525957L;

    /**
     * The days.
     */
    private int days;

    /**
     * Instantiates a new last days discount.
     *
     * @param rate the rate
     * @param days the days
     */
    public LastDaysDiscount(int rate, int days) {
        super(rate);
        this.days = days;
    }

    /**
     * @see Order
     * @see
     * com.jdeals.main.helper.discount.RatedDiscount#scan(com.jdeals.main.entity.Order,
     * boolean)
     */
    @Override
    public boolean scan(Order order, boolean apply) {
        boolean found = false;
        for (Order.ItemStack is : order.getItems()) {
            if (is == null) {
                continue;
            }

            if (is.item instanceof Event) {
                Event evt = (Event) is.item;
                long diff = MyDate.dayDiff(new MyDate(), evt.getExpiryDate());
                if (diff > 0 && diff <= days) {
                    if (apply) {
                        is.finalPrice = is.item.getPrice() * this.rate;
                    }

                    if (!found) {
                        found = true;
                    }
                }
            }
        }

        return found;
    }

    /**
     * @see Tools
     * @see com.jdeals.main.helper.discount.RatedDiscount#toString()
     */
    @Override
    public String toString() {
        return super.toString() + Tools.printVar(this.days, "Days", null, false, false);
    }

    /**
     * @see
     * com.jdeals.main.helper.discount.RatedDiscount#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.days == ((LastDaysDiscount) obj).days;
    }
}
