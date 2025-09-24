package xscript;

import javax.script.ScriptException;

public class XScriptException extends ScriptException {

	private static final long serialVersionUID = 738168386179165762L;

	private Throwable cause;
	
	private String fileName;
	
	private int line = -1;
	
	public XScriptException(){
		super("");
	}
	
	public XScriptException(String message) {
		super(message);
	}

	public XScriptException(String message, String fileName) {
		super(message);
		this.fileName = fileName;
	}
	
	public XScriptException(String message, String fileName, int line) {
		super(message);
		this.fileName = fileName;
		this.line = line;
	}
	
	public XScriptException(Throwable cause){
		super("");
		this.cause = cause;
	}
	
	public XScriptException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}

	public XScriptException(String message, String fileName, Throwable cause) {
		super(message);
		this.fileName = fileName;
		this.cause = cause;
	}
	
	public XScriptException(String message, String fileName, int line, Throwable cause) {
		super(message);
		this.fileName = fileName;
		this.line = line;
		this.cause = cause;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public int getLineNumber() {
		return line;
	}

	@Override
	public String getMessage() {
        String ret = super.getMessage();
        if (fileName != null) {
            ret += (" in " + fileName);
            if (line != -1) {
                ret += " at line number " + line;
            }
        }
        return ret;
    }
	
	@Override
	public synchronized Throwable getCause() {
		return cause;
	}
	
}
