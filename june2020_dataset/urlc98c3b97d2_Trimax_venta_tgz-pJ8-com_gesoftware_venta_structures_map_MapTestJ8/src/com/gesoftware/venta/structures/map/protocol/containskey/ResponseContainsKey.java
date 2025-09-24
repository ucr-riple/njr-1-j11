package com.gesoftware.venta.structures.map.protocol.containskey;

import java.io.Serializable;

public final class ResponseContainsKey implements Serializable {
    public final boolean m_ContainsKey;

    public ResponseContainsKey(final boolean containsKey) {
        m_ContainsKey = containsKey;
    }
}
