package semiring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.MaxPriorityQ;
import utility.PositionVector;

public class KBestSemiringSmart extends KBestSemiring {

	private int k;
	
	public KBestSemiringSmart(int k) {
		super(k);
		this.k = k;
	}
	
	/**
	 * Given a list of lists, each of size at most k, merge them and return a single list of size 
	 * at most k, containing the largest elements
	 */
	public List<Derivation> multiply(List<List<Derivation>> derivationsSet) {
		List<Derivation> kbest = new ArrayList<Derivation>();
		System.out.println(derivationsSet.size());
		PositionVector pVector = new PositionVector(-1, derivationsSet.size());
		kbest.add(SemiringUtils.getCandidateDerivation(derivationsSet, pVector, null));
		
		Map<Derivation, PositionVector> nextDerivations = new HashMap<Derivation, PositionVector>();
		MaxPriorityQ q = new MaxPriorityQ();
		
		while (kbest.size() < k ) {
			for (int i = 0; i < pVector.size(); i++) {
				PositionVector candidatePosition = 
						pVector.add(new PositionVector(i, pVector.size()));
				Derivation candidateDerivation = 
						SemiringUtils.getCandidateDerivation(derivationsSet, candidatePosition, null);
				
				if (candidateDerivation != null && !q.contains(candidateDerivation)) {
					nextDerivations.put(candidateDerivation, candidatePosition);
					q.insert(candidateDerivation);
				}				
			}
			
			if (q.size() == 0) {
				break;
			}
			Derivation best = q.extractMax();
			
			for (Derivation d : nextDerivations.keySet()) {
				if (d.getScore().equals(best.getScore())) {
					pVector = nextDerivations.get(d);
					break;
				}
			}
			kbest.add(best);
		}
		
		return kbest;
	}

}
