package selection;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

import util.Debug;
import util.Pair;
import util.Triple;

public class HeuristicCollection implements EdgeSelection{

	public int edge_selection_heuristic;

	public static final int RANDOM = 0;
	public static final int CUT = 1;
	public static final int VERTEX_ORDER = 2;
	public static final int MAXIMISE_SDEGREE = 3;
	public static final int MAXIMISE_MDEGREE = 4;
	public static final int MINIMISE_DEGREE = 5;
	public static final int MINIMISE_SDEGREE = 6;
	public static final int MINIMISE_MDEGREE = 7;
	public static final int MAXIMISE_DEGREE = 8;
	
	public HeuristicCollection(int heuristic){
		this.edge_selection_heuristic = heuristic;
	}

	public Triple<Integer, Integer, Integer> select_edge(Graph graph, boolean reduce_multiedges) {
		int best = 0;
		int V = graph.numVertices();
		int rcount = 0;
		int rtarget = 0;
		int heuristic = edge_selection_heuristic;
		Triple<Integer, Integer, Integer> r = new Triple<Integer, Integer, Integer>(-1, -1, -1);

		if (edge_selection_heuristic == RANDOM) {
			int nedges = graph.numEdges();
			rtarget = (int) (nedges * Math.random());
		} else if (edge_selection_heuristic == CUT) {
			for (int i : graph.vertices()) {
				int head = i;

				for (Pair<Integer, Integer> j : graph.edges(i)) {
					int tail = j.first();
					int count = j.second();
					int cost = Integer.MAX_VALUE;

					if (head < tail) { // to avoid duplicates
						// er, yes this is a tad ott.
						Graph g = new Graph(graph);
						g.removeAllEdges(head, tail);
						if (!g.isBiconnected()) {

							List<Graph> biconnects = new ArrayList<Graph>();
							g.extractBiconnectedComponents(biconnects);
							g.removeGraphs(biconnects);

							// now, actually do the computation
							cost = g.numVertices();
							for (Graph k : biconnects) {
								cost = Math.min(cost, k.numVertices());
							}
						}
						if (cost > 10 && cost > best && cost < Integer.MAX_VALUE) {
							r = new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
							best = cost;
						}
					}
				}
			}
			if (best != 0) {
				Debug.debug("BEST = " + best);
				return r;
			}
			heuristic = VERTEX_ORDER;
		}

		for (int i : graph.vertices()) {
			int head = i;
			int headc = graph.numUnderlyingEdges(head);

			for (Pair<Integer, Integer> j : graph.edges(i)) {
				int tail = j.first();
				int tailc = graph.numUnderlyingEdges(tail);
				int count = j.second();

				if (head < tail) { // to avoid duplicates
					int cost = 0;
					switch (heuristic) {
					case MAXIMISE_SDEGREE:
						cost = Math.max(headc, tailc);
						break;
					case MAXIMISE_DEGREE:
						cost = headc + tailc;
						break;
					case MAXIMISE_MDEGREE:
						cost = headc * tailc;
						break;
					case MINIMISE_DEGREE:
						cost = 2 * V - (headc + tailc);
						break;
					case MINIMISE_SDEGREE:
						cost = V - Math.min(headc, tailc);
						break;
					case MINIMISE_MDEGREE:
						cost = V * V - (headc * tailc);
						break;
					case VERTEX_ORDER:
						return new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
					case RANDOM:
						rcount += count;
						if (rcount >= rtarget) {
							return new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
						}
						break;
					case CUT:
						// er, yes this is a tad ott.
						Graph g = new Graph(graph);
						g.removeAllEdges(head, tail);
						if (!g.isBiconnected()) {
							int start = 0;

							List<Graph> biconnects = new ArrayList<Graph>();
							g.extractBiconnectedComponents(biconnects);
							g.removeGraphs(biconnects);
							int m = g.numEdges();

							// now, actually do the computation
							for (Graph k : biconnects) {
								cost = Math.max(cost, k.numEdges());
							}
						}
					}
					if (cost > best) {
						r = new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
						best = cost;
					}
				}
			}
		}

		if (best == 0) {
			throw new RuntimeException("Failure!!!");
		}

		return r;
	}
}
