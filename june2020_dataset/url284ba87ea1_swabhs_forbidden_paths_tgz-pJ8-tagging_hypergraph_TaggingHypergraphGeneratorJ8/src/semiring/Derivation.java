package semiring;

import java.util.List;

import hypergraph.HypergraphProto.Hyperedge;

/**
 * Data structure to be associated with a vertex in the hypergraph.
 * Saves the best incoming hyperedge to reach the vertex, along with its score and the set of
 * derivations, the combination of which lead to it. Implements the Comparable interface to 
 * respect total ordering.
 * @author swabha
 */
public class Derivation implements Comparable<Derivation> {

	private Hyperedge e;
	
	private Double score;
	
	private List<Derivation> subDerivations;
	
	public Derivation(Hyperedge e, Double score, List<Derivation> subDerivations) {
		this.e = e;
		this.score = score;
		this.subDerivations = subDerivations;
	}

	public Hyperedge getE() {
		return e;
	}

	public void setE(Hyperedge e) {
		this.e = e;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public List<Derivation> getSubDerivations() {
		return subDerivations;
	}

	public void setSubDerivations(List<Derivation> subDerivations) {
		this.subDerivations = subDerivations;
	}

	@Override
	public int compareTo(Derivation another) {
		if (score > another.getScore()) {
			return 1;
		} else if (score == another.getScore()) {
			return 0;
		} else {
			return -1;
		}		
	}

}
