package com.gesoftware.venta.structures.map.protocol.get;

import java.io.Serializable;

public final class ResponseGet implements Serializable {
    public final Serializable m_Value;

    public ResponseGet(final Serializable value) {
        m_Value = value;
    }
}
