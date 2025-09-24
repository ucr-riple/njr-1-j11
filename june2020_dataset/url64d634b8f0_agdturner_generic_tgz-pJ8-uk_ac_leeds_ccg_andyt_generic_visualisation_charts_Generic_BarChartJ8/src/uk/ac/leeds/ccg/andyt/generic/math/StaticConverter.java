/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.math;

//import java.lang.NumberFormatException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A class of static methods used to convert between types of Object and
 * primitive.
 */
public abstract class StaticConverter {

    /**
     * @return an int derived from aString. This does no checking:
     * <code>return new BigInteger(aString).intValue();</code>
     * @param aString The String to be converted to an int.
     */
    public static int to_int(String aString) {
        if (aString.equalsIgnoreCase("")) {
            // Default noDataValue
            return Integer.MIN_VALUE;
        }
        return new BigInteger(aString).intValue();
    }

    /**
     * @return a boolean derived from aString.
     * <code>
     * if (to_int(aString) == 1 || aString.equalsIgnoreCase("true")) {
     *     return true;
     * }
     * return false;
     * </code>
     * @param aString The String to be converted to a boolean.
     */
    public static boolean to_boolean(String aString) {
        if (to_int(aString) == 1 || aString.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }

    /**
     * @return A short derived from aString.
     * <code>
     * return (short) to_int(aString);
     * </code>
     * @param aString The String to be converted to an short.
     */
    public static short to_short(String aString) {
        return (short) to_int(aString);
    }

    /**
     * @return a long derived from aString. This tries to cope with exponential
     * notation (e.g. 3.4E10) by catching NumberFormatExceptions when running:
     * <code>
     * return new BigInteger(aString).longValue();
     * </code>
     * @param aString The String to be converted to a long.
     */
    public static long to_long(String aString) {
        if (aString.equalsIgnoreCase("")) {
            // Default noDataValue
            return Long.MIN_VALUE;
        }
        try {
            return new BigInteger(aString).longValue();
        } catch (NumberFormatException aNumberFormatException) {
            String[] aStringSplit = null;
            if (aString.contains("e".subSequence(0, 1))) {
                aStringSplit = aString.split("e");
            }
            if (aString.contains("E".subSequence(0, 1))) {
                aStringSplit = aString.split("E");
            }
            boolean isNegative = false;
            if (aStringSplit[1].startsWith("-")) {
                isNegative = true;
                aStringSplit[1] = aStringSplit[1].substring(1, aStringSplit[1].length());
            } else {
                if (aStringSplit[1].startsWith("+")) {
                    aStringSplit[1] = aStringSplit[1].substring(1,
                            aStringSplit[1].length());

                }
            }
            while (aStringSplit[1].startsWith("0")) {
                aStringSplit[1] = aStringSplit[1].substring(1, aStringSplit[1].length());
            }
            int power = to_int(aStringSplit[1]);
            if (isNegative) {
                power = power * -1;
            }
            BigDecimal aBigDecimal = new BigDecimal(aStringSplit[0]).multiply(BigDecimal.TEN.pow(power));
            return aBigDecimal.longValue();
        }
    }
}
