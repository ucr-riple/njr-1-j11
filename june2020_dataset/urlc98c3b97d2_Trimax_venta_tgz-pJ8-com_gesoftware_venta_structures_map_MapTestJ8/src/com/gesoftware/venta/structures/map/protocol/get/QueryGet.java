package com.gesoftware.venta.structures.map.protocol.get;

import java.io.Serializable;

public final class QueryGet implements Serializable {
    public final Serializable m_Key;

    public QueryGet(final Serializable key) {
        m_Key = key;
    }
}
