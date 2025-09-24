package mp1401.examples.misterx.model.factory;

import java.awt.Point;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.enums.ConnectionType;
import mp1401.examples.misterx.model.gameitems.enums.DetectiveType;


public interface GameItemFactory {
	
	public Map createMap();
	
	public City createCity(String name, Point position);
	
	public City createUnknownCity();

	public Connection createConnection(City cityA, City cityB, ConnectionType type);

	public MisterX createMisterX();

	public Detective createDetective(DetectiveType type);
	
	public Character createUnknownCharacter();

}
