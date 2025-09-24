package mp1401.examples.misterx.model.mapparser;

import java.util.List;

import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;


public class MapData {
	
	public List<City> cities;
	public List<Connection> connections;

	public MapData() {
	}
	
	public MapData(List<City> cities, List<Connection> connections) {
		this.cities = cities;
		this.connections = connections;
	}
}
