package cz.mff.dpp.args;

import cz.mff.dpp.args.Configurator.AccessibleState;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Collection of functions which use extensively java.lang.reflect package.
 * 
 * <p>
 * These functions are used in several places in the library, mainly in
 * {@link Configurator}, {@link ConstraintUtils} and {@link Parser}
 * </p>
 * 
 * @author Martin Sixta
 * 
 */
final class ReflectUtils {

	private static final String FACTORY_METHOD_NAME = "valueOf";

	// ------------------------------------------------------------------------
	// ANNOTATED OBJECTS - TYPE TESTING
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Decides whether {@code AccessibleObject} is a flag.
	 * 
	 * <p>
	 * Flags are of two types:
	 * <ul>
	 * <li>{@code boolean } {@link java.lang.reflect.Field Fields}</li>
	 * <li>{@link java.lang.reflect.Method Methods} with signature
	 * {@code void function()}</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * @see Option
	 * @see Argument
	 * 
	 * @param accessible
	 *            object to test
	 * @return {@code true} if {@code accessible} is a flag, {@code false}
	 *         otherwise
	 */
	public static boolean isFlagType(AccessibleObject accessible) {
		if (accessible instanceof Field) {
			Field field = (Field) accessible;

			Class<?> type = field.getType();
			return (type == boolean.class || type == Boolean.class);
		} else if (accessible instanceof Method) {
			Method method = (Method) accessible;

			boolean isReturnVoid = (Class<?>) method.getReturnType() == void.class;
			boolean takesNoArg = (method.getParameterTypes().length == 0);

			return (isReturnVoid && takesNoArg);
		} else {
			return false;
		}
	}

	/**
	 * Decides whether {@code AccessibleObject} is an array-like type.
	 * 
	 * <p>
	 * Array-like types are considered to be:
	 * <ul>
	 * <li>Java array, i.e. {@code String[]}</li>
	 * <li>List<?> or its subclasses, , i.e. {@code List<Integer>}</li>
	 * </ul>
	 * </p>
	 * 
	 * <p>
	 * Array-like types can be either annotated {@link java.lang.reflect.Field
	 * Fields} or the only parameter of a {@link java.lang.reflect.Method
	 * Methods}
	 * </p>
	 * 
	 * @see Option
	 * @see Argument
	 * 
	 * @param accessible
	 *            object to test
	 * @return {@code true} if {@code accessible} is an array-like,
	 *         {@code false} otherwise
	 */
	public static boolean isArrayType(AccessibleObject accessible) {
		if (accessible instanceof Field) {
			Field field = (Field) accessible;

			Class<?> type = field.getType();

			boolean isArray = type.isArray();
			boolean isAssignableFromList = List.class.isAssignableFrom(type);

			return (isArray || isAssignableFromList);

		} else if (accessible instanceof Method) {
			Method method = (Method) accessible;

			if (!isSetter(method)) {
				return false;
			}

			Class<?> type = method.getParameterTypes()[0];

			return (type.isArray() || List.class.isAssignableFrom(type));

		} else {
			return false;
		}
	}

	/**
	 * Decides whether {@code AccessibleObject} is a simple type.
	 * 
	 * 
	 * <p>
	 * Simple types are considered to be:
	 * 
	 * <ul>
	 * <li>Fields which are not of type boolean/Boolean or array-like</li>
	 * <li>Methods with void return value with single parameter which is not
	 * array-like</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * 
	 * @see Option
	 * @see Argument
	 * 
	 * @param accessible
	 *            object to test
	 * @return {@code true} if {@code accessible} is an simple, {@code false}
	 *         otherwise
	 */
	public static boolean isSimpleType(AccessibleObject accessible) {

		boolean isFlag = isFlagType(accessible);
		boolean isArray = isArrayType(accessible);

		if (accessible instanceof Field) {

			return (!isFlag && !isArray);

		} else if (accessible instanceof Method) {

			boolean isSetter = isSetter((Method) accessible);

			return (!isFlag && !isArray && isSetter);
		} else {
			return false;
		}

	}

