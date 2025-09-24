package model;

import java.util.Vector;

import ninja.Kitchen;
import ninja.SystemTime;
import ninja.Time;

/**
 * A class that represents an oven to cook food items.
 * 
 * @author  Isioma Nnodum		iun4534@rit.edu
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class Oven {
	
	private int _orderId;

	/**
	 * The oven's capacity.
	 */
	private int spaceUnits = 40;

	/**
	 * A list of orders currently being cooked in the oven.
	 */
	private Vector<FoodItem> _currentFoodItems;

	/**
	 * Constructor for an Oven.
	 */
	public Oven(int id) {
		
		_orderId = id;
		_currentFoodItems = new Vector<FoodItem>();
		
	}

	/**
	 * Constructor for an Oven given a list of orders.
	 * 
	 * @param currentFoodItems
	 *            the list of orders in the oven
	 */
	public Oven(Vector<FoodItem> currentFoodItems) {
		_currentFoodItems = currentFoodItems;
	}

	/**
	 * Determines if the oven has available space units.
	 * 
	 * @param the
	 *            size of the item trying to be added
	 * @return true if the oven has available units, false otherwise
	 */
	public boolean hasAvailableUnits(int attempted) {

		int occupiedUnits = 0;

		for (int i = 0; i < _currentFoodItems.size(); i++) {
			FoodItem item = _currentFoodItems.get(i);
			occupiedUnits += item.getOvenSpaceUnits();
		}

		return occupiedUnits + attempted < spaceUnits ? true : false;

	}

	/**
	 * Gets the amount of space units the oven has available.
	 * 
	 * @return an int representing the Oven's available units
	 */
	public int getAvailableUnits() {

		int occupiedUnits = 0;

		for (FoodItem item : _currentFoodItems) {
			occupiedUnits += item.getOvenSpaceUnits();
		}

		int availableUnits = spaceUnits - occupiedUnits;

		return availableUnits;

	}

	/**
	 * Adds a food item to the oven.
	 * 
	 * @param fi
	 *            the FoodItem to add
	 */
	public void addFoodItem(FoodItem fi) {
		_currentFoodItems.add(fi);
	}

	/**
	 * Removes a food item from the oven.
	 * 
	 * @param fi
	 *            the FoodItem to remove
	 */
	public void removeFoodItem(FoodItem fi) {
		_currentFoodItems.remove(fi);
	}

	/**
	 * Is the oven empty?
	 * 
	 * @return true if the oven is empty, false otherwise
	 */
	public boolean isEmpty() {
		return _currentFoodItems.isEmpty();
	}

	/**
	 * Returns a food item that is done, or none if no such food item exists.
	 * 
	 * @return A finished food item.
	 */
	public FoodItem getCookedFoodItem() {

		FoodItem retFoodItem = null;

		for (int i = 0; i < _currentFoodItems.size(); i++) {

			FoodItem item = _currentFoodItems.get(i);

			// Check if this food item is done cooking.
			long time = SystemTime.getTime()- item.getCookStartTime();
			int timeElapsed = (int) Time.scaleUp(time);
			long cooktime =item.getCookTime();

			// Scale up the elapsed time.
			if (Kitchen.PRINT_DIAGNOSTICS) {
				System.out.println(Time.formatTime(SystemTime.getTime()) + ": " + 
						item.getName() + " in oven " +_orderId+ " for " + 
						(cooktime - timeElapsed) + " more minutes");
			}
			
			// Check to see if the food item is done cooking.
			if (timeElapsed >= cooktime) {
				
				retFoodItem = item;
				_currentFoodItems.remove(i);
				break;
				
			}

		}

		return retFoodItem;

	}

}
