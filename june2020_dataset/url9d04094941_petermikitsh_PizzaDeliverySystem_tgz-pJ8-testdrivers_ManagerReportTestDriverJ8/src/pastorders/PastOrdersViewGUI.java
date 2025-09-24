package pastorders;

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

import main.PDSViewManager;
import model.Order;
import viewcontroller.GeneralViewGUI;

/***
 * The GUI view for the Past orders module.
 * 
 * @author 	Isioma Nnodum iun4534@rit.edu
 */
public class PastOrdersViewGUI extends PastOrdersView
	implements GeneralViewGUI, ZScrollTableDelegate {

	/**
	 * The JPanel to which contains everything in this view.
	 */
	private JPanel _mainPanel;

	/** 
	 * The table that displays the list of customers.
	 */
	private ZScrollTable _pastOrdersTable;
	
	/**
	 * A local list of displayed orders.
	 */
	private ArrayList<Order> _orderCache;
	
	/**
	 * A Timer that periodically refreshes the view.
	 */
	private Timer _refreshTimer;
	
	/**
	 * A panel that displays the details of a selected order.
	 */
	private JPanel _orderDisplayContainer;
	
	/**
	 * A label that is displayed if no order is selected.
	 */
	private final JLabel _noOrderLabel = new JLabel( "Select an order to view its details." );

	/**
	 * The default constructor.  Does Swing-related setup.
	 */
	public PastOrdersViewGUI() {

		// Initialize the main panel.
		_mainPanel = new JPanel();
		_mainPanel.setLayout( new GridBagLayout() );
		_mainPanel.setName( "Past Orders" );
		
		GridBagConstraints constraints = new GridBagConstraints();

		// Create a table to which the list of past orders will be written.
		JPanel pastOrdersPanel = new JPanel();
		_pastOrdersTable = new ZScrollTable( this );
		_pastOrdersTable.setPreferredSize( new Dimension( PastOrderTableCell.CELL_WIDTH, 200 ) );
		pastOrdersPanel.add( _pastOrdersTable, BorderLayout.CENTER );
		
		_mainPanel.add( pastOrdersPanel, constraints );
		
		// Initialize the order display container.
		_orderDisplayContainer = new JPanel();
		_orderDisplayContainer.setLayout( new GridBagLayout() );
		_orderDisplayContainer.add( _noOrderLabel, new GridBagConstraints() );
		_orderDisplayContainer.setPreferredSize( new Dimension( PastOrderTableCell.CELL_WIDTH, 250 ) );
		_orderDisplayContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Order Details" ) );
		constraints.gridy = 1;
		_mainPanel.add( _orderDisplayContainer, constraints );

	}

	/**
	 * @see viewcontroller.GeneralView#displayString(java.lang.String, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayString(String message, OutputChannel outChannel) {

		_mainPanel.updateUI();

	}

	/**
	 * @see viewcontroller.GeneralView#displayObject(java.lang.Object, viewcontroller.GeneralView.OutputChannel)
	 */
	public void displayObject(Object object, OutputChannel outChannel) {
		
		// The object should be an order.  Create a new order display panel for it.
		OrderDetailPanel odPanel = new OrderDetailPanel( (Order)object );
		
		// Display the panel.
		_orderDisplayContainer.removeAll();
		_orderDisplayContainer.add( odPanel, new GridBagConstraints() );
		
	}

	/**
	 * @see viewcontroller.GeneralView#displayList(java.util.ArrayList, viewcontroller.GeneralView.OutputChannel)
	 */
	@SuppressWarnings("unchecked")
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {

		// Store the list locally and update the table.
		if( (PastOrdersOutChan)outChannel == PastOrdersOutChan.OCList ) {
			_orderCache = (ArrayList<Order>) list;
			_pastOrdersTable.updateTable();
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

		switch( (PastOrdersInChan)inChannel ) {
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
		return new Dimension( PastOrderTableCell.CELL_WIDTH,
				PastOrderTableCell.CELL_HEIGHT ) ;
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
		return PastOrderTableCell.getHeader();
	}
	
	/**
	 * @see gui.scrolltable.ZScrollTableDelegate#getEmptyTableMessage(gui.scrolltable.ZScrollTable)
	 */
	public String getEmptyTableMessage( ZScrollTable table ) {
		return "No orders have been completed.";
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {	
		
		// Create a new CustomerTableCell from the customer at the given
		// index.
		PastOrderTableCell cell = new PastOrderTableCell( _orderCache.get( index ) );

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
		controller.respondToInput( "" + index, PastOrdersInChan.ICListDetails );
	}
	
	@Override
	public int getCellWidth(ZScrollTable table) {
		return PastOrderTableCell.CELL_WIDTH;
	}

	@Override
	public int getCellHeight(ZScrollTable table, int index) {
		return PastOrderTableCell.CELL_HEIGHT;
	}

	@Override
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return false;
	}

	@Override
	public void cellWasDeleted(ZScrollTable table, int index) {}
	
	
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
				controller.respondToInput( PastOrdersView.BACK_KEY , PastOrdersInChan.ICBack);
			}

		} else if ( aEvent.getSource() instanceof Timer ) {
			
			// Send the refresh message.
			controller.respondToInput( PastOrdersView.REFRESH_KEY,
										PastOrdersInChan.ICRefresh );
			
		}

	}
	
}
