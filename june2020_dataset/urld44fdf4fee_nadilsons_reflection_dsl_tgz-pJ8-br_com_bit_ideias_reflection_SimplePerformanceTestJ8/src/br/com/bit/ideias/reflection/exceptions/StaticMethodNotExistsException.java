package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class StaticMethodNotExistsException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public StaticMethodNotExistsException() {
		super();
	}

	public StaticMethodNotExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StaticMethodNotExistsException(final String message) {
		super(message);
	}

	public StaticMethodNotExistsException(final Throwable cause) {
		super(cause);
	}

}
