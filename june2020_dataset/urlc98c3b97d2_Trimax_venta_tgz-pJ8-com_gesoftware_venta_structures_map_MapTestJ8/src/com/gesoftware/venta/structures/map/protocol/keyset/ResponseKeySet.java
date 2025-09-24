package com.gesoftware.venta.structures.map.protocol.keyset;

import java.io.Serializable;
import java.util.Set;

public final class ResponseKeySet implements Serializable {
    public final Set<? extends Serializable> m_Keys;

    public ResponseKeySet(final Set<? extends Serializable> keys) {
        m_Keys = keys;
    }
}
