package ch.zhaw.regularLanguages.evolution.initialisation;

import java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.dfa.RandomDeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.evolution.EAWithConsistentGlobalProblemSet;
import ch.zhaw.regularLanguages.evolution.RandomSearch;
import ch.zhaw.regularLanguages.evolution.candidates.DFAEvolutionCandidate;
import ch.zhaw.regularLanguages.language.BooleanWrapper;
import ch.zhaw.regularLanguages.language.CharArrayWrapper;
import ch.zhaw.regularLanguages.language.WordProblemGenerator;
import dk.brics.automaton.RegExp;

public class RandomSearchInitialisation implements DFAEvolutionaryAlgorithmInitialisation<EAWithConsistentGlobalProblemSet<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>, CharArrayWrapper, BooleanWrapper, dk.brics.automaton.Automaton>, DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>> {	
	private List<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>> candidates;
	private char[] alphabet;
	private String regexp;
	private dk.brics.automaton.Automaton refrence;
	private WordProblemGenerator wpg;
	private RandomSearch<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>, dk.brics.automaton.Automaton> ea;
	
	@Override
	public void initLanguage(char[] alphabet, int maxWordLength, String regexp) {
		this.alphabet = alphabet;
		this.regexp = regexp;
		this.refrence = new RegExp(regexp).toAutomaton();
	}

	@Override
	public void initProblems(int noProblems){
		//do nothing as we dont have no problems
	}
	
	@Override
	public void initCandidates(int noCandidates) {
		if(alphabet != null){
			candidates = new LinkedList<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>>();
	    	for(int i = 0; i < noCandidates;i++){
	    		candidates.add(new DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>(RandomDeterministicFiniteAutomaton.class, alphabet));
	    	}
    	}else{
    		throw new IllegalAccessError("Language not yet initialised!");
    	}
	}

	@Override
	public long startEvolution(long cycleLimit) {
		if(alphabet == null || regexp == null){
			throw new IllegalAccessError("Language not yet initialised!");
		}
		if(candidates == null){
			throw new IllegalAccessError("Candidates not yet initialised!");
		}
		
		ea = new RandomSearch<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>, dk.brics.automaton.Automaton>(candidates, refrence);
		return ea.startEvolution(cycleLimit);
	}

	@Override
	public List<DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton>> getCandidates() {
		return candidates;
	}

	@Override
	public DFAEvolutionCandidate<RandomDeterministicFiniteAutomaton> getWinner() {
		return ea.getWinner();
	}
}
