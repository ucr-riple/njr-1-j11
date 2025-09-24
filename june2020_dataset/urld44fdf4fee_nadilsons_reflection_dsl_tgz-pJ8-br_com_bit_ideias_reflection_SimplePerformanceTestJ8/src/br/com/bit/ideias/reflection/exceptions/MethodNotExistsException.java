package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class MethodNotExistsException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public MethodNotExistsException() {
		super();
	}

	public MethodNotExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MethodNotExistsException(final String message) {
		super(message);
	}

	public MethodNotExistsException(final Throwable cause) {
		super(cause);
	}

}
