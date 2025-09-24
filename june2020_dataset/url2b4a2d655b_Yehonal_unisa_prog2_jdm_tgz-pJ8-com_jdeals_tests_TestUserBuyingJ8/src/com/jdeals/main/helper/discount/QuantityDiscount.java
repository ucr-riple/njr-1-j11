/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.discount;

import java.util.ArrayList;

import com.jdeals.libs.Tools;
import com.jdeals.main.entity.Order;

/**
 * The Class QuantityDiscount.
 */
public class QuantityDiscount extends RatedDiscount {

    /**
     *
     */
    private static final long serialVersionUID = -6387998832468160196L;

    /**
     * The min quantity.
     */
    private int minQuantity;

    /**
     * Instantiates a new quantity discount.
     *
     * @param quantity the quantity
     * @param discountRate the discount rate
     */
    public QuantityDiscount(int quantity, double discountRate) {
        super(discountRate);
        this.minQuantity = quantity;
    }

    /**
     * @see Order
     * @see
     * com.jdeals.main.helper.discount.RatedDiscount#scan(com.jdeals.main.entity.Order,
     * boolean)
     */
    @Override
    public boolean scan(Order order, boolean apply) {
        ArrayList<Order.ItemStack> stack = order.getItems();

        int quantity = 0;
        for (Order.ItemStack is1 : stack) {
            if (is1 == null) {
                continue;
            }

            quantity += is1.quantity;
            if (quantity > this.minQuantity) {
                if (apply) {
                    for (Order.ItemStack is2 : stack) {
                        if (is2 == null) {
                            continue;
                        }

                        is2.finalPrice = is2.item.getPrice() * this.rate;
                    }
                }

                return true;
            }
        }

        return false;
    }

    /**
     * @see Tools
     * @see com.jdeals.main.helper.discount.RatedDiscount#toString()
     */
    @Override
    public String toString() {
        return super.toString() + Tools.printVar(this.minQuantity, "Quantity", null, false, false);
    }

    /**
     * @see
     * com.jdeals.main.helper.discount.RatedDiscount#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.minQuantity == ((QuantityDiscount) obj).minQuantity;
    }
}
