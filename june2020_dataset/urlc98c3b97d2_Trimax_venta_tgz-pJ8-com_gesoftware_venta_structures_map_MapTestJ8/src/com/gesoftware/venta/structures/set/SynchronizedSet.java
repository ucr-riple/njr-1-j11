package com.gesoftware.venta.structures.set;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Synchronized set class definition
 */
public final class SynchronizedSet<E> implements Set<E>, Serializable {
    private final HashSet<E> m_Set;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedSet() {
        m_Set = new HashSet<E>();
    } /* End of 'SynchronizedSet::SynchronizedSet' method */

    @Override
    public final int size() {
        synchronized (m_Set) {
            return m_Set.size();
        }
    } /* End of 'SynchronizedSet::size' method */

    @Override
    public final boolean isEmpty() {
        synchronized (m_Set) {
            return m_Set.isEmpty();
        }
    } /* End of 'SynchronizedSet::isEmpty' method */

    @Override
    public final boolean contains(final Object o) {
        synchronized (m_Set) {
            return m_Set.contains(o);
        }
    } /* End of 'SynchronizedSet::contains' method */

    @Override
    public final Iterator<E> iterator() {
        synchronized (m_Set) {
            return m_Set.iterator();
        }
    } /* End of 'SynchronizedSet::iterator' method */

    @Override
    public final Object[] toArray() {
        synchronized (m_Set) {
            return m_Set.toArray();
        }
    } /* End of 'SynchronizedSet::toArray' method */

    @Override
    @SuppressWarnings("all")
    public final <T> T[] toArray(T[] a) {
        synchronized (m_Set) {
            return m_Set.toArray(a);
        }
    } /* End of 'SynchronizedSet::toArray' method */

    @Override
    public final boolean add(final E e) {
        synchronized (m_Set) {
            return m_Set.add(e);
        }
    } /* End of 'SynchronizedSet::add' method */

    @Override
    public final boolean remove(final Object o) {
        synchronized (m_Set) {
            return m_Set.remove(o);
        }
    } /* End of 'SynchronizedSet::remove' method */

    @Override
    public final boolean containsAll(final Collection<?> c) {
        synchronized (m_Set) {
            return m_Set.containsAll(c);
        }
    } /* End of 'SynchronizedSet::containsAll' method */

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        synchronized (m_Set) {
            return m_Set.addAll(c);
        }
    } /* End of 'SynchronizedSet::addAll' method */

    @Override
    public final boolean retainAll(final Collection<?> c) {
        synchronized (m_Set) {
            return m_Set.retainAll(c);
        }
    } /* End of 'SynchronizedSet::retainAll' method */

    @Override
    public final boolean removeAll(final Collection<?> c) {
        synchronized (m_Set) {
            return m_Set.removeAll(c);
        }
    } /* End of 'SynchronizedSet::removeAll' method */

    @Override
    public final void clear() {
        synchronized (m_Set) {
            m_Set.clear();
        }
    } /* End of 'SynchronizedSet::clear' method */

    @Override
    public final int hashCode() {
        synchronized (m_Set) {
            return m_Set.hashCode();
        }
    } /* End of 'SynchronizedSet::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        synchronized (m_Set) {
            return obj instanceof SynchronizedSet && m_Set.equals(obj);
        }
    } /* End of 'SynchronizedSet::equals' method */

    @Override
    public final String toString() {
        synchronized (m_Set) {
            return m_Set.toString();
        }
    } /* End of 'SynchronizedSet::toString' method */
} /* End of 'SynchronizedSet' class */
