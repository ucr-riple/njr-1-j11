package actionmenu;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import neworder.NewOrderController;
import neworder.NewOrderView;
import neworder.NewOrderViewGUI;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import actionmenu.RootView.RootInChan;
import actionmenu.RootView.RootOutChan;
import actionmenu.admin.AdminController;
import actionmenu.admin.AdminView;
import actionmenu.admin.AdminViewGUI;
import currentorders.CurrentOrdersController;
import currentorders.CurrentOrdersView;
import currentorders.CurrentOrdersViewGUI;
import customereditor.CustomerEditorController;
import customereditor.CustomerEditorView;
import customereditor.CustomerEditorViewCL;
import customereditor.CustomerEditorViewGUI;

/**
 * The logic controller for the root action menu.
 * In charge of: 
 * 	- Giving options to go to the following components:
 *    - view customer database
 *    - view administrative options
 *    - view current orders
 *    - new orders
 * 
 * @author 	Peter Mikitsh	pam3961@rit.edu
 */
public class RootController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum RootControllerState implements ControllerState {
		
		CSWaitingForSelection;
		
	}
	
	/**
	 * The default constructor.
	 */
	public RootController() {}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying customer list state.
		this.active = true;
		gotoWaitingForSelection();
		
		// Initialize the view.
		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (RootViewGUI)view );
		} else {
			//((RootViewCL)view).getUserInput();
		}
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check for log out button.
		if( channel == RootInChan.ICLogOut &&
				message.equalsIgnoreCase( RootView.LOG_OUT_KEY)) {
			
			// Set active to false.  In the GUI version,
			// pop the current view.
			this.active = false;
			
			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}
			
		}
		
		// Check the current state, and call the appropriate handler method.
		switch( (RootControllerState)currentState ) {
		
		case CSWaitingForSelection:
			handleWaitingForSelection( message, 
										  (RootInChan)channel );
			break;
			
		}
		
	}

	/**
	 * Handles input in the WaitingForSelection state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForSelection( String message, 
										  	   RootInChan channel ) {
		
		// The channel method should be either list selection or 
		//  menu option.
		switch( channel ) {
		
		case ICMenuOption: 
			
			// Check the message against the commands.
			if( message.equalsIgnoreCase( RootView.NEW_ORDER_KEY ) ) {
				
				// Create and pass control to a new NewOrderController
				// and View.
				NewOrderController cont = new NewOrderController();
				NewOrderView view = PizzaDeliverySystem.RUN_WITH_GUI ?
										new NewOrderViewGUI() : null;
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
				
			} else if( message.equalsIgnoreCase( RootView.VIEW_CUSTOMER_KEY ) ) {
				
				// Create and pass control to a new CustomerEditorController
				// and View.
				CustomerEditorController cont = new CustomerEditorController();
				CustomerEditorView view = PizzaDeliverySystem.RUN_WITH_GUI ?
											new CustomerEditorViewGUI() :
											new CustomerEditorViewCL();
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( RootView.VIEW_ORDERS_KEY ) ) {
				
				// Create and pass control to a new CurrentOrdersController
				// and View.
				CurrentOrdersController cont = new CurrentOrdersController();
				CurrentOrdersView view = PizzaDeliverySystem.RUN_WITH_GUI ?
							new CurrentOrdersViewGUI() : null;
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( RootView.ADMIN_KEY ) ) {
				AdminController cont = new AdminController();
				AdminView view = PizzaDeliverySystem.RUN_WITH_GUI ?
						new AdminViewGUI() : null;
						
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( RootView.LOG_OUT_KEY ) ) {
				
				// End control by this controller and return to the auth controller.
				this.active = false;
				
				
			} else {
				handleInputError("The input was invalid. Please try again.");
			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}


	
	
	/**
	 * Moves the controller into the WaitingForSelection state.
	 */
	private void gotoWaitingForSelection() {
		
		this.currentState = RootControllerState.CSWaitingForSelection;
		
		// Print the title of this menu.
		view.displayString( "Main Menu", RootOutChan.OCTitle );
		
		// Create the menu option input.
		view.setChannelEnabled( RootInChan.ICMenuOption, true );
		
	}
	
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, RootOutChan.OCError );
		
	}
	
}
