package br.com.bit.ideias.reflection.exceptions.pkgscanner;

import br.com.bit.ideias.reflection.exceptions.BaseReflectionDslException;
/**
 * @author Leonardo Campos
 * @date 12/12/2009
 */
public class PackageScannerException extends BaseReflectionDslException {
	private static final long serialVersionUID = -72779997540747291L;

	public PackageScannerException() {
		super();
	}

	public PackageScannerException(String message, Throwable cause) {
		super(message, cause);
	}

	public PackageScannerException(String message) {
		super(message);
	}

	public PackageScannerException(Throwable cause) {
		super(cause);
	}
}
