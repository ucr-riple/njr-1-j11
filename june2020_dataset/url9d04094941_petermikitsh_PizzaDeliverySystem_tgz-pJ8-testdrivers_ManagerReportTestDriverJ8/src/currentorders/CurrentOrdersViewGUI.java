package currentorders;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import pastorders.OrderDetailPanel;
import pastorders.PastOrderTableCell;

import main.PDSViewManager;
import model.Order;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the current orders module.
 * 
 * @author 	Casey Klimkowsky   cek3403@g.rit.edu
 */
public class CurrentOrdersViewGUI extends CurrentOrdersView
	implements GeneralViewGUI, ZScrollTableDelegate {
	
	/**
	 * The name of the button for modifying orders.
	 */
	private static final String MODIFY_BUTTON_NAME = "Modify";
	
	/**
	 * The name of the button for delete orders.
	 */
	private static final String DELETE_BUTTON_NAME = "Delete";
	
	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/** 
	 * The table that displays the list of customers.
	 */
	private ZScrollTable _currentOrdersTable;
	
	/**
	 * A local list of displayed orders.
	 */
	private ArrayList<Order> _orderCache;
	
	/**
	 * A Timer that updates the time display.
	 */
	private static Timer _refreshTimer;
	
	/**
	 * A panel that displays the details of a selected order.
	 */
	private JPanel _orderDisplayContainer;
	
	/**
	 * A label that is displayed if no order is selected.
	 */
	private final JLabel _noOrderLabel = new JLabel( "Select an order to view its details and modify it." );

	/**
	 * The butotn for modifying orders.
	 */
	private JButton _modifyButton;

	/**
	 * The butotn for deleting orders.
	 */
	private JButton _deleteButton;
	
	/**
	 * A label that displays error information.
	 */
	private JLabel _errorLabel;

	/**
	 * The default constructor. Does Swing-related setup.
	 */
	public CurrentOrdersViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new BorderLayout() );
		_mainPanel.setName( "Current Orders" );

		// Create a label to hold the error output.
		_errorLabel = new JLabel( " " );
		_errorLabel.setForeground( Color.RED );
		JPanel errorPanel = new JPanel();
		errorPanel.add( _errorLabel );
		_mainPanel.add( errorPanel, BorderLayout.SOUTH );

		// Create a panel for the center.
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridBagLayout() );
		GridBagConstraints centerConst = new GridBagConstraints();
		_mainPanel.add( centerPanel, BorderLayout.CENTER );
		
		// Create a table to which the list of current orders will be written.
		JPanel currentOrdersPanel = new JPanel();
		_currentOrdersTable = new ZScrollTable( this );
		_currentOrdersTable.setPreferredSize( new Dimension( CurrentOrderTableCell.CELL_WIDTH, 160 ) );
		currentOrdersPanel.add( _currentOrdersTable, BorderLayout.CENTER );
		
		centerPanel.add(currentOrdersPanel, centerConst );
		
		// Create panel for the bottom of the center.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridBagLayout() );
		GridBagConstraints bottomConst = new GridBagConstraints();
		bottomPanel.setBorder( BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
							"Order Details" ) );
		centerConst.gridy = 1;
		centerPanel.add( bottomPanel, centerConst );
		
		// Initialize the order display container.
		_orderDisplayContainer = new JPanel();
		_orderDisplayContainer.setPreferredSize( new Dimension( PastOrderTableCell.CELL_WIDTH, 220 ) );
		_orderDisplayContainer.setLayout( new GridBagLayout() );
		_orderDisplayContainer.add( _noOrderLabel, new GridBagConstraints() );
		bottomPanel.add( _orderDisplayContainer, bottomConst );
		
		// Initialize the panel of buttons.
		JPanel buttonPanel = new JPanel();
		_modifyButton = new JButton( MODIFY_BUTTON_NAME );
		_modifyButton.addActionListener( this );
		_modifyButton.setEnabled( false );
		_deleteButton = new JButton( DELETE_BUTTON_NAME );
		_deleteButton.addActionListener( this );
		_deleteButton.setEnabled( false );
		buttonPanel.add( _modifyButton );
		buttonPanel.add( _deleteButton );
		bottomConst.gridy = 1;
		bottomPanel.add( buttonPanel, bottomConst );

	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		// Check for error output.
		if( outChannel == CurrentOrdersOutChan.OCError ) {
			_errorLabel.setText( message );
		}

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		// The object should be an order.  
		if( object != null ) { 
				
			// Create a new order display panel for it.
			OrderDetailPanel odPanel = new OrderDetailPanel( (Order)object );

			// Display the panel, and add the panel ("modify", "delete") of buttons.
			_orderDisplayContainer.removeAll();
			GridBagConstraints constraints = new GridBagConstraints();
			_orderDisplayContainer.add( odPanel, constraints );
			
		} else {
			
			// Clear the order detail and replace it with the label.
			_orderDisplayContainer.removeAll();
			_orderDisplayContainer.add( _noOrderLabel, new GridBagConstraints() );
			_orderDisplayContainer.updateUI();
			
			// Disable the buttons.
			_modifyButton.setEnabled( false );
			_deleteButton.setEnabled( false );
			
			// Reset the error text.
			_errorLabel.setText( " " );
			
		}

	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		
		// Store the list locally and update the table.
		if( (CurrentOrdersOutChan)outChannel == CurrentOrdersOutChan.OCList ) {
			_orderCache = (ArrayList<Order>) list;
			_currentOrdersTable.updateTable();
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
		
		// Enable or disable the timer.
		if( visible ) {
			
			_refreshTimer = new Timer( 500, this );
			_refreshTimer.start();
			
		} else if( _refreshTimer != null ) {

			_refreshTimer.stop();
			_refreshTimer = null;

		}
		
	}

	/**
	 * @see viewcontroller.GeneralView#setChannelEnabled( InputChannel inChannel, boolean enabled )
	 */
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {

		switch( (CurrentOrdersInChan)inChannel ) {
		
		case ICListModify:
			_modifyButton.setEnabled( enabled );
			break;
		
		case ICListDelete:
			_deleteButton.setEnabled( enabled );
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
		return _orderCache.size();
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCellSize(gui.scrolltable.ZScrollTable)
	 */
	public Dimension getCellSize(ZScrollTable table) {
		return new Dimension( CurrentOrderTableCell.CELL_WIDTH,
				CurrentOrderTableCell.CELL_HEIGHT ) ;
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
		return CurrentOrderTableCell.getHeader();
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "There are no orders currently in the kitchen.";
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {		
		
		// Create a new CurrentOrderTableCell from the customer at the given
		// index.
		CurrentOrderTableCell cell = new CurrentOrderTableCell( _orderCache.get( index ) );

		// Color the cell based on the index.
		int shade = 220 + (index % 2) * 20;
		cell.setBackground( new Color( shade, shade, shade ) );

		return cell;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		// Send the modify message to the controller.
		controller.respondToInput( "" + index, CurrentOrdersInChan.ICListDetails );
	}
	
	@Override
	public int getCellWidth(ZScrollTable table) {
		return CurrentOrderTableCell.CELL_WIDTH;
	}

	@Override
	public int getCellHeight(ZScrollTable table, int index) {
		return CurrentOrderTableCell.CELL_HEIGHT;
	}

	@Override
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return false;
	}

	@Override
	public void cellWasDeleted(ZScrollTable table, int index) {
		// Do nothing.
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
			
			JButton sourceButton = (JButton) aEvent.getSource();
			
			if (sourceButton.getText().equals(PDSViewManager.BACK_BUTTON_NAME)) {
				controller.respondToInput( CurrentOrdersView.BACK_KEY , CurrentOrdersInChan.ICBack);
			} else if( sourceButton.getText().equals( MODIFY_BUTTON_NAME ) ) {
				controller.respondToInput( CurrentOrdersView.MODIFY_KEY , CurrentOrdersInChan.ICMenuOption );
			} else if( sourceButton.getText().equals( DELETE_BUTTON_NAME ) ) {
				controller.respondToInput( CurrentOrdersView.DELETE_KEY , CurrentOrdersInChan.ICMenuOption );
			}

		} else if ( aEvent.getSource() instanceof Timer ) {
			
			// Send the refresh message.
			controller.respondToInput( CurrentOrdersView.REFRESH_KEY,
										CurrentOrdersInChan.ICRefresh );
			
		}

	}

}
