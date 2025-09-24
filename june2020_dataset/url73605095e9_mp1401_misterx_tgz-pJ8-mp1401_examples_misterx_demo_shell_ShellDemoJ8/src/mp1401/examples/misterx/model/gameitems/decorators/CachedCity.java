package mp1401.examples.misterx.model.gameitems.decorators;

import java.util.List;

import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.observers.GameItemObserver;


public class CachedCity extends AbstractCityDecorator implements GameItemObserver {

	private static final long serialVersionUID = -3645095023467610355L;
	
	private List<Connection> cachedConnections;
	private boolean isConnectionCacheValid;
	
	public CachedCity(City city) {
		super(city);
		isConnectionCacheValid = false;
		getGame().getConnections().addGameItemObserver(this);
	}

	@Override
	public List<Connection> getConnections() {
		if(!isConnectionCacheValid()) {
			cachedConnections = super.getConnections();
			isConnectionCacheValid = true;
		}
		return cachedConnections;
	}
	
	private boolean isConnectionCacheValid() {
		return isConnectionCacheValid;
	}

	@Override
	public void gameItemUpdate() {
		isConnectionCacheValid = false;
	}
}
