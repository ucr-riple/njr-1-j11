package currentorders;

import java.util.ArrayList;
import java.util.Collections;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Order;
import model.Order.OrderStatus;
import neworder.NewOrderController;
import neworder.NewOrderView;
import neworder.NewOrderViewGUI;
import ninja.Kitchen;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import currentorders.CurrentOrdersView.CurrentOrdersInChan;
import currentorders.CurrentOrdersView.CurrentOrdersOutChan;

/**
 * The logic controller for the current orders module.
 * In charge of: 
 * 	- displaying and supporting changes to the list of orders.
 * Specific functions:
 *  - displaying the list of orders
 *  - displaying specfic order details
 *  - modifying and deleting orders
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class CurrentOrdersController extends GeneralController {

	/**
	 * The states of this controller.
	 */
	private enum CurrentOrdersState implements ControllerState {

		CSDisplayingOrderList;

	}

	/**
	 * The cached list of orders, sorted by name.
	 */
	private ArrayList<Order> _orderCache;

	/**
	 * The order object that is currently being displayed on.
	 */
	private Order _currentOrder;

	/**
	 * The default constructor.
	 */
	public CurrentOrdersController() {

		this._currentOrder = null;
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
			PDSViewManager.pushView( (CurrentOrdersViewGUI) view );
		} else {
			//((CurrentOrdersViewCL) view).getUserInput();
		}

	}

	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {

		// Check the current state, and call the appropriate handler method.
		switch( (CurrentOrdersState)currentState ) {

		case CSDisplayingOrderList:
			handleDisplayingOrderList( message, 
					(CurrentOrdersInChan)channel );
			break;

		}

	}

	/**
	 * Determines whether the given order can be
	 * modified and/or deleted.
	 * 
	 * @param order the Order to check
	 */
	public void canModifyOrDeleteOrder( Order order ) {
		
		if( null != order ) {
			
			// Check if the order can be modified.
			if( order.getStatus() == 
				OrderStatus.AwaitingPreparation ) {
				view.setChannelEnabled( CurrentOrdersInChan.ICListModify, true );
			}
			else {
				view.setChannelEnabled( CurrentOrdersInChan.ICListModify, false );
			}
		
			// Check if the order can be deleted.
			if( order.getStatus() != OrderStatus.AwaitingDelivery &&
					order.getStatus() != OrderStatus.EnRoute &&
					order.getStatus() != OrderStatus.Delivered ) {
				view.setChannelEnabled( CurrentOrdersInChan.ICListDelete, true );
			}
			else {
				view.setChannelEnabled( CurrentOrdersInChan.ICListDelete, false );
			}
			
		}
		
	}
	
	/**
	 * Handles input in the DisplayingOrderList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingOrderList( String message, 
			CurrentOrdersInChan channel ) {

		// Clear out the current customers storers.
		updateCache();

		// Check the input channel.
		switch( channel ) {

		case ICListModify:
		case ICListDelete:
			canModifyOrDeleteOrder( _currentOrder );
			break;
			
		case ICBack:
			if( message.equalsIgnoreCase( CurrentOrdersView.BACK_KEY ) ) {
				// Go back to the first state.
				this.active = false;
				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}
			} 
			break;

		case ICRefresh:
			if( message.equalsIgnoreCase( CurrentOrdersView.REFRESH_KEY ) ) {
				// Reload this state.
				canModifyOrDeleteOrder( _currentOrder );
				gotoDisplayingOrderList();

			}
			break;

		case ICListDetails:

			// The message should be a list index.  Get the list index
			// and delete/modify the food item from the order at that index.
			int index = -1;
			try { 
				index = Integer.parseInt( message );
				if( index < 0 || 
						index >= Kitchen.getOrders().size() ) {
					handleInputError("The input was invalid. Please try again.");
				}
			} catch( NumberFormatException e ) {
				handleInputError("The input was invalid. Please try again.");
			}

			if( index >= 0 && index < Kitchen.getOrders().size() ) {

				_currentOrder = _orderCache.get( index );

				if( channel == CurrentOrdersInChan.ICListDetails ) {

					canModifyOrDeleteOrder( _currentOrder );
					
					// Show the order.
					view.displayObject( _currentOrder, CurrentOrdersOutChan.OCDisplayOrder );
					gotoDisplayingOrderList();

				}

			}

			break;

		case ICMenuOption:

			// Check for the modify or delete keys.
			if( message.equalsIgnoreCase( CurrentOrdersView.DELETE_KEY ) ) {

				// Check if the order can be deleted.
				if( _currentOrder.getStatus() != OrderStatus.AwaitingDelivery &&
						_currentOrder.getStatus() != OrderStatus.EnRoute &&
						_currentOrder.getStatus() != OrderStatus.Delivered ) {

					// Remove the order from the kitchen.
					Kitchen.removeOrder( _currentOrder );
					_currentOrder = null;

					// Show the order. (It that ok if this is null, the view will handle that.)
					view.displayObject( _currentOrder, CurrentOrdersOutChan.OCDisplayOrder );

				} else {

					// Report an error.
					view.displayString( "Orders cannot be deleted once they have" +
							" passed the \"In The Kitchen\" stage." , 
							CurrentOrdersOutChan.OCError );

				}

				// Reload this state.
				gotoDisplayingOrderList();

			} else if( message.equalsIgnoreCase( CurrentOrdersView.MODIFY_KEY ) ) {

				// Check if the order can be modify.
				if( _currentOrder.getStatus() == OrderStatus.AwaitingPreparation ) {

					// Remove the order from the kitchen.
					Kitchen.removeOrder( _currentOrder );

					// Now create a New Order controller with the given order.
					NewOrderController cont = new NewOrderController( _currentOrder );
					NewOrderView view = PizzaDeliverySystem.RUN_WITH_GUI ?
							new NewOrderViewGUI() : null;
							cont.setView(view);
							view.setController(cont);
							cont.enterInitialState();

							_currentOrder = null;

							// Show the order. (It is ok that this is null, the view will handle that.)
							view.displayObject( _currentOrder, CurrentOrdersOutChan.OCDisplayOrder );

				} else {

					// Report an error.
					view.displayString( "Orders cannot be modified once they have" +
							" passed the \"Awaiting Preparation\" stage." , 
							CurrentOrdersOutChan.OCError );

				}

				// Reload this state.
				gotoDisplayingOrderList();

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

		this.currentState = CurrentOrdersState.CSDisplayingOrderList;

		// Print the order list.
		view.displayList( _orderCache, CurrentOrdersOutChan.OCList );

		// Show some instructions.
		//		view.displayString( "Modify or delete an order, or select one to view its details.", 
		//								CurrentOrdersOutChan.OCInstructions );
		view.setChannelEnabled( CurrentOrdersInChan.ICListDetails, true );
		view.setChannelEnabled( CurrentOrdersInChan.ICBackRefresh, true );

	}

	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {

		view.displayString( message, CurrentOrdersOutChan.OCError );

	}

	/**
	 * Updates the local store of orders.
	 */
	private void updateCache() {

		_orderCache = new ArrayList<Order>( Kitchen.getOrders() );
		Collections.sort( _orderCache );

	}

}
