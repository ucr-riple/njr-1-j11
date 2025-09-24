package mi.legacy.parser;

/**
 * User: goldolphin
 * Time: 2013-07-05 17:27
 */
public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
