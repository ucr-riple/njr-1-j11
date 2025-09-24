package pastorders;

import gui.scrolltable.ZScrollTable;
import gui.scrolltable.ZScrollTableCell;
import gui.scrolltable.ZScrollTableDelegate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.Order;
import neworder.FoodItemTableCell;
import ninja.Time;

/**
 * A panel that displays detailed information about an order.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class OrderDetailPanel extends JPanel implements ZScrollTableDelegate {
	
	/**
	 * The order displayed by this class.
	 */
	private Order _order;
	
	/**
	 * Creates a new panel from an order.
	 * 
	 * @param order The order to display information about.
	 */
	/**
	 * @param order
	 */
	public OrderDetailPanel( Order order ) {
		
		_order = order;
		
		// Initialize the view.
		initializeDisplay();
		
	}
	
	/**
	 * Creates the display for this panel.
	 */
	private void initializeDisplay() {
		
		//this.add( new JLabel( "" + order.getOrderId() ) );
		this.setLayout( new GridBagLayout() );

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
		JLabel customerNameLabel = new JLabel( _order.getCustomerName() );
		JLabel customerPhoneLabel = new JLabel( _order.getCustomer().getFormattedPhoneNumber() );
		JLabel customerAddressLabel = new JLabel( _order.getDeliveryLocation().getLocation() );
		customerNameLabel.setFont( new Font( "SansSerif", Font.BOLD, 14 ) );
		customerPhoneLabel.setFont( new Font( "SansSerif", Font.PLAIN, 12 ) );
		customerAddressLabel.setFont( new Font( "SansSerif", Font.PLAIN, 12 ) );
		customerDisplayPanel.add( customerNameLabel );
		customerDisplayPanel.add( customerPhoneLabel );
		customerDisplayPanel.add( customerAddressLabel );
		customerDisplayContainer.add( customerDisplayPanel );
		
		// Add the customer display panel. 
		constraints.gridx = 0;
        constraints.gridy = 0;
        //constraints.weightx = 1.0;
		this.add( customerDisplayContainer, constraints );
		
		// Create the food item table.
		JPanel tableContainer = new JPanel();
		tableContainer.setLayout( new GridBagLayout() );
		tableContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Order Items" ) );
		ZScrollTable foodItemTable = new ZScrollTable( this );
		foodItemTable.setPreferredSize( new Dimension( FoodItemTableCell.CELL_WIDTH, 175 ) );
		tableContainer.add( foodItemTable, new GridBagConstraints() );
		
		// Add the table.
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER; //end row
        constraints.gridheight = GridBagConstraints.REMAINDER;
        //constraints.weighty = 1.0;
        constraints.ipadx = 0;
		this.add( tableContainer, constraints );
		foodItemTable.updateTable();
		
		// Create the panel for the three labels.
		Font titleLabelFont = new Font( "SansSerif", Font.BOLD, 13 );
		Font dataLabelFont = new Font( "SansSerif", Font.PLAIN, 13 );
		
		JPanel labelsContainer = new JPanel();
		labelsContainer.setLayout( new GridBagLayout() );
		labelsContainer.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Order Details" ) );
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout( new GridLayout( 3, 1 ) );
		labelsContainer.add( labelsPanel, new GridBagConstraints() );
		labelsPanel.setPreferredSize( new Dimension( 250, 60 ) );
		
		JPanel timeOrderedPanel = new JPanel();
		JLabel timeOrderedTitleLabel = new JLabel( "Time Ordered: " );
		timeOrderedTitleLabel.setFont( titleLabelFont );
		JLabel timeOrderedDataLabel = new JLabel( Time.formatTime( _order.getTimeOrdered() ) );
		timeOrderedDataLabel.setFont( dataLabelFont );
		timeOrderedPanel.add( timeOrderedTitleLabel );
		timeOrderedPanel.add( timeOrderedDataLabel );
		labelsPanel.add( timeOrderedPanel );
		
		// For current orders.
		if ( _order.getStatus() != Order.OrderStatus.Delivered ) {
			JPanel estDeliveryTimePanel = new JPanel();
			JLabel estDeliveryTimeTitleLabel = new JLabel( "Est. Delivery Time: " );
			estDeliveryTimeTitleLabel.setFont( titleLabelFont );
			JLabel timeDeliveredDataLabel = new JLabel( Time.formatTime( _order.calculateEstimatedDeliveryTime() ) );
			timeDeliveredDataLabel.setFont( dataLabelFont );
			estDeliveryTimePanel.add( estDeliveryTimeTitleLabel );
			estDeliveryTimePanel.add( timeDeliveredDataLabel );
			labelsPanel.add( estDeliveryTimePanel );
		}
		
		// For past orders.
		else {
			JPanel timeDeliveredPanel = new JPanel();
			JLabel timeDeliveredTitleLabel = new JLabel( "Time Delivered: " );
			timeDeliveredTitleLabel.setFont( titleLabelFont );
			JLabel timeDeliveredDataLabel = new JLabel( Time.formatTime( _order.getTimeDelivered() ) );
			timeDeliveredDataLabel.setFont( dataLabelFont );
			timeDeliveredPanel.add( timeDeliveredTitleLabel );
			timeDeliveredPanel.add( timeDeliveredDataLabel );
			labelsPanel.add( timeDeliveredPanel );
		}
		
		JPanel pricePanel = new JPanel();
		JLabel priceTitleLabel = new JLabel( "Total Price: " );
		priceTitleLabel.setFont( titleLabelFont );
		JLabel priceDataLabel = new JLabel( "$" + Order.formatPrice( _order.calculateTotal() ) );
		priceDataLabel.setFont( dataLabelFont );
		pricePanel.add( priceTitleLabel );
		pricePanel.add( priceDataLabel );
		labelsPanel.add( pricePanel );

		// Add the labels panel.
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        this.add( labelsContainer, constraints );
		
	}


	////  ZSCROLLTABLE DELEGATE METHODS

	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getNumberOfCells(gui.scrolltable.ZScrollTable)
	 */
	public int getNumberOfCells(ZScrollTable table) {
		return _order.getFoodItems().size();
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
		return "";
	}
	
	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#getCell(gui.scrolltable.ZScrollTable, int)
	 */
	public ZScrollTableCell getCell(ZScrollTable table, int index) {		
		
		// Create a new CustomerTableCell from the customer at the given
		// index.
		FoodItemTableCell cell = FoodItemTableCell.makeFoodItemTableCell( 
												_order.getFoodItems().get( index ) );

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
		return FoodItemTableCell.foodItemTableCellHeight( _order.getFoodItems().get( index ) );
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasClicked(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasClicked(ZScrollTable table, int index) {
		// Do nothing.
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#canDeleteCell(gui.scrolltable.ZScrollTable, int)
	 */
	public boolean canDeleteCell(ZScrollTable table, int index) {
		return false;
	}

	/** 
	 * @see gui.scrolltable.ZScrollTableDelegate#cellWasDeleted(gui.scrolltable.ZScrollTable, int)
	 */
	public void cellWasDeleted(ZScrollTable table, int index) {
		// Nothing to do here.
	}
	
}
