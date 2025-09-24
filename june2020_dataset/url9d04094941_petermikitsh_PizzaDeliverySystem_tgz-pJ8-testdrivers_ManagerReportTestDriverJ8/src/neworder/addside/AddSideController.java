package neworder.addside;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.FoodItem;
import model.SideFoodItem;
import neworder.addside.AddSideView.AddSideInChan;
import neworder.addside.AddSideView.AddSideOutChan;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the Add Side module.
 * 
 * @author 	Casey Klimkowsky	cek3403@g.rit.edu
 */
public class AddSideController extends GeneralController {

	/**
	 * The states of this controller.
	 */
	private enum AddSideControllerState implements ControllerState {
		
		CSWaitingForQuanitities
		
	}
	
	/**
	 * The list of quantities according to the sides.
	 */
	private Map<SideFoodItem, Integer> _quantityMap;
	
	/**
	 * The default constructor.
	 * 
	 * @param quantityMap An assignment of sides to quantities.
	 */
	public AddSideController( Map<SideFoodItem, Integer> quantityMap ) {
		
		_quantityMap = quantityMap;
		
	}
	
	/**
	 * @see viewcontroller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {

		this.active = true;

		gotoWaitingForSideChoice();

		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (AddSideViewGUI)view );
		} else {
			//((AddSideViewCL)view).getUserInput();
		}

	}

	/**
	 * @see viewcontroller.GeneralController#respondToInput(java.lang.String, viewcontroller.GeneralView.InputChannel)
	 */	
	public void respondToInput(String message, InputChannel channel) {

		// Check for a back button.
		if( channel == AddSideInChan.ICBack && 
				message.equalsIgnoreCase( AddSideView.BACK_KEY ) ) {

			// Set active to false.  In the GUI version, pop the current
			// view.
			this.active = false;

			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}

		}

		// Check the current state, and call the appropriate handler method.
		switch ((AddSideControllerState) currentState) {
		
		case CSWaitingForQuanitities:
			handleWaitingForSideChoice(message, (AddSideInChan) channel);
			break;
			
		}
	}
	
	/**
	 * Moves the controller to the WaitingForSideChoice state.
	 * 
	 * @param message  the String message
	 * @param channel  the channel from which the input was received
	 */
	private void gotoWaitingForSideChoice() {
		
		this.currentState = AddSideControllerState.CSWaitingForQuanitities;
		view.displayString( "Enter numerical quantities for each of the available sides.", 
								AddSideOutChan.OCInstructions);

		// Obtain and sort the list of available food items.
		ArrayList<FoodItem> itemList = SideFoodItem.getDb().list();
		Collections.sort( itemList );
		
		// Now create a list of quantities that is sorted according to the sorted side list.
		ArrayList<Integer> quantities = new ArrayList<Integer>();
		for( int i = 0; i < itemList.size(); i++ ) {
			quantities.add( _quantityMap.get( (SideFoodItem)itemList.get(i) ) );
		}
		
		// Send that list to the controller.
		view.displayList( quantities, AddSideOutChan.OCQuantities );
		
	}
	
	/**
	 * Handles input in the WaitingForSideChoice state.
	 * 
	 * @param message  the String message
	 * @param channel  the channel from which the input was received
	 */
	private void handleWaitingForSideChoice(String message, AddSideInChan channel) {
		
		switch ((AddSideInChan) channel) {
		
		case ICQuantities:
			
			// First get the sorted list of sides.
			ArrayList<FoodItem> itemList = SideFoodItem.getDb().list();
			Collections.sort( itemList );
			
			// Split the string into a list of integers. Set each integer
			// as the quantity of the corresponding side food item.
			String[] quanStrings = message.split( " " );
			for( int i = 0; i < quanStrings.length; i++ ) {
				SideFoodItem side = (SideFoodItem)itemList.get(i);
				_quantityMap.put( side, Integer.parseInt( quanStrings[i] ) );
			}
			
			// Done here; return to the previous view.
			active = false;
			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}
			
			break;
			
		}
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, AddSideOutChan.OCError );

	}
	
}
