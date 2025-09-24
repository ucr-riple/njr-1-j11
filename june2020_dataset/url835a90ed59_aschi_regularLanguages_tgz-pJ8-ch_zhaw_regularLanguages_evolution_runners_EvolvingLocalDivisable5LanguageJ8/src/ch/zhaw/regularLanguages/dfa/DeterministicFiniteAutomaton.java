package ch.zhaw.regularLanguages.dfa;

import java.util.Set;
import java.util.TreeSet;

import ch.zhaw.regularLanguages.dfa.optimisation.Otpimiser;
import ch.zhaw.regularLanguages.dfa.optimisation.UnreachableStateRemover;
import ch.zhaw.regularLanguages.graphicalOutput.GraphvizRenderable;

/**
 * Class DeterministicFiniteAutomaton
 * Represents Det
 * 
 * @author adrian
 *
 */
public class DeterministicFiniteAutomaton implements GraphvizRenderable{
	private Set<State> states;
	private State startState;
	private Set<State> acceptingStates;
	private char[] alphabet;
	
	private Otpimiser<DeterministicFiniteAutomaton> removeUnreachableStates;
	
	public DeterministicFiniteAutomaton(){
		super();
		removeUnreachableStates = new UnreachableStateRemover();
	}
	
	public DeterministicFiniteAutomaton(Set<State> states, State startState,
			Set<State> acceptingStates, char[] alphabet) {
		super();
		
		if(alphabet.length == 0){
			throw new IllegalArgumentException("Alphabet can not be empty!");
		}
		if(states.isEmpty()){
			throw new IllegalArgumentException("States can not be empty!");	
		}
		if(startState == null){
			throw new IllegalArgumentException("Start state can not be null!");				
		}
		
		removeUnreachableStates = new UnreachableStateRemover();
		
		this.states = states;
		this.startState = startState;
		this.acceptingStates = acceptingStates;
		this.alphabet = alphabet;
	}


	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}

	public Set<State> getStates() {
		return states;
	}

	public Set<State> getAcceptingStates() {
		return acceptingStates;
	}

	public char[] getAlphabet() {
		return alphabet;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	public void setAcceptingStates(Set<State> acceptingStates) {
		this.acceptingStates = acceptingStates;
	}

	public void setAlphabet(char[] alphabet) {
		this.alphabet = alphabet;
	}

	
	public boolean isStateIdAvailable(String stateId){
		for(State s : states){
			if(s.getId().equals(stateId)){
				return false;
			}
		}
		return true;
	}
	
	
	
	public State process(char[] input){
		State current = this.startState;
		//System.out.println("Start: " + current);
		for(int i = 0;i < input.length;i++){
			if(current != null){
				current = current.process(input[i]);
				//System.out.println("Step " + i + ":" + current);
			}
		}
		return current;
	}
	
	public void changeLink(State origin, Character character, State target){
		if(states.contains(origin) && states.contains(target)){
			origin.getTransitionTable().updateTransition(character, target);
		}
	}
	
	public boolean isAcceptingState(State state){
		if(acceptingStates != null && acceptingStates.contains(state)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public void minimizeAutomaton(){
		//System.out.println("Minimize Automaton:");
		//System.out.println("No States before: " + getStates().size());
		removeUnreachableStates.optimise(this);
		//System.out.println("No States after: " + getStates().size());
	}
	

	@Override
	public Object clone(){
		Set<State> states = new TreeSet<State>();
		State startState;
		Set<State> acceptingStates = new TreeSet<State>();
		char[] alphabet;
		
		
		//use the same alphabet
		alphabet = getAlphabet();
		
		//copy states
		for(State s : getStates()){
			states.add(new State(s.getId()));
		}
		
		
		//copy transitionTable
		for(State s : getStates()){
			TransitionTable tt = new TransitionTable();
			
			for(Character c : s.getTransitionTable().transitionTable.keySet()){
				State oldStateRef = s.getTransitionTable().getTransitionTable().get(c);
				tt.addTransition(c, getStateRef(states, oldStateRef));
			}
			
			getStateRef(states, s).setTransitionTable(tt);
		}
		
		//startState
		startState = getStateRef(states, getStartState());
		
		//acceptingStates
		for(State s : getAcceptingStates()){
			if(!getStates().contains(s)){
				System.out.println("accepting state not in state list!!");
			}
			acceptingStates.add(getStateRef(states, s));
		}
		
		return new DeterministicFiniteAutomaton(states, startState,
				acceptingStates, alphabet);
	}
	
	public State getState(State searchToken){
		return getStateRef(states, searchToken);
	}
	
	public State getStateRef(Set<State> newStateSet, State oldRef){
		for(State newRef : newStateSet){
			if(newRef.equals(oldRef)){
				return newRef;
			}
		}
		return null;
	}
	
	public State getStateById(String id){
		State rv = null;
		
		for(State s : getStates()){
			if(s.getId().equals(id)){
				rv = s;
				break;
			}
		}
		return rv;
	}
	
	public String generateDotString(){
		//minimizeAutomaton();
		
		String output = "digraph G {" + System.getProperty("line.separator");
		for(State s : states){
			for(Character c : s.getTransitionTable().getTransitionTable().keySet()){
				output+= s + " -> " + s.getTransitionTable().process(c) + " [label=\""+ c +"\"]"+ System.getProperty("line.separator");
			}
		}
		for(State s : acceptingStates){
			if(!s.equals(startState)){
				output+= s + " [peripheries=2]" + System.getProperty("line.separator");
			}
		}
		
		if(acceptingStates.contains(startState)){
			output += getStartState() + " [color=darkred,peripheries=2]" + System.getProperty("line.separator");
		}else{
			output += getStartState() + " [color=darkred]" + System.getProperty("line.separator");
		}
		
		
		output += "}";
		
		return output;
	}
}
