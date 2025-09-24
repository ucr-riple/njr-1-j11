package ch.zhaw.regularLanguages.dfa;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import ch.zhaw.regularLanguages.dfa.mutations.MutationRegister;
import ch.zhaw.regularLanguages.evolution.candidates.Mutable;

public class RandomDeterministicFiniteAutomaton extends DeterministicFiniteAutomaton implements Mutable{
	private MutationRegister mr = new MutationRegister();
	
	
	/**
	 * Generates a random state machine.
	 * 
	 * Number of generated states: between 1 and noSymbols * 2
	 * Number of accepting states: noStates / 5. If there are less then 5 states we start with 1
	 * 
	 * @param alphabet list of symbols
	 * @return a random deterministic finite automaton for the given alphabet
	 */
	public RandomDeterministicFiniteAutomaton(char[] alphabet, int complexity){
		Random rnd = new Random();
		int rndI = 0;
		int noSymbols = alphabet.length;
		int maxStates = noSymbols*complexity*2;
		int noStates = rnd.nextInt(maxStates)+1;
		int noAcceptingStates = (noStates < 5 ? 1 : rnd.nextInt(noStates / 5));
		noAcceptingStates = noAcceptingStates < 1 ? 1 : noAcceptingStates; //ensure it is at least 1
		
		//System.out.println("Number of Accepting States: " + noAcceptingStates);
		
		Set<State> states = new TreeSet<State>();
		Set<State> acceptingStates = new TreeSet<State>();
		
		for(int i = 0; i < noStates;i++){
			State state = new State("q"+i);
			states.add(state);
		}
		
		State[] stateArr = states.toArray(new State[0]);
		
		for(int i = 0; i < noStates;i++){
			TransitionTable tt = new TransitionTable();
			for(int n = 0;n < noSymbols;n++){
				rndI = (noStates == 1 ? 0 : rnd.nextInt(noStates));
				tt.addTransition(alphabet[n], stateArr[rndI]);
			}
			stateArr[i].setTransitionTable(tt);
		}
		
		setStates(states);
		setStartState(getStateById("q0"));
		setAlphabet(alphabet);
		
		minimizeAutomaton(); //remove unreachable states
		
		noStates = getStates().size();
		stateArr = getStates().toArray(new State[0]);
		
		//add accepting states
		for(int i = 0;i < noAcceptingStates;i++){
			State state;
			do{
				rndI = (noStates == 1 ? 0 : rnd.nextInt(noStates));
				state = stateArr[rndI];
				//System.out.println("Accepting State: " + state);
			}while(acceptingStates.contains(state));
			
			acceptingStates.add(state);
		}
		
		setAcceptingStates(acceptingStates);
	}

	

	
	
	public RandomDeterministicFiniteAutomaton(DeterministicFiniteAutomaton dfa) {
		setAcceptingStates(dfa.getAcceptingStates());
		setAlphabet(dfa.getAlphabet());
		setStartState(dfa.getStartState());
		setStates(dfa.getStates());
	}

	@Override
	public void mutate(int nochanges) {
		if(mr == null){
			mr = new MutationRegister();
		}
		
		boolean check = false;
		do{
			check = mr.mutate(this);
		}while(check == false);
	}
	
	@Override
	public Object clone(){
		DeterministicFiniteAutomaton dfa = (DeterministicFiniteAutomaton)super.clone();
		return new RandomDeterministicFiniteAutomaton(dfa);
	}
		
}
