package neworder.addpizza;

import java.util.ArrayList;
import java.util.Scanner;

import model.PizzaFoodItem;
import model.Topping;
import neworder.NewOrderView;
import neworder.addpizza.AddPizzaView.AddPizzaInChan;
import neworder.addpizza.AddPizzaView.AddPizzaOutChan;
import viewcontroller.GeneralView;

public class AddPizzaViewCL extends AddPizzaView {

	/**
	 * This view's scanner.
	 */
	private Scanner scan;
	
	
	/**
	 * The default constructor.
	 */
	public AddPizzaViewCL() {
		
		scan = new Scanner( System.in );
		
	}
	


	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		// Pretty much everything is just written to the terminal, but
		// we'll insert some tags to help with tracing.
		switch( (AddPizzaOutChan)outChannel ) {
		
		case OCInstructions:
			System.out.println( "Instructions: " + message );
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
		
		if( outChannel == AddPizzaOutChan.OCDisplayPizza) {
			
			// The object is a pizza.
			PizzaFoodItem pizza = (PizzaFoodItem)object;
			
			// Display the information of the pizza customer.
			System.out.println( " Size: " + pizza.getSize());
			System.out.println( " Toppings: ");
			displayList( pizza.getToppings(), AddPizzaOutChan.OCPizzaToppingsList);
			System.out.println( " Item subtotal : $" + pizza.getFormattedPrice());
			System.out.println();
			
		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		if( outChannel == AddPizzaOutChan.OCPizzaToppingsList ) {
			
			// A elements of a list here should be Topping objects.  Display
			// each one in the terminal.
			if( list.isEmpty() ) {
				
				System.out.println( "There are no toppings on the pizza." );
				
			} else {
					
				for( int i = 0; i < list.size(); i++ ) {
					
					Topping top = (Topping)list.get( i );
					System.out.print("   " + i + ": ");
					System.out.print( top.getName() + " " );
					System.out.print( "(" + top.getLocation() + ") ");
					//System.out.print(" -  $" + top.getFormattedPrice());
					System.out.println();
					
				}
				
			}
			
			System.out.println();
			
		}
		if( outChannel == AddPizzaOutChan.OCAvailToppingsList ) {
			
			// A elements of a list here should be Topping objects.  Display
			// each one in the terminal.
			if( list.isEmpty() ) {
				
				System.out.println( "There are no toppings available." );
				
			} else {
					
				for( int i = 0; i < list.size(); i++ ) {
					
					Topping top = (Topping)list.get( i );
					System.out.print( " " + i + " : ");
					System.out.print( top.getName());
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * Tells the view to get text input from the user, and iterates this
	 *  until it is told to stop.
	 */
	public void getUserInput() {
		
		// Display the list of available commands.
		// displayCommandList();
		
		while( controller.isActive() ) {

			// Read in a nonempty string.
			String input = null;
			while( input == null || input.length() == 0 ) {
				System.out.print( "> " );
				input = scan.nextLine();
			}	

			// Check the input and send it to the controller through the 
			// appropriate channel.
			AddPizzaInChan channel = null;

			// If the first character is '*', the input is a menu option.
			if( input.charAt(0) == '*'  && input.length() >= 4) {
				
				input = input.substring(1);
				
				if( input.equalsIgnoreCase(AddPizzaView.SMALL_KEY) ||
						input.equalsIgnoreCase(AddPizzaView.MEDIUM_KEY) ||
						input.equalsIgnoreCase(AddPizzaView.LARGE_KEY) ) 
				{
					channel = AddPizzaInChan.ICSizeSelection;
				}
				else if ( input.equalsIgnoreCase(AddPizzaView.LEFT_KEY) || 
							input.equalsIgnoreCase(AddPizzaView.RIGHT_KEY) ||
							input.equalsIgnoreCase(AddPizzaView.WHOLE_KEY))
				{
					channel = AddPizzaInChan.ICLocationSelection;
				}
				else if ( input.equalsIgnoreCase(AddPizzaView.BACK_KEY)){
					channel = AddPizzaInChan.ICGoBack;
				}
				else if( input.substring(0, 3).equalsIgnoreCase( 
						NewOrderView.DELETE_KEY ) &&
						input.indexOf( ' ' ) >= 0 ) {
					input = input.substring( input.indexOf( ' ' ) + 1 );
					channel = AddPizzaInChan.ICDeleteFromList;
				}
				else if ( input.equalsIgnoreCase(AddPizzaView.CONTINUE_KEY))
				{
					channel = AddPizzaInChan.ICContinue;
				}

			} 
			else {

				// Check if the input is an integer.
				try{
					Integer.parseInt( input );
					channel = AddPizzaInChan.ICNumericInput; 
				} catch( NumberFormatException e ) {
					// Don't modify the channel.
				}

			}

			// Send the input to the controller.
			input.toString();
			controller.respondToInput( input, channel );
			
		}
	}
	
	
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		// Check the channel that was enabled, and print the for commands
		// in that channel.
		switch( (AddPizzaInChan)inChannel ) {
		
		case ICSizeSelection:
			System.out.println("  *" + AddPizzaView.SMALL_KEY + 
					" :  Select small pizza.");
			System.out.println("  *" + AddPizzaView.MEDIUM_KEY + 
					" :  Select medium pizza.");
			System.out.println("  *" + AddPizzaView.LARGE_KEY + 
					" :  Select large pizza.");
			break;
			
		case ICLocationSelection:
			System.out.println("  *" + AddPizzaView.LEFT_KEY +
					" :  Toppings on left side only.");
			System.out.println("  *" + AddPizzaView.RIGHT_KEY +
					" :  Toppings on right side only.");
			System.out.println("  *" + AddPizzaView.WHOLE_KEY +
					" :  Toppings on the whole pizza.");
			break;
		
		case ICDeleteFromList:
			System.out.println("  *" + AddPizzaView.DELETE_KEY + 
					" # : Delete topping of inputted number.");
			break;
		
		case ICQuantity:
			displayString("Enter the number of pizzas you would like.", 
					AddPizzaOutChan.OCInstructions);
			break;
			
		case ICContinue:
			System.out.println("  *" + AddPizzaView.CONTINUE_KEY +
					" :  Done adding toppings, continue to next step.");
			break;
			
		case ICToppingsSelection:
			displayString("Choose a topping to add to your pizza.", AddPizzaOutChan.OCInstructions);
			displayList(Topping.getDb().list(), AddPizzaOutChan.OCAvailToppingsList);
			System.out.println();
			break;
			
		case ICGoBack:
			System.out.println("  *" + AddPizzaView.BACK_KEY +
			" :  Go back to the previous page.");
			break;
		}
	}


}
