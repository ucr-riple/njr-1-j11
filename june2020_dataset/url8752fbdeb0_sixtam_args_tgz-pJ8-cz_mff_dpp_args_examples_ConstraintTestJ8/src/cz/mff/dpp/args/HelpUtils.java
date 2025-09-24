package cz.mff.dpp.args;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.Map.Entry;

/**
 * 
 * Collection of utility functions to help with usage of the library.
 * 
 * 
 * @author Martin Sixta
 * @author Stepan Bokoc
 * 
 */
class HelpUtils {

	/**
	 * 
	 * Prints help to the standard output for annotated objects.
	 * 
	 * 
	 * @param introspector
	 *            annotated objects
	 */
	public static void printHelp(final Introspector introspector) {
		printHelp(introspector, new PrintWriter(System.out));
	}

	public static void printHelp(final Introspector introspector, PrintWriter out) {
		out.println("Usage: OPTIONS");
		for (Option option : introspector.getDeclaredOptions()) {
			printHelpForOption(option, introspector.optionToAccesible(option), out);
		}

		out.println("Usage: ARGUMENTS");
		for (Entry<AccessibleObject, Argument> entry : introspector
				.getArguments()) {
			printHelpForArgument(entry.getValue(), entry.getKey(), out);
		}
	}

	/**
	 * 
	 * Prints help for an annotated object.
	 * 
	 * @param option
	 *            annotation for the accessible object
	 * @param accessible
	 *            annotated object to print help for
	 * @param out
	 * 			  where to print output
	 */
	private static void printHelpForOption(Option option,
			AccessibleObject accessible, PrintWriter out) {

		out.printf("\t%s ", option.name());
		for (String alias : option.aliases()) {
			out.printf(", %s ", alias);
		}

		if (ReflectUtils.isFlagType(accessible)) {
			out.printf("[flag]");
		} else if (ReflectUtils.isSimpleType(accessible)) {
			out.printf("[%s]", ReflectUtils.getValueTypeName(accessible));
		} else if (ReflectUtils.isArrayType(accessible)) {
			out.printf("[array of %s]",
					ReflectUtils.getValueTypeName(accessible));
		}

		if (option.required()) {
			out.printf(" REQUIRED ");
		}

		if (ReflectUtils.isEnumType(accessible)) {
			out.printf("\n\t\tAllowed values: %s",
					ReflectUtils.getEnumConstants(accessible));
		} 

		out.printf("\n\t\t %s \n", option.description());

		if (option.incompatible().length > 0) {
			out.printf("\n\t\t NOTE: Cannot be used together with: ");
			for (String incompatilbe : option.incompatible()) {
				out.printf("%s ", incompatilbe);

			}
		}

		if (option.mustUseWith().length > 0) {
			out.printf("\n\t\t NOTE: Used together with: ");
			for (String with : option.mustUseWith()) {
				out.printf("%s ", with);

			}
		}

		printConstraint(accessible, out);
		
		out.println();

	}

	private static void printHelpForArgument(Argument argument,
			AccessibleObject accessible, PrintWriter out) {

		if (!argument.name().isEmpty()) {
			out.printf("\t%s ", argument.name());
		} else {
			out.printf("\t%s ", "ARGUMENT");
		}

		if (ReflectUtils.isArrayType(accessible)) {
			out.printf("[array of %s] ",
					ReflectUtils.getValueTypeName(accessible));
			
			if (argument.size() > 0 ) {
				out.printf("(index: %d, size: %d)", argument.index(), argument.size() );
			} else {
				out.printf("(all from index %d)", argument.index());
			}
		} else {
			out.printf("[%s] ", ReflectUtils.getValueTypeName(accessible));
			out.printf("(index: %d)", argument.index() );
		}

		if (argument.required()) {
			out.printf(" REQUIRED ");
		}

		out.printf("\n\t\t %s \n", argument.description());
		
		printConstraint(accessible, out);
		out.println();

	}
	
	private static void printConstraint(AccessibleObject accessible, PrintWriter out) {
		Constraint constraint = ReflectUtils.getConstraint(accessible);
		
		if (constraint == null) {
			return;
		}
		boolean some = false;
		
		out.printf("\t\tconstraints: ");
		
		if (!constraint.min().isEmpty()) {
			out.printf("min=%s ",constraint.min());
			some = true;
		}
		
		if (!constraint.max().isEmpty()) {
			out.printf("max=%s ",constraint.max());
			some = true;
		}
		
		if (constraint.allowedValues().length > 0) {
			out.printf("allowed values=%s ",Arrays.toString(constraint.allowedValues()));
			out.printf("(ignore case=%s) ",constraint.ignoreCase());
			some = true;
		}
		
		if (!constraint.regexp().isEmpty()) {
			out.printf("regexp=%s ",constraint.regexp());
			some = true;
		}
		
		if (!some) {
			out.println("none");
		} else {
			out.println("");
		}
		
	}

}
