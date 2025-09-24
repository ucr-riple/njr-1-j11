package com.gesoftware.venta.structures.map;

import com.gesoftware.venta.actors.IDispatcher;
import com.gesoftware.venta.actors.Puppeteer;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.structures.map.protocol.clear.QueryClear;
import com.gesoftware.venta.structures.map.protocol.containskey.QueryContainsKey;
import com.gesoftware.venta.structures.map.protocol.containskey.ResponseContainsKey;
import com.gesoftware.venta.structures.map.protocol.containsvalue.QueryContainsValue;
import com.gesoftware.venta.structures.map.protocol.containsvalue.ResponseContainsValue;
import com.gesoftware.venta.structures.map.protocol.entryset.QueryEntrySet;
import com.gesoftware.venta.structures.map.protocol.entryset.ResponseEntrySet;
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
import com.gesoftware.venta.structures.option.Option;

import java.io.Serializable;
import java.util.*;

/**
 * Remote map class definition
 **/
@SuppressWarnings("unchecked")
public final class RemoteMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Serializable {
    private final Puppeteer m_Puppeteer;

    private Option<Integer> m_Size;
    private Boolean m_IsEmpty;

    private Boolean m_ContainsKey;
    private Boolean m_ContainsValue;

    private Option<Object> m_Get;
    private Option<Object> m_Put;

    private Option<Object> m_Remove;

    private Set<? extends Serializable> m_KeySet;
    private Collection<? extends Serializable> m_Values;

