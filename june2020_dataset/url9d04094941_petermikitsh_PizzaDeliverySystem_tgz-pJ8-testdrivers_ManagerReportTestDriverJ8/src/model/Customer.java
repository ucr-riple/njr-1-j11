package model;

import java.util.ArrayList;
import java.util.Date;

import model.database.Database;

/**
 * The model element representing the customer. Maintains a name, phone number,
 * and address.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class Customer implements Cerealizable<Customer>, Comparable<Customer> {

	private static Database<Customer> _customerDatabase;

	/**
	 * The customer's name.
	 */
	private String name;

	/**
	 * The customer's phone number.
	 */
	private String phoneNumber;

	/**
	 * The customer's street address.
	 */
	private Address streetAddress;

	/**
	 * This customer's unique identifier.
	 */
	private int customerId;

	/**
	 * A default constructor.
	 */
	public Customer() {

		this.name = "";
		this.phoneNumber = "";
		this.streetAddress = new Address();

		// Store the customerId as a hash of the current time.
		customerId = new Date().hashCode();

	}

	public Customer(String name, String phone, Address address) {

		this.name = name;
		this.phoneNumber = phone;
		this.streetAddress = address;

		// Store the customerId as a hash of the current time.
		customerId = new Date().hashCode();

	}

	/**
	 * Gets the name of the customer
	 * 
	 * @return the Customer's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the customer's name to the given name.
	 * 
	 * @param name
	 *            the String to set the Customer's name to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the customer's phone number.
	 * 
	 * @return the Customer's phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Gets a nicely formatted version of the customer's phone number.
	 */
	public String getFormattedPhoneNumber() {
		
		// Start with an empty string.
		String numberString = "";
		
		// We know that the string has at least ten characters.  Put the first 
		// three in parenthesis.
		numberString = numberString + "(" + phoneNumber.substring( 0, 3 ) + ") ";
		
		// Put then next three followed by hyphen.
		numberString = numberString + phoneNumber.substring( 3, 6 ) + "-";
		
		// And then the possibly last four.
		numberString = numberString + phoneNumber.substring( 6, 10 );
		
		return numberString;
		
	}

	/**
	 * Sets the customer's phone number to the given String.
	 * 
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the customer's street address.
	 * 
	 * @return the Customer's streetAddress
	 */
	public Address getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Sets the customer's street address to the one given
	 * 
	 * @param streetAddress
	 *            the streetAddress to set
	 */
	public void setStreetAddress(Address streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * Returns a list of orders that the customer has placed.
	 * 
	 * @return the customer's past orders
	 */
	public ArrayList<Order> getPastOrders() {
		
		ArrayList<Order> pastOrders = new ArrayList<Order>();
		
		for (int i = 0; i < Order.getDb().size(); i++) {
			
			Order order = Order.getDb().get(i);
			
			if (order.getCustomer().getKey() == this.getKey()) {
				pastOrders.add(order);
			}
			
		}
		
		return pastOrders;
		
	}
	
	/**
	 * Getter for the customer Id.
	 */
	public int getCustomerId() {
		return customerId;
	}
	
	/**
	 * Setter for the customer Id.
	 */
	public void setCustomerId( int id ) {
		this.customerId = id;
	}
	
	/**
	 * Hashes the customer by his or her phone number.
	 * 
	 * @return the hash code of the Customer's phoneNumber
	 */
	public int getKey() {
		return this.customerId;
	}

	/**
	 * Returns this model's database
	 * 
	 * @return
	 */
	public static Database<Customer> getDb() {
		if (null == _customerDatabase) {
			_customerDatabase = new Database<Customer>("customer");
		}

		return _customerDatabase;
	}

	/**
	 * Return if is a valid phone number
	 * 
	 * @param number
	 *            - the number to be validated
	 * @return if the number is valid
	 */
	public static boolean isValidPhoneNumber(String number) {
		return number.matches("\\d{10,10}");
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Customer other) {
		return this.getName().compareTo(other.getName());
	}

	/**
	 * Indicates if this customer "matches" the given search term.
	 * 
	 * @param searchTerm
	 *            The search term.
	 * @return Whether or not the customer matches search term.
	 */
	public boolean matchesSearch(String searchTerm) {

		// First, check the phone number for containment.
		if (getPhoneNumber().contains(searchTerm)) {
			return true;
		}

		// Now check the name.
		if (getName().toLowerCase().contains(searchTerm.toLowerCase())) {
			return true;
		}

		// Now check the name.
		for (String token : getName().split(" ")) {
			if (token.equalsIgnoreCase(searchTerm)) {
				return true;
			}
		}

		// If all that failed, return false.
		return false;
		
	}

}