	/**
	 * Tests whether {@code AccessibleObject} can be annotated with
	 * {@link Option}.
	 * 
	 * <p>
	 * Such {@code accessible} returns true for one of these functions:
	 * <ul>
	 * <li>{@link #isFlagType(AccessibleObject)}</li>
	 * <li>{@link #isSimpleType(AccessibleObject)}</li>
	 * <li>{@link #isArrayType(AccessibleObject)}</li>
	 * </ul>
	 * </p>
	 * 
	 * @see Option
	 * @see Argument
	 * 
	 * @param accessible
	 *            object to test
	 * @return {@code true} if {@code accessible} can be annotated with
	 *         {@code Option}, {@code false} otherwise
	 */
	public static boolean isSupportedOption(AccessibleObject accessible) {
		return (isFlagType(accessible) || isSimpleType(accessible) || isArrayType(accessible));
	}

	/**
	 * Tests whether {@code AccessibleObject} can be annotated with
	 * {@link Argument}.
	 * 
	 * <p>
	 * Such {@code accessible} can be:
	 * <ul>
	 * <li>boolean/Boolean, array-like or simple {@code Fields}</li>
	 * <li>Methods which return void and take one simple or array-like argument</li>
	 * </ul>
	 * </p>
	 * 
	 * @see Option
	 * @see Argument
	 * 
	 * @param accessible
	 *            object to test
	 * @return {@code true} if {@code accessible} can be annotated with
	 *         {@code Argument}, {@code false} otherwise
	 */
	public static boolean isSupportedArgument(AccessibleObject accessible) {
		boolean isSimple = isSimpleType(accessible);
		boolean isArray = isArrayType(accessible);

		if (accessible instanceof Field) {

			Field field = (Field) accessible;

			Class<?> type = field.getType();
			boolean isBool = (type == boolean.class || type == Boolean.class);

			return (isBool || isSimple || isArray);

		} else if (accessible instanceof Method) {

			boolean isSetter = isSetter((Method) accessible);

			return ((isSimple || isArray) && isSetter);

		} else {
			return false;
		}

	}

