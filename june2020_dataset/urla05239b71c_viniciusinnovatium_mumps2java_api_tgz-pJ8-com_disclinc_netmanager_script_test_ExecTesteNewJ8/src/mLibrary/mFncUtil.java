package mLibrary;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public final class mFncUtil {

	public static Object[] concat(Object[] dest, Object[] orig) {
		return DataStructureUtil.concat(dest, orig);
	}

	public static Object[] concatSinceLastSubscript(Object[] dest, Object[] orig) {
		return DataStructureUtil.concatSinceLast(dest, orig);
	}

	public static Boolean booleanConverter(Object obj) {
		Boolean bool = false;
		if (obj instanceof Boolean) {
			bool = Boolean.parseBoolean(String.valueOf(obj));
		} else {
			Double dbl = numberConverter(obj);
			if (dbl != 0) {
				bool = true;
			}
		}
		return bool;
	}

	public static Integer integerConverter(Object number) {
		return numberConverter(number).intValue();
	}

	public static Double castDouble(Object obj) {
		try {
			return Double.valueOf(String.valueOf(obj));
		} catch (java.lang.NumberFormatException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static Integer castInt(Object obj) {
		try {
			if (obj.toString().isEmpty()) {
				return 0;
			}
			return castDouble(obj).intValue();
		} catch (java.lang.NullPointerException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static Integer[] castIntArray(Object... obj) {
		if (obj == null) {
			return null;
		} else if (obj.length == 0) {
			return new Integer[] {};
		}

		final Integer[] array = new Integer[obj.length];
		for (int i = 0; i < obj.length; i++) {
			array[i] = castInt(obj[i]);
		}
		return array;
	}

	public static String castString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static String characterImpl(Integer... codes) {
		if (codes == null) {
			return null;
		}

		if (codes.length == 1 && codes[0] == null) {
			return null;
		}

		final String[] chars = new String[codes.length];
		for (int i = 0; i < codes.length; i++) {
			// Apenas os inteiros nao-negativos deve ser convertidos.
			if (codes[i] != null && codes[i] >= 0) {
				chars[i] = String.valueOf(Character.toChars(codes[i])[0]);
			}
		}

		return generateString(chars, null, true);
	}

	public static int findImpl(String string, String substring, int start) {
		if (string == null || string.trim().length() == 0 || substring == null
				|| substring.length() == 0) {
			return 0;
		}

		if (start < 1 || start >= string.length()) {
			return 0;
		}

		string = string.substring(start);

		char[] substringChar = substring.toCharArray();
		char[] stringChar = string.toCharArray();

		int j = 0;
		int max = substringChar.length;
		for (int i = 0; i < stringChar.length; i++) {
			if (stringChar[i] == substringChar[j]) {
				j++;
			} else {
				j = 0;
			}

			if (j == max) {
				return ++i + (start + 1);
			}
		}
		return 0;
	}

	private static String generateString(Object[] array, String delimiter) {
		return generateString(array, delimiter, false);
	}

	private static String generateString(Object[] array, String delimiter,
			boolean avoidNull) {
		final StringBuilder result = new StringBuilder();
		final int indexToInsert = array.length - 1;
		for (int i = 0; i < array.length; i++) {
			if (avoidNull && array[i] == null) {
				continue;

			}
			if (array[i] == null) {
				array[i] = "";
			}
			result.append(array[i]);
			if (delimiter != null && i < indexToInsert) {
				result.append(delimiter);
			}
		}
		return result.toString();
	}

	private static boolean hasElementOnListObject(ListObject list, int position) {
		Object elemet = list(list, position);
		return elemet != null && !"".equals(elemet.toString().trim());
	}

	public static Object list(ListObject list) {
		return list.firstElement();
	}

	public static Object list(ListObject list, int position) {
		return list.element(position);
	}

	public static int listData(ListObject list, int position) {
		return hasElementOnListObject(list, position) ? 1 : 0;
	}

	public static Object listGet(ListObject list, int position,
			String defaultValue) {
		if (hasElementOnListObject(list, position)) {
			return list(list, position);
		} else {
			return defaultValue;
		}
	}

	public static Double dateMumpsToJava(Object internalDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(1840, 12, 31);

		Calendar cal2 = Calendar.getInstance();
		cal2.set(1970, 01, 01);

		Long dateDif = cal2.getTimeInMillis() - cal1.getTimeInMillis();

		Double days = Double.valueOf(String
				.valueOf(numberConverter(internalDate)));
		Double dateMilli = days * 24d * 60d * 60d * 1000d - dateDif;

		return dateMilli;

	}

	public static Double dateJavaToMumps(Object internalDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(1840, 12, 31);

		Calendar cal2 = Calendar.getInstance();
		cal2.set(1970, 01, 01);

		Long dateDif = cal2.getTimeInMillis() - cal1.getTimeInMillis();

		Long millis = Long.valueOf(String.valueOf(internalDate));
		Double days = (millis + dateDif) / 24d / 60d / 60d / 1000d;

		return days;

	}

	public static String dateCodeFormatMumpsToJava(Object dFormat) {
		String dFormatStr = "MM/dd/yy";
		if (dFormat.equals("-1")) {
			// dFormatStr = "";
		} else if (dFormat.equals("0")) {
			dFormatStr = "dd MMM yy";
		} else if (dFormat.equals("1")) {
			dFormatStr = "MM/dd/yy";
		} else if (dFormat.equals("2")) {
			dFormatStr = "dd MMM yy";
		} else if (dFormat.equals("3")) {
			dFormatStr = "yyyy-MM-dd";
		} else if (dFormat.equals("4")) {
			dFormatStr = "dd/MM/yy";
		} else if (dFormat.equals("5")) {
			dFormatStr = "MMM d, yyyy";
		} else if (dFormat.equals("6")) {
			dFormatStr = "MMM d yyyy";
		} else if (dFormat.equals("7")) {
			dFormatStr = "MMM dd yy";
		} else if (dFormat.equals("8")) {
			dFormatStr = "yyyyMMdd";
		} else if (dFormat.equals("9")) {
			dFormatStr = "MMMM d, yyyy";
		} else if (dFormat.equals("10")) {
			dFormatStr = "F";
		} else if (dFormat.equals("11")) {
			dFormatStr = "EEE";
		} else if (dFormat.equals("12")) {
			dFormatStr = "EEEE";
		} else if (dFormat.equals("13")) {
			dFormatStr = "ddd";
		} else if (dFormat.equals("14")) {
			dFormatStr = "D";
		}
		return dFormatStr;
	}

	public static Double numberConverter(Object obj) {
		if (obj == null) {
			return 0d;
		}

		Double dbl = null;
		try {
			dbl = Double.valueOf(String.valueOf(obj));
		} catch (NumberFormatException nfe) {
			String result = "";
			char[] charArray = obj.toString().toCharArray();
			boolean startNumber = false;
			boolean hasPoint = false;
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (!startNumber && (c == '+' || c == '-')) {
					if (result.isEmpty()) {
						result = String.valueOf(c);
					} else {
						if (result.equals(String.valueOf(c))) {
							result = "+";
						} else {
							result = "-";
						}
					}
					continue;
				}
				if (Character.isDigit(c)) {
					startNumber = true;
					result = result.concat(String.valueOf(c));
					continue;
				}
				if (c == '.') {
					startNumber = true;
					if (!hasPoint) {
						hasPoint = true;
						result = result.concat(String.valueOf(c));
						continue;
					}
				}
				break;
			}
			if (result.isEmpty() || !startNumber) {
				result = "0";
			}
			dbl = Double.valueOf(result);
		}
		return dbl;
	}

	public static String pieceImpl(String string, String delimiter) {
		return mFnc.$piece(string, delimiter, 1);
	}

	public static String pieceImpl(String string, String delimiter, int index) {

		if (string == null) {
			return null;
		}
		String[] splitStr = string.split(Pattern.quote(delimiter));
		if (splitStr.length >= index && index > 0) {
			return splitStr[index - 1];
		} else {
			return "";
		}
	}

	public static String pieceImpl(String string, String delimiter, int from,
			int to) {
		if (string == null) {
			return null;
		}

		if (from > to) {
			return null;
		}

		if (from > string.length()) {
			return "";
		}
		String[] strSplit = string.split(Pattern.quote(delimiter));
		if (to > strSplit.length) {
			to = strSplit.length;
		}
		final String[] array = Arrays.copyOfRange(strSplit, from - 1, to);

		return generateString(array, delimiter);
	}

	public static String setPieceImpl(String string, String delimiter,
			Integer position, String value) {
		if (string == null || position < 0) {
			return string;
		}

		final String[] array = string.split(Pattern.quote(delimiter));
		if (value == null) {
			value = "";
		}
		array[position - 1] = value;

		return generateString(array, delimiter);
	}

	public static String setPieceImpl(String string, String delimiter,
			Integer position, Object value) {
		Object[] array = string == null ? new Object[position] : string
				.toString().split(Pattern.quote(delimiter));
		if (position > array.length) {
			array = Arrays.copyOfRange(array, 0, position);
		}
		if (value == null) {
			value = "";
		}

		array[position - 1] = value.toString();

		return generateString(array, delimiter);
	}

	public static String translateImpl(String string, String oldCharsequence,
			String newCharsequence) {

		if (string == null || oldCharsequence == null
				|| oldCharsequence.length() == 0) {
			return string;
		}

		/*
		 * Esse mecanismo foi escolhido para evidar de criar muitas strings no
		 * pool e evitar o consumo de memoria excessivo.
		 */
		char[] stringChars = string.toCharArray();
		char[] oldChars = oldCharsequence.toCharArray();
		char[] newChars = (newCharsequence != null ? newCharsequence : "")
				.toCharArray();

		// Aqui vamos completar o newChars de substiticao pois quando
		// os tamanhos nao forem identicos isso indicara que os valores
		// excedentes devem ser substituidos pelo caracter "vazio".
		if (newChars.length < oldChars.length) {
			newChars = Arrays.copyOf(newChars, oldChars.length);
		}

		final StringBuilder result = new StringBuilder();
		char copy;
		for (int i = 0; i < stringChars.length; i++) {
			copy = stringChars[i];
			for (int j = 0; j < oldChars.length; j++) {
				if (copy == oldChars[j]) {
					if (newCharsequence != null) {
						copy = newChars[j];
					} else {
						copy = '\0';
					}
					break;
				}
			}

			// Caso o caracter seja vazio nao devemos inclui-lo na string
			// gerada.
			if ('\0' != copy) {
				result.append(copy);
			}

		}

		return result.toString();
	}

	public static Object zconvert(Object string, Object mode) {
		return zconvertImpl(castString(string), castString(mode));
	}

	public static String zconvertImpl(String string, String mode) {
		if (mode == null || mode.trim().length() == 0) {
			return string;
		}

		if ("L".equalsIgnoreCase(mode)) {
			string = string.toLowerCase();
		} else if ("U".equalsIgnoreCase(mode)) {
			string = string.toUpperCase();
		}
		return string;
	}

	private mFncUtil() {
	}

	public Object length(Object string) {
		return length(castString(string));
	}

	public Object length(Object string, Object delimiter) {
		return lengthImpl(castString(string), castString(delimiter));
	}

	public int lengthImpl(String string) {
		if (string == null) {
			return 0;
		}

		return string.length();
	}

	public Object lengthImpl(String string, String delimiter) {
		if (delimiter == null && string == null) {
			return 0;
		}

		if (delimiter != null && string == null) {
			return 1;
		}

		return string.split(Pattern.quote(delimiter)).length;
	}

	public static String toString(Object expression) {
		String str = String.valueOf(expression);
		if (expression instanceof Double) {
			Double dbl = (Double) expression;
			str = BigDecimal.valueOf(dbl)
					.setScale(dbl % 1 == 0 ? 0 : 2, BigDecimal.ROUND_HALF_UP)
					.toString();
		}
		return str;
	}

	public static String round(Double value, int scale) {
		String digit[] = String.valueOf(value).split("\\.");
		int length = digit.length;
		if (length <= 1) {
			StringBuilder build = new StringBuilder(digit[0]).append(".");
			for (int i = 0; i < scale; i++) {
				build.append("0");
			}
			return build.toString();
		} else if (digit.length == 2 && (length = digit[1].length()) < scale) {
			StringBuilder build = new StringBuilder(digit[0]).append(".")
					.append(digit[1]);
			scale = scale - length;
			for (int i = 0; i < scale; i++) {
				build.append("0");
			}
			return build.toString();
		}

		return BigDecimal.valueOf(value)
				.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}
}
