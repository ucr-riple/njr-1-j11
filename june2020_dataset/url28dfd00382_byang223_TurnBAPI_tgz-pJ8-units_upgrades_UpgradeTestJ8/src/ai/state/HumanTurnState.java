package ai.state;

import java.util.ArrayList;

import units.interactions.Interaction;
/**
 * AIstate subclass in which it is the Human player's turn, and the Bot waits. 
 * 
 * @author Shenghui
 */
public class HumanTurnState extends AIstate{

	ArrayList<String> legalNextStates; 
	ArrayList<Interaction> validMoves; 


	public HumanTurnState(){
		legalNextStates = new ArrayList<String>(); 
		validMoves = new ArrayList<Interaction>(); 
		
		legalNextStates.add("BotTurnState");
		legalNextStates.add("HumanTurnState");
		legalNextStates.add("BotReactionState");
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
