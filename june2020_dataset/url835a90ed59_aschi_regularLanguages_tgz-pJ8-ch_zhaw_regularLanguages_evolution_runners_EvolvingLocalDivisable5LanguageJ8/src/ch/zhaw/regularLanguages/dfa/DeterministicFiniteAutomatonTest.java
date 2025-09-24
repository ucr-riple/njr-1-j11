package ch.zhaw.regularLanguages.dfa;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class DeterministicFiniteAutomatonTest {
	Set<State> states;
	State startState;
	Set<State> acceptingStates;
	char[] alphabet = {'0','1'};
	DeterministicFiniteAutomaton dfa;
	
	State q0;
	State q1;
	State q2;
	State q3;
	State q4;
	
	@Before
	public void setUp(){
		states = new TreeSet<State>();
		startState = null;
		acceptingStates = new TreeSet<State>();
		
		q0 = new State("q0");
		q1 = new State("q1");
		q2 = new State("q2");
		q3 = new State("q3");
		q4 = new State("q4");

		startState = q0;
		
		TransitionTable ttq0 = new TransitionTable();
		TransitionTable ttq1 = new TransitionTable();
		TransitionTable ttq2 = new TransitionTable();
		TransitionTable ttq3 = new TransitionTable();
		TransitionTable ttq4 = new TransitionTable();
		
		ttq0.addTransition('0', q1);
		ttq0.addTransition('1', q4);
		
		ttq1.addTransition('0', q2);
		ttq1.addTransition('1', q0);
		
		ttq2.addTransition('0', q3);
		ttq2.addTransition('1', q1);
		
		ttq3.addTransition('0', q4);
		ttq3.addTransition('1', q2);
		
		ttq4.addTransition('0', q0);
		ttq4.addTransition('1', q3);
		
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

		dfa = new DeterministicFiniteAutomaton(states, startState, acceptingStates, alphabet);
	}

	@Test
	public void testGetStartState() {
		assertEquals(dfa.getStartState(), q0);
	}

	@Test
	public void testSetStartState() {
		dfa.setStartState(q1);
		assertEquals(dfa.getStartState(), q1);
	}

	@Test
	public void testGetStates() {
		assertEquals(dfa.getStates(), states);
	}

	@Test
	public void testGetAcceptingStates() {
		assertEquals(dfa.getAcceptingStates(), acceptingStates);
	}

	@Test
	public void testGetAlphabet() {
		assertEquals(dfa.getAlphabet(), alphabet);
	}

	@Test
	public void testSetStates() {
		Set<State> newStates = new TreeSet<State>();
		newStates.add(q0);
		newStates.add(q1);
		
		dfa.setStates(newStates);
		
		assertEquals(dfa.getStates(), newStates);
	}

	@Test
	public void testSetAcceptingStates() {
		Set<State> newAcceptingStates = new TreeSet<State>();
		newAcceptingStates.add(q0);
		newAcceptingStates.add(q1);
		
		dfa.setAcceptingStates(newAcceptingStates);
		
		assertEquals(dfa.getAcceptingStates(), newAcceptingStates);
	}

	@Test
	public void testSetAlphabet() {
		char[] newAlphabet = {'a','b'};
		
		dfa.setAlphabet(newAlphabet);

		assertEquals(dfa.getAlphabet(), newAlphabet);
	}

	@Test
	public void testIsStateIdAvailableFalse() {
		assertTrue(!dfa.isStateIdAvailable("q0"));
	}
	
	@Test
	public void testIsStateIdAvailableTrue() {
		assertTrue(dfa.isStateIdAvailable("q8"));
	}

	@Test
	public void testProcess0() {
		char[] input = {'0', '0', '0'};
		
		State s = dfa.process(input);
		
		assertEquals(s, q3);
	}
	
	@Test
	public void testProcess1() {
		char[] input = {'1', '1', '1'};
		
		State s = dfa.process(input);
		
		assertEquals(s, q2);
	}

	@Test
	public void testChangeLink() {
		char[] input = {'0'};
		
		dfa.changeLink(q0, '0', q3);
		
		State s = dfa.process(input);
		
		assertEquals(s, q3);
	}

	@Test
	public void testIsAcceptingStateFalse() {
		assertTrue(!dfa.isAcceptingState(q0));
	}
	
	@Test
	public void testIsAcceptingStateTrue() {
		assertTrue(dfa.isAcceptingState(q4));
	}

	@Test
	public void testMinimizeAutomaton() {
		dfa.changeLink(q3, '0', q3);
		dfa.changeLink(q0, '1', q0);
		
		dfa.minimizeAutomaton();
		
		assertTrue(!dfa.getStates().contains(q4));
	}

	@Test
	public void testCloneBasics() {
		DeterministicFiniteAutomaton dfa2 = (DeterministicFiniteAutomaton)dfa.clone();
		char[] input = {'0', '0', '0'};
		State s = dfa2.process(input);
		assertEquals(s.getId(), "q3");
	}
	
	@Test
	public void testCloneDeepCopyTest1() {
		DeterministicFiniteAutomaton dfa2 = (DeterministicFiniteAutomaton)dfa.clone();
		char[] input = {'0'};
		
		State q0 = dfa2.getStateById("q0");
		State q3 = dfa2.getStateById("q3");
		
		dfa2.changeLink(q0, '0', q3);
		State s = dfa2.process(input);
		assertEquals(s.getId(), "q3");
	}
	
	@Test
	public void testCloneDeepCopyTest2() {
		DeterministicFiniteAutomaton dfa2 = (DeterministicFiniteAutomaton)dfa.clone();
		char[] input = {'0'};
		
		State q0 = dfa2.getStateById("q0");
		State q3 = dfa2.getStateById("q3");
		
		dfa2.changeLink(q0, '0', q3);
		State s = dfa.process(input);
		assertEquals(s.getId(), "q1");
	}

	@Test
	public void testGetState() {
		State search = new State("q0");
		
		State s = dfa.getState(search);
		
		assertTrue(s == q0);
	}

	@Test
	public void testGetStateById() {
		assertTrue(dfa.getStateById("q0") == q0);
	}

	@Test
	public void testGenerateDotString() {
		String dotString = "digraph G {\n"
		         + "q0 -> q4 [label=\"1\"]\n"
		         + "q0 -> q1 [label=\"0\"]\n"
		         +" q1 -> q0 [label=\"1\"]\n"
		         +" q1 -> q2 [label=\"0\"]\n"
		         +" q2 -> q1 [label=\"1\"]\n"
		         +" q2 -> q3 [label=\"0\"]\n"
		         +" q3 -> q2 [label=\"1\"]\n"
		         +" q3 -> q4 [label=\"0\"]\n"
		         +" q4 -> q3 [label=\"1\"]\n"
		         +" q4 -> q0 [label=\"0\"]\n"
		         +" q4 [peripheries=2]\n"
		         +" q0 [color=darkred]\n"
		         +" }";
		
		
		String genDotString = dfa.generateDotString();
		
		//compare ignoring spaces
		assertTrue(dotString.replaceAll("\\s+","").equalsIgnoreCase(genDotString.replaceAll("\\s+","")));
	}

}
