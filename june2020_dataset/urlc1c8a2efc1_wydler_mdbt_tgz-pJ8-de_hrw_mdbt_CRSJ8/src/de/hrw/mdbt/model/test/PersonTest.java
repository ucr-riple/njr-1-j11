package de.hrw.mdbt.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.constraints.UniqueFieldValueConstraintViolationException;
import com.db4o.internal.ReflectException;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Person;
import de.hrw.mdbt.model.Phone;

public class PersonTest {
	
	private static final String DB_TESTFILE = "PersonTest.db4o";

	private static ObjectContainer db;
	private static Calendar d = Calendar.getInstance();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		EmbeddedConfiguration config;
		config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Person.class).updateDepth(2);	//TODO: maybe move to Person.configure if Person relies on that 
		Person.configure( config );
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
	public void testSimplePerson () {
		ObjectSet <Person> allPersons = db.queryByExample(Person.class);
		int nrp = allPersons.size();
		Address a1 = new Address ("New York", "Harbour Street 5", "Blackforest", "4567", "Private");
		Address a2 = new Address ("New York", "Main Street 7", "Whitewater", "4555", "Work");
		Person p = new Person (1, "Mustermann");
		p.addAddress(a1);
		p.addAddress(a2);
		p.setSex("male");
		try {
			db.store(p);
		} catch (ReflectException e) {
			e.printStackTrace();
		}
		db.commit();
		allPersons = db.queryByExample(Person.class);
		assertEquals("Store simple person", nrp+1, allPersons.size());
	}
	
	@Test
	public void testPersonNoName () {
		String exceptionMessage = "";
		Person p = new Person ();
		try {
			db.store(p);
		} catch (ReflectException e) {
			exceptionMessage = e.getCause().getMessage();
		}
		db.commit();
		assertEquals("Do not store person without name","A person must have a last name!", exceptionMessage);
	}

	@Test
	public void testPersonNotYetBorn () {
		String exceptionMessage = "";
		Calendar d = Calendar.getInstance();
		d.set(3001, 10, 15);
		Person p2 = new Person(5, "Peter", "Marple", "George", "Sir", "Earl", d.getTime(), "male");
		try {
			db.store(p2);
		} catch (ReflectException e) {
			exceptionMessage = e.getCause().getMessage();
		}
		db.commit();
		assertEquals("Do not store person not yet born", "<dob> must not be in the future!", exceptionMessage);
	}

	@Test
	public void testPersonOK () {
		ObjectSet <Person> allPersons = db.queryByExample(Person.class);
		int nrp = allPersons.size();
		Phone pnr = new Phone("0751", "501-9733", "work");
		Calendar d = Calendar.getInstance();
		d.set(2001, 10, 15);
		Person p = new Person (100, "Peter", "Trap", "William", "Sir", "Earl",d.getTime(),"male");
		p.addPhone(pnr);
		try {
			db.store(p);
		} catch (ReflectException e) {
			e.printStackTrace();
		}
		db.commit();
		allPersons = db.queryByExample(Person.class);
		assertEquals("person should be stored", nrp+1, allPersons.size());
	}
	
	@Test
	public void testPersonIdDuplicate() {
		try {
			Person p1 = new Person (10, "Peter", "Trap", "William", "Sir", "Earl", d.getTime(),"male");
			db.store(p1);
			db.commit();
			
			Person p2 = new Person (10, "Peter", "Trap", "William", "Sir", "Earl", d.getTime(),"male");
			db.store(p2);
			db.commit();
		} catch(UniqueFieldValueConstraintViolationException ex) {
			assertNotNull(ex);
		}
	}
}

