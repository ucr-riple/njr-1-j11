package com.gesoftware.venta.math.random;

import java.util.Random;

/**
 * Randomizer class definition
 **/
public final class Randomizer {
    private final static Random c_Random;

    /**
     * Initializes generator
     **/
    static {
        c_Random = new Random();
        c_Random.setSeed(System.currentTimeMillis());
    } /* End of 'Randomizer::static' method */

    /* *
     * METHOD: Generates uniformly distributed value from [0; n)
     * RETURN: Uniformly distributed value from [0; n)
     *  PARAM: [IN] n - right interval border
     * AUTHOR: Eliseev Dmitry
     * */
    public static int nextInteger(final int n) {
        if (n <= 0)
            return 0;

        return c_Random.nextInt(n);
    } /* End of 'Randomizer::nextInteger' method */

    /* *
     * METHOD: Generates uniformly distributed value from [0; Long.MAX_VALUE)
     * RETURN: Uniformly distributed value from [0; Long.MAX_VALUE)
     * AUTHOR: Eliseev Dmitry
     * */
    public static long nextLong() {
        return c_Random.nextLong();
    } /* End of 'Randomizer::nextLong' method */

    /* *
     * METHOD: Generates uniformly distributed value from [0; 1]
     * RETURN: Uniformly distributed value from [0; 1]
     * AUTHOR: Eliseev Dmitry
     * */
    public static double nextDouble() {
        return c_Random.nextDouble();
    } /* End of 'Randomizer::nextDouble' method */

    /* *
     * METHOD: Generates uniformly distributed value from [0; n]
     * RETURN: Uniformly distributed value from [0; n]
     *  PARAM: [IN] n - right interval border
     * AUTHOR: Eliseev Dmitry
     * */
    public static double nextDouble(final double n) {
        return nextDouble() * n;
    } /* End of 'Randomizer::nextDouble' method */

    /* *
     * METHOD: Generates uniformly distributed value from [0; 1]
     * RETURN: Uniformly distributed value from [0; 1]
     * AUTHOR: Eliseev Dmitry
     * */
    public static float nextFloat() {
        return c_Random.nextFloat();
    } /* End of 'Randomizer::nextFloat' method */

    /* *
    * METHOD: Generates uniformly distributed value from [0; n]
    * RETURN: Uniformly distributed value from [0; n]
    *  PARAM: [IN] n - right interval border
    * AUTHOR: Eliseev Dmitry
    * */
    public static float nextFloat(final float n) {
        return nextFloat() * n;
    } /* End of 'Randomizer::nextFloat' method */

    /* *
     * METHOD: Generates uniformly distributed boolean value
     * RETURN: Uniformly distributed boolean value
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean nextBoolean() {
        return c_Random.nextBoolean();
    } /* End of 'Randomizer::nextBoolean' method */

    /* *
     * METHOD: Generates uniformly distributed bytes array
     *  PARAM: [OUT] bytes - array to fill with generated values
     * AUTHOR: Eliseev Dmitry
     * */
    public static void nextBytes(final byte[] bytes) {
        c_Random.nextBytes(bytes);
    } /* End of 'Randomizer::nextBytes' method */

    /* *
     * METHOD: Generates Gauss-distributed value from [0; Double.MAX_VALUE)
     * RETURN: Gauss-distributed value from [0; Double.MAX_VALUE)
     * AUTHOR: Eliseev Dmitry
     * */
    public static double nextGaussian() {
        return c_Random.nextGaussian();
    } /* End of 'Randomizer::nextGaussian' method */
} /* End of 'Randomizer' class */
