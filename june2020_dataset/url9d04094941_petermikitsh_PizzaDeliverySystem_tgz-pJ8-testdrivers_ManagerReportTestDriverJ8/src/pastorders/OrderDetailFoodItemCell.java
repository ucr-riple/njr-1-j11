package pastorders;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Font;

import javax.swing.JLabel;

import model.FoodItem;

/**
 * A miniaturized food item cell. 
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class OrderDetailFoodItemCell extends ZScrollTableCell {
	
	/**
	 * The height of a food item cell.
	 */
	public static final int CELL_HEIGHT = 50;
	
	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * Creates a new cell from the given customer 
	 * 
	 * @param customer The customer whose data should appear here.
	 */
	public OrderDetailFoodItemCell( FoodItem foodItem ) {

		this.setLayout( null );
		this.setSize( OrderDetailFoodItemCell.CELL_WIDTH, CELL_HEIGHT );
		
		// Create a JLabel for the SFI's name.
		JLabel nameLabel = new JLabel( foodItem.getName() );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 20 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -75, 500, 200 );
		
		// Create a JLabel for the SFI's price.
		JLabel priceLabel = new JLabel( "$" + foodItem.getFormattedPrice(), JLabel.TRAILING );
		priceLabel.setFont( new Font("SansSerif", Font.PLAIN, 16 ) );
		this.add( priceLabel );
		priceLabel.setBounds( 47, -123, 200, 300 );
		
	}

}
