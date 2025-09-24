package cz.mff.dpp.args;

/**
 * 
 * Exception caused by unmet constraints on annotated objects.
 * 
 * @author Stepan Bokoc
 * 
 * @see Option
 * @see Argument
 */
class CheckException extends Exception {

	private static final long serialVersionUID = 5755703389008151061L;

	public CheckException() {
		super();
	}

	public CheckException(String message) {
		super(message);
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckException(Throwable cause) {
		super(cause);
	}

	public CheckException(String format, Object... args) {
		super(String.format(format, args));
	}

	public CheckException(Throwable cause, String format, Object... args) {
		super(String.format(format, args), cause);
	}

	public static void wrap(Throwable cause, String format, Object... args)
			throws CheckException {
		throw new CheckException(cause, format, args);
	}

	public static void wrap(Throwable cause, String message)
			throws CheckException {
		throw new CheckException(cause, message);
	}
}
