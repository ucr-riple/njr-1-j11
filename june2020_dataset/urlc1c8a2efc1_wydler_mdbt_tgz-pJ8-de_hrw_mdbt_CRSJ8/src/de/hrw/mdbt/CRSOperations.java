package de.hrw.mdbt;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.CommonConfigurationProvider;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Branch;
import de.hrw.mdbt.model.Company;
import de.hrw.mdbt.model.Customer;
import de.hrw.mdbt.model.LicenseClass;
import de.hrw.mdbt.model.Model;
import de.hrw.mdbt.model.Person;
import de.hrw.mdbt.model.PriceClass;
import de.hrw.mdbt.model.Rental;
import de.hrw.mdbt.model.Vehicle;
import de.hrw.mdbt.model.VehicleGroup;

public class CRSOperations {
	ObjectContainer db;
	CommonConfigurationProvider config;

	public ObjectContainer getDb() {
		return db;
	}

	public CommonConfigurationProvider getConfig() {
		return config;
	}

	public CRSOperations(String file) {
		this.config = Db4oEmbedded.newConfiguration();
		this.db = Db4oEmbedded.openFile((EmbeddedConfiguration)config, file);
		configureAll(config);
	}

	public void fillDBDefaults() {
		fillDBDefaults(this.db);
	}

	public ObjectSet <VehicleGroup> createOffer(Branch b, Date startDate, Date endDate, PriceClass pc)	{
		return createOffer(this.db, b, startDate, endDate, pc);
	}

	public boolean createReservation (Customer c, Branch b, VehicleGroup vg, Date startDate, Date endDate) {
		return createReservation(this.db, c, b, vg, startDate, endDate);
	}

	private static boolean lock(ObjectContainer db) {
		return db.ext().setSemaphore("global", 1000);
	}

	private static void unlock(ObjectContainer db) {
		db.ext().releaseSemaphore("global");
	}

	public static void configureAll(CommonConfigurationProvider config) {
		if(config == null)
			return;
		Branch.configure(config);
		//TODO: Add all configure methods here OR switch to interface and callback structure and let all classes register themselves "somewhere"
	}

	public static void fillDBDefaults(ObjectContainer db) {
		LicenseClass l1 = new LicenseClass("Ordinary","lame");
		Calendar c4 = new GregorianCalendar(2000, 1, 1);
		Person p1 = new Person(1, "Max", "", "Mad", "?", "", c4.getTime(), "m");
		p1.addLicense(l1);
		db.store(p1);

		Company cy1 = new Company(2,"Knight Industries");
		db.store(cy1);

		Branch b1 = new Branch("DefaultBranch",
				new Address("DefaultState", "DefaultCity", "DefaultStreet", "123456", "DefaultType"),
				Time.valueOf("9:0:0"), Time.valueOf("17:0:0"),
				"0123456789");

		Calendar c1a = new GregorianCalendar(2012, 1, 1);
		Calendar c1b = new GregorianCalendar(2012, 6, 1);
		Calendar c1c = new GregorianCalendar(2013, 1, 1);
		Calendar c1d = new GregorianCalendar(2013, 6, 1);
		Model m1 = new Model("IKEA","Hans",100,"metric",l1);
		PriceClass pc1 = new PriceClass("TooCheapToDrive", "Good Luck!", 10, 2, 3);
		VehicleGroup vg1a = new VehicleGroup(100, "Diesel", c1a.getTime(), "Blue Metallic", m1, pc1);
		VehicleGroup vg1b = new VehicleGroup(100, "Diesel", c1b.getTime(), "Green Metallic", m1, pc1);
		VehicleGroup vg1c = new VehicleGroup(100, "Diesel", c1c.getTime(), "Red Metallic", m1, pc1);
		Vehicle v1 = new Vehicle("RV-DE-0001","1",1,b1,vg1a);
		new Rental(c1a.getTime(), c1b.getTime(), v1, p1);
		new Rental(c1c.getTime(), c1d.getTime(), v1, cy1);
		new Vehicle("RV-FA-0002","2",10,b1,vg1b);
		new Vehicle("RV-UL-0003","3",100,b1,vg1b);
		new Vehicle("RV-T-0004","4",1000,b1,vg1c);

		Calendar c2 = new GregorianCalendar(2012, 1, 2);
		Model m2 = new Model();
		PriceClass pc2 = new PriceClass("TooSlowToDrive", "Take Your Time!", 1, 10, 100);
		VehicleGroup vg2 = new VehicleGroup(10, "Benzin", c2.getTime(), "Blue Metallic", m2, pc2);
		new Vehicle("RV-DE-0005","1",1,b1,vg2);
		new Vehicle("RV-FA-0006","2",10,b1,vg2);
		new Vehicle("RV-UL-0007","3",100,b1,vg2);
		new Vehicle("RV-TY-0008","4",1000,b1,vg2);
		db.store(b1);

		Branch b2 = new Branch("AnotherDefaultBranch",
				new Address("DefaultState", "AnotherDefaultCity", "DefaultStreet", "234567", "DefaultType"),
				Time.valueOf("8:0:0"), Time.valueOf("16:0:0"),
				"0234567891");
		Calendar c3 = new GregorianCalendar(2013, 1, 2);
		LicenseClass l2 = new LicenseClass("TestPilot","WhatIsThisButtonFor?");
		Model m3 =  new Model("Black&Decker","NX01",200,"metric",l2);
		PriceClass pc3 = new PriceClass("IAmBatMan", "NANANANANANANANANANANANANANANANANANA BATMAAAN!!!", 0, 999, 9999);
		VehicleGroup vg3 = new VehicleGroup(100, "Diesel", c3.getTime(), "Blue Metallic", m3, pc3);
		Vehicle v2 = new Vehicle("FN-BM-0001","1",1,b2,vg3);
		new Rental(c1a.getTime(), c1d.getTime(), v2, cy1);
		db.store(b2);

		db.commit();
	}

