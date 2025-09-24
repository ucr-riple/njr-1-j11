package com.gesoftware.venta.structures.map.protocol.remove;

import java.io.Serializable;

public final class QueryRemove implements Serializable {
    public final Serializable m_Key;

    public QueryRemove(final Serializable key) {
        m_Key = key;
    }
}
