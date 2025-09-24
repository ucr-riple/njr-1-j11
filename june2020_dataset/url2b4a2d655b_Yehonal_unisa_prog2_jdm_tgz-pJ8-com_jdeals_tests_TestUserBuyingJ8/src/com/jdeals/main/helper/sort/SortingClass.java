/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.sort;

import java.util.Comparator;

/**
 * The Class SortingClass.
 *
 * @param <T> the generic type
 */
public abstract class SortingClass<T> implements Comparator<T> {

    /**
     * The Enum OrderDirection.
     */
    public enum SortingDirection {

        /**
         * The decr.
         */
        DECR("Decr"),
        /**
         * The cresc.
         */
        CRESC("Cresc");

        /**
         * The val.
         */
        private String val;

        /**
         * Instantiates a new order direction.
         *
         * @param s the s
         */
        private SortingDirection(String s) {
            this.val = s;
        }

        /**
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return val;
        }
    }

    /**
     * The direction.
     */
    private SortingDirection direction;

    /**
     * Instantiates a new sorting class.
     *
     * @see SortingDirection
     * @param direction the direction
     */
    public SortingClass(SortingDirection direction) {
        this.direction = direction;
    }

    /**
     * Gets the direction.
     *
     * @see SortingDirection
     * @return the direction
     */
    public SortingDirection getDirection() {
        return direction;
    }

    /**
     * Gets the direction.
     *
     * @see SortingDirection
     * @param sup the sup
     * @return the direction
     */
    public int getDirection(boolean sup) {
        return sup ? (direction == SortingDirection.DECR ? -1 : 1) : (direction == SortingDirection.DECR ? 1 : -1);
    }

    /**
     * Sets the direction.
     *
     * @see SortingDirection
     * @param direction the new direction
     */
    public void setDirection(SortingDirection direction) {
        this.direction = direction;
    }

}
