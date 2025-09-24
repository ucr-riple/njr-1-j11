package com.gesoftware.venta.structures.map;

import com.gesoftware.venta.actors.IActor;
import com.gesoftware.venta.actors.Theater;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.structures.map.protocol.clear.QueryClear;
import com.gesoftware.venta.structures.map.protocol.common.ResponseOK;
import com.gesoftware.venta.structures.map.protocol.containskey.QueryContainsKey;
import com.gesoftware.venta.structures.map.protocol.containskey.ResponseContainsKey;
import com.gesoftware.venta.structures.map.protocol.containsvalue.QueryContainsValue;
import com.gesoftware.venta.structures.map.protocol.containsvalue.ResponseContainsValue;
import com.gesoftware.venta.structures.map.protocol.get.QueryGet;
import com.gesoftware.venta.structures.map.protocol.get.ResponseGet;
import com.gesoftware.venta.structures.map.protocol.isempty.QueryIsEmpty;
import com.gesoftware.venta.structures.map.protocol.isempty.ResponseIsEmpty;
import com.gesoftware.venta.structures.map.protocol.keyset.QueryKeySet;
import com.gesoftware.venta.structures.map.protocol.keyset.ResponseKeySet;
import com.gesoftware.venta.structures.map.protocol.put.QueryPut;
import com.gesoftware.venta.structures.map.protocol.put.ResponsePut;
import com.gesoftware.venta.structures.map.protocol.putall.QueryPutAll;
import com.gesoftware.venta.structures.map.protocol.remove.QueryRemove;
import com.gesoftware.venta.structures.map.protocol.remove.ResponseRemove;
import com.gesoftware.venta.structures.map.protocol.size.QuerySize;
import com.gesoftware.venta.structures.map.protocol.size.ResponseSize;
import com.gesoftware.venta.structures.map.protocol.values.QueryValues;
import com.gesoftware.venta.structures.map.protocol.values.ResponseValues;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Shared map class definition
 **/
