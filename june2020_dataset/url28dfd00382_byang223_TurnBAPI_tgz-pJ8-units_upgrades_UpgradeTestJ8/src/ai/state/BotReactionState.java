package ai.state;

import java.util.ArrayList;

import units.*;
import units.interactions.*;

/**
 * AIstate subclass in which the Bot responds to an attack on one of
 * its units by a Human Player unit.
 * 
 * @author Shenghui
 */
public class BotReactionState extends AIstate {

	ArrayList<String> legalNextStates;
	ArrayList<Interaction> validMoves;

	public BotReactionState() {
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
		return "HumanTurnState";
	}

}
