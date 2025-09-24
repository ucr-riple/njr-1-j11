package com.gesoftware.venta.structures.map.protocol.containsvalue;

import java.io.Serializable;

public final class QueryContainsValue implements Serializable {
    public final Serializable m_Value;

    public QueryContainsValue(final Serializable value) {
        m_Value = value;
    }
}
