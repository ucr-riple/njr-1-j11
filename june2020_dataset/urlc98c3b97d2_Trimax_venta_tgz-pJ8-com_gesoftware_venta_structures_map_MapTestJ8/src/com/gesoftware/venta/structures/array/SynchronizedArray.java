package com.gesoftware.venta.structures.array;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Synchronized array class definition
 **/
public final class SynchronizedArray<T> implements Collection<T> {
    private final ArrayList<T> m_Items;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedArray() {
        this(10);
    } /* End of 'SynchronizedArray::SynchronizedArray' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] initialCapacity - initial array size
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedArray(final int initialCapacity) {
        m_Items = new ArrayList<T>(initialCapacity);
    } /* End of 'SynchronizedArray::SynchronizedArray' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] collection - collection to copy it's content
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedArray(final Collection<? extends T> collection) {
        m_Items = new ArrayList<T>(collection);
    } /* End of 'SynchronizedArray::SynchronizedArray' method */

    @Override
    public final int size() {
        synchronized (m_Items) {
            return m_Items.size();
        }
    } /* End of 'SynchronizedArray::size' method */

    @Override
    public final boolean isEmpty() {
        synchronized (m_Items) {
            return m_Items.isEmpty();
        }
    } /* End of 'SynchronizedArray::isEmpty' method */

    @Override
    public final boolean contains(final Object o) {
        synchronized (m_Items) {
            return m_Items.contains(o);
        }
    } /* End of 'SynchronizedArray::contains' method */

    @Override
    public final Iterator<T> iterator() {
        synchronized (m_Items) {
            return m_Items.iterator();
        }
    } /* End of 'SynchronizedArray::iterator' method */

    @Override
    public final Object[] toArray() {
        synchronized (m_Items) {
            return m_Items.toArray();
        }
    } /* End of 'SynchronizedArray::toArray' method */

    @Override
    @SuppressWarnings("all")
    public final <E> E[] toArray(final E[] items) {
        synchronized (m_Items) {
            return m_Items.toArray(items);
        }
    } /* End of 'SynchronizedArray::toArray' method */

    @Override
    public final boolean add(final T t) {
        synchronized (m_Items) {
            return m_Items.add(t);
        }
    } /* End of 'SynchronizedArray::add' method */

    @Override
    public final boolean remove(final Object o) {
        synchronized (m_Items) {
            return m_Items.remove(o);
        }
    } /* End of 'SynchronizedArray::remove' method */

    @Override
    @SuppressWarnings("all")
    public final boolean containsAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.contains(c);
        }
    } /* End of 'SynchronizedArray::containsAll' method */

    @Override
    public final boolean addAll(final Collection<? extends T> c) {
        synchronized (m_Items) {
            return m_Items.addAll(c);
        }
    } /* End of 'SynchronizedArray::addAll' method */

    @Override
    public final boolean removeAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.removeAll(c);
        }
    } /* End of 'SynchronizedArray::removeAll' method */

    @Override
    public final boolean retainAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.retainAll(c);
        }
    } /* End of 'SynchronizedArray::retainAll' method */

    @Override
    public final void clear() {
        synchronized (m_Items) {
            m_Items.clear();
        }
    } /* End of 'SynchronizedArray::clear' method */

    /* *
     * METHOD: Gets item by it's index
     * RETURN: Item
     *  PARAM: [IN] index - index to get item
     * AUTHOR: Dmitry Eliseev
     * */
    public final T get(final int index) {
        synchronized (m_Items) {
            return m_Items.get(index);
        }
    } /* End of 'SynchronizedArray::get' method */

    /* *
     * METHOD: Sets item by index
     * RETURN: Previously stored item
     *  PARAM: [IN] index - item's index
     *  PARAM: [IN] item  - item to store
     * AUTHOR: Dmitry Eliseev
     * */
    public final T set(final int index, final T item) {
        synchronized (m_Items) {
            return m_Items.set(index, item);
        }
    } /* End of 'SynchronizedArray::set' method */
} /* End of 'SynchronizedArray' class */
