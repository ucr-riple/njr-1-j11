package cz.mff.dpp.args;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static cz.mff.dpp.args.Logger.severe;
import static cz.mff.dpp.args.Logger.fine;

/**
 * 
 * Collection of utility functions to check conditions of
 * {@code AccessibleObject} imposed by {@link Constraint} annotation.
 * 
 * <p>
 * See {@link Constraint} for more details.
 * </p>
 * 
 * 
 * @see Constraint
 * 
 * @author Martin Sixta
 * 
 */
final class ConstraintUtils {

	/** Name of the static function to compare two objects */
	private static final String COMPARE = "compare";

	/** Name of the function to compare one object to another */
	private static final String COMPARE_TO = "compareTo";

	private static final String FORMAT_ALLOWED_FAILED = "'%s' is not allowed value (%s) for '%s'";
	private static final String FORMAT_REGEXP_FAILED = "'%s' does not match regular expression '%s' for '%s'";
	private static final String FORMAT_MIN_FAILED = "'%s' is less than allowed minimun of '%s'";
	private static final String FORMAT_MAX_FAILED = "'%s' is more than allowed maximum of '%s'";

	// ------------------------------------------------------------------------
	// PUBLIC METHODS
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Checks whether {@code String} representation of {@code Objects} meet
	 * criteria imposed upon them by {@code Constraint}.
	 * 
	 * <p>
	 * The check is done on String representation of objects, that means before
	 * actual value is assigned to objects annotated with {@code Option} or
	 * {@code Argument}.
	 * </p>
	 * 
	 * <p>
	 * Note that objects's not annotated by {@code Constraint} pass the check.
	 * </p>
	 * 
	 * @param accessible
	 *            {@code AccessilbeObject} annotated with {@code Constraint}
	 * @param stringValues
	 *            String values to check
	 * @throws CheckException
	 *             if values does not meet constraints imposed by
	 *             {@code Constraint} annotation
	 * 
	 * @see Constraint#allowedValues()
	 * @see Constraint#regexp()
	 * @see Constraint
	 * @see Option
	 * @see Argument
	 */
	public static void checkStringValues(AccessibleObject accessible,
			List<String> stringValues) throws CheckException {

		Constraint constraint = ReflectUtils.getConstraint(accessible);

		if (constraint == null) {
			return;
		}

		for (String value : stringValues) {
			checkStringValue(accessible, value);
		}

	}

	/**
	 * 
	 * Checks whether {@code Objects} meet criteria imposed upon them by
	 * {@code Constraint}.
	 * 
	 * <p>
	 * The check is done upon actual object values which are then (if they pass
	 * the check) are assigned to objects annotated with {@code Option} or
	 * {@code Argument}.
	 * </p>
	 * 
	 * <p>
	 * If {@code object} is array-like all values contained are checked.
	 * </p>
	 * 
	 * 
	 * 
	 * @param accessible
	 *            {@code AccessilbeObject} annotated with {@code Constraint}
	 * @param value
	 *            object to check
	 * @throws CheckException
	 *             if object does not meet criteria imposed by
	 *             {@code Constraint}
	 * @throws ConfException
	 *             if object values specified in {@code Constraint} cannot be
	 *             created or corresponding functions cannot be called
	 * 
	 * @see Constraint#min()
	 * @see Constraint#max()
	 * @see Constraint
	 * @see Option
	 * @see Argument
	 */
	public static void checkValue(AccessibleObject accessible, Object value)
			throws CheckException, ConfException {
		Constraint constraint = ReflectUtils.getConstraint(accessible);

		if (constraint == null || value == null) {
			return;
		}

		// value-type of accessible
		Class<?> valueType = ReflectUtils.getValueType(accessible);

		// value of the passed object
		Class<?> typeOfValue = value.getClass();

		if (typeOfValue.isArray()) {
			int length = Array.getLength(value);

			// NOTE we have to use java.lang.reflex.Array
			// Otherwise it would not work for primitive types
			for (int i = 0; i < length; ++i) {
				checkValue(constraint, valueType, Array.get(value, i));
			}

		} else if (value instanceof java.util.List<?>) {
			for (Object singleValue : (List<?>) value) {
				checkValue(constraint, valueType, singleValue);
			}
		} else {
			checkValue(constraint, valueType, value);
		}

	}

