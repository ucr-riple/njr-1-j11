package entity;

public class FullLocation {
	private MetroArea metroarea;
	private City city;
	
	public FullLocation(MetroArea metroarea, City city) {
		this.metroarea = metroarea;
		this.city = city;
	}

	public MetroArea getMetroarea() {
		return metroarea;
	}

	public void setMetroarea(MetroArea metroarea) {
		this.metroarea = metroarea;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
