package mp1401.examples.misterx.model.gameitems;

import mp1401.examples.misterx.model.gameitems.enums.ConnectionType;

public interface Connection extends GameItem {

	public City getCityA();

	public City getCityB();

	public ConnectionType getConnectionType();
}
