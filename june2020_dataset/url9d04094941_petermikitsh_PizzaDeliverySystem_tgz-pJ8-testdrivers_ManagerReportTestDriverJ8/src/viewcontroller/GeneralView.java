package viewcontroller;

import java.util.ArrayList;

/**
 * This class provides an abstraction for the View.  Its responsibilities 
 *  include:
 * 	- receiving output from a GeneralController subclass and displaying it
 * 	   to the user,
 *  - processing input and passing it on the GeneralController
 * It is expected that this will be subclassed for each major component
 *  of the program, and that each subclass will have a corresponding
 *  subclass of GeneralController.  Moreover, each subclass will define
 *  its own InputChannel and OutputChannel implementations, which will be
 *  used for communication between the view and the controller.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class GeneralView {
	
	/**
	 * The controller that is in charge of managing this view.
	 */
	protected GeneralController controller;
	
	/**
	 * The abstraction of the input channel.  Implementations of this should
	 *  be of the "enum" type.
	 */
	public interface InputChannel {}
	
	/**
	 * The abstraction of the output channel.  Implementations of this should
	 *  be of the "enum" type.
	 */
	public interface OutputChannel {}
	
	/**
	 * @return This view's controller.
	 */
	public GeneralController getController() {
		return controller;
	}

	/**
	 * @param controller The new controller.
	 */
	public void setController(GeneralController controller) {
		this.controller = controller;
	}
	
	/**
	 * Tess this view to enable input to the given input channel.
	 * This method must be implemented by all GeneralView subclasses.
	 * 
	 * @param inChannel	   The channel to be enabled.
	 * @param enabled 	   Whether or not to enable input.
	 */
	public abstract void setChannelEnabled( InputChannel inChannel, 
											boolean enabled ); 

	/**
	 * Writes the given string to the indicated output channel.  
	 * This method must be implemented by all GeneralView subclasses.
	 * 
	 * @param message		The string to display.
	 * @param outChannel	The channel through which the message should be
	 * 						 displayed.
	 */
	public abstract void displayString( String message, 
										OutputChannel outChannel );
	
	/**
	 * Writes the given object to the indicated output channel.  
	 *  The protocol for sending objects must be agreed upon by the view
	 *  and itscontroller.
	 * This method must be implemented by all GeneralView subclasses.
	 * 
	 * @param object 		The object to display.
	 * @param outChannel	The channel through which the message should be
	 * 						 displayed.
	 */
	public abstract void displayObject( Object object,
										OutputChannel outChannel );
	
	/**
	 * Writes the given list to the indicated output channel.
	 *  The list can contain any object; it is up to the GeneralView subclass
	 *  and its corresponding controller to agree on a format.
	 * This method must be implemented by all GeneralView subclasses.
	 * 
	 * @param <T> 			The type of object in the list.
	 * 
	 * @param list			The list to display.
	 * @param outChannel	The channel through which the message should be
	 * 						 displayed.
	 */
	public abstract <T> void displayList( ArrayList<T> list, 
									   OutputChannel outChannel );

}
