package de.hrw.mdbt.model;

public class Address {
	private String state;
	private String city;
	private String street;
	private String zip;
	private String type;

	public Address(String state, String city, String street, String zip, String type) {
		setState(state);
		setCity(city);
		setStreet(street);
		setZip(zip);
		setType(type);
	}
	public Address(){
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
