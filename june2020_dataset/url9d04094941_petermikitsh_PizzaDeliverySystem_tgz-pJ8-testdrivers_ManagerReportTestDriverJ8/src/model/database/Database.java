package model.database;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import model.Cerealizable;

/**
 * A generic "database" manager.  
 * It is essentially a wrapper for a hashmap of integers to whatever to is being
 * stored (which must implement Cerealizable), which provides automatic saving 
 * and loading of tables. 
 * 
 * @param <V>
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
public class Database<V extends Cerealizable<V>> {
	
	/**
	 * The hashmap that stores the data.
	 */
	public HashMap<Integer,V> _db;
	
	/**
	 * The string name of the database.
	 */
	private String _name;
	
	/**
	 * Every object's databse provides a counter that is incremented every
	 *  it is accessed.  This can be used for object identification.
	 */
	private int _counter;
	
	/**
	 * The only constructor
	 * 
	 * @param name The Name of the database to be created
	 */
	public Database(String name) {
		
		_counter = 0;
		_name = name;
		_db =  new HashMap<Integer,V>();
		load();
		
	}

	/**
	 * Add an element to the database
	 * will commit any changes
	 * @param key the unique key for this element
	 * @param value the element
	 */
	public void add(V value) {
		
		_db.put(value.getKey(), value);
		save();
		
	}
	
	/**
	 * Retrieve an element from the database
	 * @param key the unique identifier for the desired element
	 * @return the element
	 */
	public V get(Integer key) {
		return _db.get(key);
	}
	
	/**
	 * Remove an element from the database
	 * will commit all changes
	 * @param key - the unique identifier for the desired element
	 */
	public void remove(Integer key) {
		
		_db.remove(key);
		save();
		
	}
	
	/**
	 * Check to see if the database contains a specific element
	 * @param key the unique identifier for
	 * @return
	 */
	public boolean contains(Integer key) {
		return _db.containsKey(key);
	}
	
	/**
	 * Returns a list of the values in the database.  Note that the values are 
	 * NOT guaranteed to maintain any order between calls to this function.
	 * 
	 * @return A list of values in the table.
	 */
	public ArrayList<V> list() {
		
		ArrayList<V> retList = new ArrayList<V>();
		
		for ( V cur : _db.values() ) {
			retList.add(cur);
		}
		
		return retList;
		
	}
	
	/**
	 * Gets the number of key-value pairs in the database.
	 * @return
	 */
	public int size() {
		return _db.size();
	}
	
	/**
	 * Writes this database's information to an XML file.
	 */
	public void save() {
		
		// Create an encoder and use it to write the database.
		try {
		    // Serialize object into XML
		    XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
		        new FileOutputStream(_name +"_table.xml")));
		    
		    // Write the counter.
		    encoder.writeObject(_counter);
		    // Write the database.
		    encoder.writeObject(_db);
		    
		    encoder.close();
		} catch (FileNotFoundException e) {
			// Do nothing.
		}
		
	}
	
	/**
	 * Loads this database's information from an XML file.
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		
		// Create a decoder that will load the database from a file.
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder( 
						new BufferedInputStream( 
							new FileInputStream(_name +"_table.xml") ) );
			// Read in the counter.
			_counter = (Integer)decoder.readObject();
			// Read in the database.
			_db = ((HashMap<Integer,V>)decoder.readObject());
			decoder.close();
		} catch (FileNotFoundException e) {
			// Do nothing.
		}
		
	}
	
	public void setName(String newname) {
		_name = newname;
	}
	
	public String getName() {
		return _name;
	}
	
	public HashMap<Integer, V> getDb() {
		return _db;
	}

	/**
	 * Getter for the add count.
	 */
	public int getCounter() {
		_counter++;
		return _counter;
	}

}
