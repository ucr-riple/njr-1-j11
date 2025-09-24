/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.catalogue;

import com.jdeals.libs.MyDate;
import com.jdeals.libs.Tools;

/**
 * Entity Class for Travel.
 */
public class Travel extends Event {

    /**
     *
     */
    private static final long serialVersionUID = -3921021663667308383L;

    /**
     * The departure date.
     */
    private MyDate departureDate;

    /**
     * Instantiates a new travel.
     *
     * @see MyDate
     * @param price the price
     * @param sold the sold items
     * @param expiryDate the expiry date
     * @param location the location
     * @param departureDate the departure date
     * @see MyDate
     */
    public Travel(double price, int sold,
            MyDate expiryDate, String location, MyDate departureDate) {

        super(price, sold, expiryDate, location);

        this.departureDate = departureDate;
    }

    /**
     * Gets the departure date.
     *
     * @see MyDate
     * @return the departure date
     */
    public MyDate getDepartureDate() {
        return this.departureDate;
    }

    /**
     * Convert To String
     *
     * @param raw the raw
     * @param newLine the new line
     * @return the string
     * @see com.jdeals.main.entity.catalogue.Event#toString(boolean, boolean)
     */
    @Override
    public String toString(boolean raw, boolean newLine) {
        return super.toString(raw, newLine)
                + Tools.printVar(this.getDepartureDate().toString(), "eparture Date", null, raw, newLine);
    }
}
