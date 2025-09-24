/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper;

/**
 * Quantity Availability interface.
 *
 * @author Giuseppe Ronca
 */
public interface QAvailability extends Availability {

    /**
     * check if a specified quantity is available.
     *
     * @param quantity the quantity
     * @return true, if is available
     */
    public boolean isAvailable(int quantity);
}
