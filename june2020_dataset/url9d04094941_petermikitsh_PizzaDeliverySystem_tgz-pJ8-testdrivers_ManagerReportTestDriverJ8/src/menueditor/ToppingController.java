package menueditor;

import java.util.ArrayList;
import java.util.Collections;

import menueditor.MenuEditorView.MenuEditorOutChan;
import menueditor.ToppingView.ToppingInChan;
import menueditor.ToppingView.ToppingOutChan;
import model.FoodItem;
import model.Topping;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the menu editor module.
 * In charge of editing pizza toppings.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class ToppingController extends GeneralController {
	
	/**
	 * Reference to containing view.
	 */
	MenuEditorViewGUI menuEditorView;
	
	/**
	 * The states of this controller.
	 */
	private enum ToppingControllerState implements ControllerState {
		
		CSDisplayingToppingList;
		
	}
	
	/**
	 * An existing topping that is being modified.
	 */
	private Topping _modifyingTopping;
	
	/**
	 * Indicates if we're modifying an existing topping.
	 */
	@SuppressWarnings("unused")
	private boolean _modifying;
	
	/**
	 * The cached list of toppings.
	 */
	private ArrayList<FoodItem> _toppingCache;
	
	/**
	 * The default constructor.
	 */
	public ToppingController( MenuEditorViewGUI menuEditorView ) {
		
		this.menuEditorView = menuEditorView;
		updateCache();
		
	}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying topping list state.
		this.active = true;
		gotoDisplayingToppingList();
		
	}

	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch( (ToppingControllerState) currentState) {
		
		case CSDisplayingToppingList:
			handleDisplayingToppingList( message, (ToppingInChan) channel );
			break;
		}
		
	}

	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		menuEditorView.displayString( message, MenuEditorOutChan.OCError );
		
	}
	
	/**
	 * Updates the locally-stored cache with a copy of the topping database.
	 */
	private void updateCache() {
		
		// Update the cache.
		_toppingCache = Topping.getDb().list();
		Collections.sort( _toppingCache );
		
	}
	
	/**
	 * Moves the controller into the DisplayingToppingList state.
	 */
	private void gotoDisplayingToppingList() {
		
		this.currentState = ToppingControllerState.CSDisplayingToppingList;
		
		updateCache();
		
		// Print the topping list.
		view.displayList( _toppingCache, ToppingOutChan.OCList );
		
		// Show instructions.
		view.displayString( " ", MenuEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( ToppingInChan.ICToppingData, false );
		
		view.setChannelEnabled( ToppingInChan.ICListDelete, true );
		view.setChannelEnabled( ToppingInChan.ICListModify, true );
		
	}
	
	/**
	 * Handles input in the DisplayingToppingList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingToppingList( String message, ToppingInChan channel) {
		
		switch( channel ) {
		
		case ICToppingData:
			
			if (message.isEmpty()) {
				handleInputError( "Topping name cannot be empty." );
			}
			
			else {
				
				if ( _modifyingTopping != null ) {
					
					Topping.getDb().remove( _modifyingTopping.getKey() );
					_modifyingTopping.setName( message );
					Topping.getDb().add( _modifyingTopping );
					
					// Clear the current topping.
					_modifyingTopping = null;
					
				}
				else {
					
					Topping newTopping = new Topping( message );
					Topping.getDb().add( newTopping );
					
				}
				
			}
			
			gotoDisplayingToppingList();
			break;
			
		case ICListDelete:
		case ICListModify:
			
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
		
		default:
			break;
			
		}
		
	}
	
	/**
	 * Handles input for the listmodify/listdelete channels.  (to avoid repeated code).
	 */
	private void handleDeleteOrModify( String message, ToppingInChan channel ) {
		
		// The message should be a list index.  Get the list index
		// and delete/modify the food item from the order at that index.
		int index = -1;
		try { 
			index = Integer.parseInt( message );
			if( index < 0 || 
					index >= _toppingCache.size() ) {
				handleInputError("Index out of range. Please try again.");
			}
		} catch( NumberFormatException e ) {
			handleInputError("The input was invalid. Please try again.");
		}

		if( index >= 0 && index < _toppingCache.size() ) {

			if( channel == ToppingInChan.ICListDelete ) {
				
				Topping.getDb().remove( _toppingCache.get( index ).getKey() );
				gotoDisplayingToppingList();
				
			} else if( channel == ToppingInChan.ICListModify ) {

				_modifyingTopping = (Topping) _toppingCache.get(index);
				
				view.setChannelEnabled(ToppingInChan.ICCancel, false);
				view.displayObject( _modifyingTopping, ToppingOutChan.OCEditTopping );
				
				gotoDisplayingToppingList();

			}

		}
		
	}
	
}
