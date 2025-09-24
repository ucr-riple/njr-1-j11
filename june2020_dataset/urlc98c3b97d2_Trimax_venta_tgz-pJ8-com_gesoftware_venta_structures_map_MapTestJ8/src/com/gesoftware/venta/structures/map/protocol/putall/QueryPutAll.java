package com.gesoftware.venta.structures.map.protocol.putall;

import java.io.Serializable;
import java.util.Map;

public final class QueryPutAll implements Serializable {
    public final Map<? extends Serializable, ? extends Serializable> m_Map;

    public QueryPutAll(final Map<? extends Serializable, ? extends Serializable> map) {
        m_Map = map;
   }
}
