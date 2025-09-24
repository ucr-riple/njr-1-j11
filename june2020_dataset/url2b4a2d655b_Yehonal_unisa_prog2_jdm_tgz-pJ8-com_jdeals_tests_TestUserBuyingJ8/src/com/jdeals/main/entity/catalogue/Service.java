/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.Tools;
import com.jdeals.main.entity.user.Manager;

/**
 * Entity Class for Service.
 */
public class Service extends Supply {

    /**
     *
     */
    private static final long serialVersionUID = -1066450665607402536L;

    /**
     * The location.
     */
    private String location;

    /**
     * Instantiates a new service.
     *
     * @param description the description
     * @param location the location
     * @param price the price
     * @param sold the sold items, normally 0
     * @param supplier the supplier
     */
    public Service(String description, String location, double price, int sold,
            Manager supplier) {
        super(description, price, sold, supplier);

        this.location = location;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Checks if is available.
     *
     * @return true, if is available
     * @see com.jdeals.main.helper.Availability#isAvailable()
     */
    @Override
    public boolean isAvailable() {
        return true;
    }

    /**
     * To string.
     *
     * @see Tools
     * @param raw the raw
     * @param newLine the new line
     * @return the string
     * @see com.jdeals.main.entity.catalogue.Supply#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        return super.toString(raw, newLine)
                + Tools.printVar(this.location, "Location", null, raw, newLine);
    }

}
