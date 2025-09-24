package actionmenu.admin;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Admin module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class AdminView extends GeneralView {
	
	/**
	 * The menu selection key for viewing past orders.
	 */
	public static final String PAST_ORDERS_KEY = "PAS";
	
	/**
	 * The menu selection key for editing users.
	 */
	public static final String EDIT_USER_KEY = "USR";
	
	/**
	 * The menu selection key for editing the menu.
	 */
	public static final String EDIT_MENU_KEY = "MNU";
	
	/**
	 * The menu selection key for managing the kitchen.
	 */
	public static final String MANAGE_KEY = "MOD";
	
	/**
	 * The menu selection key for viewing manager reports.
	 */
	public static final String REPORTS_KEY = "REP";
	
	/**
	 * The menu selection key for going back to the root menu.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum AdminInChan implements InputChannel {
		
		ICMenuOption,
		ICDefault;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum AdminOutChan implements OutputChannel {
		
		OCUser,
		OCTime,
		OCError,
		OCTitle;
		
	}

}
