package cz.mff.dpp.args;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;


import static cz.mff.dpp.args.ReflectUtils.valueFromString;
/**
 * Encapsulates setting values into fields and invoking setters. 
 *
 */
final class Configurator {

	private static final String FORMAT_SET_FIELD_NOT_POSSIBLE =
			"Unable to configure field %s with object '%s'";
	private static final String FORMAT_CALL_SETTER_ERROR =
			"Unable to call method %s on object %s with params %s";

	// ------------------------------------------------------------------------
	// PUBLIC METHODS
	// ------------------------------------------------------------------------
	/**
	 * Sets stringValues to accessible.
	 * <p>
	 * If accessible is flag type (field is boolean or method has no
	 * parameter) accessible is set to true
	 * </p>
	 *
	 * <p>
	 * If accessible is simple type, new instance of stringValue is created.
	 * </p>
	 *
	 * <p>
	 * If accessible if array type, new instance of array or list is created
	 * and also new instance for every item in stringValues is created and
	 * added to array or list.
	 * </p>
	 *
	 * value is then checked for constraints and applied to accessible
	 *
	 * 
	 * @param target
	 * @param accessible method or field
	 * @param stringValues List of values to set in field or use as
	 *	parameter for method
	 *
	 * @throws CheckException if any of values does not pass the test
	 * @throws ConfException if creating any of new instances
	 * (of field type or method parameter type or any of stringValues item) fails.
	 */
	public static void set(Object target, AccessibleObject accessible,
			final List<String> stringValues) throws ConfException, CheckException {

		Class<?> type = ReflectUtils.getValueType(accessible);

		Object value = null;

		if (ReflectUtils.isFlagType(accessible)) {
			//
			// For field set is used first paremeter of stringValues
			// Setter for flag has no parameter, so value null.
			//
			if (accessible instanceof Field) {
				assert (stringValues.size() == 1);
				value = ReflectUtils.valueFromString(type, stringValues.get(0));
			}

		} else if (ReflectUtils.isSimpleType(accessible)) {
			assert (stringValues.size() == 1);
			value = ReflectUtils.valueFromString(type, stringValues.get(0));
		} else if (ReflectUtils.isArrayType(accessible)) {
			value = valueFromList(accessible, stringValues);
		} else {
			assert false : "Unsupported Option type";
		}

		// TODO check value for null, probably in valueFromString?

		ConstraintUtils.checkValue(accessible, value);

		applyValue(target, accessible, value);


	}

	// ------------------------------------------------------------------------
	// PRIVATE METHODS
	// ------------------------------------------------------------------------
	/**
	 * Applies value into accessible. For field is values set into it. For
	 * method is called invoke with value as parameter.
	 *
	 * If AccessibleObject is field, then setValue into it.
	 * If AccessibleObject is method, then invokes it with value as parameter,
	 * for flag type is method invoked with null as parameters.
	 * @param target
	 * @param accessible method or field
	 * @param value to be set in field or passed to setter
	 * @throws ConfException
	 */
	private static void applyValue(Object target, AccessibleObject accessible,
			Object value) throws ConfException {

		if (accessible instanceof Field) {

			setFieldValue(target, (Field) accessible, value);

		} else if (accessible instanceof Method) {
			if (ReflectUtils.isFlagType(accessible)) {
				//
				//Setter for flag has no parameter, instead of value
				//gets null. This must be solved separate because
				//calling it with null would resolve in third parameter
				//would be array of Object with one item - null
				//
				callSetter(target, (Method) accessible, (Object[]) null);
			} else {
				callSetter(target, (Method) accessible, value);
			}

		} else {
			assert (false);
		}

	}

