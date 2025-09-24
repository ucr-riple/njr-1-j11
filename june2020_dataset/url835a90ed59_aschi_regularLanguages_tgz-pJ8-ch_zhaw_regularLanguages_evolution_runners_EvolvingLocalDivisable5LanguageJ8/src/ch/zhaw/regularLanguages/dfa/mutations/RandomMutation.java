package ch.zhaw.regularLanguages.dfa.mutations;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;

public interface RandomMutation {
	public boolean mutate(DeterministicFiniteAutomaton dfa);
}
