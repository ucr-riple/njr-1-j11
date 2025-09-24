/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.filter;

/**
 * The Interface Predicate.
 *
 * @param <T> the generic type
 */
public interface Predicate<T> {

    /**
     * Apply the filter using specified predicate
     *
     * @param type the type
     * @return true, if successful
     */
    boolean apply(T type);
}
