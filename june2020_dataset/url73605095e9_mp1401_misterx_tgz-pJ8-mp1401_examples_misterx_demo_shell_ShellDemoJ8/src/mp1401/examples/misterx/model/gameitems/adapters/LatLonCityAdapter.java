package mp1401.examples.misterx.model.gameitems.adapters;

import java.awt.Point;

import mp1401.examples.misterx.model.factory.GameItemFactoryImpl;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.decorators.AbstractCityDecorator;


public class LatLonCityAdapter extends AbstractCityDecorator implements City {

	private static final long serialVersionUID = 8465915443040168716L;
	
	LatLonCity latLonCity;

	public LatLonCityAdapter(LatLonCity latLonCity) {
		super(GameItemFactoryImpl.getInstance().createUnknownCity());
		this.latLonCity = latLonCity;
	}

	@Override
	public String getName() {
		return latLonCity.getName();
	}

	@Override
	public Point getPosition() {
		int x = Math.round((float)latLonCity.getLon());
		int y = Math.round((float)latLonCity.getLat());
		return new Point(x, y);
	}
}
