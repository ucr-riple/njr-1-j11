package com.gesoftware.venta.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Collection utility class definition
 **/
public final class CollectionUtility {
    /* *
     * METHOD: Compares two collections only by items using iterators
     * RETURN: True if collections are same, False otherwise
     *  PARAM: [IN] collection1 - first collection of T
     *  PARAM: [IN] collection2 - second collection of T
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T> boolean equals(final Collection<T> collection1, final Collection<T> collection2) {
        if (collection1.size() != collection2.size())
            return false;

        final Iterator<T> iterator1 = collection1.iterator();
        final Iterator<T> iterator2 = collection2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext())
            if (!iterator1.next().equals(iterator2.next()))
                return false;

        return !(iterator1.hasNext() || iterator2.hasNext());
    } /* End of 'CollectionUtility::equals' method */

    /* *
     * METHOD: Compares two collections only by items ignoring their order using contains all
     * RETURN: True if collections are same, False otherwise
     *  PARAM: [IN] collection1 - first collection of T
     *  PARAM: [IN] collection2 - second collection of T
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T> boolean equalsIgnoreOrder(final Collection<T> collection1, final Collection<T> collection2) {
        return collection1.containsAll(collection2) && collection2.containsAll(collection1);
    } /* End of 'CollectionUtility::equalsIgnoreOrder' method */

    /* *
     * METHOD: Creates a sorted collection by unsorted
     * RETURN: Sorted collection
     *  PARAM: [IN] collection - collection of T to sort
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Comparable<? super T>> Collection<T> sort(final Collection<T> collection) {
        final ArrayList<T> sortedCollection = new ArrayList<T>(collection);
        Collections.sort(sortedCollection);
        return sortedCollection;
    } /* End of 'CollectionUtility::sort' method */

    /* *
     * METHOD: Creates a reversed collection
     * RETURN: Reversed collection
     *  PARAM: [IN] collection - collection of T to reverse
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T> Collection<T> reverse(final Collection<T> collection) {
        final ArrayList<T> reversedCollection = new ArrayList<T>(collection);
        Collections.reverse(reversedCollection);
        return reversedCollection;
    } /* End of 'CollectionUtility::sort' method */

    /* *
     * METHOD: Checks if collection is empty
     * RETURN: True if collection is empty, False otherwise
     *  PARAM: [IN] collection - collection to check
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    } /* End of 'CollectionUtility::isEmpty' method */
} /* End of 'CollectionUtility' class */
