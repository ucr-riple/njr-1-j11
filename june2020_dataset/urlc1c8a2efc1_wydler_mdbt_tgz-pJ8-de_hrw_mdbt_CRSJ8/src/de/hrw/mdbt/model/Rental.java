package de.hrw.mdbt.model;

import java.util.Date;

import com.db4o.ObjectContainer;
import com.db4o.config.CommonConfigurationProvider;
import com.db4o.ext.Db4oException;

public class Rental {
	private Date startDate;
	private Date endDate;
	private Integer startKm = null;
	private Integer endKm = null;
	private Vehicle vehicle;
	private Customer customer;
	private String status;

	public Rental(Date start, Date end, Vehicle v, Customer c) {
		setStartDate(start);
		setEndDate(end);
		setVehicle(v);
		setCustomer(c);
	}

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStartKm() {
		return startKm;
	}
	public void setStartKm(int startKm) {
		this.startKm = startKm;
	}
	public int getEndKm() {
		return endKm;
	}
	public void setEndKm(int endKm) {
		this.endKm = endKm;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		if (this.vehicle != null)
			this.vehicle.removeRental(this);
		this.vehicle = vehicle;
		if (vehicle != null)
			vehicle.addRental(this);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static void configure(CommonConfigurationProvider config) {
	}

	public boolean objectCanNew (ObjectContainer container) throws Db4oException {
		if(this.customer == null) throw new Db4oException("Customer cannot be null!");
		if(this.startDate == null) throw new Db4oException("Start date cannot be null!");
		if(this.startKm != null)
			if(this.startKm < 0) throw new Db4oException("Start KM cannot be smaller 0!");
		if(this.endKm != null)
			if(this.endKm < 0) throw new Db4oException("End KM cannot be smaller 0!");
		if(this.vehicle == null) throw new Db4oException("Vehicle cannot be null!");

		return true;
	}

	@Override
	public String toString() {
		return
				"--------Rental--------\n" +
				"From " + startDate.toString() + " to " + endDate.toString() + "\n" +
				"StartKm: " + startKm + " EndKm: " + endKm + "\n" +
				"----------------------\n";
	}
}