	// ------------------------------------------------------------------------
	// PRIVATE METHODS
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// AUXILIARY - STRING VALUES
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Checks single String value constraint imposed by {@code Constraint}
	 * annotation.
	 * 
	 * @param accessible
	 *            {@code AccessilbeObject} annotated with {@code Constraint}
	 * @param stringValue
	 *            String value to check
	 * @throws CheckException
	 *             if value does not meet constraints imposed by
	 *             {@code Constraint} annotation
	 * 
	 * @see #checkStringValues(AccessibleObject, List)
	 */
	private static void checkStringValue(AccessibleObject accessible,
			String stringValue) throws CheckException {
		Constraint constraint = ReflectUtils.getConstraint(accessible);

		if (constraint == null) {
			return;
		}

		if (!checkAllowedValues(constraint, stringValue)) {
			throwAndLog(FORMAT_ALLOWED_FAILED, stringValue,
					Arrays.toString(constraint.allowedValues()), accessible);
		}

		if (!checkRegexp(constraint, stringValue)) {
			throwAndLog(FORMAT_REGEXP_FAILED, stringValue, constraint.regexp(),
					accessible);
		}
	}

	/**
	 * 
	 * Checks whether stringValue is one of listed in
	 * {@code Constraint#allowedValues()} honoring
	 * {@code Constraint#ignoreCase()}
	 * 
	 * @param constraint
	 *            corresponding {@code Constraint}
	 * @param stringValue
	 *            String value to check against
	 * @return {@code true} if an object passed the test or constraint is not
	 *         specified, otherwise {@code false}
	 * @throws CheckException
	 *             if {@code stringValue} is not allowed value
	 */
	private static boolean checkAllowedValues(Constraint constraint,
			String stringValue) throws CheckException {

		if (constraint.allowedValues().length == 0) {
			return true;
		}

		boolean passed = false;
		for (String allowed : constraint.allowedValues()) {

			if (constraint.ignoreCase()) {
				passed = stringValue.equalsIgnoreCase(allowed);
			} else {
				passed = stringValue.equals(allowed);
			}

			if (passed) {
				break;
			}
		}

		return passed;
	}

	/**
	 * 
	 * Checks whether stringValue matches a regular expression defined in
	 * {@code Constraint#regexp()}
	 * 
	 * @param constraint
	 *            corresponding {@code Constraint}
	 * @param stringValue
	 *            String value to check against regexp
	 * @return {@code true} if an object passed the test or constraint is not
	 *         specified, otherwise {@code false}
	 * @throws CheckException
	 *             if {@code stringValue} does not match the regular exception
	 */

	private static boolean checkRegexp(Constraint constraint, String stringValue)
			throws CheckException {

		if (!constraint.regexp().isEmpty()) {
			try {
				return stringValue.matches(constraint.regexp());
			} catch (PatternSyntaxException e) {
				throw new CheckException(e,
						"Cannot check '%s' against regular exception'%s'",
						stringValue, constraint.regexp());
			}
		} else {
			return true;
		}
	}

	// ------------------------------------------------------------------------
	// AUXILIARY - OBJECT VALUES
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Checks single value constraint imposed by {@code Constraint} annotation.
	 * 
	 * @param constraint
	 *            the corresponding {@code Constraint}
	 * @param type
	 *            type of the object
	 * @param value
	 *            value to check
	 * 
	 * @throws CheckException
	 *             if object does not pass test
	 * @throws ConfException
	 *             if needed objects values cannot be created, appropriate
	 *             functions called
	 * 
	 * @see #checkValue(AccessibleObject, Object)
	 * @see Constraint
	 */
	private static void checkValue(Constraint constraint, Class<?> type,
			Object value) throws CheckException, ConfException {

		if (!checkMin(constraint, type, value)) {

			throwAndLog(FORMAT_MIN_FAILED, value, constraint.min());
		}

		if (!checkMax(constraint, type, value)) {

			throwAndLog(FORMAT_MAX_FAILED, value, constraint.max());
		}

	}

