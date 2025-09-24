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
import org.junit.Test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Branch;
import de.hrw.mdbt.model.Model;
import de.hrw.mdbt.model.PriceClass;
import de.hrw.mdbt.model.Vehicle;
import de.hrw.mdbt.model.VehicleGroup;

public class VehicleGroupTest {

	private static final String DB_TESTFILE = "VehicleGroupTest.db4o";

	private static ObjectContainer db;

	private VehicleGroup vg;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(Paths.get(DB_TESTFILE));
		db = Db4oEmbedded.openFile(DB_TESTFILE);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		db.close();
	}

	@Before
	public void setUp() throws Exception {
		Calendar c = new GregorianCalendar(2013, 1, 1);
		vg = new VehicleGroup(142, "Nuklearer Abfall", c.getTime(), "Blau", new Model(), new PriceClass());
	}

	@After
	public void tearDown() throws Exception {
		db.rollback();
	}

	//TODO: add tests for getters/setters

	@Test
	public void testStore() {
		assertEquals(0,db.query(VehicleGroup.class).size());
		db.store(vg);
		assertEquals(1,db.query(VehicleGroup.class).size());
	}

	@Test
	public void testStoreMultipleVehicleGroups() {
		Calendar c1 = new GregorianCalendar(2013, 1, 1);
		Calendar c2 = new GregorianCalendar(2012, 1, 2);
		assertEquals(0,db.query(VehicleGroup.class).size());
		db.store(new VehicleGroup(142, "Nuklearer Abfall", c1.getTime(), "Blau Metallic", new Model(), new PriceClass()));
		db.store(new VehicleGroup(100, "Diesel", c2.getTime(), "Rot", new Model(), new PriceClass()));
		assertEquals(2,db.query(VehicleGroup.class).size());
	}

	@Test
	public void testDelete() {
		assertEquals(0,db.query(VehicleGroup.class).size());
		Address a = new Address();
		Branch b = new Branch("myBranch", a, Time.valueOf("9:0:0"), Time.valueOf("17:0:0"), "0123456789");
		new Vehicle("RV-TT-0001","1",1,b,vg);
		db.store(vg);
		assertEquals(1,db.query(VehicleGroup.class).size());
		assertEquals(1,db.query(Vehicle.class).size());
		db.delete(vg);
		assertEquals(0,db.query(VehicleGroup.class).size());
		assertEquals(0,db.query(Vehicle.class).size());
	}
}
