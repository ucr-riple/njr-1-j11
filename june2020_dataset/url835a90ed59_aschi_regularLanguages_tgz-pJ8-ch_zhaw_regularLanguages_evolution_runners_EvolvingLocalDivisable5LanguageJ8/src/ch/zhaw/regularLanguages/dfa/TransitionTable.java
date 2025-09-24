package ch.zhaw.regularLanguages.dfa;

import java.util.HashMap;
import java.util.Map;

public class TransitionTable {
	Map<Character, State> transitionTable;
	
	public TransitionTable(){
		transitionTable = new HashMap<Character, State>();
	}
	
	public void addTransition(Character character, State state){
		doubleCheckRemove(character);
		transitionTable.put(character, state);
	}
	
	public void updateTransition(Character character, State state){
		doubleCheckRemove(character);
		transitionTable.put(character, state);
	}
	
	public void doubleCheckRemove(Character character){
		transitionTable.remove(character);
		for(Character c : transitionTable.keySet()){
			if(c.charValue() == character.charValue()){
				transitionTable.remove(c);
			}
		}
	}
	
	/**
	 * Remove all links to the old target and replace it with a link to a new target
	 * @param oldTarget
	 * @param newTarget
	 */
	public void replaceTarget(State oldTarget, State newTarget){
		Map<Character, State> newTransitionTable = new HashMap<Character, State>();
		
		for(Character c : transitionTable.keySet()){
			if(transitionTable.get(c) == oldTarget){
				newTransitionTable.put(c, newTarget);
			}else{
				newTransitionTable.put(c, transitionTable.get(c));
			}
		}
		transitionTable = newTransitionTable;
	}
	
	public State process(Character character){
		return transitionTable.get(character);		
	}
	
	public Map<Character, State> getTransitionTable(){
		return transitionTable;
	}
}
