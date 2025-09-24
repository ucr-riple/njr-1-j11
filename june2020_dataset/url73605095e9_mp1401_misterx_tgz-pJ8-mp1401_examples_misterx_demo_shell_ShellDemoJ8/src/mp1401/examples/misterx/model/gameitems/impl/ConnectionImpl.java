package mp1401.examples.misterx.model.gameitems.impl;

import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.enums.ConnectionType;

public class ConnectionImpl extends AbstractGameItemImpl implements Connection {
	
	private static final long serialVersionUID = -3615856223418598971L;
	
	private final City cityA;
	private final City cityB;
	private final ConnectionType connectionType;
	
	public ConnectionImpl(City cityA, City cityB, ConnectionType connectionType) {
		super();
		this.cityA = cityA;
		this.cityB = cityB;
		this.connectionType = connectionType;
	}
	
	@Override
	public City getCityA() {
		return cityA;
	}
	
	@Override
	public City getCityB() {
		return cityB;
	}

	@Override
	public ConnectionType getConnectionType() {
		return connectionType;
	}
	
	@Override
	public String toString() {
		return connectionType.toString() + " from " + cityA + " to " + cityB;
	}
}
