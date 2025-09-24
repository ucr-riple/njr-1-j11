package cz.mff.dpp.args;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * Register of factory Classes.
 * 
 * <p>
 * When creating objects from their String representation we can stumble upon several
 * difficulties
 * </p>
 * 
 * <p>
 * The very fundamental one is that primitive types in Java are not objects. To
 * compensate this problem wrapper classes exits for primitive types. To ease 
 * selecting appropriate wrapper class {@code FactoryRegister} is used.
 * </p>
 * 
 * <p>
 * The other problem might occur when user is not satisfied with class's native
 * constructing and wants to override it (by specifying appropriate annotation)
 * {@code FactoryRegistry} is here to help.
 * </p>
 * 
 * <p>
 * NOTE: Usage of this class is not thread-safe.
 * </p>
 * 
 * @author Martin Sixta
 *
 */
final class FactoryRegistry {

	private static Map<Class<?>, Class<?>> primiteTypes;
	private static Map<Class<?>, Class<?>> userTypes;
	

	static {
		primiteTypes = new HashMap<Class<?>, Class<?>>();
		userTypes = new HashMap<Class<?>, Class<?>>();

		primiteTypes.put(int.class, Integer.class);
		primiteTypes.put(short.class, Short.class);
		primiteTypes.put(boolean.class, Boolean.class);
		primiteTypes.put(double.class, Double.class);
		primiteTypes.put(float.class, Float.class);
		primiteTypes.put(char.class, CharacterHandler.class);
		primiteTypes.put(Character.class, CharacterHandler.class);
		primiteTypes.put(char.class, CharHandler.class);
		primiteTypes.put(byte.class, Byte.class);
	}
	
	/**
	 * Disable creating of instances.
	 * 
	 */
	private FactoryRegistry() {
		
	}
	
	
	/**
	 * Registers user-defined factory method.
	 * 
	 * @param type {@link Class} to register factory for.
	 * @param factoryClass {@link Class} which creates objects of {@code type}
	 */
	public static void register(Class<?> type, Class<?> factoryClass) {
		userTypes.put(type, factoryClass);
	}

	/**
	 * Unregisters used-defined factory.
	 * 
	 * 
	 * @param type {@link Class} to unregister. If factory class was
	 * 				specified for this {@code type} it will be no longer
	 * 				used to create objects of {@code type}
	 */
	public static void unregister(Class<?> type) {
		userTypes.remove(type);
	}

	/**
	 * Returns factory {@link Class} for the given {@code type}
	 * 
	 * <p>
	 * If user defined factory class has been specified for the given {@code type}
	 * </p>
	 * 
	 * 
	 * 
	 * @param type {@code type} for which to get it's factory class
	 * @return If user-defined factory class has been specified for the given
	 * 		   {@code type}. If not and {@code type} is primitive type, its
	 * 		   wrapper class is return. Otherwise the {@code type} itself.
	 */
	public static Class<?> getFactoryType(Class<?> type) {

		Class<?> userType = userTypes.get(type);
		Class<?> primitiveType = primiteTypes.get(type);

		if (userType != null) {
			return userType;
		} else if (primitiveType != null) {
			return primitiveType;
		} else {
			return type;
		}
	}

}
