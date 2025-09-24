package kitcheneditor;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Admin module.
 * This will have two subclasses, one for each type of view: command-line, and
 *  GUI.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public abstract class KitchenEditorView extends GeneralView {
	
	/**
	 * The menu selection key for refreshing the view.
	 */
	public static final String REFRESH_KEY = "REF";
	
	/**
	 * The menu selection key for going back.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum KitchenEditorInChan implements InputChannel {
		
		ICTimeScale,
		ICNumCooks,
		ICNumOvens,
		ICNumDrivers,
		ICTax,
		ICRefresh,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum KitchenEditorOutChan implements OutputChannel {
		
		OCTimeScale,
		OCNumCooks,
		OCNumOvens,
		OCNumDrivers,
		OCTax,
		OCError,
		OCInstructions;
		
	}

}
