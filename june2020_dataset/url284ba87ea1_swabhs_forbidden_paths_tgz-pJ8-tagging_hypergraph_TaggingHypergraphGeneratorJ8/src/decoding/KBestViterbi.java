package decoding;

import hypergraph.HypergraphUtils;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semiring.Derivation;
import semiring.Semiring;

/**
 * Generic viterbi to run on algo0 and algo1
 * @author swabha
 *
 */
public class KBestViterbi {
	
	/** Dynamic programming state saving variables, one list for each vertex */
	private Map<Integer, List<Derivation>> derivationsSet;
	
	private Semiring<List<Derivation>> semiring;
	
	public KBestViterbi(Semiring<List<Derivation>> semiring) {
		this.semiring = semiring;
	}
	
	/** 
	 * Initializes the weight of terminals to 1.0.
	 * Also initializes the best possible hyperedge to reach the terminal(the backpointer) to null
	 */
	public Map<Integer, List<Derivation>> initialize(Hypergraph h) {
		derivationsSet = new HashMap<Integer, List<Derivation>>();
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		
		for (Integer terminal : terminalIds) {
			List<Derivation> dList = new ArrayList<Derivation>();
			dList.add(new Derivation(null, 1.0, null));
			derivationsSet.put(terminal, dList);
		}
		return derivationsSet;
	}
	
	/**
	 * Run Viterbi on a KBestSemiring and get a list of at most K derivation lists,
	 * the 1 best for each vertex
	 */
	public Map<Integer, List<Derivation>> run(Hypergraph h) {
		Map<Integer, List<Hyperedge>> inMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> vertices = HypergraphUtils.toposort(h);
		initialize(h);
				
		for (Integer v: vertices) {	
			List<Hyperedge> incomingEdges = inMap.get(v);
			List<Derivation> dList = null;
			if (!derivationsSet.containsKey(v)) {
				dList = new ArrayList<Derivation>();
			} else {
				dList = derivationsSet.get(v);
			}
			
			for (Hyperedge e : incomingEdges) {
				List<List<Derivation>> subDerivations = new ArrayList<List<Derivation>>();
				for (Integer child : e.getChildrenIdsList()) {
					subDerivations.add(derivationsSet.get(child));
				}
				
				List<Derivation> product = semiring.multiply(subDerivations);
				for (Derivation d : product) {
					d.setE(e);
					d.setScore(d.getScore() * e.getWeight());
				}
				dList = semiring.add(dList, product);				
			}
			derivationsSet.put(v, dList);
		}
		return derivationsSet;
	}
}
