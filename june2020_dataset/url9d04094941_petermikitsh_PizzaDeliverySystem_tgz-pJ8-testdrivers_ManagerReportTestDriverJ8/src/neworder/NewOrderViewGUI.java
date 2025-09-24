package neworder;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;

import main.PDSViewManager;
import model.Address;
import model.Customer;
import model.FoodItem;
import model.PizzaFoodItem;
import model.SideFoodItem;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the Root module.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class NewOrderViewGUI extends NewOrderView
	implements GeneralViewGUI, ZScrollTableDelegate, KeyListener {

	/**
	 * The name of the "ok" button.
	 */
	private static final String OK_BUTTON_NAME = "OK";

	/**
	 * The name of the "add pizza" button.
	 */
	private static final String PIZZA_BUTTON_NAME = "Pizza";

	/**
	 * The name of the "add side" button.
	 */
	private static final String SIDE_BUTTON_NAME = "Side";
	
	/**
	 * The name of the submit button.
	 */
	private static final String SUBMIT_BUTTON_NAME = "Submit";
	
	/**
	 * The name of the submit button.
	 */
	private static final String CONTINUE_BUTTON_NAME = "Continue";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The label that prints instructions.
	 */
	private JLabel _instructionsLabel;
	
	/**
	 * THe label that prints error data.
	 */
	private JLabel _errorLabel;
	
	/**
	 * The panel that holds the customer input panel.
	 */
	private JPanel _customerInputContainer;
	
	/**
	 * The textfield that displays a customer's name.
	 */
	private JTextField _customerNameField;

	/**
	 * The textfield that displays a customer's phone number.
	 */
	private JFormattedTextField _customerPhoneField;

	/**
	 * The textfield that displays a customer's address.
	 */
	private JComboBox _customerAddressField;
	
	/** 
	 * The button that is used for form submission.
	 */
	private JButton _okButton;
	
	/**
	 * The panel that displays the order.
	 */
	private JPanel _orderDisplayContainer;

	/**
	 * The label that displays the customer's name.
	 */
	private JLabel _customerNameLabel;

	/**
	 * The label that displays the customer's phone.
	 */
	private JLabel _customerPhoneLabel;

	/**
	 * The label that displays the customer's address.
	 */
	private JLabel _customerAddressLabel;
	
	/**
	 * The table of food items.
	 */
	private ZScrollTable _foodItemTable;
	
	/**
	 * Stores the cells in the foodItemTable.
	 */
	private ArrayList<FoodItemTableCell> _foodItemTableCells;

	/**
	 * Indicates if objects on the table can be deleted.
	 */
	private boolean _canDeleteFromTable;

	/**
	 * Indicates if objects on the table can be modified.
	 */
	private boolean _canModifyTable;
	
	/**
	 * Displays the current subtotal (no tax) of the order.
	 */
	private JLabel _subTotalLabel;
	
	/**
	 * Displays the current tax of the order.
	 */
	private JLabel _taxLabel;
	
	/**
	 * Displays the current total price of the order.
	 */
	private JLabel _totalLabel;
	
	/**
	 * The container that holds that New Pizza and New Side buttons and confirmation panel.
	 */
	private JPanel _addButtonContainer;
	
	/**
	 * The panel that contains that New Pizza and New Side buttons.
	 */
	private JPanel _addButtonPanel;
	
	/**
	 * The panel that contains information on the confirmation screen.
	 */
	private JPanel _confirmPanel;
	
	/**
	 * Displays the estimated time to delivery.
	 */
	private JLabel _estimatedTimeLabel;
	
	/**
	 * The button for submitting the order.
	 */
	private JButton _submitButton;
	
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public NewOrderViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "New Order" );

		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );
		
		// Create a label for instructions.
		_instructionsLabel = new JLabel( "", JLabel.CENTER );

		// Initialize the customer fields.
		initializeCustomerFields();
		
		// Create an "ok" button.
		_okButton = new JButton( NewOrderViewGUI.OK_BUTTON_NAME );
		_okButton.setEnabled(false);
		_okButton.addActionListener( this );
		
		// Now the order display container.
		_customerInputContainer = new JPanel();
		initializeOrderDisplayContainer();
		
		// Initialize the panel that displays confirmation information.
		_confirmPanel = new JPanel();
		_confirmPanel.setLayout( new GridLayout( 2, 1, 0, 10 ) );
		_confirmPanel.setPreferredSize( new Dimension( 200, 200 ) );
		
		JLabel confirmLabel = new JLabel( "<html><center>The order has been forwarded " +
				"to the kitchen.</center></html>", JLabel.CENTER );
		confirmLabel.setFont( new Font( "SansSerif", Font.BOLD, 14 ) );
		
		JPanel estTimePanel = new JPanel();
		JLabel estTimeTitleLabel = new JLabel( "Estimated Delivery Time: " );
		estTimeTitleLabel.setFont( new Font( "SansSerif", Font.BOLD, 14 ) );
        _estimatedTimeLabel = new JLabel();
        _estimatedTimeLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        estTimePanel.add( estTimeTitleLabel );
        estTimePanel.add( _estimatedTimeLabel );
        _confirmPanel.add( confirmLabel );
		_confirmPanel.add( estTimePanel );	
		
	}
	
	/**
	 * Creates the order display panel.
	 */
	private void initializeOrderDisplayContainer() {
		
		// Create the container, which has the input panel in its center.
		_orderDisplayContainer = new JPanel();
		_orderDisplayContainer.setLayout( new GridBagLayout() );

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets =  new Insets( 5, 5, 5, 5 );
		constraints.ipadx = 7;
		constraints.ipady = 7;

		// Create the customer display panel.
		JPanel customerDisplayContainer = new JPanel();
		customerDisplayContainer.setLayout( new GridBagLayout() );
		customerDisplayContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Customer Information" ) );
		JPanel customerDisplayPanel = new JPanel();
		customerDisplayPanel.setLayout( new GridLayout( 3,1 ) );
		_customerNameLabel = new JLabel();
		_customerPhoneLabel = new JLabel();
		_customerAddressLabel = new JLabel();
		_customerNameLabel.setFont( new Font( "SansSerif", Font.BOLD, 16 ) );
		_customerPhoneLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
		_customerAddressLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
		customerDisplayPanel.add( _customerNameLabel );
		customerDisplayPanel.add( _customerPhoneLabel );
		customerDisplayPanel.add( _customerAddressLabel );
		customerDisplayContainer.add( customerDisplayPanel );
		
		// Add the customer display panel. 
		constraints.gridx = 0;
        constraints.gridy = 0;
		_orderDisplayContainer.add( customerDisplayContainer, constraints );
		
		// Create the food item table.
		JPanel tableContainer = new JPanel();
		tableContainer.setLayout( new GridBagLayout() );
		tableContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Order Information" ) );
		_foodItemTable = new ZScrollTable( this );
		_foodItemTable.setPreferredSize( new Dimension( FoodItemTableCell.CELL_WIDTH, 300 ) );
		tableContainer.add( _foodItemTable, new GridBagConstraints() );
		
		// Add the table.
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER; //end row
        constraints.gridheight = 2;
        constraints.ipadx = 75;
		_orderDisplayContainer.add( tableContainer, constraints );
		
		// Create the panel with the new pizza and new side button.
		_addButtonContainer = new JPanel();
		_addButtonContainer.setLayout( new GridBagLayout() );
		_addButtonContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Add to Order" ) );
		_addButtonPanel = new JPanel();
		_addButtonPanel.setLayout( new GridLayout( 2, 1, 0, 10 ) );
		_addButtonPanel.setPreferredSize( new Dimension( 200, 200 ) );
		JButton _newPizzaButton = new JButton( NewOrderViewGUI.PIZZA_BUTTON_NAME );
		JButton _newSideButton = new JButton( NewOrderViewGUI.SIDE_BUTTON_NAME );
		_newPizzaButton.addActionListener( this );
		_newSideButton.addActionListener( this );
		Font buttonFont = new Font( "SansSerif", Font.BOLD, 20 );
		_newPizzaButton.setFont( buttonFont );
		_newSideButton.setFont( buttonFont );
		_addButtonPanel.add( _newPizzaButton );
		_addButtonPanel.add( _newSideButton );
		
		// Add the new button panel.
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        _orderDisplayContainer.add( _addButtonContainer, constraints );
        
        // Create the price labels.
        JPanel priceContainer = new JPanel();
        priceContainer.setLayout( new GridBagLayout() );
        
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout( new GridBagLayout() );
        GridBagConstraints priceConst = new GridBagConstraints();
        
        JLabel subTotalTitleLabel = new JLabel( "Subtotal:  ", JLabel.TRAILING );
        subTotalTitleLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        _subTotalLabel = new JLabel( "", JLabel.TRAILING );
        _subTotalLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        
        JLabel taxTitleLabel = new JLabel( "Sales Tax:  ", JLabel.TRAILING );
        taxTitleLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        _taxLabel = new JLabel( "", JLabel.TRAILING );
        _taxLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
   
        JLabel totalTitleLabel = new JLabel( "Total:  ", JLabel.TRAILING );
        totalTitleLabel.setFont( new Font( "SansSerif", Font.BOLD, 14 ) );
        _totalLabel = new JLabel( "", JLabel.TRAILING );
        _totalLabel.setFont( new Font( "SansSerif", Font.BOLD, 14 ) );
        
        priceConst.fill = GridBagConstraints.BOTH;
        priceConst.ipady = 2;
		priceConst.gridx = 0;
		priceConst.gridy = 0;
		priceConst.anchor = GridBagConstraints.EAST;
		pricePanel.add( subTotalTitleLabel, priceConst );
		priceConst.gridx = 1;
		priceConst.gridy = 0;
		priceConst.anchor = GridBagConstraints.WEST;
		pricePanel.add( _subTotalLabel, priceConst );
		priceConst.gridx = 0;
		priceConst.gridy = 1;
		priceConst.anchor = GridBagConstraints.EAST;
		pricePanel.add( taxTitleLabel, priceConst );
		priceConst.gridx = 1;
		priceConst.gridy = 1;
		priceConst.anchor = GridBagConstraints.WEST;
		pricePanel.add( _taxLabel, priceConst );
		priceConst.gridx = 0;
		priceConst.gridy = 2;
		priceConst.anchor = GridBagConstraints.EAST;
		pricePanel.add( totalTitleLabel, priceConst );
		priceConst.gridx = 1;
		priceConst.gridy = 2;
		priceConst.weightx = 0.75;
		priceConst.anchor = GridBagConstraints.WEST;
		pricePanel.add( _totalLabel, priceConst );
		
		priceContainer.add( pricePanel, new GridBagConstraints() );
        
        // Create the submit button. 
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout( new FlowLayout( FlowLayout.TRAILING ) );
		_submitButton = new JButton( NewOrderViewGUI.SUBMIT_BUTTON_NAME );
		_submitButton.addActionListener( this );
		_submitButton.setPreferredSize( new Dimension( 150, 50 ) );
		submitPanel.add( _submitButton );
		
        // Create and add a panel for the price and submit button.
        JPanel priceSubmitPanel = new JPanel();
        priceSubmitPanel.setLayout( new GridLayout( 1, 2 ) );
        priceSubmitPanel.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        priceSubmitPanel.add( priceContainer );
        priceSubmitPanel.add( submitPanel );
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
	    constraints.ipadx = 50;
		_orderDisplayContainer.add( priceSubmitPanel, constraints );

	}

	/**
	 * Creates the customer information panel.
	 * 
	 * @param withNameAndAddress Indicates if the name and address panels should 
	 *  		visible.
	 */
	private void initializeCustomerInputContainer( boolean withNameAndAddress ) {
		
		// Create the container, which has the input panel in its center.
		_customerInputContainer.removeAll();
		_customerInputContainer.setLayout( new GridBagLayout() );
		JPanel customerInputPanel = new JPanel();
		customerInputPanel.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Add the instructions label.
		constraints.insets = new Insets( 10, 10, 10, 10 );
		customerInputPanel.add( _instructionsLabel, constraints );

		if( withNameAndAddress ) {

			// Add the text fields to the panel.
			JPanel fieldPanel = new JPanel();
			fieldPanel.setLayout( new GridBagLayout() );

			JLabel customerPhoneLabel = new JLabel( "Phone:  ", JLabel.TRAILING );
			customerPhoneLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );

			JLabel customerNameLabel = new JLabel( "Name:  ", JLabel.TRAILING );
			customerNameLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );

			JLabel customerAddressLabel = new JLabel( "Address:  ", JLabel.TRAILING );
			customerAddressLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );

			GridBagConstraints custConst = new GridBagConstraints();
			custConst.fill = GridBagConstraints.BOTH;
			custConst.insets = new Insets( 2, 2, 2, 2 );

			custConst.gridx = 0;
			custConst.gridy = 0;
			custConst.anchor = GridBagConstraints.EAST;
			fieldPanel.add( customerNameLabel, custConst );
			custConst.gridx = 1;
			custConst.gridy = 0;
			custConst.anchor = GridBagConstraints.WEST;
			fieldPanel.add( _customerNameField, custConst );

			custConst.gridx = 0;
			custConst.gridy = 1;
			custConst.anchor = GridBagConstraints.EAST;
			fieldPanel.add( customerPhoneLabel, custConst );
			custConst.gridx = 1;
			custConst.gridy = 1;
			custConst.anchor = GridBagConstraints.WEST;
			fieldPanel.add( _customerPhoneField, custConst );

			custConst.gridx = 0;
			custConst.gridy = 2;
			custConst.anchor = GridBagConstraints.EAST;
			fieldPanel.add( customerAddressLabel, custConst );
			custConst.gridx = 1;
			custConst.gridy = 2;
			custConst.weightx = 0.75;
			custConst.anchor = GridBagConstraints.WEST;
			fieldPanel.add( _customerAddressField, custConst );

			constraints.gridy = 1;
			constraints.ipady = 0;
			customerInputPanel.add( fieldPanel, constraints );

		} else {

			// Add the text fields to the panel.
			JPanel fieldPanel = new JPanel();
			fieldPanel.setLayout( new GridBagLayout() );

			JLabel customerPhoneLabel = new JLabel( "Phone:  ", JLabel.TRAILING );
			customerPhoneLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );

			GridBagConstraints custConst = new GridBagConstraints();
			custConst.fill = GridBagConstraints.BOTH;
			custConst.insets = new Insets( 2, 2, 2, 2 );

			custConst.gridx = 0;
			custConst.gridy = 0;
			custConst.anchor = GridBagConstraints.EAST;
			fieldPanel.add( customerPhoneLabel, custConst );
			custConst.gridx = 1;
			custConst.gridy = 0;
			custConst.ipadx = 130;
			custConst.anchor = GridBagConstraints.WEST;
			fieldPanel.add( _customerPhoneField, custConst );

			constraints.gridy = 1;
			constraints.ipady = 0;
			customerInputPanel.add( fieldPanel, constraints );

		}

		// Add the "ok" button.
		JPanel okButtonPanel = new JPanel();
		okButtonPanel.add( _okButton );
		constraints.gridy = 2;
		customerInputPanel.add( okButtonPanel, constraints );
		
		_customerInputContainer.add( customerInputPanel, new GridBagConstraints() );
		
	}
	
	/**
	 * Creates the customer name, phone, and address fields.
	 */
	private void initializeCustomerFields() {

		// Name field.
		_customerNameField = new JTextField( 6 );

		// Phone field.
		try {
			_customerPhoneField = new JFormattedTextField( new MaskFormatter( "(###) ###-####" ) );
		}
		catch( ParseException e ) {
			_customerPhoneField = new JFormattedTextField();
		}
		_customerPhoneField.setColumns( 6 );

		// Create the address field with instruction text as the first element.
		Vector<Address> addressArray = new Vector<Address>( Address.getDb().list() );
		addressArray.add( 0, new Address( "(Select an address.)", 0 ) );
		_customerAddressField = new JComboBox( addressArray );
		_customerAddressField.addActionListener( this );
		_customerAddressField.setBackground( Color.WHITE );

		_customerNameField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_customerPhoneField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_customerAddressField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );

		_customerNameField.setPreferredSize( new Dimension( 230, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_customerPhoneField.setPreferredSize( new Dimension( 230, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_customerAddressField.setPreferredSize( new Dimension( 230, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );

		_customerNameField.addKeyListener( this );
		_customerPhoneField.addKeyListener( this );
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// Check the channel.
		switch( (NewOrderOutChan)outChannel ) {
	
		case OCInstructions:
			_instructionsLabel.setText( message );
			break;

		case OCError:
			_errorLabel.setText( message );
			break;
			
		case OCSubTotalDisplay:
			_subTotalLabel.setText( message );
			break;
			
		case OCTaxDisplay:
			_taxLabel.setText( message );
			break;
			
		case OCTotalDisplay:
			_totalLabel.setText( message );
			break;
			
		case OCDeliveryTimeDisplay:
			_estimatedTimeLabel.setText( message );
			break;

		}

		_mainPanel.updateUI();

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {

		// The object should be a Customer.  Put its data into the labels.
		if( object instanceof Customer ) {
			
			Customer cust = (Customer)object;
			_customerNameLabel.setText( cust.getName() );
			_customerPhoneLabel.setText(cust.getFormattedPhoneNumber() );
			_customerAddressLabel.setText(cust.getStreetAddress().getLocation() );
			
			// Also send the data to the forms.
			_customerNameField.setText( cust.getName() );
			_customerPhoneField.setText( cust.getPhoneNumber() );
			_customerAddressField.setSelectedItem( cust.getStreetAddress() );
			
			validateInput();
			
		}
				
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// Store the list locally and update the table.
		if( (NewOrderOutChan)outChannel == NewOrderOutChan.OCFoodItemList ) {
			
			// If the list is empty, disable the submit button.
			_submitButton.setEnabled( !list.isEmpty() );
			
			// Clear the cell array.
			_foodItemTableCells = new ArrayList<FoodItemTableCell>();
			
			// Create pizza cells for the pizza items.
			for( T object : list ) {
				
				if( object instanceof PizzaFoodItem ) {
					_foodItemTableCells.add( new PizzaFoodItemTableCell( (PizzaFoodItem)object ) );
				}
				
			}
			
			// Recover the side quantity map: start with a sorted side
			// food item list.
			ArrayList<FoodItem> itemList = SideFoodItem.getDb().list();
			Collections.sort( itemList );
			
			// Create the map and initialize all of the values to zero.
			HashMap<SideFoodItem, Integer> qMap = new HashMap<SideFoodItem, Integer>();
			for( FoodItem sfi : SideFoodItem.getDb().list() ) {
				qMap.put( (SideFoodItem)sfi, 0 );
			}
			
			// Go through the SFIs in the given list and increment the corresponding
			// element of the map.
			for( T object : list ) {
				if( object instanceof SideFoodItem ) {
					int value = qMap.get( (SideFoodItem)object );
					qMap.put( (SideFoodItem)object, value + 1 );
				}
			}
			
			// Now create a cell for all of the keys of the map with nonzero 
			// values.
			for( FoodItem sfi : itemList ) {
				int quantity = qMap.get( sfi );
				if( quantity > 0 ) {
					_foodItemTableCells.add( new SideFoodItemTableCell( (SideFoodItem)sfi, quantity  ) );
				}
			}
			
			_foodItemTable.updateTable();
			
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
	public void setVisible( boolean visible ) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );
		
		// Show the back button if this is going to be displayed.
		PDSViewManager.setBackButtonEnabled( visible );
		
		if( visible ) {
			// Send the refresh signal.
			controller.respondToInput( NewOrderView.REFRESH_KEY, NewOrderInChan.ICMenuOption );
		}
		
	}


	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled(viewcontroller.GeneralView.InputChannel, boolean)
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		// Clear the error output whenever the input methods are changed.
		_errorLabel.setText( " " );
		
		switch( (NewOrderInChan)inChannel ) {
		
		case ICCustomerName:
		case ICCustomerAddress:
			
			// Reinitialize the customer info panel with or without the name
			// and address fields.
			_customerNameField.setVisible( enabled );
			_customerAddressField.setVisible( enabled );
			initializeCustomerInputContainer( enabled );
			
			// There is no break statement here intentionally.
		
		case ICCustomerPhone:
			
			// Show or hide the customer input container and order display container.
			if( enabled ) {
				_mainPanel.remove( _orderDisplayContainer );
				_mainPanel.add( _customerInputContainer);
			} else {
				_mainPanel.remove( _customerInputContainer );
				_mainPanel.add( _orderDisplayContainer );
			}
			
			break;
			
		case ICMenuOption:
			
			// Change the text on the submit button, and also hide/ show the
			// add side/pizza buttons.
			_addButtonContainer.removeAll();
			if( enabled ) {
				_submitButton.setText( SUBMIT_BUTTON_NAME );
				_addButtonContainer.add( _addButtonPanel );
				_addButtonContainer.setBorder( BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
						"Add to Order" ) );
			} else {
				_submitButton.setText( CONTINUE_BUTTON_NAME );
				_addButtonContainer.setBorder( BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
						"Confirmation" ) );
				_addButtonContainer.remove( _addButtonPanel );
				_addButtonContainer.add( _confirmPanel );
			}
			
			break;
			
		case ICListDelete:
			
			// Make it so we can or cannot delete things from the table.
			_canDeleteFromTable = enabled;
			break;
			
		case ICListModify:
			// Make it so we can or cannot modify things
			_canModifyTable = enabled;
			break;

		case ICBack:
			PDSViewManager.setBackButtonEnabled( enabled );
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
					NewOrderViewGUI.OK_BUTTON_NAME ) ) {
				
				_okButton.setEnabled( false );
				
				// Send data from the customer info fields.
				if( _customerNameField.isVisible() ) {
					controller.respondToInput( _customerNameField.getText(),
											NewOrderInChan.ICCustomerName );
				}
				
				// Remove non-integer characters from the phone number.
				String phone = _customerPhoneField.getText();
				String digits = "";
				for (int i = 0; i < phone.length(); i++ ) {
					if( (int) phone.charAt(i) >= 48 &&
							(int) phone.charAt(i) <= 57 ) {
						digits += phone.charAt(i);
					}
				}
				controller.respondToInput( digits, NewOrderInChan.ICCustomerPhone );
				
				if( _customerAddressField.isVisible() ) {
					controller.respondToInput( ((Address) _customerAddressField.getSelectedItem()).getLocation(),
							NewOrderInChan.ICCustomerAddress);
				}
				
			} else if( sourceButton.getText().equals( 
					NewOrderViewGUI.PIZZA_BUTTON_NAME ) ) {

				controller.respondToInput( NewOrderView.ADD_PIZZA_KEY,
						NewOrderInChan.ICMenuOption );

			} else if( sourceButton.getText().equals( 
					NewOrderViewGUI.SIDE_BUTTON_NAME ) ) {

				controller.respondToInput( NewOrderView.ADD_SIDE_KEY,
						NewOrderInChan.ICMenuOption );

			} else if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {
				
				controller.respondToInput( NewOrderView.BACK_KEY ,
						NewOrderInChan.ICBack );

			} else if( sourceButton.getText().equals( 
					NewOrderViewGUI.SUBMIT_BUTTON_NAME ) ) {

				controller.respondToInput( NewOrderView.DONE_KEY,
						NewOrderInChan.ICMenuOption);

			} else if( sourceButton.getText().equals( 
					NewOrderViewGUI.CONTINUE_BUTTON_NAME ) ) {

				controller.respondToInput( NewOrderView.DONE_KEY,
						NewOrderInChan.ICMenuOption);

			} 

		}
		else if( aEvent.getSource() instanceof JComboBox ) {
			
			JComboBox sourceBox = (JComboBox) aEvent.getSource();
			
			Address selectedAddress = (Address) sourceBox.getSelectedItem();
			_customerAddressField.setSelectedItem( selectedAddress.getLocation() ); 
			validateInput();
			
		}

	}
	
	
	////  ZSCROLLTABLE DELEGATE METHODS

	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getNumberOfCells(gui.scrolltable.ZScrollTable)
	 */
	public int getNumberOfCells(ZScrollTable table) {
		return _foodItemTableCells.size();
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellSpacing(gui.scrolltable.ZScrollTable)
	 */
	public int getCellSpacing(ZScrollTable table) {
		return 1;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getTableBGColor(gui.scrolltable.ZScrollTable)
	 */
	public Color getTableBGColor(ZScrollTable table) {
		return new Color( 100, 100, 100 );
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getHeaderCell(gui.scrolltable.ZScrollTable)
	 */
	public ZScrollTableCell getHeaderCell( ZScrollTable table ) {
		return null;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "There are no items in the order.";
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {		
		
		// Create a new CustomerTableCell from the customer at the given
		// index.
		FoodItemTableCell cell = _foodItemTableCells.get( index );

		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
		
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return FoodItemTableCell.CELL_WIDTH;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		
		// Get a calculation based on the contents of the food item.
		return _foodItemTableCells.get( index ).getHeight();
		
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		
		// Send the modify message to the controller.
		if( _canModifyTable ) {
			controller.respondToInput( "" + index, NewOrderInChan.ICListModify );
		}
		
	}


	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#canDeleteCell(gui.scrolltable.ZScrollTable, int)
	 */
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return _canDeleteFromTable && _foodItemTableCells.get( index ) instanceof PizzaFoodItemTableCell;
	}


	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasDeleted(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasDeleted(ZScrollTable table, int index) {
		
		// Send the delete message to the controller.
		controller.respondToInput( "" + index, NewOrderInChan.ICListDelete );
		
	}
	

	/////// KEY LISTENER METHODS

	
	public void keyPressed(KeyEvent e) {

		// Check for the enter key.
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ENTER) {

			// Pretend that an action was performed.
			this.actionPerformed( new ActionEvent( _okButton, 0, null ) );

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		this.validateInput();
	
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * Validates the input of the fields.  Disables the ok button if something
	 *  is wrong.
	 */
	private void validateInput() {
		
		boolean validInput = true;
		
		// Make sure the name is non-empty.
		if( _customerNameField.isVisible() && 
				_customerNameField.getText().isEmpty() ) {
			validInput = false;
		}
		
		// Make sure the phone number is valid.
		// Remove non-integer characters from the phone number.
		String phone = _customerPhoneField.getText();
		String digits = "";
		for (int i = 0; i < phone.length(); i++ ) {
			if( (int) phone.charAt(i) >= 48 &&
					(int) phone.charAt(i) <= 57 ) {
				digits += phone.charAt(i);
			}
		}
		
		if( !Customer.isValidPhoneNumber(digits) ) {
			validInput = false;
		}
		
		// Make sure the selected index of the dropdown box is non zero.
		if( _customerAddressField.isVisible() && 
				_customerAddressField.getSelectedIndex() == 0 ) {
			validInput = false;
		}
		
		// Now, disable or enable the submit button based on this.
		if( validInput ) {
			_okButton.setEnabled( true );
		} else {
			_okButton.setEnabled( false );
		}
		
	}

}
