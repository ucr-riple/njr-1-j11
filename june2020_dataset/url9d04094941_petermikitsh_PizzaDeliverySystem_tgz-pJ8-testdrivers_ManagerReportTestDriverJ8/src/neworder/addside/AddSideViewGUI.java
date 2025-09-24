package neworder.addside;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import main.PDSViewManager;
import model.FoodItem;
import model.SideFoodItem;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the Add Side module.
 * 
 * @author 	Casey Klimkowsky	cek3403@g.rit.edu
 */
public class AddSideViewGUI extends AddSideView
implements GeneralViewGUI, KeyListener {

	/**
	 * The name of the "add" button.
	 */
	private static final String ADD_BUTTON_NAME = "Done";

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/**
	 * The scrollpane that holds the panel of pickers.
	 */
	private JScrollPane _pickerScroll;

	/**
	 * The panel that displays all of the pickers.
	 */
	private JPanel _pickerPanel;

	/**
	 * The width of the side list.
	 */
	private static final int SIDE_PANEL_WIDTH = 500;

	/**
	 * The done button.
	 */
	private JButton _doneButton;
	
	/**
	 * The list of picker panels.
	 */
	private ArrayList<SidePickerPanel> _pickerList;
	
	/**
	 * The label that shows instructions.
	 */
	private JLabel _instructionsLabel;

	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public AddSideViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "New Order: Add Side" );
		
		// Add the instructions label.
		_instructionsLabel = new JLabel();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.add( _instructionsLabel );
		_mainPanel.add( instructionsPanel, BorderLayout.NORTH );
		
		// Add stuff to the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		_mainPanel.add( centerPanel, BorderLayout.CENTER );

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets =  new Insets( 5, 5, 5, 5 );
		constraints.ipadx = 25;
		constraints.ipady = 25;

		// Add the scrollpanel that holds the panel of picker.
		_pickerPanel = new JPanel();
		_pickerPanel.setLayout( new GridLayout( 0, 3, 15, 10 ) );
		_pickerPanel.setPreferredSize( 
				new Dimension( AddSideViewGUI.SIDE_PANEL_WIDTH, 
						( SidePickerPanel.HEIGHT + 70 ) * (SideFoodItem.getDb().size() / 3) ) );
		
		_pickerScroll = new JScrollPane( _pickerPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		_pickerScroll.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Select a Side" ) );
		_pickerScroll.setPreferredSize( 
				new Dimension( AddSideViewGUI.SIDE_PANEL_WIDTH + 45, 300 ) );
		constraints.gridx = 0;
		constraints.gridy = 0;
		centerPanel.add( _pickerScroll, constraints );

		// Add the panel with the quantity field and the submit button.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new FlowLayout() );

		_doneButton = new JButton( AddSideViewGUI.ADD_BUTTON_NAME );
		_doneButton.addActionListener( this );
		_doneButton.setPreferredSize( new Dimension( 150, 50 ) );
		_doneButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );

		bottomPanel.add( _doneButton );

		constraints.gridy = 1;
		centerPanel.add( bottomPanel, constraints );
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		if( outChannel == AddSideOutChan.OCInstructions ) {
			_instructionsLabel.setText( message );
		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {

		// Nothing to do here.

	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// Clear the panel.
		_pickerPanel.removeAll();

		// First, obtain and sort the list of available food items.
		ArrayList<FoodItem> itemList = SideFoodItem.getDb().list();
		Collections.sort( itemList );
		
		// First, create a new array of picker panels.
		_pickerList = new ArrayList<SidePickerPanel>();
		for( int i = 0; i < itemList.size(); i++ ) {
		
			// Create a new picker.
			SidePickerPanel spPanel = new SidePickerPanel( (SideFoodItem)itemList.get(i),
															(Integer)list.get(i) );
			spPanel.addKeyListener( this );

			// Add the picker to the array...
			_pickerList.add( spPanel );

			// ... and to the content panel.
			JPanel pickerContainer = new JPanel();
			pickerContainer.add( spPanel );
			_pickerPanel.add( pickerContainer );

		}

		// Update the scroll panel.
		_pickerScroll.updateUI();

	}

	/**
	 * @return This GUI's content panel.
	 */
	public JPanel getMainPanel() {
		return _mainPanel;
	}
	
	/**
	 * Called when this view will be displayed.  Does nothing.
	 */
	public void onEnter() {}

	/**
	 * Set the panel visible or invisible.
	 */
	public void setVisible( boolean visible ) {

		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );

		// Show the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( visible );

	}

	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		// Do nothing.
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
					AddSideViewGUI.ADD_BUTTON_NAME ) ) {

				// Compile the list of quantities into a single string.
				String quantityString = "";
				for( int i = 0; i < _pickerList.size(); i++ ) {
					quantityString = quantityString + _pickerList.get(i).getQuantity() + " ";
				}

				// Now send the quantity string to the controller.
				controller.respondToInput( quantityString,
						AddSideInChan.ICQuantities );

			} else if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {
				
				// Go back.
				controller.respondToInput( AddSideView.BACK_KEY ,
						AddSideInChan.ICBack );

			} 

		}

	}


	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {

		// Validate all of the input fields.
		this.validateInput();
		
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {}
	
	/**
	 * Makes sure all of the inputs are correct.
	 */
	private void validateInput() {
		
		boolean validInput = true;
		
		// Check each picker.
		for( SidePickerPanel picker : _pickerList ) {
			
			// Check the text field for validity.
			try { 
				int quantity = picker.getQuantity();
				if( quantity < 0 || quantity > 999 ) {
					validInput = false;
				}
			} catch( NumberFormatException exc ) {
				validInput = false;
			}
					
		}
		
		// Enable or disable the done button.
		_doneButton.setEnabled( validInput) ;
		
	}

}
