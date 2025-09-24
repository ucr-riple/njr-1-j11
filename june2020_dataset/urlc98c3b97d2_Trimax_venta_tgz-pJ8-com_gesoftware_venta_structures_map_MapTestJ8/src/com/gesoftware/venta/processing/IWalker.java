package com.gesoftware.venta.processing;


/**
 * Walker interface definition
 **/
public interface IWalker<T> {
    /* *
     * METHOD: Walks by item
     *  PARAM: [IN] item - item to walk
     * AUTHOR: Eliseev Dmitry
     * */
    public void walk(final T item);
} /* End of 'IWalker' interface */
