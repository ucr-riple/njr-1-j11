package model;

import java.util.ArrayList;

/**
 * A class that represents a pizza.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class PizzaFoodItem extends FoodItem {

	/**
	 * An ArrayList representing the pizza's toppings.
	 */
	private ArrayList<Topping> toppings;
	
	/**
	 * The size of the pizza.
	 */
	public PizzaSize size;
	
	/**
	 * The available pizza sizes.
	 */
	public enum PizzaSize {
		
		PizzaSizeSmall,
		PizzaSizeMedium,
		PizzaSizeLarge;
		
		public String toString(){
			
			String size = null;
			
			switch (this){
			
			case PizzaSizeSmall:
				size = "Small";
				break;
			
			case PizzaSizeMedium:
				size = "Medium";
				break;
				
			case PizzaSizeLarge:
				size = "Large";
				break;
			
			}
			
			return size;
		}
		
	}
	
	/**
	 * Constructor for a Pizza.
	 */
	public PizzaFoodItem() {
		toppings = new ArrayList<Topping>();
		price = 0.0;
	}
	
	/**
	 * Constructor for a Pizza with no toppings.
	 * 
	 * @param name   the name of the pizza
	 * @param price   the base price of the pizza
	 * @param prepTime   how long it takes to prepare the pizza
	 * @param cookTime   how long it takes to cook the pizza
	 * @param ovenSpaceUnits   the number of oven space units the pizza occupies
	 */
	public PizzaFoodItem(String name, double price, int prepTime, int cookTime, int ovenSpaceUnits ) {
		
		super(name, price, prepTime, cookTime, ovenSpaceUnits );
		
		toppings = new ArrayList<Topping>();
		
	}
	
	/**
	 * Constructor for a Pizza.
	 * 
	 * @param name   the name of the pizza
	 * @param price   the base price of the pizza
	 * @param toppings   the list of toppings
	 * @param prepTime   how long it takes to prepare the pizza
	 * @param cookTime   how long it takes to cook the pizza
	 * @param ovenSpaceUnits   the number of oven space units the pizza occupies
	 */
	public PizzaFoodItem(String name, double price, ArrayList<Topping> toppings, PizzaSize size,
			int prepTime, int cookTime, int ovenSpaceUnits ) {
		
		super(name, price, prepTime, cookTime, ovenSpaceUnits );
		
		this.toppings = new ArrayList<Topping>();
		this.size = size;
		
		for (Topping topping : toppings) {
			
			addTopping(topping);
			
		}
		
	}
	
	/**
	 * Returns the pizza's toppings.
	 * 
	 * @return   the ArrayList of the PizzaFoodItem's toppings
	 */
	public ArrayList<Topping> getToppings() {
		return toppings;
	}
	
	/**
	 * Sets the pizza's toppings to the given list.
	 * 
	 * @param toppings   the ArrayList of Toppings to set
	 */
	public void setToppings(ArrayList<Topping> newToppings) {
		
		while (this.getToppings().size() > 0) {
			Topping delTopping = this.getToppings().get(0);
			this.price -= delTopping.calculatePrice();
			this.removeTopping(delTopping);
		}
				
		for (Topping addTopping : newToppings)
			addTopping(addTopping);
		
	}
	
	/**
	 * Adds the given topping to the pizza and adds to the price.
	 * 
	 * @param   topping   the Topping to add to the PizzaFoodItem
	 */
	public void addTopping(Topping topping) {
		
		this.toppings.add(topping);
		this.calculatePrice();
		
	}
	
	/**
	 * Removes the given topping from the pizza.
	 * 
	 * @param   topping  the Topping to remove from the PizzaFoodItem
	 */
	public void removeTopping(Topping topping) {
		
		toppings.remove(topping);
		this.calculatePrice();
		
	}
	
	/**
	 * Removes the topping at the given index from the pizza.
	 * 
	 * @param   index  the index to remove the Topping from
	 */
	public void removeTopping(int index) {
		
		toppings.remove(index);
		this.calculatePrice();
		
	}
	
	/**
	 * Returns the size of the pizza.
	 * 
	 * @return   the size of the PizzaFoodItem
	 */
	public PizzaSize getSize() {
		return size;
	}
	
	/**
	 * Sets the size of the pizza.
	 * 
	 * @param   sets the PizzaFoodItem's size to the given PizzaSize
	 */
	public void setSize(PizzaSize size) {
		
		this.size = size;
	
		// Sets variables based on size.
		switch (size) {
		
		case PizzaSizeSmall:
				
			this.setPrice(8.00);
			this.setPrepTime(8);
			this.setCookTime(13);
			this.setOvenSpaceUnits(1);
			
			break;
		
		case PizzaSizeMedium:
			
			this.setPrice(11.00);
			this.setPrepTime(10);
			this.setCookTime(15);
			this.setOvenSpaceUnits(2);
			
			break;
			
		case PizzaSizeLarge:
			
			this.setPrice(16.00);
			this.setPrepTime(15);
			this.setCookTime(20);
			this.setOvenSpaceUnits(4);
			
			break;
			
		}
		
	}
	
	/**
	 * Calculates the price of the pizza based on its toppings and size.
	 * 
	 * @return The price of the pizza
	 */
	public double calculatePrice(){
		
		price = getBasePrice();
		
		double toppingCost = 0.0;
		
		switch(size) {
		
			case PizzaSizeSmall:
				toppingCost = 1.0;
				break;
				
			case PizzaSizeMedium:
				toppingCost = 1.5;
				break;
				
			case PizzaSizeLarge:
				toppingCost = 2.0;
				break;
				
		}
		
		int numPepperoni = 0;
		
		for(Topping t : toppings) {
			
			
			if( t.getLocation() == 
				Topping.ToppingLocation.ToppingLocationWhole){
				price += toppingCost;
				if ( t.getName().equalsIgnoreCase("Pepperoni")){
					numPepperoni++;
				}
			}
			
			else {
				price += (toppingCost/2);
				if ( t.getName().equalsIgnoreCase("Pepperoni")){
					numPepperoni += .5;
				}
			}
			
		}
		
		if ( numPepperoni == .5 ){
			price -= (toppingCost/2);
		}
		else if ( numPepperoni == 0){
			price -= 0;
		}
		else {
			price -= toppingCost;
		}
		
		return price;
		
	}
	
	/**
	 * Gives the base price of the pizza based on its size.
	 * 
	 * @return base price of pizza
	 */
	public double getBasePrice() {
		
		double basePrice = 0.0;
		
		switch(size) {
		
			case PizzaSizeSmall:
				basePrice = 8.0;
				break;
	
			case PizzaSizeMedium:
				basePrice = 11.0;
				break;
	
			case PizzaSizeLarge:
				basePrice = 16.0;
				break;	
			
		}
		
		return basePrice;

	}
	
	/**
	 * Returns a formatted string representation of the food item's price.
	 * 
	 * @return   the formatted String representation
	 */
	public String getFormattedPrice() {
		
		double currPrice = this.calculatePrice();
		return df.format(currPrice);
		
	}
	
	/**
	 * Determines if you can add a topping to a location by calculating
	 * the number of the toppings on the left and right sides.
	 * 
	 * @param loc The location is question that you want to add to.
	 * @return true if you can add one topping at that location
	 */
	public boolean canAddToppingToLocation(Topping.ToppingLocation loc) {
		
		int left = 0, right = 0;
		
		for (Topping topping: this.toppings) {
			Topping.ToppingLocation placement = topping.getLocation();
			
			switch (placement) {
			
				case ToppingLocationLeft:
				
					left += 1;
					break;
					
				case ToppingLocationRight:
				
					right += 1;
					break;
					
				case ToppingLocationWhole:
					
					left += 1;
					right += 1;
			}
			
		}
		
		return toppingValidator(loc, left, right);
		
	}
	
	/**
	 * 
	 * toppingValidator determines if a topping can be added at a given location with
	 * a given number of toppings on the left and right of the pizza.
	 * 
	 * @param loc The location of the topping to be added.
	 * @param left The number of toppings on the left.
	 * @param right The number of toppings on the right.
	 * @return
	 */
	private boolean toppingValidator(Topping.ToppingLocation loc, int left, int right) {
		
		switch (loc) {
		
			case ToppingLocationLeft:
				
				if (left < 10) {
					return true;
				}
				else {
					return false;
				}
				
			case ToppingLocationRight:
				
				if (right < 10) {
					return true;
				}
				else {
					return false;
				}
				
			case ToppingLocationWhole:
				
				if (Math.max(left, right) < 10) {
					return true;
				}
				else {
					return false;
				}
			
		}
		
		return false;
	}

}
