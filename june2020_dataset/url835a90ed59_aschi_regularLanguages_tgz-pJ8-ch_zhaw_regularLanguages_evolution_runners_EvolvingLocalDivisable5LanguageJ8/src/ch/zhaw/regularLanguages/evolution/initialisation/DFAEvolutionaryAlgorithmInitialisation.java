package ch.zhaw.regularLanguages.evolution.initialisation;

public interface DFAEvolutionaryAlgorithmInitialisation<EAType, CandidateType> extends	EvolutionaryAlgorithmInitialisation<EAType, CandidateType> {
	public void initLanguage(char[] alphabet, int maxWordLength, String regexp);
	public void initProblems(int noProblems);
	public void initCandidates(int noCandidates);
}
