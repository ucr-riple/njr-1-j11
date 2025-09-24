package ai.state;

import java.util.ArrayList;

import units.interactions.Interaction;
import units.interactions.UnitButtonAttack;
import units.interactions.UnitButtonMove;
import units.interactions.UnitButtonNova;
import units.interactions.UnitButtonProduce;
/**
 * AIstate subclass in which it is the Bot's turn to perform moves. 
 * 
 * @author Shenghui
 */
public class BotTurnState extends AIstate {

	ArrayList<String> legalNextStates;
	ArrayList<Interaction> validMoves;

	public BotTurnState() {
		legalNextStates = new ArrayList<String>();
		validMoves = new ArrayList<Interaction>();

		legalNextStates.add("HumanTurnState");
		legalNextStates.add("HumanReactionState");

		validMoves.add(new UnitButtonAttack());
		validMoves.add(new UnitButtonMove());
		validMoves.add(new UnitButtonNova());
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
