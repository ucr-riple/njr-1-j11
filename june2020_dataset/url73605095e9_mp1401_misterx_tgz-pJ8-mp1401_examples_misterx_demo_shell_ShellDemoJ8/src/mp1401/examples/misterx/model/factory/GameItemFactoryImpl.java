package mp1401.examples.misterx.model.factory;

import java.awt.Point;

import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.decorators.CachedCity;
import mp1401.examples.misterx.model.gameitems.enums.ConnectionType;
import mp1401.examples.misterx.model.gameitems.enums.DetectiveType;
import mp1401.examples.misterx.model.gameitems.impl.CityImpl;
import mp1401.examples.misterx.model.gameitems.impl.ConnectionImpl;
import mp1401.examples.misterx.model.gameitems.impl.DetectiveImpl;
import mp1401.examples.misterx.model.gameitems.impl.MapImpl;
import mp1401.examples.misterx.model.gameitems.impl.MisterXImpl;
import mp1401.examples.misterx.model.gameitems.impl.UnknownCharacterImpl;
import mp1401.examples.misterx.model.gameitems.impl.UnknownCityImpl;


public class GameItemFactoryImpl implements GameItemFactory {
	
	private static GameItemFactory instance;
	
	private GameItemFactoryImpl() {
	}
	
	public static GameItemFactory getInstance() {
		if(instance == null) {
			instance = new GameItemFactoryImpl();
		}
		return instance;
	}

	@Override
	public Map createMap() {
		return new MapImpl();
	}

	@Override
	public City createCity(String name, Point position) {
		return new CachedCity(new CityImpl(name, position));
	}

	@Override
	public City createUnknownCity() {
		return new UnknownCityImpl();
	}

	@Override
	public Connection createConnection(City cityA, City cityB, ConnectionType type) {
		return new ConnectionImpl(cityA, cityB, type);
	}

	@Override
	public MisterX createMisterX() {
		return new MisterXImpl();
	}

	@Override
	public Detective createDetective(DetectiveType type) {
		return new DetectiveImpl(type);
	}

	@Override
	public Character createUnknownCharacter() {
		return new UnknownCharacterImpl();
	}

}
