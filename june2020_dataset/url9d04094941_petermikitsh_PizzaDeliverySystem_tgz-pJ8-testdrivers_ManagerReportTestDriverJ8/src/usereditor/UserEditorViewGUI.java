package usereditor;

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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import main.PDSViewManager;
import model.User;
import model.User.UserPermissions;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the User module.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class UserEditorViewGUI extends UserEditorView
	implements GeneralViewGUI, ZScrollTableDelegate, KeyListener {
	
	/**
	 * The name of the update button.
	 */
	private static final String UPDATE_BUTTON_NAME = "Update";

	/** 
	 * The name of the add button (when editing User data).
	 */
	private static final String ADD_BUTTON_NAME = "Add";
	
	/**
	 * The name of the update cancel button.
	 */
	private static final String CANCEL_BUTTON_NAME = "Cancel";

	/**
	 * The name of the yes button.
	 */
	private static final String YES_BUTTON_NAME = "Yes";

	/**
	 * The name of the new no button.
	 */
	private static final String NO_BUTTON_NAME = "No";
	
	/**
	 * The name of the admin checkbox.
	 */
	private static final String ADMIN_CHECK_NAME = "Administrator";

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/**
	 * The panel that displays the user's detail.
	 */
	private JPanel _userDetailPanel;

	/**
	 * The textfield that displays a user's name.
	 */
	private JTextField _userNameField;

	/**
	 * The textfield that displays a user's login id
	 */
	private JTextField _userLoginIDField;

	/**
	 * The textfield that displays a user's password
	 */
	private JTextField _userPasswordField;
	
	/**
	 * The checkbox for determining if a user should be an admin or not.
	 */
	private JCheckBox _userAdminCheckbox;
	
	/**
	 * The button for submitting new or update user data.
	 */
	private JButton _submitButton;
	
	/**
	 * The button for canceling a user edit.
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
	 * The table that displays the list of users.
	 */
	private ZScrollTable _userTable;
	
	/**
	 * Indicates if the table can be modified.
	 */
	private boolean _canModifyTable;
	
	/**
	 * Indicates if entries on the table can be deleted.
	 */
	private boolean _canDeleteFromTable;

	/**
	 * The label that prints error data.
	 */
	private JLabel _errorLabel;	
	
	/**
	 * Indicates if an error was encountered in the course of sending data to the controller.
	 */
	private boolean _errorOccurred;

	/**
	 * A local list of displayed users.
	 */
	private ArrayList<User> _userCache;

	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public UserEditorViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "User Database" );

		// Create a label to hold the error output.
		_errorLabel = new JLabel();
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );

		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridLayout( 1, 2 ) );
		_mainPanel.add( centerPanel, BorderLayout.CENTER );

		// Create panels for the leftside of the center panel.
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new GridBagLayout() );
		GridBagConstraints leftConst = new GridBagConstraints();
		centerPanel.add( leftPanel );
		
		// Create a table to which the list of Users will be written for the left panel.
		JPanel UserTablePanel = new JPanel();
		UserTablePanel.setLayout( new GridBagLayout() );
		_userTable = new ZScrollTable( this );
		_userTable.setPreferredSize( new Dimension( UserTableCell.CELL_WIDTH, 340 ) );
		UserTablePanel.add( _userTable, new GridBagConstraints()  );
		leftPanel.add( UserTablePanel, leftConst );
		
		// Create the confirm panel and add it to a container in the left panel.
		JPanel confirmContainer = new JPanel();
		confirmContainer.setPreferredSize( new Dimension( UserTableCell.CELL_WIDTH, 80 ) );
		confirmContainer.setLayout( new GridBagLayout() );
		this.createConfirmPanel();
		_confirmPanel.setPreferredSize( new Dimension( UserTableCell.CELL_WIDTH, 70 ) );
		confirmContainer.add( _confirmPanel, new GridBagConstraints() );
		leftConst.gridy = 1;
		leftPanel.add( confirmContainer, leftConst );
		
		// Create a panel for the right side.
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout( new GridBagLayout() );
		centerPanel.add( rightPanel );
		GridBagConstraints rightConst = new GridBagConstraints();

		// Create the User detail panel and add it to the right panel.
		this.createUserDetailPanel();
		rightPanel.add( _userDetailPanel, rightConst );

	}

	/**
	 * Initializes the User detail panel.
	 */
	private void createUserDetailPanel() {

		// Create a new panel.
		_userDetailPanel = new JPanel();
		_userDetailPanel.setLayout( new GridBagLayout() );
		_userDetailPanel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"User Details" ) );
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.ipadx = 100;

		// Add the text fields to the CDP.
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout( new GridBagLayout() );
		
		// Name field.
		JLabel userNameLabel = new JLabel( "Name: ", JLabel.TRAILING );
		userNameLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_userNameField = new JTextField( 6 );
		
		// Login id field.
		JLabel userLoginIDLabel = new JLabel( "Login ID: ", JLabel.TRAILING );
		userLoginIDLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_userLoginIDField = new JTextField( 6 );
		
		// Password field.
		_userPasswordField = new JTextField( 6 );
		JLabel userPasswordLabel = new JLabel( "Password: ", JLabel.TRAILING );
		userPasswordLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		
		_userNameField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_userLoginIDField.setFont( new Font( "Monospaced", Font.PLAIN, 16 ) );
		_userPasswordField.setFont( new Font( "Monospaced", Font.PLAIN, 16 ) );
		
		_userNameField.setPreferredSize( new Dimension( 150, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_userLoginIDField.setPreferredSize( new Dimension( 150, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_userPasswordField.setPreferredSize( new Dimension( 150, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		
		_userNameField.addKeyListener( this );
		_userLoginIDField.addKeyListener( this );
		_userPasswordField.addKeyListener( this );
		
		GridBagConstraints userConst = new GridBagConstraints();
		userConst.fill = GridBagConstraints.BOTH;
		userConst.gridx = 0;
		userConst.gridy = 0;
		userConst.anchor = GridBagConstraints.EAST;
		fieldPanel.add( userNameLabel, userConst );
		userConst.gridx = 1;
		userConst.gridy = 0;
		userConst.anchor = GridBagConstraints.WEST;
		fieldPanel.add( _userNameField, userConst );
		userConst.gridx = 0;
		userConst.gridy = 1;
		userConst.anchor = GridBagConstraints.EAST;
		fieldPanel.add( userLoginIDLabel, userConst );
		userConst.gridx = 1;
		userConst.gridy = 1;
		userConst.anchor = GridBagConstraints.WEST;
		fieldPanel.add( _userLoginIDField, userConst );
		userConst.gridx = 0;
		userConst.gridy = 2;
		userConst.anchor = GridBagConstraints.EAST;
		fieldPanel.add( userPasswordLabel, userConst );
		userConst.gridx = 1;
		userConst.gridy = 2;
		userConst.weightx = 0.75;
		userConst.anchor = GridBagConstraints.WEST;
		fieldPanel.add( _userPasswordField, userConst );
		
		// Add the admin checkbox.
		JPanel checkboxPanel = new JPanel();
		_userAdminCheckbox = new JCheckBox( ADMIN_CHECK_NAME );
		checkboxPanel.add( _userAdminCheckbox );
		userConst.gridx = 0;
		userConst.gridy = 3;
		userConst.gridwidth = 2;
		userConst.anchor = GridBagConstraints.CENTER;
		fieldPanel.add( checkboxPanel, userConst );
		
		_userDetailPanel.add( fieldPanel, constraints );
		
		// Add buttons to the UDP.
		JPanel buttonPanel = new JPanel();
		_submitButton = new JButton( UserEditorViewGUI.ADD_BUTTON_NAME );
		_submitButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_submitButton.addActionListener( this );
		buttonPanel.add( _submitButton );
		_cancelButton = new JButton( UserEditorViewGUI.CANCEL_BUTTON_NAME );
		_cancelButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_cancelButton.addActionListener( this );
		buttonPanel.add( _cancelButton );
		constraints.gridy = 1;
		_userDetailPanel.add( buttonPanel, constraints );

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
		JButton yesButton = new JButton( UserEditorViewGUI.YES_BUTTON_NAME );
		yesButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		yesButton.addActionListener( this );
		buttonPanel.add( yesButton );
		JButton noButton = new JButton( UserEditorViewGUI.NO_BUTTON_NAME );
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
		switch( (UserEditorOutChan)outChannel ) {

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

		// Check if the object is a User.
		if( object instanceof User ) {

			// Send the User's data to the text displays.
			User user = (User)object;
			_userNameField.setText( user.getName() );
			_userLoginIDField.setText( user.getLoginId() );
			_userPasswordField.setText( user.getPassword() );
			_userAdminCheckbox.setSelected( user.getPermissions() == UserPermissions.ADMIN );
			
			// Set the add/update text to "update".
			_submitButton.setText( UserEditorViewGUI.UPDATE_BUTTON_NAME ); 

		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// Store the list locally and update the table.
		if( (UserEditorOutChan)outChannel == UserEditorOutChan.OCList ) {
			_userCache = (ArrayList<User>) list;
			_userTable.updateTable();
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

		switch( (UserEditorInChan)inChannel ) {
		
		case ICListModify:
			
			// Update the local variable.
			_canModifyTable = enabled;
			_userTable.updateTable();
			break;
			
		case ICListDelete:
			
			// Update the local variable.
			_canDeleteFromTable = enabled;
			_userTable.updateTable();
			break;
		
		case ICUserData:
			
			_userNameField.setText("");
			_userLoginIDField.setText("");
			_userPasswordField.setText("");
			_userAdminCheckbox.setSelected( false );
			
			// Reset the button text to "Add".  
			_submitButton.setText( UserEditorViewGUI.ADD_BUTTON_NAME );
			
			break;
			
		case ICConfirm:
			// Hide or show the confirmation panel.
			_confirmPanel.setVisible( enabled );
			
			_userNameField.setEditable( !enabled );
			_userLoginIDField.setEditable( !enabled );
			_userPasswordField.setEditable( !enabled );
			_userAdminCheckbox.setEnabled( !enabled );
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
		return _userCache.size();
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
		return "Database is empty.";
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {		
		// Create a new UserTableCell from the User at the given
		// index.
		UserTableCell cell = new UserTableCell( _userCache.get( index ) );

		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return UserTableCell.CELL_WIDTH;
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		return UserTableCell.CELL_HEIGHT;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		// Send the modify message to the controller if we should.
		if( _canModifyTable ) {
			controller.respondToInput( "" + index, UserEditorInChan.ICListModify );
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
		controller.respondToInput( "" + index, UserEditorInChan.ICListDelete );
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
			if( sourceButton.getText().equals( UserEditorViewGUI.UPDATE_BUTTON_NAME ) || 
				sourceButton.getText().equals( UserEditorViewGUI.ADD_BUTTON_NAME ) ) {
				
				// Send the "new user" message if the add button is displayed.
				if( sourceButton.getText().equals( UserEditorViewGUI.ADD_BUTTON_NAME ) ) {
					controller.respondToInput( UserEditorView.ADD_KEY, UserEditorInChan.ICMenuOption );
				}
				
				_errorOccurred = false; 

				// Send the current values of the name, phone, and address
				// fields to the controller.
				controller.respondToInput( _userNameField.getText(),
						UserEditorInChan.ICUserData );
				
				// Only proceed if nothing was printed to error output.
				if( !_errorOccurred ) {
					controller.respondToInput( _userLoginIDField.getText(),
							UserEditorInChan.ICUserData );
				} if( !_errorOccurred ) { 
					controller.respondToInput( _userPasswordField.getText(),
							UserEditorInChan.ICUserData );
				} if( !_errorOccurred ) {
					String message = _userAdminCheckbox.isSelected() ? "1" : "" ;
					controller.respondToInput( message, 
							UserEditorInChan.ICUserData );
				}

			} else if( sourceButton.getText().equals( 
					UserEditorViewGUI.CANCEL_BUTTON_NAME ) ) {

				controller.respondToInput( UserEditorView.CANCEL_KEY,
						UserEditorInChan.ICConfirm );
				
				// Disable the add button again.
				validateInput();

			} else if( sourceButton.getText().equals( 
					UserEditorViewGUI.YES_BUTTON_NAME ) ) {

				controller.respondToInput( UserEditorView.CONFIRM_YES_KEY,
						UserEditorInChan.ICConfirm );

			} else if( sourceButton.getText().equals( 
					UserEditorViewGUI.NO_BUTTON_NAME ) ) {

				controller.respondToInput( UserEditorView.CONFIRM_NO_KEY,
						UserEditorInChan.ICConfirm);

			} else if( sourceButton.getText().equals( 
					PDSViewManager.BACK_BUTTON_NAME ) ) {

				// Send the back message.
				controller.respondToInput( UserEditorView.BACK_KEY,
						UserEditorInChan.ICBack );

			}

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
		if( _userNameField.getText().isEmpty() ) {
			validInput = false;
		}

		// Make sure the login id field is nonempty.
		if( _userLoginIDField.getText().isEmpty() ) {
			validInput = false;
		}
		
		// Make sure the password field is nonempty.
		if( _userPasswordField.getText().isEmpty() ) {
			validInput = false;
		}
		
		// Now, disable or enable the submit button based on this.
		_submitButton.setEnabled( validInput );
		
	}

}
