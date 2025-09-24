package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class FieldNotExistsException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public FieldNotExistsException() {
		super();
	}

	public FieldNotExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FieldNotExistsException(final String message) {
		super(message);
	}

	public FieldNotExistsException(final Throwable cause) {
		super(cause);
	}

}
