package com.gesoftware.venta.math.noise;

import java.util.Random;

/**
 * White noise generator class definition
 **/
public class WhiteNoiseGenerator implements NoiseGenerator {
    private final Random m_Random;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public WhiteNoiseGenerator() {
        m_Random = new Random(System.currentTimeMillis());
    } /* End of 'WhiteNoiseGenerator::WhiteNoiseGenerator' method */

    @Override
    public final double noise(final double x) {
        return m_Random.nextDouble();
    } /* End of 'WhiteNoiseGenerator::noise' method */

    @Override
    public final double noise(final double x, final double y) {
        return m_Random.nextDouble();
    } /* End of 'WhiteNoiseGenerator::noise' method */

    @Override
    public final double[][] generate(final int width, final int height) {
        final double noise[][] = new double[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                noise[x][y] = noise(x, y);

        return noise;
    } /* End of 'WhiteNoiseGenerator::generate' method */
} /* End of 'WhiteNoiseGenerator' class */