	public static ObjectSet<VehicleGroup> createOffer(ObjectContainer db, final Branch b, final Date startDate, final Date endDate, final PriceClass pc) {
		if(b==null || pc==null || startDate.after(endDate) )
			return null;
		@SuppressWarnings("serial")
		ObjectSet<VehicleGroup> result = db.query(new Predicate<VehicleGroup>() {
			@Override
			public boolean match(VehicleGroup vg) {
				if( vg.getPriceClass() != pc) return false;
				for(Vehicle v : vg.getVehicles()) {
					if(v.getBranch() != b) continue;
					boolean overlap = false;
					for(Rental r : v.getRentals()) {
						// check next vehicle if any rental overlaps
						if(r.getStartDate().getTime() <= endDate.getTime() && startDate.getTime() <= r.getEndDate().getTime())
						{
							overlap = true;
							break;
						}
					}
					if(!overlap)
						return true;
				}
				// no possible vehicles found
				return false;
			}
		});
		return result;
	}

	public static boolean createReservation(ObjectContainer db, Customer c, Branch b, VehicleGroup vg, Date startDate, Date endDate) {
		if(c==null || b==null || vg==null || startDate.after(endDate) )
			return false;

		boolean hasLock = lock(db);
		try
		{
			if(!hasLock)
			{
				System.out.println("Database lock failed!\n");
				return false;
			}

			boolean licenseOk = false;
			for(LicenseClass lc : c.getLicenses())
				if(lc == vg.getModel().getRequiredLicense())
					licenseOk = true;
			if(!licenseOk)
			{
				System.out.println(c.toString()+"has no matching license.\n");
				return false;
			}

			Vehicle selectedVehicle = null;
			for(Vehicle v : vg.getVehicles()) {
				if(v.getBranch() != b) continue;
				boolean overlap = false;
				for(Rental r : v.getRentals()) {
					// check next vehicle if any rental overlaps
					if(r.getStartDate().getTime() <= endDate.getTime() && startDate.getTime() <= r.getEndDate().getTime())
					{
						overlap = true;
						break;
					}
				}
				if(!overlap)
					selectedVehicle = v;
			}

			Rental r = new Rental(startDate, endDate, selectedVehicle, c);
			db.store(r);

			System.out.println(c.toString()+"just rent:\n"+selectedVehicle.toString()+":\n"+r.toString()+"\n");

			return true;
		}
		finally
		{
			unlock(db);
		}
	}
}
