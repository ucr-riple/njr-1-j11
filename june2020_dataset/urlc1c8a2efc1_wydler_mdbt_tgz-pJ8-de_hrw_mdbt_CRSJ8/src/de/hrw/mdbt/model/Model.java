package de.hrw.mdbt.model;

public class Model {
	private String manufacturer;
	private String name;
	private int capacity;
	private String measure;
	private LicenseClass requiredLicense;

	public Model() {
	}

	public Model(String manufacturer, String name, int capacity, String measure, LicenseClass requiredLicense) {
		setManufacturer(manufacturer);
		setName(name);
		setCapacity(capacity);
		setMeasure(measure);
		setRequiredLicense(requiredLicense);
	}

	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public LicenseClass getRequiredLicense() {
		return requiredLicense;
	}
	public void setRequiredLicense(LicenseClass requiredLicense) {
		this.requiredLicense = requiredLicense;
	}
}
