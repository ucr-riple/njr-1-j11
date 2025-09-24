package com.gesoftware.venta.utility;

import java.util.Date;

/**
 * ParsingUtility class definition
 */
public final class ParsingUtility {
    /* *
     * METHOD: Converts an object to string if possible
     * RETURN: String if success, exception otherwise
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static String toString(final Object object) {
        if (object == null)
            return null;

        return (String)object;
    } /* End of 'ParsingUtility::toString' method */

    /* *
     * METHOD: Converts an integer to string
     * RETURN: String value representation
     *  PARAM: [IN] value - integer value to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static String toString(final Number value) {
        if (value == null)
            return null;

        return value.toString();
    } /* End of 'ParsingUtility::toString' method */

    /* *
     * METHOD: Converts an object to an integer if possible
     * RETURN: Integer object representation
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static int toInteger(final Object object) {
        if (object == null)
            return 0;

        return Integer.parseInt(object.toString());
    } /* End of 'ParsingUtility::toString' method */

    /* *
     * METHOD: Converts an object to an long if possible
     * RETURN: Long object representation
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static long toLong(final Object object) {
        if (object == null)
            return 0;

        return Long.parseLong(object.toString());
    } /* End of 'ParsingUtility::toLong' method */

    /* *
     * METHOD: Converts an object to an double if possible
     * RETURN: Double object representation
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static double toDouble(final Object object) {
        if (object == null)
            return 0.0;

        return Double.parseDouble(object.toString());
    } /* End of 'ParsingUtility::toDouble' method */

    /* *
     * METHOD: Converts an object to an date if possible
     * RETURN: Date object representation
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static Date toDate(final Object object) {
        if (object == null)
            return null;

        return (Date)object;
    } /* End of 'ParsingUtility::toDate' method */

    /* *
     * METHOD: Converts an object to enum if possible
     * RETURN: Enum object representation
     *  PARAM: [IN] object - object to convert
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Enum<T>> T toEnum(final String value, final Class<T> enumClass) {
        if (ValidationUtility.isEmpty(value))
            return null;

        return Enum.valueOf(enumClass, value);
    } /* End of 'ParsingUtility::toEnum' method */
} /* End of 'ParsingUtility' class */
