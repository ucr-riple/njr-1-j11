package ai.state;

import java.util.TreeMap;

import modes.models.GameModel;
import player.Player;
import units.Unit;

/**
 * Controller class that controls the transitions from state to state in
 * accordance to the finite state machine.
 * 
 * @author Shenghui
 */
public class AIstateController {

	private static TreeMap<String, AIstate> myStates;
	private static AIstate currentState;
	private static GameModel myModel;

	public AIstateController() {
		myStates = new TreeMap<String, AIstate>();
	}

	public void initalizeController(String startState, GameModel myModel) {
		myStates.put("BotReactionState", new BotReactionState());
		myStates.put("BotTurnState", new BotTurnState());
		myStates.put("HumanReactionState", new HumanReactionState());
		myStates.put("HumanTurnState", new HumanTurnState());

		this.myModel = myModel;
		currentState = myStates.get(startState);
	}

	/**
	 * Function that checks if the input state is legal in accordance to the
	 * finite state machine. If so, transition to next state.
	 * 
	 * @param state
	 */
	public static void setState(String state) {
		if (currentState.containsLegalNextStates(state)) {
			currentState = myStates.get(state);
		}
	}

	/**
	 * Signal method in the Attack and Nova ButtonInteraction classes to notify
	 * the Controller when a Unit is under attack. This is used to transition to
	 * either the HumanReactionState or the BotReactionState.
	 * 
	 * @param target
	 */
	public static void sendInteraction(Unit target) {
		Player beingAttacked = (Player) target.getOwner();

		if (beingAttacked.getType().equals("BOT")) {
			setState("BotReactionState");
		} else {
			setState("HumanReactionState");
		}
	}

	/**
	 * Signal method from the GameModel.updateTurn method. Every time the turn
	 * is switched, the Controller is notified. This is used for the
	 * HumanTurnState and the BotTurnState.
	 * 
	 * @param current
	 */
	public static void sendCurrentPlayerUpdate(Player current) {
		Player currentPlayer = current;
		if (currentPlayer.getType().equals("BOT")) {
			setState("BotTurnState");
		} else {
			setState("HumanTurnState");
		}
	}

}