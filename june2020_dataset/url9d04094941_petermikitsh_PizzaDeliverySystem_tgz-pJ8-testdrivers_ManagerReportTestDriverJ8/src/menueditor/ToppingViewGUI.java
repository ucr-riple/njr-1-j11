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
import model.Topping;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the topping module.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class ToppingViewGUI extends ToppingView
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
	 * The text field that displays a topping's name.
	 */
	private JTextField _toppingNameField;
	
	/**
	 * The panel that displays a topping's detail.
	 */
	private JPanel _toppingDetailPanel;
	
	/**
	 * The panel that holds the topping buttons.
	 */
	private JPanel _toppingButtonPanel;
	
	/**
	 * The button that adds/updates toppings.
	 */
	private JButton _addButton;
	
	/**
	 * The button that cancels in-progress updates on a topping.
	 */
	private JButton _cancelButton;
	
	/**
	 * The table that displays the list of toppings.
	 */
	private ZScrollTable _toppingTable;
	
	/**
	 * A single header cell that stores the list title.
	 */
	private static ZScrollTableCell _toppingHeaderCell;
	
	/**
	 * A local list of displayed toppings.
	 */
	private ArrayList<Topping> _toppingCache;
		
	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public ToppingViewGUI() {
		
		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new GridBagLayout() );
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 1;
		
		// Create the table that displays the list of toppings.
		_toppingTable = new ZScrollTable( this );
		_toppingTable.setPreferredSize( new Dimension( ToppingTableCell.CELL_WIDTH, 295 ) );
		_mainPanel.add( _toppingTable );
		
		// Create the bottom panel.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridBagLayout() );
		constraints.ipady = 15;
		_mainPanel.add( bottomPanel, constraints );
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout( new GridLayout( 0, 1, 0, 7 ) );
		JLabel toppingNameLabel = new JLabel( "Name:  ", JLabel.RIGHT );
		toppingNameLabel.setFont( PDSViewManager.DEFAULT_TF_LABEL_FONT );
		textPanel.add( toppingNameLabel );
		
		// Create the topping detail panel.
		_toppingDetailPanel = new JPanel();
		_toppingDetailPanel.setLayout( new GridBagLayout() );
		_toppingDetailPanel.add( textPanel );
		_toppingNameField = new JTextField( 15 );
		_toppingNameField.setFont( PDSViewManager.DEFAULT_TEXTFIELD_FONT );
		_toppingNameField.setPreferredSize( new Dimension( 180, PDSViewManager.DEFAULT_TEXTFIELD_HEIGHT ) );
		_toppingNameField.addKeyListener( this );
		_toppingDetailPanel.add( _toppingNameField );
		JPanel test = new JPanel();
		test.setLayout( new GridBagLayout() );
		GridBagConstraints c2 = new GridBagConstraints();
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(35, 0, 0, 0);
		test.add( _toppingDetailPanel, c2 );
		bottomPanel.add( test );
		
		// Create the button panel.
		_toppingButtonPanel = new JPanel();
		_addButton = new JButton( ToppingViewGUI.ADD_BUTTON_NAME);
		_addButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_addButton.addActionListener( this );
		_addButton.setPreferredSize( new Dimension( 100, 25 ) );
		_addButton.setEnabled( false );
		_toppingButtonPanel.add( _addButton );
		_cancelButton = new JButton( ToppingViewGUI.CANCEL_BUTTON_NAME );
		_cancelButton.setFont( PDSViewManager.DEFAULT_BUTTON_FONT );
		_cancelButton.addActionListener( this );
		_cancelButton.setPreferredSize( new Dimension( 100, 25 ) );
		_cancelButton.setEnabled( false );
		_toppingButtonPanel.add( _cancelButton );
		constraints.ipady = 0;
		bottomPanel.add( _toppingButtonPanel, constraints );
		
	}
	
	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		
		switch( (ToppingInChan) inChannel ) {

		case ICCancel:
			_toppingNameField.setText( "" );
			_addButton.setText( ToppingViewGUI.ADD_BUTTON_NAME );
			break;
			
		}
		
	}

	@Override
	public void displayString(String message, OutputChannel outChannel) {}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		// Check if the object is a topping.
		if( object instanceof Topping ) {
			
			// Send the topping's data to the text displays.
			Topping topping = (Topping) object;
			_toppingNameField.setText( topping.getName() );
			
			// Set the add/update text to "update".
			_addButton.setEnabled(true);
			_cancelButton.setEnabled(true);
			_addButton.setText( ToppingViewGUI.UPDATE_BUTTON_NAME );
			
		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		// Store the list locally and update the table.
		if( (ToppingOutChan) outChannel == ToppingOutChan.OCList ) {
			_toppingCache = (ArrayList<Topping>) list;
			_toppingTable.updateTable();
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
			if( sourceButton.getText().equals( ToppingViewGUI.UPDATE_BUTTON_NAME ) || 
				sourceButton.getText().equals( ToppingViewGUI.ADD_BUTTON_NAME ) ) {
								
				// Send the current values of the name field to the controller.
				controller.respondToInput( _toppingNameField.getText(),
						ToppingInChan.ICToppingData );
				
			}
			
			else if( sourceButton.getText().equals( 
				ToppingViewGUI.CANCEL_BUTTON_NAME ) ) {
				controller.respondToInput( ToppingView.CANCEL_KEY,
					ToppingInChan.ICCancel );
			}
			
			// Reset the buttons and clear the input fields.
			_addButton.setText( ToppingViewGUI.ADD_BUTTON_NAME );
			_addButton.setEnabled(false);
			_cancelButton.setEnabled(false);
			_toppingNameField.setText( "" );
			
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
		
		String nameString = _toppingNameField.getText();
		
		if( ! nameString.equals("") ) {
			_addButton.setEnabled(true);
			_cancelButton.setEnabled(true);
		}
		else {
			_addButton.setEnabled(false);
			_cancelButton.setEnabled(false);
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
		return _toppingCache.size();
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
		
		if ( _toppingHeaderCell == null ) {
			
			// Create the header cell text.
			JLabel headerName = new JLabel( "Pizza Toppings" );
			headerName.setFont( new Font( "SansSerif", Font.PLAIN, 22 ) );
			
			// Create the cell and add the text.
			_toppingHeaderCell = new ZScrollTableCell();
			_toppingHeaderCell.setPreferredSize( 
					new Dimension( ToppingTableCell.CELL_WIDTH, 40 ) );
			_toppingHeaderCell.setLayout( new GridBagLayout() );
			_toppingHeaderCell.add( headerName );
			
		}
		
		return _toppingHeaderCell;
		
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "There are no toppings on the menu.";
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {
		
		// Create a new ToppingTableCell from the topping at the given index.
		ToppingTableCell cell = new ToppingTableCell( _toppingCache.get( index ) );
		
		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
		
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellWidth(gui.scrolltable.ZScrollTable)
	 */
	public int getCellWidth(ZScrollTable table) {
		return ToppingTableCell.CELL_WIDTH;
	}

	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellHeight(gui.scrolltable.ZScrollTable, int)
	 */
	public int getCellHeight(ZScrollTable table, int index) {
		return ToppingTableCell.CELL_HEIGHT;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		
		_cancelButton.setEnabled( true );
		controller.respondToInput( "" + index, ToppingInChan.ICListModify );
		
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
		controller.respondToInput( "" + index, ToppingInChan.ICListDelete );
		
	}

	/**
	 * @return This GUI's content panel.
	 */
	public JPanel getMainPanel() {
		return _mainPanel;
	}

}
