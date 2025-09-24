package ch.zhaw.regularLanguages.dfa.mutations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;

public class MutationRegister implements RandomMutation{
	private List<RandomMutation> mutList = new ArrayList<RandomMutation>();
	
	public MutationRegister() {
		mutList.add(new AddRandomStateMutation());
		mutList.add(new ChangeRandomLinkMutation());
		mutList.add(new RemoveRandomStateMutation());
		mutList.add(new RemoveRandomAcceptingStateMutation());
		mutList.add(new AddRandomAcceptingStateMutation());
	}

	@Override
	public boolean mutate(DeterministicFiniteAutomaton dfa) {
		Random rnd = new Random();
		int i = rnd.nextInt(mutList.size());
		//System.out.println("Mutation "+i);
		return mutList.get(i).mutate(dfa);
	}
}
