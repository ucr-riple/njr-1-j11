package mp1401.examples.misterx.model.game.helpers;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.collections.GameItemList;

public interface Helper {
	
	public Game getGame();
	
	public Map getMap();
	
	public GameItemList<Connection> getConnections();
	
	public MisterX getMisterX();
	
	public GameItemList<Detective> getDetectives();

}
