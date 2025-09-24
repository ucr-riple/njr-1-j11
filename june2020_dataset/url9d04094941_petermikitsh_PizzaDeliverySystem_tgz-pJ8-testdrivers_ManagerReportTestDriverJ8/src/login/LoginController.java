package login;

import login.LoginView.UserInChan;
import login.LoginView.UserOutChan;
import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.User;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;
import actionmenu.RootController;
import actionmenu.RootView;
import actionmenu.RootViewGUI;

/**
 * The logic controller for the authentication module. In charge of 
 *   logging the user in to the system.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 *
 */
public class LoginController extends GeneralController {

	/**
	 * The states of this controller.
	 */
	private enum UserControllerState implements ControllerState {
		
		CSWaitingForLoginId,
		CSWaitingForPassword;
		
	}
	
	/**
	 * The user that is being manipulated.
	 */
	private User _currentUser;
	
	/**
	 * The default constructor.
	 */
	public LoginController() {
		_currentUser = new User();
	}
	
	/**
	 * Constructor that takes a user.
	 * 
	 * @param user   given user to set
	 */
	public LoginController(User user) {
		_currentUser = user;
	}
	
	/**
	 * @see viewcontroller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {

		this.active = true;
		
		gotoWaitingForLoginId();
		
		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (LoginViewGUI)view );
		} else {
			((LoginViewCL)view).getUserInput();
		}

	}

	/**
	 * @see viewcontroller.GeneralController#respondToInput(java.lang.String, viewcontroller.GeneralView.InputChannel)
	 */	
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch ((UserControllerState) currentState) {
		
		case CSWaitingForLoginId:
			handleWaitingForLoginId(message, (UserInChan) channel);
			break;

		case CSWaitingForPassword:
			handleWaitingForPassword(message, (UserInChan) channel);
			break;

		}
	}
	
	/**
	 * Moves the controller to the WaitingForLoginId state.
	 */
	private void gotoWaitingForLoginId() {
		
		this.currentState = UserControllerState.CSWaitingForLoginId;
		view.displayString("Login ID: ", UserOutChan.OCInstructions);
		view.setChannelEnabled(UserInChan.ICLoginIdInput, true);
		
	}
	
	/**
	 * Handles input in the WaitingForLoginId state.
	 * 
	 * @param message   the String message
	 * @param channel   the channel from which the input was received
	 */
	private void handleWaitingForLoginId(String message, UserInChan channel) {
		
		switch ((UserInChan) channel) {
		
		case ICStringInput:
			
			// Sets the current user to the given login ID.
			_currentUser = User.getDb().get(message.hashCode());
						
			if ( _currentUser != null )
				gotoWaitingForPassword();
			
			else
				handleInputError("Invalid username/password combination.");
			
		}
		
	}
	
	/**
	 * Moves the controller to the WaitingForPassword state.
	 */
	private void gotoWaitingForPassword() {
		
		this.currentState = UserControllerState.CSWaitingForPassword;
		view.displayString("Password: ", UserOutChan.OCInstructions);
		view.setChannelEnabled(UserInChan.ICPasswordInput, true);
		
	}
	
	/**
	 * Handles input in the WaitingForPassword state.
	 * 
	 * @param message   the String message
	 * @param channel   the channel from which the input was received
	 */
	private void handleWaitingForPassword(String message, UserInChan channel) {
		
		switch ((UserInChan) channel) {
		
		case ICStringInput:
			
			if( message.equals( _currentUser.getPassword() ) ) {
				
				// Set the current user in the PDS singleton, and 
				// send the username to the view manager (GUI mode only)
				PizzaDeliverySystem.currentUser = _currentUser;
				
				if( PizzaDeliverySystem.RUN_WITH_GUI ) {
					PDSViewManager.setUserNameString( _currentUser.getName() );
				}
				
				// Proceed to the root module .
				RootController cont = new RootController();
				RootView view = PizzaDeliverySystem.RUN_WITH_GUI ?
											new RootViewGUI() : null;
				cont.setView(view);
				view.setController(cont);
				cont.enterInitialState();
				gotoWaitingForLoginId();
				
				this.active = false;
				
			}
			else {
				handleInputError("Invalid username/password combination. Please try again.");
				gotoWaitingForLoginId();
			}
		}
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, UserOutChan.OCError );

	}

}
