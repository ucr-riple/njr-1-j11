package com.gesoftware.venta.math.distance;

/**
 * Distance interface definition
 **/
public interface IDistance<T> {
    /* *
     * METHOD: Calculates distance between two objects
     * RETURN: Distance between objects
     *  PARAM: [IN] object1 - first object
     *  PARAM: [IN] object2 - second object
     * AUTHOR: Eliseev Dmitry
     * */
    public double getDistance(final T object1, final T object2);
} /* End of 'IDistance interface */
