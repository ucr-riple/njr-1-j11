package actionmenu;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Root module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class RootView extends GeneralView {
	
	/**
	 * The menu selection key for adding new orders.
	 */
	public static final String NEW_ORDER_KEY = "NEW";
	
	/**
	 * The menu selection key for viewing customers.
	 */
	public static final String VIEW_CUSTOMER_KEY = "CUS";

	/**
	 * The menu selection key for viewing current orders.
	 */
	public static final String VIEW_ORDERS_KEY = "CUR";
	
	/**
	 * The menu selection key for viewing administrative options.
	 */
	public static final String ADMIN_KEY = "ADM";
	
	/**
	 * The menu selection key for logging out.
	 */
	public static final String LOG_OUT_KEY = "LOG";
	
	/**
	 * The input channels for this view.
	 */
	public enum RootInChan implements InputChannel {
		
		ICDefault,
		ICMenuOption,
		ICLogOut;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum RootOutChan implements OutputChannel {
		
		OCError,
		OCTitle;
		
	}

}