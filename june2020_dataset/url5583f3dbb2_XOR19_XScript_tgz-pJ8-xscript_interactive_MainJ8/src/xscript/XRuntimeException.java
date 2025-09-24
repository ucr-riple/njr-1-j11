package xscript;

public class XRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2049503669920795667L;
	
	private String type;
	
	public XRuntimeException(String type, String message, Object...args) {
		super(String.format(message, args));
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	@Override
	public String toString() {
        String message = getLocalizedMessage();
        return (message != null) ? (type + ": " + message) : type;
    }

}
