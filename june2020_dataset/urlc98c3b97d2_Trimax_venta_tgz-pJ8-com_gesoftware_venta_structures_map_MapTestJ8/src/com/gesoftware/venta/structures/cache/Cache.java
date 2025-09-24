package com.gesoftware.venta.structures.cache;

import com.gesoftware.venta.structures.map.SynchronizedMap;

import java.io.Serializable;

/**
 * Cache class definition
 **/
public abstract class Cache<K, V> implements Serializable {
    private final SynchronizedMap<K, V> m_Items = new SynchronizedMap<K, V>();

    /* *
     * METHOD: Gets item from cache if cached, load to cache otherwise
     * RETURN: Requested item
     *  PARAM: [IN] key - item key
     * AUTHOR: Dmitry Eliseev
     * */
    public final V get(final K key) {
        synchronized (m_Items) {
            if (!m_Items.containsKey(key))
                m_Items.put(key, cacheItem(key));
        }

        return m_Items.get(key);
    } /* End of 'Cache::get' method */

    /* *
     * METHOD: Gets cache size
     * RETURN: The number of cached items
     * AUTHOR: Dmitry Eliseev
     * */
    public final int size() {
        return m_Items.size();
    } /* End of 'Cache::size' method */

    /* *
     * METHOD: Clears cache
     * AUTHOR: Dmitry Eliseev
     * */
    public final void clear() {
        m_Items.clear();
    } /* End of 'Cache::clear' method */

    /* *
     * METHOD: Loads item from external structure
     * RETURN: Loaded item
     *  PARAM: [IN] key - item key
     * AUTHOR: Dmitry Eliseev
     * */
    protected abstract V cacheItem(final K key);
} /* End of 'Cache' class */
