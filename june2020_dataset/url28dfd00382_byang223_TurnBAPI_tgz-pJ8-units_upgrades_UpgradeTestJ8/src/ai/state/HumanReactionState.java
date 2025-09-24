package ai.state;

import java.util.ArrayList;

import units.interactions.Interaction;

/**
 * AIstate subclass in which the Human player responds to an attack on one of
 * its units by a Bot unit.
 * 
 * @author Shenghui
 */
public class HumanReactionState extends AIstate {

	ArrayList<String> legalNextStates;
	ArrayList<Interaction> validMoves;

	public HumanReactionState() {
		legalNextStates = new ArrayList<String>();
		validMoves = new ArrayList<Interaction>();

		legalNextStates.add("BotTurnState");
		legalNextStates.add("HumanTurnState");
	}

	@Override
	public ArrayList<Interaction> getValidMoves() {
		return validMoves;
	}

	@Override
	public boolean containsLegalNextStates(String state) {
		return legalNextStates.contains(state);
	}

	@Override
	public String getNextStateMovesComplete() {
		return "BotTurnState";
	}

}
