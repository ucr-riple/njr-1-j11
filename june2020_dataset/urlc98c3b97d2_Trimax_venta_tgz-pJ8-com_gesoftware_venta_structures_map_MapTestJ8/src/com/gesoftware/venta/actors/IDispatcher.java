package com.gesoftware.venta.actors;

import java.io.Serializable;

/**
 * IDispatcher interface definition
 **/
public interface IDispatcher<T> {
    /* *
     * METHOD: Executes each time when object arrives
     * RETURN: Some object if you want it to send back, null otherwise
     *  PARAM: [IN] object - received object
     * AUTHOR: Dmitry Eliseev
     * */
    public Serializable dispatch(final T object);
} /* End of 'IDispatcher' interface */
