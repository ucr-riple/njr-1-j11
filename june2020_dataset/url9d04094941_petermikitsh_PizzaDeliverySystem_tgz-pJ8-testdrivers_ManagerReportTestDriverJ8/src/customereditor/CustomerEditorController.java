package customereditor;

import java.util.ArrayList;
import java.util.Collections;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.Address;
import model.Customer;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import customereditor.CustomerEditorView.CustomerEditorInChan;
import customereditor.CustomerEditorView.CustomerEditorOutChan;

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
public class CustomerEditorController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum CustomerControllerState implements ControllerState {
		
		CSDisplayingCustomerList,
		CSWaitingForDeleteConfirm,
		CSWaitingForNewCustomer;
		
	}
	
	/**
	 * An existing customer that is being modified.
	 */
	private Customer _modifyingCustomer;
	
	/**
	 * The customer object to which new data will be added.
	 */
	private Customer _creatingCustomer;
	
	/**
	 * Indicates if we're modifying an existing customer.
	 */
	private boolean _modifying;
	
	/**
	 * The cached list of customers, sorted by name.
	 */
	private ArrayList<Customer> _customerCache;
	
	/**
	 * The default constructor.
	 */
	public CustomerEditorController() {
		
		updateCache( "" );
		
	}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying customer list state.
		this.active = true;
		gotoDisplayingCustomerList();
		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (CustomerEditorViewGUI)view );
		} else {
			((CustomerEditorViewCL)view).getUserInput();
		}
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {

		// Check for a back button.
		if( channel == CustomerEditorInChan.ICBack && 
				message.equalsIgnoreCase( CustomerEditorView.BACK_KEY ) ) {

			// Set active to false.  In the GUI version, pop the current
			// view.
			this.active = false;

			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}

		}

		// Check the current state, and call the appropriate handler method.
		switch( (CustomerControllerState)currentState ) {

		case CSDisplayingCustomerList:
			handleDisplayingCustomerList( message, 
										  (CustomerEditorInChan)channel );
			break;
			
		case CSWaitingForDeleteConfirm:
			handleWaitingForDeleteConfirm( message, 
										(CustomerEditorInChan)channel );
			break;
			
		case CSWaitingForNewCustomer:
			handleWaitingForCustomerInfo( message, 
										(CustomerEditorInChan)channel );
			break;
			
		}
		
	}
	
	/**
	 * Handles input in the DisplayingCustomerList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingCustomerList( String message, 
										  	   CustomerEditorInChan channel ) {
		
		// Clear out the current customers storers.
		_modifyingCustomer = null;
		_creatingCustomer = null;
		
		// The channel method should be either list selection or 
		//  menu option.
		switch( channel ) {
		
		case ICMenuOption: 
			
			// Possible commands: ADD: add a customer
			if( message.equalsIgnoreCase( CustomerEditorView.ADD_KEY ) ) {

				_modifying = false;
				_creatingCustomer = new Customer();
				gotoWaitingForCustomerInfo();

			} else {

				handleInputError("The input was invalid. Please try again.");
				
			}
			break;
			
		case ICSearchTerm:
			
			// Update the cache with a filtered list.
			updateCache( message );
			
			// Go to the results display.
			gotoDisplayingCustomerList();
			break;

		case ICListDelete:
		case ICListModify:
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
			
		case ICConfirm:
			if( message.equalsIgnoreCase( CustomerEditorView.CANCEL_KEY ) ) {
				// Reload this state.
				gotoDisplayingCustomerList();
			}
			break;
			
		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}

	/**
	 * Handles input in the WaitingForDeleteConfirm state.
	 * 
	 * @param message   The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForDeleteConfirm(String message,
			CustomerEditorInChan channel) {
		
		// Check for the confirmation or for a back button.
		if( channel == CustomerEditorInChan.ICConfirm ) {
		
			if( message.equalsIgnoreCase( CustomerEditorView.CONFIRM_YES_KEY ) ) {
				
				// Delete the customer from the list, then return the main
				// prompt.
				Customer.getDb().remove( _modifyingCustomer.getKey());
				updateCache( "" );
				gotoDisplayingCustomerList();
				
			} else if( message.equalsIgnoreCase( CustomerEditorView.CONFIRM_NO_KEY ) ) {
				
				// Do nothing, just return to the main prompt.
				gotoDisplayingCustomerList();
			
			} else {
				
				handleInputError("The input was invalid. Please try again.");
				
			}

		} else {

			handleInputError("The input was invalid. Please try again.");

		}

	}

	/**
	 * Handles input in the handleWaitingForNewName state.
	 * 
	 * @param message   The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleWaitingForCustomerInfo( String message, 
			CustomerEditorInChan channel ) {

		// Check the channel.
		switch( (CustomerEditorInChan)channel ) {

		case ICCustomerData:

			// Add the name, phone number, and address as data is passed in.
			if( _creatingCustomer.getName().isEmpty() ) { 

				if( message.isEmpty() ) {
					handleInputError( "Customer name cannot be empty." );
					_creatingCustomer = new Customer();
				} else {
					_creatingCustomer.setName( message );
				}

			} else if( _creatingCustomer.getPhoneNumber().isEmpty() ) {

				if( Customer.isValidPhoneNumber( message ) ) {

					// Make sure that the database does not already have a customer
					// with this phone number.
					boolean numberExists = false;
					if( !(_modifying && message.equals( _modifyingCustomer.getPhoneNumber() )) ) {
						for( Customer cust : Customer.getDb().list() ) {
							if( cust.getPhoneNumber().equals( message ) ) {
								numberExists = true;
								break;
							}
						}
					}
						
					// Report an error if the number is already used, or store the phone
					// number in the customer if not.
					if( numberExists ) {
						view.displayString( "Customer with given number already " +
								"exists in the database.", 
								CustomerEditorOutChan.OCError );
						_creatingCustomer = new Customer();
					} else {
						_creatingCustomer.setPhoneNumber( message );
					}

				} else {

					handleInputError("The phone number entered is invalid. Please try again.");

				}

			} else if( _creatingCustomer.getStreetAddress().isEmpty() ) {

				// Check that the address is valid.
				Address addr = Address.getAddressForAlias( message);
				if( null == addr ) {

					// Report an error.
					view.displayString("The entered address is not in the coverage area.",
							CustomerEditorOutChan.OCError );
					_creatingCustomer = new Customer();

				} else {

					_creatingCustomer.setStreetAddress( addr );

					// Now, if we're not modifying, just add the new customer to
					// the database.  Otherwise, remove the old ("modifying") customer from
					// the database, store its ID in the new customer, and add the new customer.
					if( _modifying ) {
						Customer.getDb().remove( _modifyingCustomer.getKey() );
						_creatingCustomer.setCustomerId( _modifyingCustomer.getCustomerId() );
					}
					
					Customer.getDb().add( _creatingCustomer );
					updateCache( "" );
					gotoDisplayingCustomerList();

				}
				
			}

			break;

		case ICListDelete:
		case ICListModify:
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
			
		case ICConfirm:
			// Check for the cancel button.
			if( message.equalsIgnoreCase( CustomerEditorView.CANCEL_KEY ) ) {
				gotoDisplayingCustomerList();
			}
			break;

		default:
			handleInputError("The input was invalid. Please try again.");
			break;

		}

	}
	
	/**
	 * Handles input for the listmodify/listdelete channels.  (to avoid repeated code).
	 */
	private void handleDeleteOrModify( String message, CustomerEditorInChan channel ) {
		
		// The message should be a list index.  Get the list index
		// and delete/modify the food item from the order at that index.
		int index = -1;
		try { 
			index = Integer.parseInt( message );
			if( index < 0 || 
					index >= _customerCache.size() ) {
				handleInputError("Index out of range. Please try again.");
			}
		} catch( NumberFormatException e ) {
			handleInputError("The input was invalid. Please try again.");
		}

		if( index >= 0 && index < _customerCache.size() ) {

			if( channel == CustomerEditorInChan.ICListDelete ) {

				// Store the indicated customer and prompt for 
				// a confirmation.
				_modifyingCustomer = _customerCache.get(index);
				gotoWaitingForDeleteConfirm();

			} else if( channel == CustomerEditorInChan.ICListModify ) {

				// Get the customer that is indicated and set that as the
				// temp one, then create a new customer and go to the 
				// editing state.
				_modifying = true;
				_modifyingCustomer = _customerCache.get(index);
				_creatingCustomer = new Customer();
				gotoWaitingForCustomerInfo();

			}

		}
		
	}

	/**
	 * Moves the controller into the DisplayingCustomerList state.
	 */
	private void gotoDisplayingCustomerList() {
		
		this.currentState = CustomerControllerState.CSDisplayingCustomerList;
	
		// Clear the current customer.
		_creatingCustomer = null;
		_modifyingCustomer = null;
		
		// Print the customer list.
		view.displayList( _customerCache, CustomerEditorOutChan.OCList );
		
		// Show some instructions.
		view.displayString( " ", CustomerEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( CustomerEditorInChan.ICCustomerData , false );
		view.setChannelEnabled( CustomerEditorInChan.ICConfirm, false );
		
		view.setChannelEnabled( CustomerEditorInChan.ICListDelete, true );
		view.setChannelEnabled( CustomerEditorInChan.ICListModify, true );
		view.setChannelEnabled( CustomerEditorInChan.ICMenuOption, true );
		view.setChannelEnabled( CustomerEditorInChan.ICSearchTerm, true );
		view.setChannelEnabled( CustomerEditorInChan.ICBack, true );
		
	}
	
	/**
	 * Moves the controller to the WaitingForDeleteConfirm state.
	 */
	private void gotoWaitingForDeleteConfirm() {
		
		this.currentState = CustomerControllerState.CSWaitingForDeleteConfirm;
		
		// Print instructions.
		view.displayString( "<html><center>Do you really want to delete customer<br>\"" +
							_modifyingCustomer.getName() + "\"?</center></html>",
							CustomerEditorOutChan.OCConfirm );
			
		view.setChannelEnabled( CustomerEditorInChan.ICCustomerData, false );
		view.setChannelEnabled( CustomerEditorInChan.ICListDelete, false );
		view.setChannelEnabled( CustomerEditorInChan.ICListModify, false );
		view.setChannelEnabled( CustomerEditorInChan.ICMenuOption, false );
		view.setChannelEnabled( CustomerEditorInChan.ICSearchTerm, false );
		
		view.setChannelEnabled( CustomerEditorInChan.ICConfirm, true );
		
	}
	
	/**
	 * Moves the controller to the WaitingForCustomerInfo state.
	 */
	private void gotoWaitingForCustomerInfo() {
		
		this.currentState = CustomerControllerState.CSWaitingForNewCustomer;

		// Print a prompt for the name to input.
		view.displayString( "Enter the customer's name, phone number, and address.", 
				CustomerEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( CustomerEditorInChan.ICSearchTerm, true );
		view.setChannelEnabled( CustomerEditorInChan.ICConfirm, false );

		view.setChannelEnabled( CustomerEditorInChan.ICMenuOption, true );
		// view.setChannelEnabled( CustomerEditorInChan.ICCustomerData, true );
		view.setChannelEnabled( CustomerEditorInChan.ICListDelete, true );
		view.setChannelEnabled( CustomerEditorInChan.ICListModify, true );

		// Send the current customer to the view so it can display it as
		// preset input.
		view.displayObject( _modifyingCustomer, CustomerEditorOutChan.OCEditCustomer );

	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, CustomerEditorOutChan.OCError );
		
	}

	/**
	 * Updates the locally-stored cache with a sorted copy of the customer
	 * database.
	 * 
	 * @param filter The string term to filter the list with.
	 */
	private void updateCache( String filterTerm ) {
		
		// Filter the results if the filter term is nonempty.
		if( filterTerm.length() == 0 ) {

			// Update the cache.
			_customerCache = Customer.getDb().list();
			
		} else {
			
			_customerCache = new ArrayList<Customer>();
			
			// Add customers to the cache list if they match the filter.
			for( Customer cust : Customer.getDb().list() ) {
				
				if( cust.matchesSearch( filterTerm ) ) {
					
					_customerCache.add( cust );
					
				}
				
			}
			
		}
		
		// Sort the results.
		Collections.sort( _customerCache );
		
	}

}
