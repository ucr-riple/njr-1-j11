package mp1401.examples.misterx.demo.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mp1401.examples.misterx.model.factory.GameItemFactory;
import mp1401.examples.misterx.model.factory.GameItemFactoryImpl;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.enums.ConnectionType;
import mp1401.examples.misterx.model.mapparser.MapData;
import mp1401.examples.misterx.model.mapparser.MapDataParser;


public class DemoMapDataParser implements MapDataParser{

	@Override
	public MapData getMapData() {
		List<City> cities = new ArrayList<City>();
		List<Connection> connections = new ArrayList<Connection>();
		
		GameItemFactory factory = GameItemFactoryImpl.getInstance();
		
		City basel = factory.createCity("Basel", new Point(3, 1));
		City zuerich = factory.createCity("Zürich", new Point(5, 2));
		City bern = factory.createCity("Bern", new Point(3, 3));
		City luzern = factory.createCity("Luzern", new Point(4, 4));
		City lausanne = factory.createCity("Lausanne", new Point(2, 5));
		City chur = factory.createCity("Chur", new Point(6, 5));
		City genf = factory.createCity("Genf", new Point(1, 6));
		cities.add(zuerich);
		cities.add(basel);
		cities.add(bern);
		cities.add(luzern);
		cities.add(lausanne);
		cities.add(chur);
		cities.add(genf);
		
		connections.add(factory.createConnection(genf, basel, ConnectionType.PLANE));
		connections.add(factory.createConnection(genf, lausanne, ConnectionType.TRAIN));
		connections.add(factory.createConnection(lausanne, bern, ConnectionType.TRAIN));
		connections.add(factory.createConnection(lausanne, luzern, ConnectionType.CAR));
		connections.add(factory.createConnection(bern, basel, ConnectionType.TRAIN));
		connections.add(factory.createConnection(bern, luzern, ConnectionType.CAR));
		connections.add(factory.createConnection(bern, zuerich, ConnectionType.TRAIN));
		connections.add(factory.createConnection(zuerich, basel, ConnectionType.PLANE));
		connections.add(factory.createConnection(zuerich, luzern, ConnectionType.TRAIN));
		connections.add(factory.createConnection(zuerich, chur, ConnectionType.TRAIN));
		connections.add(factory.createConnection(chur, luzern, ConnectionType.CAR));
		
		return new MapData(cities, connections);
	}

}
