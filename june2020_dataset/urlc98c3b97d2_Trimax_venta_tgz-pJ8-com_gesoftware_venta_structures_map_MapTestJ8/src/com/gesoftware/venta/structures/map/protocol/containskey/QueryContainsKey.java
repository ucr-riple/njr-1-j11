package com.gesoftware.venta.structures.map.protocol.containskey;

import java.io.Serializable;

public final class QueryContainsKey implements Serializable {
    public final Serializable m_Key;

    public QueryContainsKey(final Serializable key) {
        m_Key = key;
    }
}
