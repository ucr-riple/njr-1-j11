package com.gesoftware.venta.structures.queue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Fixed queue class
 **/
public final class FixedQueue<T> implements Serializable {
    private final ArrayList<T> m_Items;
    private final int m_Size;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] size - queue size
     * AUTHOR: Dmitry Eliseev
     * */
    public FixedQueue(final int size) {
        if (size <= 0)
            throw new NegativeArraySizeException();

        m_Items = new ArrayList<T>(size);
        m_Size  = size;
    } /* End of 'FixedQueue::FixedQueue' method */

    /* *
     * METHOD: Removes unnecessary items from queue
     * AUTHOR: Dmitry Eliseev
     * */
    private void balance() {
        if (m_Items.size() > m_Size)
            m_Items.remove(0);
    } /* End of 'FixedQueue::balance' method */

    /* *
     * METHOD: Adds an item to queue
     *  PARAM: [IN] item - item to add
     * AUTHOR: Dmitry Eliseev
     * */
    public final void push(final T item) {
        synchronized (m_Items) {
            m_Items.add(item);
            balance();
        }
    } /* End of 'FixedQueue::push' method */

    /* *
     * METHOD: Obtain items collection
     * RETURN: Items collection
     * AUTHOR: Dmitry Eliseev
     * */
    public final Collection<T> values() {
        synchronized (m_Items) {
            return new ArrayList<T>(m_Items);
        }
    } /* End of 'FixedQueue::values' method */

    /* *
     * METHOD: Clears queue
     * AUTHOR: Dmitry Eliseev
     * */
    public final void clear() {
        synchronized (m_Items) {
            m_Items.clear();
        }
    } /* End of 'FixedQueue::clear' method */
} /* End of 'FixedQueue' class */