    private Set<Entry<? extends Serializable, ? extends Serializable>> m_EntrySet;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] host - host to connect
     *  PARAM: [IN] port - connection port
     * AUTHOR: Dmitry Eliseev
     * */
    public RemoteMap(final String host, final int port) {
        m_Puppeteer = new Puppeteer(host, port);

        registerDispatchers();
    } /* End of 'RemoteMap::RemoteMap' method */

    /* *
     * METHOD: Notifies all listeners
     * AUTHOR: Dmitry Eliseev
     * */
    private void notifyEveryOne() {
        synchronized (this) {
            notifyAll();
        }
    } /* End of 'RemoteMap::notifyEveryOne' method */

    /* *
     * METHOD: Registers dispatchers
     * AUTHOR: Dmitry Eliseev
     * */
    private void registerDispatchers() {
        /* Size dispatcher */
        m_Puppeteer.registerDispatcher(ResponseSize.class, new IDispatcher<ResponseSize>() {
            @Override
            public final Serializable dispatch(final ResponseSize object) {
                m_Size = new Option<Integer>(object.m_Size);
                notifyEveryOne();
                return null;
            }
        });

        /* Is empty dispatcher */
        m_Puppeteer.registerDispatcher(ResponseIsEmpty.class, new IDispatcher<ResponseIsEmpty>() {
            @Override
            public final Serializable dispatch(final ResponseIsEmpty object) {
                m_IsEmpty = object.m_IsEmpty;
                notifyEveryOne();
                return null;
            }
        });

        /* Contains key dispatcher */
        m_Puppeteer.registerDispatcher(ResponseContainsKey.class, new IDispatcher<ResponseContainsKey>() {
            @Override
            public final Serializable dispatch(final ResponseContainsKey object) {
                m_ContainsKey = object.m_ContainsKey;
                notifyEveryOne();
                return null;
            }
        });

        /* Contains value dispatcher */
        m_Puppeteer.registerDispatcher(ResponseContainsValue.class, new IDispatcher<ResponseContainsValue>() {
            @Override
            public final Serializable dispatch(final ResponseContainsValue object) {
                m_ContainsValue = object.m_ContainsValue;
                notifyEveryOne();
                return null;
            }
        });

        /* Get dispatcher */
        m_Puppeteer.registerDispatcher(ResponseGet.class, new IDispatcher<ResponseGet>() {
            @Override
            public final Serializable dispatch(final ResponseGet object) {
                m_Get = new Option<Object>(object.m_Value);
                notifyEveryOne();
                return null;
            }
        });

        /* Put dispatcher */
        m_Puppeteer.registerDispatcher(ResponsePut.class, new IDispatcher<ResponsePut>() {
            @Override
            public final Serializable dispatch(final ResponsePut object) {
                m_Put = new Option<Object>(object.m_Value);
                notifyEveryOne();
                return null;
            }
        });

        /* Remove dispatcher */
        m_Puppeteer.registerDispatcher(ResponseRemove.class, new IDispatcher<ResponseRemove>() {
            @Override
            public final Serializable dispatch(final ResponseRemove object) {
                m_Remove = new Option<Object>(object.m_Value);
                notifyEveryOne();
                return null;
            }
        });

        /* Key set dispatcher */
        m_Puppeteer.registerDispatcher(ResponseKeySet.class, new IDispatcher<ResponseKeySet>() {
            @Override
            public final Serializable dispatch(final ResponseKeySet object) {
                m_KeySet = object.m_Keys;
                notifyEveryOne();
                return null;
            }
        });

        /* Values dispatcher */
        m_Puppeteer.registerDispatcher(ResponseValues.class, new IDispatcher<ResponseValues>() {
            @Override
            public final Serializable dispatch(final ResponseValues object) {
                m_Values = object.m_Values;
                notifyEveryOne();
                return null;
            }
        });

        /* Entry set dispatcher */
        m_Puppeteer.registerDispatcher(ResponseEntrySet.class, new IDispatcher<ResponseEntrySet>() {
            @Override
            public final Serializable dispatch(final ResponseEntrySet object) {
                m_EntrySet = object.m_EntrySet;
                notifyEveryOne();
                return null;
            }
        });
    } /* End of 'RemoteMap::registerDispatchers' method */

    @Override
    public final int size() {
        m_Puppeteer.giveCommand(new QuerySize());

        synchronized (this) {
            try {
                while (m_Size == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_Size == null)
            return 0;

        final int size = m_Size.get();
        m_Size = null;
        return size;
    } /* End of 'RemoteMap::size' method */

    @Override
    public boolean isEmpty() {
        m_Puppeteer.giveCommand(new QueryIsEmpty());

        synchronized (this) {
            try {
                while (m_IsEmpty == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_IsEmpty == null)
            return true;

        final boolean isEmpty = m_IsEmpty;
        m_IsEmpty = null;
        return isEmpty;
    } /* End of 'RemoteMap::isEmpty' method */

    @Override
    public final boolean containsKey(final Object key) {
        if (!(key instanceof Serializable))
            return false;

        m_Puppeteer.giveCommand(new QueryContainsKey((Serializable) key));

        synchronized (this) {
            try {
                while (m_ContainsKey == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_ContainsKey == null)
            return true;

        final boolean containsKey = m_ContainsKey;
        m_ContainsKey = null;
        return containsKey;
    } /* End of 'RemoteMap::containsKey' method */

    @Override
    public final boolean containsValue(final Object value) {
        if (!(value instanceof Serializable))
            return false;

        m_Puppeteer.giveCommand(new QueryContainsValue((Serializable) value));

        synchronized (this) {
            try {
                while (m_ContainsValue == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_ContainsValue == null)
            return true;

        final boolean containsValue = m_ContainsValue;
        m_ContainsValue = null;
        return containsValue;
    } /* End of 'RemoteMap::containsValue' method */

    @Override
    public final V get(final Object key) {
        if (!(key instanceof Serializable))
            return null;

        m_Puppeteer.giveCommand(new QueryGet((Serializable) key));

        synchronized (this) {
            try {
                while (m_Get == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_Get == null)
            return null;

        final Object get = m_Get.get();
        m_Get = null;

        return (V) get;
    } /* End of 'RemoteMap::get' method */

    @Override
    public final V put(final K key, final V value) {
        m_Puppeteer.giveCommand(new QueryPut(key, value));

        synchronized (this) {
            try {
                while (m_Put == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_Put == null)
            return null;

        final Object put = m_Put.get();
        m_Put = null;

        return (V) put;
    } /* End of 'RemoteMap::put' method */

    @Override
    public final V remove(final Object key) {
        if (!(key instanceof Serializable))
            return null;

        m_Puppeteer.giveCommand(new QueryRemove((Serializable) key));

        synchronized (this) {
            try {
                while (m_Remove == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_Remove == null)
            return null;

        final Object remove = m_Remove.get();
        m_Remove = null;

        return (V) remove;
    } /* End of 'RemoteMap::remove' method */

    @Override
    public final void putAll(final Map<? extends K, ? extends V> m) {
        if (!(m instanceof Serializable))
            return;

        m_Puppeteer.giveCommand(new QueryPutAll(m));
    } /* End of 'RemoteMap::putAll' method */

    @Override
    public final void clear() {
        m_Puppeteer.giveCommand(new QueryClear());
    } /* End of 'RemoteMap::clear' method */

    @Override
    public final Set<K> keySet() {
        m_Puppeteer.giveCommand(new QueryKeySet());

        synchronized (this) {
            try {
                while (m_KeySet == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_KeySet == null)
            return new HashSet<K>();

        final Object keySet = m_KeySet;
        m_KeySet = null;

        return (Set<K>) keySet;
    } /* End of 'RemoteMap::keySet' method */

    @Override
    public final Collection<V> values() {
        m_Puppeteer.giveCommand(new QueryValues());

        synchronized (this) {
            try {
                while (m_Values == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_Values == null)
            return new LinkedList<V>();

        final Object values = m_Values;
        m_Values = null;

        return (Collection<V>) values;
    } /* End of 'RemoteMap::values' method */

    @Override
    public final Set<Entry<K, V>> entrySet() {
        m_Puppeteer.giveCommand(new QueryEntrySet());

        synchronized (this) {
            try {
                while (m_EntrySet == null)
                    wait();
            } catch (final InterruptedException e) {
                LoggingUtility.error("Can't wait: " + e.getMessage());
            }
        }

        if (m_EntrySet == null)
            return new HashSet<Entry<K, V>>();

        final Object entrySet = m_EntrySet;
        m_EntrySet = null;

        return (HashSet<Entry<K, V>>) entrySet;
    } /* End of 'RemoteMap::entrySet' method */

    @Override
    protected void finalize() throws Throwable {
        LoggingUtility.core("Remote map removed");
        m_Puppeteer.finish();

        super.finalize();
    } /* End of 'RemoteMap::finalize' method */
} /* End of 'RemoteMap' class */
