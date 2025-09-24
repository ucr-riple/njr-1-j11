package de.hrw.mdbt.model.test;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.internal.ReflectException;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Employee;

public class EmployeeTest {
	
	private static final String DB_TESTFILE = "EmployeeTest.db4o";

	private static ObjectContainer db;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		EmbeddedConfiguration config;
		config = Db4oEmbedded.newConfiguration();
		//Employee.configure( config );
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
	public void testEmployee() {
		Address a1 = new Address ("New York", "Harbour Street 5", "Blackforest", "4567", "Private");
		Address a2 = new Address ("New York", "Main Street 7", "Whitewater", "4555", "Work");
		Employee p = new Employee(1, "Mustermann");
		p.addAddress(a1);
		p.addAddress(a2);
		p.setSex("male");
		try {
			db.store(p);
		} catch (ReflectException e) {
			e.printStackTrace();
		}
		ObjectSet<Employee> allPersons = db.queryByExample(Employee.class);
		assertEquals("Store simple person", 1, allPersons.size());
	}
	
	@Test
	public void testEmployeeWithMail() {
		Employee p = new Employee(2, "Mustermann");
		p.setEmail("test@crc.com");
		try {
			db.store(p);
		} catch (ReflectException e) {
			e.printStackTrace();
		}
		ObjectSet<Employee> allPersons = db.queryByExample(Employee.class);
		assertEquals("Store simple person", 1, allPersons.size());		
	}
	
	@Test
	public void testEmployeeWithInvalidMail() {
		Employee p = new Employee(2, "Mustermann");
		p.setEmail("test@test.com");
		try {
			db.store(p);
		} catch (ReflectException e) {
			assertNotNull(e);
		}
	}

}
