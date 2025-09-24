package usereditor;

import viewcontroller.GeneralView;

/**
 * The abstract view for the User module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class UserEditorView extends GeneralView {
	
	/**
	 * The menu selection key for adding customers.
	 */
	public static final String ADD_KEY = "ADD";
	
	/**
	 * The menu selection key for editing customer data.
	 */
	public static final String UPDATE_KEY = "UPD";
	
	/**
	 * The menu selection key for canceling an update.
	 */
	public static final String CANCEL_KEY = "CAN";
	
	/**
	 * The menu selection key for deleting selected customers.
	 */
	public static final String DELETE_KEY = "DEL";
	
	/**
	 * The menu selection key for modifying selected customers.
	 */
	public static final String MODIFY_KEY = "MOD";
	
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
	public enum UserEditorInChan implements InputChannel {
		
		ICListModify,
		ICListDelete,
		ICMenuOption, // Add, Update, Cancel (update)
		ICUserData,
		ICConfirm,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum UserEditorOutChan implements OutputChannel {
		
		OCList,
		OCInstructions,
		OCConfirm,
		OCError,
		OCDisplayUser,
		OCEditUser;
		
	}

}
