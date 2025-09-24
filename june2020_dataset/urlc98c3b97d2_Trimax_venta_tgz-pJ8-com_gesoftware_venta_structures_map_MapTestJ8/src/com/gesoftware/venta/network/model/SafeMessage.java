package com.gesoftware.venta.network.model;

import java.io.Serializable;
import java.util.List;

/* *
 * Safe message class definition
 * */
public final class SafeMessage implements Serializable {
    /* Encrypted blocks */
    private final List<byte[]> m_Blocks;

    /* *
     * METHOD: Safe message class constructor
     *  PARAM: [IN] blocks - encrypted blocks
     * AUTHOR: Eliseev Dmitry
     * */
    public SafeMessage(final List<byte[]> blocks) {
        m_Blocks = blocks;
    } /* End of 'SafeMessage::SafeMessage' method */

    /* *
     * METHOD: Gets encrypted blocks list
     * RETURN: Encrypted blocks
     * AUTHOR: Eliseev Dmitry
     * */
    public final List<byte[]> getBlocks() {
        return m_Blocks;
    } /* End of 'SafeMessage::getBlocks' method */
} /* End of 'SafeMessage' class */
