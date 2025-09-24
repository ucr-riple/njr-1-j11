package com.gesoftware.venta.math.interpolation;

/**
 * Cosine interpolator class definition
 **/
public final class CosineInterpolator implements Interpolator<Double> {
    @Override
    public final Double interpolate(final Double a, final Double b, final double t) {
        if ((t < 0.0)||(t > 1.0))
            throw new IllegalArgumentException("Interpolation value should be between 0.0 and 1.0 (Given: " + t + ")");

        final double f = (1 - Math.cos(t * Math.PI)) * 0.5;
        return a*f + (1.0 - f)*b;
    } /* End of 'CosineInterpolator::interpolate' method */
} /* End of 'CosineInterpolator' class */
