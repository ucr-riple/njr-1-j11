package entity;

public class MetroArea {
	private String country;
	private String id;
	private String displayName;
		
	public MetroArea(String country, String id) {
		this.country = country;
		this.id = id;
	}
	
	public MetroArea(String country, String id, String displayName) {
		this.country = country;
		this.id = id;
		this.displayName = displayName;
	}

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
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
		return "MetroArea [country=" + country + ", id=" + id
				+ ", displayName=" + displayName + "]";
	}
	
}
