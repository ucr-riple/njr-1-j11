package de.hrw.mdbt.model;

import java.util.Date;
import java.util.ArrayList;

import com.db4o.ObjectContainer;

import de.hrw.mdbt.model.Model;

public class VehicleGroup {
	private boolean aboutToDelete = false;

	private int power;
	private String fuelType;
	private Date purchaseDate;
	private String color;
	private Model model;
	private PriceClass priceClass;
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	private ArrayList<Equipment> equipment = new ArrayList<Equipment>();
	
	public VehicleGroup() {
	}

	public VehicleGroup(int power, String fuelType, Date purchaseDate, String color, Model model, PriceClass priceClass) {
		setPower(power);
		setFuelType(fuelType);
		setPurchaseDate(purchaseDate);
		setColor(color);
		setModel(model);
		setPriceClass(priceClass);
	}

	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public PriceClass getPriceClass() {
		return priceClass;
	}
	public void setPriceClass(PriceClass priceClass) {
		this.priceClass = priceClass;
	}
	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}
	public void setEquipment(ArrayList<Equipment> equipments) {
		this.equipment = equipments;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	protected void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	protected void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
	protected void removeVehicle(Vehicle vehicle) {
		this.vehicles.remove(vehicle);
	}

	private boolean checkConstraints() {
		//HACK: prevent inserts/updates during objectOnDelete
		if (aboutToDelete)
			return false;

		if (this.power < 0)
			return false;
		if (this.fuelType == null) 
			return false;
		if (this.model == null)
			return false;
		if (this.priceClass == null)
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
}
