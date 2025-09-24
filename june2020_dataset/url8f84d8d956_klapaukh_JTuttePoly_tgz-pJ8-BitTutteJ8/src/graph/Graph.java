package graph;

import java.util.List;

import util.Isomorphism;
import util.Label;
import util.Pair;
import util.Triple;

public class Graph {

	private AdjacencyMatrix graph;
	private int nartics;
	private int ncomponents;
	private int[] label;

	public Graph(int i) {
		graph = new AdjacencyMatrix(i);
		nartics = 0;
		ncomponents = 0;
	}

	public Graph(AdjacencyMatrix g) {
		graph = new AdjacencyMatrix(g);
		nartics = 0;
		ncomponents = 0;
		checkBiConnectivity();
	}

	public Graph(Graph g) {
		this(g.graph);
	}

	public void addEdge(int from, int to) {
		addEdge(from, to, 1);
	}

	public void addEdge(int from, int to, int count) {
		label = null;
		graph.addEdge(from, to, count);
		checkBiConnectivity();
	}

	public Iterable<Integer> vertices() {
		return graph.vertices();
	}

	public int removeAllEdges(int from, int to) {
		label = null;
		int r = graph.removeAllEdges(from, to);
		if (r > 0 && from != to) {
			checkBiConnectivity();
		}
		return r;
	}

	public int numEdges() {
		return graph.numEdges();
	}

	public boolean isMulticycle() {
		return nartics == 1 && graph.numUnderlyingEdges() == graph.numVertices();
	}

	public Iterable<Pair<Integer, Integer>> edges(int v) {
		return graph.edges(v);
	}

	public void contractEdge(Triple<Integer, Integer, Integer> edge) {
		label = null;
		graph.removeEdge(edge.first, edge.second, edge.third);
		graph.contractEdge(edge.first, edge.second);
		checkBiConnectivity();
	}

	public boolean removeEdge(Triple<Integer, Integer, Integer> e) {
		label = null;
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

	public boolean isMultitree() {
		return graph.numUnderlyingEdges() < graph.numVertices();
	}

	public void removeGraphs(List<Graph> graphs) {
		label = null;
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

	public boolean isBiconnected() {
		return ncomponents == 1 && nartics == 1;
	}

	private BCDat datae = new BCDat();

	public void extractBiconnectedComponents(List<Graph> bcs) { // was retree
		// Now, we traverse the entire graph and extract any and all biconnected components

		datae.reset(graph.domainSize());

		// now, check for connectedness
		for (int i : graph.vertices()) {
			if (!datae.visited.get(i)) {
				// dfs search to identify component roots
				// System.out.println("DEBUG --- Going in the loop");
				extract_biconnects(i, i, bcs, datae);
			}
		}
		// System.out.println("DEGUG --- " + bcs.size());
	}

	private void extract_biconnects(int u, int v, List<Graph> bcs, BCDat data) {
		// traverse edge tail->head
		data.dfsnum.set(v, data.vindex);
		data.visited.set(v, true);
		data.lowlink.set(v, data.vindex++);
		// now, consider edges
		for (Pair<Integer, Integer> i : graph.edges(v)) {
			int w = i.first();
			// System.out.println("DEBUG --- First Inner loop " + w + " " + v);
			Triple<Integer, Integer, Integer> e = new Triple<Integer, Integer, Integer>(v, w, i.second());

			if (!data.visited.get(w)) {
				// System.out.println("DEBUG --- Second inner loop");
				data.cstack.add(e);
				extract_biconnects(v, w, bcs, data);
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.lowlink.get(w)));
				if (data.lowlink.get(w) == data.dfsnum.get(v)) {
					// System.out.println("DEBUG -- Adding to list");
					// v is an articulation point separating
					// the component containing w from others.
					bcs.add(extract_biconnect(e, data));
				} else if (data.lowlink.get(w) > data.dfsnum.get(v)) {
					// v is not in a biconnected component with w
					data.cstack.remove(data.cstack.size() - 1);
					// System.out.println("DEBUG --- Removing from cstack");
				}
			} else if (w != u && data.dfsnum.get(v) > data.dfsnum.get(w)) {
				// this is a back edge ...
				data.lowlink.set(v, Math.min(data.lowlink.get(v), data.dfsnum.get(w)));
				// which means we're in a biconnected component ...
				data.cstack.add(e);
			}
		}
	}

	private Graph extract_biconnect(Triple<Integer, Integer, Integer> e, BCDat data) {
		Graph g = new Graph(graph.domainSize());
		Triple<Integer, Integer, Integer> c = new Triple<Integer, Integer, Integer>(0, 0, 0);
		do {
			c = data.cstack.get(data.cstack.size() - 1);
			// in what follows, I use g.graph to avoid rechecking
			// biconnectivity every time...
			g.graph.addEdge(c.first, c.second, c.third);
			data.cstack.remove(data.cstack.size() - 1);
		} while (c != e);

		// finally, remove any dumb vertices!
		for (int i = 0; i < graph.domainSize(); ++i) {
			if (g.numEdges(i) == 0) {
				g.graph.clear(i);
			}
		}

		g.checkBiConnectivity();

		g.nartics = 1; // since this is a biconnected component!
		g.ncomponents = 1;

		return g;
	}

	private BCDat datac = new BCDat();

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
		for (Pair<Integer, Integer> i : graph.edges(v)) {
			int w = i.first();
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

	/**
	 * Remove the vertex and all associated edges
	 * 
	 * @param vertex
	 *            The vertex to remove
	 */
	public void clear(int vertex) {
		label = null;
		graph.clear(vertex);
	}

	/**
	 * Returns a pendant vertex
	 * 
	 * @return Pendant Vertex or -1
	 */
	public int pendant() {
		for (int i : vertices()) {
			if (numUnderlyingEdges(i) == 1) {
				return i;
			}
		}
		return -1;
	}

	public int numEdges(int vertex) {
		return graph.numEdges(vertex);
	}

	public int numUnderlyingEdges(int vertex) {
		return graph.numUnderlyingEdges(vertex);
	}

	public String toString() {
		return graph.toString();
	}

	public int numVertices() {
		return graph.numVertices();
	}

	public int[] label(Label l){
		return graph.label(l);
	}
	
	public int hashCode() {
		if(label == null){
			this.label = Isomorphism.canonicalLabel(this);
		}
		return graph.hashCode(label);
	}

	public boolean equals(Object o) {
		if (o instanceof Graph) {
			// return this.graph.equals(((Graph) o).graph);
			Graph g = (Graph) o;

			if (g.numEdges() != this.numEdges()) {
				return false;
			}
			if (g.graph.numMultiedges() != this.graph.numMultiedges()) {
				return false;
			}

			if (this.label == null) {
				this.label = Isomorphism.canonicalLabel(this);
			}
			if (g.label == null) {
				g.label = Isomorphism.canonicalLabel(g);
			}

			for (int i = 0; i < g.label.length; i++) {
						int myEdges = this.label[i];
						int gEdges = g.label[i];
						if (myEdges != gEdges) {
							return false;
						}
			}
			return true;
		}
		return false;
	}

	public int numUnderlyingEdges(int v, int i) {
		return graph.numEdges(v, i) > 0 ? 1 : 0;
	}
	
	public int domainSize(){
		return graph.domainSize();
	}

	public int numEdges(int v, int i) {
		return graph.numEdges(v, i);
	}
}