	/**
	 * Calls object's method with given parameters. 
	 * 
	 * 
	 * @param target
	 * @param method method that will be invoked.
	 * @param params parameters that will be given to method.
	 *
	 * @throws ConfException if invoking method fails.
	 */
	private static void callSetter(Object target, Method method,
			Object... params) throws ConfException {
		AccessibleState state = new AccessibleState(method);
		try {
			method.invoke(target, params);
		} catch (Exception ex) {
			ConfException.wrap(ex, FORMAT_CALL_SETTER_ERROR, method, target,
					java.util.Arrays.toString(params));
		}  finally {
			state.restore();
		}

	}
	/**
	 * Sets value into field
	 * @param target
	 * @param field field to set value in
	 * @param value value to set in field
	 * @throws ConfException if calling Field.set() fails
	 */
	private static void setFieldValue(Object target, Field field, Object value) throws ConfException {

		//
		// Make the field accessible before setting its value and
		// restore the previous accessibility state after that.
		//
		AccessibleState fieldState = new AccessibleState(field);
		try {
			field.set(target, value);
		} catch (Exception ex) {
			ConfException.wrap(ex, FORMAT_SET_FIELD_NOT_POSSIBLE, field.getName(),
					value.toString());
		} finally {
			fieldState.restore();
		}

	}

	// ------------------------------------------------------------------------
	// CREATING LISTS/ARRAYS
	// ------------------------------------------------------------------------
	/**
	 * Creates instance of same type as AccessibleObject and fills it
	 * with values.
	 * @param accessible
	 *            Field or Method
	 * @param stringValues
	 *            Values to be set into list
	 * @return created array-like type
	 * @throws ConfException if an object cannot be created
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static Object valueFromList(AccessibleObject accessible,
			final List<String> stringValues) throws ConfException {

		Class<?> klass = ReflectUtils.getType(accessible);

		if (klass.isArray()) {

			Class<?> componentType = klass.getComponentType();
			return createArrayAndFill(componentType, stringValues);

		} else if (List.class.isAssignableFrom(klass)) {

			Class<?> valueType = ReflectUtils.getValueType(accessible);


			return createListAndFill(valueType, (Class<? extends List>) klass,
					stringValues);

		} else {
			throw new ConfException("Cannot create array-like type!");
		}
	}

	/**
	 * Creates new instance of array with given type and sets stringValues into
	 * it.
	 * 
	 * @param type
	 *            type of array to be created
	 * @param stringValues
	 *            list of values to be set in array
	 * @return new instance of array with given type and stringValues set into it.
	 *
	 * @throws ConfException if creating new instance fails or if cannot
	 *  create instance for any of stringValues
	 */
	private static Object createArrayAndFill(Class<?> type,
			final List<String> stringValues) throws ConfException {

		assert (type != null);
		assert (stringValues != null);
		Object array = Array.newInstance(type, stringValues.size());

		int index = 0;
		for (String argument : stringValues) {
			Object value = valueFromString(type, argument);
			Array.set(array, index++, value);

		}

		return array;
	}

	/**
	 * Creates new list of listType and sets stringValues with componentType
	 * into it. If listType is List, then LinkedList is created
	 * 
	 * @param componentType
	 *            type of component of list
	 * @param listType
	 *            type of list to be created
	 * @param stringValues
	 *            list of values to be set into new list
	 * @return instance of listType filled with
	 *
	 * @throws ConfException if instance of list cannot be created
	 * or if cannot create instance for any of stringValues
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Object createListAndFill(Class<?> componentType,
			final Class<? extends List> listType,
			final List<String> stringValues) throws ConfException {
		List list;

		try {
			if (listType == List.class) {
				list = LinkedList.class.newInstance();
			} else {
				list = listType.newInstance();
			}
		} catch (Exception e) {
			throw new ConfException("Cannot create list!", e);
		}
		// Creates new instances for all values in stringValues
		// and adds it into list
		for (String argument : stringValues) {
			Object value = valueFromString(componentType, argument);
			list.add(value);
		}

		return list;
	}

	// ------------------------------------------------------------------------
	// AUXILIARY
	// ------------------------------------------------------------------------
	/**
	 * Captures state of AccessibleState object and makes it accessible. The
	 * state can be restored with restore()
	 * 
	 * Could use Java 7 AutoCloseable (we are using Java 6).
	 * 
	 */
	 final static class AccessibleState {

		private boolean accessible;
		private AccessibleObject accessibleObject;

		public AccessibleState(AccessibleObject accessibleObject) {
			this.accessibleObject = accessibleObject;
			save();
			makeAccessible();
		}

		public void restore() {
			accessibleObject.setAccessible(accessible);
		}

		public void save() {
			accessible = accessibleObject.isAccessible();
		}

		public void makeAccessible() {
			accessibleObject.setAccessible(true);
		}
	}
}
