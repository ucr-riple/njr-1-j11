package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class ObjectCreateException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public ObjectCreateException() {
	}

	public ObjectCreateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ObjectCreateException(final String message) {
		super(message);
	}

	public ObjectCreateException(final Throwable cause) {
		super(cause);
	}

}
