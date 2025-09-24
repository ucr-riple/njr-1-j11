package com.gesoftware.venta.math.interpolation;

/**
 * Interpolator interface definition
 **/
public interface Interpolator<N extends Number> {
    /* *
     * METHOD: Interpolates two values using interpolation coefficient
     * RETURN: Interpolated value
     *  PARAM: [IN] a - first number
     *  PARAM: [IN] b - second number
     *  PARAM: [IN] t - interpolation coefficient from [0; 1]
     * AUTHOR: Eliseev Dmitry
     * */
    public N interpolate(final N a, final N b, final double t);
} /* End of 'Interpolator' interface */
