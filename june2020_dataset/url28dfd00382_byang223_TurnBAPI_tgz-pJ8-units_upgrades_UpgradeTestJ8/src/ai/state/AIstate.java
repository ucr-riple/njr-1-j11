package ai.state;

import java.util.ArrayList;

import modes.models.GameModel;
import units.Unit;
import units.interactions.Interaction;
import ai.Bot;
import attribute.AttributeMove;

/***
 * AIstate abstract superclass that governs the states in the Bot Finite State
 * Machine Hiearchy.
 * 
 * @author Shenghui Sun
 */

public abstract class AIstate {

	/**
	 * Performs the calculation for the Bot's StrategyAI believes to be the
	 * optimal action. After the State is out of moves, the next state is set
	 * depending on the current state and its next state in the finite state
	 * diagram.
	 * 
	 * @param myModel
	 */
	public void doMove(GameModel myModel) {

		Bot currPlayer = (Bot) myModel.getCurrentPlayer();
		ArrayList<Unit> units = currPlayer.getPlayerUnits().getData();

		ArrayList<Interaction> validMoves = getValidMoves();

		while (getMovesLeft(myModel) >= 0) {
			int maxPt = 0;
			Unit bestUnit = null;
			Interaction bestInteraction = null;

			for (Unit u : units) {
				for (Interaction i : validMoves) {
					if (u.getInteractionList().contains(i)) {
						int pts = currPlayer.getStrategy().analyzeTurn(u, i);

						if (pts > maxPt) {
							maxPt = pts;
							bestUnit = u;
							bestInteraction = i;
						}
					}
				}
			}

			currPlayer.doMove(bestUnit, bestInteraction);
		}

		// moves are done
		AIstateController.setState(getNextStateMovesComplete());
	}

	/**
	 * Function to access the correct next state after all the moves have been
	 * completed.
	 * 
	 * @return
	 */
	public abstract String getNextStateMovesComplete();

	/**
	 * Returns a list of valid interactions for the current state. Called in
	 * AIstate.doMove() by the AIstateController. The list of interactions and
	 * the list of the state's units are iterated through to calculate the
	 * optimal move for the Bot. Each AIstate subclass has a list of
	 * interactions that are allowed in that state and a list of valid next
	 * states in accordance to the finite state diagram.
	 * 
	 * @return
	 */
	public abstract ArrayList<Interaction> getValidMoves();

	/**
	 * Function to check if the AIstateController's attempt to change to the
	 * next state in its setState() method follows the finite state diagram.
	 * 
	 * @param state
	 * @return
	 */
	public abstract boolean containsLegalNextStates(String state);

	/**
	 * Function to calculate how many moves are left. Used in AIstate.doMove().
	 * The Bot will iterate through and complete actions until it is out of
	 * moves.
	 * 
	 * @param myModel
	 * @return
	 */
	public int getMovesLeft(GameModel myModel) {
		AttributeMove move = (AttributeMove) myModel.getSelectedUnit()
				.getAttribute("Move");

		return move.getMovesLeft();
	}
}
