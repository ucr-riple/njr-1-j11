package cz.mff.dpp.args;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

/**
 * 
 * Constraints are used to restrict values of objects annotated with
 * {@link Option} or {@link Argument}.
 * 
 * <p>
 * There are two types of checks {@code Constraint} perform:
 * 
 * <ul>
 * <li>checks on string representation of values (that means on the input
 * tokens)</li>
 * <li>checks on values of created objects</li>
 * </ul>
 * That also means that for checks on values, specified parameters are first
 * converted into objects (the same way as the values).
 * </p>
 * 
 * <p>
 * This annotation is mainly for convenience (and to satisfy requirements). If
 * you need something more sophisticated use annotated {@code Methods}.
 * </p>
 * 
 * <p>
 * Array-like types are checked one-by-one, the check passes if all values are
 * correct.
 * </p>
 * 
 * <p>
 * <h4>Simple example</h4>
 * 
 * <pre>
 * {@code @Option} (name = "-p", aliases = {"--port"}, required = true)
 * {@code @Constraint} (min="1025", max="65535")
 * int port;
 * </pre>
 * 
 * </p>
 * 
 * 
 * @see Option
 * 
 * @author Stepan Bokoc
 * @author Martin Sixta
 * 
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Constraint {

	/**
	 * Checks that "{@code value >= min()}"
	 * 
	 * <p>
	 * Values are compared in the following way (in order of execution):
	 * <ul>
	 * <li>{@code static compare(T x, T y)} of the object (or of comparator if
	 * specified)</li>
	 * <li>{@code value.compareTo(minValue)}</li>
	 * </ul>
	 * 
	 * 
	 * The compareTo function should have the same properties as
	 * {@link Comparable#compareTo(Object)}. {@code compare(x,y)} should produce
	 * the same result as {@code x.compareTo(y)}.
	 * </p>
	 * 
	 * <p>
	 * The check operates on values.
	 * </p>
	 * 
	 * @return string representation of minimum value, or empty string if none
	 *         specified
	 */
	String min() default "";

	/**
	 * 
	 * * Checks that "{@code value <= max()}"
	 * 
	 * 
	 * <p>
	 * Values are compared in the following way (in order of execution):
	 * <ul>
	 * <li>{@code static compare(T x, T y)} of the object (or of comparator if
	 * specified)</li>
	 * <li>{@code value.compareTo(maxValue)}</li>
	 * </ul>
	 * 
	 * 
	 * The compareTo function should have the same properties as
	 * {@link Comparable#compareTo(Object)}. {@code compare(x,y)} should produce
	 * the same result as {@code x.compareTo(y)}.
	 * 
	 * </p>
	 * 
	 * <p>
	 * The check operates on values.
	 * </p>
	 * 
	 * @return string representation of maximum value, or empty string if none
	 *         specified
	 */
	String max() default "";

	/**
	 * Allowed values for an object.
	 * 
	 * <p>
	 * The check operates on string representation of values.
	 * </p>
	 * 
	 * @return allowed values or empty array if none specified
	 */

	String[] allowedValues() default {};

	/**
	 * 
	 * Checks string representation of an objects against a regular expression.
	 * 
	 * <p>
	 * The check operates on string representation of values.
	 * </p>
	 * 
	 * @return regular expression or empty array if none specified
	 * 
	 * @see String#matches(String)
	 */
	String regexp() default "";

	/**
	 * 
	 * Whether allowed values are compared case sensitive or case insensitive.
	 * 
	 * @return {@code true} if allowed values are compared case insensitive,
	 *         {@code false} otherwise
	 */
	boolean ignoreCase() default false;

	/**
	 * The {@code Class<?>} to use when calling {@code compare(x, y)} in checks
	 * that compare values.
	 * 
	 * @return {@code Class<?>} to use to compare values, {@code None.class} if
	 *         was is specified
	 */
	Class<?> comparator() default None.class;

}
