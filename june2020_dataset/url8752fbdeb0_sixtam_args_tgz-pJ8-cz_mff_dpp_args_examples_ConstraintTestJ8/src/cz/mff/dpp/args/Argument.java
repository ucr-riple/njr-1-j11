package cz.mff.dpp.args;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 
 * Binds free (command-line) arguments to objects.
 * 
 * 
 * <p>
 * Free arguments are those tokens from the input arguments which do not belong
 * to any option, does not take form of a option (in which case Exception is
 * thrown) or are listed after option list terminator ("--").
 * </p>
 * 
 * <p>
 * Free arguments have relative index - relative position (not including options
 * and their values) in which they were encountered by the parser.
 * </p>
 * 
 * <p>
 * {@code Argument} can annotate {@link java.lang.reflect.Field Fields} as well
 * as {@link java.lang.reflect.Method Methods}. And their types are similar to
 * Option's (except that {@code Argument} does not support flag types). The
 * other major difference is {@link #index()} and {@link #size()} options.
 * </p>
 * 
 * <h4>Argument parameters and annotated objects</h4>
 * <p>
 * <h5>Single valued</h5>
 * Take exactly one argument. If we let T be any primitive Java type, Enum or
 * Class except of Arrays and {@code List<?>} (or subclass of{@code List<?>})
 * then such type is determined by
 * <ul>
 * <li>{@link java.lang.reflect.Field Fields} of type T</li>
 * <li>non-static {@link java.lang.reflect.Method Methods} of the form
 * {@code void func(T)}</li>
 * </ul>
 * 
 * </p>
 * 
 * <p>
 * <h5>Multi valued</h5>
 * Take zero, one or more free arguments. If we let T be any primitive Java
 * type, Enum or Class except of Arrays and Lists&lt;?&gt; (or subclass of
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
 * <h2>Creating Objects</h2>
 * <p>
 * Please see documentation for {@link Option} (section Creating Objects), it
 * applies to {@code Arguments} as well.
 * </p>
 * 
 * @author Stepan Bokoc
 * @author Martin Sixta
 * 
 * 
 * @see Option
 * @see Parser
 * 
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Argument {

	/**
	 * 
	 * Sets whether the Argument is required.
	 * 
	 * <p>
	 * If an Argument is required, the library will check whether the argument
	 * was set. If not {@link ParseException} will be thrown.
	 * </p>
	 * 
	 * <p>
	 * The condition is tested after all input arguments are processed.
	 * </p>
	 * 
	 * 
	 * @return {@code true} if the argument is required, {@code false} if not
	 * 
	 * @see Parser
	 */
	boolean required() default false;

	/**
	 * Optional description of the argument.
	 * 
	 * 
	 * @return description of the argument
	 */

	String description() default "";

	/**
	 * Relative index of a free argument (Start position in case of
	 * {@code array/List<?>} )
	 * 
	 * <p>
	 * The index must be bigger than zero.
	 * <p>
	 * 
	 * <p>
	 * If such argument is not in the input, ParseException will be thrown.
	 * </p>
	 * 
	 * @return relative index
	 */
	int index() default 0;

	/**
	 * In case the underlying type is {@code array/List<?>} size of the created
	 * list starting from index(). If -1, all available from index().
	 * 
	 * 
	 * <p>
	 * If there is not enough parameters in the input, ParseException will be
	 * thrown.
	 * </p>
	 * 
	 * @return requested size, -1 if none specified
	 */
	int size() default -1;

	/**
	 * 
	 * Optional name of the argument.
	 * 
	 * 
	 * @return name, empty string if none specified
	 */
	String name() default "";

	/**
	 * 
	 * Factory class used to create instances for annotated Field/Methods.
	 * 
	 * <p>
	 * See section Creating Objects in {@link Option} if you need to overwrite
	 * default behavior.
	 * </p>
	 * 
	 * @return Factory class, None.class if none specified
	 */
	Class<?> factory() default None.class;

}
