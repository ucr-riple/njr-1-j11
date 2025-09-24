package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class FieldPrivateException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public FieldPrivateException() {
		super();
	}

	public FieldPrivateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FieldPrivateException(final String message) {
		super(message);
	}

	public FieldPrivateException(final Throwable cause) {
		super(cause);
	}

}
