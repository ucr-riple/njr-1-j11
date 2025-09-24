package ch.zhaw.regularLanguages.dfa.optimisation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;

public class UnreachableStateRemover implements Otpimiser<DeterministicFiniteAutomaton>{

	@Override
	public void optimise(DeterministicFiniteAutomaton obj) {
		Set<State> processed = new HashSet<State>();
		List<State> nextList = new LinkedList<State>();
		List<State> queue = new LinkedList<State>();
		char[] alphabet = obj.getAlphabet();
		
		State next = null;
		State current = obj.getStartState();
		queue.add(current);
		while(queue.size() > 0){
			nextList.clear();
			
			//get all linked states
			for (char c : alphabet) {
				next = current.process(c);
				nextList.add(next);
				//System.out.println("Found a link: " + current.toString() + " -- (" + c + ") -->" + next.toString());
			}
			
			processed.add(current);
			queue.remove(current);
			
			for (State s : nextList){
				if(!processed.contains(s)){
					queue.add(s);
				}
			}
			
			if(queue.size() > 0){
				current = queue.get(0);
			}else{
				current = null;
			}
		}
		
		Set<State> newStates = processed;
		Set<State> newAcceptingStates = new HashSet<State>();
		
		//remove unreachable accepting states
		if(obj.getAcceptingStates() != null){
			for(State s : obj.getAcceptingStates()){
				if(processed.contains(s)){
					newAcceptingStates.add(s);
				}
			}
		}
		
		//set new (optimised) states
		obj.setAcceptingStates(newAcceptingStates);
		obj.setStates(newStates);
	}
}
