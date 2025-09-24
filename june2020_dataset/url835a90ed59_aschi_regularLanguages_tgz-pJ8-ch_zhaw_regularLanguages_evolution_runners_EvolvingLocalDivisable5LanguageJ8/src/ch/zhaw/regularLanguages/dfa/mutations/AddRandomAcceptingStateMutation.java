package ch.zhaw.regularLanguages.dfa.mutations;

import java.util.Random;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;

public class AddRandomAcceptingStateMutation implements RandomMutation{

	@Override
	public boolean mutate(DeterministicFiniteAutomaton dfa) {
		if(dfa.getStates().size() == dfa.getAcceptingStates().size()){
			return false;
		}
		
		Random rnd = new Random();
		
		Set<State> states = dfa.getStates();
		State state = null;
		
		State[] stateArr = states.toArray(new State[0]);
		
		do{
			state = stateArr[rnd.nextInt(states.size())];
		}while(dfa.getAcceptingStates().contains(state));
		
		dfa.getAcceptingStates().add(state);
		
		//return true as it can not fail
		return true;
	}

}
