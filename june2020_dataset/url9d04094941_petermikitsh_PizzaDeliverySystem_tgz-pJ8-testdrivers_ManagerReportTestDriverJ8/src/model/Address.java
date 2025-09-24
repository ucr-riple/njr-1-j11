package model;

import model.database.Database;

/**
 * A class that represents a street address for order deliveries.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class Address implements Cerealizable<Address> {
	
	/**
	 * The static database of available addresses.
	 */
	private static Database<Address> _addressDatabase;

	/**
	 * The location of the address.
	 */
	private String location;
	
	/**
	 * The amount of time it takes to get to the address from the pizza shop.
	 */
	private int timeToLocation;
	
	/**
	 * Constructor for an Address.
	 */
	public Address() {}
	
	/**
	 * Constructor for an Address from a string and location.
	 * 
	 * @param location   the location of the address
	 * @param timeToLocation   the time to the address
	 */
	public Address(String location, int timeToLocation) {
		
		this.location = location;
		this.timeToLocation = timeToLocation;
		
	}
	
	/**
	 * Gets the location of the address.
	 * 
	 * @return   the location of the Address
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the location of the address to the one given.
	 * 
	 * @param location   the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the amount of time to the address.
	 * 
	 * @return   the time to the Address
	 */
	public int getTimeToLocation() {
		return timeToLocation;
	}
	
	/**
	 * Sets the amount of time to the address to the one given.
	 * 
	 * @param timeToLocation   the time to set
	 */
	public void setTimeToLocation(int timeToLocation) {
		this.timeToLocation = timeToLocation;
	}
	
	/**
	 * Hashes the address's location to be used as a key.
	 * 
	 * @return   the hash code made using the Address's location
	 */
	public int getKey() {
		return location.hashCode();
	}
	
	/**
	 * Indicates if the Address is empty.
	 * 
	 * @return True if the "location" string is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return (location == null ) || location.isEmpty();
	}
	
	/**
	 * Compares two addresses for equality.
	 */
	public boolean equals( Object other ) {
		
		// Check the location strings.
		if( other instanceof Address ) {
			return this.getLocation().equals( ((Address)other).getLocation() );
		} else {
			return false;
		}
		
	}
	
	/**
	 * Returns this model's database
	 * 
	 * @return The database of addresses.
	 */
	public static Database<Address> getDb() {
		
		if ( null == _addressDatabase ) {
			_addressDatabase = new Database<Address>( "address" );
		}
		
		return _addressDatabase;
		
	}
	
	/**
	 * Returns the address object represented by the given alias string.
	 *  For example, even though "U of R" is not a valid address in the 
	 *  base, giving "U of R" to this method will return the "University 
	 *  of Rochester" address object.
	 * 
	 * @param alias  The string to un-alias-ify.
	 * @return The address object that is represented by that alias.
	 */
	public static Address getAddressForAlias( String alias ) {
		
		// Check the alias column.
		for( int i = 0; i < addressAliases.length; i++ ) {
			for( int j = 0; j < addressAliases[i].length; j++ ) {
				if( alias.equalsIgnoreCase( addressAliases[i][j] ) ) {
					return Address.getDb().get( addressAliases[i][0].hashCode() );
				}
			}
		}
		
		// If we got here with no success, return null.
		return null;
		
	}
	
	@Override
	public String toString() {
		return getLocation();
	}
	
	/**
	 * The alias table.  The first element of each subarray gives the string
	 * 	that will be displayed, and the following elements are aliases.
	 */
	private static final String[][] addressAliases = 
		{ { "RIT", "Rochester Institute of Technology", "rit", "brick city" },
		{ "University of Rochester", "u of r", "ur", "u r" }, 
		{ "Nazareth College", "naz" },
		{ "St. John Fisher College", "st. john fisher", "fisher", "sjf" },
		{ "Roberts Wesleyan College", "robwes", "rwc" },
		{ "Monroe Community College", "mcc" } };
	
}
