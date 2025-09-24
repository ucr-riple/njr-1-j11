package neworder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Address;
import model.Customer;
import model.FoodItem;
import model.Order;
import model.PizzaFoodItem;
import model.SideFoodItem;
import neworder.NewOrderView.NewOrderInChan;
import neworder.NewOrderView.NewOrderOutChan;
import neworder.addpizza.AddPizzaController;
import neworder.addpizza.AddPizzaView;
import neworder.addpizza.AddPizzaViewCL;
import neworder.addpizza.AddPizzaViewGUI;
import neworder.addside.AddSideController;
import neworder.addside.AddSideView;
import neworder.addside.AddSideViewGUI;
import ninja.Kitchen;
import ninja.Time;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the customer editor module.
 * In charge of: 
 * 	- displaying and supporting changes to the list of customers.
 * Specific functions:
 *  - adding new customers,
 *  - editing the information of existing customers,
 *  - searching for customers by phone number,
 *  - deleting existing customers.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class NewOrderController extends GeneralController {

	/**
	 * The states of this controller.
	 */
	private enum NewOrderContState implements ControllerState {

		CSWaitingForPhoneNumber,
		CSWaitingForCustInfo,
		CSDisplayingOrder,
		CSDisplayingOrderConfirmation;

	}

	/**
	 * The order that is being manipulated.
	 */
	private Order _currentOrder;
	
	/**
	 * The current map of sidefood items to quantities.  
	 */
	private Map<SideFoodItem, Integer> _quantityMap;

	/**
	 * Indicates that an order was passed into this controller to be modified.
	 */
	private boolean _modifying;

	/**
	 * The default constructor.
	 */
	public NewOrderController() {

		// Create with a new order.
		this( new Order( new ArrayList<FoodItem>(), new Customer() ) );
		_modifying = false;

	}

	/**
	 * Constructor that takes an order.
	 */
	public NewOrderController( Order order ) {
		
		_currentOrder = order;
		_modifying = true;
		
		// Recover the quantity map.Recover the side quantity map: start with a sorted side
		// food item list.
		ArrayList<FoodItem> itemList = SideFoodItem.getDb().list();
		Collections.sort( itemList );
		
		// Create the map and initialize all of the values to zero.
		_quantityMap = new HashMap<SideFoodItem, Integer>();
		for( FoodItem sfi : SideFoodItem.getDb().list() ) {
			_quantityMap.put( (SideFoodItem)sfi, 0 );
		}
		
		// Go through the SFIs in the given list and increment the corresponding
		// element of the map.
		ArrayList<PizzaFoodItem> pizzaFoodItems = new ArrayList<PizzaFoodItem>();
		for( FoodItem item : _currentOrder.getFoodItems() ) {
			if( item instanceof SideFoodItem ) {
				int value = _quantityMap.get( (SideFoodItem)item );
				_quantityMap.put( (SideFoodItem)item, value + 1 );
			} else {
				pizzaFoodItems.add( (PizzaFoodItem)item );
			}
		}
		
		// Now clear the order's list and add in back in all of the pizzas.
		_currentOrder.getFoodItems().clear();
		for( PizzaFoodItem item : pizzaFoodItems ) {
			_currentOrder.addFoodItem( item );
		}
		
	}

	/**
	 * @see viewcontroller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {

		// The initial state is the displaying customer list state.
		this.active = true;
		
		// The state that we go to depends on whether or not we're modifying an order.
		if( !_modifying ) {
			gotoWaitingForPhoneNumber();
		} else {
			gotoDisplayingOrder();
		}

		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (NewOrderViewGUI)view );
		} else {
			//((NewOrderViewCL)view).getUserInput();
		}

	}

	/**
	 * Tells this controller to skip the customer asking part and go right
	 *  to the order display.
	 */
	public void enterOrderTakingState() {

		this.active = true;
		gotoDisplayingOrder();

	}

	/**
	 * @see viewcontroller.GeneralController#respondToInput(java.lang.String, viewcontroller.GeneralView.InputChannel)
	 */	
	public void respondToInput(String message, InputChannel channel) {

		// Check the current state, and call the appropriate handler method.
		switch( (NewOrderContState)currentState ) {

		case CSWaitingForPhoneNumber:
			handleWaitingForPhoneNumber( message, 
					(NewOrderInChan)channel );
			break;

		case CSWaitingForCustInfo:
			handleWaitingForCustInfo( message, 
					(NewOrderInChan)channel );
			break;

		case CSDisplayingOrder:
			handleDisplayingOrder( message, 
					(NewOrderInChan)channel );
			break;

		case CSDisplayingOrderConfirmation:
			handleDisplayingOrderConfirmation( message, 
					(NewOrderInChan)channel );
			break;

		}

	}

	/**
	 * Handles input in the WaitingForPhoneNumber state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForPhoneNumber( String message, 
			NewOrderInChan channel ) {

		// Only one channel to get input.
		switch( (NewOrderInChan)channel ) {

		case ICMenuOption:
			// Do nothing
			break;

		case ICCustomerPhone: 

			// Check the phone number input for validity.
			if( Customer.isValidPhoneNumber( message ) ) {

				// Check for the customer in the database.
				Customer foundCust = null;
				for( Customer cust : Customer.getDb().list() ) {
					if( cust.getPhoneNumber().equals( message ) ) {
						foundCust = cust;
						break;
					}
				}

				// Search for the customer in the database.
				if( foundCust != null ) {

					// Set this as the current customer for the order
					// and proceed to the displaying order state.
					_currentOrder.setCustomer( foundCust );
					gotoDisplayingOrder();

				} else {

					// If the customer is not in the database, go to the add
					// customer state.
					_currentOrder.getCustomer().setPhoneNumber( message );
					gotoWaitingForCustInfo();

				}

			} else {

				// Report an error.
				handleInputError("<html>Phone numbers must be numeric and 10 characters in length.</html>");

			}
			break;

		case ICBack:

			// Check for a back button.
			if( message.equalsIgnoreCase( NewOrderView.BACK_KEY ) ) {

				// Set active to false.  In the GUI version, pop the current
				// view.
				this.active = false;

				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}

			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}

	/**
	 * Handles input in the WaitingForPhoneNumber state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForCustInfo( String message, 
			NewOrderInChan channel ) {

		// Only one channel to get input.
		switch( (NewOrderInChan)channel ) {

		case ICMenuOption:
			// Do nothing
			break;

		case ICCustomerPhone:

			// Check if the string is a valid phone number.  If so, set
			// it as the new phone number.
			if( Customer.isValidPhoneNumber( message ) ) {

				_currentOrder.getCustomer().setPhoneNumber( message );

			} 
			break;

		case ICCustomerName:

			// Set the new string as the name.
			_currentOrder.getCustomer().setName( message );
			break;

		case ICCustomerAddress:

			// Check that the address is valid.
			Address addr = Address.getAddressForAlias( message);
			if( null == addr ) {

				// Do nothing. 
				
			} else {

				_currentOrder.getCustomer().setStreetAddress( addr );

				// If we got a valid address, the customer should
				// be done being detailed.  Add this new customer
				// to the database and proceed to the order construction
				// state.
				Customer.getDb().add( _currentOrder.getCustomer() );
				gotoDisplayingOrder();

			}
			break;

		case ICBack:

			// Check for a back button.
			if( message.equalsIgnoreCase( NewOrderView.BACK_KEY ) ) {

				// Go back to the customer phone number prompt.
				gotoWaitingForPhoneNumber();

			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}

	/**
	 * Handles input in the WaitingForPhoneNumber state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingOrder( String message, 
			NewOrderInChan channel ) {

		// Only one channel to get input.
		switch( (NewOrderInChan)channel ) {

		case ICMenuOption:

			// Check which button was pressed.
			if( message.equalsIgnoreCase( NewOrderView.ADD_PIZZA_KEY ) ) {

				// Get a new pizza from the AddPizzaController, then
				// add it to the order.

				AddPizzaController apCont = new AddPizzaController( _currentOrder.getFoodItems() );
				AddPizzaView apView = PizzaDeliverySystem.RUN_WITH_GUI ?
						new AddPizzaViewGUI() :
							new AddPizzaViewCL();

				apCont.setView( apView );
				apView.setController( apCont );
				_currentOrder.getFoodItems().addAll( apCont.getPizzas() );
	
				// Return to this state (so we update all the information).
				gotoDisplayingOrder();

			} else if(  message.equalsIgnoreCase( NewOrderView.ADD_SIDE_KEY) ) {

				// Get a new side from the AddSideController, then
				// add it to the order.
				AddSideController asCont = new AddSideController( _quantityMap );
				AddSideView asView = PizzaDeliverySystem.RUN_WITH_GUI ?
						new AddSideViewGUI() : null;
				asCont.setView( asView );
				asView.setController( asCont );
				asCont.enterInitialState();

				// Return to this state (so we update all the information).
				gotoDisplayingOrder();

			} else if( message.equalsIgnoreCase( NewOrderView.DONE_KEY) ) { 

				// Print an error message if the order is empty.
				if ( _currentOrder.getFoodItems().size() == 0 )
					handleInputError("Orders must contain at least one item.");
				else {
					
					// Update the fooditem list in the order.
					updateOrderFoodItemList();
					
					// Send the order to the kitchen.
					Kitchen.addOrder( _currentOrder );

					// Proceed to the order confirmation.
					gotoDisplayingOrderConfirmation();
				}

			} else if( message.equalsIgnoreCase( NewOrderView.REFRESH_KEY ) ) { 

				// Reload this state.
				gotoDisplayingOrder();

			} else {
				handleInputError("The input was invalid. Please try again.");
			}

			break;

		case ICConfirm:
			if( message.equalsIgnoreCase( NewOrderView.CANCEL_KEY) ) { 

				// Go back to the main prompt.
				this.active = false;
			}
			break;

		case ICListDelete:
		case ICListModify:

			// The message should be a list index.  Get the list index
			// and delete/modify the food item from the order at that index.
			int index = -1;
			try { 
				index = Integer.parseInt( message );
				if( index < 0 || 
						index >= _currentOrder.getFoodItems().size() ) {
					handleInputError("Index out of range. Please try again.");
				}
			} catch( NumberFormatException e ) {
				handleInputError("The input was invalid. Please try again.");
			}

			if( index >= 0 && index < _currentOrder.getFoodItems().size() ) {

				FoodItem selectedItem = _currentOrder.getFoodItems().get(index);

				if( channel == NewOrderInChan.ICListDelete ) {

					_currentOrder.removeFoodItem( selectedItem );

				} else if( channel == NewOrderInChan.ICListModify ) {

					// Create a new AddPizza or AddSide controller, telling
					// it that we're modifying the item.
					if( selectedItem instanceof PizzaFoodItem ) {
						
						AddPizzaController apCont;
						AddPizzaView apView;
						if( PizzaDeliverySystem.RUN_WITH_GUI ) {
							
							apCont =
								new AddPizzaController( _currentOrder.getFoodItems());
							apView = new AddPizzaViewGUI( (PizzaFoodItem)selectedItem );
						}
						else{
							apCont =
								new AddPizzaController( (PizzaFoodItem)selectedItem, true );
							apView = new AddPizzaViewCL();
						}
						apCont.setView( apView );
						apView.setController( apCont );
						_currentOrder.getFoodItems().addAll( apCont.getPizzas() );
						_currentOrder.removeFoodItem( selectedItem );

					} else {

						AddSideController asCont = new AddSideController( _quantityMap );
						AddSideView asView = PizzaDeliverySystem.RUN_WITH_GUI ?
								new AddSideViewGUI() : null;
						asCont.setView( asView );
						asView.setController( asCont );
						asCont.enterInitialState();

					}

				}

			}

			gotoDisplayingOrder();

			break;

		case ICBack:

			// Check for a back button.
			if( message.equalsIgnoreCase( NewOrderView.BACK_KEY ) ) {

				// Go back to the customer phone number prompt.
				gotoWaitingForCustInfo();

			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}

	/**
	 * Handles input in the WaitingForPhoneNumber state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingOrderConfirmation( String message, 
			NewOrderInChan channel ) {

		// Only one channel to get input.
		switch( (NewOrderInChan)channel ) {

		case ICMenuOption:

			// Check which button was pressed.
			if( message.equalsIgnoreCase( NewOrderView.DONE_KEY) ) {

				this.active = false;

				if( PizzaDeliverySystem.RUN_WITH_GUI) {
					PDSViewManager.popView();
				}

			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}
	
	/**
	 * Moves the controller into the WaitingForPhoneNumber state.
	 */
	private void gotoWaitingForPhoneNumber() {

		this.currentState = NewOrderContState.CSWaitingForPhoneNumber;

		// Print instructions to enter a customer's phone number.
		view.displayString( "Enter the customer's phone number.",
				NewOrderOutChan.OCInstructions );

		view.setChannelEnabled( NewOrderInChan.ICCustomerName, false );
		view.setChannelEnabled( NewOrderInChan.ICCustomerAddress, false );
		view.setChannelEnabled( NewOrderInChan.ICConfirm, false );
		view.setChannelEnabled( NewOrderInChan.ICListDelete, false );
		view.setChannelEnabled( NewOrderInChan.ICListModify, false );
		view.setChannelEnabled( NewOrderInChan.ICMenuOption, false );

		view.setChannelEnabled( NewOrderInChan.ICCustomerPhone, true);

	}

	/**
	 * Moves the controller into the WaitingForPhoneNumber state.
	 */
	private void gotoWaitingForCustInfo() {

		this.currentState = NewOrderContState.CSWaitingForCustInfo;
		// Print instructions to enter a customer's phone number.
		view.displayString( "Enter a name and address for this" +
				" customer.",
				NewOrderOutChan.OCInstructions );

		view.setChannelEnabled( NewOrderInChan.ICConfirm, false );
		view.setChannelEnabled( NewOrderInChan.ICListDelete, false );
		view.setChannelEnabled( NewOrderInChan.ICListModify, false );
		view.setChannelEnabled( NewOrderInChan.ICMenuOption, false );

		view.setChannelEnabled( NewOrderInChan.ICCustomerName, true );
		view.setChannelEnabled( NewOrderInChan.ICCustomerAddress, true );
	}

	/**
	 * Moves the controller into the WaitingForPhoneNumber state.
	 */
	private void gotoDisplayingOrder() {

		this.currentState = NewOrderContState.CSDisplayingOrder;
				
		// Disable the back button if we're modifying.
		if( _modifying ) {
			view.setChannelEnabled( NewOrderInChan.ICBack, false );
		}

		// Disable the customer info channels.
		view.setChannelEnabled( NewOrderInChan.ICCustomerName, false );
		view.setChannelEnabled( NewOrderInChan.ICCustomerAddress, false );

		// Enable the other channels.
		view.setChannelEnabled( NewOrderInChan.ICListDelete, true);
		view.setChannelEnabled( NewOrderInChan.ICListModify, true);
		view.setChannelEnabled( NewOrderInChan.ICMenuOption, true);

		// Update the fooditem list in the order.
		updateOrderFoodItemList();
		
		// Display the customer and the list.
		view.displayObject( _currentOrder.getCustomer(),
				NewOrderOutChan.OCCustomerDisplay );
		view.displayList( _currentOrder.getFoodItems(),
				NewOrderOutChan.OCFoodItemList );
		
		view.displayString( "$" + Order.formatPrice( _currentOrder.calculateSubtotal() ),
				NewOrderOutChan.OCSubTotalDisplay );
		view.displayString( "$" + Order.formatPrice( _currentOrder.getTax() ),
				NewOrderOutChan.OCTaxDisplay );
		view.displayString( "$" + Order.formatPrice( _currentOrder.calculateTotal() ),
				NewOrderOutChan.OCTotalDisplay );

	}

	/**
	 * Moves the controller into the WaitingForPhoneNumber state.
	 */
	private void gotoDisplayingOrderConfirmation() {

		this.currentState = NewOrderContState.CSDisplayingOrderConfirmation;

		// Disallow modifying and deleting of the list.
		view.setChannelEnabled( NewOrderInChan.ICCustomerName, false );
		view.setChannelEnabled( NewOrderInChan.ICCustomerAddress, false );		

		view.setChannelEnabled( NewOrderInChan.ICListDelete, false );
		view.setChannelEnabled( NewOrderInChan.ICListModify, false );
		view.setChannelEnabled( NewOrderInChan.ICMenuOption, false );
		view.setChannelEnabled( NewOrderInChan.ICBack, false );

		// Show a "continue" option.
		view.setChannelEnabled( NewOrderInChan.ICConfirm, true);

		// Display the customer and the list.
		view.displayObject( _currentOrder.getCustomer(),
				NewOrderOutChan.OCCustomerDisplay );
		view.displayList( _currentOrder.getFoodItems(),
				NewOrderOutChan.OCFoodItemList );

		view.displayString( "$" + Order.formatPrice( _currentOrder.calculateSubtotal() ),
				NewOrderOutChan.OCSubTotalDisplay );
		view.displayString( "$" + Order.formatPrice( _currentOrder.getTax() ),
				NewOrderOutChan.OCTaxDisplay );
		view.displayString( "$" + Order.formatPrice( _currentOrder.calculateTotal() ),
				NewOrderOutChan.OCTotalDisplay );
		
		view.displayString( "" + Time.formatTime(_currentOrder.calculateEstimatedDeliveryTime()),
				NewOrderOutChan.OCDeliveryTimeDisplay );

	}

	/**
	 * Updates the food item list stored in the order based on the quantity map.
	 */
	private void updateOrderFoodItemList() {
		
		// Filter out the list of pizzas in the order.
		ArrayList<PizzaFoodItem> pizzaList = new ArrayList<PizzaFoodItem>();
		for( FoodItem item : _currentOrder.getFoodItems() ) {
			if( item instanceof PizzaFoodItem ) {
				pizzaList.add( (PizzaFoodItem)item );
			}
		}
		
		// Remove everything from the list.
		_currentOrder.getFoodItems().clear();
		
		// Add the pizzas.
		_currentOrder.getFoodItems().addAll( pizzaList );
		
		// Add the sides to the order according to the 
		// quantity map.
		for( SideFoodItem side : _quantityMap.keySet() ) {
			
			// Duplicate this side the appropriate number of times.
			for( int i = 0; i < _quantityMap.get( side ); i++ ) {
				
				SideFoodItem duplicateItem = new SideFoodItem( 
						side.getName(), 
						side.getPrice(),
						side.getPrepTime(),
						side.getCookTime(),
						side.getOvenSpaceUnits() );
				_currentOrder.addFoodItem( duplicateItem );
				
			}
			
		}
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {

		view.displayString( message, NewOrderOutChan.OCError );

	}

}
