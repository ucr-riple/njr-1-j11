/*
 * FoodItem.java
 */

package model;

import java.text.DecimalFormat;

import ninja.SystemTime;


/**
 * A class that represents a food item.
 * 
 * @author   Casey Klimkowsky   cek3403@g.rit.edu
 */
public abstract class FoodItem implements Cerealizable<FoodItem>, Comparable<FoodItem> {
	
	/**
	 * Used to format prices.
	 */
	DecimalFormat df = new DecimalFormat( "0.00" );
	
	/**
	 * The name of the food item.
	 */
	protected String name;
	
	/**
	 * How much the food item costs.
	 */
	protected double price;
	
	/**
	 * How long it takes for the food item to be prepared.
	 */
	private int prepTime;
	
	/**
	 * How long it takes for the food item to cook.
	 */
	private int cookTime;
	
	/**
	 * How many oven space units the food item uses.
	 */
	private int ovenSpaceUnits;
	
	/**
	 * The time at which the food began being prepared
	 */
	private long preparationStartTime;
	
	/**
	 * The time at which the food begin cooking.
	 */
	private long cookStartTime;
	
	/**
	 * The time at which the food was picked up by a driver to
	 * be delivered.
	 */
	private long deliveryStartTime;
	
	/*
	 * The time at which the food began waiting for preparation.
	 */
	private long prepWaitStartTime;
	
	/*
	 * The time at which the food began waiting to be cooked.
	 */
	private long cookWaitStartTime;
	
	/*
	 * The time at which the food began waitng to be delivered.
	 */
	private long deliveryWaitStartTime;
	
	/*
	 * The status of the foodItem in the kitchen.
	 */
	private FoodItemStatus status;
	
	public enum FoodItemStatus {
		
		AwaitingPreparation,
		Preparing,
		AwaitingOven,
		Cooking,
		AwaitingDelivery,
		EnRoute,
		Delivered;
		
		public String toString() {
			
			String retStr = "";
			
			switch( this ) {

			case AwaitingPreparation:
				retStr = "Awaiting Preparation";
				break;
			case Preparing:
				retStr = "Preparing";
				break;
			case AwaitingOven:
				retStr = "Awaiting Cooking";
				break;
			case Cooking:
				retStr = "In Oven";
				break;
			case AwaitingDelivery:
				retStr = "Awaiting Delivery";
				break;
			case EnRoute:
				retStr = "En Route";
				break;
			case Delivered:
				retStr = "Delivered";
				break;
			
			}
			
			return retStr;
		}
	}
	
	/**
	 * Constructor for a FoodItem.
	 */
	public FoodItem() {}
	
	/**
	 * Constructor for a FoodItem; to be used in Topping class.
	 * 
	 * @param name   the food item's name
	 * @param price   the food item's price
	 */
	public FoodItem(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	/**
	 * Constructor for a FoodItem.
	 * 
	 * @param name   the food item's name
	 * @param price   the food item's price
	 * @param prepTime   the food item's preparation time
	 * @param cookTime   the food item's cook time
	 * @param ovenSpaceUnits   the food item's oven space units
	 * @param availableForMenu   is the food item on the menu?
	 */
	public FoodItem( String name, double price, int prepTime, int cookTime, int ovenSpaceUnits ) {
		
		this.name = name;
		this.price = price;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.ovenSpaceUnits = ovenSpaceUnits;
		
	}
	
	/**
	 * Gets the food item's name.
	 * 
	 * @return   the food item's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the food item's name to the given String.
	 * 
	 * @param name   String to set the food item's name to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the food item's price.
	 * 
	 * @return   the food item's price
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Calculates the food item's price.
	 * 
	 * @return the food item's price based on related variables
	 */
	public abstract double calculatePrice();

	
	/**
	 * Sets the food item's price to the given double (in dollars and cents).
	 * 
	 * @param price   double to set the food item's price to
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Gets the food item's preparation time.
	 * 
	 * @return   the food item's prep time
	 */
	public int getPrepTime() {
		return prepTime;
	}
	
	/**
	 * Sets the food item's preparation time to the given int (in minutes).
	 * 
	 * @param prepTime   number of minutes to set the food item's preparation time to
	 */
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}
	
	/**
	 * Gets the food item's cook time.
	 * 
	 * @return   the food item's cook time
	 */
	public int getCookTime() {
		return cookTime;
	}
	
