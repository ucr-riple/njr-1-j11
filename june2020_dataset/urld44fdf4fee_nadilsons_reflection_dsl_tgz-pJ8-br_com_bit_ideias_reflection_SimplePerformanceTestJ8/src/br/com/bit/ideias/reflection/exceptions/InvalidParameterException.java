package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Nadilson Oliveira da Silva
 * @date 19/02/2009
 * 
 */
public class InvalidParameterException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public InvalidParameterException() {
	}

	public InvalidParameterException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidParameterException(final String message) {
		super(message);
	}

	public InvalidParameterException(final Throwable cause) {
		super(cause);
	}

}
