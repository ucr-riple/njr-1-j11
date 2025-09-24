package ch.zhaw.regularLanguages.dfa.mutations;

import java.util.Random;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;

public class RemoveRandomAcceptingStateMutation implements RandomMutation{

	@Override
	public boolean mutate(DeterministicFiniteAutomaton dfa) {
		Random rnd = new Random();
		
		Set<State> acceptingStates = dfa.getAcceptingStates();
		
		if(acceptingStates.size() <=1){
			return false;
		}else{
			acceptingStates.remove(acceptingStates.toArray()[rnd.nextInt(acceptingStates.size()-1)]);
			return true;
		}
	}

}
