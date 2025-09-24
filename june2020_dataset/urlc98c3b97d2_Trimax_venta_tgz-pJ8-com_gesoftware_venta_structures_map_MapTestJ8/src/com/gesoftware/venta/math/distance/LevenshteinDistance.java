package com.gesoftware.venta.math.distance;

/**
 * Levensthtein distance class definition
 **/
public final class LevenshteinDistance implements IDistance<String> {
    /* *
     * METHOD: Calculates the minumum of three integers
     * RETURN: The minimal number of three given
     *  PARAM: [IN] a - first integer
     *  PARAM: [IN] b - second integer
     *  PARAM: [IN] c - third integer
     * AUTHOR: Eliseev Dmitry
     * */
    private static int minimum(final int a, final int b, final int c) {
        return Math.min(Math.min(a, b), c);
    } /* End of 'LevenshteinDistance::minimum' method */

    @Override
    public final double getDistance(final String string1, final String string2) {
        final int[][] distance = new int[string1.length() + 1][string2.length() + 1];

        for (int i = 0; i <= string1.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= string2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= string1.length(); i++)
            for (int j = 1; j <= string2.length(); j++)
                distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + ((string1.charAt(i - 1) == string2.charAt(j - 1)) ? 0 : 1));

        return distance[string1.length()][string2.length()];
    } /* End of 'LevensthteinDistance::getDistance' method */
} /* End of 'LevensthteinDistance' class */