package cz.mff.dpp.args;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.mff.dpp.args.ConfException;
import static cz.mff.dpp.args.Logger.severe;

/**
 * Utility class for {@link Option} and related functions/constants for working
 * with input options, mainly used by {@code Parser} to process input
 * parameters.
 * 
 * 
 * @see Option
 * @see Parser
 * 
 * @author Martin Sixta
 * @author Stepan Bokoc
 * 
 */
final class OptionUtils {

	/**
	 * Prefix used together with simple options.
	 * 
	 * @see Parser
	 */
	public static final String SIMPLE_OPTION_PREFIX = "-";

	/**
	 * Prefix used together with long options.
	 * 
	 * @see Parser
	 */
	public static final String LONG_OPTION_PREFIX = "--";

	/**
	 * Option list terminator.
	 * 
	 * <p>
	 * When this token is encountered all tokens following are considered to be
	 * free argument.
	 * </p>
	 * 
	 * @see Parser
	 */
	public static final String OPTION_LIST_TERMINATOR = "--";

	/**
	 * 
	 * Indicates whether a token takes the form of a simple option.
	 * 
	 * @see Parser
	 * @see Option
	 * 
	 * @param token
	 *            {@code String} to test against
	 * @return {@code true} if token takes the form of a simple option,
	 *         {@code false} otherwise
	 */
	public static boolean isSimpleOption(String token) {
		if (token.startsWith(SIMPLE_OPTION_PREFIX) && (token.length() == 2)
				&& !token.equals(OPTION_LIST_TERMINATOR)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Indicates whether a token takes the form of a long option.
	 * 
	 * @see Parser
	 * @see Option
	 * 
	 * @param token
	 *            {@code String} to test against
	 * @return {@code true} if token takes the form of a long option,
	 *         {@code false} otherwise
	 * 
	 */
	public static boolean isLongOption(String token) {
		if (token.startsWith(LONG_OPTION_PREFIX) && token.length() > 2
				&& (token.indexOf('=') == -1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Indicates whether a token takes the form of a long or a simple option.
	 * 
	 * @see Parser
	 * @see Option
	 * 
	 * @param token
	 *            {@code String} to test against
	 * @return {@code true} if token takes form of a long/simple option,
	 *         {@code false} otherwise
	 */
	public static boolean isOption(String token) {
		return (isSimpleOption(token) || isLongOption(token));
	}

	/**
	 * Returns list of default values of a option.
	 * 
	 * @param option
	 *            annotation to get default values for
	 * @return list of default values, or empty list if default values were not
	 *         specified for the option
	 * 
	 * @see Option
	 */
	public static List<String> getDefaultValues(Option option) {
		return Arrays.asList(option.defaultValues());
	}

	/**
	 * 
	 * Auxiliary class to hold data about parsed arguments, options and theirs
	 * values.
	 * 
	 * @see Option
	 * @see Parser
	 * 
	 */
	public static final class Used {
		/** Stores parameters of used Options. */
		private Map<Option, List<String>> usedOptionParams;

		/** Stores position in which an option was parsed. */
		private Map<Option, Integer> usedOptionPosition;

		// Ehh, no std::pair in Java :(
		/** Stores free arguments. */
		private List<String> freeArguments;

		/** Stores position in which an argument was parsed. */
		private List<Integer> freeArgumentsPosition;

		/** Keeps track of used positions. */
		int position = 0;

		/**
		 * Creates new object in which information about parsed options and
		 * arguments can be stored.
		 */
		public Used() {
			usedOptionParams = new HashMap<Option, List<String>>();
			usedOptionPosition = new HashMap<Option, Integer>();

			freeArguments = new LinkedList<String>();
			freeArgumentsPosition = new LinkedList<Integer>();

		}

		/**
		 * 
		 * Stores used option along its parameters.
		 * 
		 * 
		 * @param option
		 *            {@code Option} used
		 * @param parameters
		 *            used parameters
		 * @throws ParseException
		 *             if such option was already used
		 */
		public void addOption(final Option option, final List<String> parameters)
				throws ParseException {
			if (usedOptionParams.containsKey(option)) {
				throw new ParseException(
						"Option %s (with parameters %s) already parsed!",
						option.name(), parameters.toString());
			}

			usedOptionParams.put(option, parameters);

			int nextPosition = getNextPosition();
			usedOptionPosition.put(option, nextPosition);
		}

		/**
		 * Stores arguments value.
		 * 
		 * <p>
		 * Arguments are stored in order in which they have been added.
		 * </p>
		 * 
		 * 
		 * @param value
		 *            arguments's value to store
		 */
		public void addArgument(String value) {
			freeArguments.add(value);

			int nextPosition = getNextPosition();
			freeArgumentsPosition.add(nextPosition);
		}

		/**
		 * Stores arguments values.
		 * 
		 * <p>
		 * Arguments are stored in order in which they have been added.
		 * </p>
		 * 
		 * 
		 * @param values
		 *            arguments's values to store
		 */
		public void addArgument(List<String> values) {
			for (String value : values) {
				freeArguments.add(value);
				int nextPosition = getNextPosition();
				freeArgumentsPosition.add(nextPosition);
			}
		}

		/**
		 * 
		 * Returns stored argument relative to other stored arguments.
		 * 
		 * @param index
		 *            relative argument's index
		 * @return stored argument
		 * 
		 * @throws if
		 *             such argument is not available
		 * 
		 * @see Parser
		 */
		public String getArgument(int index) throws ConfException {
			try {
				return freeArguments.get(index);
			} catch (Exception e) {
				String message = String.format("No argument with index %d!",
						index);

				severe(message);
				throw new ConfException(e, message);
			}

		}
		/**
		 * Return true if argument with given index exists, false otherwise
		 * @param index index of argument
		 * @return true if argument with given index exists, false otherwise
		 */
		public boolean isArgumentUsed(int index){
			if(index < freeArguments.size()){
				return true;
			} else{
				return false;
			}
		}

		/**
		 * 
		 * Returns list of stored arguments of requested size, relative to other
		 * arguments.
		 * 
		 * @param index
		 *            start position relative to other stored arguments
		 * @param size
		 *            requested size, or value <= 0 for maximum size
		 * 
		 * @return list of arguments with requested attributes
		 * 
		 * @throws if
		 *             such argument list cannot be assembled
		 * @see Parser
		 */
		public List<String> getArgument(int index, int size)
				throws ConfException {

			int fromIndex = index;
			int toIndex;

			if (size <= 0) {
				toIndex = freeArguments.size();
			} else {
				toIndex = fromIndex + size;
			}

			try {
				return freeArguments.subList(fromIndex, toIndex);
			} catch (IndexOutOfBoundsException e) {

				String message = String.format(
						"List of arguments [%d, %d) cannot be assemlbed!",
						fromIndex, toIndex);
				
				severe(message);
				throw new ConfException(e, message);
			}

		}

		/**
		 * 
		 * Returns parameters of used option, or {@code null} if no such option
		 * was used.
		 * 
		 * @param option
		 *            the option whose associated parameters are to be returned
		 * @return the parameters of the specified option, or {@code null} if no
		 *         such option was used.
		 */
		public List<String> getOptionParams(Option option) {
			return usedOptionParams.get(option);
		}

		/**
		 * 
		 * Returns the position in which an option was used option, or
		 * {@code -1} if no such option was used.
		 * 
		 * @param option
		 *            the option whose position is to be returned
		 * @return the position of the specified option, or {@code -1} if no
		 *         such option was used.
		 */
		public int getOptionPosition(Option option) {
			Integer result = usedOptionPosition.get(option);

			if (result != null) {
				return result;
			} else {
				return -1;
			}

		}

		/**
		 * 
		 * Returns all used {@code Options}
		 * 
		 * @return Collection of {@code Options}
		 */
		public Collection<Option> getUsedOptions() {
			return usedOptionParams.keySet();
		}

		/**
		 * 
		 * Decides whether an {@code Option} was used.
		 * 
		 * 
		 * @param option
		 *            the {@code Option} to be tested
		 * @return true of option was used, false otherwise
		 */
		public boolean isOptionUsed(Option option) {
			return usedOptionParams.containsKey(option);
		}

		/**
		 * 
		 * Returns next position to be used.
		 * 
		 * 
		 * @return next position
		 */
		private int getNextPosition() {
			return position++;
		}

	}

}
