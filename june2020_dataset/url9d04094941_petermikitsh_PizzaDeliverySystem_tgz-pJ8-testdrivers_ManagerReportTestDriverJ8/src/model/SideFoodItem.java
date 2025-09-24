package model;

import model.database.Database;

/**
 * A class that represents a simple food item, such as a pizza log or salad.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class SideFoodItem extends FoodItem {

	private static Database<FoodItem> _sideFoodItemDatabase;
	
	/**
	 * Constructor for a SideFoodItem.
	 */
	public SideFoodItem() {}
	
	/**
	 * Constructor for a SideFoodItem.
	 * 
	 * @param name   the food item's name
	 * @param price   the food item's price
	 * @param prepTime   the food item's preparation time
	 * @param cookTime   the food item's cook time
	 * @param ovenSpaceUnits   the food item's oven space units
	 */
	public SideFoodItem(String name, double price, int prepTime, int cookTime, int ovenSpaceUnits ) {
		super(name, price, prepTime, cookTime, ovenSpaceUnits );
	}
	
	public static Database<FoodItem> getDb() {
		
		if ( null == _sideFoodItemDatabase ) {
			_sideFoodItemDatabase = new Database<FoodItem>("sides");
		}
		
		return _sideFoodItemDatabase;
		
	}
	
	public double calculatePrice() {
		price = super.getPrice();
		return price;
	}
	
}
