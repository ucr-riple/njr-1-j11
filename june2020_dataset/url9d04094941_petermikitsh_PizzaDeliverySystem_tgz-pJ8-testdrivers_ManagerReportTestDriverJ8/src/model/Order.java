package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.FoodItem.FoodItemStatus;
import model.database.Database;
import ninja.Kitchen;
import ninja.SystemTime;

/**
 * A class that represents an order of food items.
 * 
 * @author Casey Klimkowsky cek3403@g.rit.edu
 * @author Isioma Nnodum iun4534@rit.edu
 */
public class Order implements Cerealizable<Order>, Comparable<Order> {

	/**
	 * Database of past orders.
	 */
	private static Database<Order> _orderDatabase;

	/**
	 * Used to format order ID numbers.
	 */
	private DecimalFormat orderIdDf = new DecimalFormat( "000000" );

	/**
	 * The food items contained in the order.
	 */
	private ArrayList<FoodItem> foodItems;

	/**
	 * Unique order ID number.
	 * 
	 * @see generateOrderId;
	 */
	private int orderId;

	/**
	 * The customer who placed the order. Contains the customer's name and phone
	 * number.
	 */
	private Customer customer;

	/**
	 * The time the order was taken (sent to the kitchen).
	 */
	private long timeOrdered;

	/**
	 * The time the order was delivered.
	 */
	private long timeDelivered;

	/**
	 * The preparation stages for an order.
	 */
	public enum OrderStatus {

		AwaitingPreparation, InTheKitchen, AwaitingDelivery, EnRoute, Delivered;

		public String toString() {

			String returnString = "";

			switch (this) {

			case AwaitingPreparation:
				returnString = "Awaiting Preparation";
				break;
			case InTheKitchen:
				returnString = "In the Kitchen";
				break;
			case AwaitingDelivery:
				returnString = "Awaiting Delivery";
				break;
			case EnRoute:
				returnString = "En Route";
				break;
			case Delivered:
				returnString = "Delivered";
				break;

			}

			return returnString;

		}

	}

	/**
	 * The stage of preparation the order is currently in.
	 */
	private OrderStatus status;

	/**
	 * Constructor for an order.
	 */
	public Order() {

		this.foodItems = new ArrayList<FoodItem>();
		this.status = OrderStatus.AwaitingPreparation;
		
		// Initialize the order id to -1, which is a temporary value that
		// will be replaced as soon as the order is requested.
		this.orderId = -1;

	}

	/**
	 * Constructor for an order.
	 * 
	 * @param foodItems
	 *            the FoodItems contained in the Order
	 * @param customer
	 *            the Customer who placed the Order
	 */
	public Order(ArrayList<FoodItem> foodItems, Customer customer) {

		this.foodItems = foodItems;
		this.customer = customer;
		this.status = OrderStatus.AwaitingPreparation;
		
		// Initialize the order id to -1, which is a temporary value that
		// will be replaced as soon as the order is requested.
		this.orderId = -1;

	}

	/**
	 * Gets the list of food items in the order.
	 * 
	 * @return the ArrayList of FoodItems in the Order
	 */
	
