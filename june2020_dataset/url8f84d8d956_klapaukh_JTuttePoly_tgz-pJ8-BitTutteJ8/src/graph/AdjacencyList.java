package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AdjacencyList  {
	private int numEdges;
	private List<Integer> vertices;
	private int domainSize;
	private List<Map<Integer, Integer>> edges;
	private int numMultiEdges;

	public AdjacencyList(int n) {
		edges = new ArrayList<Map<Integer, Integer>>(n);
		for (int i = 0; i < n; i++) {
			edges.add(new HashMap<Integer, Integer>());
		}
		numEdges = 0;
		numMultiEdges = 0;
		domainSize = n;
		vertices = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			vertices.add(i);
		}
	}

	public AdjacencyList(AdjacencyList g) {
		this.edges = new ArrayList<Map<Integer, Integer>>(g.edges.size());
		for (Map<Integer, Integer> m : g.edges) {
			this.edges.add(new HashMap<Integer, Integer>(m));
		}
		numEdges = g.numEdges;
		numMultiEdges = g.numMultiEdges;
		domainSize = g.domainSize;
		vertices = new ArrayList<Integer>(g.vertices);
	}

	public int domainSize() {
		return domainSize;
	}

	public Iterable<Integer> vertices() {
		return vertices;
	}

	public Iterable<Map.Entry<Integer, Integer>> edges(int v) {
		return edges.get(v).entrySet();
	}

	public boolean equals(Object o){
		if(o instanceof AdjacencyList){
			return this.equals((AdjacencyList)o);
		}
		return false;
	}
	
	public boolean equals(AdjacencyList a){
		if(a.domainSize != this.domainSize){
			return false;
		}
		if(a.numEdges != this.numEdges){
			return false;
		}
		if(a.numMultiEdges != this.numMultiEdges){
			return false;
		}
		if(a.vertices.size() != this.vertices.size()){
			return false;
		}
		if(a.edges.size() != this.edges.size()){
			return false;
		}
		
		for(int i = 0; i< this.edges.size();i++){
			Map<Integer,Integer> edgethis = this.edges.get(i); //Edges from i
			Map<Integer, Integer> edgeother = a.edges.get(i); 
			if(edgethis.size() != edgeother.size()){
				return false;
			}
			for(int to =0; to < edges.size();to++){
				Integer thisnum = edgethis.get(to);
				Integer othernum = edgeother.get(to);
				if(thisnum != othernum){
					return false;
				}
			}
		}
		return true;
	}
	
	
	public int numVertices() {
		return vertices.size();
	}

	public int numEdges() {
		return numEdges;
	}

	public int numUnderlyingEdges() {
		return numEdges - numMultiEdges;
	}

	public int numEdges(int vertex) {
		int count = 0;
		for (int i : edges.get(vertex).values()) {
			count += i;
		}
		return count;
	}

	public int numUnderlyingEdges(int vertex) {
		return edges.get(vertex).size();
	}

	public int numEdges(int from, int to) {
		Map<Integer, Integer> fset = edges.get(from);
		Integer i = fset.get(to);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	public int numMultiedges() {
		return numMultiEdges;
	}

	public boolean isMultiGraph() {
		return numMultiEdges > 0;
	}

	// there is no add vertex!
	public void clear(int v) {
		// Now, clear all edges involving v

		Map<Integer, Integer> vset = edges.get(v);
		for (Map.Entry<Integer, Integer> entry : vset.entrySet()) {
			int k = entry.getValue();
			numMultiEdges -= (k - 1);
			numEdges -= k;
			if (entry.getKey() != v) {
				edges.get(entry.getKey()).remove(v);
			}
		}
		edges.set(v, new HashMap<Integer, Integer>()); // save memory
	}


	public void remove(int v) {
		vertices.remove((Integer) v);
		clear(v);
	}

	public boolean addEdge(int from, int to, int c) {
		numEdges += c;

		// the following is a hack to check
		// whether the edge we're inserting
		// is already in the graph or not
		Map<Integer, Integer> tos = edges.get(to);
		Integer i = tos.get(from);
		if (i != null) {
			// edge already present so another multi-edge!
			numMultiEdges += c;
			tos.put(from, i + c);
			// don't want to increment same edge twice!
			if (from != to) {
				tos = edges.get(from);
				i = edges.get(from).get(to);
				tos.put(to, i + c);
			}
			return true;
		} else {
			// completely new edge!
			numMultiEdges += (c - 1);
			tos.put(from, c);
			// self-loops only get one mention in the edge set
			if (from != to) {
				edges.get(from).put(to, c);
			}
			return false;
		}
	}

	public boolean addEdge(int from, int to) {
		return addEdge(from, to, 1);
	}

	public boolean removeEdge(int from, int to, int c) {
		Map<Integer, Integer> fset = edges.get(from); // optimisation
		Integer i = fset.get(to);
		if (i != null) {
			if (i > c) {
				// this is a multi-edge, so decrement count.
				numMultiEdges -= c;
				numEdges -= c;
				fset.put(to, i - c);
				if (from != to) {
					i = edges.get(to).get(from);
					edges.get(to).put(from, i - c);
				}
			} else {
				// clear our ALL edges
				numEdges -= i;
				numMultiEdges -= (i - 1);
				fset.remove(to);
				if (from != to) {
					edges.get(to).remove(from);
				}
			}
			return true;
		}
		return false;
	}

	public int removeAllEdges(int from, int to) {
		// remove all edges "from--to"
		int r = 0;
		Map<Integer, Integer> fset = edges.get(from); // optimisation
		Integer i = fset.get(to);
		if (i != null) {
			r = i;
			numEdges -= r;
			numMultiEdges -= (r - 1);
			fset.remove(to);
			if (from != to) {
				edges.get(to).remove(from);
			}
		}

		return r;
	}

	public boolean removeEdge(int from, int to) {
		return removeEdge(from, to, 1);
	}

	public void remove(AdjacencyList g) {
		boolean done = false;
		for (Integer i : g.vertices) {
			done = false;
			out: while (!done) {
				for (Map.Entry<Integer, Integer> j : g.edges.get(i).entrySet()) {
					if (i >= j.getKey()) {
						if (removeEdge(i, j.getKey(), j.getValue())) {
							continue out;
						}
					}
				}
				done = true;
			}
		}
	}

	// Ok, this implementation is seriously inefficient!
	// could use an indirection trick here as one solution?
	//
	// POST: vertex 'from' remains, whilst vertex 'to' is removed
	void contractEdge(int from, int to) {
		if (from == to) {
			throw new RuntimeException("cannot contract a loop!");
		}
		for (Map.Entry<Integer, Integer> i : edges.get(to).entrySet()) {
			if (i.getKey() == to) {
				// is self loop
				addEdge(from, from, i.getValue());
			} else {
				addEdge(from, i.getKey(), i.getValue());
			}
		}
		removeEdge(from, from, 1);
		remove(to);
	}



	public String toString() {
		StringBuilder ss = new StringBuilder();
		for (int i : vertices) {
			for (Map.Entry<Integer, Integer> e : edges.get(i).entrySet()) {
				ss = ss.append(i).append(" -> ").append(e.getKey()).append('\n');
			}
		}

		return ss.toString();
	}
}
