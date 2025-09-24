package entity;

public class City {
	private String name;
	private String country;
	private double latitude;
	private double longitude;
	private String state;
	
	public City(String name, String country, double latitude, double longitude, String state) {
		this.name = name;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
	}

	public City(String name, String country, double latitude, double longitude) {
		this.name = name;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public City(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", country=" + country + ", latitude="
				+ latitude + ", longitude=" + longitude + ", state=" + state
				+ "]";
	}
		
}