	public ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}

	/**
	 * Sets the food items contained in the order to the given list of food
	 * items.
	 * 
	 * @param foodItems
	 *            the list of FoodItems to assign to the Order
	 */
	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	/**
	 * Gets the order ID.
	 * 
	 * @return the Order's ID number
	 */
	public int getOrderId() {
		// If the value is -1, generate a new orderId.
		if( orderId == -1 ) {
			orderId = generateOrderId();
		}
		return orderId;
	}
	
	/**
	 * Sets the order ID.
	 */
	public void setOrderId( int orderId ) {
		this.orderId = orderId;
	}

	/**
	 * Generates a unique order ID.
	 * 
	 * @return the generated ID number
	 */
	private static int generateOrderId() {

		// Get an ID from the Order database.
		return Order.getDb().getCounter();

	}

	/**
	 * Gets the customer who placed the order.
	 * 
	 * @return the Customer who placed the order
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Assigns the given customer to the order.
	 * 
	 * @param customer
	 *            the Customer to set to the Order
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Gets the time the order was placed.
	 * 
	 * @return the time the order was placed
	 */
	public long getTimeOrdered() {
		return timeOrdered;
	}

	/**
	 * Sets the time the order was placed to the given time in epoch time.
	 * 
	 * @param orderTime
	 *            a String representing the order time
	 */
	public void setTimeOrdered(long timeOrdered) {
		this.timeOrdered = timeOrdered;
	}

	/**
	 * Gets the time the order was delivered.
	 * 
	 * @return the delivery time of the order
	 */
	public long getTimeDelivered() {
		return timeDelivered;
	}

	/**
	 * Sets the time the order was delivered to the given time in epoch time.
	 * 
	 * @param timeDelivered
	 *            a String representing the order delivery time
	 */
	public void setTimeDelivered(long timeDelivered) {
		this.timeDelivered = timeDelivered;
	}

	/**
	 * Gets the time from order call-in to delivery.
	 * 
	 * @return the difference between the time ordered and the time delivered
	 */
	public long getTotalTime() {		
		return getTimeDelivered() - getTimeOrdered();
	}

	/**
	 * Gets the current status of the order.
	 * 
	 * @return the current status of the Order
	 */
	public OrderStatus getStatus() {

		if( (status != OrderStatus.Delivered )
				&& ( status != OrderStatus.EnRoute ) ) {

			if (foodItems.size() != 0) {

				boolean awaitingPrep = true;
				boolean awaitingDel = true;

				// The order is in the kitchen...
				status = OrderStatus.InTheKitchen;

				// ...unless all of the order's food items are awaiting
				// preparation.
				for (FoodItem f : foodItems) {
					if (f.getStatus() != FoodItemStatus.AwaitingPreparation) {
						awaitingPrep = false;
						break;
					}
				}

				if (awaitingPrep) {
					status = OrderStatus.AwaitingPreparation;
					awaitingDel = false;
				} else {

					// If the order is not awaiting preparation, then it is in
					// the
					// kitchen unless all of its food items are awaiting
					// delivery.
					for (FoodItem f : foodItems) {
						if (f.getStatus() != FoodItemStatus.AwaitingDelivery) {
							awaitingDel = false;
							break;
						}
					}

					if (awaitingDel) {
						status = OrderStatus.AwaitingDelivery;
					}
				}

			}
		}

		return status;
	}

	/**
	 * Sets the status of the order to the given order.
	 * 
	 * @param status
	 *            the status to set the Order to
	 */
	public void setStatus(OrderStatus status) {

		this.status = status;

		if (status.equals(OrderStatus.Delivered)) {
		} 
		
		switch (status) {
		case Delivered:
		setTimeDelivered(SystemTime.getTime());
			break;
		case EnRoute:
			break;
		}

	}

	/**
	 * Calculates the time the Order started being prepared based on the
	 * FoodItem in the Order with the earliest preparation start time.
	 */
	public long getPrepStartTime() {

		long orderPrepStartTime = foodItems.get(0).getPreparationStartTime();

		for (FoodItem item : foodItems) {

			if (item.getPreparationStartTime() < orderPrepStartTime)
				orderPrepStartTime = item.getPreparationStartTime();

		}

		return orderPrepStartTime;

	}

	/**
	 * Calculates the time the Order started being cooked based on the FoodItem
	 * in the Order with the earliest cooking start time.
	 */
	public long getCookStartTime() {

		long orderCookStartTime = foodItems.get(0).getCookStartTime();

		for (FoodItem item : foodItems) {

			if (item.getCookStartTime() < orderCookStartTime)
				orderCookStartTime = item.getCookStartTime();

		}

		return orderCookStartTime;

	}

	/**
	 * Gets the name of the customer who placed the order.
	 * 
	 * @return the name of the Customer
	 */
	public String getCustomerName() {
		return customer.getName();
	}

	/**
	 * Gets the delivery location of the order.
	 * 
	 * @return the delivery address of the Order
	 */
	public Address getDeliveryLocation() {
		return customer.getStreetAddress();
	}

	/**
	 * Gets the preparation time for the order.
	 * 
	 * @return the preparation time for the Order
	 */
	public int getPreparationTime() {

		// The estimated prep time will be the total prep time for the order
		// divided by the current number of cooks.
		String numCooksStr = Configuration.getDb().get( Kitchen.COOK_CONFIG_NAME.hashCode() ).getValue();
		int numCooks = Integer.parseInt( numCooksStr );
		
		int totalPrepTime = 0;
		for (FoodItem item : foodItems) {

			totalPrepTime += item.getPrepTime();

		}

		return totalPrepTime / numCooks;
		
	}

	/**
	 * Gets the time required to cook the order.
	 * 
	 * @return the cooking time for the Order
	 */
	public int getCookingTime() {

		// The estimated cook time will be the total cook time for the order
		// divided by the current number of ovens.
		String numOvensStr = Configuration.getDb().get( Kitchen.OVEN_CONFIG_NAME.hashCode() ).getValue();
		int numOven = Integer.parseInt( numOvensStr );

		int totalCookTime = 0;
		for (FoodItem item : foodItems) {

			totalCookTime += item.getCookTime();

		}

		return totalCookTime / numOven;

	}
	/**
	 * get the actual time this order took start preparation
	 * time ordered - the time the food item took to prepare
	 * @return
	 */
	public long getTimeWaitingForPreparation() {
		
		long maxTime = 0;
		for ( FoodItem item : foodItems) {
			long time = item.getPreparationStartTime() - timeOrdered;
			if ( time > maxTime ) {
				maxTime =  time;
			}
		}
		
		return maxTime;
		
	}

	/**
	 * Calculates the average time the items in the order spent waiting to be
	 * cooked.
	 * 
	 * @return the average time items spent waiting to be cooked
	 */
	public long getCookWaitTime() {

		// TODO to this based on the time it finished being prepped instead of the time it started prep
		long maxTime = 0;
		
		for ( FoodItem item : foodItems) {
			long time = item.getCookStartTime() - item.getPreparationStartTime();
			if ( time > maxTime ) {
				maxTime =  time;
			}
		}
		
		return maxTime;

	}

	/**
	 * Calculates the time the items in the order spent waiting to be
	 * delivered.
	 * 
	 * @return the time items spent waiting to be delivered
	 */
	public long getDeliveryWaitTime() {
		
		long maxTime = 0;
		
		for ( FoodItem item : foodItems) {
			long time = item.getDeliveryStartTime() - item.getDeliveryWaitStartTime();
			if ( time > maxTime ) {
				maxTime =  time;
			}
		}
		
		return maxTime;

	}

	/**
	 * Calculates the estimated number of minutes it will take for the order to
	 * be delivered.
	 * 
	 * @return the estimated number of minutes until the order is delivered
	 */
	public int calculateEstimatedDeliveryWaitTime() {

		int estimatedDeliveryTime = getPreparationTime()
				+ getCookingTime()
				+ getDeliveryLocation()
						.getTimeToLocation();

		return estimatedDeliveryTime;

		// TODO implement a better algorithm?

	}

	/**
	 * Estimates the time that the order will be delivered.
	 * 
	 * @return the estimated time of delivery
	 */
	public long calculateEstimatedDeliveryTime() {
		// TODO should this depend on the time scale?
		// convertToMilliseconds didn't convert the minutes correctly.
		return getTimeOrdered() + (calculateEstimatedDeliveryWaitTime() * 60);
	}

	/**
	 * Calculates the total oven space units required for the order.
	 * 
	 * @return an int representing the required number of oven space units
	 */
	public int calculateOvenSpaceUnitsRequired() {

		int ovenSpaceUnitsRequired = 0;

		for (FoodItem item : this.foodItems)
			ovenSpaceUnitsRequired += item.getOvenSpaceUnits();

		return ovenSpaceUnitsRequired;

	}

	/**
	 * Calculates the cost of the entire order (without tax).
	 * 
	 * @return the cost of the Order
	 */
	public double calculateSubtotal() {

		double orderCost = 0.00;

		for (FoodItem item : foodItems)
			orderCost += item.getPrice();

		return orderCost;

	}
	
	/**
	 * Calculates the cost of the entire order (with tax).
	 * 
	 * @return the cost of the Order
	 */
	public double calculateTotal() {
		return calculateSubtotal() + getTax();
	}

	/**
	 * Calculates the amount of tax charged on the order.
	 * 
	 * @return tax
	 */
	public double getTax() {
		double taxMultiplier = ( Double.parseDouble( Configuration.getDb().get( 
				Kitchen.TAX_CONFIG_NAME.hashCode() ).getValue() ) ) / 100.0;
		return calculateSubtotal() * taxMultiplier;
	}
	
	/**
	 * Returns a formatted string representaiton of the order's id number.
	 */
	public String getFormattedOrderId() {
		return orderIdDf.format( orderId );
	}
	
	/**
	 * Adds a food item to the order.
	 * 
	 * @param item
	 *            the FoodItem to add to the Order
	 */
	public void addFoodItem(FoodItem item) {
		foodItems.add(item);
	}

	/**
	 * Removes a food item from the order.
	 * 
	 * @param item
	 *            the FoodItem to remove from the Order.
	 */
	public void removeFoodItem(FoodItem item) {
		foodItems.remove(item);
	}

	/**
	 * Returns this model's database.
	 * 
	 * @return the database containing all past orders
	 */
	public static Database<Order> getDb() {

		if (null == _orderDatabase)
			_orderDatabase = new Database<Order>("order");

		return _orderDatabase;

	}

	/**
	 * Get the unique key for this object (the order ID).
	 */
	public int getKey() {
		return getOrderId();
	}

	@Override
	/**
	 * compareTo: necessary for implementing Comparable<Order>.
	 * 
	 * Returns an integer less than zero if this order was created before
	 * another order; zero if the two dates are equal, and integer greater
	 * than zero if the the current order was created after another order.
	 * 
	 */
	public int compareTo(Order other) {

		int returnValue = 0;

		if (this.timeOrdered > other.getTimeOrdered())
			returnValue = 1;

		else if (this.timeOrdered < other.getTimeOrdered())
			returnValue = -1;

		return returnValue;

	}

	/**
	 * A method to format prices.
	 */
	public static String formatPrice( double price ) {
		
		DecimalFormat priceDf = new DecimalFormat("0.00");
		return priceDf.format( price );
		
	}

}