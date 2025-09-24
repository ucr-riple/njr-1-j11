package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Nadilson Oliveira da Silva
 * @date 19/02/2009
 * 
 */
public class InvalidStateException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public InvalidStateException() {
	}

	public InvalidStateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidStateException(final String message) {
		super(message);
	}

	public InvalidStateException(final Throwable cause) {
		super(cause);
	}

}
