/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.filter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Class Filter.
 */
abstract public class Filter {

    /**
     * Run.
     *
     * @param <T> the generic type
     * @param col the col
     * @param predicate the predicate
     * @return the collection
     */
    public static <T> Collection<T> run(Collection<T> col,
            Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : col) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
