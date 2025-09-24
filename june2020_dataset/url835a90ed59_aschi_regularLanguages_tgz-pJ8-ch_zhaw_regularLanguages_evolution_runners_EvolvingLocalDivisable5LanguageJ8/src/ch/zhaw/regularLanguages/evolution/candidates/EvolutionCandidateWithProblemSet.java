package ch.zhaw.regularLanguages.evolution.candidates;

import ch.zhaw.regularLanguages.evolution.problems.EvolvingProblemSet;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.helpers.PublicCloneable;

public abstract class EvolutionCandidateWithProblemSet<T, R, PSI extends PublicCloneable, PSO extends PublicCloneable> extends EvolutionCandidate<T, EvolvingProblemSet<PSI, PSO>, R>{
	private EvolvingProblemSet<PSI, PSO> problemSet;
	
	public EvolutionCandidateWithProblemSet(T obj, EvolvingProblemSet<PSI, PSO>problemSet){
		super();
		this.problemSet = problemSet;
		setObj(obj);		
	}
		
	public EvolutionCandidateWithProblemSet(Class<T> classTypeDef, EvolvingProblemSet<PSI, PSO> problemSet){
		super();
		setClassTypeDef(classTypeDef);
		this.problemSet = problemSet;
	}
	
	public EvolvingProblemSet<PSI, PSO> getProblemSet(){
		return problemSet;
	}
}