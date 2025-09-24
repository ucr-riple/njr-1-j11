/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.MyDate;
import com.jdeals.libs.MyVar;
import com.jdeals.libs.Tools;
import com.jdeals.main.helper.QAvailability;

/**
 * Entity class for Restourant item.
 *
 * @author Giuseppe Ronca
 */
public class Restourant extends Event implements QAvailability {

    /**
     *
     */
    private static final long serialVersionUID = 4884413862373244003L;

    /**
     * The description.
     */
    private String description;

    /**
     * The name.
     */
    private String name;

    /**
     * The total events.
     */
    private int totalEvents;

    /**
     * Default constructor.
     *
     * @param name the name
     * @param description the description
     * @param price the price
     * @param sold the sold
     * @param expiryDate the expiry date
     * @param location the location
     * @param avaibleEvents events available
     * @see MyDate
     * @see Event
     */
    public Restourant(String name, String description, double price, int sold,
            MyDate expiryDate, String location, int avaibleEvents) {
        super(price, sold, expiryDate, location);

        this.description = description;
        this.name = name;
        this.totalEvents = avaibleEvents;
    }

    /**
     * Gets the name of the Restourant.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets Description.
     *
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets Total Events.
     *
     * @return total events
     */
    public int getTotalEvents() {
        return totalEvents;
    }

    /**
     * @see com.jdeals.main.entity.catalogue.Event#isAvailable()
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable(1);
    }

    /**
     * @see com.jdeals.main.helper.QAvailability#isAvailable(int)
     */
    public boolean isAvailable(int quantity) {
        if (super.isAvailable()) {
            return quantity <= this.getAvailableItems();
        }
        return false;
    }

    /**
     * Gets the available items.
     *
     * @return the available items
     */
    public int getAvailableItems() {
        return this.totalEvents - this.getSold();
    }

    /**
     * @see Tools
     * @see com.jdeals.main.entity.catalogue.Event#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        return super.toString(raw, newLine)
                + Tools.printVars(null, raw, newLine, new MyVar(this.name, "Restourant"),
                        new MyVar(this.totalEvents - this.getSold(), "Avaible coupon"));
    }
}
