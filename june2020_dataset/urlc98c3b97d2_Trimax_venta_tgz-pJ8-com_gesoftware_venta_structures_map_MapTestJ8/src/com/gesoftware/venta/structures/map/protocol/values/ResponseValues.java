package com.gesoftware.venta.structures.map.protocol.values;

import java.io.Serializable;
import java.util.Collection;

public final class ResponseValues implements Serializable {
    public final Collection<? extends Serializable> m_Values;

    public ResponseValues(final Collection<? extends Serializable> values) {
        m_Values = values;
    }
}
