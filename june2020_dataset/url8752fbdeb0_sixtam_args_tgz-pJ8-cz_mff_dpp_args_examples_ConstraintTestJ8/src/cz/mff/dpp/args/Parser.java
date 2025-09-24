package cz.mff.dpp.args;

import cz.mff.dpp.args.OptionUtils.Used;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import static cz.mff.dpp.args.Logger.severe;
import static cz.mff.dpp.args.Logger.info;
import static cz.mff.dpp.args.Logger.warning;

/**
 * Parses the (command-line) input of the program to configure objects with
 * {@code Option}, {@code Argument} or {@code Constraint} annotation.
 * 
 * <p>
 * The {@code Parser} is the main way how a user of the library specifies what
 * (command-line) arguments should be processed. Note that the input does not
 * have to come from the command-line, it can be "ordinary" array of tokens to
 * process, but we assume that in most cases the library will process
 * command-line arguments.
 * </p>
 * 
 * <p>
 * After specifying what parameters the program expects (see examples,
 * {@link Option}, {@link Argument} and {@code Constraint} for more details),
 * the usage of Parser is straight-forward:
 * 
 * <pre>
 * public static void main(String[] args) {
 * 	// create new instance of an annotated objects
 * 	Options options = new Options();
 * 
 * 	// create parser bind to the object
 * 	Parser parser = new Parser(timeOptions);
 * 
 * 	try {
 * 		parser.parse(args);
 * 	} catch (ParseException e) {
 * 		// handle exception
 * 		parser.usage();
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * Note: if object is annotated but not specified in the input, nothing will
 * happen (Methods will not be called, Fields will be null).
 * </p>
 * 
 * <p>
 * How to specify program's options/arguments see {@code Option},
 * {@code Argument} and {@code Constraint}
 * </p>
 * 
 * 
 * @see Option
 * @see Argument
 * @see Constraint
 * 
 * @author Stepan Bokoc
 * @author Martin Sixta
 */
public class Parser {

	/** The object to operate on. */
	private Object target;

	/** Keeps information about annotated objects in the target */
	private Introspector annotatedObjects;

