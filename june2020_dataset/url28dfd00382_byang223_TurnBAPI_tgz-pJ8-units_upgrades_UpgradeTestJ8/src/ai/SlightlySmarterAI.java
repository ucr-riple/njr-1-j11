package ai;

import java.util.ArrayList;
import java.util.Iterator;

import map.LevelMap;
import map.Tile;
import modes.models.GameModel;
import units.Unit;
import units.interactions.Interaction;
import units.interactions.UnitButtonMove;

/**
 * This is a slightly more complex implementation of a StrategyAI to demonstrate
 * that the Bot player is capable of completing interactions such as "move" just
 * like a human player.
 * 
 * @author Shenghui
 */

public class SlightlySmarterAI implements StrategyAI {

	@Override
	public void completeTurn(LevelMap map, Bot thisP, GameModel myState) {

		ArrayList<Unit> units = myState.getCurrentPlayer().getPlayerUnits()
				.getData();
		Iterator<Unit> it = units.iterator();
		Unit unit = it.next();
		UnitButtonMove myMV = new UnitButtonMove();
		myState.setSelectedUnit(unit);
		Tile unitTileisOn = map.getTileByCoords(unit.getXTileLoc(),
				unit.getYTileLoc());
		myState.setSelectedTile(unitTileisOn);
		Tile selectedDestination = map.getTileByCoords(6, 5);

		myState.setSelectedDestination(selectedDestination);
		myMV.performButton(myState);
	}

	@Override
	public int analyzeTurn(Unit units, Interaction inter) {
		return 0;
	}

}
