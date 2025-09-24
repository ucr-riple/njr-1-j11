package com.gesoftware.venta.math.random;

import java.util.Random;

/**
 * Incident class definition
 **/
public final class Incident {
    private final Random m_Generator = new Random();

    /* *
     * METHOD: Generates uniformly distributed value and compares it with given probability
     * RETURN: True if incident is happen, false otherwise
     *  PARAM: [IN] probability - incident's probability (%)
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean isHappen(final double probability) {
        if ((probability < 0.0)||(probability > 100.0))
            throw new IllegalArgumentException("Probability must be between 0 and 100 percents");

        return (m_Generator.nextDouble() * 100.0) <= probability;
    } /* End of 'Incident::isHappen' method */
} /* End of 'Incident' class */
