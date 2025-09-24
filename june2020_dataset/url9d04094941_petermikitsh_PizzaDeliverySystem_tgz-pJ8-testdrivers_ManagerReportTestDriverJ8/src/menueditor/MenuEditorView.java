package menueditor;

import viewcontroller.GeneralView;

/**
 * The abstract view for the menu editor module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public abstract class MenuEditorView extends GeneralView {
	
	/**
	 * The menu selection key for going back.
	 */
	public static final String BACK_KEY = "BCK";
	
	/**
	 * The input channels for this view.
	 */
	public enum MenuEditorInChan implements InputChannel {
		
		ICMenuOption,
		ICBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum MenuEditorOutChan implements OutputChannel {
		
		OCInstructions,
		OCError;
		
	}

}
