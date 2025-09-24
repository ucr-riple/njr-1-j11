import graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import polynomial.FactorPoly;
import polynomial.X;
import polynomial.Y;
import selection.EdgeSelection;
import selection.HeuristicCollection;
import util.Cache;
import util.Debug;
import util.Pair;
import util.Triple;

public class BitTutte {

	public static final int V_RANDOM = 0;
	public static final int V_MINIMISE_UNDERLYING_DEGREE = 1;
	public static final int V_MAXIMISE_UNDERLYING_DEGREE = 2;
	public static final int V_MINIMISE_DEGREE = 3;
	public static final int V_MAXIMISE_DEGREE = 4;
	public static final int V_BFS = 5;
	public static final int V_NONE = 6;

	MyTimer global_timer;
	boolean status_flag;
	boolean write_tree;

	int resize_stats = 0;
	long num_steps = 0;
	long num_bicomps = 0;
	long num_cycles = 0;
	long num_disbicomps = 0;
	long num_trees = 0;
	long num_completed = 0;
	long old_num_steps = 0;
	int tree_id = 2;

	boolean reduce_multicycles = true;
	boolean reduce_multiedges = true;
	boolean use_add_contract = false;
	int split_threshold = 0;
	private int smallGraphThreshold = 5;
	Cache cache;

	EdgeSelection edgeSelection;

	long totalWork;
	long workDone;

	public static void main(String[] args) {
		BitTutte t;
		if (args.length == 1) {
			t = new BitTutte(args[0]);
			System.err.println(t.cache.statistics());
			System.err.println("Steps: " + t.num_steps);
		} else {
			for (int i = 0; i < 100; i++) {
				t = new BitTutte(null);
			}
		}
	}

