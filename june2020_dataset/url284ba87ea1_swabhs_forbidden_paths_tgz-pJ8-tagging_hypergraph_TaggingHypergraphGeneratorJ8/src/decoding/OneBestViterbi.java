package decoding;

import hypergraph.HypergraphUtils;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semiring.Derivation;
import semiring.OneBestSemiring;
import semiring.Semiring;

public class OneBestViterbi {

	Semiring<Derivation> semiring;
	
	public OneBestViterbi() {
		this.semiring = new OneBestSemiring();
	}

	/** 
	 * Initializes the weight of terminal nodes to 1.0 and the rest of the nodes to 0.0
	 * For every node, initializes the best possible hyperedge to reach it(backPointers) to null
	 */
	Map<Integer, Derivation> initialize(Hypergraph h) {
		Map<Integer, Derivation> derivationMap = new HashMap<Integer, Derivation>();
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		
		for (Vertex v : h.getVerticesList()) {
			Derivation d = null;
			if (terminalIds.contains(v.getId())) {
				// Exact definition for terminal vertices - no incoming edge, so null
				d = new Derivation(null, 1.0, null);
			} else {
				// For each non terminal vertex, this needs to be set to the 1 best edge to reach it
				d = new Derivation(null, 0.0, null);
			}			
			derivationMap.put(v.getId(), d);
		}
		return derivationMap;
	}
	
	/**
	 * Run Viterbi on a OneBestSemiring and get a list of derivations, the 1 best for each vertex
	 */
	public Derivation run(Hypergraph h) { 
		Map<Integer, List<Hyperedge>> inMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> topoSortedVertices = HypergraphUtils.toposort(h);
		
		Map<Integer, Derivation> derivationMap = initialize(h);
		
		for (Integer v: topoSortedVertices) {	
			List<Hyperedge> incomingEdges = inMap.get(v);
			Derivation dv = derivationMap.get(v);
			
			for (Hyperedge e : incomingEdges) {
				// Construct a list of all derivations from the children of an edge
				List<Derivation> childDerList = new ArrayList<Derivation>();
				for (Integer child : e.getChildrenIdsList()) {
					childDerList.add(derivationMap.get(child));
				}
				
				Derivation product = semiring.multiply(childDerList);
				product.setE(e);
				product.setScore(e.getWeight() * product.getScore());
				
				dv = semiring.add(dv, product);							
			}
			derivationMap.put(v, dv);
		}
		int rootVertex = h.getVerticesCount() - 1;
		return derivationMap.get(rootVertex);
	}
	
}
