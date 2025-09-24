package currentorders;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;

import model.Order;
import ninja.Time;

/**
 * A table view cell that displays a current order's information.
 * 
 * @author 	Casey Klimkowsky   cek3403@g.rit.edu
 */
@SuppressWarnings("serial")
public class CurrentOrderTableCell extends ZScrollTableCell {

	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 700;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 20;
	
	/**
	 * Font used in table cells.
	 */
	public static final Font CELL_FONT = new Font( "SansSerif", Font.PLAIN, 13 );
	
	/**
	 * Font used in header cell.
	 */
	public static final Font HEADER_FONT = new Font( "SansSerif", Font.PLAIN, 19 );
	
	/**
	 * Default constructor.
	 */
	public CurrentOrderTableCell() { }
	
	/**
	 * Creates a new cell from the given order.
	 * 
	 * @param currentOrder 
	 * 				The order whose data should appear here.
	 */
	public CurrentOrderTableCell(Order currentOrder) {

		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( new GridLayout(1, 4) );

		// Create a JLabel for the customer's name.
		JLabel orderIDLabel = new JLabel( "" + currentOrder.getOrderId(), JLabel.CENTER  );
		orderIDLabel.setFont( CELL_FONT );
		this.add(orderIDLabel);

		// Create a JLabel for the customer's phone number
		JLabel customerNameLabel = new JLabel( currentOrder.getCustomerName(), JLabel.CENTER  );
		customerNameLabel.setFont( CELL_FONT );
		customerNameLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(customerNameLabel);

		// Create JLabel for the estimated delivery time
		JLabel estDeliveryTimeLabel = 
			new JLabel( Time.formatTime(currentOrder.calculateEstimatedDeliveryTime()), 
					JLabel.CENTER );
		estDeliveryTimeLabel.setFont( CELL_FONT );
		estDeliveryTimeLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(estDeliveryTimeLabel);
		
		// Create JLabel for the status of the order
		JLabel statusLabel = new JLabel( currentOrder.getStatus().toString(), JLabel.CENTER );
		statusLabel.setFont( CELL_FONT );
		statusLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(statusLabel);
		
	}
	
	/**
	 * Creates the header cell for the Current Orders table.
	 * 
	 * @return current orders header cell
	 */
	public static CurrentOrderTableCell getHeader() {
		
		CurrentOrderTableCell header = new CurrentOrderTableCell();
		header.setSize( CELL_WIDTH, CELL_HEIGHT );
		//header.setLayout( new GridLayout(1, 4) );
		header.setLayout( null );
		
		// Create a JLabel for the customer's name header
		JLabel orderIDLabel = new JLabel( "Order Id", JLabel.CENTER );
		orderIDLabel.setFont( HEADER_FONT );
		header.add(orderIDLabel);
		orderIDLabel.setBounds(0, 0, 185, CELL_HEIGHT);

		// Create a JLabel for the customer's phone number header
		JLabel customerNameLabel = new JLabel( "Customer Name", JLabel.CENTER );
		customerNameLabel.setFont( HEADER_FONT );
		header.add(customerNameLabel);
		customerNameLabel.setBounds(170, 0, 185, CELL_HEIGHT);

		// Create JLabel for the estimated delivery time header
		JLabel estDeliveryTimeLabel = new JLabel( "Est. Delivery Time", JLabel.CENTER );
		estDeliveryTimeLabel.setFont( HEADER_FONT );
		header.add(estDeliveryTimeLabel);
		estDeliveryTimeLabel.setBounds(345, 0, 185, CELL_HEIGHT);
		
		// Create JLabel for the status of the order header
		JLabel statusLabel = new JLabel( "Status", JLabel.CENTER );
		statusLabel.setFont( HEADER_FONT );
		header.add(statusLabel);
		statusLabel.setBounds(528, 0, 185, CELL_HEIGHT);
		
		return header;
		
	}

}
