package customereditor;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import model.Customer;

/**
 * A table view cell that displays a customer's information.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class CustomerTableCell extends ZScrollTableCell {

	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 75;
	
	/**
	 * Creates a new cell from the given customer 
	 * 
	 * @param customer The customer whose data should appear here.
	 */
	public CustomerTableCell(Customer customer) {

		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( null );

		// Create a JLabel for the customer's name.
		JLabel nameLabel = new JLabel( customer.getName() );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 22 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -75, 500, 200 );

		// Create a JLabel for the customer's phone number
		JLabel phoneLabel = new JLabel( customer.getFormattedPhoneNumber() );
		phoneLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
		phoneLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(phoneLabel);
		phoneLabel.setBounds( 35, -55, 500, 200 );

		// Create a JLabel for the customer's phone number
		JLabel addressLabel = new JLabel( customer.getStreetAddress().getLocation() );
		addressLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
		addressLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(addressLabel);
		addressLabel.setBounds( 35, -40, 500, 200 );

	}

}
