package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class ConstructorNotExistsException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public ConstructorNotExistsException() {
	}

	public ConstructorNotExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConstructorNotExistsException(final String message) {
		super(message);
	}

	public ConstructorNotExistsException(final Throwable cause) {
		super(cause);
	}

}
