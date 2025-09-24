/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class NumberUtil {


	//Hierarchy according to: http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.2.4
	private static final int SHORT = 0;
	private static final int INTEGER = 1;
	private static final int LONG = 2;
	private static final int FLOAT = 3;
	private static final int DOUBLE = 4;

	//Number types for easier if/switch etc. usage (so that instanceof does not need to be used)
	private static final HashMap<Class<?>, Integer> types = new LinkedHashMap<Class<?>, Integer>();
	static {
		types.put(Short.class, SHORT);
		types.put(Integer.class, INTEGER);
		types.put(Long.class, LONG);
		types.put(Float.class, FLOAT);
		types.put(Double.class, DOUBLE);
	}

	//Shared decimal patterns
	private static final HashMap<Integer, DecimalFormat> roundDecimals = new LinkedHashMap<Integer, DecimalFormat>();
	static {
		roundDecimals.put(1, new DecimalFormat("#.#"));
		roundDecimals.put(2, new DecimalFormat("#.##"));
		roundDecimals.put(3, new DecimalFormat("#.###"));
		roundDecimals.put(4, new DecimalFormat("#.####"));
		roundDecimals.put(5, new DecimalFormat("#.#####"));
	}

	private static int MAX_DIGIT_PATTERN = 6;

	//Some predefined digit patterns
	private static final HashMap<Integer, String[]> digitPattern = new LinkedHashMap<Integer, String[]>();
	static {
		digitPattern.put(1, new String[] {"#", 		"0"});
		digitPattern.put(2, new String[] {"##", 	"00"});
		digitPattern.put(3, new String[] {"###", 	"000"});
		digitPattern.put(4, new String[] {"####", 	"0000"});
		digitPattern.put(5, new String[] {"#####", 	"00000"});
		digitPattern.put(MAX_DIGIT_PATTERN,
				new String[] {"######", "000000"});
	}



	/**
	 * Checks if a number is odd
	 * 
	 * @param num
	 * @return True if the number is odd, false if the number is even
	 */
	public static boolean isOdd(int num){
		return num % 2 != 0;
	}

	/**
	 * Checks if a number is even
	 * 
	 * @param num
	 * @return True if the number is even, false if the number is even
	 */
	public static boolean isEven(int num){
		return num % 2 == 0;
	}

	/**
	 * Rounds to the given number of decimals for double values.<br>
	 * Example with two decimals: 1.22565... -> 1.23
	 * 
	 * @param d
	 * @param numberOfDecimals
	 * @return
	 */
	public static double roundDecimals(double d, int numberOfDecimals) {
		if (roundDecimals.containsKey(numberOfDecimals)) {
			return Double.valueOf(roundDecimals.get(numberOfDecimals).format(d));
		} else {
			return Double.valueOf(createDecimalFormat(1, numberOfDecimals,
					false, false, false, false, false).format(d));
		}
	}

	/**
	 * Rounds to the given number of decimals for float values.<br>
	 * Example with two decimals: 1.22565... -> 1.23
	 * 
	 * @param d
	 * @param numberOfDecimals
	 * @return
	 */
	public static float roundDecimals(float d, int numberOfDecimals) {
		if (roundDecimals.containsKey(numberOfDecimals)) {
			return Float.valueOf(roundDecimals.get(numberOfDecimals).format(d));
		} else {
			return Float.valueOf(createDecimalFormat(1, numberOfDecimals,
					false, false, false, false, false).format(d));
		}	}

	/**
	 * This method provides an easy way to create a {@link DecimalFormat} object.<br>
	 * <br>
	 * See the javadoc for more information about the formatting possibilities
	 * 
	 * @param minNumberOfDigits
	 * @param maxNumberOfDecimals
	 * @param currency
	 * @param internationalCurrency
	 * @param leadingZeroes
	 * @param percentage
	 * @param grouping
	 * @return
	 */
	public static DecimalFormat createDecimalFormat(int minNumberOfDigits, int maxNumberOfDecimals,
			boolean currency, boolean internationalCurrency, boolean leadingZeroes,
			boolean percentage, boolean grouping) {
		if (minNumberOfDigits <= 0) {
			throw new NumberUtilError("Minimum number of digits has to be > 0");
		}

		if (maxNumberOfDecimals < 0) {
			throw new NumberUtilError("Maximum number of decimals has to be >= 0");
		}

		StringBuilder sb = new StringBuilder(minNumberOfDigits + maxNumberOfDecimals);

		if (currency || internationalCurrency) {
			//Will be replaced by the currency symbol
			sb.append("\u00A4");

			if (internationalCurrency) {
				//Two currency symbols are replaced with the international currency symbol
				sb.append("\u00A4");
			}
		}

		sb.append(getDigitPattern(minNumberOfDigits, leadingZeroes, grouping));

		sb.append(".");

		sb.append(getDigitPattern(maxNumberOfDecimals, false, false));

		if (percentage) {
			sb.append("%");
		}

		return new DecimalFormat(sb.toString());
	}

	/**
	 * Returns the digit pattern with the given number of digits
	 * 
	 * @param numberOfDigits The number of digits
	 * @param zeroes If <code>true</code>, missing digits will be shown as zeroes
	 * @param grouping
	 * @return
	 */
	private static String getDigitPattern(int numberOfDigits, boolean zeroes, boolean grouping) {
		if (numberOfDigits < 0) {
			throw new NumberUtilError("Number of digits has to be >= 0");
		}

		StringBuilder sb = new StringBuilder(numberOfDigits);

		if (digitPattern.containsKey(numberOfDigits)) {
			sb.append(digitPattern.get(numberOfDigits)[zeroes ? 1 : 0]);
		} else {

			sb.append(digitPattern.get(MAX_DIGIT_PATTERN)[zeroes ? 1 : 0]);

			if (zeroes) {
				for (int i = MAX_DIGIT_PATTERN; i < numberOfDigits; i++) {
					sb.append("0");
				}
			} else {
				for (int i = MAX_DIGIT_PATTERN; i < numberOfDigits; i++) {
					sb.append("#");
				}
			}

		}

		if (grouping && sb.length() > 3) {
			//Add the grouping separator if requested
			sb.insert(sb.length() - 3, ',');
		}

		return sb.toString();
	}

	/**
	 * 
	 * 
	 * @param n
	 * @param minNumberOfDigits
	 * @param maxNumberOfDecimals
	 * @param currency
	 * @param internationalCurrency
	 * @param leadingZeroes
	 * @param percentage
	 * @param grouping
	 * @return
	 */
	public static String formatNumber(Number n, int minNumberOfDigits, int maxNumberOfDecimals,
			boolean currency, boolean internationalCurrency, boolean leadingZeroes,
			boolean percentage, boolean grouping) {
		return createDecimalFormat(minNumberOfDigits, maxNumberOfDecimals, currency,
				internationalCurrency, leadingZeroes, percentage, grouping).format(n);
	}

	/**
	 * 
	 * 
	 * @param n
	 * @param currency
	 * @param internationalCurrency
	 * @param percentage
	 * @param grouping
	 * @return
	 */
	public static String formatNumber(Number n, boolean currency, boolean internationalCurrency,
			boolean percentage, boolean grouping) {
		//Use at least 4 digits to make the grouping work
		return createDecimalFormat(4, 0, currency,
				internationalCurrency, false, percentage, grouping).format(n);
	}

	/**
	 * 
	 * 
	 * @param n
	 * @param minNumberOfDigits
	 * @param maxNumberOfDecimals
	 * @param leadingZeroes
	 * @param grouping
	 * @return
	 */
	public static String formatNumber(Number n, int minNumberOfDigits, int maxNumberOfDecimals,
			boolean leadingZeroes, boolean grouping) {
		return createDecimalFormat(minNumberOfDigits, maxNumberOfDecimals, false,
				false, leadingZeroes, false, grouping).format(n);
	}

	/**
	 * 
	 * 
	 * @param n
	 * @param grouping
	 * @return
	 */
	public static String formatNumber(Number n, boolean grouping) {
		//Use at least 4 digits to make the grouping work
		return createDecimalFormat(4, 0, false,
				false, false, false, grouping).format(n);
	}


	/**
	 * Adds n1 + n2.<br>
	 * The returned number result has the type of the input number
	 * with the higher "precision" (for example, a float value added to an
	 * integer value results in a floating point operation, returning a float type).
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Number add(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() + n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() + n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() + n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() + n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() + n2.doubleValue();
		}

		throw new NumberUtilError("Number addition error");
	}

	/**
	 * Subtracts n1 - n2.<br>
	 * The returned number result has the type of the input number
	 * with the higher "precision" (for example, a float value subtracted from an
	 * integer value results in a floating point operation, returning a float type).
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Number subtract(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() - n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() - n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() - n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() - n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() - n2.doubleValue();
		}

		throw new NumberUtilError("Number subtraction error");
	}

	/**
	 * Multiplies n1 * n2.<br>
	 * The returned number result has the type of the input number
	 * with the higher "precision" (for example, a float value multiplied by an
	 * integer value results in a floating point operation, returning a float type).
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Number multiply(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() * n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() * n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() * n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() * n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() * n2.doubleValue();
		}

		throw new NumberUtilError("Number multiplication error");
	}


	/**
	 * Divides n1 / n2.<br>
	 * The returned number result has the type of the input number
	 * with the higher "precision" (for example, a float value divided by an
	 * integer value results in a floating point operation, returning a float type).
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Number divide(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() / n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() / n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() / n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() / n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() / n2.doubleValue();
		}

		throw new NumberUtilError("Number division error");
	}

	/**
	 * Calculates the remainder n1 % n2.<br>
	 * The returned number result has the type of the input number
	 * with the higher "precision" (for example, a float value multiplied by an
	 * integer value results in a floating point operation, returning a float type).
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Number remainder(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() % n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() % n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() % n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() % n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() % n2.doubleValue();
		}

		throw new NumberUtilError("Number remainder error");
	}

	/**
	 * Checks if n1 > n2.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static boolean gt(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() > n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() > n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() > n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() > n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() > n2.doubleValue();
		}

		throw new NumberUtilError("Number greater than error");
	}

	/**
	 * Checks if n1 < n2.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static boolean lt(Number n1, Number n2) {
		int type = getOperationType(n1, n2);

		if (type == SHORT) {
			return n1.shortValue() < n2.shortValue();
		} else if (type == INTEGER) {
			return n1.intValue() < n2.intValue();
		} else if (type == LONG) {
			return n1.longValue() < n2.longValue();
		} else if (type == FLOAT) {
			return n1.floatValue() < n2.floatValue();
		} else if (type == DOUBLE) {
			return n1.doubleValue() < n2.doubleValue();
		}

		throw new NumberUtilError("Number less than error");
	}

	/**
	 * Determines the type of the operation. For example, a float value multiplied
	 * by an integer value results in a floating point operation, returning a
	 * float type). The floating point operations are defined in
	 * http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.2.4
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	private static int getOperationType(Number n1, Number n2) {
		Class<?> c1 = n1.getClass();
		Class<?> c2 = n2.getClass();

		if (!types.containsKey(c1)) {
			throw new NumberUtilError("Unknown number class " + c1);
		}

		if (!types.containsKey(c2)) {
			throw new NumberUtilError("Unknown number class " + c2);
		}

		int type1 = types.get(c1);
		int type2 = types.get(c2);

		return type1 > type2 ? type1 : type2;
	}

	/**
	 * Generates a single random number. The type of the resulting numbers is the type of the
	 * given min or max value, whichever has the higher "precision"
	 * (short -> int -> long -> float -> double)<br>
	 * <br>
	 * For multiple random numbers use {@link #generateRandomNumbers(int, Number, Number)}
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static Number generateRandomNumber(Number min, Number max) {
		return generateRandomNumbers(1, min, max).get(0);
	}

	/**
	 * Generates random numbers. The type of the resulting numbers is the type of the
	 * given min or max value, whichever has the higher "precision"
	 * (short -> int -> long -> float -> double)
	 * 
	 * @param count
	 * @param min
	 * @param max
	 * @return
	 */
	public static ArrayList<Number> generateRandomNumbers(int count, Number min, Number max) {
		Random random = new Random();
		ArrayList<Number> numbers = new ArrayList<>(count);

		int type = getOperationType(min, max);

		if (type == SHORT) {
			int randomMax = max.intValue() - min.intValue() + 1;	//Including the maximum number (from min to max)
			for (int i = 0; i < count; i++) {
				numbers.add(random.nextInt(randomMax) + min.intValue());
			}
		} else if (type == INTEGER) {
			int randomMax = max.intValue() - min.intValue() + 1;	//Including the maximum number (from min to max)
			for (int i = 0; i < count; i++) {
				numbers.add(random.nextInt(randomMax) + min.intValue());
			}
		} else if (type == LONG) {
			long randomMax = max.longValue() - min.longValue() + 1l;	//Including the maximum number (from min to max)
			for (int i = 0; i < count; i++) {
				numbers.add(random.nextLong() * randomMax + min.longValue());
			}
		} else if (type == FLOAT) {
			float randomMax = max.floatValue() - min.floatValue();	//Excluding the maximum number (between min and max)
			for (int i = 0; i < count; i++) {
				numbers.add(random.nextFloat() * randomMax + min.floatValue());
			}
		} else if (type == DOUBLE) {
			double randomMax = max.doubleValue() - min.doubleValue();	//Excluding the maximum number (between min and max)
			for (int i = 0; i < count; i++) {
				numbers.add(random.nextDouble() * randomMax + min.doubleValue());
			}
		}

		return numbers;
	}

	/**
	 * Casts the given number to the given class.<br>
	 * Valid classes are all the number or primitive classes:<br>
	 * - short.class, Short.class, Short.TYPE<br>
	 * - int.class, Integer.class, Integer.TYPE<br>
	 * - double.class, Double.class, Double.TYPE<br>
	 * etc.
	 * 
	 * @param c
	 * @param number
	 * @return
	 */
	public static Number castTo(Class<?> c, Number number) {

		String primitiveString = null;

		if (ReflectionUtil.primitiveTypeMapper.containsKey(c)) {
			//e.g. Double.TYPE or double.class
			primitiveString = (String)ReflectionUtil.primitiveTypeMapper.get(c)[0];
		} else if (ReflectionUtil.primitiveClassMapper.containsKey(c)) {
			//e.g. Double.class
			primitiveString = (String)ReflectionUtil.primitiveClassMapper.get(c)[0];
		}

		if (primitiveString == null) {
			throw new NumberUtilError("Invalid number cast. " + c.getCanonicalName() + " is not a valid class.");
		}

		switch (primitiveString) {
		case "int":
			return number.intValue();
		case "short":
			return number.shortValue();
		case "long":
			return number.longValue();
		case "double":
			return number.doubleValue();
		case "float":
			return number.floatValue();
		default:
			break;
		}

		throw new NumberUtilError("Invalid number cast. " + c.getCanonicalName() + " is not a valid class.");
	}

}
