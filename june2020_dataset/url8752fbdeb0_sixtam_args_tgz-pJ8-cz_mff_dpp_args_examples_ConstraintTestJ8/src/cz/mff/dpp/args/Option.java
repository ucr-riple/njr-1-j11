/**
 * Test test
 */
package cz.mff.dpp.args;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Binds command-line options and their parameters to objects.
 * 
 * <p>
 * {@code Option} is the main way how to specify which command-line options are
 * recognized by a program. {@code Option} can annotate
 * {@link java.lang.reflect.Field Fields} as well as
 * {@link java.lang.reflect.Method Methods}. Restrictions are described bellow.
 * </p>
 * 
 * 
 * <h2>Types of options</h2>
 * 
 * <h4>Simple option</h4>
 * <p>
 * Simple option take the form of "-o", i.e. character '-' followed by one
 * character other than '-'. Simple options are exactly two characters long.
 * </p>
 * 
 * <p>
 * Parameters can be supplied in two ways:
 * <ul>
 * <li>immediately after the name of the option (i.e. "-oVALUE")</li>
 * <li>separated on the command-line by white characters (i.e."-o VALUE")</li>
 * </ul>
 * 
 * </p>
 * 
 * 
 * <h4>Long option</h4>
 * <p>
 * Long option take the form of "--long-option", i.e. "--" followed by at least
 * one character excluding character '='.
 * </p>
 * 
 * <p>
 * Parameters can be supplied in two ways:
 * <ul>
 * <li>immediately after the name, followed by '=' and a parameter (i.e.
 * "--long-option=VALUE")</li>
 * <li>separated on the command-line by white characters
 * (i.e."--long-option VALUE")</li>
 * </ul>
 * </p>
 * 
 * <h3>Option parameters and annotated objects</h3>
 * <p>
 * What and how many parameters an option can take is determined by the
 * annotated objects. The following types are recognized/supported:
 * </p>
 * 
 * <p>
 * <h5>Flags</h5>
 * Flags take no parameter and are determined by Option annotation of:
 * <ul>
 * <li>{@link java.lang.reflect.Field Fields} of type {@code boolean} or
 * {@link Boolean}</li>
 * <li>non-static {@link java.lang.reflect.Method Methods} of the form
 * {@code void func()}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <h5>Single valued</h5>
 * Take exactly one parameter. If we let T be any primitive Java type, Enum or
 * Class except of Arrays and {@code List<?>} (or subclass of{@code List<?>})
 * then such type is determined by
 * <ul>
 * <li>{@link java.lang.reflect.Field Fields} of type T</li>
 * <li>non-static {@link java.lang.reflect.Method Methods} of the form
 * {@code void func(T)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <h5>Multi valued</h5>
 * Take zero, one or more parameters. If we let T be any primitive Java type,
 * Enum or Class except of Arrays and Lists&lt;?&gt; (or subclass of
 * List&lt;?&gt;) then such type is determined by
 * <ul>
 * <li>{@link java.lang.reflect.Field Fields} of type {@code T[]} or
 * {@code List<?>} (and subclasses of {@code List<?>})</li>
 * <li>non-static {@link java.lang.reflect.Method Methods} of the form
 * {@code void func(T[]} or {@code void func(List<?>)} (and subclasses of
 * {@code List<?>})</li>
 * </ul>
 * </p>
 * 
 * 
 * 
 * <p>
 * Note: Parameters for multi valued types are all command-line parameters
 * following its option name until next option is specified or option list
 * terminator is reached. This is in order not to confuse the user in separating
 * values from free arguments.
 * </p>
 * 
 * 
 * <h2>Creating Objects</h2>
 * <p>
 * To successfully assign a value to a {@code Field} or calling a {@code Method}
 * with a parameter means that the library must be able create Objects of these
 * "value types".
 * </p>
 * 
 * <p>
 * Objects are tried to be created in the following way (in order of execution):
 * <ul>
 * <li>public constructor with {@code String} parameter</li>
 * <li>static function {@code T valueOf(String value)}</li>
 * </ul>
 * where resulting objects must be assignable to the value type. If the library
 * cannot create an object, {@code ParseError} is thrown.
 * </p>
 * 
 * <p>
 * Note: Enumerations are created from string representation case-insensitive.
 * </p>
 * 
 * <p>
 * Note: When a factory Class is specified, it is used instead of the value
 * class.
 * </p>
 * 
 * <h3>Example</h3>
 * <p>
 * 
 * <pre>
 * private static final class TimeOptions {
 * 	// Options
 * 		
 * 	{@code @}Option(name = "-V", aliases = { "--version" })
 * 	public boolean printVersion;
 * 
 * 	{@code @}Option(name = "--help")
 * 	public boolean printUsage;
 * 
 * 	{@code @}Option(name = "-v", aliases = { "--verbose" })
 * 	void setVerbose() {
 * 		verbose = true;
 * 	}
 * 		
 * 	private boolean verbose;
 * 
 * 	{@code @}Option(name = "-a",  mustUseWith={"-a"})
 * 	public boolean append;
 * 
 * 	{@code @}Option(name = "-p", aliases = { "--portability" })
 * 	public boolean portability;
 * 
 * 	{@code @}Option(name = "-f", aliases = { "--format" })
 * 	public String format;
 * 
 * 	{@code @}Option(name = "-o", aliases = { "--output" })
 * 	private void setOutputFile(String outputFile) {
 * 		this.outputFile = outputFile;
 * 	}
 * 	private String outputFile;
 * 		
 * 	{@code @}Argument(index = 0)
 * 	String command;
 * 		
 * 	{@code @}Argument(index = 0)
 * 	String[] freeArguments;
 * 
 * 
 * }
 * 
 * </p>
 * 
 * 
 * @author Martin Sixta
 * @author Stepan Bokoc
 * 
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Option {

	/**
	 * 
	 * Sets whether the Option is required.
	 * 
	 * <p>
	 * If an Option is required, the library will check whether the option was
	 * used. If not {@link ParseException} will be thrown.
	 * </p>
	 * 
	 * <p>
	 * The condition is tested after all input arguments are processed.
	 * </p>
	 * 
	 * 
	 * @return {@code true} if the option is required, {@code false} if not
	 * 
	 * @see Parser
	 */
	boolean required() default false;

	/**
	 * Primary name of the option.
	 * 
	 * @return name of the option
	 */
	String name();

	/**
	 * 
	 * Other names that can be used in place of {@code name()}.
	 * 
	 * 
	 * @return aliases of the option
	 */
	String[] aliases() default {};

	/**
	 * 
	 * Specifies what other option cannot be used together with this option.
	 * 
	 * 
	 * <p>
	 * If any option specified as incompatible is used together with the current
	 * option {@link ParseException} will be thrown to indicate wrong usage.
	 * </p>
	 * 
	 * 
	 * @return incompatible options
	 */
	String[] incompatible() default {};

	/**
	 * Specifies options that must be used if the option is used as well.
	 * 
	 * <p>
	 * {@link ParseException} will be thrown if the option is used without these
	 * options.F
	 * 
	 * </p>
	 * 
	 * @return options that must be used together with this option
	 */
	String[] mustUseWith() default {};

	/**
	 * Optional description of the option.
	 * 
	 * <p>
	 * It is encouraged to specify description, as it will be shown to users if
	 * needed.
	 * </p>
	 * 
	 * @return description of the option
	 */
	String description() default "";

	/**
	 * 
	 * Factory class used to create instances to be assigned to annotated
	 * objects.
	 * 
	 * @return Factory class
	 */
	Class<?> factory() default None.class;

	/**
	 * Default values to use if none were specified by the user.
	 * 
	 * <p>
	 * Specifying default values means that the user can leave out parameters
	 * for the option, even though the parameters are required. Default values
	 * will be used instead.
	 * </p>
	 * 
	 * <p>
	 * Warning: default values will be used only if the option is specified (and
	 * parsed)!
	 * </p>
	 * 
	 * <p>
	 * Warning: if Option requires only one argument and more than one is
	 * specified in defaultValues, only the first one will be used (and warning
	 * will be logged).
	 * </p>
	 * 
	 * @return default values
	 */
	String[] defaultValues() default {};
}
