package menueditor;

import java.util.ArrayList;
import java.util.Collections;

import menueditor.MenuEditorView.MenuEditorOutChan;
import menueditor.MenuItemView.MenuItemInChan;
import menueditor.MenuItemView.MenuItemOutChan;
import model.FoodItem;
import model.SideFoodItem;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

/**
 * The logic controller for the menu editor module.
 * In charge of editing menu items.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class MenuItemController extends GeneralController {

	/**
	 * Reference to containing view.
	 */
	MenuEditorViewGUI menuEditorView;
	
	/**
	 * The states of this controller.
	 */
	private enum MenuItemControllerState implements ControllerState {
		
		CSDisplayingMenuItemList;
		
	}
	
	/**
	 * An existing menu item that is being modified.
	 */
	private FoodItem _modifyingMenuItem;
	
	/**
	 * The menu item object to which new data will be added.
	 */
	private FoodItem _creatingMenuItem;
	
	/**
	 * Indicates if we're modifying an existing menu item.
	 */
	private boolean _modifying;
	
	/**
	 * The cached list of menu items, sorted by name.
	 */
	private ArrayList<FoodItem> _menuItemCache;
	
	/**
	 * The default constructor.
	 */
	public MenuItemController( MenuEditorViewGUI menuEditorView ) {
		
		this.menuEditorView = menuEditorView;
		updateCache();
		
	}
	
	/**
	 * @see controller.GeneralController#enterInitialState()
	 */
	public void enterInitialState() {
		
		// The initial state is the displaying menu item list state.
		this.active = true;
		gotoDisplayingMenuItemList();
		
	}

	/**
	 * @see controller.GeneralController#respondToInput(java.lang.String, view.GeneralView.InputChannel)
	 */
	public void respondToInput(String message, InputChannel channel) {
		
		// Check the current state, and call the appropriate handler method.
		switch( (MenuItemControllerState) currentState) {
		
		case CSDisplayingMenuItemList:
			handleDisplayingMenuItemList( message, 
					(MenuItemInChan) channel );
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
	 * Updates the locally-stored cache with a copy of the menu item database.
	 */
	private void updateCache() {
		
		// Update the cache.
		_menuItemCache = SideFoodItem.getDb().list();
		Collections.sort( _menuItemCache );
		
	}
	
	/**
	 * Moves the controller into the DisplayingMenuItemList state.
	 */
	private void gotoDisplayingMenuItemList() {
		
		this.currentState = MenuItemControllerState.CSDisplayingMenuItemList;
		
		updateCache();
		
		// Print the menu item list.
		view.displayList( _menuItemCache, MenuItemOutChan.OCList );
		
		// Show instructions.
		view.displayString( " ", MenuEditorOutChan.OCInstructions );
		
		view.setChannelEnabled( MenuItemInChan.ICMenuItemName, true);
		view.setChannelEnabled( MenuItemInChan.ICMenuItemPrice, true);
		view.setChannelEnabled( MenuItemInChan.ICMenuItemPT, true);
		view.setChannelEnabled( MenuItemInChan.ICMenuItemCT, true);
		view.setChannelEnabled( MenuItemInChan.ICMenuItemOU, true);
		view.setChannelEnabled( MenuItemInChan.ICListDelete, true );
		view.setChannelEnabled( MenuItemInChan.ICListModify, true );
		
	}
	
	/**
	 * Handles input in the DisplayingMenuItemList state.
	 * 
	 * @param message  The string message.
	 * @param channel  The channel from which the input was received.
	 */
	private void handleDisplayingMenuItemList( String message, MenuItemInChan channel) {
		
		switch( channel ) {
		
		case ICMenuItemName:
		case ICMenuItemPrice:
		case ICMenuItemPT:
		case ICMenuItemCT:
		case ICMenuItemOU:
			
			if (message.isEmpty()) {
				handleInputError( "Invalid input." );
			}
			
			else {
				
				if ( channel == MenuItemInChan.ICMenuItemName ) {
					if ( ! _modifying ) {
						_creatingMenuItem = new SideFoodItem();
						_creatingMenuItem.setName(message);
					}
					else {
						SideFoodItem.getDb().remove( _modifyingMenuItem.getKey() );
						_modifyingMenuItem.setName( message );
					}
				}
				else if ( channel == MenuItemInChan.ICMenuItemPrice ) {
					
					try {
						if ( ! _modifying ) {
							_creatingMenuItem.setPrice( Double.parseDouble( message ) );
						}
						else {
							_modifyingMenuItem.setPrice( Double.parseDouble( message ) );
						}
					} catch( NumberFormatException exc ) {
						handleInputError( "Invalid input." );
					}
					
				}
				else if ( channel == MenuItemInChan.ICMenuItemPT ) {
					
					try {
						if ( ! _modifying ) {
							_creatingMenuItem.setPrepTime( Integer.parseInt( message ) );
						}
						else {
							_modifyingMenuItem.setPrepTime( Integer.parseInt( message ) );
						}
					} catch( NumberFormatException exc ) {
						handleInputError( "Invalid input." );
					}
					
				}
				else if ( channel == MenuItemInChan.ICMenuItemCT ) {
					try {
						if ( ! _modifying ) {
							_creatingMenuItem.setCookTime( Integer.parseInt( message ) );
						}
						else {
							_modifyingMenuItem.setCookTime( Integer.parseInt( message ) );
						}
					} catch( NumberFormatException exc ) {
						handleInputError( "Invalid input." );
					}
					
				}
				else if ( channel == MenuItemInChan.ICMenuItemOU ) {
					
					try {
						if ( ! _modifying ) {
							_creatingMenuItem.setOvenSpaceUnits( Integer.parseInt( message ) );
							SideFoodItem.getDb().add( _creatingMenuItem );
							_creatingMenuItem = new SideFoodItem();
						}
						else {
							_modifyingMenuItem.setOvenSpaceUnits( Integer.parseInt( message ) );
							SideFoodItem.getDb().add( _modifyingMenuItem );
							_modifying = false;	
						}
					} catch( NumberFormatException exc ) {
						handleInputError( "Invalid input." );
					}
					
				}
					
			}

			gotoDisplayingMenuItemList();
			break;
			
		case ICListDelete:
		case ICListModify:
			
			// Pass to the handler method.
			handleDeleteOrModify( message, channel );
			break;
		
		case ICCancel:
			_modifying = false;
			break;
		
		default:
			//handleInputError("The input was invalid. Please try again.");
			break;
			
		}
		
	}
	
	/**
	 * Handles input for the listmodify/listdelete channels.  (to avoid repeated code).
	 */
	private void handleDeleteOrModify( String message, MenuItemInChan channel ) {
		
		// The message should be a list index.  Get the list index
		// and delete/modify the food item from the order at that index.
		int index = -1;
		try { 
			index = Integer.parseInt( message );
			if( index < 0 || 
					index >= _menuItemCache.size() ) {
				handleInputError("Index out of range. Please try again.");
			}
		} catch( NumberFormatException e ) {
			handleInputError("The input was invalid. Please try again.");
		}

		if( index >= 0 && index < _menuItemCache.size() ) {

			if( channel == MenuItemInChan.ICListDelete ) {
				
				FoodItem foodItemToRemove = _menuItemCache.get( index );
				
				if ( foodItemToRemove instanceof SideFoodItem ) {
					SideFoodItem.getDb().remove( foodItemToRemove.getKey() );
				}
				
				gotoDisplayingMenuItemList();
				
			} else if( channel == MenuItemInChan.ICListModify ) {

				_modifying = true;
				_modifyingMenuItem = (FoodItem) _menuItemCache.get(index);
				view.setChannelEnabled( MenuItemInChan.ICCancel, false );
				view.displayObject( _modifyingMenuItem, MenuItemOutChan.OCEditMenuItem );
				
				gotoDisplayingMenuItemList();

			}

		}
		
	}
	
}