	/**
	 * 
	 * Checks whether an object is bigger or equal to required minimum.
	 * 
	 * @param constraint
	 *            the corresponding {@code Constraint}
	 * @param type
	 *            type of the object
	 * @param value
	 *            value to check
	 * 
	 * @return {@code true} if an object passed the test or constraint is not
	 *         specified, otherwise {@code false}
	 * 
	 * @throws CheckException
	 *             if object does not pass test
	 * @throws ConfException
	 *             if needed objects values cannot be created, appropriate
	 *             functions called
	 * 
	 * @see #checkValue(AccessibleObject, Object)
	 * @see Constraint
	 */
	private static boolean checkMin(Constraint constraint, Class<?> type,
			Object value) throws CheckException, ConfException {

		if (constraint.min().isEmpty()) {
			return true;
		}

		Object min;
		try {
			min = ReflectUtils.valueFromString(type, constraint.min());
		} catch (ConfException ex) {
			throw new ConfException(ex,
					"Cannot create value for min from '%s'", constraint.min());
		}

		int result = compare(constraint, type, value, min);

		if (result < 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * Checks whether an object is less or equal to required maximum.
	 * 
	 * @param constraint
	 *            the corresponding {@code Constraint}
	 * @param type
	 *            type of the object
	 * @param value
	 *            value to check
	 * 
	 * @return {@code true} if an object passed the test or constraint is not
	 *         specified, otherwise {@code false}
	 * 
	 * @throws CheckException
	 *             if object does not pass test
	 * @throws ConfException
	 *             if needed objects values cannot be created, appropriate
	 *             functions called
	 * 
	 * @see #checkValue(AccessibleObject, Object)
	 * @see Constraint
	 */
	private static boolean checkMax(Constraint constraint, Class<?> type,
			Object value) throws CheckException, ConfException {

		if (constraint.max().isEmpty()) {
			return true;
		}

		Object max;
		try {
			max = ReflectUtils.valueFromString(type, constraint.max());
		} catch (ConfException ex) {
			throw new ConfException(ex, "Cannot create value for max from %s",
					constraint.min());
		}

		int result = compare(constraint, type, value, max);

		if (result > 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * Compares two objects.
	 * <p>
	 * Values are compared in the following way (in order of execution):
	 * <ul>
	 * <li>{@code static compare(T x, T y)} of the object (or of comparator if
	 * specified)</li>
	 * <li>{@code compare.compareTo(compareTo)}</li>
	 * </ul>
	 * </p>
	 * 
	 * @param constraint
	 *            associated constraint
	 * @param type
	 * @param compare
	 *            value
	 * @param compareTo
	 *            value to compare to
	 * @return result of comparison
	 * @throws ConfException
	 *             if objects cannot be compared.
	 */
	private static int compare(Constraint constraint, Class<?> type,
			Object compare, Object compareTo) throws ConfException {

		Class<?>[] compareToArgsTypes = { type };

		// let's try static comapre(x, y) on creatorType
		Class<?>[] compareArgsTypes = { type, type };

		Class<?> comparatorType;

		if (constraint.comparator() != None.class) {
			comparatorType = constraint.comparator();
		} else {
			comparatorType = FactoryRegistry.getFactoryType(type);
		}

		try {
			Method comparator = ReflectUtils.getMethod(comparatorType, COMPARE,
					compareArgsTypes);

			if (ReflectUtils.isFieldAssignableFrom(int.class, comparator)) {
				return (Integer) comparator.invoke(null, compare, compareTo);
			}

		} catch (Exception e) {
			Logger.fine("Cannot invoke static compare(x,y) for type %s",
					type.getName());
		}

		Exception cause = null;

		// no luck, let's try compareTo!
		try {
			Method comparatorTo = ReflectUtils.getMethod(type, COMPARE_TO,
					compareToArgsTypes);

			if (ReflectUtils.isFieldAssignableFrom(int.class, comparatorTo)) {
				return (Integer) comparatorTo.invoke(compare, compareTo);
			}
		} catch (Exception e) {
			Logger.fine("Cannot invoke compareTo for type %s", type.getName());
			cause = e;
		}

		throw new ConfException(cause, "Cannot compare type %s", type.getName());

	}

	/**
	 * 
	 * Logs and then throws exception.
	 * 
	 * This is to remove duplicated code.
	 * 
	 * 
	 * @param format
	 * @param args
	 * @throws CheckException
	 *             always
	 */
	private static void throwAndLog(String format, Object... args)
			throws CheckException {

		severe(format, args);

		throw new CheckException(format, args);
	}

}
