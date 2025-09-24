
package cz.mff.dpp.args;

/**
 * This exception is thrown when any error during parsing occurs.
 * It can mean that some options was used incorrectly or
 * there this library was used incorrectly.
 *
 */
public class ParseException extends Exception {
	
	private static final long serialVersionUID = 6288510048824885708L;

	public ParseException() {
		super();
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String format, Object... args) {
		super(String.format(format, args));
	}

	public ParseException(Throwable cause, String format, Object... args) {
		super(String.format(format, args), cause);
	}

	public static void wrap(Throwable cause, String format, Object... args) throws ParseException {
		throw new ParseException(cause, format, args);
	}

	public static void wrap(Throwable cause, String message) throws ParseException {
		throw new ParseException(cause, message);
	}
}
