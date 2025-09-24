package ai;

import java.util.ArrayList;
import java.util.Iterator;

import map.LevelMap;
import modes.models.GameModel;
import units.Unit;
import units.interactions.Interaction;
import units.interactions.UnitButtonMove;

/**
 * This is the most basic implementation of a StrategyAI to test if the Strategy
 * class and Bot class are working. When it is the bot's turn, it kills one of
 * it's units.
 * 
 * @author Shenghui
 */

public class DumbStrategyAI implements StrategyAI {

	UnitButtonMove interactMove = new UnitButtonMove();

	@Override
	public void completeTurn(LevelMap map, Bot thisP, GameModel myState) {
		ArrayList<Unit> units = myState.getCurrentPlayer().getPlayerUnits()
				.getData();
		Iterator<Unit> it = units.iterator();

		if (it.hasNext()) {
			Unit unit = it.next();
			unit.beDestroyed(unit, map);
		}
	}

	@Override
	public int analyzeTurn(Unit unit, Interaction inter) {
		return 0;
	}

}
