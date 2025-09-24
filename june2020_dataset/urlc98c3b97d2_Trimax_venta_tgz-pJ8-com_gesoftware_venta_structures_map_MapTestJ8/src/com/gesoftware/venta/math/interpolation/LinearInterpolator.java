package com.gesoftware.venta.math.interpolation;

/**
 * Linear interpolator class definition
 **/
public final class LinearInterpolator implements Interpolator<Double> {
    @Override
    public final Double interpolate(final Double a, final Double b, final double t) {
        if ((t < 0.0)||(t > 1.0))
            throw new IllegalArgumentException("Interpolation value should be between 0.0 and 1.0 (Given: " + t + ")");

        return a * (1.0 - t) + b * t;
    } /* End of 'LinearInterpolator::interpolate' method */
} /* End of 'LinearInterpolator' class */
