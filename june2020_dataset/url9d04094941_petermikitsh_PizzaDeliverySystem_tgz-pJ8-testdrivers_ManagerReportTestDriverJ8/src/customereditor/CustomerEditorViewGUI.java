package customereditor;

import gui.hinttextfield.HintTextField;
import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;
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
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the Customer module.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class CustomerEditorViewGUI extends CustomerEditorView
	implements GeneralViewGUI, ZScrollTableDelegate, KeyListener, FocusListener {

	/**
	 * The name of the search clear button.
	 */
	private static final String SEARCH_CLEAR_BUTTON_NAME = "Clear Search";
	
	/**
	 * The name of the update button.
	 */
	private static final String UPDATE_BUTTON_NAME = "Update";

	/** 
	 * The name of the add button (when editing customer data).
	 */
	private static final String ADD_BUTTON_NAME = "Add";
	
	/**
	 * The name of the update cancel button.
	 */
	private static final String CANCEL_BUTTON_NAME = "Clear";

	/**
	 * The name of the yes button.
	 */
	private static final String YES_BUTTON_NAME = "Yes";

	/**
	 * The name of the new no button.
	 */
	private static final String NO_BUTTON_NAME = "No";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/**
	 * The panel that displays the customer's detail.
	 */
	private JPanel _customerDetailPanel;

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
	//private JTextField _customerAddressField;
	private JComboBox _customerAddressField;
	
	/**
	 * The button for submitting new or update customer data.
	 */
	private JButton _submitButton;
	
	/**
	 * The button for canceling a customer edit.
	 */
	private JButton _cancelButton;

	/**
	 * The panel that holds the confirm messages.
	 */
	private JPanel _confirmPanel;

	/**
	 * The label that displays confirm messages.
	 */
	private JLabel _confirmLabel;
	
	/**
	 * The textfield that holds the search.
	 */
	private HintTextField _searchField;
	
	/** 
	 * The button that clears the search button.
	 */
	private JButton _searchClearButton;
	
	/**
	 * A single header cell that stores the search bar and clear button. 
	 */
	private static ZScrollTableCell _customerHeaderCell;

	/** 
	 * The table that displays the list of customers.
	 */
	private ZScrollTable _customerTable;
	
	/**
	 * Indicates if the table can be modified.
	 */
	private boolean _canModifyTable;
	
	/**
	 * Indicates if entries on the table can be deleted.
	 */
	private boolean _canDeleteFromTable;
	
	/**
	 * The panel that holds the content on the right side of the window.
	 */
	private JPanel _rightPanel;

	/**
	 * The label that prints error data.
	 */
	private JLabel _errorLabel;	
	
	/**
	 * Indicates if an error was encountered in the course of sending data to the controller.
	 */
	private boolean _errorOccurred;

	/**
	 * A local list of displayed customers.
	 */
	private ArrayList<Customer> _customerCache;

	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public CustomerEditorViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Customer Database" );

		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );

		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		GridBagConstraints centerConst = new GridBagConstraints();
		_mainPanel.add( centerPanel, BorderLayout.CENTER );

		// Create panels for the leftside of the center panel.
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new GridBagLayout() );
		GridBagConstraints leftConst = new GridBagConstraints();
		centerPanel.add( leftPanel, centerConst );

		// Initialize the search stuff.
		_searchField = new HintTextField( "Search", 10 );
		_searchField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_searchField.addKeyListener( this );
		_searchClearButton = new JButton( CustomerEditorViewGUI.SEARCH_CLEAR_BUTTON_NAME );
		_searchClearButton.addActionListener( this );
		
		// Create a table to which the list of customers will be written for the left panel.
		JPanel customerTablePanel = new JPanel();
		customerTablePanel.setLayout( new GridBagLayout() );
		_customerTable = new ZScrollTable( this );
		_customerTable.setPreferredSize( new Dimension( CustomerTableCell.CELL_WIDTH, 340 ) );
		customerTablePanel.add( _customerTable, new GridBagConstraints()  );
		leftPanel.add( customerTablePanel, leftConst );
		
		// Create the confirm panel and add it to a container in the left panel.
		JPanel confirmContainer = new JPanel();
		confirmContainer.setPreferredSize( new Dimension( CustomerTableCell.CELL_WIDTH, 80 ) );
		confirmContainer.setLayout( new GridBagLayout() );
		this.createConfirmPanel();
		_confirmPanel.setPreferredSize( new Dimension( CustomerTableCell.CELL_WIDTH, 70 ) );
		confirmContainer.add( _confirmPanel, new GridBagConstraints() );
		leftConst.gridy = 1;
		leftPanel.add( confirmContainer, leftConst );
		
		// Create a panel for the right side.
		_rightPanel = new JPanel();
		_rightPanel.setLayout( new GridBagLayout() );
		centerConst.gridx = 1;
		centerConst.ipadx = 30;
		centerPanel.add( _rightPanel, centerConst );
		GridBagConstraints rightConst = new GridBagConstraints();

		// Create the customer detail panel and add it to the right panel.
		this.createCustomerDetailPanel();
		_rightPanel.add( _customerDetailPanel, rightConst );
		
	}

	/**
	 * Initializes the customer detail panel.
	 */
	private void createCustomerDetailPanel() {

		// Create a new panel.
		_customerDetailPanel = new JPanel();
		_customerDetailPanel.setLayout( new GridBagLayout() );
		_customerDetailPanel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Customer Details" ) );
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.ipadx = 100;

		// Add the text fields to the CDP.
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout( new GridBagLayout() );
		
		// Name field.
		JLabel customerNameLabel = new JLabel( "Name: ", JLabel.TRAILING );
		customerNameLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_customerNameField = new JTextField( 6 );
		 
		// Create the phone field.
		JLabel customerPhoneLabel = new JLabel( "Phone: ", JLabel.TRAILING );
		customerPhoneLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		try {
			_customerPhoneField = new JFormattedTextField( new MaskFormatter( "(###) ###-####" ) );
		}
		catch( ParseException e ) {
			_customerPhoneField = new JFormattedTextField();
		}
		_customerPhoneField.setColumns( 6 );
		
		// Create the address field with instruction text as the first element.
		JLabel customerAddressLabel = new JLabel( "Address: ", JLabel.TRAILING );
		customerAddressLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		Vector<Address> addressArray = new Vector<Address>( Address.getDb().list() );
		addressArray.add( 0, new Address( "(Select an address.)", 0 ) );
		_customerAddressField = new JComboBox( addressArray );
		_customerAddressField.addActionListener( this );
		_customerAddressField.setBackground( Color.WHITE );
		
		_customerNameField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_customerPhoneField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_customerAddressField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		
		_customerNameField.setPreferredSize( new Dimension( 150, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_customerPhoneField.setPreferredSize( new Dimension( 280, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_customerAddressField.setPreferredSize( new Dimension( 145, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		
		_customerNameField.addKeyListener( this );
		_customerPhoneField.addKeyListener( this );
		_customerPhoneField.addFocusListener( this );
		
		GridBagConstraints custConst = new GridBagConstraints();
		custConst.fill = GridBagConstraints.BOTH;
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
		_customerDetailPanel.add( fieldPanel, constraints );
		
		// Add buttons to the CDP.
		JPanel buttonPanel = new JPanel();
		_submitButton = new JButton( CustomerEditorViewGUI.ADD_BUTTON_NAME );
		_submitButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_submitButton.addActionListener( this );
		buttonPanel.add( _submitButton );
		_cancelButton = new JButton( CustomerEditorViewGUI.CANCEL_BUTTON_NAME );
		_cancelButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_cancelButton.addActionListener( this );
		buttonPanel.add( _cancelButton );
		constraints.gridy = 1;
		_customerDetailPanel.add( buttonPanel, constraints );

	}

	/**
	 * Initializes the confirmation panel.
	 */
	private void createConfirmPanel() {

		// Create a new panel.
		_confirmPanel = new JPanel();
		_confirmPanel.setLayout( new GridLayout( 2, 1 ) );

		// Add a panel with the confirmation message.
		JPanel confirmMessagePanel = new JPanel();
		_confirmLabel = new JLabel();
		confirmMessagePanel.add( _confirmLabel );

		// Create a panel with the two buttons: "yes" and "no".
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new FlowLayout() );
		JButton yesButton = new JButton( CustomerEditorViewGUI.YES_BUTTON_NAME );
		yesButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		yesButton.addActionListener( this );
		buttonPanel.add( yesButton );
		JButton noButton = new JButton( CustomerEditorViewGUI.NO_BUTTON_NAME );
		noButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		noButton.addActionListener( this );
		buttonPanel.add( noButton );

		_confirmPanel.add( confirmMessagePanel );
		_confirmPanel.add( buttonPanel );

	}
	
	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// Check the channel.
		switch( (CustomerEditorOutChan)outChannel ) {

		case OCError:
			_errorLabel.setText( message );
			_errorOccurred = true;
			break;
			
		case OCConfirm:
			_confirmLabel.setText( message );
			break;

		}

		_mainPanel.updateUI();

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {

		// Check if the object is a customer.
		if( object instanceof Customer ) {

			// Send the customer's data to the text displays.
			Customer cust = (Customer)object;
			_customerNameField.setText( cust.getName() );
			_customerPhoneField.setText( cust.getPhoneNumber() );
			_customerAddressField.setSelectedItem( cust.getStreetAddress() );
			
			// Set the add/update text to "update".
			_submitButton.setText( CustomerEditorViewGUI.UPDATE_BUTTON_NAME ); 

		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// Store the list locally and update the table.
		if( (CustomerEditorOutChan)outChannel == CustomerEditorOutChan.OCList ) {
			_customerCache = (ArrayList<Customer>) list;
			_customerTable.updateTable();
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
		
		// Kill the header cell if we're going invisible.
		if( !visible ) {
			_customerHeaderCell = null;
		}
		
		// Validate the input.
		if( visible ) {
			this.validateInput();
		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		// Clear the error output whenever the input methods are changed.
		_errorLabel.setText( " " );

		switch( (CustomerEditorInChan)inChannel ) {
		
		case ICListModify:
			
			// Update the local variable.
			_canModifyTable = enabled;
			_customerTable.updateTable();
			break;
			
		case ICListDelete:
			
			// Update the local variable.
			_canDeleteFromTable = enabled;
			_customerTable.updateTable();
			break;
			
		case ICCustomerData:
			
			_customerNameField.setText( "" );
			_customerPhoneField.setText( "" );
			_customerAddressField.setSelectedIndex( 0 );

			// Reset the button text to "Add".  
			_submitButton.setText( CustomerEditorViewGUI.ADD_BUTTON_NAME );
			
			break;
			
		case ICSearchTerm:
			
			// Enable or disable the search bar and button.(Initialize it first if needed).
			_searchField.setEnabled( enabled );
			_searchClearButton.setEnabled( enabled );
			break;
			
		case ICConfirm:
			
			// Hide or show the confirmation panel.
			_confirmPanel.setVisible( enabled );
			
			// Switch the inputs on or off.
			_customerNameField.setEditable( !enabled );
			_customerPhoneField.setEditable( !enabled );
			_customerAddressField.setEnabled( !enabled );
			_submitButton.setEnabled( !enabled );
			_cancelButton.setEnabled( !enabled );
			
			break;
			
		case ICBack:
			
			// Show the back button.
			PDSViewManager.setBackButtonEnabled( enabled );
			break;
			
		}
		
	}


	//// ZSCROLLTABLE DELEGATE METHODS


	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getNumberOfCells(gui.scrolltable.ZScrollTable)
	 */
	public int getNumberOfCells(ZScrollTable table) {
		return _customerCache.size();
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
		
		// Create the header if needed.
		if( _customerHeaderCell == null ) {
			
			// Create a search bar and clear button.
			JPanel searchPanel = new JPanel();
			searchPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );	
			searchPanel.add( _searchField );
			searchPanel.add( _searchClearButton );

			// Create the cell and add the search panel.
			_customerHeaderCell = new ZScrollTableCell();
			_customerHeaderCell.setPreferredSize( 
					new Dimension( CustomerTableCell.CELL_WIDTH, 40 ) );
			_customerHeaderCell.setLayout( new GridBagLayout() );
			_customerHeaderCell.add( searchPanel, new GridBagConstraints() );
			
		}
		
		return _customerHeaderCell;

	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return _searchField.getText().isEmpty() ? "Database is empty." : "No customers found.";
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {		
		
		// Create a new CustomerTableCell from the customer at the given
		// index.
		CustomerTableCell cell = new CustomerTableCell( _customerCache.get( index ) );

		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
		
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return CustomerTableCell.CELL_WIDTH;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		return CustomerTableCell.CELL_HEIGHT;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		
		// Send the modify message to the controller if we should.
		if( _canModifyTable ) {
			controller.respondToInput( "" + index, CustomerEditorInChan.ICListModify );
		}
		
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#canDeleteCell(gui.scrolltable.ZScrollTable, int)
	 */
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return _canDeleteFromTable;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasDeleted(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasDeleted(ZScrollTable table, int index) {
		// Send the delete message to the controller.
		controller.respondToInput( "" + index, CustomerEditorInChan.ICListDelete );
	}
	
	
	/////// ACTION LISTENER METHODS


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
			if( sourceButton.getText().equals( CustomerEditorViewGUI.UPDATE_BUTTON_NAME ) || 
				sourceButton.getText().equals( CustomerEditorViewGUI.ADD_BUTTON_NAME ) ) {
				
				// Send the "new customer" message if the add button is displayed.
				if( sourceButton.getText().equals( CustomerEditorViewGUI.ADD_BUTTON_NAME ) ) {
					controller.respondToInput( CustomerEditorView.ADD_KEY, CustomerEditorInChan.ICMenuOption );
				}
				
				_errorOccurred = false; 

				// Send the current values of the name, phone, and address
				// fields to the controller.
				controller.respondToInput( _customerNameField.getText(),
						CustomerEditorInChan.ICCustomerData );
				
				// Only proceed if nothing was printed to error output.
				if( !_errorOccurred ) {
					
					// Remove non-integer characters from the phone number.
					String phone = _customerPhoneField.getText();
					String digits = "";
					for (int i = 0; i < phone.length(); i++ ) {
						if( (int) phone.charAt(i) >= 48 &&
								(int) phone.charAt(i) <= 57 ) {
							digits += phone.charAt(i);
						}
					}
					controller.respondToInput( digits, CustomerEditorInChan.ICCustomerData );
					
				} if( !_errorOccurred ) {
					
					controller.respondToInput( _customerAddressField.getSelectedItem().toString(),
							CustomerEditorInChan.ICCustomerData );
					
				}
				
				// Clear the search bar and trigger a key event.
				_searchField.clearTextField();
				this.keyReleased( new KeyEvent( _searchField, 0, 0, 0, 0, '\0' ) );

			} else if( sourceButton.getText().equals( 
					CustomerEditorViewGUI.CANCEL_BUTTON_NAME ) ) {

				controller.respondToInput( CustomerEditorView.CANCEL_KEY,
						CustomerEditorInChan.ICConfirm );
				
				// Disable the add button again.
				validateInput();
				
				// Clear the search bar and trigger a key event.
				_searchField.clearTextField();
				this.keyReleased( new KeyEvent( _searchField, 0, 0, 0, 0, '\0' ) );

			} else if( sourceButton.getText().equals( 
					CustomerEditorViewGUI.YES_BUTTON_NAME ) ) {

				controller.respondToInput( CustomerEditorView.CONFIRM_YES_KEY,
						CustomerEditorInChan.ICConfirm );
				
				// Clear the search bar and trigger a key event.
				_searchField.clearTextField();
				this.keyReleased( new KeyEvent( _searchField, 0, 0, 0, 0, '\0' ) );

			} else if( sourceButton.getText().equals( 
					CustomerEditorViewGUI.NO_BUTTON_NAME ) ) {

				controller.respondToInput( CustomerEditorView.CONFIRM_NO_KEY,
						CustomerEditorInChan.ICConfirm);

			} else if( sourceButton.getText().equals( 
					CustomerEditorViewGUI.SEARCH_CLEAR_BUTTON_NAME ) ) {

				// Clear the search bar and trigger a key event.
				_searchField.clearTextField();
				this.keyReleased( new KeyEvent( _searchField, 0, 0, 0, 0, '\0' ) );

			} else if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {

				// Send the back message.
				controller.respondToInput( CustomerEditorView.BACK_KEY,
						CustomerEditorInChan.ICBack );

			}

		} else {
			validateInput();
		}

	}
	
	
	////// KEY LISTENER METHODS
	
	
	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {

		// Get the text from the search field and send it to the controller.
		if( e.getSource() == _searchField ) {
			controller.respondToInput( _searchField.getText().trim(), 
										CustomerEditorInChan.ICSearchTerm );
		}
		// Validate the input.
		this.validateInput();
		
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * Validates the input of the fields.  Disables the submit button if something
	 *  is wrong.
	 */
	private void validateInput() {
		
		boolean validInput = true;
		
		// Make sure the name is nonempty.
		if( _customerNameField.getText().isEmpty() ) {
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
		if( _customerAddressField.getSelectedIndex() == 0 ) {
			validInput = false;
		}
		
		// Now, disable or enable the submit button based on this.
		_submitButton.setEnabled( validInput );
		
	}

	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent fEvent ) {
	
		if( fEvent.getSource() == _customerPhoneField ) {
			
			// Move the cursor to front of this textfield if it has nothing in it.
			String phone = _customerPhoneField.getText();
			String digits = "";
			for (int i = 0; i < phone.length(); i++ ) {
				if( (int) phone.charAt(i) >= 48 &&
						(int) phone.charAt(i) <= 57 ) {
					digits += phone.charAt(i);
				}
			}
			if( digits.isEmpty() ) {
				_customerPhoneField.moveCaretPosition( 0 );
			}
			
		}
		
	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent arg0) {}

}
