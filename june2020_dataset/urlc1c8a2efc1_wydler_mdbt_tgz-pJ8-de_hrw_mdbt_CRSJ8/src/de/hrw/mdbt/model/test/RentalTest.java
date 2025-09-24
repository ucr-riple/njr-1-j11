package de.hrw.mdbt.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.Db4oException;

import de.hrw.mdbt.model.Customer;
import de.hrw.mdbt.model.Rental;
import de.hrw.mdbt.model.Vehicle;

public class RentalTest {

	private static final String DB_TESTFILE = "RentalTest.db4o";

	private static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		EmbeddedConfiguration config;
		config = Db4oEmbedded.newConfiguration();
		db = Db4oEmbedded.openFile(config, DB_TESTFILE);
	}

	@AfterClass
	public static void tearDownAfterClass(){
		db.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		db.rollback();
	}

	@Test
	public void testRental() {
		Vehicle v = new Vehicle("BC-BC 1234", "1234567890", 10, null, null);
		Customer c = new Customer(1);
		Rental r = new Rental(new Date(), new Date(), v, c);

		db.store(r);

		ObjectSet<Rental> allPersons = db.queryByExample(Rental.class);
		assertEquals("Store simple person", 1, allPersons.size());
	}

	@Test
	public void testRentalNoVehicle() {
		Customer c = new Customer(1);
		Rental r = new Rental(new Date(), new Date(), null, c);

		try {
			db.store(r);
		} catch(Db4oException ex) {
			assertNotNull(ex);
		}
	}

	@Test
	public void testRentalNoCustomer() {
		Vehicle v = new Vehicle("BC-BC 1234", "1234567890", 10, null, null);
		Rental r = new Rental(new Date(), new Date(), v, null);

		try {
			db.store(r);
		} catch(Db4oException ex) {
			assertNotNull(ex);
		}
	}
}
