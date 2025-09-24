/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.MyDate;
import com.jdeals.libs.MyVar;
import com.jdeals.libs.Tools;

import java.util.Calendar;

/**
 * Abstract class to define an Event.
 *
 * @author Giuseppe Ronca
 */
abstract public class Event extends Item {

    /**
     *
     */
    private static final long serialVersionUID = 1478655695118887635L;

    /**
     * The expiry date.
     */
    private MyDate expiryDate;

    /**
     * The location.
     */
    private String location;

    /**
     * Default constructor.
     *
     * @param price the price
     * @param sold the sold
     * @param expiryDate the expiry date
     * @param location the location
     * @see MyDate
     */
    public Event(double price, int sold, MyDate expiryDate, String location) {
        super(price, sold);

        this.expiryDate = expiryDate;
        this.location = location;
    }

    /**
     * Gets the expiry Date.
     *
     * @return the expiry Date
     * @see MyDate
     */
    public MyDate getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Location Accessor Method.
     *
     * @return Location of current event
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @see Tools
     * @see com.jdeals.main.entity.catalogue.Item#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        return super.toString(raw, newLine)
                + Tools.printVars(null, raw, newLine, new MyVar(this.getLocation(), "Location"), new MyVar(this.getExpiryDate(), "Expiry Date"));
    }

    /**
     * Check if an event is avaible.
     *
     * @return avaible or not
     */
    @Override
    public boolean isAvailable() {
        return this.getExpiryDate().getDate().after(Calendar.getInstance().getTime());
    }

}
