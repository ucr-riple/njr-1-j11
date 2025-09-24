package ch.zhaw.regularLanguages.evolution.initialisation;

import java.util.List;


public interface EvolutionaryAlgorithmInitialisation <EAType, CandidateType>{
	
	/**
	 * Starts the evolutions
	 * @param noCandidates Number of candidates
	 * @param noProblems Number of problems
	 * @param cycleLimit Cycle limit to avoid endless looping if it doesn't converge as expected
	 * @return returns the number of cycles it took to find a valid solution
	 */
	public long startEvolution(long cycleLimit);
	public List<CandidateType> getCandidates();
	public CandidateType getWinner();
}