package frs.hotgammon;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;

public interface TurnDeterminer {

	public Color nextTurn();
	public void setGame(Game game);
}
