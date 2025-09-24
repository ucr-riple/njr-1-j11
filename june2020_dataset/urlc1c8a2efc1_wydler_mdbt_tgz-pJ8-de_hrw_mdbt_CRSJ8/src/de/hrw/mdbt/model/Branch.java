package de.hrw.mdbt.model;

import java.sql.Time;
import java.util.ArrayList;

import com.db4o.ObjectContainer;
import com.db4o.config.CommonConfigurationProvider;
import com.db4o.constraints.UniqueFieldValueConstraint;

import de.hrw.mdbt.model.Address;
import de.hrw.mdbt.model.Vehicle;

public class Branch {
	private static final Time MIN_OPENINGTIME = Time.valueOf("5:0:0");
	
	private boolean aboutToDelete = false;

	private String name;
	private Address address;
	private Time openingTime;
	private Time closingTime;
	private String phone;
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

	public Branch(String name, Address address, Time openingTime, Time closingTime, String phone)
	{
		setName(name);
		setAddress(address);
		setOpeningTime(openingTime);
		setClosingTime(closingTime);
		setPhone(phone);
	}

	public Branch() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Time getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Time opening) {
		if(opening != null && opening.before(MIN_OPENINGTIME)) {
			throw new IllegalArgumentException("Opening time invalid!");
		}
		this.openingTime = opening;
	}
	public Time getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(Time closing) {
		this.closingTime = closing;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	protected void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
	
	protected void removeVehicle(Vehicle vehicle) {
		this.vehicles.remove(vehicle);
	}

	public static void configure(CommonConfigurationProvider config) {
		config.common().objectClass(Branch.class).objectField("phone").indexed(true);
		config.common().add(new UniqueFieldValueConstraint(Branch.class, "phone"));
	}

	private boolean checkConstraints() {
		//HACK: prevent inserts/updates during objectOnDelete
		if (aboutToDelete)
			return false;

		if (this.name == null)
			return false;
		if (this.phone == null) 
			return false;

		return true;
	}

	public boolean objectCanNew(ObjectContainer db) {
		return checkConstraints();
	}

	public boolean objectCanUpdate(ObjectContainer db) {
		return checkConstraints();
	}

	public void objectOnDelete(ObjectContainer db) {
		aboutToDelete = true;

		ArrayList<Vehicle> vehiclesToDelete = new ArrayList<Vehicle>(vehicles);
		for (Vehicle v : vehiclesToDelete) {
			if (v != null)
			{
				db.activate(v, 1);
				db.delete(v);
			}
		}
	}

	public String toString() {
		return
				"--------Branch--------\n" +
				name + "\n" +
				address + "\n" +
				"From " + openingTime.toString() + " to " + closingTime.toString() + "\n" +
				"Phone: " + phone +
				"----------------------\n";
	}
}
