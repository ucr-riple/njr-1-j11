package com.gesoftware.venta.structures.map.protocol.put;

import java.io.Serializable;

public final class ResponsePut implements Serializable {
    public final Serializable m_Value;

    public ResponsePut(final Serializable value) {
        m_Value = value;
    }
}
