package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class MethodAccessException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public MethodAccessException() {
		super();
	}

	public MethodAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MethodAccessException(final String message) {
		super(message);
	}

	public MethodAccessException(final Throwable cause) {
		super(cause);
	}

}
