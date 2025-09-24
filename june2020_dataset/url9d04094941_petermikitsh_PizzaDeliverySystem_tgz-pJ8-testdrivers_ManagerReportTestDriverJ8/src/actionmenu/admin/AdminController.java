package actionmenu.admin;

import kitcheneditor.KitchenEditorController;
import kitcheneditor.KitchenEditorView;
import kitcheneditor.KitchenEditorViewGUI;
import main.PDSViewManager;
import main.PizzaDeliverySystem;
import managerreport.ManagerReportController;
import managerreport.ManagerReportView;
import managerreport.ManagerReportViewCL;
import managerreport.ManagerReportViewGUI;
import menueditor.MenuEditorController;
import menueditor.MenuEditorView;
import menueditor.MenuEditorViewGUI;
import pastorders.PastOrdersController;
import pastorders.PastOrdersView;
import pastorders.PastOrdersViewGUI;
import usereditor.UserEditorController;
import usereditor.UserEditorView;
import usereditor.UserEditorViewGUI;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import actionmenu.admin.AdminView.AdminInChan;
import actionmenu.admin.AdminView.AdminOutChan;

/**
 * The logic controller for the admin action menu.
 *  Gives options to go to the following components.
 *   - View Past Orders
 *   - Edit User
 *   - Manage Kitchen
 *   - Manager Reports
 * 
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class AdminController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum AdminControllerState implements ControllerState {
		
		CSWaitingForSelection;
		
	}
	
	/**
	 * The default constructor.
	 */
	public AdminController() {}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying customer list state.
		this.active = true;
		gotoWaitingForSelection();
		if ( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (AdminViewGUI)view );
		} else {
			//((AdminViewCL)view).getUserInput();
		}
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch( (AdminControllerState)currentState ) {
		
		case CSWaitingForSelection:
			handleWaitingForSelection( message, (AdminInChan)channel );
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
										  	   AdminInChan channel ) {
		
		// The channel method should be either list selection or 
		//  menu option.
		switch( channel ) {
		
		case ICMenuOption: 
			
			// Check the message against the possible commands.
			if( message.equalsIgnoreCase( AdminView.REPORTS_KEY ) ) {
				
				// Create and pass control to a new ManagerReportsController
				// and View.
				ManagerReportController cont = new ManagerReportController();
				ManagerReportView view  = PizzaDeliverySystem.RUN_WITH_GUI ?
						new ManagerReportViewGUI() :
						new ManagerReportViewCL();
						
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( AdminView.PAST_ORDERS_KEY ) ) {
				
				// Create and pass control to a new PastOrdersController
				// and View.
				PastOrdersController cont = new PastOrdersController();
				PastOrdersView view  = PizzaDeliverySystem.RUN_WITH_GUI ?
						new PastOrdersViewGUI() : null; 
						
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( AdminView.EDIT_USER_KEY ) ) {
				
				// Create and pass control to a new UserEditorController
				// and View.
				UserEditorController cont = new UserEditorController();
				UserEditorView view  = PizzaDeliverySystem.RUN_WITH_GUI ?
						new UserEditorViewGUI() : null;
						
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if ( message.equalsIgnoreCase( AdminView.EDIT_MENU_KEY) ) {
				
				MenuEditorController cont = new MenuEditorController();
				MenuEditorView view  = PizzaDeliverySystem.RUN_WITH_GUI ?
						new MenuEditorViewGUI() : null;
				
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( AdminView.MANAGE_KEY ) ) {
				
				// Create and pass control to a new KitchenEditorController
				// and View.
				KitchenEditorController cont = new KitchenEditorController();
				KitchenEditorView view  = PizzaDeliverySystem.RUN_WITH_GUI ?
						new KitchenEditorViewGUI() : null;	
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForSelection();
				
			} else if( message.equalsIgnoreCase( AdminView.BACK_KEY ) ) {
				// End control by this controller and return to the root 
				// controller.
				this.active = false;
				
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
				
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
		
		this.currentState = AdminControllerState.CSWaitingForSelection;
		
		// Print the title of this menu.
		view.displayString( "Administrator Menu", AdminOutChan.OCTitle );
		
		// Create the menu option input.
		view.setChannelEnabled( AdminInChan.ICMenuOption, true );
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, AdminOutChan.OCError );
		
	}

}
