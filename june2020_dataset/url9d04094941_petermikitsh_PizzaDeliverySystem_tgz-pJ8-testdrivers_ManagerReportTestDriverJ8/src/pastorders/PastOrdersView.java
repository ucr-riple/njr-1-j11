package pastorders;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Past Orders module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class PastOrdersView extends GeneralView {
	
	/**
	 * The menu selection key for modifying selected order.
	 */
	public static final String DETAILS_KEY = "DET";
	
	/**
	 * The menu selection key for refreshing a view.
	 */
	public static final String REFRESH_KEY = "REF";
	
	/**
	 * The menu selection key for confirming something.
	 */
	public static final String CONFIRM_YES_KEY = "YES";
	
	/**
	 * The menu selection key for unconfirming something.
	 */
	public static final String CONFIRM_NO_KEY = "NO";
	
	/**
	 * The menu selection key for going back.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum PastOrdersInChan implements InputChannel {

		ICListDetails,
		ICListModify,
		ICBack,
		ICRefresh,
		ICDefault,
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum PastOrdersOutChan implements OutputChannel {
		
		OCList,
		OCInstructions,
		OCError,
		OCDisplayOrder;
		
	}

}
