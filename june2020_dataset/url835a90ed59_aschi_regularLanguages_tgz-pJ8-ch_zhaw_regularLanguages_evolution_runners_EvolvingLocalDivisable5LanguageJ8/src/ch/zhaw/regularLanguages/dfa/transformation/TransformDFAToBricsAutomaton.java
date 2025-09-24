package ch.zhaw.regularLanguages.dfa.transformation;

import java.util.Arrays;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.Transition;

/**
 * Transforms a DeterministicFiniteAutomaton into a dk.brics.automaton.Automaton. This is used to minimize & compare automatons (minimization & comparison of automatons is implemented in Brics solution)
 * @author adrian
 *
 */
public class TransformDFAToBricsAutomaton implements Transformer<DeterministicFiniteAutomaton, Automaton>{
	@Override
	public Automaton transform(DeterministicFiniteAutomaton input) {
		Automaton.setAllowMutate(true);
		Automaton bricsAutomaton = new Automaton();
		
		//Get existing state array
		ch.zhaw.regularLanguages.dfa.State[] existingStates = input.getStates().toArray(new State[0]);
		
		//Create new state array
		dk.brics.automaton.State[] newStates = new dk.brics.automaton.State[existingStates.length];
		for(int i = 0;i < newStates.length;i++){
			newStates[i] = new dk.brics.automaton.State();
		}
		
		for(int i = 0;i < existingStates.length;i++){
			//copy transition table
			for(char c : existingStates[i].getTransitionTable().getTransitionTable().keySet()){
				int targetIndex = Arrays.asList(existingStates).indexOf(existingStates[i].process(c));
				newStates[i].addTransition(new Transition(c, newStates[targetIndex]));
			}
		
			//set accepting
			if(input.isAcceptingState(existingStates[i])){
				newStates[i].setAccept(true);
			}else{
				newStates[i].setAccept(false);
			}
		}

		//replace states of emptyAutomaton
		Set<dk.brics.automaton.State> states = bricsAutomaton.getStates();
		states.clear();
		
		for(dk.brics.automaton.State s : newStates){
			states.add(s);
		}
		
		//set initial state
		bricsAutomaton.setInitialState(newStates[0]);

		return bricsAutomaton;
	}	
}
