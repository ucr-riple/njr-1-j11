package com.gesoftware.venta.structures.map;

import java.io.Serializable;
import java.util.*;

/**
 * Synchronized map class definition
 **/
public final class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
    private final HashMap<K, V> m_Map;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Dmitry Eliseev
     * */
    public SynchronizedMap() {
        m_Map = new HashMap<K, V>();
    } /* End of 'SynchronizedMap::SynchronizedMap' method */

    @Override
    public final int size() {
        synchronized (m_Map) {
            return m_Map.size();
        }
    } /* End of 'SynchronizedMap::size' method */

    @Override
    public final boolean isEmpty() {
        synchronized (m_Map) {
            return m_Map.isEmpty();
        }
    } /* End of 'SynchronizedMap::isEmpty' method */

    @Override
    public final boolean containsKey(final Object key) {
        synchronized (m_Map) {
            return m_Map.containsKey(key);
        }
    } /* End of 'SynchronizedMap::containsKey' method */

    @Override
    public final boolean containsValue(final Object value) {
        synchronized (m_Map) {
            return m_Map.containsValue(value);
        }
    } /* End of 'SynchronizedMap::containsValue' method */

    @Override
    public final V get(final Object key) {
        synchronized (m_Map) {
            return m_Map.get(key);
        }
    } /* End of 'SynchronizedMap::get' method */

    @Override
    public final V put(final K key, final V value) {
        synchronized (m_Map) {
            return m_Map.put(key, value);
        }
    } /* End of 'SynchronizedMap::put' method */

    @Override
    public final V remove(final Object key) {
        synchronized (m_Map) {
            return m_Map.remove(key);
        }
    } /* End of 'SynchronizedMap::remove' method */

    @Override
    public final void putAll(final Map<? extends K, ? extends V> m) {
        synchronized (m_Map) {
            m_Map.putAll(m);
        }
    } /* End of 'SynchronizedMap::putAll' method */

    @Override
    public final void clear() {
        synchronized (m_Map) {
            m_Map.clear();
        }
    } /* End of 'SynchronizedMap::clear' method */

    @Override
    public final Set<K> keySet() {
        synchronized (m_Map) {
            return new HashSet<K>(m_Map.keySet());
        }
    } /* End of 'SynchronizedMap::keySet' method */

    @Override
    public final Collection<V> values() {
        synchronized (m_Map) {
            return new ArrayList<V>(m_Map.values());
        }
    } /* End of 'SynchronizedMap::values' method */

    @Override
    public final Set<Entry<K, V>> entrySet() {
        synchronized (m_Map) {
            return new HashSet<Entry<K, V>>(m_Map.entrySet());
        }
    } /* End of 'SynchronizedMap::entrySet' method */

    @Override
    public final int hashCode() {
        synchronized (m_Map) {
            return m_Map.hashCode();
        }
    } /* End of 'SynchronizedMap::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        synchronized (m_Map) {
            return obj instanceof SynchronizedMap && m_Map.equals(obj);
        }
    } /* End of 'SynchronizedMap::equals' method */

    @Override
    public final String toString() {
        synchronized (m_Map) {
            return m_Map.toString();
        }
    } /* End of 'SynchronizedMap::toString' method */
} /* End of 'SynchronizedMap' class */
