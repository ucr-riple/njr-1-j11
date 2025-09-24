/*
 * DatabaseTest.java
 */

package junit;

import junit.framework.TestCase;
import model.Address;
import model.Customer;
import model.database.Database;

/**
 * JUnit test class for Database.java
 * 
 * @author   Casey Klimkowsky   cek3403@g.rit.edu
 */
public class DatabaseTest extends TestCase {

	public DatabaseTest(String name) {
		super(name);
	}
	
	public void testDatabaseString() {
		new Database<Customer>("customer");
	}

	public void testDatabase() {
		new Database<Customer>( "customer_test");
	}

	public void testAdd() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer = new Customer("Casey", "555-555-5555", address);
		
		database.add(customer);
		
		assertTrue(database.contains(customer.getKey()));
		
	}

	public void testGet() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer = new Customer("Casey", "555-555-5555", address);
		
		int key = customer.getKey();
		database.add(customer);
		
		assertTrue(database.get(key).equals(customer));
		
	}

	public void testRemove() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer = new Customer("Casey", "555-555-5555", address);
		
		database.add(customer);
		database.remove(customer.getKey());
		
		assertTrue(database.size() == 0);
		
	}

	public void testContains() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer1 = new Customer("Casey", "555-555-5555", address);
		Customer customer2 = new Customer("Isioma", "333-333-3333", address);
		Customer customer3 = new Customer("Peter", "111-111-1111", address);
		
		database.add(customer1);
		database.add(customer2);
		database.add(customer3);
		
		int key = customer2.getKey();
		
		assertTrue(database.contains(key));
		
	}

	public void testList() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer = new Customer("Casey", "555-555-5555", address);
		
		database.add(customer);
		
		assertTrue((database.list()).contains(customer));
		
	}

	public void testSize() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		Address address = new Address("RIT", 18);
		Customer customer = new Customer("Casey", "555-555-5555", address);
		
		database.add(customer);
		
		assertTrue(database.size() == 1);
		
	}

	public void testSetName() {
		
		Database<Customer> database = new Database<Customer>("customer");
		assertTrue(database.getName() == "customer");
		
		database.setName("new name");
		assertTrue(database.getName().equals("new name"));
		
	}

	public void testGetName() {
		
		Database<Customer> database = new Database<Customer>("customer");
		
		assertTrue(database.getName().equals("customer"));
		
	}

}
