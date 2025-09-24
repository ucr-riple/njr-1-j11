package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ninja.Time;

/**
 * A class that represents a manager report.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class ManagerReport {
	
	DecimalFormat _averageFormat = new DecimalFormat("0.0");
	DecimalFormat _maxFormat = new DecimalFormat("0.0");
	DecimalFormat _dollarFormat = new DecimalFormat("$0.00");
	
	/**
	 * The list of orders contained in the manager report.
	 */
	private ArrayList<Order> orders;

	/**
	 * Constructor for a ManagerReport. Creates a blank list of orders.
	 */
	public ManagerReport() {
		this.orders = Order.getDb().list();
	}

	/**
	 * Constructor for a ManagerReport from a given list of orders.
	 * 
	 * @param orders
	 *            the list of Orders
	 */
	public ManagerReport(ArrayList<Order> orders) {
		this.orders = orders;
	}

	/**
	 * Sets the list of orders to the given list.
	 */
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	/**
	 * Calculates the average cost per order.
	 * 
	 * @return average cost per order
	 */
	public double calculateAvgCostPerOrder() {

		int numOrders = getNumberOfOrders();

		if (numOrders == 0)
			return 0;

		double totalCost = 0.0;

		for (Order order : this.orders)
			totalCost += order.calculateSubtotal();

		return totalCost / numOrders;

	}

	/**
	 * Calculates the average time the orders spent waiting for preparation.
	 * 
	 * @return average preparation wait time
	 */
	public double calculateAvgTimeWaitingForPreparation() {

		int numOrders = getNumberOfOrders();

		if (numOrders == 0)
			return 0;

		long totalTime = 0;

		for (Order order : orders) {
			totalTime += order.getTimeWaitingForPreparation();
		}
		
		return Time.convertToRealMinutes(totalTime / numOrders);

	}

	/**
	 * Calculates the maximum time an order spent waiting for preparation.
	 * 
	 * @return maximum preparation wait time
	 */
	public double calculateMaxTimeWaitingForPreparation() {

		long maxTime = 0;

		for (Order order : this.orders)
			if (order.getPreparationTime() > maxTime)
				maxTime = order.getTimeWaitingForPreparation();

		return Time.convertToRealMinutes(maxTime);

	}
	
	/**
	 * Calculates the average time the orders spent waiting for cooking.
	 * 
	 * @return average cooking wait time
	 */
	public double calculateAvgTimeWaitingToCook() {

		int numOrders = getNumberOfOrders();

		if (numOrders == 0)
			return 0;

		long totalTime = 0;

		for (Order order : this.orders)
			totalTime += order.getCookWaitTime();

		return Time.convertToRealMinutes(totalTime / numOrders);

	}
	
	/**
	 * Calculates the maximum time an order spent waiting for cooking.
	 * 
	 * @return maximum cooking wait time
	 */
	public double calculateMaxTimeWaitingToCook() {

		long maxTime = 0;

		for (Order order : this.orders)
			if (order.getCookWaitTime() > maxTime)
				maxTime = order.getCookWaitTime();

		return Time.convertToRealMinutes(maxTime);

	}

	/**
	 * Calculates the average time the orders spent waiting for delivery pick
	 * up.
	 * 
	 * @return average delivery wait time
	 */
	public double calculateAvgTimeWaitingToBeRetrievedForDelivery() {

		int numOrders = getNumberOfOrders();

		if (numOrders == 0)
			return 0;

		long totalTime = 0;

		for (Order order : this.orders)
			totalTime += (double) order.getDeliveryWaitTime();

		return Time.convertToRealMinutes(totalTime / numOrders);

	}

	/**
	 * Calculates the maximum time an order spent waiting for delivery pick up.
	 * 
	 * @return maximum delivery wait time
	 */
	public double calculateMaxTimeWaitingToBeRetrievedForDelivery() {

		long maxTime = 0;

		for (Order order : this.orders)
			if (order.getCookingTime() > maxTime)
				maxTime = order.getDeliveryWaitTime();

		return Time.convertToRealMinutes(maxTime);

	}

	/**
	 * Calculates the average time from order call-in to delivery time per
	 * order.
	 * 
	 * @return average time from order call-in to delivery time
	 */
	public double avgTotalTime() {

		int numOrders = getNumberOfOrders();

		if (numOrders == 0) {
			return 0;
		}
		long totalTime = 0;

		for (Order order : this.orders)
			totalTime += (double) order.getTotalTime();

		return Time.convertToRealMinutes(totalTime / numOrders);

	}

	/**
	 * Calculates the maximum time from order call-in to delivery time per
	 * order.
	 * 
	 * @return maximum time from order call-in to delivery time
	 */
	public double maxTotalTime() {

		long maxTime = 0;

		for (Order order : this.orders)
			if (order.getTotalTime() > maxTime) {
				maxTime = order.getTotalTime();
			}

		return Time.convertToRealMinutes(maxTime);

	}

	/**
	 * Calculates the total number of orders in the manager report.
	 * 
	 * @return the number of Orders
	 */
	public int getNumberOfOrders() {
		return orders.size();
	}
	
	/**
	 * Returns a String representation of the number of orders.
	 * 
	 * @return the number of Orders (as a String)
	 */
	public String printNumberOfOrders() {		
		return "" + getNumberOfOrders();
	}
	
	/**
	 * Returns a String representation of the
	 * average cost per order.
	 * 
	 * @return the average cost per Order (as a String)
	 */
	public String printAvgCostPerOrder() {		
		
		double cost = calculateAvgCostPerOrder();
		return _dollarFormat.format(cost);
		
	}
	
	/**
	 * Returns a String representation of the
	 * average time from order call-in to delivery.
	 * 
	 * @return the average time from order call-in 
	 * 				to delivery (as a String)
	 */
	public String printAvgTotalTime() {
		
		double time = avgTotalTime();
		
		String unit = "minutes";
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _maxFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * maximum time from order call-in to delivery.
	 * 
	 * @return the max time from order call-in
	 * 				to delivery (as a String)
	 */
	public String printMaxTotalTime() {
		
		double time = maxTotalTime();
		
		String unit = "minutes";
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _maxFormat.format(time) + " " + unit;
		
	}
		
	/**
	 * Returns a String representation of the
	 * average cook wait time.
	 * 
	 * @return the average cook wait time (as a String)
	 */
	public String printAvgCookingWaitTime() {
		
		double time = calculateAvgTimeWaitingToCook();
		String unit = "minutes";
		
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _averageFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * maximum cook wait time.
	 * 
	 * @return the max cook wait time (as a String)
	 */
	public String printMaxCookingWaitTime() {
		
		double time = calculateMaxTimeWaitingToCook();
		
		String unit = "minutes";
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _maxFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * average time waiting for preparation.
	 * 
	 * @return the average prep wait time (as a String)
	 */
	public String printAvgTimeWaitingForPreparation() {
		
		double time = calculateAvgTimeWaitingForPreparation();
		String unit = "minutes";
		
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _averageFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * maximum time waiting for preparation.
	 * 
	 * @return the max prep wait time (as a String)
	 */
	public String printMaxTimeWaitingForPreparation() {
		
		double time = calculateMaxTimeWaitingForPreparation();
		String unit = "minutes";
		
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _maxFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * average time waiting for delivery.
	 * 
	 * @return the average delivery wait time (as a String)
	 */
	public String printAvgTimeWaitingToBeRetrievedForDelivery() {
		
		double time = calculateAvgTimeWaitingToBeRetrievedForDelivery();
		
		String unit = "minutes";
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		
		return _averageFormat.format(time) + " " + unit;
		
	}
	
	/**
	 * Returns a String representation of the
	 * maximum time waiting for delivery.
	 * 
	 * @return the max delivery wait time (as a String)
	 */
	public String printMaxTimeWaitingToBeRetrievedForDelivery() {
		
		double time = calculateMaxTimeWaitingToBeRetrievedForDelivery();
		
		String unit = "minutes";
		if (time < 1.00) { 
			unit = "seconds";
			time *= 60;
			time = ((time * 100) + 0.5) / 100.0;
		}
		return _maxFormat.format(time) + " " + unit;
		
	}

}
