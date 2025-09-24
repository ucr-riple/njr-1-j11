package neworder;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Font;

import javax.swing.JLabel;

import model.FoodItem;
import model.PizzaFoodItem;
import model.SideFoodItem;
import model.Topping;

/**
 * A table view cell that displays information about a foodItem
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
public class FoodItemTableCell extends ZScrollTableCell {

	/**
	 * The width of a FI cell.
	 */
	public static final int CELL_WIDTH = 300;
	
	/**
	 * Factory method: creates an appropriate cell from the given food item. 
	 * 
	 * @param foodItem The foodItem to create a cell from.
	 */
	public static FoodItemTableCell makeFoodItemTableCell( FoodItem foodItem ) {

		// Check the class of the food item.
		if( foodItem instanceof PizzaFoodItem ) {
			return new PizzaFoodItemTableCell( (PizzaFoodItem)foodItem );
		} else if( foodItem instanceof SideFoodItem ) {
			return new SideFoodItemTableCell( (SideFoodItem) foodItem );
		} else {
			return null;
		}

	}
	
	/**
	 * Returns the height of a cell constructed from the given food item.
	 */
	public static int foodItemTableCellHeight( FoodItem foodItem ) {
		
		// Check the class of the food item.
		if( foodItem instanceof PizzaFoodItem ) {
			return PizzaFoodItemTableCell.pizzaFoodItemTableCellHeight( (PizzaFoodItem)foodItem );
		} else if( foodItem instanceof SideFoodItem ) {
			return SideFoodItemTableCell.sideFoodItemTableCellHeight( (SideFoodItem)foodItem );
		} else {
			return 0;
		}
		
	}

}


/**
 * A table view cell that displays information about a PizzaFoodItem
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
class PizzaFoodItemTableCell extends FoodItemTableCell {
	
	/**
	 * The fixed size of a PFI cell with no toppings.
	 */
	private static final int BASE_HEIGHT = 55;
	
	/**
	 * The amount that is added to the height of a PFI cell for each topping.
	 */
	private static final int TOPPING_HEIGHT = 20;
	
	/**
	 * Creates a new cell from the given pizza food item 
	 * 
	 * @param foodItem The pizza food item whose data should appear here.
	 */
	public PizzaFoodItemTableCell( PizzaFoodItem foodItem ) {

		this.setLayout( null );
		this.setSize( FoodItemTableCell.CELL_WIDTH, pizzaFoodItemTableCellHeight( foodItem ) );
		
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
		
		// Add toppings.
		for( int index = 0; index < foodItem.getToppings().size(); index ++ ) {
			
			Topping topping = foodItem.getToppings().get( index );
			JLabel locationLabel = new JLabel( topping.getLocation().toString(), JLabel.TRAILING );
			locationLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
			this.add(locationLabel);
			locationLabel.setBounds( 35, - 48 + index * TOPPING_HEIGHT, 40, 200 );
			
			JLabel toppingLabel = new JLabel( topping.getName() );
			toppingLabel.setFont( new Font("SansSerif", Font.BOLD, 13 ) );
			this.add(toppingLabel);
			toppingLabel.setBounds( 85, - 48 + index * TOPPING_HEIGHT, 200, 200 );
			
		}

	}

	/**
	 * Returns the height of the given PFI.
	 * @param foodItem
	 * @return
	 */
	public static int pizzaFoodItemTableCellHeight(PizzaFoodItem foodItem) {
		
		// Return a fixed value plus a multiple of the number of toppings 
		return BASE_HEIGHT + foodItem.getToppings().size() * TOPPING_HEIGHT;
		
	}
	
}

/**
 * A table view cell that displays information about a SideFoodItem
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
@SuppressWarnings("serial")
class SideFoodItemTableCell extends FoodItemTableCell {
	
	/**
	 * The height of a side food item cell.
	 */
	private static final int SFI_CELL_HEIGHT = 50;
	
	/**
	 * Creates a new cell from the given side food item 
	 * 
	 * @param foodItem The side food item whose data should appear here.
	 */
	public SideFoodItemTableCell( SideFoodItem foodItem ) {

		this( foodItem, -1 );
		
	}
	
	/**
	 * Creates a new cell from the given side food item with a quantity.
	 * 
	 * @param foodItem The side food item whose data should appear here.
	 * @param quantity The quantity to display for this side..
	 */
	public SideFoodItemTableCell( SideFoodItem foodItem, int quantity ) {

		this.setLayout( null );
		this.setSize( FoodItemTableCell.CELL_WIDTH, sideFoodItemTableCellHeight( foodItem ) );
		
		// Create a JLabel for the SFI's name.
		String quantityString = (quantity > 0) ? " <b>x " + quantity + "</b>" : "";
		JLabel nameLabel = new JLabel( "<html>" + foodItem.getName() + quantityString + "</html>" );
		nameLabel.setFont( new Font("SansSerif", Font.PLAIN, 20 ) );
		this.add(nameLabel);
		nameLabel.setBounds( 15, -75, 500, 200 );
		
		// Create a JLabel for the SFI's price.
		JLabel priceLabel = new JLabel( "$" + foodItem.getFormattedPrice() + " ea.", JLabel.TRAILING );
		priceLabel.setFont( new Font("SansSerif", Font.PLAIN, 16 ) );
		this.add( priceLabel );
		priceLabel.setBounds( 67, -123, 200, 300 );
		
	}

	/**
	 * Returns the height of the given PFI.
	 * @param foodItem
	 * @return
	 */
	public static int sideFoodItemTableCellHeight( SideFoodItem foodItem ) {
		
		return SFI_CELL_HEIGHT;
		
	}
	
}
