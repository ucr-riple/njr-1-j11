package menueditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import main.PDSViewManager;
import viewcontroller.GeneralViewGUI;

/**
 * The GUI view for the menu editor module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class MenuEditorViewGUI extends MenuEditorView 
	implements GeneralViewGUI {

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The JPanel which holds the menu item stuff.
	 */
	private JPanel _menuItemsPanel;
	
	/**
	 * The JPanel which holds the topping stuff.
	 */
	private JPanel _toppingsPanel;
	
	/**
	 * The label that prints instructions.
	 */
	private JLabel _instructionsLabel;
	
	/**
	 * Indicates if an error was encountered in the course of sending data to the controller.
	 */
	@SuppressWarnings("unused")
	private boolean _errorOccurred;
	
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public MenuEditorViewGUI() {
		
		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Edit Menu" );
		
		// Create a label to hold the instructions.
		_instructionsLabel = new JLabel();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.add( _instructionsLabel );
		_mainPanel.add( instructionsPanel , BorderLayout.NORTH );

		JLabel keyLabel = new JLabel( "PT = Preparation Time; " +
				"CT = Cooking Time; OU = Oven Space Units" );
		JPanel keyPanel = new JPanel();
		keyPanel.add( keyLabel );
		_mainPanel.add( keyPanel, BorderLayout.SOUTH );
		
		// Initialize the views for the menu items and toppings
		MenuItemViewGUI menuItemGUI = new MenuItemViewGUI();
		ToppingViewGUI toppingGUI = new ToppingViewGUI();
		
		// Create the center panel.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
		_menuItemsPanel = menuItemGUI.getMainPanel();
		centerPanel.add( _menuItemsPanel );
		constraints.gridx = 1;
		constraints.ipadx = 50;
		_toppingsPanel = toppingGUI.getMainPanel();
		constraints.anchor = GridBagConstraints.PAGE_START;
		centerPanel.add( _toppingsPanel, constraints );
		_mainPanel.add( centerPanel, BorderLayout.CENTER );
		
		// Initialize the controllers for the menu items and toppings
		MenuItemController menuItemCont = new MenuItemController( this );
		ToppingController toppingCont = new ToppingController( this );
		menuItemCont.setView( menuItemGUI );
		toppingCont.setView( toppingGUI );
		menuItemGUI.setController( menuItemCont );
		toppingGUI.setController( toppingCont );
		menuItemCont.enterInitialState();
		toppingCont.enterInitialState();
		
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
			if( sourceButton.getText().equals( PDSViewManager.BACK_BUTTON_NAME ) ) {
				
				// Send the back message.
				controller.respondToInput( MenuEditorView.BACK_KEY,
						MenuEditorInChan.ICBack );
				
			}
			
		}
		
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
	public void setVisible(boolean visible) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );
		
		// Show the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( visible );
		
	}

	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		switch( (MenuEditorInChan) inChannel ) {
		
		case ICBack:
			
			// Show the back button.
			PDSViewManager.setBackButtonEnabled( enabled );
			break;
			
		}
			
	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {
		
		// Check the channel.
		switch( (MenuEditorOutChan) outChannel ) {
		
		case OCInstructions:
			_instructionsLabel.setText( message );
			break;

		case OCError:
			_errorOccurred = true;
			break;
		
		}
		
		_mainPanel.updateUI();
		
	}

	@Override
	public void displayObject(Object object, OutputChannel outChannel) {}

	@Override
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {}
	
}
