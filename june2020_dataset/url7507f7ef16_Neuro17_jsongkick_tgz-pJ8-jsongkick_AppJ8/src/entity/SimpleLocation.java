package entity;

public class SimpleLocation {
	private String city;
	private double lat;
	private double lng;
	
	public SimpleLocation(double lat, double lng, String city) {
		this.city = city;
		this.lat = lat;
		this.lng = lng;
	}

	public SimpleLocation(String city) {
		super();
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "SimpleLocation [city=" + city + ", lat=" + lat + ", lng=" + lng
				+ "]";
	}
	
}
