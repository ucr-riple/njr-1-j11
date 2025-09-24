package mp1401.examples.misterx.model.gameitems;

import mp1401.examples.misterx.model.gameitems.collections.GameItemList;
import mp1401.examples.misterx.model.mapparser.MapDataParser;


public interface Map extends GameItem {
	
	public void setMapDataParser(MapDataParser mapDataParser);
	
	public void fillMap();
	
	public boolean isFilled();
	
	public GameItemList<Connection> getConnections();
	
	public GameItemList<City> getCities();
	
	public City getCityByName(String name);

}
