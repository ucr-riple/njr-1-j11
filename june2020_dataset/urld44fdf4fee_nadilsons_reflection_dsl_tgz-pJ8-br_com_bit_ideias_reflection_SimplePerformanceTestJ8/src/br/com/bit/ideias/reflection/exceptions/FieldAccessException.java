package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class FieldAccessException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public FieldAccessException() {
		super();
	}

	public FieldAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FieldAccessException(final String message) {
		super(message);
	}

	public FieldAccessException(final Throwable cause) {
		super(cause);
	}

}
