package hep.io.root.output;

/**
 * An exception which is thrown when an error occurs during processing of
 * streamer information.
 * @author tonyj
 */
class StreamerInfoException extends Exception {

    private String fieldName;
    private String className;

    StreamerInfoException(String message) {
        super(message);
    }

    StreamerInfoException(String message, StreamerInfoException x) {
        super(message, x);
    }

    void setField(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        String result = super.getMessage();
        if (fieldName != null) {
            result += String.format("\n\tWhile handling field %s of class %s", fieldName, className);
        }
        return result;
    }
}
