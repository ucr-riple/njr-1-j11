/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.MyVar;
import com.jdeals.libs.Tools;
import com.jdeals.main.entity.user.Manager;

/**
 * Entity Class for Supply item.
 */
abstract public class Supply extends Item {

    /**
     *
     */
    private static final long serialVersionUID = -2028349903080084111L;

    /**
     * The description.
     */
    private String description;

    /**
     * The supplier.
     */
    private Manager supplier;

    /**
     * Instantiates a new supply.
     *
     * @param description the description
     * @param price the price
     * @param sold the sold
     * @param supplier the supplier
     * @see Manager
     */
    public Supply(String description, double price, int sold, Manager supplier) {
        super(price, sold);

        this.supplier = supplier.clone();
        this.description = description;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the supplier.
     *
     * @return the supplier
     * @see Manager
     */
    public Manager getSupplier() {
        return supplier;
    }

    /**
     * Clone.
     *
     * @see Manager
     * @return the supply
     * @throws CloneNotSupportedException the clone not supported exception
     * @see com.jdeals.main.entity.catalogue.Item#clone()
     */
    @Override
    public Supply clone() throws CloneNotSupportedException {
        Supply res = (Supply) super.clone();
        if (res.supplier != null) {
            res.supplier = (Manager) res.supplier.clone();
        }
        return res;
    }

    /**
     * To string.
     *
     * @see Tools
     * @param raw the raw
     * @param newLine the new line
     * @return the string
     * @see com.jdeals.main.entity.catalogue.Item#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        String supplier = "";
        if (this.getSupplier() != null) {
            supplier = this.getSupplier().getUsername();
        }

        return super.toString(raw, newLine)
                + Tools.printVars(null, raw, newLine,
                        new MyVar(supplier.isEmpty() ? "None" : supplier, "Supplier"),
                        new MyVar(this.getDescription(), "Description"),
                        new MyVar(this.getSupplier().getScoreAverage(), "Score"));
    }
}
