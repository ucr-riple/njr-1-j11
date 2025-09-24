package usereditor;

import java.util.ArrayList;
import java.util.Collections;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.User;
import model.User.UserPermissions;
import usereditor.UserEditorView.UserEditorInChan;
import usereditor.UserEditorView.UserEditorOutChan;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the user editor module.
 * In charge of: 
 * 	- displaying and supporting changes to the list of users.
 * Specific functions:
 *  - adding new users,
 *  - editing the information of existing users,
 *  - searching for users by phone number,
 *  - deleting existing users.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class UserEditorController extends GeneralController {
	
	/**
	 * The states of this controller.
	 */
	private enum UserControllerState implements ControllerState {
		
		CSDisplayingUserList,
		CSWaitingForDeleteConfirm,
		CSWaitingForNewUser;
		
	}
	
	/**
	 * An existing user that is being modified.
	 */
	private User _modifyingUser;
	
	/**
	 * The user object to which new data will be added.
	 */
	private User _creatingUser;
	
	/**
	 * Indicates if we're modifying an existing user.
	 */
	private boolean _modifying;
	
	/**
	 * The cached list of users, sorted by name.
	 */
	private ArrayList<User> _userCache;
	
	/**
	 * The default constructor.
	 */
	public UserEditorController() {
		
		updateCache();
		
	}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying user list state.
		this.active = true;
		gotoDisplayingUserList();
		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (UserEditorViewGUI)view );
		} else {
			// Do nothing.
		}
		
	}
	
	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {

		// Check for a back button.
		if( channel == UserEditorInChan.ICBack && 
				message.equalsIgnoreCase( UserEditorView.BACK_KEY ) ) {

			// Set active to false.  In the GUI version, pop the current
			// view.
			this.active = false;

			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}

		}

		// Check the current state, and call the appropriate handler method.
		switch( (UserControllerState)currentState ) {

		case CSDisplayingUserList:
			handleDisplayingUserList( message, 
										  (UserEditorInChan)channel );
			break;
			
		case CSWaitingForDeleteConfirm:
			handleWaitingForDeleteConfirm( message, 
										(UserEditorInChan)channel );
			break;
			
		case CSWaitingForNewUser:
			handleWaitingForUserInfo( message, 
										(UserEditorInChan)channel );
			break;
			
		}
		
	}
	
	/**
	 * Handles input in the DisplayingUserList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingUserList( String message, 
										  	   UserEditorInChan channel ) {
		
		// Clear out the current users storers.
		_modifyingUser = null;
		_creatingUser = null;
		
		// The channel method should be either list selection or 
		//  menu option.
		switch( channel ) {
		
		case ICMenuOption: 
			
			// Possible commands: ADD: add a user
			if( message.equalsIgnoreCase( UserEditorView.ADD_KEY ) ) {

				_modifying = false;
				_creatingUser = new User();
				gotoWaitingForUserInfo();

			} else {

				handleInputError("The input was invalid. Please try again.");
				
			}
			break;
			
		case ICListDelete:
		case ICListModify:
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
			
		case ICConfirm:
			if( message.equalsIgnoreCase( UserEditorView.CANCEL_KEY ) ) {
				// Reload this state.
				gotoDisplayingUserList();
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
			UserEditorInChan channel) {
		
		// Check for the confirmation or for a back button.
		if( channel == UserEditorInChan.ICConfirm ) {
		
			if( message.equalsIgnoreCase( UserEditorView.CONFIRM_YES_KEY ) ) {
				
				// Delete the user from the list, then return the main
				// prompt.
				User.getDb().remove( _modifyingUser.getKey());
				updateCache();
				gotoDisplayingUserList();
				
			} else if( message.equalsIgnoreCase( UserEditorView.CONFIRM_NO_KEY ) ) {
				
				// Do nothing, just return to the main prompt.
				gotoDisplayingUserList();
			
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
	private void handleWaitingForUserInfo( String message, 
			UserEditorInChan channel ) {

		// Check the channel.
		switch( (UserEditorInChan)channel ) {

		case ICUserData:

			// Add the name, phone number, and address as data is passed in.
			if( _creatingUser.getName().isEmpty() ) { 

				_creatingUser.setName( message );

			} else if( _creatingUser.getLoginId().isEmpty() ) {

				// Make sure that the database does not already have a user
				// with this login id.
				boolean idExists = false;
				if( !(_modifying && message.equals( _modifyingUser.getLoginId() )) ) {
					for( User user : User.getDb().list() ) {
						if( user.getLoginId().equals( message ) ) {
							idExists = true;
							break;
						}
					}
				}
					
				// Report an error if the number is already used, or store login id
				// in the user if not.
				if( idExists ) {
					view.displayString( "User with given login ID already " +
							"exists in the database.", 
							UserEditorOutChan.OCError );
					_creatingUser = new User();
				} else {
					_creatingUser.setLoginId( message );
				}

			} else if( _creatingUser.getPassword().isEmpty() ) {

				// Store the password.
				_creatingUser.setPassword( message );
				
			} else {
				
				// Check the string to see if it is empty.  It if isn't, set the 
				// new user as an admin.  If not, don't do that.  In either case,
				// submit the new user to the database.
				if( message.isEmpty() ) {
					_creatingUser.setPermissions( UserPermissions.NON_ADMIN );
				} else {
					_creatingUser.setPermissions( UserPermissions.ADMIN );
				}

				// Now, if we're not modifying, just add the new user to
				// the database.  Otherwise, remove the old ("modifying") user from
				// the database and add the new user.
				if( _modifying ) {
					User.getDb().remove( _modifyingUser.getKey() );
				}
				
				User.getDb().add( _creatingUser );
				updateCache();
				gotoDisplayingUserList();
				
			}

			break;

		case ICListDelete:
		case ICListModify:
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
			
		case ICConfirm:
			// Check for the cancel button.
			if( message.equalsIgnoreCase( UserEditorView.CANCEL_KEY ) ) {
				gotoDisplayingUserList();
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
	private void handleDeleteOrModify( String message, UserEditorInChan channel ) {
		
		// The message should be a list index.  Get the list index
		// and delete/modify the food item from the order at that index.
		int index = -1;
		try { 
			index = Integer.parseInt( message );
			if( index < 0 || 
					index >= _userCache.size() ) {
				handleInputError("Index out of range. Please try again.");
			}
		} catch( NumberFormatException e ) {
			handleInputError("The input was invalid. Please try again.");
		}

		if( index >= 0 && index < _userCache.size() ) {
			
			_modifyingUser = _userCache.get(index);
			
			// Make sure this user is the one that is logged in now.
			if(  PizzaDeliverySystem.currentUser != null &&
					PizzaDeliverySystem.currentUser.getLoginId().equals( _modifyingUser.getLoginId() ) ) {
				
				// Display an error and do nothing else.
				handleInputError( "The selected user is currently logged in and cannot be modified." );
				_modifyingUser = null;
				
			} else {
	
				if( channel == UserEditorInChan.ICListDelete ) {
	
					// Store the indicated user and prompt for 
					// a confirmation.
					gotoWaitingForDeleteConfirm();
	
				} else if( channel == UserEditorInChan.ICListModify ) {
	
					// Get the user that is indicated and set that as the
					// temp one, then create a new user and go to the 
					// editing state.
					_modifying = true;
					_creatingUser = new User();
					gotoWaitingForUserInfo();
	
				}
				
			}

		}
		
	}

	/**
	 * Moves the controller into the DisplayingUserList state.
	 */
	private void gotoDisplayingUserList() {
		
		this.currentState = UserControllerState.CSDisplayingUserList;
	
		// Clear the current user.
		_creatingUser = null;
		_modifyingUser = null;
		
		// Print the user list.
		view.displayList( _userCache, UserEditorOutChan.OCList );
		
		// Show some instructions.
		view.displayString( " ", UserEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( UserEditorInChan.ICUserData , false );
		view.setChannelEnabled( UserEditorInChan.ICConfirm, false );
		
		view.setChannelEnabled( UserEditorInChan.ICListDelete, true );
		view.setChannelEnabled( UserEditorInChan.ICListModify, true );
		view.setChannelEnabled( UserEditorInChan.ICMenuOption, true );
		view.setChannelEnabled( UserEditorInChan.ICBack, true );
		
	}
	
	/**
	 * Moves the controller to the WaitingForDeleteConfirm state.
	 */
	private void gotoWaitingForDeleteConfirm() {
		
		this.currentState = UserControllerState.CSWaitingForDeleteConfirm;
		
		// Print instructions.
		view.displayString( "<html><center>Do you really want to delete user<br>\"" +
							_modifyingUser.getName() + "\"?</center></html>",
							UserEditorOutChan.OCConfirm );
			
		view.setChannelEnabled( UserEditorInChan.ICUserData, false );
		view.setChannelEnabled( UserEditorInChan.ICListDelete, false );
		view.setChannelEnabled( UserEditorInChan.ICListModify, false );
		view.setChannelEnabled( UserEditorInChan.ICMenuOption, false );
		
		view.setChannelEnabled( UserEditorInChan.ICConfirm, true );
		
	}
	
	/**
	 * Moves the controller to the WaitingForUserInfo state.
	 */
	private void gotoWaitingForUserInfo() {
		
		this.currentState = UserControllerState.CSWaitingForNewUser;

		// Print a prompt for the name to input.
		view.displayString( "Enter the user's name, phone number, and address.", 
				UserEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( UserEditorInChan.ICConfirm, false );

		view.setChannelEnabled( UserEditorInChan.ICMenuOption, true );
		view.setChannelEnabled( UserEditorInChan.ICListDelete, true );
		view.setChannelEnabled( UserEditorInChan.ICListModify, true );

		// Send the current user to the view so it can display it as
		// preset input.
		view.displayObject( _modifyingUser, UserEditorOutChan.OCEditUser );

	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		view.displayString( message, UserEditorOutChan.OCError );	
	}

	/**
	 * Updates the locally-stored cache with a sorted copy of the user
	 * database.
	 * 
	 * @param filter The string term to filter the list with.
	 */
	private void updateCache() {
		
		// Get the user database.
		_userCache = User.getDb().list();
		
		// Sort the results.
		Collections.sort( _userCache );
		
	}

}
