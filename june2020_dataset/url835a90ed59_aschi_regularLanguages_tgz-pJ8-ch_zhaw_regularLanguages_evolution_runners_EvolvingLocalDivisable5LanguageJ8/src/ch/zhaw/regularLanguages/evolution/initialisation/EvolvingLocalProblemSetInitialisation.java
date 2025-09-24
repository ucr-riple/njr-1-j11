package ch.zhaw.regularLanguages.evolution.initialisation;

import java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.dfa.RandomDeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.evolution.EAWithEvolvingLocalProblemSets;
import ch.zhaw.regularLanguages.evolution.candidates.DFAEvolutionCandidateWithProblemSet;
import ch.zhaw.regularLanguages.evolution.problems.EvolvingProblemSet;
import ch.zhaw.regularLanguages.language.BooleanWrapper;
import ch.zhaw.regularLanguages.language.CharArrayWrapper;
import ch.zhaw.regularLanguages.language.WordProblemGenerator;
import dk.brics.automaton.RegExp;

public class EvolvingLocalProblemSetInitialisation implements DFAEvolutionaryAlgorithmInitialisation<EAWithEvolvingLocalProblemSets<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>, dk.brics.automaton.Automaton>, DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>> {	
	private List<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>> candidates;
	private char[] alphabet;
	private String regexp;
	private int noProblems;
	private dk.brics.automaton.Automaton refrence;
	private WordProblemGenerator wpg;
	private EAWithEvolvingLocalProblemSets<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>, dk.brics.automaton.Automaton> ea;
	
	@Override
	public void initLanguage(char[] alphabet, int maxWordLength, String regexp) {
		this.alphabet = alphabet;
		this.regexp = regexp;
		this.refrence = new RegExp(regexp).toAutomaton();
		wpg = new WordProblemGenerator(alphabet, maxWordLength, regexp);
	}

	@Override
	public void initProblems(int noProblems){
		this.noProblems = noProblems; 
	}
	
	@Override
	public void initCandidates(int noCandidates) {
		if(alphabet != null && noProblems != 0){
			candidates = new LinkedList<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>>();
	    	for(int i = 0; i < noCandidates;i++){
	    		
	    		//(Class<AUTOMATON> classTypeDef, char[] alphabet, AUTOMATON obj, EvolvingProblemSet<CharArrayWrapper, BooleanWrapper> problemSet)
	    		candidates.add(new DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>(
	    				RandomDeterministicFiniteAutomaton.class, 
	    				alphabet, 
	    				new RandomDeterministicFiniteAutomaton(alphabet, 2), 
	    				new EvolvingProblemSet<CharArrayWrapper, BooleanWrapper>(wpg.generateProblemSet(noProblems, true), wpg))
	    		);
	    	}
    	}else{
    		throw new IllegalAccessError("Language or problem length not yet initialised!");
    	}
	}

	@Override
	public long startEvolution(long cycleLimit) {
		if(alphabet == null || regexp == null || wpg == null){
			throw new IllegalAccessError("Language not yet initialised!");
		}
		if(candidates == null){
			throw new IllegalAccessError("Candidates not yet initialised!");
		}
		
		ea = new EAWithEvolvingLocalProblemSets<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>, dk.brics.automaton.Automaton>(candidates, refrence, noProblems);
		return ea.startEvolution(cycleLimit);
	}

	@Override
	public List<DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton>> getCandidates() {
		// TODO Auto-generated method stub
		return candidates;
	}

	@Override
	public DFAEvolutionCandidateWithProblemSet<RandomDeterministicFiniteAutomaton> getWinner() {
		// TODO Auto-generated method stub
		return ea.getWinner();
	}
}
