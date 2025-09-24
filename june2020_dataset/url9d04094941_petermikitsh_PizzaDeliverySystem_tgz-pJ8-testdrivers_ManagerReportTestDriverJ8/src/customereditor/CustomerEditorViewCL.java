
package customereditor;

import java.util.ArrayList;
import java.util.Scanner;

import model.Customer;
import viewcontroller.GeneralViewCL;
/**
 * The command line view for the Customer module.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class CustomerEditorViewCL extends CustomerEditorView
	implements GeneralViewCL {
	
	/**
	 * This view's scanner.
	 */
	private Scanner scan;
	
	/**
	 * The default constructor.
	 */
	public CustomerEditorViewCL() {
		
		scan = new Scanner( System.in );
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		// Pretty much everything is just written to the terminal, but
		// we'll insert some tags to help with tracing.
		switch( (CustomerEditorOutChan)outChannel ) {
		
		case OCInstructions:
			System.out.println( "Instructions: " + message );
			break;
			
		case OCConfirm:
			System.out.println( "Confirm: " + message );
			break;
			
		case OCError:
			System.err.println( "Error: " + message );
			break;
		
		}
		
		System.out.println();
		
	}


	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		if( outChannel == CustomerEditorOutChan.OCDisplayCustomer) {
			
			// The object is a customer.
			Customer cust = (Customer)object;
			
			// Display the information of the customer.
			System.out.println( " Name         : " + cust.getName() );
			System.out.println( " Phone Number : " + cust.getPhoneNumber() );
			System.out.println( " Address      : " + cust.getStreetAddress().getLocation() );
			
			System.out.println();
			
		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		if( outChannel == CustomerEditorOutChan.OCList ) {
			
			// A elements of a list here should be Customer objects.  Display
			// each one in the terminal.
			if( list.isEmpty() ) {
				
				System.out.println( "There are no customers in the list." );
				
			} else {
					
				for( int i = 0; i < list.size(); i++ ) {
					
					Customer cust = (Customer)list.get( i );
					System.out.print( "" + i + " : ");
					System.out.print( cust.getName() + "  |  " );
					System.out.print( cust.getPhoneNumber() + "  |  " );
					System.out.println( cust.getStreetAddress().getLocation() );
					
				}
				
			}
			
			System.out.println();
			
		}
		
	}
	
	/**
	 * Tells the view to get text input from the user, and iterates this
	 *  until it is told to stop.
	 */
	public void getUserInput() {
		
		while( controller.isActive() ) {

			// Read in a nonempty string.
			String input = null;
			while( input == null || input.length() == 0 ) {
				System.out.print( "> " );
				input = scan.nextLine();
			}	

			// Check the input and send it to the controller through the 
			// appropriate channel.
			CustomerEditorInChan channel = CustomerEditorInChan.ICCustomerData;

			// If the first character is '*', the input is a menu option.
			if( input.charAt(0) == '*' ) {
				
				input = input.substring( 1 );
				channel = CustomerEditorInChan.ICMenuOption;
				
				if( input.equalsIgnoreCase( CustomerEditorView.CONFIRM_YES_KEY ) ||
						input.equalsIgnoreCase( CustomerEditorView.CONFIRM_NO_KEY ) ) {
					
					channel = CustomerEditorInChan.ICConfirm;
					
				} else if( input.length() >= 3 &&
						input.substring(0, 3).equalsIgnoreCase( 
												CustomerEditorView.DELETE_KEY ) &&
									input.indexOf( ' ' ) >= 0 ) {
					
					// The second token of the string should be a number.
					// Send it through the list delete channel.
					input = input.substring( input.indexOf( ' ' ) + 1 );
					channel = CustomerEditorInChan.ICListDelete;
					
				} else if( input.length() >= 3 && 
						input.substring(0, 3).equalsIgnoreCase( 
												CustomerEditorView.MODIFY_KEY ) &&
									input.indexOf( ' ' ) >= 0 ) {
					
					// The second token of the string should be a number.
					// Send it through the list modify channel.
					input = input.substring( input.indexOf( ' ' ) + 1 );
					channel = CustomerEditorInChan.ICListModify;
					
				}

			}
			
			// If the input string is a valid phone number, search for that
			// customer.
			else if( Customer.isValidPhoneNumber( input ) ) {
				
				channel = CustomerEditorInChan.ICCustomerData;
				
			}


			// Send the input to the controller.
			controller.respondToInput( input, channel );
			
		}
		
	}

	
	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled(viewcontroller.GeneralView.InputChannel, boolean)
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		switch( (CustomerEditorInChan)inChannel ) {
		
		case ICMenuOption: 
			System.out.println( "  *" + CustomerEditorView.ADD_KEY + 
					" :  Add a new customer." );
			System.out.println( "  *" + CustomerEditorView.MODIFY_KEY + " #" +
					" :  Modify the information of customer #." );
			System.out.println( "  *" + CustomerEditorView.DELETE_KEY + " #" + 
					" :  Delete customer # from the database." );
			System.out.println( "  *" + CustomerEditorView.BACK_KEY + 
					" :  Return to the root menu." );
			System.out.println();
			break;
		case ICConfirm:
			System.out.println( "  *" + CustomerEditorView.CONFIRM_YES_KEY + 
					" :  Answer \"yes\" to this prompt." );
			System.out.println( "   *" + CustomerEditorView.CONFIRM_NO_KEY + 
					" :  Answer \"no\" to this prompt." );
			System.out.println();
			break;
		case ICCustomerData:
			System.out.println( "  *" + CustomerEditorView.BACK_KEY + 
					" :  Return to the customer list." );
			System.out.println();
			break;

		}
		
	}
	
}
