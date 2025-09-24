package com.gesoftware.venta.math.tools;

import com.gesoftware.venta.math.vectors.Vec2i;
import com.gesoftware.venta.math.vectors.Vec2r;

/**
 * Mathematics class definition
 **/
public final class Mathematics {
    private static final double c_Epsilon = 0.00001;

    /* *
     * METHOD: Compares two double values
     * RETURN: True if values are approximately the same, False otherwise
     *  PARAM: [IN] value1 - first value
     *  PARAM: [IN] value2 - second value
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean equals(final double value1, final double value2) {
        return value1 == value2 || Math.abs(value1 - value2) < c_Epsilon;
    } /* End of 'Mathematics::equals' method */

    /* *
     * METHOD: Compares double with a null
     * RETURN: True if value is close to null, False otherwise
     *  PARAM: [IN] value - value to compare with null
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isNull(final double value) {
        return equals(value, 0.0);
    } /* End of 'Mathematics::isNull' method */

    /* *
     * METHOD: Finds minimal value of given
     * RETURN: The minumal value of given
     *  PARAM: [IN] numbers - values to find minimal
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Number> T min(final T... numbers) {
        T min = numbers[0];

        for (int i = 1; i < numbers.length; i++)
            if (numbers[i].doubleValue() < min.doubleValue())
                min = numbers[i];

        return min;
    } /* End of 'Mathematics::min' method */

    /* *
     * METHOD: Finds maximal value of given
     * RETURN: The maximal value of given
     *  PARAM: [IN] numbers - values to find maximal
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Number> T max(final T... numbers) {
        T max = numbers[0];

        for (int i = 1; i < numbers.length; i++)
            if (numbers[i].doubleValue() > max.doubleValue())
                max = numbers[i];

        return max;
    } /* End of 'Mathematics::max' method */

    /* *
     * METHOD: Calculates distance between two points
     * RETURN: The Euclidian distance between two points
     *  PARAM: [IN] a - first point
     *  PARAM: [IN] b - second point
     * AUTHOR: Eliseev Dmitry
     * */
    public static double distance(final Vec2r a, final Vec2r b) {
        return Math.pow(Math.pow(b.getX() - a.getX(), 2.0) + Math.pow(b.getY() - a.getY(), 2.0), 0.5);
    } /* End of 'Mathematics::distance' method */

    /* *
     * METHOD: Calculates distance between two points
     * RETURN: The Euclidian distance between two points
     *  PARAM: [IN] a - first point
     *  PARAM: [IN] b - second point
     * AUTHOR: Eliseev Dmitry
     * */
    public static double distance(final Vec2i a, final Vec2i b) {
        return Math.pow(Math.pow(b.getX() - a.getX(), 2.0) + Math.pow(b.getY() - a.getY(), 2.0), 0.5);
    } /* End of 'Mathematics::distance' method */

    /* *
     * METHOD: Determine if value from [min; max]
     * RETURN: True if yes, False otherwise
     *  PARAM: [IN] value - value to check
     *  PARAM: [IN] min   - left border
     *  PARAM: [IN] max   - right border
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isInRange(final double value, final double min, final double max) {
        return (value >= min) && (value <= max);
    } /* End of 'Mathematics::isInRange' method */

    /* *
     * METHOD: Determine if value from [min; max]
     * RETURN: True if yes, False otherwise
     *  PARAM: [IN] value - value to check
     *  PARAM: [IN] min   - left border
     *  PARAM: [IN] max   - right border
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isInRange(final int value, final int min, final int max) {
        return (value >= min) && (value <= max);
    } /* End of 'Mathematics::isInRange' method */

    /* *
     * METHOD: Clamps value using given interval
     * RETURN: Clamped value
     *  PARAM: [IN] value - value to clamp
     *  PARAM: [IN] min   - left border
     *  PARAM: [IN] max   - right border
     * AUTHOR: Eliseev Dmitry
     * */
    public static double clamp(final double value, final double min, final double max) {
        return min(max(value, min), max);
    } /* End of 'Mathematics::clamp' method */

    /* *
     * METHOD: Clamps value using given interval
     * RETURN: Clamped value
     *  PARAM: [IN] value - value to clamp
     *  PARAM: [IN] min   - left border
     *  PARAM: [IN] max   - right border
     * AUTHOR: Eliseev Dmitry
     * */
    public static int clamp(final int value, final int min, final int max) {
        return min(max(value, min), max);
    } /* End of 'Mathematics::clamp' method */

    /* *
     * METHOD: Mixes two value using mixing coefficient
     * RETURN: Mixed value (value1 * coefficient + value2 * (1 - coefficient))
     *  PARAM: [IN] value1      - value #1
     *  PARAM: [IN] value2      - value #2
     *  PARAM: [IN] coefficient - mixing coefficient [0; 1]
     * AUTHOR: Eliseev Dmitry
     * */
    public static double mix(final double value1, final double value2, final double coefficient) {
        if ((coefficient < 0.0)||(coefficient > 1.0))
            throw new IllegalArgumentException("Coefficient must be between 0 and 1");

        return value1 * coefficient + value2 * (1.0 - coefficient);
    } /* End of 'Mathematics::mix' method */
} /* End of 'Mathematics' class */
