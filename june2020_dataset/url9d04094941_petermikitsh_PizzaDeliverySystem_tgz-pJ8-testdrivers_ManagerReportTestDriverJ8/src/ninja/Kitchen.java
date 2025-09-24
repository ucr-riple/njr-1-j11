package ninja;

import java.util.ArrayList;
import java.util.Vector;

import model.Configuration;
import model.FoodItem;
import model.FoodItem.FoodItemStatus;
import model.Order;
import model.Order.OrderStatus;
import model.Oven;
import model.database.Database;

/**
 * Class that represents a kitchen. There is a kitchen within the kitchen
 * (inception), as well as cooks, ovens, and delivery drivers. The kitchen
 * maintains the orders currently in the kitchen.
 * 
 * @author  Isioma Nnodum	iun4534@g.rit.edu
 */
public class Kitchen {

	/**
	 * Indicates if this component will print diagnostic information.
	 */
	public static final boolean PRINT_DIAGNOSTICS = false;

	/**
	 * The wait time for threads that lock on an array.
	 */
	public static final long KITCHEN_THREAD_WAIT_TIME = 500;

	/**
	 * The name of the cook configuration.
	 */
	public static final String COOK_CONFIG_NAME = "numCooks";

	/**
	 * The name of the oven configuration.
	 */
	public static final String OVEN_CONFIG_NAME = "numOvens";

	/**
	 * The name of the driver configuration.
	 */
	public static final String DRIVER_CONFIG_NAME = "numDrivers";

	/**
	 * The name of the tax configuration.
	 */
	public static final String TAX_CONFIG_NAME = "tax";

	/**
	 * The default number of cooks.
	 */
	private static final int DEFAULT_NUM_COOKS = 1;

	/**
	 * The default number of ovens.
	 */
	private static final int DEFAULT_NUM_OVENS = 1;

	/**
	 * The default number of drivers.
	 */
	private static final int DEFAULT_NUM_DRIVERS = 1;

	/**
	 * The default tax percent.
	 */
	private static final double DEFAULT_TAX = 8.0;

	/**
	 * The list of cooks.
	 */
	private static ArrayList<Cook> _cooks;
	
	/**
	 * The list of drivers.
	 */
	private static ArrayList<Driver> _drivers;
	
	/**
	 * The list of ovens.
	 */
	private static Vector<Oven> _ovens;

	/**
	 * The kitchen's oven runner.
	 */
	private static OvenRunner _ovenRunner;

	/**
	 * The kitchen's delivery runner.
	 */
	private static DeliveryRunner _deliveryRunner;

	/**
	 * The list of the food items that are awaiting preparation.
	 */
	private static Vector<FoodItem> _awaitingPrepFIs;

	/**
	 * The list of the food items that are awaiting the oven.
	 */
	private static Vector<FoodItem> _awaitingOvenFIs;

	/**
	 * The master list of all orders.
	 */
	private static Vector<Order> _allOrders;

	/**
	 * The number of cooks Defaults to 1 if value not found in the configuration
	 * table
	 * 
	 * @see openKitchen(int, int, int);
	 */
	private static int _numCooks;
	
	/**
	 * The number of drivers Defaults to 1 if value not found in the
	 * configuration table
	 * 
	 * @see openKitchen(int, int, int);
	 */
	private static int _numDrivers;
	
	/**
	 * The number of ovens Defaults to 1 if value not found in the configuration
	 * table
	 * 
	 * @see openKitchen(int, int, int);
	 */
	private static int _numOvens;

	/**
	 * Initializes the kitchen.
	 */
	public static void openKitchen() {

		// Put default values in the configs if necessary.
		initializeConfigs();

		// Get the number of ovens, drivers, and cooks from the configuration
		// table.
		Database<Configuration> config = Configuration.getDb();

		// Change the default setting to the new value
		// may throw a number format exception.
		_numOvens = Integer.parseInt(config.get(OVEN_CONFIG_NAME.hashCode())
				.getValue());
		_numDrivers = Integer.parseInt(config
				.get(DRIVER_CONFIG_NAME.hashCode()).getValue());
		_numCooks = Integer.parseInt(config.get(COOK_CONFIG_NAME.hashCode())
				.getValue());

		// Create the storage arrays.
		_allOrders = new Vector<Order>();
		_awaitingPrepFIs = new Vector<FoodItem>();
		_awaitingOvenFIs = new Vector<FoodItem>();

		_ovens = new Vector<Oven>();
		_drivers = new ArrayList<Driver>();
		_cooks = new ArrayList<Cook>();

		// instantiate the ninjas based on the global settings.
		for (int i = 0; i < _numCooks; i++) {
			_cooks.add(new Cook(i));
		}
		for (int i = 0; i < _numOvens; i++) {
			_ovens.add(new Oven(i));
		}
		for (int i = 0; i < _numDrivers; i++) {
			_drivers.add(new Driver(i));
		}

		_ovenRunner = new OvenRunner();
		_deliveryRunner = new DeliveryRunner();

		// Start the cooks, drivers, and oven runners.
		for (int i = 0; i < _numCooks; i++) {
			_cooks.get(i).start();
		}
		for (int i = 0; i < _numDrivers; i++) {
			_drivers.get(i).start();
		}

		_ovenRunner.start();
		_deliveryRunner.start();

	}

