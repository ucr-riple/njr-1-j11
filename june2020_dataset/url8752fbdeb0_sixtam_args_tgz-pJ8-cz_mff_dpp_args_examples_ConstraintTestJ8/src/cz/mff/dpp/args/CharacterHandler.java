package cz.mff.dpp.args;

/**
 * 
 * {@code CharacterHandler} is used to create and compare {@link Character
 * Characters}.
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
final class CharacterHandler {

	private CharacterHandler() {

	}

	/**
	 * 
	 * Creates char from String
	 * 
	 * @param stringValue
	 *            string "representation" of Character
	 * 
	 * @return first char of the stringValue
	 */
	public static Character valueOf(String stringValue) {

		if (stringValue.length() > 1) {
			Logger.warning("Using only first Character from '%s'", stringValue);
		}
		return (stringValue.charAt(0));
	}

	/**
	 * Compares two Characters values numerically. The value returned is
	 * identical to what would be returned by:
	 * 
	 * {@code x.compareTo(y) }
	 * 
	 * 
	 * @param x
	 *            the first Character to compare
	 * @param y
	 *            the second Character to compare
	 * @return the value 0 if x == y; a value less than 0 if x < y; and a value
	 *         greater than 0 if x > y
	 */

	public static int compare(Character x, Character y) {
		return x.compareTo(y);
	}

}
