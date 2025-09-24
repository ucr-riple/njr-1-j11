package neworder;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Customer module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class NewOrderView extends GeneralView {
	
	/**
	 * The menu selection key for adding a pizza.
	 */
	public static final String ADD_PIZZA_KEY = "PIZ";
	
	/**
	 * The menu selection key for adding a side.
	 */
	public static final String ADD_SIDE_KEY = "SID";
	
	/**
	 * The key for deleting an item from the food list.
	 */
	public static final String DELETE_KEY = "DEL";
	
	/**
	 * The key for modifying an item on the food list.
	 */
	public static final String MODIFY_KEY = "MOD";
	
	/**
	 * The menu selection key for viewing the confirmation screen.
	 */
	public static final String DONE_KEY = "DON";
	
	/**
	 * The mneu selection key for confirming no.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The menu selection key for the canceling the order.
	 */
	public static final String CANCEL_KEY = "CAN";
	
	/**
	 * Indicates that order view should be refreshed.
	 */
	public static final String REFRESH_KEY = "REF";
	
	/**
	 * The input channels for this view.
	 */
	public enum NewOrderInChan implements InputChannel {
		
		ICMenuOption,   // add pizza, add side, done, yes, no, cancel
		ICConfirm,
		ICListModify,
		ICListDelete,
		ICStringInput,
		ICCustomerName,
		ICCustomerPhone,
		ICCustomerAddress,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum NewOrderOutChan implements OutputChannel {
		
		OCInstructions,
		OCCustomerDisplay,
		OCFoodItemList,
		OCOrderDisplay,
		OCSubTotalDisplay,
		OCTaxDisplay,
		OCTotalDisplay,
		OCDeliveryTimeDisplay,
		OCError;
		
	}

}
