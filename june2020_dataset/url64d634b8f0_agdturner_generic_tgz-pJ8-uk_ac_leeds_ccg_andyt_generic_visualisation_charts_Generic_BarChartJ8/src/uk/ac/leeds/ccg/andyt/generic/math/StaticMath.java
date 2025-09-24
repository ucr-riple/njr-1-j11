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

/**
 * A class of static methods that deal with numbers and maths.
 */
public abstract class StaticMath {

    /**
     * @param value
	 * @return the next number in double format that is larger than value
	 */
	public static double getValueALittleBitLarger(double value) {
		if (value == Double.MAX_VALUE) {
			System.out.println(
                    "Warning: Returning Double.POSITIVE_INFINITY in " +
                    StaticMath.class.getName() +
                    ".getValueALittleBitLarger(double(" + value + "))");
            return Double.POSITIVE_INFINITY;
		} else {
			double difference = 1.0d;
			double counter = 1.0d;
			boolean calculated;
			if (value != 0.0d) {
				difference = Math.abs(value);
				counter = value;
			}
			for (int i = 2048; i > 2; i /= 2) {
				calculated = false;
				while (!calculated) {
					if ((value + difference) == value) {
						calculated = true;
					} else {
						counter *= (double) i;
						difference = Math.abs(1.0d / counter);
					}
				}
				counter /= (double) i;
				difference = Math.abs(1.0d / counter);
			}
			// System.out.println( value + difference );
			return value + difference;
		}
	}

    /**
     * @param value
	 * @return the next number in double format that is smaller than value
	 */
	public static double getValueALittleBitSmaller(double value) {
		if (value == Double.MIN_VALUE) {
			System.out.println(
                    "Warning: Returning Double.NEGATIVE_INFINITY in " +
                    StaticMath.class.getName() +
                    ".getValueALittleBitSmaller(double(" + value + "))");
            return Double.NEGATIVE_INFINITY;
		} else {
			double difference = Math.abs(value);
			double counter = value;
			boolean calculated;
			int ite = 0;
			for (int i = 2048; i > 2; i /= 2) {
				calculated = false;
				while (!calculated) {
					if ((value - difference) == value) {
						calculated = true;
					} else {
						counter *= (double) i;
						difference = Math.abs(1.0d / counter);
					}
				}
				counter /= (double) i;
				difference = Math.abs(1.0d / counter);
			}
			return value - difference;
		}
	}

	
}
