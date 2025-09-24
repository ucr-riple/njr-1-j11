package menueditor;

import java.awt.*;

import javax.swing.*;

import model.FoodItem;
import gui.scrolltable.ZScrollTableCell;

/**
 * A table view cell that displays a menu item.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
@SuppressWarnings("serial")
public class MenuItemTableCell extends ZScrollTableCell {

	/**
	 * The width of a cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * The height of a cell.
	 */
	public static final int CELL_HEIGHT = 90;
	
	/**
	 * Creates a new cell from the given menu item.
	 * 
	 * @param item 
	 * 		The menu item whose data should appear here.
	 */
	public MenuItemTableCell(FoodItem item) {
		
		this.setSize( CELL_WIDTH, CELL_HEIGHT );
		this.setLayout( null );
		
		// Create a JLabel for the menu item's name.
		JLabel nameLabel = new JLabel( item.getName() );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 16 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -75, 500, 200 );
		
		// Create a JLabel for the menu item's price.
		JLabel priceLabel = new JLabel( "Price: $" + item.getFormattedPrice() );
		priceLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
		priceLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(priceLabel);
		priceLabel.setBounds( 35, -55, 500, 200 );
		
		// Create a JLabel for the menu item's prep time.
		JLabel detailsLabel = new JLabel( "PT: " + item.getPrepTime() + 
				"; CT: " + item.getCookTime() + 
				"; OU: " + item.getOvenSpaceUnits() );
		detailsLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
		detailsLabel.setForeground( new Color( 50, 50, 50 ) );
		this.add(detailsLabel);
		detailsLabel.setBounds( 35, -35, 500, 200 );
		
	}
	
}
