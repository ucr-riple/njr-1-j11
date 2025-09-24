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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ReflectionUtil {

	/**
	 * Maps primitive strings to their type and their wrapper class
	 * (e.g. "int" -> Class&lt;Integer&gt; and java.lang.Integer)<br>
	 * <br>
	 * The primitive type can be retrieved with Boolean.TYPE or boolean.class etc.<br>
	 * The wrapper class can be retrieved with Boolean.class etc.<br>
	 * <br>
	 * <b>Map content:</b><br>
	 * Map&lt;primitive string, [primitive type, wrapper class]&gt;
	 */
	public static final Map<String, Class<?>[]> primitiveMapper = new HashMap<String, Class<?>[]>();
	static {
		//					primitive				primitive			wrapper
		//					string					type				class
		//											e.g. Class<Long>	e.g. java.lang.Long
		//					[key]					[0]					[1]
		primitiveMapper.put("boolean", 	new Class<?>[] {Boolean.TYPE, 	Boolean.class});
		primitiveMapper.put("char", 	new Class<?>[] {Character.TYPE, Character.class});
		primitiveMapper.put("int", 		new Class<?>[] {Integer.TYPE, 	Integer.class});
		primitiveMapper.put("short", 	new Class<?>[] {Short.TYPE, 	Short.class});
		primitiveMapper.put("long", 	new Class<?>[] {Long.TYPE, 		Long.class});
		primitiveMapper.put("double", 	new Class<?>[] {Double.TYPE, 	Double.class});
		primitiveMapper.put("float", 	new Class<?>[] {Float.TYPE, 	Float.class});
		primitiveMapper.put("byte", 	new Class<?>[] {Byte.TYPE, 		Byte.class});
		primitiveMapper.put("void", 	new Class<?>[] {Void.TYPE, 		Void.class});
	}

	/**
	 * Maps primitive types to their primitive string and wrapper class
	 * (e.g. Class&lt;Integer&gt; -> "int" and java.lang.Integer)<br>
	 * <br>
	 * The primitive type can be retrieved with Boolean.TYPE or boolean.class etc.<br>
	 * The wrapper class can be retrieved with Boolean.class etc.<br>
	 * <br>
	 * <b>Map content:</b><br>
	 * Map&lt;primitive type, [primitive string, wrapper class]&gt;
	 */
	public static final Map<Class<?>, Object[]> primitiveTypeMapper = new HashMap<Class<?>, Object[]>();
	static {
		for (String primitive : primitiveMapper.keySet()) {
			primitiveTypeMapper.put(primitiveMapper.get(primitive)[0], new Object[] {primitive, primitiveMapper.get(primitive)[1]});
		}
	}

	/**
	 * Maps primitive wrapper classes to their primitive string and type
	 * (e.g. Integer.class -> "int" and Class&lt;Integer&gt;)<br>
	 * <br>
	 * The primitive type can be retrieved with Boolean.TYPE or boolean.class etc.<br>
	 * The wrapper class can be retrieved with Boolean.class etc.<br>
	 * <br>
	 * <b>Map content:</b><br>
	 * Map&lt;wrapper class, [primitive string, primitive type]&gt;
	 */
	public static final Map<Class<?>, Object[]> primitiveClassMapper = new HashMap<Class<?>, Object[]>();
	static {
		for (String primitive : primitiveMapper.keySet()) {
			primitiveClassMapper.put(primitiveMapper.get(primitive)[1], new Object[] {primitive, primitiveMapper.get(primitive)[0]});
		}
	}

	private static final String[] lookupPackages = {"java.lang", "java.util"};


	/**
	 * Parses a method which is given in string form. See
	 * {@link #parseMethodProperties(String, String[])} for detailed info.<br>
	 * This method has predefined lookup packages java.lang and java.util
	 * 
	 * 
	 * @param methodString
	 * @return
	 * @see #parseMethodProperties(String, String[])
	 */
	public static MethodProperties parseMethodProperties(String methodString) {
		return parseMethodProperties(methodString, lookupPackages);
	}

	/**
	 * Parses a method which is given in string form. The method can contain
	 * parameters, enclosed in brackets. The parameters have to be given with
	 * the fully qualified class name (e.g. java.util.Map), except primitive types
	 * (like int, boolean, etc.) which will be mapped to their class (int to
	 * java.lang.Integer, boolean to java.lang.Boolean etc.).<br>
	 * <br>
	 * <b>Examples:</b><br>
	 * testMethod(java.lang.String)<br>
	 * 
	 * 
	 * @param methodString
	 * @param lookupPackages
	 * @return
	 */
	public static MethodProperties parseMethodProperties(String methodString, String[] lookupPackages) {

		//Makes sure there are no multiple spaces
		methodString = methodString.replaceAll("[ ]+", " ");
		//Makes sure there is no space after the method name
		methodString = methodString.replace(" (", "(");

		int firstBracket = methodString.indexOf("(");
		int lastBracket = methodString.lastIndexOf(")");

		//If there is an opening bracket, the closing bracket needs to be there too
		if (firstBracket != -1 && lastBracket == -1) {
			throw new ReflectionUtilError("Closing bracket missing in " + methodString);
		}

		//Method without brackets (-> no parameters)
		String part1 = methodString;
		String classTypes = "";

		if (firstBracket != -1) {
			//Method with brackets (empty or with parameters)
			part1 = methodString.substring(0, firstBracket);

			//The test for the last bracket has been done earlier where
			//the ReflectionUtilError is thrown -> we are sure it is here
			classTypes = methodString.substring(firstBracket + 1, lastBracket);
		}

		//This is where the method name starts and the return value ends
		int lastSpace = part1.lastIndexOf(" ");

		//No return type given? -> use void
		if (lastSpace == -1) {
			part1 = "void " + part1;
			lastSpace = 5;
		}

		//This is where the return value starts
		int secondLastSpace = part1.lastIndexOf(" ", lastSpace - 1);

		if (secondLastSpace == -1) {
			//Only the return type is in front of the method name
			secondLastSpace = -1;
		}

		String methodName = part1.substring(lastSpace + 1, part1.length());
		String returnType = part1.substring(secondLastSpace + 1, lastSpace);

		if (isModifier(returnType)) {
			throw new ReflectionUtilError("'" + returnType + "' is a method modifier and not a valid return type");
		}

		MethodProperties p = new MethodProperties(methodName);

		Class<?> returnClass = getClassForName(returnType, lookupPackages);

		if (returnClass == null) {
			throw new ReflectionUtilError("Invalid return type '" + returnType + "'. Failed to get class for type.");
		}

		p.setReturnType(returnClass);

		if (classTypes.length() > 0) {
			//Remove all spaces and split
			String[] types = classTypes.replaceAll(" ", "").split(",");

			//Get all the classes for the given parameter types
			for (int i = 0; i < types.length; i++) {
				Class<?> c = getClassForName(types[i], lookupPackages);
				if (c != null) {
					p.addParameterType(c);
				} else {
					p.addInvalidType(types[i]);
				}
			}
		}

		return p;
	}


	/**
	 * Returns the class for the given class name. This method accepts primitive
	 * types (like "int", "boolean", etc.).<br>
	 * A list of packages can be given to look up classes which do not contain
	 * the fully qualified class name.
	 * 
	 * @param className The class name (e.g. "int", "boolean", "java.lang.String", etc.
	 * @param lookupPackages Packages to look up classes if the fully qualified
	 * class name is not given (e.g. String instead of java.lang.String ->
	 * java.lang has to be given through lookupPackages).
	 * @return
	 */
	public static Class<?> getClassForName(String className, String[] lookupPackages) {
		if (isPrimitiveType(className)) {
			return getClassObjectForPrimitive(className);
		} else {
			if (!className.contains(".")) {
				//There is no fully qualified class name given -> use the lookup
				//packages to find the right one
				if (lookupPackages != null) {
					for (int i = 0; i < lookupPackages.length; i++) {
						try {
							return Class.forName(lookupPackages[i] + "." + className);
						} catch (ClassNotFoundException e) {
							//It was the wrong one
						}
					}
				}
			} else {
				try {
					//Class name with fully qualified class name given
					return Class.forName(className);
				} catch (ClassNotFoundException e) {
				}
			}
		}

		//Something went wrong
		return null;
	}


	/**
	 * Returns the class object for a primitive type like "int", "float", "boolean", etc.<br>
	 * Example: <code>getClassObjectForPrimitiveType("int")</code> returns
	 * <code>Class&lt;Integer&gt;</code>
	 * 
	 * @param primitive
	 * @return
	 */
	public static Class<?> getClassObjectForPrimitive(String primitive) {
		return primitiveMapper.get(primitive)[0];
	}

	/**
	 * Checks if the given string represents a primitive type like "int", "boolean",
	 * etc.
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isPrimitiveType(String className) {
		return primitiveMapper.containsKey(className);
	}

	/**
	 * Checks if the given modifierName matches any of the possible modifiers
	 * (e.g. public, static, final, etc.)
	 * 
	 * @param modifierName
	 * @return
	 */
	public static boolean isModifier(String modifierName) {
		try {
			Modifier.valueOf(modifierName);
			return true;
		} catch (Exception e) {
			//Not a modifier
			return false;
		}
	}

	/**
	 * Checks if the given object is an instance of the class c (or if the
	 * given object is of the class c).<br>
	 * This method also handles primitive types. An int object (which is handled
	 * as a Class&lt;Integer&gt;) will return true for a class c=java.lang.Integer.
	 * 
	 * @param object
	 * @param c
	 * @return
	 */
	public static boolean isInstanceOrEqualOf(Object object, Class<?> c) {

		if (isPrimitiveType(c.getCanonicalName())) {
			c = primitiveMapper.get(c.getCanonicalName())[1];
		}

		if (object.getClass().getCanonicalName().equals(c.getCanonicalName())
				|| c.isInstance(object)) {

			return true;
		}

		return false;

	}

	/**
	 * <p>Gets a {@code List} of superclasses for the given class.</p>
	 * <br />
	 * <br />
	 * Code from: https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html
	 *
	 * @param cls  the class to look up, may be {@code null}
	 * @return the {@code List} of superclasses in order going up from this one
	 *  {@code null} if null input
	 */
	public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
		if (cls == null) {
			return null;
		}

		List<Class<?>> classes = new ArrayList<Class<?>>();
		Class<?> superclass = cls.getSuperclass();

		//Travel upwards until there is no superclass any more
		while (superclass != null) {
			classes.add(superclass);
			superclass = superclass.getSuperclass();
		}

		return classes;
	}

	/**
	 * <p>Gets a {@code List} of all interfaces implemented by the given
	 * class and its superclasses.</p>
	 *
	 * <p>The order is determined by looking through each interface in turn as
	 * declared in the source file and following its hierarchy up. Then each
	 * superclass is considered in the same way. Later duplicates are ignored,
	 * so the order is maintained.</p>
	 * <br />
	 * <br />
	 * Code from: https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html
	 *
	 * @param cls  the class to look up, may be <code>null</code>
	 * @return the {@link List} of interfaces in order, <code>null</code> if null input
	 */
	public static List<Class<?>> getAllInterfaces(Class<?> cls) {
		if (cls == null) {
			return null;
		}

		//Using a hash set to avoid duplicates (only first occurrences are used)
		LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<Class<?>>();

		getAllInterfaces(cls, interfacesFound);

		return new ArrayList<Class<?>>(interfacesFound);
	}

	/**
	 * Get the interfaces for the specified class.
	 * <br />
	 * <br />
	 * Code from: https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html
	 *
	 * @param cls  the class to look up, may be <code>null</code>
	 * @param interfacesFound the {@link Set} of interfaces for the class
	 */
	private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
		while (cls != null) {
			Class<?>[] interfaces = cls.getInterfaces();

			//Collecting all interfaces and traveling upwards
			for (Class<?> i : interfaces) {
				if (interfacesFound.add(i)) {
					getAllInterfaces(i, interfacesFound);
				}
			}

			cls = cls.getSuperclass();
		}
	}


	/**
	 * Creates a short string representation of the given method.<br />
	 * The toString() method of a {@link Method} object returns the full
	 * package names, so this implementation only shows the method and class names.
	 * 
	 * @param m
	 * @return
	 */
	public static String getShortString(Method m) {
		StringBuilder sb = new StringBuilder();

		sb.append(m.getName());
		sb.append("(");

		Class<?>[] types = m.getParameterTypes();
		for (int i = 0; i < types.length; i++) {
			sb.append(types[i].getSimpleName());

			if (i > 0) {
				sb.append(", ");
			}
		}

		sb.append(")");

		return sb.toString();
	}

	/**
	 * Looks through all the class fields (class variables) in the parent object
	 * until the field of the given object is found.
	 * 
	 * @param obj The object to find the field name for
	 * @param parent The parent object in which the object is expected to be defined.
	 * @return The field name, or <code>null</code> if the object was not found.
	 */
	public static String getFieldName(Object obj, Object parent) {

		Field allFieldArray[] = parent.getClass().getDeclaredFields();

		for (Field f : allFieldArray) {
			try {
				f.setAccessible(true);
				if (f.get(parent) == obj) {
					return f.getName();
				}
			} catch (Exception e) {
				//Just skip fields which are not accessible
				continue;
			}
		}

		return null;
	}


	/***************************************************************************
	 * A class which holds all the parsed method properties from
	 * {@link ReflectionUtil#parseMethodProperties(String, String[])} or
	 * {@link ReflectionUtil#parseMethodProperties(String)}
	 * 
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	public static class MethodProperties {

		private String methodName = null;

		private Class<?> returnType = null;

		private LinkedList<Class<?>> parameterTypes = null;

		private LinkedList<String> invalidTypes = null;

		/**
		 * 
		 * 
		 * @param methodName
		 */
		public MethodProperties(String methodName) {
			this.methodName = methodName;

			parameterTypes = new LinkedList<Class<?>>();
			invalidTypes = new LinkedList<String>();
		}

		/**
		 * 
		 * 
		 * @param returnType
		 */
		protected void setReturnType(Class<?> returnType) {
			this.returnType = returnType;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public boolean hasReturnType() {
			return returnType != null;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public Class<?> getReturnType() {
			return returnType;
		}

		/**
		 * 
		 * 
		 * @param parameterType
		 */
		protected void addParameterType(Class<?> parameterType) {
			parameterTypes.add(parameterType);
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public String getMethodName() {
			return methodName;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public LinkedList<Class<?>> getParameterTypes() {
			return parameterTypes;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public Class<?>[] getParameterTypesArray() {
			return parameterTypes.toArray(new Class<?>[parameterTypes.size()]);
		}

		/**
		 * Returns a comma separated string with all the parameter types
		 * 
		 * @return
		 */
		public String getParameterTypesString() {
			return Arrays.toString(getParameterTypesArray())
					.replace("[", "")
					.replace("]", "")
					.replace("class ", "");
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public int getNumberOfParameterTypes() {
			return parameterTypes.size();
		}

		/**
		 * 
		 * 
		 * @param invalidType
		 */
		protected void addInvalidType(String invalidType) {
			invalidTypes.add(invalidType);
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public boolean hasInvalidTypes() {
			return invalidTypes.size() > 0;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public LinkedList<String> getInvalidTypes() {
			return invalidTypes;
		}

		/**
		 * Creates the method string (method name, plus parameter types in brackets)
		 * 
		 * @return
		 */
		public String getMethodString() {
			return getMethodName() + "(" + getParameterTypesString() + ")";
		}


		@Override
		public String toString() {
			return getMethodString();
		}

	}

}
