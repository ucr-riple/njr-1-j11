package ch.zhaw.regularLanguages.dfa.transformation;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;
import ch.zhaw.regularLanguages.dfa.TransitionTable;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

public class TransformDFAToBricsAutomatonTest {
	private DeterministicFiniteAutomaton dfa;
	private Transformer<DeterministicFiniteAutomaton, Automaton> t;
	private Automaton referenceAutomaton;
	
	@Before
	public void setUp() throws Exception {
		Set<State> states = new TreeSet<State>();
		
		char[] alphabet = {'a', 'b', 'c'};
		
		State q0 = new State("q0");
		State q1 = new State("q1");
		State q2 = new State("q2");
		State q3 = new State("q3");
		
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		
		
		TransitionTable ttq0 = new TransitionTable();
		ttq0.addTransition('a', q1);
		ttq0.addTransition('b', q0);
		ttq0.addTransition('c', q0);
		q0.setTransitionTable(ttq0);
		
		TransitionTable ttq1 = new TransitionTable();
		ttq1.addTransition('a', q1);
		ttq1.addTransition('b', q2);
		ttq1.addTransition('c', q0);
		q1.setTransitionTable(ttq1);
		
		TransitionTable ttq2 = new TransitionTable();
		ttq2.addTransition('a', q1);
		ttq2.addTransition('b', q0);
		ttq2.addTransition('c', q3);
		q2.setTransitionTable(ttq2);
		
		TransitionTable ttq3 = new TransitionTable();
		ttq3.addTransition('a', q1);
		ttq3.addTransition('b', q0);
		ttq3.addTransition('c', q0);
		q3.setTransitionTable(ttq3);
		
		Set<State> acceptingStates = new TreeSet<State>();
		acceptingStates.add(q3);
		
		dfa = new DeterministicFiniteAutomaton(states, q0, acceptingStates, alphabet);
		t = new TransformDFAToBricsAutomaton();
		
		referenceAutomaton = new RegExp("[abc]*abc").toAutomaton();
	}

	@Test
	public void testTransform() {
		Automaton out = t.transform(dfa);
		assertEquals(out, referenceAutomaton);
	}

}