@SuppressWarnings("unchecked")
public final class SharedMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Serializable {
    private final Map<K, V> m_Map;
    private Theater m_Theater;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] port - port to listen incoming connections
     * AUTHOR: Dmitry Eliseev
     * */
    public SharedMap(final int port) {
        m_Map     = new SynchronizedMap<K, V>();

        m_Theater = new Theater(port);
        new Thread(m_Theater).start();
        registerActors();
    } /* End of 'SharedMap::SharedMap' method */

    /* *
     * METHOD: Registers actors for incoming commands
     * AUTHOR: Dmitry Eliseev
     * */
    private void registerActors() {
        /* Size query */
        m_Theater.registerActor(QuerySize.class, new IActor<QuerySize>() {
            @Override
            public final Serializable react(final String clientID, final QuerySize object) {
                LoggingUtility.core("User <" + clientID + "> queried map size");
                return new ResponseSize(size());
            }
        });

        /* Is empty query */
        m_Theater.registerActor(QueryIsEmpty.class, new IActor<QueryIsEmpty>() {
            @Override
            public final Serializable react(final String clientID, final QueryIsEmpty object) {
                LoggingUtility.core("User <" + clientID + "> queried map emptiness");
                return new ResponseIsEmpty(isEmpty());
            }
        });

        /* Contains key query */
        m_Theater.registerActor(QueryContainsKey.class, new IActor<QueryContainsKey>() {
            @Override
            public final Serializable react(final String clientID, final QueryContainsKey object) {
                LoggingUtility.core("User <" + clientID + "> queried key contains: " + object.m_Key);
                return new ResponseContainsKey(containsKey(object.m_Key));
            }
        });

        /* Contains value query */
        m_Theater.registerActor(QueryContainsValue.class, new IActor<QueryContainsValue>() {
            @Override
            public final Serializable react(final String clientID, final QueryContainsValue object) {
                LoggingUtility.core("User <" + clientID + "> queried value contains: " + object.m_Value);
                return new ResponseContainsValue(containsValue(object.m_Value));
            }
        });

        /* Get query */
        m_Theater.registerActor(QueryGet.class, new IActor<QueryGet>() {
            @Override
            public final Serializable react(final String clientID, final QueryGet object) {
                LoggingUtility.core("User <" + clientID + "> queried value: " + object.m_Key);
                return new ResponseGet(get(object.m_Key));
            }
        });

        /* Put query */
        m_Theater.registerActor(QueryPut.class, new IActor<QueryPut>() {
            @Override
            public final Serializable react(final String clientID, final QueryPut object) {
                LoggingUtility.core("User <" + clientID + "> put: " + object.m_Key + " -> " + object.m_Value);
                return new ResponsePut(put((K) object.m_Key, (V) object.m_Value));
            }
        });

        /* Remove query */
        m_Theater.registerActor(QueryRemove.class, new IActor<QueryRemove>() {
            @Override
            public final Serializable react(final String clientID, final QueryRemove object) {
                LoggingUtility.core("User <" + clientID + "> removed: " + object.m_Key);
                return new ResponseRemove(remove(object.m_Key));
            }
        });

        /* Put all query */
        m_Theater.registerActor(QueryPutAll.class, new IActor<QueryPutAll>() {
            @Override
            public final Serializable react(final String clientID, final QueryPutAll object) {
                putAll((Map<? extends K, ? extends V>) object.m_Map);
                LoggingUtility.core("User <" + clientID + "> put all: " + object.m_Map);
                return new ResponseOK();
            }
        });

        /* Key set query */
        m_Theater.registerActor(QueryKeySet.class, new IActor<QueryKeySet>() {
            @Override
            public final Serializable react(final String clientID, final QueryKeySet object) {
                LoggingUtility.core("User <" + clientID + "> queried key set");
                return new ResponseKeySet(keySet());
            }
        });

        /* Clear query */
        m_Theater.registerActor(QueryClear.class, new IActor<QueryClear>() {
            @Override
            public final Serializable react(final String clientID, final QueryClear object) {
                clear();
                LoggingUtility.core("User <" + clientID + "> cleared map");
                return new ResponseOK();
            }
        });

        /* Values query */
        m_Theater.registerActor(QueryValues.class, new IActor<QueryValues>() {
            @Override
            public final Serializable react(final String clientID, final QueryValues object) {
                LoggingUtility.core("User <" + clientID + "> queried values");
                return new ResponseValues(values());
            }
        });
    }  /* End of 'SharedMap::registerActors' method */

    @Override
    public final int size() {
        return m_Map.size();
    } /* End of 'SharedMap::size' method */

    @Override
    public final boolean isEmpty() {
        return m_Map.isEmpty();
    } /* End of 'SharedMap::isEmpty' method */

    @Override
    public final boolean containsKey(final Object key) {
        return m_Map.containsKey(key);
    } /* End of 'SharedMap::containsKey' method */

    @Override
    public final boolean containsValue(final Object value) {
        return m_Map.containsValue(value);
    } /* End of 'SharedMap::containsValue' method */

    @Override
    public final V get(final Object key) {
        return m_Map.get(key);
    } /* End of 'SharedMap::get' method */

    @Override
    public final V put(final K key, final V value) {
        return m_Map.put(key, value);
    } /* End of 'SharedMap::put' method */

    @Override
    public final V remove(final Object key) {
        return m_Map.remove(key);
    } /* End of 'SharedMap::remove' method */

    @Override
    public final void putAll(final Map<? extends K, ? extends V> m) {
        m_Map.putAll(m);
    } /* End of 'SharedMap::putAll' method */

    @Override
    public final void clear() {
        m_Map.clear();
    } /* End of 'SharedMap::clear' method */

    @Override
    public final Set<K> keySet() {
        return m_Map.keySet();
    } /* End of 'SharedMap::keySet' method */

    @Override
    public final Collection<V> values() {
        return m_Map.values();
    } /* End of 'SharedMap::values' method */

    @Override
    public final Set<Entry<K, V>> entrySet() {
        return m_Map.entrySet();
    } /* End of 'SharedMap::entrySet' method */

    @Override
    protected final void finalize() throws Throwable {
        m_Theater.finish();

        super.finalize();
    } /* End of 'SharedMap::finalize' method */
} /* End of 'SharedMap' class */
