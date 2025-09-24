package br.com.bit.ideias.reflection.exceptions;

/**
 * @author Leonardo Campos
 * @date 16/11/2009
 */
public class SyntaxException extends RQLException {
    private static final long serialVersionUID = 3506449352181458139L;

    public SyntaxException() {
        super();
    }

    public SyntaxException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SyntaxException(String arg0) {
        super(arg0);
    }

    public SyntaxException(Throwable arg0) {
        super(arg0);
    }
}