	/**
	 * Does the setup on the various config settings.
	 */
	private static void initializeConfigs() {

		// For each of the configs, check to make sure it is not null. If it is,
		// supply a default value.
		Database<Configuration> config = Configuration.getDb();

		Configuration ovens = config.get(OVEN_CONFIG_NAME.hashCode());
		Configuration drivers = config.get(DRIVER_CONFIG_NAME.hashCode());
		Configuration cooks = config.get(COOK_CONFIG_NAME.hashCode());
		Configuration tax = config.get(TAX_CONFIG_NAME.hashCode());

		if (cooks == null) {
			cooks = new Configuration(COOK_CONFIG_NAME, "" + DEFAULT_NUM_COOKS);
			config.add(cooks);
		}

		if (ovens == null) {
			ovens = new Configuration(OVEN_CONFIG_NAME, "" + DEFAULT_NUM_OVENS);
			config.add(ovens);
		}

		if (drivers == null) {
			drivers = new Configuration(DRIVER_CONFIG_NAME, ""
					+ DEFAULT_NUM_DRIVERS);
			config.add(drivers);
		}

		if (tax == null) {
			tax = new Configuration(TAX_CONFIG_NAME, "" + DEFAULT_TAX);
			config.add(tax);
		}

	}

	/**
	 * Shuts down the kitchen.
	 */
	public static void closeKitchen() {

		// Tell the cooks, runners, and drivers to finish up their work and go
		// home.
		if (null != _drivers)
			for (Driver driver : _drivers) {
				driver.deactivate();
			}
		if (null != _cooks)
			for (Cook cook : _cooks) {
				cook.deactivate();
			}
		if (null != _ovenRunner)
			_ovenRunner.deactivate();
		if (null != _deliveryRunner)
			_deliveryRunner.deactivate();

	}

	/**
	 * Gets the list of cooks.
	 */
	public static ArrayList<Cook> getCooks() {
		return _cooks;
	}

	/**
	 * Gets the list of drivers.
	 */
	public static ArrayList<Driver> getDrivers() {
		return _drivers;
	}

	/**
	 * Gets the list of ovens.
	 */
	public static Vector<Oven> getOvens() {
		return _ovens;
	}

	/**
	 * Gets the list of delivery runners.
	 */
	public static DeliveryRunner getDeliveryRunner() {
		return _deliveryRunner;
	}

	/**
	 * Gets the list of oven runners.
	 */
	public static OvenRunner getOvenRunner() {
		return _ovenRunner;
	}

	/**
	 * Gets the list of all orders currently in the kitchen.
	 */
	public static Vector<Order> getOrders() {
		return _allOrders;
	}

	/**
	 * Adds an order to the kitchen system.
	 * 
	 * @param order
	 *            The new order.
	 */
	public static void addOrder(Order order) {

		// Mark the order start time.
		order.setTimeOrdered( SystemTime.getTime() );
		
		// Add the order to the list of orders.
		_allOrders.add(order);

		// Send the order to be broken into food items.
		Kitchen.decomposeOrder(order);

	}

	/**
	 * Breaks an order into constituent FIs and puts them in the right place.
	 * 
	 * @param order
	 */
	private static void decomposeOrder(Order order) {

		// For now, just put all of the food items in the awaiting prep
		// list.
		for (FoodItem item : order.getFoodItems()) {

			// Set the status of the food item "Awaiting Prep", time stamp it,
			// and add it to the queue.
			item.setStatus(FoodItemStatus.AwaitingPreparation);
			_awaitingPrepFIs.add(item);

		}

	}

	/**
	 * Removes an order from the kitchen.
	 * 
	 * @param order
	 *            The order to remove.
	 */
	public static void removeOrder(Order order) {

		// Remove from the all orders list.
		_allOrders.remove(order);

		// Also remove each food item from the awaiting prep list.
		for (FoodItem item : order.getFoodItems()) {

			_awaitingPrepFIs.remove(item);

		}

	}
	
	/**
	 * Sends a food item to the awaiting oven list.
	 * 
	 * @param item
	 *            The FI to send.
	 */
	public static void sendToAwaitingOven(FoodItem item) {

		// Add to the list.
		item.setStatus(FoodItemStatus.AwaitingOven);
		_awaitingOvenFIs.add(item);

	}

