package semiring;

import hypergraph.HypergraphProto.Hyperedge;

import java.util.ArrayList;
import java.util.List;

import utility.PositionVector;

public class SemiringUtils {
	
	/** */
	public static Derivation getCandidateDerivation(
			List<List<Derivation>> dSet, PositionVector pv, Hyperedge e) {
		double prod = 1.0;
		
		List<Derivation> subDerivations = new ArrayList<Derivation>();
		for (int i = 0; i < dSet.size(); ++i) {
			if (dSet.get(i).size() <= pv.get(i)) {
				return null;
			}
			//prod *= dSet.get(i).get(pv.get(i)).getScore();
			prod = Math.exp(Math.log(prod) + Math.log(dSet.get(i).get(pv.get(i)).getScore()));
			subDerivations.add(dSet.get(i).get(pv.get(i)));
		}
		
		Derivation d = new Derivation(null, prod, subDerivations);
		if (e!=null) {
			d.setE(e);
			//d.setScore(d.getScore() * e.getWeight());
			d.setScore(Math.exp(Math.log(d.getScore()) + Math.log(e.getWeight())));
		}
		return d;
	}

}
