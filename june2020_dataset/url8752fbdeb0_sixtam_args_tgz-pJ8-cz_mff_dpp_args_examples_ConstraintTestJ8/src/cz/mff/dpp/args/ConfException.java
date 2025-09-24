package cz.mff.dpp.args;

/**
 * 
 * Exception caused configuration errors of annotated objects.
 * 
 * @author Stepan Bokoc
 * 
 * @see Option
 * @see Argument
 */
class ConfException extends Exception {

	private static final long serialVersionUID = -6434036932983497468L;

	public ConfException() {
		super();
	}

	public ConfException(String message) {
		super(message);
	}

	public ConfException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfException(Throwable cause) {
		super(cause);
	}

	public ConfException(String format, Object... args) {
		super(String.format(format, args));
	}

	public ConfException(Throwable cause, String format, Object... args) {
		super(String.format(format, args), cause);
	}

	public static void wrap(Throwable cause, String format, Object... args)
			throws ConfException {
		throw new ConfException(cause, format, args);
	}

	public static void wrap(Throwable cause, String message)
			throws ConfException {
		throw new ConfException(cause, message);
	}

}
