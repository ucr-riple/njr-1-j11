package com.gesoftware.venta.math.noise;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.math.interpolation.Interpolator;

import java.util.Random;

/**
 * Perlin noise class definition
 **/
public final class PerlinNoiseGenerator implements NoiseGenerator {
    private final static int c_MaximalVerticesCount     = 256;
    private final static int c_MaximalMaskVerticesCount = c_MaximalVerticesCount - 1;

    private final WhiteNoiseGenerator m_WhiteNoiseGenerator;
    private final Interpolator<Double> m_Interpolator;
    private final double m_Persistance;
    private final int m_OctavesCount;

    private final double m_WhiteNoise[] = new double[c_MaximalVerticesCount];
    private final int m_Permutations[]  = new int[c_MaximalVerticesCount * 2];

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] interpolator - interpolator for smoothing points
     *  PARAM: [IN] persistance  - level difference coefficient
     *  PARAM: [IN] numOctaves   - the number of levels to merge
     * AUTHOR: Eliseev Dmitry
     * */
    public PerlinNoiseGenerator(final Interpolator<Double> interpolator, final double persistance, final int numOctaves) {
        m_WhiteNoiseGenerator = new WhiteNoiseGenerator();
        m_OctavesCount        = numOctaves;
        m_Interpolator        = interpolator;
        m_Persistance         = persistance;

        generateWhiteNoise();
        generatePermutations();
    } /* End of 'PerlinNoiseGenerator::PerlinNoiseGenerator' method */

    /* *
     * METHOD: Generates uniformly distributed white noise
     * AUTHOR: Eliseev Dmitry
     * */
    private void generateWhiteNoise() {
        for (int i = 0; i < c_MaximalVerticesCount; i++) {
            m_WhiteNoise[i]   = m_WhiteNoiseGenerator.noise(i);
            m_Permutations[i] = i;
        }
    } /* End of 'PerlinNoiseGenerator::generateWhiteNoise' method */

    /* *
     * METHOD: Generates random permutations
     * AUTHOR: Eliseev Dmitry
     * */
    private void generatePermutations() {
        final Random random = new Random();
        for ( int i = 0; i < c_MaximalVerticesCount; i++) {
            int swapIndex = random.nextInt(c_MaximalMaskVerticesCount);
            int temp = m_Permutations[ swapIndex ];
            m_Permutations[ swapIndex ] = m_Permutations[ i ];
            m_Permutations[ i ] = temp;
            m_Permutations[ i + c_MaximalVerticesCount] = m_Permutations[ i ];
        }
    } /* End of 'PerlinNoiseGenerator::generatePermutations' method */

    /* *
     * METHOD: Determines integer part of value
     * RETURN: Integer part of value
     *  PARAM: [IN] v - real value
     * AUTHOR: Eliseev Dmitry
     * */
    private int floor(double v) {
        return (int) Math.floor(v);
    } /* End of 'PerlinNoiseGenerator::floor' method */

    /* *
     * METHOD: Smoothering polynom
     * RETURN: Smoothed value
     *  PARAM: [IN] t - value to smooth
     * AUTHOR: Eliseev Dmitry
     * */
    private double smooth(final double t) {
        return t * t * (3 - 2 * t);
    } /* End of 'PerlinNoiseGenerator::smooth' method */

    /* *
     * METHOD: Generates 1D noise according to Perlin's algorithm
     * RETURN: Perlin's noise value
     *  PARAM: [IN] x - abscissa
     * AUTHOR: Eliseev Dmitry
     * */
    private double perlinNoise(final double x) {
        final int integerX = floor(x);

        final double fractionalX = x - integerX;

        final int rx0 = integerX & c_MaximalMaskVerticesCount;
        final int rx1 = (rx0 + 1) & c_MaximalMaskVerticesCount;

        final double sx = smooth(fractionalX);

        final double c0 = m_WhiteNoise[m_Permutations[m_Permutations[rx0]]];
        final double c1 = m_WhiteNoise[m_Permutations[m_Permutations[rx1]]];

        return m_Interpolator.interpolate(c0, c1, sx);
    } /* End of 'PerlinNoiseGenerator::PerlinNoiseGenerator' method */

    /* *
     * METHOD: Generates 2D noise according to Perlin's algorithm
     * RETURN: Perlin's noise value
     *  PARAM: [IN] x - abscissa
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    private double perlinNoise(final double x, final double y) {
        final int integerX = floor(x);
        final int integerY = floor(y);

        final double fractionalX = x - integerX;
        final double fractionalY = y - integerY;

        final int rx0 = integerX & c_MaximalMaskVerticesCount;
        final int rx1 = (rx0 + 1) & c_MaximalMaskVerticesCount;

        final int ry0 = integerY & c_MaximalMaskVerticesCount;
        final int ry1 = ( ry0 + 1 ) & c_MaximalMaskVerticesCount;

        final double c00 = m_WhiteNoise[m_Permutations[m_Permutations[rx0] + ry0]];
        final double c10 = m_WhiteNoise[m_Permutations[m_Permutations[rx1] + ry0]];
        final double c01 = m_WhiteNoise[m_Permutations[m_Permutations[rx0] + ry1]];
        final double c11 = m_WhiteNoise[m_Permutations[m_Permutations[rx1] + ry1]];

        final double sx = smooth(fractionalX);
        final double sy = smooth(fractionalY);

        final double nx0 = m_Interpolator.interpolate( c00, c10, sx );
        final double nx1 = m_Interpolator.interpolate( c01, c11, sx );

        return m_Interpolator.interpolate( nx0, nx1, sy );
    } /* End of 'PerlinNoiseGenerator::perlinNoise' method */

    @Override
    public final double noise(final double x) {
        double frequency = 1.0;
        double amplitude = 1.0;
        double fractalNoise = 0;
        double amplitudesSum = 0.0;
        for ( int k = 0; k < m_OctavesCount; ++k ) {
            amplitudesSum += amplitude;
            fractalNoise  += perlinNoise(x * frequency) * amplitude;
            amplitude     *= m_Persistance;
            frequency     *= 2;
        }

        /* Normalization */
        return fractalNoise / amplitudesSum;
    } /* End of 'PerlinNoiseGenerator::noise' method */

    @Override
    public final double noise(final double x, final double y) {
        double frequency = 1.0;
        double amplitude = 1.0;
        double fractalNoise = 0;
        double amplitudesSum = 0.0;
        for (int k = 0; k < m_OctavesCount; k++) {
            amplitudesSum += amplitude;
            fractalNoise  += perlinNoise(x * frequency, y * frequency) * amplitude;
            amplitude     *= m_Persistance;
            frequency     *= 2;
        }

        /* Normalization */
        return fractalNoise / amplitudesSum;
    } /* End of '' method */

    @Override
    public final double[][] generate(final int width, final int height) {
        final double inversedWidth  = 1.0 / width;
        final double inversedHeight = 1.0 / height;

        generateWhiteNoise();
        generatePermutations();

        final double perlinNoise[][] = new double[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                perlinNoise[x][y] = noise(m_OctavesCount * x * inversedWidth, m_OctavesCount * y * inversedHeight);

        return perlinNoise;
    } /* End of 'PerlinNoiseGenerator::generate' method */
} /* End of 'PerlinNoiseGenerator' class */
