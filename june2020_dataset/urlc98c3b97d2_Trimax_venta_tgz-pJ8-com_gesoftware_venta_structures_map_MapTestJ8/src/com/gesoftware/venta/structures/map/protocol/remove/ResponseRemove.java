package com.gesoftware.venta.structures.map.protocol.remove;

import java.io.Serializable;

public final class ResponseRemove implements Serializable {
    public final Serializable m_Value;

    public ResponseRemove(final Serializable value) {
        m_Value = value;
    }
}
