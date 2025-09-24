package ai;

import map.LevelMap;
import modes.models.GameModel;
import units.Unit;
import units.interactions.Interaction;

/**
 * The strategy interface for concrete subclass strategies that implement
 * different AI approches to the game
 * 
 * @author Shenghui
 */

public interface StrategyAI extends java.io.Serializable {

	/**
	 * Function to call to have the Bot complete its turn in GameMode.
	 */
	void completeTurn(LevelMap map, Bot thisP, GameModel myModel);

	/**
	 * Function called in AIstate to calculate the effectiveness of the input
	 * unit doing the input interaction. This is unique to each Strategy because
	 * each Strategy method weighs different actions and situations differently.
	 */
	int analyzeTurn(Unit units, Interaction interaction);
}
