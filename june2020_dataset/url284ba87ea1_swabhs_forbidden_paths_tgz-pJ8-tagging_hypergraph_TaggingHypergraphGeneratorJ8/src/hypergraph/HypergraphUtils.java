package hypergraph;

import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class HypergraphUtils {
	
	/**
	 * Runs in O(|V|+|E|).
	 * @return
	 */
	static Map<Integer, List<Hyperedge>> generateOutgoingMap(Hypergraph h) {		
		Map<Integer, List<Hyperedge>> outMap = new HashMap<Integer, List<Hyperedge>>();
		Map<Integer, Hyperedge> eMap = getEdgesMap(h);
		for (Vertex v : h.getVerticesList()) {
			List<Hyperedge> outgoing = new ArrayList<Hyperedge>();
			for (Integer e : v.getOutEdgeList()) {
				outgoing.add(eMap.get(e));
			}
			outMap.put(v.getId(), outgoing);
		}
		/* Old Implementation
		for (Vertex v : h.getVerticesList()) {
			outMap.put(v.getId(), new ArrayList<Hyperedge>());
		}
		
		for(Hyperedge e: h.getEdgesList()) {			
			for (Integer childId : e.getChildrenIdsList()) {
				List<Hyperedge> outgoing = outMap.get(childId);
				outgoing.add(e);
				outMap.put(childId, outgoing);
			}			
		}*/
		return outMap;
	}
	
	/** 
	 * Constructs a map between each vertex and its incoming hyperedges.
	 * Source vertices map to an empty list.
	 */
	public static Map<Integer, List<Hyperedge>> generateIncomingMap(Hypergraph h) {
		Map<Integer, List<Hyperedge>> inMap = new HashMap<Integer, List<Hyperedge>>();
				
		Map<Integer, Hyperedge> edgesMap = getEdgesMap(h);
		
		for (Vertex v : h.getVerticesList()) {
			List<Hyperedge> inedges = new ArrayList<Hyperedge>();
			for (Integer edge : v.getInEdgeList()) {
				inedges.add(edgesMap.get(edge));
			}
			inMap.put(v.getId(), inedges);			
		}
		
		/* Old Implementation
		 * for(Hyperedge e: h.getEdgesList()) {
			List<Hyperedge> incoming = inMap.get(e.getParentId());
			incoming.add(e);
			inMap.put(e.getParentId(), incoming);
		}*/
		return inMap;
	}

	/** Get the list of ids for the vertices which are not parents for any hyperedge */
	public static List<Integer> getTerminals(Hypergraph h) {
		List<Integer> terminals = new ArrayList<Integer>();
		
		for (Vertex v : h.getVerticesList()) {
			if (v.getInEdgeList().size() == 0)
				terminals.add(v.getId());
		}
		/* Old implementation
		// Assuming all the vertices are terminals
		for (Vertex v : h.getVerticesList()) {
			terminals.add(v.getId());
		}
		
		for (Hyperedge e : h.getEdgesList()) {
			int index = terminals.indexOf(e.getParentId());
			if (index != -1) {
				terminals.remove(index);
			}
		}*/
		return terminals;
	}
	
	/**
	 * Topologically sorts all vertices in a hypergraph given a list of terminal vertices
	 * Runs in O(|V|+|E|).
	 */
	public static List<Integer> toposort(Hypergraph h) {
		List<Integer> sorted = new ArrayList<Integer>();
		
		List<Integer> terminals = getTerminals(h);
		Collections.reverse(terminals);
		Stack<Integer> toVisit = new Stack<Integer>();
		toVisit.addAll(terminals);
		
		List<Integer> seenEdges = new ArrayList<Integer>(h.getEdgesCount());
		Map<Integer, List<Hyperedge>> outMap = generateOutgoingMap(h);
		Map<Integer, List<Hyperedge>> inMap = generateIncomingMap(h);
		
		while (toVisit.empty() == false) {
			int vert = toVisit.pop();
			sorted.add(vert);
			for (Hyperedge outEdge : outMap.get(vert)) {
				if (sorted.containsAll(outEdge.getChildrenIdsList())) {
					seenEdges.add(outEdge.getId());
				}
				int nextTarget = outEdge.getParentId();
				boolean validNextTarget = true;
				for (Hyperedge inEdge : inMap.get(nextTarget)) {
					if (seenEdges.contains(inEdge.getId()) == false) {
						validNextTarget = false;
						break;
					}
				}
				if (validNextTarget) {
					toVisit.push(nextTarget);
				}
			}
		}
		return sorted;
	}
	
	
	/** Displays the 1-best parse */
	public static String renderResult(List<Hyperedge> edges, Hypergraph h) {
		StringBuilder builder = new StringBuilder();
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (Vertex v : h.getVerticesList()) {
			names.put(v.getId(), v.getName());
		}
		Collections.reverse(edges);
		for (Hyperedge e : edges) {
			if (e != null)
				builder.append(names.get(e.getParentId()));
				builder.append(" ");
		}
		return builder.toString();
	}
	
	/** Displays the hypergraph, listing each hyperedge in a line */
	public static void renderHypergraph(Hypergraph h) {
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (Vertex v : h.getVerticesList()) {
			names.put(v.getId(), v.getName());
		}
		
		for (Hyperedge edges : h.getEdgesList()) {
			System.out.print("Edge: " + edges.getId() + "\t");
			System.out.print("Parent: " + names.get(edges.getParentId()) + "\t");
			System.out.print("Children: " );
			for (Integer v : edges.getChildrenIdsList()) {
				System.out.print(names.get(v) + " ");
			}
			System.out.println();
		}
	}
	
	/** Gets a map between the vertex Id and the vertex */
	public static Map<Integer, Vertex> getVerticesMap(Hypergraph h) {
		Map<Integer, Vertex> vMap = new HashMap<Integer, Vertex>();
		for (Vertex vertex : h.getVerticesList()) {
			vMap.put(vertex.getId(), vertex);
		}
		return vMap;
	}
	
	/** Gets a map between the edge Id and the edge */
	public static Map<Integer, Hyperedge> getEdgesMap(Hypergraph h) {
		Map<Integer, Hyperedge> eMap = new HashMap<Integer, Hyperedge>();
		for (Hyperedge edge : h.getEdgesList()) {
			eMap.put(edge.getId(), edge);
		}
		return eMap;
	}
}