	// ------------------------------------------------------------------------
	// OBJECT CREATING
	// ------------------------------------------------------------------------
	/**
	 * Creates an object instance from its string representation.
	 * 
	 * <p>
	 * The instance is created either by calling a string constructor or a
	 * static factory method {@code valueOf}.
	 * <p>
	 * 
	 * <p>
	 * Enum types are created ignoring case.
	 * </p>
	 * 
	 * <p>
	 * If the type has registered factory, the factory is used to create an
	 * object.
	 * </p>
	 * 
	 * 
	 * @see FactoryRegistry
	 * 
	 * @param type
	 *            the type to create value for
	 * @param stringValue
	 *            string representation of type's object
	 * @return an object of the given type
	 * 
	 * @throws ConfException
	 *             if object cannot be created
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object valueFromString(Class<?> type, String stringValue)
			throws ConfException {

		// Creating Enums is a bit different, so it is handled separately
		if (type.isEnum()) {
			return valueOf((Class<Enum>) type, stringValue);
		}

		Class<?> creatorType = FactoryRegistry.getFactoryType(type);

		Class<?>[] argsType = { String.class };
		// First try to create the value instance by invoking a string
		// constructor of the field class.
		try {
			Constructor<?> fieldConstructor = getConstructor(creatorType,
					argsType);
			return fieldConstructor.newInstance(stringValue);

		} catch (Exception e) { /* quell the exception and try the next method */
		}

		// If there is no suitable constructor, try to create the instance by
		// invoking a static factory method.
		try {
			Method factory = getMethod(creatorType, FACTORY_METHOD_NAME,
					argsType);


			if (isFieldAssignableFrom(type, factory)) {
				return invokeMethod(factory, stringValue);
			}
		} catch (Exception e) { /* quell the exception */
		}


		throw new ConfException("Cannot create value for type %s from '%s'",
				type, stringValue);

	}



	// ------------------------------------------------------------------------
	// TYPES
	// ------------------------------------------------------------------------

	/**
	 * Returns the type of {@code accessible}.
	 * 
	 * 
	 * @see #getValueType(AccessibleObject)
	 * 
	 * @param accessible
	 *            {@code accessible} to get the type for
	 * 
	 * @return {@code accessible}'s type
	 */
	public static Class<?> getType(AccessibleObject accessible) {

		Class<?> type = null;

		if (accessible instanceof Field) {
			type = ((Field) accessible).getType();
		} else if (accessible instanceof Method) {
			type = ((Method) accessible).getParameterTypes()[0];
		} else {
			assert (false);
		}

		return type;

	}

	/**
	 * Returns the value type of {@code accessible}.
	 * 
	 * <p>
	 * For Containers or arrays it returns the stored type, i.e. for String[] it
	 * returns String.
	 * 
	 * This is different from {@link #getType(AccessibleObject) getType()}.
	 * 
	 * </p>
	 * 
	 * @see #getType(AccessibleObject)
	 * 
	 * @param accessible
	 *            {@code accessible} to get the type for
	 * 
	 * @return {@code accessible}'s type
	 */
	public static Class<?> getValueType(AccessibleObject accessible) {

		if (accessible instanceof Field) {
			Field field = (Field) accessible;

			Class<?> type;

			if (ReflectUtils.isArrayType(field)) {
				type = ReflectUtils.getGenericType(field);
			} else {
				type = field.getType();
			}

			return type;

		} else if (accessible instanceof Method) {
			Method method = (Method) accessible;

			if (method.getParameterTypes().length != 1) {
				return null;
			}

			Class<?> type = method.getParameterTypes()[0];

			if (ReflectUtils.isArrayType(method)) {
				type = ReflectUtils.getGenericType(method);
			}

			return type;

		} else {
			return null;
		}

	}

	/**
	 * 
	 * Returns name of accessible's value type.
	 * 
	 * @param accessible
	 *            {@code accessible} to get the value type name for
	 * @return value type name or an empty String
	 */
	public static String getValueTypeName(AccessibleObject accessible) {
		Class<?> type = getValueType(accessible);

		if (type != null) {
			return type.getName();
		} else {
			return new String();
		}

	}
	/**
	 * Returns true if field or method parameter is Enum type
	 * @param accessible
	 * @return true if field or method parameter is Enum type
	 */
	public static boolean isEnumType(AccessibleObject accessible){
		return isEnumType(getValueType(accessible));
	}

	/**
	 * Returns true if field or method parameter is Enum type
	 * @param type
	 * @return true if field or method parameter is Enum type
	 */
	private static boolean isEnumType(Class<?> type){
		if(type == null){
			return false;
		} else{
			return type.isEnum();
		}
	}
	/**
	 * Returns string with all enum constants.
	 * @param accessible field or method
	 * @return string with all enum constants
	 */
	public static String getEnumConstants(AccessibleObject accessible){
		Class<?> type = getValueType(accessible);
		assert (type != null);
		Object[] enumConstants = type.getEnumConstants();
		String ret = "";
		for(Object enumConstant : enumConstants){
			ret += enumConstant.toString() + ", ";
		}
		return ret;
	}

	// ------------------------------------------------------------------------
	// ANNOTATIONS
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Returns {@code accessible}'s Option annotation if it is present, else
	 * null.
	 * 
	 * 
	 * @see Option
	 * @param accessbile
	 *            {@code accessible} to get the {@code Option} annotation for.
	 * @return {@code accessible}'s Option annotation if it is present, else
	 *         null
	 */
	public static Option getOption(final AccessibleObject accessbile) {
		return accessbile.getAnnotation(Option.class);
	}

	/**
	 * 
	 * Returns {@code accessible}'s Argument annotation if it is present, else
	 * null.
	 * 
	 * 
	 * @see Argument
	 * @param accessible
	 *            {@code accessible} to get the {@code Argument} annotation for.
	 * @return {@code accessible}'s Argument annotation if it is present, else
	 *         null
	 */

	public static Argument getArgument(final AccessibleObject accessible) {
		return accessible.getAnnotation(Argument.class);
	}

	/**
	 * 
	 * Returns {@code accessible}'s Constraint annotation if it is present, else
	 * null.
	 * 
	 * 
	 * @see Constraint
	 * @param accessible
	 *            {@code accessible} to get the {@code Constraint} annotation
	 *            for.
	 * @return {@code accessible}'s Constraint annotation if it is present, else
	 *         null
	 */
	public static Constraint getConstraint(final AccessibleObject accessible) {
		return accessible.getAnnotation(Constraint.class);
	}

	/**
	 * Returns a method for the given methodName, field and parameters type.
	 * 
	 * @param field
	 * @param methodName
	 * @param argsType
	 * @return
	 * @throws Exception
	 */

	/**
	 * Returns a method of the type with given name and parameter types.
	 * 
	 * @param type
	 *            Type for which to get a method
	 * @param methodName
	 *            name of a method
	 * @param paramType
	 *            parameter types of a method
	 * @return method Method with the given name and parameter types
	 * @throws Exception
	 *             if a matching method is not found or cannot be used
	 * 
	 * @see Class#getMethod(String, Class...)
	 */
	public static Method getMethod(Class<?> type, String methodName,
			Class<?>[] paramType) throws Exception {
		return type.getMethod(methodName, paramType);
	}

	/**
	 * Returns constructor for the given type with specified parameters.
	 * 
	 * @param type
	 *            Type for which to get a constructor
	 * @param paramType
	 *            parameter types of a constructor
	 * @return Constructor with the given parameters
	 * @throws Exception
	 */
	private static Constructor<?> getConstructor(Class<?> type,
			Class<?>[] paramType) throws Exception {

		return type.getConstructor(paramType);
	}

	/**
	 * 
	 * Decides whether return value from the factory method can be assigned to a
	 * type
	 * 
	 * @param type
	 *            Type to assign factory return value to
	 * @param factory
	 *            Method to test the return value from
	 * @return {@code true} if type is assignable from factory's return type
	 * 
	 * @see Class#isAssignableFrom(Class)
	 */
	public static boolean isFieldAssignableFrom(Class<?> type, Method factory) {
		return type.isAssignableFrom(factory.getReturnType());
	}

	// ------------------------------------------------------------------------
	// AUXILIARY
	// ------------------------------------------------------------------------

	/**
	 * Returns generic class of a field or of the first method's parameter.
	 * 
	 * @param accessible
	 * @return generic type of a accessible, null on failure to decide the type
	 */
	private static Class<?> getGenericType(AccessibleObject accessible) {
		Type componentType = null;
		
		Class<?> klass = ReflectUtils.getType(accessible);

		//TODO just a quick fix
		if (klass.isArray()) {
			return klass.getComponentType();
		}

		if (accessible instanceof Field) {
			componentType = ((Field) accessible).getGenericType();
		} else if (accessible instanceof Method) {
			componentType = ((Method) accessible).getGenericParameterTypes()[0];
		} else {
			assert (false);
		}

		assert componentType != null;

		return getParameterizedType(componentType);

	}

	/**
	 * Returns generic type of componentType
	 * 
	 * <p>
	 * Black magic!
	 * </p>
	 * 
	 * @param componentType
	 * @return Generic type of Parameterized Type, null otherwise
	 */
	private static Class<?> getParameterizedType(Type componentType) {

		if (componentType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) componentType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			for (Type fieldArgType : fieldArgTypes) {
				Class<?> fieldArgClass = (Class<?>) fieldArgType;
				return fieldArgClass;
			}
		}

		return null;
	}

	/**
	 * Decides whether a method can be used as a setter.
	 * 
	 * @param method Method to test
	 * @return true if the method can be a setter
	 */
	private static boolean isSetter(Method method) {
		boolean oneArg = method.getParameterTypes().length == 1;
		boolean isReturnVoid = method.getReturnType() == void.class;

		return (oneArg && isReturnVoid);

	}

	/**
	 * Creates instance for enum type if name is one of enum constants.
	 * @param enumeration
	 * @param name
	 * @return Enumeration 
	 */
	private static <T extends Enum<T>> T valueOf(Class<T> enumeration,
			String name) {
		for (T enumValue : enumeration.getEnumConstants()) {
			if (enumValue.name().equalsIgnoreCase(name)) {
				return enumValue;
			}
		}
		throw new IllegalArgumentException("There is no value with name '"
				+ name + " in Enum " + enumeration.getClass().getName());
	}
	/**
	 * Invokes method with stringValue as parameter
	 * @param factory
	 * @param stringValue
	 * @return
	 * @throws Exception
	 */
	private static Object invokeMethod(Method factory, String stringValue) throws Exception{
		AccessibleState state = new AccessibleState(factory);

			try {
				return factory.invoke(null, stringValue);
			} catch (Exception ex) {
				throw ex;
			} finally{
				state.restore();
			}

	}

}
