package com.gesoftware.venta.utility;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Sorting utility class
 **/
public final class SortingUtility {
    /* *
     * METHOD: Sorts any collection using java sorting library
     * RETURN: Sorted collection
     *  PARAM: [IN] c - collection to sort
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Comparable<? super T>> Collection<T> sort(final Collection<T> c) {
        final ArrayList<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    } /* End of 'SortingUtility::sort' method */
} /* End of 'SortingUtility' class */
