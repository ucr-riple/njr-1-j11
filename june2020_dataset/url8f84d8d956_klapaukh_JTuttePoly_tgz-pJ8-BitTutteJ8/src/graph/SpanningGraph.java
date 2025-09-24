package graph;

import java.util.List;
import java.util.Map;

import util.Triple;

public class SpanningGraph {
	private AdjacencyList graph;
	private int nartics;
	private int ncomponents;

	public SpanningGraph(int i) {
		this(i, false);
	}

	public SpanningGraph(int i, boolean bfs) {
		graph = new AdjacencyList(i);
		nartics = 0;
		ncomponents = 0;
	}

	public SpanningGraph(AdjacencyList g) {
		this(g, false);
	}

	public SpanningGraph(AdjacencyList g, boolean bfs) {
		graph = new AdjacencyList(g);
		nartics = 0;
		ncomponents = 0;
		checkBiConnectivity();
	}

	public SpanningGraph(SpanningGraph g) {
		this(g.graph);
	}

	public Iterable<Integer> vertices() {
		return graph.vertices();
	}

	public Iterable<Map.Entry<Integer, Integer>> edges(int v) {
		return graph.edges(v);
	}

	public int domain_size() {
		return graph.domainSize();
	}

	public int num_vertices() {
		return graph.numVertices();
	}

	public int numEdges() {
		return graph.numEdges();
	}

	public int numEdges(int vertex) {
		return graph.numEdges(vertex);
	}

	public int numEdges(int v1, int v2) {
		return graph.numEdges(v1, v2);
	}

	public int numUnderlying_edges() {
		return graph.numUnderlyingEdges();
	}

	public int numUnderlying_edges(int vertex) {
		return graph.numUnderlyingEdges(vertex);
	}

	public int numMultiedges() {
		return graph.numMultiedges();
	}

	public int numComponents() {
		return ncomponents;
	}

	public boolean isConnected() {
		return ncomponents == 1;
	}

	public boolean isBiconnected() {
		return ncomponents == 1 && nartics == 1;
	}

	public boolean isTree() {
		return graph.numEdges() < graph.numVertices();
	}

	public boolean isMultitree() {
		return graph.numUnderlyingEdges() < graph.numVertices();
	}

	public boolean isMulticycle() {
		return nartics == 1 && graph.numUnderlyingEdges() == graph.numVertices();
	}

	public boolean clear(int v) {
		checkBiConnectivity();
		graph.clear(v);
		return true;
	}

	public void remove(int vertex) {
		graph.remove(vertex);
		checkBiConnectivity();
	}

	public void addEdge(int from, int to) {
		addEdge(from, to, 1);
	}

	public void addEdge(int from, int to, int count) {
		graph.addEdge(from, to, count);
		checkBiConnectivity();
	}

	public int removeAllEdges(int from, int to) {
		int r = graph.removeAllEdges(from, to);
		if (r > 0 && from != to) {
			checkBiConnectivity();
		}
		return r;
	}

	public boolean removeLine(List<Triple<Integer, Integer, Integer>> line) {
		if (line.size() == 1) {
			return removeEdge(line.get(0));
		}
		// now, remove all internal vertices
		for (int i = 0; i != line.size() - 1; ++i) {
			graph.remove(line.get(i).second);
		}
		checkBiConnectivity();
		return true;
	}

	public boolean removeEdge(int from, int to) {
		return removeEdge(from, to, 1);
	}

	public boolean removeEdge(int from, int to, int c) {
		if (graph.removeEdge(from, to, c)) {
			if (from != to) {
				// by removing an edge, we may have disconnected the
				// graph ...
				checkBiConnectivity();
			}
			return true;
		}
		return false;
	}

	public boolean removeEdge(Triple<Integer, Integer, Integer> e) {
		if (graph.removeEdge(e.first, e.second, e.third)) {
			if (e.first != e.second) {
				// by removing an edge, we may have disconnected the
				// graph ...
				checkBiConnectivity();
			}
			return true;
		}
		return false;
	}

	public void contractEdge(Triple<Integer, Integer, Integer> edge) {
		graph.removeEdge(edge.first, edge.second, edge.third);
		graph.contractEdge(edge.first, edge.second);
		checkBiConnectivity();
	}


	public void contractLine(List<Triple<Integer, Integer, Integer>> line) {
		if (line.size() == 1) {
			graph.removeEdge(line.get(0).first, line.get(0).second, line.get(0).third);
		} else {
			// now, remove all internal vertices
			for (int i = 0; i != line.size() - 1; ++i) {
				graph.remove(line.get(i).second);
			}
		}
		graph.contractEdge(line.get(0).first, line.get(line.size() - 1).second);
		checkBiConnectivity();
	}


	static BCDat datae = new BCDat();

	public void extractBiconnectedComponents(List<SpanningGraph> bcs) { // was retree
		// Now, we traverse the entire graph and extract any and all biconnected components

		datae.reset(graph.domainSize());

		// now, check for connectedness
		for (int i : graph.vertices()) {
			if (!datae.visited.get(i)) {
				// dfs search to identify component roots
//				System.out.println("DEBUG --- Going in the loop");
				extract_biconnects(i, i, bcs, datae);
			}
		}
//		System.out.println("DEGUG --- " + bcs.size());
	}

