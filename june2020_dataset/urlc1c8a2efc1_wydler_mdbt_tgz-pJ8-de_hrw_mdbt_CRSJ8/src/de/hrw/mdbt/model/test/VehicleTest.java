package de.hrw.mdbt.model.test;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import de.hrw.mdbt.model.Model;
import de.hrw.mdbt.model.PriceClass;
import de.hrw.mdbt.model.Vehicle;
import de.hrw.mdbt.model.VehicleGroup;

public class VehicleTest {

	private static final String DB_TESTFILE = "VehicleTest.db4o";

	private static ObjectContainer db;

	private Branch b;
	private Vehicle v;
	private VehicleGroup vg;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		EmbeddedConfiguration config;
		config = Db4oEmbedded.newConfiguration();
		Vehicle.configure( config );
		db = Db4oEmbedded.openFile(config, DB_TESTFILE);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		db.close();
	}

	@Before
	public void setUp() throws Exception {
		b = new Branch("myBranch",new Address(), Time.valueOf("9:0:0"), Time.valueOf("17:0:0"),"0123456789");
		Calendar c = new GregorianCalendar(2013, 1, 1);
	    vg = new VehicleGroup(100, "Diesel", c.getTime(), "Blau", new Model(), new PriceClass());
		v = new Vehicle("RV-DB-40","42",1,b,vg);
	}

	@After
	public void tearDown() throws Exception {
		db.rollback();
	}

	//TODO: add tests for getters/setters

	@Test
	public void testStoreNegativeActualKmFail() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
		v.setActualKm(-1000);
		db.store(v);
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
	}

	@Test
	public void testStoreLicenseNumberUnique() {
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
		b = new Branch("myBranch",new Address(), Time.valueOf("9:0:0"), Time.valueOf("17:0:0"),"0123456789");
		Calendar c = new GregorianCalendar(2013, 1, 1);
	    vg = new VehicleGroup(100, "Diesel", c.getTime(), "Blau", new Model(), new PriceClass());
		new Vehicle("RV-TT-0001","1",1,b,vg);
		new Vehicle("RV-TT-0001","2",10,b,vg);
		db.store(vg);
		//HACK: everything below in this function ;)
		// we need to commit as the unique constraint is checked when committing the change
		exception.expect(UniqueFieldValueConstraintViolationException.class);
		db.commit();
	}

	@Test
	public void testStore() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
		db.store(v);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(1,db.query(Vehicle.class).size());
		assertEquals(1,db.query(VehicleGroup.class).size());
	}

	@Test
	public void testStoreMultipleVehicles() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
		new Vehicle("RV-TT-0001","1",1,b,vg);
		new Vehicle("RV-TT-0002","2",10,b,vg);
		new Vehicle("RV-TT-0003","3",100,b,vg);
		new Vehicle("RV-TT-0004","4",1000,b,vg);
		db.store(b);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(5,db.query(Branch.class).get(0).getVehicles().size());
		assertEquals(5,db.query(Vehicle.class).size());
		assertEquals(1,db.query(VehicleGroup.class).size());
		assertEquals(5,db.query(VehicleGroup.class).get(0).getVehicles().size());
	}

	@Test
	public void testDelete() {
		assertEquals(0,db.query(Branch.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(0,db.query(VehicleGroup.class).size());
		db.store(v);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(1,db.query(Branch.class).get(0).getVehicles().size());
		assertEquals(1,db.query(Vehicle.class).size());
		assertEquals(1,db.query(VehicleGroup.class).size());
		assertEquals(1,db.query(VehicleGroup.class).get(0).getVehicles().size());
		db.delete(v);
		assertEquals(1,db.query(Branch.class).size());
		assertEquals(0,db.query(Branch.class).get(0).getVehicles().size());
		assertEquals(0,db.query(Vehicle.class).size());
		assertEquals(1,db.query(VehicleGroup.class).size());
		assertEquals(0,db.query(VehicleGroup.class).get(0).getVehicles().size());
	}

}
