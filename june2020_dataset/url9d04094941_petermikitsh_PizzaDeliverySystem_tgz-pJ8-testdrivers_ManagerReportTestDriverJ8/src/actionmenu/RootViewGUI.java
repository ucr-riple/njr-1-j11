package actionmenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.User.UserPermissions;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the Root module.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class RootViewGUI extends RootView
	implements GeneralViewGUI {

	/**
	 * The name of the new order button.
	 */
	private static final String NEW_ORDER_BUTTON_NAME = "<html>Create New Order</html>";
	
	/**
	 * The name of the customer database button.
	 */
	private static final String CUSTOMER_BUTTON_NAME = "<html>Customer Database</html>";

	/**
	 * The name of the current orders button.
	 */
	private static final String CURRENT_ORDERS_BUTTON_NAME = "<html>Current Orders</html>";
	
	/**
	 * The name of the administrative options button.
	 */
	private static final String ADMIN_BUTTON_NAME = "<html><center>Administrative Options</center></html>";

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/** 
	 * The button that goes to the New Order component
	 */
	private JButton _newOrderButton;

	/** 
	 * The button that goes to the Customer Editor component
	 */
	private JButton _customerDatabaseButton;

	/** 
	 * The button that goes to the Current Orders component
	 */
	private JButton _currentOrdersButton;

	/** 
	 * The button that goes to the Admin component
	 */
	private JButton _adminButton;
	
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public RootViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Main Menu" );
		
		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		_mainPanel.add( centerPanel, BorderLayout.CENTER );
		
		// Create the buttons.
		Font buttonFont = new Font( "SansSerif", Font.BOLD, 18 );
		
		_newOrderButton = new JButton( RootViewGUI.NEW_ORDER_BUTTON_NAME );
		_newOrderButton.addActionListener( this );
		_newOrderButton.setFont( buttonFont );
		
		_customerDatabaseButton = new JButton( RootViewGUI.CUSTOMER_BUTTON_NAME );
		_customerDatabaseButton.addActionListener( this );
		_customerDatabaseButton.setFont( buttonFont );
		
		_currentOrdersButton = new JButton( RootViewGUI.CURRENT_ORDERS_BUTTON_NAME );
		_currentOrdersButton.addActionListener( this );
		_currentOrdersButton.setFont( buttonFont );
		
		_adminButton = new JButton( RootViewGUI.ADMIN_BUTTON_NAME );
		_adminButton.addActionListener( this );
		_adminButton.setFont( buttonFont );
		
		// Now add the buttons.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridLayout( 2 , 2 , 10 , 10 ) );
		buttonPanel.setPreferredSize( new Dimension( 500, 300 ) );
		buttonPanel.add( _newOrderButton );
		buttonPanel.add( _currentOrdersButton );
		buttonPanel.add( _customerDatabaseButton );
		buttonPanel.add( _adminButton );
		
		centerPanel.add( buttonPanel, new GridBagConstraints() );
		_mainPanel.add( centerPanel );

	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// We shouldn't ever have to display any strings here.
		
		_mainPanel.updateUI();

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {

		// We don't need to do anything here.
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// We don't need to do anything here.

	}

	/**
	 * @return This GUI's content panel.
	 */
	public JPanel getMainPanel() {
		return _mainPanel;
	}
	
	/**
	 * Set the panel visible or invisible.
	 */
	public void setVisible( boolean visible ) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );
		
		// Hide the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( !visible );
		
		// Show the log out button if this is going to be displayed.
		PDSViewManager.setLogOutButtonEnabled( visible );
		
	}

	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		switch( (RootInChan)inChannel ) {
		
		case ICMenuOption:
			
			// Enable or disable the buttons.
			_newOrderButton.setEnabled( enabled );
			_customerDatabaseButton.setEnabled( enabled );
			_currentOrdersButton.setEnabled( enabled );
			
			// Disable the admin button if the user is not an admin.
			if ( PizzaDeliverySystem.currentUser != null)
			_adminButton.setEnabled( PizzaDeliverySystem.currentUser.getPermissions() == UserPermissions.ADMIN  );
			
			break;
			
		}
		
	}

	/**
	 * Called when a button is pressed.
	 * 
	 * @param aEvent 
	 */
	public void actionPerformed(ActionEvent aEvent) {

		// Check if the event was caused by a button.
		if( aEvent.getSource() instanceof JButton ) {

			JButton sourceButton = (JButton)aEvent.getSource();

			// Check the button for its name.
			if( sourceButton.getText().equals( 
					RootViewGUI.NEW_ORDER_BUTTON_NAME ) ) {

				controller.respondToInput( RootView.NEW_ORDER_KEY ,
											RootInChan.ICMenuOption );

			} else if( sourceButton.getText().equals( 
					RootViewGUI.CUSTOMER_BUTTON_NAME ) ) {

				controller.respondToInput( RootView.VIEW_CUSTOMER_KEY,
						RootInChan.ICMenuOption );

			} else if( sourceButton.getText().equals( 
					RootViewGUI.CURRENT_ORDERS_BUTTON_NAME ) ) {

				controller.respondToInput( RootView.VIEW_ORDERS_KEY,
						RootInChan.ICMenuOption );

			} else if( sourceButton.getText().equals( 
					RootViewGUI.ADMIN_BUTTON_NAME ) ) {

				controller.respondToInput( RootView.ADMIN_KEY,
						RootInChan.ICMenuOption );

			} else if( sourceButton.getText().equals(
					PDSViewManager.LOG_OUT_BUTTON_NAME ) ) {
				
				controller.respondToInput( RootView.LOG_OUT_KEY,
						RootInChan.ICLogOut );
				
			}
			
			if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {
				
				// Do nothing.

			} 
			
		}

	}

}