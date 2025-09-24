/*
 * UserViewCL.java
 */

package login;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author cklimkowsky
 *
 */
public class LoginViewCL extends LoginView {

	/**
	 * This view's scanner.
	 */
	private Scanner scan;
	
	/**
	 * The default constructor.
	 */
	public LoginViewCL() {
		
		scan = new Scanner(System.in);
		
	}
	
	/**
	 * Tells the view to get text input from the user, and iterates this
	 * until it is told to stop.
	 */
	public void getUserInput() {
		
		while (controller.isActive()) {
			
			String input = null;
			
			while (input == null || input.length() == 0) {
				System.out.print( "> " );
				input = scan.nextLine();
			}
			
			UserInChan channel = UserInChan.ICStringInput;
			
			controller.respondToInput(input, channel);
			
		}
		
	}

	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		switch ((UserOutChan) outChannel) {
		
		case OCInstructions:
			System.out.println(message);
			break;
		
		case OCError:
			System.err.println("Error: " + message);
			break;
		
		}
		
		System.out.println();
		
	}

	@Override
	public void displayObject(Object object, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}
	
	
}
