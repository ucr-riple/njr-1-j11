package com.gesoftware.venta.structures.sortedarray;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public final class SortedArray<E extends Comparable<E>> implements Collection<E> {
    private final SortedList<E> items;

    public SortedArray() {
        this(null);
    }

    public SortedArray(final Comparator<E> comparator) {
        items = new SortedList<E>(comparator);
    }

    @Override
    public final int size() {
        return items.size();
    }

    @Override
    public final boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public final boolean contains(final Object o) {
        return items.contains(o);
    }

    @Override
    public final Iterator<E> iterator() {
        return items.iterator();
    }

    @Override
    public final Object[] toArray() {
        return items.toArray();
    }

    @Override
    @SuppressWarnings("all")
    public final <T> T[] toArray(final T[] a) {
        return items.toArray(a);
    }

    @Override
    public final boolean add(final E e) {
        return items.add(e);
    }

    @Override
    public final boolean remove(final Object o) {
        return items.remove(o);
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        return items.addAll(c);
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        return items.removeAll(c);
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        return items.retainAll(c);
    }

    @Override
    public final void clear() {
        items.clear();
    }

    @Override
    public final boolean equals(final Object o) {
        return o instanceof SortedArray && items.equals(o);
    }

    @Override
    public final int hashCode() {
        return items.hashCode();
    }
}
