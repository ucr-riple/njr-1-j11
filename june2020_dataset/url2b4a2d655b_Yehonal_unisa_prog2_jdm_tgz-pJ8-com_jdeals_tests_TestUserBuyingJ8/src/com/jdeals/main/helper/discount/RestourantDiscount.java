/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.discount;

import java.util.ArrayList;

import com.jdeals.libs.Tools;
import com.jdeals.main.entity.Order;
import com.jdeals.main.entity.catalogue.Restourant;

/**
 * The Class RestourantDiscount.
 */
public class RestourantDiscount extends LastDaysDiscount {

    /**
     *
     */
    private static final long serialVersionUID = -325069303996122352L;

    /**
     * The sold rate.
     */
    private double soldRate;

    /**
     * Instantiates a new restourant discount.
     *
     * @param soldRate the sold rate
     * @param discountRate the discount rate
     * @param days the days
     */
    public RestourantDiscount(int soldRate, int discountRate, int days) {
        super(discountRate, days);
        this.soldRate = (1.0 - (soldRate / 100.0));
    }

    /**
     * @see
     * com.jdeals.main.helper.discount.LastDaysDiscount#scan(com.jdeals.main.entity.Order,
     * boolean)
     */
    @Override
    public boolean scan(Order order, boolean apply) {
        boolean found = false;
        for (Order.ItemStack is : order.getItems()) {
            if (is == null) {
                continue;
            }

            if (is.item instanceof Restourant) {
                ArrayList<Order.ItemStack> tmp = new ArrayList<>();
                tmp.add(is);

                if (super.scan(new Order(tmp), false)
                        && is.item.getSold() < (((Restourant) is.item).getTotalEvents() * this.soldRate)) {
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
     * Get percentage value of rate.
     *
     * @return percentage value
     */
    public double getSoldRate() {
        return Math.round((1.0 - rate) * 100);
    }

    /**
     * @see Tools
     * @see com.jdeals.main.helper.discount.LastDaysDiscount#toString()
     */
    @Override
    public String toString() {
        return super.toString() + Tools.printVar(this.getSoldRate(), "Sold Percentage", null, false, false);
    }

    /**
     * @see
     * com.jdeals.main.helper.discount.LastDaysDiscount#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.soldRate == ((RestourantDiscount) obj).soldRate;
    }
}
