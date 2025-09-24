package com.gesoftware.venta.math.random;

import com.gesoftware.venta.math.vectors.Vec2i;
import com.gesoftware.venta.math.vectors.Vec2r;

/**
 * Generator class definition
 **/
public final class Generator {
    /* *
     * METHOD: Generates uniformly distributed Vec2i
     * RETURN: Uniformly distributed Vec2i
     *  PARAM: [IN] minumum - absolute minumum value
     *  PARAM: [IN] maximum - absolute maximum value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r generateVec2r(final double minimum, final double maximum) {
        return new Vec2r(generateValue(minimum, maximum, Randomizer.nextBoolean()),
                         generateValue(minimum, maximum, Randomizer.nextBoolean()));
    } /* End of 'Generator::generateVec2r' method */

    /* *
     * METHOD: Generates uniformly distributed Vec2i
     * RETURN: Uniformly distributed Vec2i
     *  PARAM: [IN] minumum - absolute minumum value
     *  PARAM: [IN] maximum - absolute maximum value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i generateVec2i(final int minimum, final int maximum) {
        return new Vec2i(generateValue(minimum, maximum, Randomizer.nextBoolean()),
                         generateValue(minimum, maximum, Randomizer.nextBoolean()));
    } /* End of 'Generator::generateVec2i' method */

    /* *
     * METHOD: Generates uniformly distributed value either from [minimum; maximum] or [-maximum; -minumum]
     * RETURN: Uniformly distributed value either from [minimum; maximum] or [-maximum; -minumum]
     *  PARAM: [IN] minumum    - absolute minumum value
     *  PARAM: [IN] maximum    - absolute maximum value
     *  PARAM: [IN] isPositive - is result value positive
     * AUTHOR: Eliseev Dmitry
     * */
    public static double generateValue(final double minimum, final double maximum, final boolean isPositive) {
        return ((isPositive)?1:-1) * (minimum + Randomizer.nextDouble() * (maximum - minimum));
    } /* End of 'Generator::generateValue' method */

    /* *
     * METHOD: Generates uniformly distributed value either from [minimum; maximum] or [-maximum; -minumum]
     * RETURN: Uniformly distributed value either from [minimum; maximum] or [-maximum; -minumum]
     *  PARAM: [IN] minumum    - absolute minumum value
     *  PARAM: [IN] maximum    - absolute maximum value
     *  PARAM: [IN] isPositive - is result value positive
     * AUTHOR: Eliseev Dmitry
     * */
    public static int generateValue(final int minimum, final int maximum, final boolean isPositive) {
        return ((isPositive)?1:-1) * (minimum + Randomizer.nextInteger(maximum - minimum));
    } /* End of 'Generator::generateValue' method */

    /* *
     * METHOD: Generates uniformly distributed value from [average - deviation; average + deviation]
     * RETURN: Uniformly distributed value from [average - deviation; average + deviation]
     *  PARAM: [IN] average   - average value
     *  PARAM: [IN] deviation - deviation from average value
     * AUTHOR: Eliseev Dmitry
     * */
    public static double generateValue(final double average, final double deviation) {
        return average + getSign(Randomizer.nextBoolean()) * Randomizer.nextDouble() * deviation;
    } /* End of 'Generator::' method */

    /* *
     * METHOD: Generates uniformly distributed value from [average - deviation; average + deviation]
     * RETURN: Uniformly distributed value from [average - deviation; average + deviation]
     *  PARAM: [IN] average   - average value
     *  PARAM: [IN] deviation - deviation from average value
     * AUTHOR: Eliseev Dmitry
     * */
    public static int generateValue(final int average, final int deviation) {
        return average + getSign(Randomizer.nextBoolean()) * Randomizer.nextInteger(deviation);
    } /* End of 'Generator::generateValue' method */

    /* *
     * METHOD: Determines sign multiplier depends on parameter
     * RETURN: 1 if true, -1 otherwise
     *  PARAM: [IN] isPositive - is sign positive
     * AUTHOR: Eliseev Dmitry
     * */
    private static int getSign(final boolean isPositive) {
        return isPositive ? 1 : -1;
    } /* End of 'Generator::getSign' method */
} /* End of 'Generator' class */
