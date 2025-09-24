package com.gesoftware.venta.structures.queue;

import java.io.Serializable;
import java.util.*;

/**
 * Synchronzied queue class definition
 **/
public final class SynchronizedQueue<T> implements Queue<T>, Serializable {
    private final LinkedList<T> m_Items;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedQueue() {
        m_Items = new LinkedList<T>();
    } /* End of 'SynchronizedQueue::SynchronizedQueue' method */

    @Override
    public final int size() {
        synchronized (m_Items) {
            return m_Items.size();
        }
    } /* End of 'SynchronizedQueue::size' method */

    @Override
    public final boolean isEmpty() {
        synchronized (m_Items) {
            return m_Items.isEmpty();
        }
    } /* End of 'SynchronizedQueue::isEmpty' method */

    @Override
    public final boolean contains(final Object o) {
        synchronized (m_Items) {
            return m_Items.contains(o);
        }
    } /* End of 'SynchronizedQueue::contains' method */

    @Override
    public final Iterator<T> iterator() {
        synchronized (m_Items) {
            return m_Items.iterator();
        }
    } /* End of 'SynchronizedQueue::iterator' method */

    @Override
    public final Object[] toArray() {
        synchronized (m_Items) {
            return m_Items.toArray();
        }
    } /* End of 'SynchronizedQueue::toArray' method */

    @Override
    @SuppressWarnings("all")
    public final <A> A[] toArray(final A[] a) {
        synchronized (m_Items) {
            return m_Items.<A>toArray(a);
        }
    } /* End of 'SynchronizedQueue::toArray' method */

    @Override
    public final boolean add(final T t) {
        synchronized (m_Items) {
            return m_Items.add(t);
        }
    } /* End of 'SynchronizedQueue::add' method */

    @Override
    public final boolean remove(final Object o) {
        synchronized (m_Items) {
            return m_Items.remove(o);
        }
    } /* End of 'SynchronizedQueue::remove' method */

    @Override
    public final boolean containsAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.containsAll(c);
        }
    } /* End of 'SynchronizedQueue::' method */

    @Override
    public final boolean addAll(final Collection<? extends T> c) {
        synchronized (m_Items) {
            return m_Items.addAll(c);
        }
    } /* End of 'SynchronizedQueue::containsAll' method */

    @Override
    public final boolean removeAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.removeAll(c);
        }
    } /* End of 'SynchronizedQueue::removeAll' method */

    @Override
    public final boolean retainAll(final Collection<?> c) {
        synchronized (m_Items) {
            return m_Items.retainAll(c);
        }
    } /* End of 'SynchronizedQueue::retainAll' method */

    @Override
    public final void clear() {
        synchronized (m_Items) {
            m_Items.clear();
        }
    } /* End of 'SynchronizedQueue::clear' method */

    @Override
    public final boolean offer(final T t) {
        synchronized (m_Items) {
            return m_Items.offer(t);
        }
    } /* End of 'SynchronizedQueue::offer' method */

    @Override
    public final T remove() {
        synchronized (m_Items) {
            return m_Items.remove();
        }
    } /* End of 'SynchronizedQueue::remove' method */

    @Override
    public final T poll() {
        synchronized (m_Items) {
            return m_Items.poll();
        }
    } /* End of 'SynchronizedQueue::poll' method */

    @Override
    public final T element() {
        synchronized (m_Items) {
            return m_Items.element();
        }
    } /* End of 'SynchronizedQueue::element' method */

    @Override
    public final T peek() {
        synchronized (m_Items) {
            return m_Items.peek();
        }
    } /* End of 'SynchronizedQueue::peek' method */

    @Override
    public final int hashCode() {
        synchronized (m_Items) {
            return m_Items.hashCode();
        }
    } /* End of 'SynchronizedQueue::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        synchronized (m_Items) {
            return obj instanceof SynchronizedQueue && m_Items.equals(obj);
        }
    } /* End of 'SynchronizedQueue::equals' method */

    @Override
    public final String toString() {
        synchronized (m_Items) {
            return m_Items.toString();
        }
    } /* End of 'SynchronizedQueue::toString' method */
} /* End of 'SynchronizedQueue' class */
