package ch.zhaw.regularLanguages.dfa.mutations;

import java.util.Random;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;

public class RemoveRandomStateMutation implements RandomMutation {

	@Override
	public boolean mutate(DeterministicFiniteAutomaton dfa) {
		Random rnd = new Random();
		
		Set<State> states = dfa.getStates();
		State[] stateArr = states.toArray(new State[0]);
		
		if(states.size() <= 2){
			return false;
		}else{
			//index of the state to be removed
			int i;
			State oldState = null;
			
			//Start state shall not be removed
			do{
				i = rnd.nextInt(states.size());
				oldState = stateArr[i];
			}while(dfa.getStartState() == oldState || (dfa.getAcceptingStates().contains(oldState) && dfa.getAcceptingStates().size() == 1));
			
			//replace links to the to-be-removed state from all other states
			for(State s : states){
				//TODO: Evaluate if self link or random link is to be prefered
				//s.getTransitionTable().replaceTarget(oldState, s); //self link
				
				//get a replacement state
				State newState = null;
				do{
					newState = stateArr[rnd.nextInt(states.size())];	
				}while(newState == oldState);
				s.getTransitionTable().replaceTarget(oldState, newState); // random link
			}

			if(dfa.getAcceptingStates().contains(oldState)){
				dfa.getAcceptingStates().remove(oldState);
			}
			
			//remove the old state
			states.remove(oldState);
			
			return true;
		}
	}
}
