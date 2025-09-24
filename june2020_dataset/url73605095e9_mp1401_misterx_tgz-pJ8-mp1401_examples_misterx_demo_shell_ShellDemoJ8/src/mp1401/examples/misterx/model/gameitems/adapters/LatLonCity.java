package mp1401.examples.misterx.model.gameitems.adapters;

public class LatLonCity {

	private String name;
	private double lat;
	private double lon;

	public LatLonCity(String name, double lat, double lon) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}
}
