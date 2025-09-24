package mi.lexer;

/**
 * User: goldolphin
 * Time: 2013-05-22 03:50
 */
public class LexException extends RuntimeException {
    public LexException(String message) {
        super(message);
    }

    public LexException(String message, Throwable cause) {
        super(message, cause);
    }
}
