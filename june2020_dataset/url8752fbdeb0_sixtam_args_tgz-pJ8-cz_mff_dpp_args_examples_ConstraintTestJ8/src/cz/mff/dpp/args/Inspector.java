package cz.mff.dpp.args;

import cz.mff.dpp.args.OptionUtils.Used;
import static cz.mff.dpp.args.Logger.severe;
import java.lang.reflect.AccessibleObject;
import java.util.Map.Entry;

/** This class is used to check if values set by ArgsLibrary was set correctly.
 * Checks if all required options were used, none of incompatible options
 * were used and all dependent options for every option were used.
 *
 * Every mistake in usage is logged
 *
 */
final class Inspector {

	private final Introspector annotatedObjects;
	private final Used used;
	private boolean passed = true;

	public Inspector(final Introspector annotatedObjects, final Used used) {
		this.annotatedObjects = annotatedObjects;
		this.used = used;

	}

	/**
	 * Checks if all required options were used and if
	 * options used are compatible.
	 * @throws CheckException
	 */
	public void check() throws CheckException {
		checkAllRequiredOptionsUsed();
		checkCompatibility();
		checkAllRequiredArgumentsUsed();

		if (!passed) {
			throw new CheckException("Taget object did not pass check. "
					+ "All errors logged with level SEVERE");
		}
	}

	/**
	 * Checks if all required options were used. If some was not, error is logged.
	 */
	private void checkAllRequiredOptionsUsed() {
		for (Option option : annotatedObjects.getDeclaredOptions()) {
			if (option.required()) {
				if (!used.isOptionUsed(option)) {
					logError("Mandatory option %s was not used", option.name());
				}

			}
		}
	}

	/**
	 * Checks if all used options are compatible with others and if all
	 * dependent options were used for every option.
	 */
	private void checkCompatibility() {
		for (Option usedOption : used.getUsedOptions()) {
			checkIncompatibleOptionsUsed(usedOption);
			checkDependentOptionsUsed(usedOption);

		}
	}

	/**
	 * Checks if given option is compatible with all other used.
	 * @param usedOption option to check
	 */
	private void checkIncompatibleOptionsUsed(Option usedOption) {
		for (String incompatibleOptionString : usedOption.incompatible()) {
			Option incompatibleOption = annotatedObjects.nameToOption(incompatibleOptionString);
			if (used.isOptionUsed(incompatibleOption)) {
				logError("Options %s and %s can not be used together",
						usedOption.name(), incompatibleOption.name());
			}
		}
	}

	/**
	 * Check if all option required to use given on were used.
	 * @param usedOption option to check
	 */
	private void checkDependentOptionsUsed(Option usedOption) {
		for (String useOnlyWithOptionString : usedOption.mustUseWith()) {

			Option useOnlyWithOption = annotatedObjects.nameToOption(useOnlyWithOptionString);

			if (!used.isOptionUsed(useOnlyWithOption)) {
				logError("Option %s can be used only with option %s",
						usedOption.name(), useOnlyWithOption.name());
			}
		}
	}
	/**
	 * Check if all required arguments were used. Logs all unused arguments
	 * with Level.SEVERE.
	 */
	private void checkAllRequiredArgumentsUsed(){

		for(Entry<AccessibleObject, Argument> arguments : annotatedObjects.getArguments()){
			Argument argument = arguments.getValue();
			if(argument.required()){
				if(!used.isArgumentUsed(argument.index())){
					logError("Mandatory argument %s with index %s not used",
							argument.name(), argument.index());
				}
			}
		}
	}

	/**
	 * Logs error and sets passed flag to false
	 * @param format formatted message to be logged
	 * @param args
	 */
	private void logError(String format, Object... args) {
		severe(format, args);
		passed = false;
	}


}
