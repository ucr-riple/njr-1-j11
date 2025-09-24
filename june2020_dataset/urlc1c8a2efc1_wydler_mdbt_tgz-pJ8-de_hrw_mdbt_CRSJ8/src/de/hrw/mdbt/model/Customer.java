package de.hrw.mdbt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Customer {
	private int id;
	private Date registerDate = GregorianCalendar.getInstance().getTime();
	private float discount = 0.0f;
	private ArrayList<LicenseClass> licenses = new ArrayList<LicenseClass>();

	public Customer() {
	}

	public Customer(int id) {
		setId(id);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public ArrayList<LicenseClass> getLicenses() {
		return licenses;
	}

	public void addLicense(LicenseClass lc) {
		licenses.add(lc);
	}
	public void removeLicense(LicenseClass lc) {
		licenses.remove(lc);
	}

	@Override
	public String toString() {
		return
				"--------Customer--------\n" +
				"ID: " + id + "\n" +
				"Discount: " + discount + "\n" +
				"-----------------------\n";
	}
}
