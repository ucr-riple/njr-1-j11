package currentorders;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Current Orders module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class CurrentOrdersView extends GeneralView {
	
	/**
	 * The menu selection key for deleting selected orders.
	 */
	public static final String DELETE_KEY = "DEL";
	
	/**
	 * The menu selection key for modifying selected order.
	 */
	public static final String MODIFY_KEY = "MOD";
	
	/**
	 * The menu selection key for viewing the details of a selected order.
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
	public enum CurrentOrdersInChan implements InputChannel {
		
		ICListModify,
		ICListDelete,
		ICListDetails,
		ICMenuOption,
		ICConfirm,
		ICBackRefresh,
		ICDefault,
		ICRefresh,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum CurrentOrdersOutChan implements OutputChannel {
		
		OCList,
		OCInstructions,
		OCConfirm,
		OCError,
		OCDisplayOrder;
		
	}

}