	/** Keeps track of used options/arguments and theirs values */
	private Used used;

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Creates parser bind to annotated @ target} (Java Object).
	 * 
	 * @param object
	 *            annotated object to process input arguments for
	 * 
	 * @throws IllegalArgumentException
	 *             if the annotations specified in {@code target} contain
	 *             errors.
	 */
	public Parser(Object object) throws IllegalArgumentException {

		target = object;

		try {
			annotatedObjects = new Introspector(target);
		} catch (ConfException e) {
			String message = String.format("Cannot initialize target '%s'",
					target);

			severe(message);
			throw new IllegalArgumentException(message, e);
		}

		used = new Used();

	}

	// ------------------------------------------------------------------------
	// PUBLIC METHODS
	// ------------------------------------------------------------------------

	/**
	 * Parses the input parameters and configures the {@code target} object.
	 * 
	 * <p>
	 * Successful parsing of the input throws no exception.
	 * </p>
	 * 
	 * @param parameters
	 *            input
	 * @throws ParseException
	 *             when an error is encountered while parsing input parameters,
	 *             configuring the target or checking values
	 */
	public void parse(final String[] parameters) throws ParseException {

		// process input parameters
		processInputParameters(parameters);

		// process free arguments
		processArguments();

		// check post conditions
		check();

	}

	/**
	 * 
	 * Prints help message for the target to the standard output.
	 * 
	 */
	public void usage() {
		HelpUtils.printHelp(annotatedObjects);
	}

	public void usage(PrintStream out) {
		HelpUtils.printHelp(annotatedObjects, new PrintWriter(out));
	}
	public void usage(PrintWriter out) {
		HelpUtils.printHelp(annotatedObjects, out);
	}

	public String getUsageString() {

		StringWriter stringWriter = new StringWriter();

		HelpUtils.printHelp(annotatedObjects, new PrintWriter(stringWriter));

		return stringWriter.toString();

	}

	// ------------------------------------------------------------------------
	// PRIVATE METHODS
	// ------------------------------------------------------------------------

	/**
	 * 
	 * Processes (command-line) input for the {@code target}.
	 * 
	 * <p>
	 * This functions serves as the main processing loop.
	 * <p>
	 * 
	 * 
	 * @param arguments
	 *            command-line arguments to process
	 * @throws ParseException
	 *             if an error is encountered while processing input arguments
	 */
	private void processInputParameters(String[] arguments)
			throws ParseException {

		Tokenizer tokenizer = new Tokenizer(annotatedObjects, arguments);

		LinkedList<String> tokens = new LinkedList<String>();
		for (String token : tokenizer) {
			tokens.add(token);
		}

		while (!tokens.isEmpty()) {
			String current = tokens.pop();

			if (current.equals(OptionUtils.OPTION_LIST_TERMINATOR)) {
				used.addArgument(tokens);
				tokens.clear();
			} else if (OptionUtils.isOption(current)) {
				// handle options
				processOption(current, tokens);
			} else {
				used.addArgument(current);
			}
		}

	}

	/**
	 * Processes single option (parameter) from the input.
	 * 
	 * 
	 * <p>
	 * After checking that such option was defined by annotation, checks are
	 * done on string parameters. Then the value is set.
	 * </p>
	 * 
	 * <p>
	 * Note that parameters for the option are consumed from {@code tokens}.
	 * </p>
	 * 
	 * @param optionName
	 *            name of the input option
	 * @param tokens
	 *            the rest of the input arguments
	 * @throws ParseException
	 *             if an error is encountered while processing the option
	 * 
	 * @see Option
	 */
	private void processOption(String optionName, LinkedList<String> tokens)
			throws ParseException {

		info("Processing option %s", optionName);

		Option option = annotatedObjects.nameToOption(optionName);

		if (option == null) {
			throw new ParseException("Cannot parse unspecified option '%s'",
					optionName);
		}

		AccessibleObject accessible = annotatedObjects
				.optionToAccesible(option);

		assert (accessible != null);

		List<String> params = getOptionParams(accessible, option, tokens);

		used.addOption(option, params);

		if (params.isEmpty()) {
			throw new ParseException("Option '%s' requires a parameter!",
					optionName);
		}

		try {
			ConstraintUtils.checkStringValues(accessible, params);

			info("Setting option %s with %s", optionName, params.toString());

			set(accessible, params, option);

		} catch (ConfException ex) {
			throw new ParseException(ex, "Failed to set %s for option %s",
					params, optionName);
		} catch (CheckException ex) {
			throw new ParseException(ex,
					"Setting %s in %s did not pass the check.", params,
					optionName);
		}

	}

	/**
	 * 
	 * Processes annotated arguments.
	 * 
	 * <p>
	 * This is the main loop for processing objects annotated with
	 * {@code Argument}
	 * </p>
	 * 
	 * 
	 * @throws ParseException
	 * 
	 * @see Argument
	 */
	private void processArguments() throws ParseException {

		for (Entry<AccessibleObject, Argument> entry : annotatedObjects
				.getArguments()) {

			Argument argument = entry.getValue();
			AccessibleObject accessible = entry.getKey();

			List<String> stringValues = null;

			try {
				stringValues = getArgumentParams(accessible, argument);
			} catch (ConfException e) {
				throw new ParseException(e,
						"Failed to obtain parameters for argument (%s)",
						accessible);
			}

			try {

				ConstraintUtils.checkStringValues(accessible, stringValues);

				info("Setting argument %s with %s", accessible,
						stringValues.toString());

				set(accessible, stringValues, argument);

			} catch (ConfException ex) {
				throw new ParseException(ex,
						"Failed to set %s for argument %s", stringValues,
						argument.name());
			} catch (CheckException ex) {
				throw new ParseException(ex,
						"Setting %s for %s did not pass the check",
						stringValues, accessible);
			}

		}

	}

	/**
	 * 
	 * Returns parameter list for an option from the input arguments.
	 * 
	 * <p>
	 * Parameters are consumed from the {@code tokens}
	 * </p>
	 * 
	 * 
	 * @param accessible
	 *            object to get the parameters for
	 * @param option
	 *            associated {@code Option}
	 * @param tokens
	 *            input arguments
	 * 
	 * @return list of parameters for the option
	 * 
	 * @see Option
	 */
	private List<String> getOptionParams(AccessibleObject accessible,
			Option option, LinkedList<String> tokens) {

		LinkedList<String> params = new LinkedList<String>();

		if (ReflectUtils.isFlagType(accessible)) {
			// flag, set it to true

			params.add(Boolean.toString(true));

		} else if (ReflectUtils.isArrayType(accessible)) {
			// array-like type

			while (!tokens.isEmpty() && isOptionParameter(tokens.peek())) {
				params.add(tokens.pop());
			}

			if (params.isEmpty()) {
				params.addAll(OptionUtils.getDefaultValues(option));
			}

		} else {
			// single valued option
			String param = tokens.peek();

			if (param != null && isOptionParameter(param)) {
				params.add(tokens.pop());
			} else {
				List<String> defaults = OptionUtils.getDefaultValues(option);
				if (defaults.size() > 1) {
					params.add(defaults.get(0));

					if (defaults.size() != 1) {
						warning("Warning: Using only first default value for option '%s'",
								option.name());
					}
				}
			}

		}

		return params;

	}

	/**
	 * Returns parameter list for an {@code Argument} from the parsed input
	 * arguments.
	 * 
	 * @param accessible
	 * @param argument
	 * @return list of parameters for the argument
	 * @throws ConfException
	 *             if cannot be decided what parameters the argument should have
	 * 
	 * @see Argument
	 */

	private List<String> getArgumentParams(AccessibleObject accessible,
			Argument argument) throws ConfException {

		List<String> stringValues = new LinkedList<String>();

		if (ReflectUtils.isSimpleType(accessible)) {

			String stringValue;

			stringValue = used.getArgument(argument.index());
			stringValues.add(stringValue);

		} else if (ReflectUtils.isArrayType(accessible)) {

			int index = argument.index();
			int size = argument.size();

			stringValues = used.getArgument(index, size);

		} else {
			throw new ConfException("Cannot assign argument for '%s'",
					accessible);
		}

		return stringValues;
	}

	// ------------------------------------------------------------------------
	// AUXILIARY
	// ------------------------------------------------------------------------
	/**
	 * 
	 * Prepares environment and then sets objects with parameters.
	 * 
	 * 
	 * @param accessible
	 *            the object to assign parameters to
	 * @param stringValues
	 *            values for the object
	 * @param annotation
	 *            associated annotation of the object
	 * @throws CheckException
	 *             if the object does not pass some test
	 * @throws ConfException
	 *             if setting values of the object fails
	 */
	private void set(AccessibleObject accessible, List<String> stringValues,
			Annotation annotation) throws CheckException, ConfException {

		Class<?> factory = getFactoryType(annotation);
		Class<?> valueType = ReflectUtils.getValueType(accessible);

		if (factory != None.class) {
			info("Registering factory class '%s' for '%s'", factory, valueType);
			FactoryRegistry.register(valueType, factory);
		}

		Configurator.set(target, accessible, stringValues);

		if (factory != None.class) {
			info("Unregistring factory for '%s'", valueType);
			FactoryRegistry.unregister(valueType);
		}

	}

	/**
	 * 
	 * Checks the target object against specification declared by its
	 * annotations.
	 * 
	 * 
	 * @throws ParseException
	 */
	private void check() throws ParseException {
		Inspector inspector = new Inspector(annotatedObjects, used);
		try {
			inspector.check();
		} catch (CheckException ex) {
			throw new ParseException(
					"Check found some errors. See Parser.printErrors for more details");
		}

	}

	/**
	 * 
	 * Tests whether a token is a parameter (and not a known/annotated option)
	 * 
	 * <p>
	 * This is needed to make the parser more robust, Tokenizer is quite simple.
	 * </p>
	 * 
	 * @param token
	 *            input string to test
	 * @return {@code true} if token is a parameter, {@code false}
	 */
	private boolean isOptionParameter(String token) {
		boolean isOptionName = (annotatedObjects.nameToOption(token) != null);
		boolean isOptionTerminator = token
				.equals(OptionUtils.OPTION_LIST_TERMINATOR);

		if (isOptionName || isOptionTerminator) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * Returns Factory class specified in an annotation.
	 * 
	 * 
	 * @param annotation
	 *            annotation to get the factory for
	 * @return Factory class, or {@code NullFactory} if no factory was
	 *         specified.
	 * 
	 * @see None
	 */
	private static Class<?> getFactoryType(Annotation annotation) {
		if (annotation instanceof Option) {
			return ((Option) annotation).factory();
		} else if (annotation instanceof Argument) {
			return ((Argument) annotation).factory();
		} else {
			assert false : "Not supported type of AccessibleObject!";
			return None.class;
		}
	}

}
