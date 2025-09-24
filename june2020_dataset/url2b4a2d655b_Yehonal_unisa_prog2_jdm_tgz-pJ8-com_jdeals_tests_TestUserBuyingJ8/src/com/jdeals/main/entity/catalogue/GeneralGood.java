/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.Tools;
import com.jdeals.main.entity.user.Manager;
import com.jdeals.main.helper.QAvailability;

/**
 * Entity class for General Goods .
 *
 * @author Giuseppe Ronca
 */
public class GeneralGood extends Supply implements QAvailability {

    /**
     *
     */
    private static final long serialVersionUID = -4691400988423801672L;

    /**
     * The total items.
     */
    private int totalItems;

    /**
     * Instantiates a new general goods.
     *
     * @param description the description
     * @param price the price
     * @param sold the sold
     * @param avaibleItems the avaible items
     * @param supplier the supplier
     */
    public GeneralGood(String description, double price, int sold, int avaibleItems, Manager supplier) {
        super(description, price, sold, supplier);

        this.totalItems = avaibleItems;
    }

    /**
     * Check if there's still an item .
     *
     * @return true, if is available
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable(1);
    }

    /**
     * @see com.jdeals.main.helper.QAvailability#isAvailable(int)
     */
    @Override
    public boolean isAvailable(int quantity) {
        return quantity <= this.getAvailableItems();
    }

    /**
     * Gets the total items.
     *
     * @return Original number of supply
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Returns available items.
     *
     * @return the available items
     */
    public int getAvailableItems() {
        return this.totalItems - this.getSold();
    }

    /**
     * @see com.jdeals.main.entity.catalogue.Supply#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        return super.toString(raw, newLine)
                + Tools.printVar(this.getTotalItems() - this.getSold(), "Avaible items", null, raw, newLine);
    }
}
