package pastorders;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;

import model.Order;

/**
 * A table view cell that displays a customer's information.
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
@SuppressWarnings("serial")
public class PastOrderTableCell extends ZScrollTableCell {

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
	 * Creates a new cell from the given customer 
	 * 
	 * @param customer The customer whose data should appear here.
	 */
	public PastOrderTableCell(Order pastOrder) {

		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( new GridLayout(1, 4) );

		// Create a JLabel for the customer's name.
		JLabel orderIDLabel = new JLabel( "" + pastOrder.getOrderId(), JLabel.CENTER  );
		orderIDLabel.setFont( CELL_FONT );
		this.add(orderIDLabel);

		// Create a JLabel for the customer's phone number
		JLabel customerNameLabel = new JLabel( pastOrder.getCustomerName(), JLabel.CENTER  );
		customerNameLabel.setFont( CELL_FONT );
		customerNameLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(customerNameLabel);

		// Create a JLabel for the customer's address
		JLabel addressLabel = new JLabel( pastOrder.getDeliveryLocation().getLocation(), JLabel.CENTER  );
		addressLabel.setFont( CELL_FONT );
		addressLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(addressLabel);
		
		// Create JLabel for the orders total cost
		JLabel totalCostLabel = new JLabel( "$" + Order.formatPrice( pastOrder.calculateTotal() ), JLabel.CENTER  );
		totalCostLabel.setFont( CELL_FONT );
		totalCostLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(totalCostLabel);		

	}
	
	/**
	 * Default constructor.
	 */
	public PastOrderTableCell() {}
	
	/**
	 * Gets the header cell for the past orders table.
	 * 
	 * @return header cell
	 */
	public static PastOrderTableCell getHeader() {
		
		PastOrderTableCell header = new PastOrderTableCell();
		header.setPreferredSize( new Dimension( CELL_WIDTH, CELL_HEIGHT ) );
		header.setLayout( new GridLayout(1, 4) );

		// Create a JLabel for the customer's name header
		JLabel orderIDLabel = new JLabel( "Order Id", JLabel.CENTER );
		orderIDLabel.setFont( HEADER_FONT );
		header.add(orderIDLabel);

		// Create a JLabel for the customer's phone number header
		JLabel customerNameLabel = new JLabel( "Customer Name", JLabel.CENTER );
		customerNameLabel.setFont( HEADER_FONT );
		header.add(customerNameLabel);

		// Create a JLabel for the customer's address header
		JLabel addressLabel = new JLabel( "Location", JLabel.CENTER  );
		addressLabel.setFont( HEADER_FONT );
		header.add(addressLabel);
		
		// Create JLabel for the orders total cost header
		JLabel totalCostLabel = new JLabel( "Total", JLabel.CENTER  );
		totalCostLabel.setFont( HEADER_FONT );
		header.add(totalCostLabel);	
		
		return header;
		
	}

}