	public void removeGraphs(List<SpanningGraph> graphs) {
		// finally, remove all edges present in the biconnects
		// how could this be optimised a little?
		for (int i = 0; i != graphs.size(); ++i) {
			graph.remove(graphs.get(i).graph);
		}
		// could remove any isolated vertices here,
		// but I don't think it's necessary for the tutte
		// algorithm!

		nartics = 0; // this is a tree by definition now!!!!!
		ncomponents = 99; // not sure how many there are ...
	}

	static BCDat datac = new BCDat();

	private void checkBiConnectivity() { // was retree
		// reset visited information

		datac.reset(graph.domainSize());

		nartics = 0;
		ncomponents = 1;
		// dfs search to identify component roots
		biconnect(graph.vertices().iterator().next(), graph.vertices().iterator().next(), datac);
		// now, check for connectedness
		for (int i : graph.vertices()) {
			if (!datac.visited.get(i)) {
				biconnect(i, i, datac);
				ncomponents++;
			}
		}
	}

	private void biconnect(int u, int v, BCDat data) {
		// traverse edge tail->head
		data.dfsnum.set(v, data.vindex);
		data.visited.set(v, true);
		data.lowlink.set(v, data.vindex++);
		// now, consider edges
		for (Map.Entry<Integer, Integer> i : graph.edges(v)) {
			int w = i.getKey();
			if (!data.visited.get(w)) {
				biconnect(v, w, data);
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.lowlink.get(w)));
				if (data.lowlink.get(w) == data.dfsnum.get(v)) {
					// v is an articulation point separating
					// the component containing w from others.
					nartics++;
				} else if (data.lowlink.get(w) > data.dfsnum.get(v)) {
					// v is not in an bicomp with w
					nartics += 2;
				}
			} else if (u != w && data.dfsnum.get(v) > data.dfsnum.get(w)) {
				// this is a real back edge ...
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.dfsnum.get(w)));
			}
		}
	}

	private void extract_biconnects(int u, int v, List<SpanningGraph> bcs, BCDat data) {
		// traverse edge tail->head
		data.dfsnum.set(v, data.vindex);
		data.visited.set(v, true);
		data.lowlink.set(v, data.vindex++);
		// now, consider edges
		for (Map.Entry<Integer, Integer> i : graph.edges(v)) {
			int w = i.getKey();
//			System.out.println("DEBUG --- First Inner loop " + w + " " + v);
			Triple<Integer, Integer, Integer> e = new Triple<Integer, Integer, Integer>(v, w, i.getValue());

			if (!data.visited.get(w)) {
//				System.out.println("DEBUG --- Second inner loop");
				data.cstack.add(e);
				extract_biconnects(v, w, bcs, data);
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.lowlink.get(w)));
				if (data.lowlink.get(w) == data.dfsnum.get(v)) {
//					System.out.println("DEBUG -- Adding to list");
					// v is an articulation point separating
					// the component containing w from others.
					bcs.add(extract_biconnect(e, data));
				} else if (data.lowlink.get(w) > data.dfsnum.get(v)) {
					// v is not in a biconnected component with w
					data.cstack.remove(data.cstack.size() - 1);
//					System.out.println("DEBUG --- Removing from cstack");
				}
			} else if (w != u && data.dfsnum.get(v) > data.dfsnum.get(w)) {
				// this is a back edge ...
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.dfsnum.get(w)));
				// which means we're in a biconnected component ...
				data.cstack.add(e);
			}
		}
	}

	
	
	private SpanningGraph extract_biconnect(Triple<Integer, Integer, Integer> e, BCDat data) {
		SpanningGraph g = new SpanningGraph(graph.domainSize());
		Triple<Integer, Integer, Integer> c = new Triple<Integer, Integer, Integer>(0, 0, 0);
		do {
			c = data.cstack.get(data.cstack.size() - 1);
			// in what follows, I use g.graph to avoid rechecking
			// biconnectivity every time...
			g.graph.addEdge(c.first, c.second, c.third);
			data.cstack.remove(data.cstack.size() - 1);
		} while (c != e);

		// finally, remove any dumb vertices!
		for (int i = 0; i != graph.domainSize(); ++i) {
			if (g.numEdges(i) == 0) {
				g.graph.remove(i);
			}
		}

		g.checkBiConnectivity();

		g.nartics = 1; // since this is a biconnected component!
		g.ncomponents = 1;

		return g;
	}
	
	public String toString(){
//		StringBuilder ss = new StringBuilder();
		return graph.toString();
	}
	
	
	
	public boolean equals(Object o){
		if( o instanceof SpanningGraph ){
			return this.equals((SpanningGraph)o);
		}
		return false;
	}
	
	
	public boolean equals(SpanningGraph g){
		if(g.nartics != this.nartics){
			return false;
		}
		if(g.ncomponents != this.ncomponents){
			return false;
		}
		return this.graph.equals(g.graph);
		
	}
}
