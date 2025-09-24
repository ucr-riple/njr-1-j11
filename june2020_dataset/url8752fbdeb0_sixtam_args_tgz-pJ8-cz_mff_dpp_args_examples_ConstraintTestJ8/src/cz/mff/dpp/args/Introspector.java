package cz.mff.dpp.args;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import static cz.mff.dpp.args.ReflectUtils.getOption;
import static cz.mff.dpp.args.ReflectUtils.getArgument;
import static cz.mff.dpp.args.Logger.severe;

/**
 * This class finds all fields and method annotated with Option or Argument and
 * gives interface to get some info about them.
 * 
 */
final class Introspector {

	private Object target;
	private List<Option> declaredOptions;
	// Maps options name and aliases to Option object
	private HashMap<String, Option> options;
	// Maps name of Option (not alias) to its field
	private HashMap<Option, AccessibleObject> objectsWithOption;
	// Maps Field/Method to ist Argument annotation
	private HashMap<AccessibleObject, Argument> objectsWithArgument;

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------
	public Introspector(Object target) throws ConfException {
		this.target = target;

		options = new HashMap<String, Option>();
		objectsWithOption = new HashMap<Option, AccessibleObject>();
		objectsWithArgument = new HashMap<AccessibleObject, Argument>();
		declaredOptions = new LinkedList<Option>();
		init();
	}

	// ------------------------------------------------------------------------
	// PUBLIC METHODS
	// ------------------------------------------------------------------------
	/**
	 * Translates option to accessibleObject.
	 * 
	 * @param option
	 *            key to accessible
	 * @return Accessible object that corresponds to option
	 */
	public AccessibleObject optionToAccesible(final Option option) {
		return objectsWithOption.get(option);
	}

	/**
	 * Translates option name to option.
	 * 
	 * @param optionName
	 * @return associated Option
	 */
	public Option nameToOption(final String optionName) {
		return options.get(optionName);
	}

	/**
	 * Returns all declared options.
	 * 
	 * @return declared options
	 */
	public Collection<Option> getDeclaredOptions() {
		return declaredOptions;
	}

	/**
	 * Returns all objects with Argument annotation.
	 * 
	 * @return set of annotated objects and theirs associated Argument
	 */
	public Set<Entry<AccessibleObject, Argument>> getArguments() {
		return objectsWithArgument.entrySet();
	}

	/**
	 * Return names to all options.
	 * 
	 * @return names to options
	 */
	public Set<String> getAllOptionNames() {
		return options.keySet();
	}

	// ------------------------------------------------------------------------
	// PRIVATE METHODS
	// ------------------------------------------------------------------------
	/**
	 * Finds all methods and fields with Argument or Option annotation and saves
	 * them.
	 */
	private void init() throws ConfException {
		addFieldsWithAnnotation();
		addMethodsWithAnnotation();
	}

	/**
	 * Stores all fields with Argument or Option annotation.
	 */
	private void addFieldsWithAnnotation() throws ConfException {
		Field[] declaredFields = target.getClass().getDeclaredFields();
		addAccessibleWithAnnotation(declaredFields);
	}

	/**
	 * Stores all methods with Argument or Option annotation.
	 */
	private void addMethodsWithAnnotation() throws ConfException {
		Method[] declaredMethods = target.getClass().getDeclaredMethods();
		addAccessibleWithAnnotation(declaredMethods);
	}

	/**
	 * Stores all given accessible object that have either Option or Argument
	 * annotation.
	 * 
	 * @param accessibleObjects
	 */
	private void addAccessibleWithAnnotation(
			AccessibleObject[] accessibleObjects) throws ConfException {
		for (AccessibleObject accessible : accessibleObjects) {
			Option option = getOption(accessible);

			if (option != null) {
				if (!ReflectUtils.isSupportedOption(accessible)) {
					severe("'%s' annotated with @Option is not of supported type!",
							accessible);

					throw new ConfException(
							"'%s' annotated with @Option is not of supported type!",
							accessible);
				}

				storeOption(option);
				storeAccessible(option, accessible);
			}

			Argument argument = getArgument(accessible);

			if (argument != null && option != null) {
				throw new ConfException("'%s' has Option as well as Agrument!",
						accessible);
			}

			if (argument != null) {
				if (!ReflectUtils.isSupportedArgument(accessible)) {
					severe("'%s' annotated with @Argument is not of supported type!",
							accessible);

					throw new ConfException(
							"'%s' annotated with @Argument is not of supported type!",
							accessible);

				}
				storeArgument(argument, accessible);
			}
		}
	}

	/**
	 * Stores given argument in hashmap with accesibleObject as key.
	 * 
	 * @param argument
	 *            object with Argument annotation, will be stored in HashMap.
	 * @param accessible
	 *            object, will be stores as key to argument
	 */
	private void storeArgument(Argument argument, AccessibleObject accessible) {
		objectsWithArgument.put(accessible, argument);
	}

	/**
	 * Stores option in list with other declared options and adds option to
	 * HashMap with option name and all aliases as key.
	 * 
	 * @param option
	 *            Option to be stored
	 * @throws ArgsException
	 *             when option is already specified
	 */
	private void storeOption(Option option) throws ConfException {
		String name = option.name();

		checkOptionName(name);

		declaredOptions.add(option);
		options.put(name, option);

		for (String alias : option.aliases()) {

			checkOptionAlias(alias);

			options.put(alias, option);
		}
	}

	/**
	 * Stores accessible object with option as key.
	 * 
	 * @param option
	 *            to be stored as key to accessible
	 * @param accessible
	 *            to be stored
	 */
	private void storeAccessible(Option option, AccessibleObject accessible) {
		objectsWithOption.put(option, accessible);
	}

	/**
	 * Checks if given optionName is valid option name and if there is no other
	 * alias or option name with same value.
	 * 
	 * @param optionName
	 *            to be checked.
	 * @throws ConfException
	 *             if optionName does not pass test
	 */
	private void checkOptionName(String optionName) throws ConfException {

		if (!OptionUtils.isOption(optionName)) {
			throw new ConfException("'%s' is not a valid option name!",
					optionName);

		}

		if (options.containsKey(optionName)) {
			throw new ConfException("Option '%s' already specified!",
					optionName);
		}

	}

	/**
	 * Checks if given optionName is valid option name (or alias name) and if
	 * there is no other alias or option name with same value. Differs from
	 * previous checkOptionName only in text of exception.
	 * 
	 * @param optionName
	 * @throws ConfException
	 *             if optionName does not pass test
	 */
	private void checkOptionAlias(String optionName) throws ConfException {

		if (!OptionUtils.isOption(optionName)) {
			throw new ConfException("'%s' is not a valid alias!", optionName);

		}

		if (options.containsKey(optionName)) {
			throw new ConfException("Alias '%s' already specified!", optionName);
		}

	}
}
