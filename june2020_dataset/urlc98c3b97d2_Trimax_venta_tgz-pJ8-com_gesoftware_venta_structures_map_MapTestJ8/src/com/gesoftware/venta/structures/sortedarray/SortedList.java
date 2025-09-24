package com.gesoftware.venta.structures.sortedarray;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

final class SortedList<T> extends LinkedList<T> {
    private Comparator<? super T> m_Comparator = null;

    public SortedList() {
    }

    public SortedList(final Comparator<? super T> comparator) {
        m_Comparator = comparator;
    }

    @Override
    public final boolean add(final T item) {
        final int insertionPoint = Collections.binarySearch(this, item, m_Comparator);
        super.add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, item);
        return true;
    }

    @Override
    public final boolean addAll(final Collection<? extends T> items) {
        boolean result = false;
        if (items.size() > 4) {
            result = super.addAll(items);
            Collections.sort(this, m_Comparator);
        } else
            for (final T item : items)
                result |= add(item);

        return result;
    }

    public final boolean containsElement(final T item) {
        return (Collections.binarySearch(this, item, m_Comparator) > -1);
    }
}