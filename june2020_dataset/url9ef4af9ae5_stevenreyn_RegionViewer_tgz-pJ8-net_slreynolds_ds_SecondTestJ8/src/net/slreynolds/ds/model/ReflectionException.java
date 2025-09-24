
package net.slreynolds.ds.model;

/**
 * Report an error with reflection
 */
public class ReflectionException extends Exception {
    
	private static final long serialVersionUID = -5153147521115665003L;

	public ReflectionException(String message) {
        super(message);
    }
    
    public ReflectionException(String message, Throwable cause)  {
        super(message,cause);
    }  
}
