package pastorders;

import java.util.ArrayList;
import java.util.Collections;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Order;
import pastorders.PastOrdersView.PastOrdersInChan;
import pastorders.PastOrdersView.PastOrdersOutChan;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the Past orders module.
 * In charge of: 
 * 	- displaying and supporting changes to the list of orders.
 * Specific functions:
 *  - displaying the list of orders
 *  - displaying specfic order details
 *  - modifying and deleting orders
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class PastOrdersController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum PastOrdersState implements ControllerState {
		
		CSDisplayingOrderList;
		
	}
	
	/**
	 * The cached list of orders, sorted by name.
	 */
	private ArrayList<Order> _orderCache;
	
	/**
	 * The default constructor.
	 */
	public PastOrdersController() {
		
		// Update the cache.
		updateCache();
		
	}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying order list state.
		this.active = true;
		gotoDisplayingOrderList();
		
		if ( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (PastOrdersViewGUI)view );
		} else {
			//((PastOrdersViewCL)view).getUserInput();
		}	
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the Past state, and call the appropriate handler method.
		switch( (PastOrdersState)currentState ) {
		
		case CSDisplayingOrderList:
			handleDisplayingOrderList( message, 
										  (PastOrdersInChan)channel );
			break;

		}
		
	}
	
	/**
	 * Handles input in the DisplayingOrderList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingOrderList( String message, 
										  	   PastOrdersInChan channel ) {
		
		// Update the cache and reset the current order.
		updateCache();
		
		// Check the input channel.
		switch( channel ) {
		
		case ICBack:
			if( message.equalsIgnoreCase( PastOrdersView.BACK_KEY ) ) {
				// Go back to the first state.
				this.active = false;
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
				

			} else {
				handleInputError("The input was invalid. Please try again.");
			}
			break;
			
		case ICRefresh:
			if( message.equalsIgnoreCase( PastOrdersView.REFRESH_KEY ) ) {

				// Reload this state.
				gotoDisplayingOrderList();

			} else {
				handleInputError("The input was invalid. Please try again.");
			}
			break;
		
		case ICListDetails:

			// The message should be a list index.  Get the list index
			// and delete/modify the food item from the order at that index.
			int index = -1;
			try { 
				index = Integer.parseInt( message );
				if( index < 0 || index >= _orderCache.size() ) {
					handleInputError("Index out of range. Please try again.");
				}
			} catch( NumberFormatException e ) {
				handleInputError("The input was invalid. Please try again.");
			}

			if( index >= 0 && index < _orderCache.size() ) {

				// Store this order and display the order.
				view.displayObject( _orderCache.get(index), PastOrdersOutChan.OCDisplayOrder );

			}

			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}

	/**
	 * Moves the controller into the DisplayingCustomerList state.
	 */
	private void gotoDisplayingOrderList() {
		
		this.currentState = PastOrdersState.CSDisplayingOrderList;
		
		// Print the customer list.
		view.displayList( _orderCache, PastOrdersOutChan.OCList );
		
		// Show some instructions.
		view.displayString( "Select an order to view its details.", 
								PastOrdersOutChan.OCInstructions );
		view.setChannelEnabled( PastOrdersInChan.ICListDetails, true );
		view.setChannelEnabled( PastOrdersInChan.ICRefresh, true );
		view.setChannelEnabled( PastOrdersInChan.ICBack, true );
		
		
	}

	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, PastOrdersOutChan.OCError );
		
	}
	
	/**
	 * Updates the local store of orders.
	 */
	private void updateCache() {
		
		_orderCache = Order.getDb().list();
		Collections.sort( _orderCache );
		
	}

}
