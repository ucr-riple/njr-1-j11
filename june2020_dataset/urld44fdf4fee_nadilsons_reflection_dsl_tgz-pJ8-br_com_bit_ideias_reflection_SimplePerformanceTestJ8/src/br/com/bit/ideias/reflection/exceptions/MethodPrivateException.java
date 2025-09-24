package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class MethodPrivateException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public MethodPrivateException() {
		super();
	}

	public MethodPrivateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MethodPrivateException(final String message) {
		super(message);
	}

	public MethodPrivateException(final Throwable cause) {
		super(cause);
	}

}
