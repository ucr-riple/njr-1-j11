package br.com.bit.ideias.reflection.exceptions;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class ApplyInterceptorException extends BaseReflectionDslException {

	private static final long serialVersionUID = 1L;

	public ApplyInterceptorException() {
		super();
	}

	public ApplyInterceptorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ApplyInterceptorException(final String message) {
		super(message);
	}

	public ApplyInterceptorException(final Throwable cause) {
		super(cause);
	}

}
