package com.gesoftware.venta.structures.map.protocol.size;

import java.io.Serializable;

public final class ResponseSize implements Serializable {
    public final int m_Size;

    public ResponseSize(final int size) {
        m_Size = size;
    }
}
