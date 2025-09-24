package ch.zhaw.regularLanguages.evolution;

import java.util.List;

import ch.zhaw.regularLanguages.evolution.candidates.EvolutionCandidate;

public class RandomSearch<E extends EvolutionCandidate, R> implements EvolutionaryAlgorithm<E>{
	List<E> candidates;
	R reference;
	
	E winner;
	
	
	public RandomSearch(List<E> candidates, R reference){
		this.candidates = candidates;
		this.reference = reference;
	}
	
	
	@Override
	public long startEvolution(long cycleLimit) {
		long cycle = 0;
		
		A: while(cycle < cycleLimit){
			
			for(E cand : candidates){
				if(cand.checkValidity(reference)){
					winner = cand;
					break A;
				}
				
				cand.setObj(cand.initObj()); //Create new random automaton
			}
			
			cycle++;
		}
				
		return cycle;
	}

	@Override
	public E getWinner() {
		return winner;
	}

	@Override
	public List<E> getCandidates() {
		return candidates;
	}

}
