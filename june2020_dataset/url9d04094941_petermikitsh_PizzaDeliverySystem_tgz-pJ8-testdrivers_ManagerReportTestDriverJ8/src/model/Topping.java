package model;

import model.database.Database;

/**
 * A class that represents a topping on a PizzaFoodItem.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class Topping extends FoodItem {
	
	private static Database<FoodItem> _toppingDatabase;
	
	/**
	 * The available topping locations.
	 */
	public enum ToppingLocation {
		
		ToppingLocationLeft,
		ToppingLocationRight,
		ToppingLocationWhole;
		
		public String toString() {
			
			switch( this ) {
			
			case ToppingLocationLeft:
				return "Left";
			case ToppingLocationRight:
				return "Right";
			case ToppingLocationWhole:
				return "Whole";
			default:
				return "";

			}
			
		}

	}
	
	/**
	 * Indication of the topping's location.
	 */
	private ToppingLocation location;
	
	/**
	 * Constructor for a Topping.
	 */
	public Topping() {}
	
	/**
	 * Constructor for a Topping.
	 * 
	 * @param name   the topping's name
	 * @param price   the topping's price
	 */
	public Topping(String name) {
		
		super();
		setName(name);
		
	}
	
	/**
	 * Constructor for a Topping.
	 * 
	 * @param name	the topping's name
	 * @param location   the topping's location
	 * @param price	the   topping's price
	 */
	public Topping(String name, ToppingLocation location, double price) {
		
		super(name, price);
		this.location = location;
		
	}
	
	/**
	 * Gets the topping's location.
	 * 
	 * @return   the topping's location
	 */
	public ToppingLocation getLocation() {
		return location;
	}
	
	/**
	 * Sets the topping's location [left, right, whole]. Checks to make sure the given location String is valid.
	 * 
	 * @param location   String to set the topping's location to
	 */
	public void setLocation(ToppingLocation location) {
		this.location = location;
	}
	
	/**
	 * Implements calculate price. Toppings don't have prices.
	 */
	public double calculatePrice() {
		
		return 0.0;
	}
	
	public static Database<FoodItem> getDb() {
		
		if ( null == _toppingDatabase ) {
			_toppingDatabase = new Database<FoodItem>("toppings");
		}
		
		return _toppingDatabase;
		
	}
	
	public int getKey() {
		return this.name.hashCode();
	}

}
