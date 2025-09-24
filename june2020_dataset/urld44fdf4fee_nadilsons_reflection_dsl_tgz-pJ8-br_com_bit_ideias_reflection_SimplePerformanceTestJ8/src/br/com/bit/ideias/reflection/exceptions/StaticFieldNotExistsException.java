package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class StaticFieldNotExistsException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public StaticFieldNotExistsException() {
		super();
	}

	public StaticFieldNotExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StaticFieldNotExistsException(final String message) {
		super(message);
	}

	public StaticFieldNotExistsException(final Throwable cause) {
		super(cause);
	}

}
