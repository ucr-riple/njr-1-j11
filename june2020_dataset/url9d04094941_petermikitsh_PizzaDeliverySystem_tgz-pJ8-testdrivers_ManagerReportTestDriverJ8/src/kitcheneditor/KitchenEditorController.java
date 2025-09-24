package kitcheneditor;

import kitcheneditor.KitchenEditorView.KitchenEditorInChan;
import kitcheneditor.KitchenEditorView.KitchenEditorOutChan;
import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Configuration;
import ninja.Kitchen;
import ninja.SystemTime;
import ninja.SystemTime.SystemTimeScale;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import actionmenu.admin.AdminView;

/**
 * The logic controller for the kitchen editor.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class KitchenEditorController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum KitchenEditorControllerState implements ControllerState {
		
		CSDisplayingKitchenData;
		
	}
	
	/**
	 * The default constructor.
	 */
	public KitchenEditorController() {}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying customer list state.
		this.active = true;
		gotoDisplayingKitchenData();
		if ( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (KitchenEditorViewGUI)view );
		} else {
			// ((KitchenEditor)view).getUserInput();
		}
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch( (KitchenEditorControllerState)currentState ) {
		
		case CSDisplayingKitchenData:
			handleDisplayingKitchenData( message, (KitchenEditorInChan)channel );
			break;
			
		}
		
	}
	
	/**
	 * Handles input in the DisplayingKitchenData state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingKitchenData( String message, 
											KitchenEditorInChan channel ) {
		
		// The channel method should be either list selection or 
		//  menu option.
		switch( channel ) {
		
		case ICBack:
			
			if( message.equalsIgnoreCase( AdminView.BACK_KEY ) ) {
				// End control by this controller and return to the root 
				// controller.
				this.active = false;
				
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
			}
			break;
			
		case ICTimeScale:
			// Parse the message into a timescale value.
			SystemTimeScale timeScale = SystemTimeScale.parseTimeScale( message );
			if( timeScale != null ) {
				SystemTime.setTimeScale( timeScale );
			}
			break;
			
		case ICNumCooks:
		case ICNumOvens:
		case ICNumDrivers:
			
			// Parse the message to an integer.
			int number = 0;
			try {
				number = Integer.parseInt( message );
			} catch( NumberFormatException exc ) {}
			
			// Make sure the number is positive.
			if( number > 0 ) {
				
				// Store the number in the config databse.
				String configKey = null;
				if( channel == KitchenEditorInChan.ICNumCooks ) {
					configKey = Kitchen.COOK_CONFIG_NAME;
				} else if( channel == KitchenEditorInChan.ICNumOvens ) {
					configKey = Kitchen.OVEN_CONFIG_NAME;
				} else if( channel == KitchenEditorInChan.ICNumDrivers ) {
					configKey = Kitchen.DRIVER_CONFIG_NAME;
				}
				
				Configuration.getDb().add( new Configuration( configKey, message ) );
				
			} else {
				handleInputError( "Staff numbers must be positive integers." );
			}
			
			break;
			
		case ICTax:
			// Parse the message to an integer.
			double tax = -1;
			try {
				tax = Double.parseDouble( message );
			} catch( NumberFormatException exc ) {}

			// Make sure the number is nonnegative.
			if( tax >= 0 ) {

				// Store the number in the config databse.
				Configuration.getDb().add( new Configuration( Kitchen.TAX_CONFIG_NAME, message ) );

			} else {
				handleInputError( "Tax must be a decimal value and cannot be negative." );
			} 
			break;
			
		case ICRefresh:
			// Refresh the editability fields for the kitchen staff numbers.
			refreshKitchenStaffFields();
			break;
			
		
		}

	}
	
	/**
	 * Moves the controller into the WaitingForSelection state.
	 */
	private void gotoDisplayingKitchenData() {
		
		this.currentState = KitchenEditorControllerState.CSDisplayingKitchenData;
		
		// Display the timescale.
		String timeScale = Configuration.getDb().get( SystemTime.TIMESCALE_CONFIG_NAME.hashCode() ).getValue();
		view.displayString( timeScale, KitchenEditorOutChan.OCTimeScale );
		
		// Display the kitchen config data.
		String numOvens = Configuration.getDb().get( Kitchen.OVEN_CONFIG_NAME.hashCode() ).getValue();
		String numDrivers = Configuration.getDb().get( Kitchen.DRIVER_CONFIG_NAME.hashCode() ).getValue();
		String numCooks = Configuration.getDb().get( Kitchen.COOK_CONFIG_NAME.hashCode() ).getValue();
		String tax = Configuration.getDb().get( Kitchen.TAX_CONFIG_NAME.hashCode() ).getValue();

		view.displayString( numOvens, KitchenEditorOutChan.OCNumOvens );
		view.displayString( numCooks, KitchenEditorOutChan.OCNumCooks);
		view.displayString( numDrivers, KitchenEditorOutChan.OCNumDrivers );
		view.displayString( tax, KitchenEditorOutChan.OCTax );
		
		// Enabled the inputs.
		view.setChannelEnabled( KitchenEditorInChan.ICTax, true );
		refreshKitchenStaffFields();
		
	}
	
	/**
	 * Enables or disables the kitchen staff input based on whether or not
	 *  there are any orders in the kitchen.
	 */
	private void refreshKitchenStaffFields() {
		
		// Check if there is anything in the kitchen.
		boolean enabled = Kitchen.getOrders().isEmpty();
		view.setChannelEnabled( KitchenEditorInChan.ICNumCooks, enabled );
		view.setChannelEnabled( KitchenEditorInChan.ICNumOvens, enabled );
		view.setChannelEnabled( KitchenEditorInChan.ICNumDrivers, enabled );
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, KitchenEditorOutChan.OCError );
		
	}

}
