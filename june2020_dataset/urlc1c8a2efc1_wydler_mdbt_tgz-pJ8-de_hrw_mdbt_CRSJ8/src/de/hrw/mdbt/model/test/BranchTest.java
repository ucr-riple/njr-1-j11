package de.hrw.mdbt.model.test;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.constraints.UniqueFieldValueConstraintViolationException;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Branch;
import de.hrw.mdbt.model.Vehicle;
import de.hrw.mdbt.model.VehicleGroup;

public class BranchTest {

	private static final String DB_TESTFILE = "BranchTest.db4o";

	private static final String INITIAL_NAME = "myBranch";
	private static final Time INITIAL_OPENINGTIME = Time.valueOf("9:0:0");
	private static final Time INITIAL_CLOSINGTIME = Time.valueOf("17:0:0");
	private static final String INITIAL_PHONE = "0123456789";

	private static ObjectContainer db;

	private Branch b;
	private Address a;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		Branch.configure(config);
		db = Db4oEmbedded.openFile(config, DB_TESTFILE);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		db.close();
	}

	@Before
	public void setUp() throws Exception {
		a = new Address();
		b = new Branch(INITIAL_NAME, a, INITIAL_OPENINGTIME, INITIAL_CLOSINGTIME, "0123456789");
	}

	@After
	public void tearDown() throws Exception {
		db.rollback();
	}

	@Test
	public void testGetName() {
		assertEquals(INITIAL_NAME, b.getName());
	}

	@Test
	public void testSetName() {
		b.setName("someOtherName");
		assertEquals("someOtherName", b.getName());
		b.setName(INITIAL_NAME);
		assertEquals(INITIAL_NAME, b.getName());
	}

	@Test
	public void testGetAddress() {
		assertEquals(a, b.getAddress());
	}

	@Test
	public void testSetAddress() {
		Address a2 = new Address();
		b.setAddress(a2);
		assertEquals(a2, b.getAddress());
		b.setAddress(a);
		assertEquals(a, b.getAddress());
	}

	@Test
	public void testGetOpeningTime() {
		assertEquals(INITIAL_OPENINGTIME, b.getOpeningTime());
	}

	@Test
	public void testGetClosingTime() {
		assertEquals(INITIAL_CLOSINGTIME, b.getClosingTime());
	}

	@Test
	public void testSetOpeningTime() {
		Time ot = Time.valueOf("8:0:0"); 
		b.setOpeningTime(ot);
		assertEquals(ot, b.getOpeningTime());
		b.setOpeningTime(INITIAL_OPENINGTIME);
		assertEquals(INITIAL_OPENINGTIME, b.getOpeningTime());
		
		exception.expect(IllegalArgumentException.class);
		b.setOpeningTime(Time.valueOf("4:59:0"));
	}

	@Test
	public void testSetClosingTime() {
		Time ct = Time.valueOf("16:0:0"); 
		b.setClosingTime(ct);
		assertEquals(ct, b.getClosingTime());
		b.setClosingTime(INITIAL_CLOSINGTIME);
		assertEquals(INITIAL_CLOSINGTIME, b.getClosingTime());
	}

	@Test
	public void testGetPhone() {
		assertEquals(INITIAL_PHONE, b.getPhone());
	}

	@Test
	public void testSetPhone() {
		b.setPhone("1234567890");
		assertEquals("1234567890", b.getPhone());
		b.setPhone(INITIAL_PHONE);
		assertEquals(INITIAL_PHONE, b.getPhone());
	}

	@Test
	public void testGetVehicles() {
		assertEquals(0, b.getVehicles().size());
		VehicleGroup vg = new VehicleGroup();
		Vehicle v = new Vehicle("RV-TT-0001","1",1,b,vg);
		assertEquals(1, b.getVehicles().size());
		assertEquals(v, b.getVehicles().get(0));
	}

	@Test
	public void testStore() {
		assertEquals(0,db.query(Branch.class).size());
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
	}
	
	@Test
	public void testStoreDuplicate() {
		db.store(b);
		
		Branch b2 = new Branch(INITIAL_NAME, a, INITIAL_OPENINGTIME, INITIAL_CLOSINGTIME, "0123456789");
		db.store(b2);
		
		exception.expect(UniqueFieldValueConstraintViolationException.class);
		db.commit();
	}

	@Test
	public void testStoreNullNameFails() {
		assertEquals(0,db.query(Branch.class).size());
		b.setName(null);
		db.store(b);
		assertEquals(0,db.query(Branch.class).size());
	}

	@Test
	public void testStoreNullOpeningTime() {
		assertEquals(0,db.query(Branch.class).size());
		b.setOpeningTime(null);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
	}

	@Test
	public void testStoreNullClosingTime() {
		assertEquals(0,db.query(Branch.class).size());
		b.setClosingTime(null);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
	}

	@Test
	public void testStoreNullPhoneFails() {
		assertEquals(0,db.query(Branch.class).size());
		b.setPhone(null);
		db.store(b);
		assertEquals(0,db.query(Branch.class).size());
	}

	@Test
	public void testAddMultipleVehicles() {
		assertEquals(0,b.getVehicles().size());
		VehicleGroup vg = new VehicleGroup();
		new Vehicle("RV-TT-0001","1",1,b,vg);
		new Vehicle("RV-TT-0002","2",10,b,vg);
		new Vehicle("RV-TT-0003","3",100,b,vg);
		new Vehicle("RV-TT-0004","4",1000,b,vg);
		assertEquals(4,b.getVehicles().size());
	}

	@Test
	public void testRemoveMultipleVehicles() {
		assertEquals(0,b.getVehicles().size());
		VehicleGroup vg = new VehicleGroup();
		Vehicle v1 = new Vehicle("RV-TT-0001","1",1,b,vg);
		Vehicle v2 = new Vehicle("RV-TT-0002","2",10,b,vg);
		Vehicle v3 = new Vehicle("RV-TT-0003","3",100,b,vg);
		Vehicle v4 = new Vehicle("RV-TT-0004","4",1000,b,vg);
		assertEquals(4,b.getVehicles().size());

		v3.setBranch(null);
		assertEquals(3,b.getVehicles().size());
		v2.setBranch(null);
		assertEquals(2,b.getVehicles().size());
		v1.setBranch(null);
		assertEquals(1,b.getVehicles().size());
		v4.setBranch(null);
		assertEquals(0,b.getVehicles().size());
	}

	@Test
	public void testStoreMultipleVehicles() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		VehicleGroup vg = new VehicleGroup();
		new Vehicle("RV-TT-0001","1",1,b,vg);
		new Vehicle("RV-TT-0002","2",10,b,vg);
		new Vehicle("RV-TT-0003","3",100,b,vg);
		new Vehicle("RV-TT-0004","4",1000,b,vg);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(4,db.query(Vehicle.class).size());
	}

	@Test
	public void testDeleteMultipleVehicles() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		VehicleGroup vg = new VehicleGroup();
		Vehicle v1 = new Vehicle("RV-TT-0001","1",1,b,vg);
		Vehicle v2 = new Vehicle("RV-TT-0002","2",10,b,vg);
		Vehicle v3 = new Vehicle("RV-TT-0003","3",100,b,vg);
		Vehicle v4 = new Vehicle("RV-TT-0004","4",1000,b,vg);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(4,db.query(Vehicle.class).size());

		db.delete(v3);
		assertEquals(3,b.getVehicles().size());
		assertEquals(3,db.query(Vehicle.class).size());
		db.delete(v2);
		assertEquals(2,b.getVehicles().size());
		assertEquals(2,db.query(Vehicle.class).size());
		db.delete(v1);
		assertEquals(1,b.getVehicles().size());
		assertEquals(1,db.query(Vehicle.class).size());
		db.delete(v4);
		assertEquals(0,b.getVehicles().size());
		assertEquals(0,db.query(Vehicle.class).size());
	}

	@Test
	public void testDelete() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		VehicleGroup vg = new VehicleGroup();
		new Vehicle("RV-TT-0001","1",1,b,vg);
		new Vehicle("RV-TT-0002","2",10,b,vg);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(2,db.query(Vehicle.class).size());

		db.delete(b);
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
	}
}
