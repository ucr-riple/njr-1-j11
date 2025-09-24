
package net.slreynolds.ds.export;

/**
 * Report an exception while attempting to export a
 * model.
 */
public class ExportException extends Exception {
    
	private static final long serialVersionUID = -1766477557947499640L;

	public ExportException(String message) {
        super(message);
    }
    
    public ExportException(String message, Throwable cause)  {
        super(message,cause);
    }
}
