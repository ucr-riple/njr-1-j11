package mp1401.examples.misterx.model.game.helpers;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.game.GameImpl;
import mp1401.examples.misterx.model.gameitems.Connection;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.Map;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.gameitems.collections.GameItemList;

public class AbstractHelper implements Helper {

	@Override
	public Game getGame() {
		return GameImpl.getInstance();
	}

	@Override
	public Map getMap() {
		return getGame().getMap();
	}

	@Override
	public GameItemList<Connection> getConnections() {
		return getGame().getConnections();
	}

	@Override
	public MisterX getMisterX() {
		return getGame().getMisterX();
	}

	@Override
	public GameItemList<Detective> getDetectives() {
		return getGame().getDetectives();
	}
	
	

}
