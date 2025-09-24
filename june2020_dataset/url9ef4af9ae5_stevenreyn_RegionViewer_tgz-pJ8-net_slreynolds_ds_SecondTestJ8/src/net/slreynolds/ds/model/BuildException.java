
package net.slreynolds.ds.model;

/**
 * Report an error while trying to build a Graph
 */
public class BuildException extends Exception {
    

	private static final long serialVersionUID = 2597666328997861895L;

	public BuildException(String message) {
        super(message);
    }
    
    public BuildException(String message, Throwable cause)  {
        super(message,cause);
    }
    
}
