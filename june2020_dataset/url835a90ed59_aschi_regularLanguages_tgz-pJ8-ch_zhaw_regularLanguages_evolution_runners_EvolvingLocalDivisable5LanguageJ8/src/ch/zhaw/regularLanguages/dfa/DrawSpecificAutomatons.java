package ch.zhaw.regularLanguages.dfa;

import java.util.Set;
import java.util.TreeSet;

import ch.zhaw.regularLanguages.graphicalOutput.GraphvizRenderer;

public class DrawSpecificAutomatons {

	public static void main(String[] args) {
		Set<State> states = new TreeSet<State>();
		State startState = null;
		Set<State> acceptingStates = new TreeSet<State>();
		char[] alphabet = {'0','1'};
		
		State q0 = new State("q0");
		State q1 = new State("q1");
		State q2 = new State("q2");
		State q3 = new State("q3");
		State q4 = new State("q4");

		startState = q0;
		
		TransitionTable ttq0 = new TransitionTable();
		TransitionTable ttq1 = new TransitionTable();
		TransitionTable ttq2 = new TransitionTable();
		TransitionTable ttq3 = new TransitionTable();
		TransitionTable ttq4 = new TransitionTable();
		
		ttq0.addTransition('0', q1);
		ttq0.addTransition('1', q2);
		
		ttq1.addTransition('0', q2);
		ttq1.addTransition('1', q0);
		
		ttq2.addTransition('0', q0);
		ttq2.addTransition('1', q1);
		
		ttq3.addTransition('0', q4);
		ttq3.addTransition('1', q3);
		
		ttq4.addTransition('0', q3);
		ttq4.addTransition('1', q4);
		
		q0.setTransitionTable(ttq0);
		q1.setTransitionTable(ttq1);
		q2.setTransitionTable(ttq2);
		q3.setTransitionTable(ttq3);
		q4.setTransitionTable(ttq4);
		
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		
		acceptingStates.add(q4);

		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton(states, startState, acceptingStates, alphabet);
		
		GraphvizRenderer.renderGraph(dfa, "output.svg");
	
	}

}
