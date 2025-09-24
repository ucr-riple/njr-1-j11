package entity;
public class Venue {
	
	private double latitude;
	private double longitude;
	private MetroArea metroArea;
	private String id;
	private String displayName;
	
	public Venue(double latitude, double longitude, MetroArea metroArea, String id, String displayName) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.metroArea = metroArea;
		this.id = id;
		this.displayName = displayName;
	}

	public Venue(MetroArea metroArea, String id, String displayName) {
		super();
		this.metroArea = metroArea;
		this.id = id;
		this.displayName = displayName;
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
	
	public MetroArea getMetroArea() {
		return metroArea;
	}
	
	public void setMetroArea(MetroArea metroArea) {
		this.metroArea = metroArea;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return "Venue [latitude=" + latitude + ", longitude=" + longitude
				+ ", metroArea=" + metroArea + ", id=" + id + ", displayName="
				+ displayName + "]";
	}	
}