	/**
	 * Sets the food item's cook time to the given int (in minutes).
	 * 
	 * @param cookTime   number of minutes to set the food item's cook time to
	 */
	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}
	
	/**
	 * Gets the food item's oven space units.
	 * 
	 * @return   the food item's oven space units
	 */
	public int getOvenSpaceUnits() {
		return ovenSpaceUnits;
	}
	
	/**
	 * Sets the food item's oven space units to the given int.
	 * 
	 * @param ovenSpaceUnits   int to set the food item's oven space units to
	 */
	public void setOvenSpaceUnits(int ovenSpaceUnits) {
		this.ovenSpaceUnits = ovenSpaceUnits;
	}
	
	/**
	 * Gets the prep start time
	 * 
	 * @return The time food started being prepared
	 */
	public long getPreparationStartTime(){
		return preparationStartTime;
	}
	
	/**
	 * Sets the prep start time
	 * 
	 * @param The time that the item began being prepared.
	 */
	public void setPreparationStartTime(long time){
		preparationStartTime = time;
	}
	
	/**
	 * Gets the cook start time
	 * 
	 * @return The time food started being cooked.
	 */
	public long getCookStartTime(){
		return cookStartTime;
	}
	
	/**
	 * Sets the cook start time
	 * 
	 * @param The time that the item began being cooked.
	 */
	public void setCookStartTime(long time){
		cookStartTime = time;
	}
	
	/**
	 * Gets the delivery start time
	 * 
	 * @return The time food started being delivered
	 */
	public long getDeliveryStartTime(){
		return deliveryStartTime;
	}
	
	/**
	 * Sets the delivery start time
	 * 
	 * @param The time that the item began being delivered.
	 */
	public void setDeliveryStartTime(long time){
		deliveryStartTime = time;
	}
	
	/**
	 * Gets the time when the food began waiting to be prepared.
	 * 
	 * @return The time when the food started waiting to be 
	 * prepared.
	 */
	public long getPrepWaitStartTime(){
		return prepWaitStartTime;
	}
	
	/**
	 * Sets the time when the food began waiting to be prepared.
	 * 
	 * @param The time the food begins waiting to be prepared.
	 */
	public void setAwaitingPreparationStartTime(long time){
		prepWaitStartTime = time;
	}
	
	/**
	 * Gets the time when the food began waiting to be cooked.
	 * 
	 * @return The time when the food started waiting to be 
	 * cooked.
	 */
	public long getCookWaitStartTime(){
		return cookWaitStartTime;
	}
	
	/**
	 * Sets the time when the food began waiting to be cooked.
	 * 
	 * @param The time the food begins waiting to be cooked.
	 */
	public void setAwaitingOvenStartTime(long time){
		cookWaitStartTime = time;
	}
	
	/**
	 * Gets the time when the food began waiting to be delivered.
	 * 
	 * @return The time when the food started waiting to be 
	 * deivered.
	 */
	public long getDeliveryWaitStartTime(){
		return deliveryWaitStartTime;
	}
	
	/**
	 * Sets the time when the food began waiting to be delivered.
	 * 
	 * @param The time the food begins waiting to be delivered.
	 */
	public void setAwaitingDeliveryStartTime(long time){
		deliveryWaitStartTime = time;
	}
	
	
	/**
	 * Gets the status of foodItem at the time based on where each
	 * foodItem is at the time.
	 * 
	 * @return State that the foodItem is in at the time
	 */
	public FoodItemStatus getStatus(){
		return status;
	}
	
	/**
	 * Sets the status of this food item.
	 */
	public void setStatus( FoodItemStatus status ) {
		this.status = status;
		
		switch (status) {
		case Cooking:
			this.setCookStartTime(SystemTime.getTime());
			break;
		case AwaitingPreparation:
			this.setAwaitingPreparationStartTime(SystemTime.getTime());
			break;
		case Preparing:
			this.setPreparationStartTime(SystemTime.getTime());
			break;
		case AwaitingOven:
			this.setAwaitingOvenStartTime(SystemTime.getTime());
			break;
		case AwaitingDelivery:
			this.setAwaitingDeliveryStartTime(SystemTime.getTime());
			break;
		}
		
	}
	
	/**
	 * Hashes the food item's name to be used as a key.
	 * 
	 * @return   the hash code made using the FoodItem's name
	 */
	public int getKey() {
		return this.name.hashCode();
	}
	
	/**
	 * Returns a formatted string representation of the food item's price.
	 * 
	 * @return   the formatted String representation
	 */
	public String getFormattedPrice() {
		double price = this.calculatePrice();
		return df.format(price);
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo( FoodItem other ) {
		return this.getName().compareTo( other.getName() );
	}
	

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getKey();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals( Object other ) {
		return (other instanceof FoodItem) && 
				( ((FoodItem)other).getName().equals(this.getName()) );
	}
	
}
