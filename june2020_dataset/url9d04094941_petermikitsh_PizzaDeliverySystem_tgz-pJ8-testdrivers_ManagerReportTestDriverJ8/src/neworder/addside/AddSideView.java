package neworder.addside;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Add Side module.
 * 
 * @author 	Casey Klimkowsky	cek3403@g.rit.edu
 */
public abstract class AddSideView extends GeneralView {
	
	/**
	 * The menu selection key for going back.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum AddSideInChan implements InputChannel {
		
		ICQuantities,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum AddSideOutChan implements OutputChannel {
		
		OCInstructions,
		OCQuantities,
		OCError;
		
	}
	
}
