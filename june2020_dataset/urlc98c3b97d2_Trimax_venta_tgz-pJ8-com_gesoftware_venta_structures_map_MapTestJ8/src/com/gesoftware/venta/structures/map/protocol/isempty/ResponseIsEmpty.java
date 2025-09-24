package com.gesoftware.venta.structures.map.protocol.isempty;

import java.io.Serializable;

public final class ResponseIsEmpty implements Serializable {
    public final boolean m_IsEmpty;

    public ResponseIsEmpty(final boolean isEmpty) {
        m_IsEmpty = isEmpty;
    }
}
