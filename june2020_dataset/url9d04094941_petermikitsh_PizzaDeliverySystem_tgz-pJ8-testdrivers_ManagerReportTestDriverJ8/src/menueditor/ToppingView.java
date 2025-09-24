package menueditor;

import viewcontroller.GeneralView;

/**
 * The abstract view for the topping editor module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public abstract class ToppingView extends GeneralView {

	/**
	 * The menu selection key for adding menu items or toppings.
	 */
	public static final String ADD_KEY = "ADD";
	
	/**
	 * The menu selection key for editing menu item data.
	 */
	public static final String UPDATE_KEY = "UPD";
	
	/**
	 * The menu selection key for canceling an update.
	 */
	public static final String CANCEL_KEY = "CAN";
	
	/**
	 * The menu selection key for deleting selected menu items.
	 */
	public static final String DELETE_KEY = "DEL";
	
	/**
	 * The menu selection key for modifying selected menu items.
	 */
	public static final String MODIFY_KEY = "MOD";
	
	/**
	 * The input channels for this view.
	 */
	public enum ToppingInChan implements InputChannel {
		
		ICCancel,
		ICToppingData,
		ICListModify,
		ICListDelete;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum ToppingOutChan implements OutputChannel {
		
		OCList,
		OCDisplayTopping,
		OCEditTopping;
		
	}

}
