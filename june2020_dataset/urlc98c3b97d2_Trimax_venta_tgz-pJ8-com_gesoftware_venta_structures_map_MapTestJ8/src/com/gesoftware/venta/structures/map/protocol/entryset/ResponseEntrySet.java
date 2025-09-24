package com.gesoftware.venta.structures.map.protocol.entryset;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public final class ResponseEntrySet implements Serializable {
    public final Set<Map.Entry<? extends Serializable, ? extends Serializable>> m_EntrySet;

    public ResponseEntrySet(final Set<Map.Entry<? extends Serializable, ? extends Serializable>> entrySet) {
        m_EntrySet = entrySet;
    }
}
