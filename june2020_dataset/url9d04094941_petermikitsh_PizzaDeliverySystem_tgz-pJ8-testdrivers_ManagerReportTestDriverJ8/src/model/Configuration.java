package model;

import model.database.Database;

/**
 * A configuration object represents a value that is read elsewhere in the PDS
 * for the purposes of set up and instantiation
 * 
 * @author Isioma Nnodum iun4534@rit.edu
 */
public class Configuration implements Cerealizable<Configuration> {

	/**
	 * Database containing configuration information.
	 */
	private static Database<Configuration> _configurationDatabase;

	/**
	 * The name of this variable
	 */
	private String _name;
	
	/**
	 * The value of this variable
	 */
	private String _value;

	/**
	 * Main Constructor
	 * @param name - the name of this variable
	 * @param value - the value of this variable
	 */
	public Configuration(String name, String value) {
		
		_name = name;
		_value = value;

	}
	
	/**
	 * The Default constructor
	 */
	public Configuration() {}

	/**
	 * Get the name of a configuration variable
	 * @return the name
	 */
	public String getName() {

		return _name;
	}

	/**
	 * Change the name of a configuration variable
	 * @param newName - the new name of this variable
	 */
	public void setName(String newName) {

		_name = newName;
	}

	/** 
	 * get the value of a variable
	 * @return the stored value 
	 */
	public String getValue() {

		return _value;

	}
	
	/**
	 * change the value of a variable
	 * @param newValue - the new value of this variable
	 */
	public void setValue(String newValue) {
		_value = newValue;
	}

	/**
	 * Hashes this variable by it name 
	 * 
	 * @return the hash code of the variable
	 */
	public int getKey() {
		return _name.hashCode();
	}

	/**
	 * Returns this model's database
	 * 
	 * @return
	 */
	public static Database<Configuration> getDb() {
		
		if (null == _configurationDatabase) {
			_configurationDatabase = new Database<Configuration>(
					"configuration");
		}

		return _configurationDatabase;
		
	}

}
