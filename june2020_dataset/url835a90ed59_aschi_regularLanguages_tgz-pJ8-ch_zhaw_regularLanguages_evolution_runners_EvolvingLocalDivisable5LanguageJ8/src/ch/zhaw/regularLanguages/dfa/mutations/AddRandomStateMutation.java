package ch.zhaw.regularLanguages.dfa.mutations;

import java.util.Random;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;
import ch.zhaw.regularLanguages.dfa.TransitionTable;

public class AddRandomStateMutation implements RandomMutation{
	@Override
	public boolean mutate(DeterministicFiniteAutomaton dfa) {
		Random rnd = new Random();
		
		Set<State> states = dfa.getStates();
		State[] stateArr = states.toArray(new State[0]);
		
		char[] alphabet = dfa.getAlphabet();
		
		int noSymbols = alphabet.length;
		//int i = states.size();
		
		int i = 0;
		String stateId = "q"+i;
		while(!dfa.isStateIdAvailable(stateId)){
			i++;
			stateId = "q"+i;
		}
		State state = new State(stateId);
			
		i = states.size();
		
		TransitionTable tt = new TransitionTable();
		
		//generate transition table		
		for(int n = 0;n < noSymbols;n++){
			tt.addTransition(alphabet[n], stateArr[rnd.nextInt(i)]);
		}
		
		//add the new state
		state.setTransitionTable(tt);
		states.add(state);
		
		int avg = calcNOAverageIncomingEdges(states);
		for(int n = 0;n < ((avg/2) + rnd.nextInt(avg*2 - avg/2 + 1));n++){
			State origin;
			do{
				origin = dfa.getState(new State("q"+rnd.nextInt((i<=1?1:i-1))));
			}while(origin==null);
			
			dfa.changeLink(origin, alphabet[rnd.nextInt(noSymbols)], state);
		}
		
		//return true as it can not fail
		return true;
	}
	
	private int calcNOAverageIncomingEdges(Set<State> states){
		int total = 0;
		
		for(State s : states){
			for(Character c : s.getTransitionTable().getTransitionTable().keySet()){
				State target = s.getTransitionTable().getTransitionTable().get(c);
				if(target != null){
					total++;
				}
			}
		}
		
		int avg = total/states.size();
		return avg;
	}
}
