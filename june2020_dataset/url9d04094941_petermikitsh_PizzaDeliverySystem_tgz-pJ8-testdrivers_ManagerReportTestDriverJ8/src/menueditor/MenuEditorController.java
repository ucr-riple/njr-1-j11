package menueditor;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import menueditor.MenuEditorView.MenuEditorInChan;
import menueditor.MenuEditorView.MenuEditorOutChan;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the menu editor module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class MenuEditorController extends GeneralController {

	/**
	 * The states of this controller.
	 */
	private enum MenuEditorControllerState implements ControllerState {
		
		CSWaitingForSelection;
		
	}
	
	/**
	 * The default constructor.
	 */
	public MenuEditorController() {}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		this.active = true;
		gotoWaitingForSelection();
		if ( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (MenuEditorViewGUI) view );
		}
		
	}

	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch( (MenuEditorControllerState) currentState ) {
		
		case CSWaitingForSelection:
			handleWaitingForSelection( message, (MenuEditorInChan) channel );
			break;
		
		}
		
	}

	/**
	 * Moves the controller into the WaitingForSelection state.
	 */
	private void gotoWaitingForSelection() {
		
		this.currentState = MenuEditorControllerState.CSWaitingForSelection;
		
		// Create the menu option input.
		view.setChannelEnabled( MenuEditorInChan.ICMenuOption, true );
		
	}
	
	/**
	 * Handles input in the WaitingForSelection state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForSelection( String message, 
										  	   MenuEditorInChan channel ) {
		
		switch( channel ) {
		
		case ICBack:
			if( message.equalsIgnoreCase( MenuEditorView.BACK_KEY ) ) {
				
				this.active = false;
				
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
				
			}
			else {
				handleInputError("The input was invalid. Please try again.");
			}
			break;
			
		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, MenuEditorOutChan.OCError );
		
	}
	
}
