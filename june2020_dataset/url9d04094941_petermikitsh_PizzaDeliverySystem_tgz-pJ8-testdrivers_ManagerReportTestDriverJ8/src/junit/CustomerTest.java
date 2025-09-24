package junit;

import model.Address;
import model.Customer;
import junit.framework.TestCase;

public class CustomerTest extends TestCase {

	public CustomerTest(String name) {
		super(name);
	}

	public final void testCustomer() {
		new Customer();
		Address oldAddress = new Address("RIT", 20);
		Address newAddress = new Address("UR", 15);
		Customer custOne = new Customer("Ted", "1234567890", oldAddress);
		custOne.setName("Dan");
		assertTrue(custOne.getName().equals("Dan"));
		custOne.setStreetAddress(newAddress);
		assertEquals(custOne.getStreetAddress(), newAddress);
		custOne.setPhoneNumber("5854751234");
		assertTrue(custOne.getPhoneNumber().equals("5854751234"));
	}

	public final void testGetKey() {
		Customer custOne = new Customer("Ted", "1234567890", new Address("RIT", 20));
		assertTrue(custOne.getPhoneNumber().hashCode()==custOne.getKey());
	}

	public final void testGetDb() {
		Customer.getDb();
	}

	public final void testIsValidPhoneNumber() {
		assertFalse(Customer.isValidPhoneNumber("123456789"));
		assertTrue(Customer.isValidPhoneNumber("1234567890"));
		assertFalse(Customer.isValidPhoneNumber("12345678901"));
	}

}
