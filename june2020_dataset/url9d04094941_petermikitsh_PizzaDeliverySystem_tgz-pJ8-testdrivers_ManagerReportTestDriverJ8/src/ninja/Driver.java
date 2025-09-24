package ninja;

import java.util.ArrayList;

import model.Address;
import model.Order;

/**
 * A Driver will deliver Orders that have the same delivery location.
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 * 
 */
public class Driver extends Thread {
	
	private int _driverId;

	/**
	 * represents the state of this driver
	 */
	private boolean _running;

	/**
	 * The orders this driver is delivering
	 */
	private ArrayList<Order> _currentOrders;
	
	/**
	 * The location to which this driver is traveling null if the driver is idle.
	 */
	private Address _deliveryLocation;
	
	/**
	 * Indicates the time at which this driver started its journey to the
	 *  delivery location.
	 */
	private long _startTime;

	/**
	 * Create a new driver
	 */
	public Driver(int id) {
		
		_driverId = id;
		_running = false;
		_currentOrders = new ArrayList<Order>();
		_deliveryLocation = null;
		
	}

	/**
	 * Stop this driver
	 */
	public void deactivate() {
		_running = false;

	}

	/**
	 * Get the next list of orders going to the same location
	 */
	private void retreive() {

		while ((0 == _currentOrders.size()) && _running) {
			
			_currentOrders = Kitchen.driverRetrieve();
			
			// Sleep for a bit.
        	try {
        		Thread.sleep( Kitchen.KITCHEN_THREAD_WAIT_TIME );
        	} catch( InterruptedException ex ) {}
        	
		}
		
		if( Kitchen.PRINT_DIAGNOSTICS ) {
			
			System.out.println( Time.formatTime(SystemTime.getTime()) + ": " +
					"Driver "+ _driverId +": Retrieved "+ _currentOrders.size() +
					" order(s) for delivery going to:" + _currentOrders.get(0).getDeliveryLocation().getLocation());
			
			for (Order order : _currentOrders ) {
				System.out.println("\t" + order.getOrderId());
			}
			
		}
		
	}

	/**
	 * Deliver those orders.
	 */
	private void deliver() {

		// Since the driver is going to a single location it will take as long
		// as it take to deliver a single item to deliver all of them.
		_deliveryLocation = _currentOrders.get(0).getDeliveryLocation();
		long timeToLocation = Time.scaleUp(Time.convertToMilliseconds( _deliveryLocation.getTimeToLocation()) );
		
		// Mark the departure time.
		_startTime = SystemTime.getTime();
		
		if( Kitchen.PRINT_DIAGNOSTICS ) {
			System.out.println( Time.formatTime(SystemTime.getTime()) + ": " +"Driver "+ _driverId +": Enroute to:");
			System.out.println("\t" + _deliveryLocation.getLocation());
			System.out.println("\t\t Will be at location in " + _deliveryLocation.getTimeToLocation() / 2 + " minutes");
			System.out.println("\t\t Will be at location in " + timeToLocation / 2 + " ninja milliseconds");
		}
		
		// Sleep for the first half of the delivery time.
		try {
			Thread.sleep( timeToLocation / 2);
		} catch (InterruptedException ex) {
			System.err.println(ex.getMessage());
		}
		
		// Set all orders to delivered.
		for (Order currentOrder : _currentOrders) {

			// Tell the kitchen that we delivered this order.
			Kitchen.orderDelivered( currentOrder );
			
			if( Kitchen.PRINT_DIAGNOSTICS ) {
				System.out.println( Time.formatTime(SystemTime.getTime()) + ": " + 
						"Driver "+ _driverId +": Delivered order: " + currentOrder.getOrderId());
			}
			
		}
		
		if( Kitchen.PRINT_DIAGNOSTICS ) {
			
			System.out.println( Time.formatTime(SystemTime.getTime()) + ": " +"Driver "+ _driverId +": Returning from:");
			System.out.println("\t" + _deliveryLocation.getLocation());
			System.out.println("\t\t Will be back home in " + _deliveryLocation.getTimeToLocation() / 2  + " minutes");
			System.out.println("\t\t Will be back home in " + timeToLocation / 2 + " ninja milliseconds");
			
		}
		
		// Sleep for the second half of the delivery time (coming back to shop).
		try {
			Thread.sleep( timeToLocation / 2 );
		} catch (InterruptedException ex) {
			System.err.println(ex.getMessage());
		}
		
		if( Kitchen.PRINT_DIAGNOSTICS ) {
			System.out.println( Time.formatTime(SystemTime.getTime()) + ": " +"Driver "+ _driverId +": Now back home.");
		}

		// Reset the storage variables.
		_currentOrders.clear();
		_deliveryLocation = null;

	}

	/**
	 * Activate this driver.
	 */
	public void run() {
		
		_running = true;
		
		while (_running) {
			retreive();

			if (0 != _currentOrders.size()) {
				deliver();
			}

		}
		
	}
	
	/**
	 * A getter for the location to which this driver is driving.
	 */
	public Address getCurrentDeliveryLocation() {
		return _deliveryLocation;
	}
	
	/**
	 * Returns an (epoc) time stamp that indicates when this driver started
	 *  its journey.
	 */
	public long getStartTime() {
		return _startTime;
	}
	
}
