package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Leonardo Campos
 * @date 16/11/2009
 */
public class RQLException extends BaseReflectionDslException {
    private static final long serialVersionUID = -2489499682789570352L;

    public RQLException() {
        super();
    }

    public RQLException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RQLException(String arg0) {
        super(arg0);
    }

    public RQLException(Throwable arg0) {
        super(arg0);
    }
}
