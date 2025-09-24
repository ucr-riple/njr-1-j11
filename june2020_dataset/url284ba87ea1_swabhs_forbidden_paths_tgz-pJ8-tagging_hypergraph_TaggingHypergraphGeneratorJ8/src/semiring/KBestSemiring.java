package semiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.PositionVector;

public class KBestSemiring implements Semiring<List<Derivation>> {
	
	private int k;
	
	public KBestSemiring(int k) {
		this.k = k;
	}

	/**
	 * Given a list of lists, each of size k, merge them and return a single list of size k,
	 * containing the k largest elements
	 * @param derivationsSet
	 */
	@Override
	public List<Derivation> multiply(List<List<Derivation>> derivationsSet) {	
		Map<String, Derivation> allCandidates = new HashMap<String, Derivation>();
		getPermutations(new PositionVector(-1, derivationsSet.size()), allCandidates, derivationsSet);
		List<Derivation> fullSet = new ArrayList<Derivation>();
		for (Derivation candidate : allCandidates.values()) {
			fullSet.add(candidate);
		}
		Collections.sort(fullSet);
		Collections.reverse(fullSet);
		if (k <= fullSet.size()) {
			return fullSet.subList(0, k);
		} else {
			return fullSet;
		}		
	}
		
	/**
	 * Recursive function to get all permutations that can result in candidate derivations
	 * @param pv
	 * @param allCandidates
	 * @param dSet
	 */
	void getPermutations(PositionVector pv, Map<String, Derivation> allCandidates, List<List<Derivation>> dSet) {
		if (checkForValidDerivationPosition(dSet, pv, allCandidates) == false)
			return;
		Derivation derivation = SemiringUtils.getCandidateDerivation(dSet, pv, null);
		if (derivation != null) {
			allCandidates.put(getPositionString(pv), derivation);
		}
		
		for (int i = 0; i < pv.size(); i++) {
			PositionVector newPv = pv.add(new PositionVector(i, pv.size()));
			getPermutations(newPv, allCandidates, dSet);
		}
	}
	
	/**
	 * 
	 * @param dSet
	 * @param pv
	 * @param derivationMap
	 * @return
	 */
	boolean checkForValidDerivationPosition(
			List<List<Derivation>> dSet, PositionVector pv, Map<String, Derivation> derivationMap) {
		if (derivationMap.containsKey(getPositionString(pv)))
			return false;
		int i = 0;
		for (List<Derivation> derivations : dSet) {
			if (pv.get(i) >= derivations.size())
				return false;
			++i;
		}
		return true;
	}
	
	/**
	 * 
	 * @param pv
	 * @return
	 */
	String getPositionString(PositionVector pv) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pv.size(); ++i)
			builder.append(pv.get(i));
		return builder.toString();
	}
	
	/**
	 * Given two lists of size k each, combines them to return a single list containing k largest
	 * elements
	 */
	@Override
	public List<Derivation> add(List<Derivation> derivations1,	List<Derivation> derivations2) {
		
		int it1 = 0;
		int it2 = 0;
		
		List<Derivation> kbest = new ArrayList<Derivation>();
		while (kbest.size() < k && it1 < derivations1.size() && it2 < derivations2.size()) {
			if (derivations1.get(it1).compareTo(derivations2.get(it2)) >= 0) {
				kbest.add(derivations1.get(it1));
				++it1;
			} else if ((it2 < derivations2.size()) && 
					(derivations2.get(it2).compareTo(derivations1.get(it1)) > 0)) {
				kbest.add(derivations2.get(it2));
				++it2; 
			} else {
				kbest.add(derivations1.get(it1));
				++it1; 
				if (kbest.size() < k) {
					kbest.add(derivations2.get(it2));
					++it2;
				}			
			}
		}
		if (kbest.size() < k) {
			if (it1 == derivations1.size()) {
				kbest.addAll(derivations2.subList(it2, derivations2.size()));
			} else {
				kbest.addAll(derivations1.subList(it1, derivations1.size()));
			}
		}
		if (kbest.size() > k) {
			kbest.removeAll(kbest.subList(k, kbest.size()));
		}
		return kbest;
	}

}
