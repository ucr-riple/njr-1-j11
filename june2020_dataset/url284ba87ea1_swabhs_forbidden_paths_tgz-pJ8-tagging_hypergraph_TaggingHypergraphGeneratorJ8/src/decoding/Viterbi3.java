package decoding;

import hypergraph.HypergraphUtils;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semiring.Derivation;
import semiring.SemiringUtils;
import utility.MaxPriorityQ;
import utility.PositionVector;

/**
 * Implementation of Algorithm 2 from the Chiang-Huang paper.
 * @author swabha
 *
 */
public class Viterbi3 {
	
	private int k;
	
	/** Saves a k sized list of derivations for each vertex */
	private Map<Integer, List<Derivation>> derivationsSet;
	
	public Viterbi3(int k) {
		super();
		this.k = k;
	}
		
	/** 
	 * Initializes the weight of terminal nodes to 1.0 and the rest of the nodes to 0.0
	 * For every node, initializes the best possible hyperedge to reach it(backPointers) to null
	 */
	Map<Integer, List<Derivation>> initialize(Hypergraph h) {
		derivationsSet = new HashMap<Integer, List<Derivation>>();
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		
		for (Integer terminal : terminalIds) {
			Derivation d = new Derivation(null, 1.0, null);
			List<Derivation> dList = new ArrayList<Derivation>();
			dList.add(d);
			derivationsSet.put(terminal, dList);			
		}
		return derivationsSet;
	}
	
	/** TODO(swabha): Return only the root derivation, not the entire map
	 * Run Viterbi to get a list of k best derivations for each vertex in hypergraph
	 */
	public Map<Integer, List<Derivation>> run(Hypergraph h) {
		Map<Integer, List<Hyperedge>> inMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> vertices = HypergraphUtils.toposort(h);
		initialize(h);
		
		for (Integer v: vertices) {	
			List<Hyperedge> edges = inMap.get(v);
			if (!derivationsSet.containsKey(v)) {
				derivationsSet.put(v, findKBestForVertex(edges));
			}
		}
		return derivationsSet;
	}
	
	/** merge + max */
	private List<Derivation> findKBestForVertex(List<Hyperedge> edges) {
		List<Derivation> kbest = new ArrayList<Derivation>();	
		MaxPriorityQ q = new MaxPriorityQ();
		
		/** Map an edge with the list of its child derivations */
		Map<Integer, List<List<Derivation>>> derivationMap = 
				new HashMap<Integer, List<List<Derivation>>>();
		/** Maps an edge with the next best subderivation candidates */
		Map<Derivation, PositionVector> positionMap = 
				new HashMap<Derivation, PositionVector>();
		
		
		// Fill in data structures and insert top derivations from each incoming hyperedge
		for (Hyperedge e: edges) {
			// Set derivationMap
			List<List<Derivation>> derivationsUnderEdge = new ArrayList<List<Derivation>>();
			for (Integer u : e.getChildrenIdsList()) {
				derivationsUnderEdge.add(derivationsSet.get(u));
			}
			derivationMap.put(e.getId(), derivationsUnderEdge);
			
			// Set positionMap
			PositionVector pVector = new PositionVector(-1, e.getChildrenIdsCount());
			Derivation candidate = 
					SemiringUtils.getCandidateDerivation(derivationsUnderEdge, pVector, e);
			positionMap.put(candidate, pVector);
			
			q.insert(candidate);
		}
		
		while (kbest.size() < (k - 1) && q.size() > 0) {
			// Extract the 1st best candidate
			Derivation best = q.extractMax();
			kbest.add(best);
			
			Hyperedge bestEdge = best.getE();
			queueNextBestCandidates(
					derivationMap.get(bestEdge.getId()), 
					positionMap.get(best), 
					positionMap, q, bestEdge);
		}
		
		// Insert k-th best candidate
		if (q.size() > 0) {
			kbest.add(q.extractMax());
		}
		return kbest;
	}
	
	/**
	 * Adds to the priority queue all neighboring candidates to the extracted best candidate
	 * @param fullSet
	 * @param pVector
	 * @param q
	 * @param edges
	 */
	void queueNextBestCandidates(
			List<List<Derivation>> fullSet, 
			PositionVector counters, 
			Map<Derivation, PositionVector> posOfDerivation,
			MaxPriorityQ q, 
			Hyperedge e) {
		
		for (int i = 0; i < counters.size(); i++) {
			PositionVector candidatePosition = 
					counters.add(new PositionVector(i, counters.size()));
			Derivation candidateDerivation = 
					SemiringUtils.getCandidateDerivation(fullSet, candidatePosition, e);
			
			if (candidateDerivation != null && !q.contains(candidateDerivation)) {
				
				posOfDerivation.put(candidateDerivation, candidatePosition);
				q.insert(candidateDerivation);
			}				
		}
	}
	
}
