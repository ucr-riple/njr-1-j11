package junit;

import model.Address;
import junit.framework.TestCase;

public class AddressTest extends TestCase {
	
	public final void setUp() {

		Address.getDb().add( new Address( "Rochester Institute of Technology", 18 ) );
		Address.getDb().add( new Address( "University of Rochester", 12 ) );
		Address.getDb().add( new Address( "Nazareth College", 25 ) );
		Address.getDb().add( new Address( "St. John Fisher", 21 ) );
		Address.getDb().add( new Address( "Roberts Wesleyan College", 25 ) );
		Address.getDb().add( new Address( "Monroe Community College", 18 ) );
		
	}

	public AddressTest(String name) {
		super(name);
	}

	public final void testAddress() {
		new Address();
	}

	public final void testAddressStringInt() {
		new Address("RIT", 20);
	}

	public final void testGetLocation() {
		Address loc = new Address("RIT", 20);
		assertTrue(loc.getLocation().equals("RIT"));
	}

	public final void testSetLocation() {
		Address loc = new Address("RIT", 20);
		loc.setLocation("NYU");
		assertTrue(loc.getLocation().equals("NYU"));
	}

	public final void testGetTimeToLocation() {
		Address loc = new Address("RIT", 20);
		assertTrue(loc.getTimeToLocation()==20);
	}

	public final void testSetTimeToLocation() {
		Address loc = new Address("RIT", 20);
		loc.setTimeToLocation(10);
		assertTrue(loc.getTimeToLocation()==10);
	}

	public final void testGetKey() {
		Address loc = new Address("RIT", 20);
		assertTrue(loc.getKey()==loc.getLocation().hashCode());
	}
	
	public final void testGetDb() {
		
		setUp();
		assertNotNull( Address.getDb().get( "Rochester Institute of Technology".hashCode() ) );
		
	}

	public final void testGetAddressForAlias() {
		
		setUp();
		
		// Try to get different addresses for aliases.
		Address add1 = Address.getAddressForAlias( "Rochester Institute of Technology" );
		assertEquals( add1.getLocation(), "Rochester Institute of Technology" );
		
		Address add2 = Address.getAddressForAlias( "RIT" );
		assertEquals( add2.getLocation(), "Rochester Institute of Technology" );

		Address add3 = Address.getAddressForAlias( "rit" );
		assertEquals( add3.getLocation(), "Rochester Institute of Technology" );

		Address add4 = Address.getAddressForAlias( "rit" );
		assertNotSame( add4.getLocation(), "St. John Fisher" );
		
		Address add5 = Address.getAddressForAlias( "bard" );
		assertNull( add5 );
		
	}
	
}
