package viewcontroller;

/**
 * Specifies the methods that must be implemented by all command line views.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public interface GeneralViewCL {
	
	/**
	 * Called to initial the input-output loop.  When this ends, the view will
	 *  be removed.
	 */
	public void getUserInput();

}
