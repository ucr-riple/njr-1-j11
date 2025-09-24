package com.gesoftware.venta.math.noise;

import com.gesoftware.venta.math.interpolation.Interpolator;

/**
 * Noise generator interface definition
 **/
public interface NoiseGenerator {
    /* *
     * METHOD: Generates noise distributed according to implemented algorithm from [0; 1]
     * RETURN: Noise value
     *  PARAM: [IN] x - absciss
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract double noise(final double x);

    /* *
     * METHOD: Generates noise distributed according to implemented algorithm from [0; 1]
     * RETURN: Noise value
     *  PARAM: [IN] x - absciss
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract double noise(final double x, final double y);

    /* *
     * METHOD: Generates 2D noise
     * RETURN: 2D noise
     *  PARAM: [IN] width  - noise width
     *  PARAM: [IN] height - noise height
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract double[][] generate(final int width, final int height);
} /* End of 'NoiseGenerator' class */
