package cz.mff.dpp.args;


/**
 * 
 * {@code CharHandler} is used to create and compare chars.
 * 
 * <p>
 * The class is needed to circumvent inconsistency in Java API
 * </p>
 * 
 * 
 * @author Martin Sixta
 * 
 * @see FactoryRegistry
 * 
 */
final class CharHandler {

	private CharHandler() {

	}

	/**
	 * 
	 * Creates char from String
	 * 
	 * @param stringValue
	 *            string "representation" of char
	 * 
	 * @return first char of the stringValue
	 */
	public static char valueOf(String stringValue) {
		if (stringValue.length() > 1) {
			Logger.warning("Using only first char from '%s'", stringValue);
		}
		return (stringValue.charAt(0));
	}

	/**
	 * Compares two char values numerically. The value returned is identical to
	 * what would be returned by:
	 * 
	 * {@code Character.valueOf(x).compareTo(Character.valueOf(y)) }
	 * 
	 * 
	 * @param x the first char to compare
	 * @param y the second char to compare
	 * @return the value 0 if x == y; a value less than 0 if x < y; and a value greater than 0 if x > y
	 */
	public static int compare(char x, char y) {
		return Character.valueOf(x).compareTo(y);
	}

}