	/**
	 * Sends a food item to the given oven.
	 * 
	 * @param item
	 *            The FI to send.
	 * @param oven
	 *            The oven to add to.
	 */
	public static void sendToOven(FoodItem item, Oven oven) {

		// Add the food item to the oven, and set a time stamp on the food item.
		item.setStatus(FoodItemStatus.Cooking);
		oven.addFoodItem(item);

	}

	/**
	 * Sends a food item to the awaiting delivery list. (Actually just marks the
	 * food item as "awaiting delivery".
	 * 
	 * @param item
	 *            The FI to send.
	 */
	public synchronized static void sendToWarmingArea(FoodItem item) {

		// Set a time stamp and mark the item as awaiting delivery.
		item.setStatus(FoodItemStatus.AwaitingDelivery);

	}

	/**
	 * Called when a cook wants a food item.
	 * 
	 * @return A food item if there is one awaiting prep, or null if not.
	 */
	public synchronized static FoodItem cookRetrieve() {

		FoodItem retItem = null;

		if (!_awaitingPrepFIs.isEmpty()) {

			retItem = _awaitingPrepFIs.firstElement();
			_awaitingPrepFIs.remove(retItem);

		}

		return retItem;

	}

	/**
	 * Called when an oven runner wants a food item.
	 * 
	 * @return A food item if there is one awaiting ovens, or null if not.
	 */
	public static FoodItem ovenRunnerRetrieve() {

		FoodItem retItem = null;

		if (!_awaitingOvenFIs.isEmpty()) {

			retItem = _awaitingOvenFIs.firstElement();
			_awaitingOvenFIs.remove(retItem);
		}

		return retItem;

	}

	/**
	 * Returns an oven with the specified number of oven units available.
	 * 
	 * @param ovUnits
	 *            The needed number of oven units.
	 * @return An appropriate oven, or null if no such oven exists now.
	 */
	public static Oven getAvailableOven(int ovUnits) {

		// Search through the ovens to find one with enough space.
		Oven retOven = null;
		for (Oven ov : _ovens) {

			if (ov.hasAvailableUnits(ovUnits)) {
				retOven = ov;
				break;
			}

		}

		return retOven;

	}

	/**
	 * Called when a delivery runner wants a food item from the ovens.
	 * 
	 * @return A food item if there is one awaiting prep, or null if not.
	 */
	public static FoodItem deliveryRunnerRetrieve() {

		// Check all of the ovens for a food item that is done.
		FoodItem retItem = null;
		for (int i = 0; i < _ovens.size(); i++) {
			Oven ov = _ovens.get(i);

			// Try to get a completed food item from the oven.
			FoodItem fi = ov.getCookedFoodItem();
			if (fi != null) {
				retItem = fi;
				break;
			}

		}

		return retItem;

	}

	/**
	 * Called when a driver wants a completed order to deliver.
	 * 
	 * @return A collection of orders if there are some that is completed, or an
	 *         empty list.
	 */
	public synchronized static ArrayList<Order> driverRetrieve() {

		ArrayList<Order> retOrders = new ArrayList<Order>();

		// Try to get several orders that are going to the same place.
		String deliveryAddress = _allOrders.isEmpty() ? null : _allOrders.get(0).getDeliveryLocation().getLocation();

		ArrayList<Order> tmpOrders = new ArrayList<Order>(_allOrders);
		for (int i = 0; i < tmpOrders.size(); i++) {

			Order order = tmpOrders.get(i);

			// Check if the order is awaiting delivery.
			if (order.getStatus() == OrderStatus.AwaitingDelivery) {
				// Check if the order is going to the same place as this
				// delivery.
				String thisDelAdd = order.getCustomer().getStreetAddress()
						.getLocation();

				if (thisDelAdd.equalsIgnoreCase(deliveryAddress)) {
					// Set all orders to EnRoute.
					order.setStatus(OrderStatus.EnRoute);
					retOrders.add(order);

				}

			}
			
		}
		
		return retOrders;

	}

	/**
	 * Called when a driver has delivered an order.
	 * 
	 * @param order
	 *            The order that was delivered.
	 */
	public synchronized static void orderDelivered(Order order) {

		// Remove the order from the list and put it in the past order database.
		order.setStatus(Order.OrderStatus.Delivered);
		removeOrder(order);
		Order.getDb().add(order);
		
		if (Kitchen.PRINT_DIAGNOSTICS) {
			System.out.println(Time.formatTime(SystemTime.getTime())
					+ ": Order " + order.getOrderId() + " to "
					+ order.getDeliveryLocation().getLocation()
					+ " has been Delivered");
			
		}

	}

}
