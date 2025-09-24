package ch.zhaw.regularLanguages.evolution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.evolution.candidates.EvolutionCandidate;
import ch.zhaw.regularLanguages.evolution.candidates.EvolutionCandidateWithProblemSet;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.helpers.PublicCloneable;

public class EAWithEvolvingLocalProblemSets<E extends EvolutionCandidateWithProblemSet, R> implements EvolutionaryAlgorithm<E>{
	private List<E> candidates;
	
	private E winner;
	
	private R reference;
	
	private int maxFitness;
	private int maxC;
	
	
	public EAWithEvolvingLocalProblemSets(List<E> candidates, R reference, int noProblems) {
		this.candidates = candidates;
		this.reference = reference;
		this.maxFitness = noProblems;
	}
	
	public E getWinner(){
		return winner;
	}
	
	public int getMaxC(){
		return maxC;
	}
		
	public List<E> getCandidates(){
		return candidates;
	}

	@Override
	public long startEvolution(long cycleLimit) {
		long cycle = 0;
		boolean finalForm = false;
		List <E>newList = null;
		
		long countEq = 0;
		long countNbig = 0;
		long countIbig = 0;
		
		A : while(cycle < cycleLimit && finalForm == false){
			if(newList == null){
				newList = new LinkedList<E>();
			}else{
				newList.clear();
			}
			
			for(int i = 0;i < candidates.size();i+=2){
				int n = (i <= 0 ? candidates.size()-1 : i-1);
				
				int fitnessI = candidates.get(i).fitness(candidates.get(n).getProblemSet());
				int fitnessN = candidates.get(n).fitness(candidates.get(i).getProblemSet());
				
				if(fitnessI == maxFitness){
					if(candidates.get(i).checkValidity(reference)){
						winner = candidates.get(i);
						finalForm = true;
						break A;
					}
				}
				if(fitnessN == maxFitness){
					if(candidates.get(n).checkValidity(reference)){
						winner = candidates.get(n);
						finalForm = true;
						break A;
					}
				}
				
				if(fitnessI > maxC){
					maxC = fitnessI;
				}
				
				if(fitnessN > maxC){
					maxC = fitnessN;
				}
				
				
				int x = fitnessI - fitnessN;
				if(x == 0){
					countEq++;
					//candidate n = candidate i
					if(Math.random() < 0.5){
						newList.add(candidates.get(i));
						newList.add((E)candidates.get(i).cloneWithMutation());
					}else{
						newList.add(candidates.get(n));
						newList.add((E)candidates.get(n).cloneWithMutation());						
					}
				}else if(x < 0){
					countNbig++;
					//candidate n > candidate i
					newList.add(candidates.get(n));
					newList.add((E)candidates.get(n).cloneWithMutation());
				}else if(x > 0){
					countIbig++;
					//candidate i > candidate n
					newList.add(candidates.get(i));
					newList.add((E)candidates.get(i).cloneWithMutation());	
				}
			}
			
			candidates.clear();
			candidates.addAll(newList);
			
			Collections.shuffle(candidates);
			
			cycle++;
		}
		
		return cycle;
	}
}