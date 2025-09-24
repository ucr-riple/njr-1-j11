package ch.zhaw.regularLanguages.evolution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.evolution.candidates.EvolutionCandidate;
import ch.zhaw.regularLanguages.evolution.problems.EvolvingProblemSet;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.helpers.ListExporter;
import ch.zhaw.regularLanguages.helpers.PublicCloneable;

public class EAWithEvolvingGlobalProblemSet<E extends EvolutionCandidate, PSI extends PublicCloneable, PSO extends PublicCloneable, R> implements EvolutionaryAlgorithm<E>{
	private List<E> candidates;
	
	private E winner;
	
	private R reference;
	
	private int maxC;
	
	
	private EvolvingProblemSet<PSI, PSO> problemSet;
	
	public EAWithEvolvingGlobalProblemSet(EvolvingProblemSet<PSI, PSO> problemSet, List<E> candidates, R reference) {
		this.problemSet = problemSet;	
		this.candidates = candidates;
		this.reference = reference;
	}
	
	public E getWinner(){
		return winner;
	}
	
	public int getMaxC(){
		return maxC;
	}
	
	public ProblemSet<PSI, PSO> getProblemSet(){
		return problemSet;
	}
		
	public List<E> getCandidates(){
		return candidates;
	}
	
	public int getProblemSetSize(){
		return problemSet.getProblemSet().size();
	}

	public long startEvolution(long cycleLimit) {
		long cycle = 0;
		boolean finalForm = false;
		List <E>newList = null;
		
		A : while(cycle < cycleLimit && finalForm == false){
			int maxFitness = problemSet.getProblems().size();
			
			//calculate fitness values
			for(E c : candidates){
				//c.checkViabilityAndResetIfNeeded();
				c.setFitness(c.fitness(problemSet));
			}
			Collections.sort(candidates);
			
			if(newList == null){
				newList = new LinkedList<E>();
			}else{
				newList.clear();
			}
			for(int i = 0;i < (candidates.size())/2;i++){
				if(candidates.get(i).getFitness() == maxFitness){
					//System.out.println("Winner candidate found..stresstesting it");
					if(candidates.get(i).checkValidity(reference)){
						winner = candidates.get(i);
						finalForm = true;
						break A;
					}
				}
				if(candidates.get(i).getFitness() > maxC){
					maxC = candidates.get(i).getFitness();
				}
				
				newList.add(candidates.get(i)); //add old object
				newList.add((E)candidates.get(i).cloneWithMutation()); //add mutated clone
			}
			
			//continue with new list
			candidates.clear();
			candidates.addAll(newList);
			
			//evolve problem set
			problemSet.evolve(0.5);
						
			cycle++;
		}
		return cycle;
	}
}