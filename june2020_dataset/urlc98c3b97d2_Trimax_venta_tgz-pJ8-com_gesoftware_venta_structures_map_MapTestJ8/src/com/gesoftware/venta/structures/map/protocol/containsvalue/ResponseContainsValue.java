package com.gesoftware.venta.structures.map.protocol.containsvalue;

import java.io.Serializable;

public final class ResponseContainsValue implements Serializable {
    public final boolean m_ContainsValue;

    public ResponseContainsValue(final boolean containsValue) {
        m_ContainsValue = containsValue;
    }
}