	public BitTutte(String s) {
		global_timer = new MyTimer();
		num_steps = 0;
		num_cycles = 0;
		cache = new Cache();
		edgeSelection = new HeuristicCollection(HeuristicCollection.MINIMISE_SDEGREE);

		// Make a graph
		int minVertex = 3;
		int varVertex = 10;
		int minEdges = 2;
		int varEdges = 10;
		int minMulti = 1;
		int varMulti = 1;

		Graph g = null;
		long seed = 0;

		if (s != null) {
			try {
				File f = new File(s);
				Scanner scan = new Scanner(f);
				scan.useDelimiter("[^0-9]+");
				List<Pair<Integer, Integer>> l = new ArrayList<Pair<Integer, Integer>>();
				Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
				int count = 0;
				while (scan.hasNext()) {
					int from = scan.nextInt();
					int to = scan.nextInt();
					l.add(new Pair<Integer, Integer>(from, to));
					if (!numbers.containsKey(from)) {
						numbers.put(from, count++);
					}
					if (!numbers.containsKey(to)) {
						numbers.put(to, count++);
					}

				}
				scan.close();
				g = new Graph(count);
				g = orderVertices(g, l, numbers, V_MINIMISE_DEGREE);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (g == null) {
			Random rand = new Random();
			seed = rand.nextLong();
			// seed =2001405263954154592L;
			// System.out.println("Seed " + seed);
			rand.setSeed(seed);

			int numVertex = rand.nextInt(varVertex) + minVertex;
			int numEdges = rand.nextInt(varEdges) + minEdges;

			System.out.println("Graph with " + numVertex + " vertices and " + numEdges + " edges");
			g = new Graph(numVertex);

			for (int i = 0; i < numEdges; i++) {
				int from = rand.nextInt(numVertex);
				int to = rand.nextInt(numVertex);
				int num = rand.nextInt(varMulti) + minMulti;
				numEdges += num - 1;
				debug("Adding " + from + " -> " + to + " x" + num);
				g.addEdge(from, to, num);
			}
		}

		totalWork = pow(2, g.numEdges());
		workDone = 0;
		// System.err.println(g.numEdges());
		FactorPoly tutte = tutte(g, 1);
		System.out.println();
		System.out.println(tutte.toString());
		// BigInteger correct = new BigInteger("2").pow(numEdges);
		// BigInteger tuttei = tutte.substitute(2, 2);
		// if (!correct.equals(tuttei)) {
		// System.err.println("Correct T(2,2) = " + correct + " but we get " + tuttei);
		// throw new RuntimeException("Sorry " + seed);
		// }

	}
	
	private long pow(long a, long b){
		if(b <0) {
			 throw new RuntimeException("b < 0");
		}
		long t = 1;
		for(int i = 0 ; i < b ;i++){
			t*=a;
		}
		return t;
	}

	/**
	 * Make the graph, but give the vertex labels some meaning Both the graph g and Map numbers will be modified
	 * 
	 * @param g
	 *            The graph to populate
	 * @param l
	 *            List of edges
	 * @param numbers
	 *            Mapping of vertices to the smallest domain possible
	 * @param vertexHeuristic
	 *            ordering heuristic to use
	 * @return Graph g with all the vertices added.
	 */
	private Graph orderVertices(Graph g, List<Pair<Integer, Integer>> l, Map<Integer, Integer> numbers, int vertexHeuristic) {
		// TODO UNTESTED
		List<Pair<Integer, Integer>> counts = new ArrayList<Pair<Integer, Integer>>();
		for (Integer i : numbers.keySet()) {
			counts.add(new Pair<Integer, Integer>(i, 0));
		}
		Set<Pair<Integer, Integer>> underlyingEdges;
		switch (vertexHeuristic) {
		case V_RANDOM:
			for (int i = 0; i < counts.size(); i++) {
				counts.set(i, new Pair<Integer, Integer>(counts.get(i).first(), (int) (Math.random() * 2000)));
			}
			Collections.sort(counts, new Pair.MaximizeSecondComparator());
			break;
		case V_MINIMISE_UNDERLYING_DEGREE:
			underlyingEdges = new TreeSet<Pair<Integer, Integer>>(l);
			for (Pair<Integer, Integer> i : underlyingEdges) {
				counts.set(numbers.get(i.first()), new Pair<Integer, Integer>(i.first(), counts.get(numbers.get(i.first())).second() + 1));
				counts.set(numbers.get(i.second()), new Pair<Integer, Integer>(i.second(), counts.get(numbers.get(i.second())).second() + 1));
			}
			Collections.sort(counts, new Pair.MinimizeSecondComparator());
			break;
		case V_MAXIMISE_UNDERLYING_DEGREE:
			underlyingEdges = new TreeSet<Pair<Integer, Integer>>(l);
			for (Pair<Integer, Integer> i : underlyingEdges) {
				counts.set(numbers.get(i.first()), new Pair<Integer, Integer>(i.first(), counts.get(numbers.get(i.first())).second() + 1));
				counts.set(numbers.get(i.second()), new Pair<Integer, Integer>(i.second(), counts.get(numbers.get(i.second())).second() + 1));
			}
			Collections.sort(counts, new Pair.MaximizeSecondComparator());
			break;
		case V_MINIMISE_DEGREE:
			for (Pair<Integer, Integer> i : l) {
				counts.set(numbers.get(i.first()), new Pair<Integer, Integer>(i.first(), counts.get(numbers.get(i.first())).second() + 1));
				counts.set(numbers.get(i.second()), new Pair<Integer, Integer>(i.second(), counts.get(numbers.get(i.second())).second() + 1));
			}
			Collections.sort(counts, new Pair.MinimizeSecondComparator());
			break;
		case V_MAXIMISE_DEGREE:
			for (Pair<Integer, Integer> i : l) {
				counts.set(numbers.get(i.first()), new Pair<Integer, Integer>(i.first(), counts.get(numbers.get(i.first())).second() + 1));
				counts.set(numbers.get(i.second()), new Pair<Integer, Integer>(i.second(), counts.get(numbers.get(i.second())).second() + 1));
			}
			Collections.sort(counts, new Pair.MaximizeSecondComparator());
			break;
		case V_BFS:
			System.out.println("BFS vertex order is unimplemented, falling back to none");
		case V_NONE:
			// just do nothing
		default:
			break;
		}

		for (int i = 0; i < counts.size(); i++) {
			numbers.put(counts.get(i).first(), i);
		}

		for (Pair<Integer, Integer> i : l) {
			g.addEdge(numbers.get(i.first()), numbers.get(i.second()));
		}

		return g;
	}

	private FactorPoly tutte(Graph graph, int mid) {
		num_steps++;
		debug("Original Graph:\n");
		debug(graph);
		// === 1. APPLY SIMPLIFICATIONS ===

		int numY = reduce_loops(graph);
		FactorPoly RF = new FactorPoly(new Y(numY));

		{
			if(numY > 0){
				System.out.println("Reduced loops");
			}
			int numEdges = graph.numEdges();
			workDone += numY;
			for (int i = numEdges + numY - 1; i >= numEdges; i--) {
//				System.out.println("Reduced loops counting");
				workDone += pow(2, i)-1;
			}
		}
		// System.out.println("Removing all loops to get " + RF.toString());
		System.err.printf("\r%.2f%%", 100.0 * workDone / (double) totalWork);
		if (graph.numVertices() >= smallGraphThreshold && !graph.isMultitree()) {
			FactorPoly r = cache.get(graph);
			if (r != null) {
				workDone += pow(2, graph.numEdges()) -1;
				debug("Cache Hit!!");
				return r.timesnew(RF);
			}
		}
		Graph cacheEntry = new Graph(graph);
		FactorPoly poly;

		// === 3. CHECK FOR ARTICULATIONS, DISCONNECTS AND/OR TREES ===

		if (reduce_multicycles && graph.isMulticycle()) {
			debug("--- MultiCycle ---");
			num_cycles++;

			int numEdgesBefore = graph.numEdges();

			poly = reduce_cycle(new FactorPoly(new X(1)), graph);

			int numEdgesAfter = graph.numEdges();
			workDone += numEdgesBefore - numEdgesAfter; 
			for (int i = numEdgesBefore - 1; i >= numEdgesAfter; i--) {
				workDone += pow(2, i)-1;
			}

			debug(poly);
			// if(write_tree) { write_tree_leaf(mid,graph,cout); }
		} else if (!graph.isBiconnected()) {
			List<Graph> biconnects = new ArrayList<Graph>();
			graph.extractBiconnectedComponents(biconnects);

			debug("--- Biconnected --- " + biconnects.size());

			// figure out how many tree ids I need
			int tid = tree_id;
			tree_id += biconnects.size();
			// if(biconnects.size() > 0 && write_tree) { write_tree_nonleaf(mid,tid,tree_id-tid,graph,cout); }
			// else if(write_tree) { write_tree_leaf(mid,graph,cout); }
			// System.out.println("Have ");
			// System.out.println(graph);
			// System.out.println("removing ");
			// System.out.println(biconnects);
			graph.removeGraphs(biconnects);

			// System.out.println("get ");
			// System.out.println(graph);
			if (graph.isMultitree()) {
				num_trees++;
			}
			if (biconnects.size() > 1) {
				num_disbicomps++;
			}

			int numEdgesBefore = graph.numEdges();

			poly = reduce_tree(new FactorPoly(new X(1)), graph);
			debug(poly);

			int numEdgesAfter = graph.numEdges();
			workDone += numEdgesBefore - numEdgesAfter; 
			for (int i = numEdgesBefore - 1; i >= numEdgesAfter; i--) {
				workDone += pow(2, i)-1;
			}

			// now, actually do the computation
			for (Graph i : biconnects) {
				num_bicomps++;
				if (i.isMulticycle()) {
					debug("--- Is inner multicycle");
					// this is actually a cycle!
					num_cycles++;

					numEdgesBefore = graph.numEdges();

					poly.times(reduce_cycle(new FactorPoly(new X(1)), i));

					numEdgesAfter = graph.numEdges();
					workDone += numEdgesBefore - numEdgesAfter; 
					for (int j = numEdgesBefore - 1; j >= numEdgesAfter; j--) {
						workDone += pow(2, j)-1;
					}
					// if(write_tree) { write_tree_leaf(tid++,i,System.out); }
				} else {
					workDone++;
					poly.times(tutte(i, tid++));
				}
			}
		} else {
			debug("--- DELETE / CONTRACT ---");
			// TREE OUTPUT STUFF
			int lid = tree_id;
			int rid = tree_id + 1;
			tree_id = tree_id + 2; // allocate id's now so I know them!
			// if(write_tree) { write_tree_nonleaf(mid,lid,2,graph,cout); }

			// === 4. PERFORM DELETE / CONTRACT ===

			Graph g2 = new Graph(graph);
			Triple<Integer, Integer, Integer> edge = edgeSelection.select_edge(graph, reduce_multiedges);

			// System.out.println("---Picked Edge " + edge);
			// now, delete/contract on the edge's endpoints
			graph.removeEdge(edge);
			g2.contractEdge(edge);

			// System.out.println("------delete");
			// System.out.println(graph);
			// System.out.println("------contract");
			// System.out.println(g2);

			// recursively compute the polynomial, starting with delete
			workDone+=1;
			if (edge.third > 1) {
				
				int numEdges = graph.numEdges();
				
				workDone += edge.third; 
				for (int j = numEdges+ edge.third - 1; j >= numEdges ; j--) {
					workDone += pow(2, j)-1;
				}
				poly = tutte(graph, lid);
				// System.out.println(poly);
				FactorPoly pp = tutte(g2, rid);
				// System.out.println(pp);
				pp.times(new Y(0, edge.third - 1));
				
				poly.add(pp);
			} else {
				poly = tutte(graph, lid);
				debug("--returning");
				poly.add(tutte(g2, rid));
			}
		}

		if (cacheEntry.numVertices() >= smallGraphThreshold && !cacheEntry.isMultitree()) {
			cache.add(cacheEntry, poly);
		}

		// if(CACHELOOK != null && !CACHELOOK.toString().equals(poly.toString())){
		// System.out.println(graph);
		// System.out.println("Cache: " + CACHELOOK.toString());
		// System.out.println("Computed: " + poly.toString());
		// System.out.println(poly.toString().equals((new FactorPoly(poly)).toString()));
		// throw new RuntimeException("Cached value was wrong!!!");
		// }
		System.err.printf("\r%.2f%%  %d %d", 100.0 * workDone / (double) totalWork, workDone, totalWork);
		return poly.timesnew(RF);
	}

	// Depricated by reducing not biconnected things
	// private FactorPoly reduce_pendant(int p, Graph graph) {
	// int count = graph.numEdges(p);
	// graph.clear(p);
	//
	// FactorPoly r = new FactorPoly(new X(1));
	// if (count > 1) {
	// r.add(new Y(1, count - 1));
	// }
	// return r;
	// }

	private FactorPoly reduce_tree(FactorPoly X_p, Graph graph) {
		FactorPoly r = new FactorPoly(new Y(0)); // new polymial "1"

		for (int i : graph.vertices()) { // For each vertex
			for (Pair<Integer, Integer> j : graph.edges(i)) { // For each edge from that vertex
				if (i >= j.first()) {// no double ups
					FactorPoly xy = new FactorPoly(X_p);
					if (j.second() > 1) {
						xy.add(new Y(1, j.second() - 1));
					}
					r.times(xy);
				}
			}
		}

		return r;
	}

	static List<Triple<Integer, Integer, Integer>> line = new ArrayList<Triple<Integer, Integer, Integer>>();

	private FactorPoly reduce_cycle(FactorPoly X_p, Graph graph) {
		// This is a somewhat icky piece of code for reducing
		// a cycle. it's really a hack at the moment.

		int last = graph.vertices().iterator().next();
		int v = last;
		int s = v;

		do {
			Iterator<Pair<Integer, Integer>> itj = graph.edges(v).iterator();
			Pair<Integer, Integer> j = itj.next();
			if (j.first() == last) {
				j = itj.next();
			}
			last = v;
			line.add(new Triple<Integer, Integer, Integer>(v, j.first(), j.second()));
			v = j.first();
		} while (v != s);

		FactorPoly xs = new FactorPoly(X_p);
		FactorPoly acc = new FactorPoly(X_p);
		if (line.get(0).third > 1) {
			acc.add(new Y(1, line.get(0).third - 1));
			xs.add(new Y(1, line.get(0).third - 1));
		}

		for (int k = 1; k < line.size() - 1; ++k) {
			FactorPoly tmp = new FactorPoly(X_p);
			if (line.get(k).third > 1) {
				tmp.add(new Y(1, line.get(k).third - 1));
			}
			if (line.get(k + 1).third > 1) {
				xs.times(new Y(0, line.get(k + 1).third - 1));
			}
			acc.times(tmp);
			xs.add(acc);
		}

		FactorPoly ys = new FactorPoly(new Y(line.get(0).third, line.get(0).third));
		for (int k = 1; k < line.size(); ++k) {
			if (line.get(k).third > 1) {
				ys.times(new Y(0, line.get(k).third - 1));
			}
		}
		xs.add(ys);

		line.clear(); // clear it for next time around

		return xs;
	}

	int reduce_loops(Graph graph) {
		int c = 0;
		for (Integer i : graph.vertices()) {
			c += graph.removeAllEdges(i, i);
		}
		return c;
	}

	public void debug(Object s) {
		Debug.debug(s);
	}
}
