package com.gesoftware.venta.actors;

import java.io.Serializable;

/**
 * IActor interface definition
 **/
public interface IActor<T> {
    /* *
     * METHOD: Executes each time when object arrives
     * RETURN: Some object if you want it to send back, null otherwise
     *  PARAM: [IN] clientID - unique actor's identifier
     *  PARAM: [IN] object   - received object
     * AUTHOR: Dmitry Eliseev
     * */
    public Serializable react(final String clientID, final T object);
} /* End of 'IActor' interface */
