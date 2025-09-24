package com.gesoftware.venta.structures.map.protocol.put;

import java.io.Serializable;

public final class QueryPut implements Serializable {
    public final Serializable m_Key;
    public final Serializable m_Value;

    public QueryPut(final Serializable key, final Serializable value) {
        m_Value = value;
        m_Key   = key;
    }
}