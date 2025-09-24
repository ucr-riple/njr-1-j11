package menueditor;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import main.PDSViewManager;
import menueditor.MenuEditorView.MenuEditorOutChan;
import model.FoodItem;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the menu item module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class MenuItemViewGUI extends MenuItemView
	implements GeneralViewGUI, ZScrollTableDelegate, KeyListener {

	/**
	 * The name of the add button.
	 */
	private static final String ADD_BUTTON_NAME = "Add";
	
	/**
	 * The name of the update cancel button.
	 */
	private static final String CANCEL_BUTTON_NAME = "Cancel";
	
	/**
	 * The name of the update button.
	 */
	private static final String UPDATE_BUTTON_NAME = "Update";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The text field that displays a menu item's name.
	 */
	private JTextField _menuItemNameField;
	
	/**
	 * The text field that displays a menu item's price.
	 */
	private JTextField _menuItemPriceField;
	
	/**
	 * The text field that displays a menu item's preparation time.
	 */
	private JTextField _menuItemPrepTimeField;
	
	/**
	 * The text field that displays a menu item's cook time.
	 */
	private JTextField _menuItemCookTimeField;
	
	/**
	 * The text field that displays a menu item's oven space units.
	 */
	private JTextField _menuItemOvenSpaceUnitsField;
	
	/**
	 * The panel that displays a menu item's detail.
	 */
	private JPanel _menuItemDetailPanel;
	
	/**
	 * The panel that holds the menu item buttons.
	 */
	private JPanel _menuItemButtonPanel;
	
	/**
	 * The button that adds/updates menu items.
	 */
	private JButton _addButton;
	
	/**
	 * The button that cancels in-progress updates on a menu item.
	 */
	private JButton _cancelButton;
	
	/**
	 * The table that displays the list of menu itemss.
	 */
	private ZScrollTable _menuItemTable;
	
	/**
	 * A single header cell that stores the list title.
	 */
	private static ZScrollTableCell _menuItemHeaderCell;
	
	/**
	 * A local list of displayed menu items.
	 */
	private ArrayList<FoodItem> _menuItemCache;
	
	/**
	 * Indicates if an error was encountered in the course of sending data to the controller.
	 */
	private boolean _errorOccurred;
	
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public MenuItemViewGUI() {
			
		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 1;
		
		// Create the table that displays the list of menu items.
		_menuItemTable = new ZScrollTable( this );
		_menuItemTable.setPreferredSize( new Dimension( MenuItemTableCell.CELL_WIDTH, 295 ) );
		_mainPanel.add( _menuItemTable );
		
		// Create the bottom panel.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridBagLayout() );
		constraints.ipady = 15;
		_mainPanel.add( bottomPanel, constraints );
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout( new GridLayout( 2, 1, 0, 18 ) );
		JLabel menuItemNameLabel = new JLabel( "Name: ", JLabel.RIGHT );
		menuItemNameLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		JLabel menuItemPriceLabel = new JLabel( "Price ($): ", JLabel.RIGHT );
		menuItemPriceLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		textPanel.add( menuItemNameLabel );
		textPanel.add( menuItemPriceLabel );
		
		// Create the menu item detail panel.
		_menuItemDetailPanel = new JPanel();
		_menuItemDetailPanel.setLayout( new GridBagLayout() );
		
		_menuItemNameField = new JTextField( 15 );
		_menuItemNameField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_menuItemNameField.setPreferredSize( new Dimension( 180, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_menuItemNameField.addKeyListener( this );
		
		_menuItemPriceField = new JTextField( 15 );
		_menuItemPriceField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_menuItemPriceField.setPreferredSize( new Dimension( 180, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_menuItemPriceField.addKeyListener( this );
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout( new GridLayout( 2, 1, 0, 7 ) );
		fieldPanel.add( _menuItemNameField );
		fieldPanel.add( _menuItemPriceField );
		
		JLabel prepTimeLabel = new JLabel( "PT: " );
		prepTimeLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_menuItemPrepTimeField = new JTextField( 2 );
		_menuItemPrepTimeField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_menuItemPrepTimeField.setPreferredSize( new Dimension( 0, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_menuItemPrepTimeField.addKeyListener( this );
		JPanel prepTimePanel = new JPanel();
		prepTimePanel.setLayout( new FlowLayout() );
		prepTimePanel.add( prepTimeLabel );
		prepTimePanel.add( _menuItemPrepTimeField );
		
		JLabel cookTimeLabel = new JLabel( "CT: " );
		cookTimeLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_menuItemCookTimeField = new JTextField( 2 );
		_menuItemCookTimeField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_menuItemCookTimeField.setPreferredSize( new Dimension( 0, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_menuItemCookTimeField.addKeyListener( this );
		JPanel cookTimePanel = new JPanel();
		cookTimePanel.setLayout( new FlowLayout() );
		cookTimePanel.add( cookTimeLabel );
		cookTimePanel.add( _menuItemCookTimeField );
		
		JLabel spaceUnitsLabel = new JLabel( "OU: " );
		spaceUnitsLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		_menuItemOvenSpaceUnitsField = new JTextField( 2 );
		_menuItemOvenSpaceUnitsField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_menuItemOvenSpaceUnitsField.setPreferredSize( new Dimension( 0, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_menuItemOvenSpaceUnitsField.addKeyListener( this );
		JPanel ovenSpaceUnitsPanel = new JPanel();
		ovenSpaceUnitsPanel.setLayout( new FlowLayout() );
		ovenSpaceUnitsPanel.add( spaceUnitsLabel );
		ovenSpaceUnitsPanel.add( _menuItemOvenSpaceUnitsField );
		
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout( new GridBagLayout() );
		detailsPanel.add( prepTimePanel );
		detailsPanel.add( cookTimePanel );
		detailsPanel.add( ovenSpaceUnitsPanel );
		
		constraints.gridx = 1;
		constraints.ipady = 5;
		GridBagConstraints detailPanelConstraints = new GridBagConstraints();
		detailPanelConstraints.ipadx = 0;
		detailPanelConstraints.gridy = 0;
		_menuItemDetailPanel.add( textPanel, detailPanelConstraints );
		_menuItemDetailPanel.add( fieldPanel, detailPanelConstraints );
		detailPanelConstraints.gridy = 1;
		detailPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		detailPanelConstraints.gridwidth = 2;
		_menuItemDetailPanel.add( detailsPanel, detailPanelConstraints );
		constraints.gridx = 0;
		
		bottomPanel.add( _menuItemDetailPanel );
		
		// Create the button panel.
		_menuItemButtonPanel = new JPanel();
		_addButton = new JButton( MenuItemViewGUI.ADD_BUTTON_NAME);
		_addButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_addButton.addActionListener( this );
		_addButton.setPreferredSize( new Dimension( 100, 25 ) );
		_addButton.setEnabled( false );
		_menuItemButtonPanel.add( _addButton );
		_cancelButton = new JButton( MenuItemViewGUI.CANCEL_BUTTON_NAME );
		_cancelButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_cancelButton.addActionListener( this );
		_cancelButton.setPreferredSize( new Dimension( 100, 25 ) );
		_cancelButton.setEnabled( false );
		_menuItemButtonPanel.add( _cancelButton );
		constraints.ipady = 0;
		bottomPanel.add( _menuItemButtonPanel, constraints );
		
	}
	
	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		switch( (MenuItemInChan) inChannel ) {

		case ICCancel:
			_menuItemNameField.setText( "" );
			_menuItemPriceField.setText( "" );
			_menuItemPrepTimeField.setText( "" );
			_menuItemCookTimeField.setText( "" );
			_menuItemOvenSpaceUnitsField.setText( "" );
			_addButton.setText( MenuItemViewGUI.ADD_BUTTON_NAME );
			break;
			
		}
		
	}
	
	@Override
	public void displayString(String message, OutputChannel outChannel) {
		
		switch( (MenuEditorOutChan) outChannel ) {
		
		case OCError:
			//_errorLabel.setText( message );
			_errorOccurred = true;
			break;
		
		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		// Check if the object is a topping.
		if( object instanceof FoodItem ) {
			
			// Send the topping's data to the text displays.
			FoodItem side = (FoodItem) object;
			_menuItemNameField.setText( side.getName() );
			_menuItemPriceField.setText( "" + side.getFormattedPrice() );
			_menuItemPrepTimeField.setText( "" + side.getPrepTime() );
			_menuItemCookTimeField.setText( "" + side.getCookTime() );
			_menuItemOvenSpaceUnitsField.setText( "" + side.getOvenSpaceUnits() );
			
			// Set the add/update text to "update".
			_addButton.setEnabled(true);
			_cancelButton.setEnabled(true);
			_addButton.setText( MenuItemViewGUI.UPDATE_BUTTON_NAME );
			
		}
		
	}
	
	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		// Store the list locally and update the table.
		if( (MenuItemOutChan) outChannel == MenuItemOutChan.OCList ) {
			_menuItemCache = (ArrayList<FoodItem>) list;
			_menuItemTable.updateTable();
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
			JButton sourceButton = (JButton) aEvent.getSource();
			
			// Check the button for its name.
			if( sourceButton.getText().equals( MenuItemViewGUI.UPDATE_BUTTON_NAME ) || 
				sourceButton.getText().equals( MenuItemViewGUI.ADD_BUTTON_NAME ) ) {
				
				_errorOccurred = false;
				
				// Send the current values of the name field to the controller.
				controller.respondToInput( _menuItemNameField.getText(),
						MenuItemInChan.ICMenuItemName );
				
				if (! _errorOccurred ) {
					
					controller.respondToInput( _menuItemPriceField.getText(),
							MenuItemInChan.ICMenuItemPrice );
					controller.respondToInput( _menuItemPrepTimeField.getText(),
							MenuItemInChan.ICMenuItemPT );
					controller.respondToInput( _menuItemCookTimeField.getText(),
							MenuItemInChan.ICMenuItemCT );
					controller.respondToInput( _menuItemOvenSpaceUnitsField.getText(),
							MenuItemInChan.ICMenuItemOU );
					
					_menuItemNameField.setText( "" );
					_menuItemPriceField.setText( "" );
					_menuItemPrepTimeField.setText( "" );
					_menuItemCookTimeField.setText( "" );
					_menuItemOvenSpaceUnitsField.setText( "" );
					
				}
				
			}
		
			else if( sourceButton.getText().equals( MenuItemViewGUI.CANCEL_BUTTON_NAME ) ) {
				controller.respondToInput( MenuItemView.CANCEL_KEY,
						MenuItemInChan.ICCancel );
			}
			
			// Reset the buttons and clear the input fields.
			_addButton.setText( MenuItemViewGUI.ADD_BUTTON_NAME );
			_addButton.setEnabled(false);
			_cancelButton.setEnabled(false);
			_menuItemNameField.setText( "" );
			_menuItemPriceField.setText( "" );
			_menuItemPrepTimeField.setText( "" );
			_menuItemCookTimeField.setText( "" );
			_menuItemOvenSpaceUnitsField.setText( "" );
			
		}
	}
	
	/**
	 * Set the panel visible or invisible.
	 */
	public void setVisible(boolean visible) {
		
		// Pass the change on to the main panel.
		_mainPanel.setVisible( visible );
		
	}
	
	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		
		// Check the input fields.
		String nameString = _menuItemNameField.getText();
		String priceString = _menuItemPriceField.getText();
		String PTString = _menuItemPrepTimeField.getText();
		String CTString = _menuItemCookTimeField.getText();
		String OUString = _menuItemOvenSpaceUnitsField.getText();
				
		// Used to determine if the next character is valid.
		boolean validInput = true;
		
		// Check for valid price.
		try {
			double price = Double.parseDouble( priceString );
			if ( price <= 0 || price > 100 ) {
				validInput = false;
			}
		} catch ( NumberFormatException e ) {
			validInput = false;
		}
		
		// Check for valid preparation and cook times
		// and oven space units.
		try {
			int PT = Integer.parseInt( PTString );
			int CT = Integer.parseInt( CTString );
			int OU = Integer.parseInt( OUString );
			
			if ( PT < 0 || CT < 0 || OU < 0 ) {
				validInput = false;
			}
			
		} catch ( NumberFormatException e ) {
			validInput = false;
		}
		
		// ##.## and #.## validation
		if( priceString.contains(".") && validInput ) {
			
			int index = priceString.indexOf(".");
			
			if( priceString.substring(0, index).length() > 2 ||
				priceString.substring(index + 1, 
						priceString.length()).length() != 2 )  {
				validInput = false;
			}
			
		}
		
		// ## and # validation
		else if(! priceString.contains(".") && validInput) {
			
			if ( priceString.length() < 1 || priceString.length() > 2) {
				validInput = false;
			}
			
		}
		
		// Validate the cancel button.
		if ((! nameString.equals("")) || (! priceString.equals("")) ||
				(! PTString.equals("")) || (! CTString.equals("")) ||
				(! OUString.equals(""))) {
			_cancelButton.setEnabled(true);
		}
		else {
			_cancelButton.setEnabled(false);
		}
		
		// Validate the add button.
		if( validInput && (! nameString.isEmpty()) ) {
			_addButton.setEnabled(true);
		}
		else {
			_addButton.setEnabled(false);
		}
		
	}
	
	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {}

	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getNumberOfCells(gui.scrolltable.ZScrollTable)
	 */
	public int getNumberOfCells(ZScrollTable table) {
		return _menuItemCache.size();
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
	public ZScrollTableCell getHeaderCell(ZScrollTable table) {
		
		if ( _menuItemHeaderCell == null ) {
			
			// Create the header cell text.
			JLabel headerName = new JLabel( "Side Items" );
			headerName.setFont( new Font( "SansSerif", Font.PLAIN, 22 ) );
			
			// Create the cell and add the text.
			_menuItemHeaderCell = new ZScrollTableCell();
			_menuItemHeaderCell.setPreferredSize( 
					new Dimension( MenuItemTableCell.CELL_WIDTH, 40 ) );
			_menuItemHeaderCell.setLayout( new GridBagLayout() );
			_menuItemHeaderCell.add( headerName );
			
		}
		
		return _menuItemHeaderCell;
		
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "There are no sides on the menu.";
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {
		
		// Create a new ToppingTableCell from the topping at the given index.
		MenuItemTableCell cell = new MenuItemTableCell( _menuItemCache.get( index ) );
		
		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
		
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return MenuItemTableCell.CELL_WIDTH;
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		return MenuItemTableCell.CELL_HEIGHT;
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		
		_cancelButton.setEnabled( true );
		controller.respondToInput( "" + index, MenuItemInChan.ICListModify );
		
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#canDeleteCell(gui.scrolltable.ZScrollTable, int)
	 */
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return true;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasDeleted(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasDeleted(ZScrollTable table, int index) {
		
		// Send the delete message to the controller.
		controller.respondToInput( "" + index, MenuItemInChan.ICListDelete );
		
	}
	
	/**
	 * @return This GUI's content panel.
	 */
	public JPanel getMainPanel() {
		return _mainPanel;
	}
	
}
