import graph.Graph;
import graph.SpanningGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import polynomial.FactorPoly;
import polynomial.X;
import polynomial.Y;
import util.Debug;
import util.Triple;

public class Tutte {
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

	public static void main(String[] args) {
		if (args.length == 1) {
			new Tutte(args[0]);
		} else {
			for (int i = 0; i < 100; i++) {
				new Tutte(null);
			}
		}
	}

	public Tutte(String s) {
		global_timer = new MyTimer();
		num_steps = 0;
		num_cycles = 0;

		// Make a graph
		int minVertex = 3;
		int varVertex = 10;
		int minEdges = 2;
		int varEdges = 10;
		int minMulti = 1;
		int varMulti = 1;

		SpanningGraph g = null;
		long seed = 0;

		if (s != null) {
			try {
				File f = new File(s);
				Scanner scan = new Scanner(f);
				scan.useDelimiter("[^0-9]+");
				g = new SpanningGraph(30);
				while(scan.hasNext()){
//					System.out.println(scan.next());
					
					int from = scan.nextInt();
					int to = scan.nextInt();
					g.addEdge(from, to);
				}
//				return;
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
			g = new SpanningGraph(numVertex);

			for (int i = 0; i < numEdges; i++) {
				int from = rand.nextInt(numVertex);
				int to = rand.nextInt(numVertex);
				int num = rand.nextInt(varMulti) + minMulti;
				numEdges += num - 1;
				Debug.debug("Adding " + from + " -> " + to + " x" + num);
				g.addEdge(from, to, num);
			}
		}
		FactorPoly tutte = tutte(g, 1);

		System.out.println(tutte.toString());

		// BigInteger correct = new BigInteger("2").pow(numEdges);
		// BigInteger tuttei = tutte.substitute(2, 2);
		// if (!correct.equals(tuttei)) {
		// System.err.println("Correct T(2,2) = " + correct + " but we get " + tuttei);
		// throw new RuntimeException("Sorry " + seed);
		// }

	}

	private FactorPoly tutte(SpanningGraph graph, int mid) {
		num_steps++;

		// === 1. APPLY SIMPLIFICATIONS ===
		FactorPoly RF = new FactorPoly(new Y(reduce_loops(graph)));
		// System.out.println("Removing all loops to get " + RF.toString());

		FactorPoly poly;

		// === 3. CHECK FOR ARTICULATIONS, DISCONNECTS AND/OR TREES ===

		if (reduce_multicycles && graph.isMulticycle()) {
			Debug.debug("--- MultiCycle ---");
			num_cycles++;
			poly = reduce_cycle(new FactorPoly(new X(1)), graph);

			Debug.debug(poly);
			// if(write_tree) { write_tree_leaf(mid,graph,cout); }
		} else if (!graph.isBiconnected()) {
			List<SpanningGraph> biconnects = new ArrayList<SpanningGraph>();
			graph.extractBiconnectedComponents(biconnects);

			Debug.debug("--- Bridge --- " + biconnects.size());

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
			poly = reduce_tree(new FactorPoly(new X(1)), graph);
			Debug.debug(poly);

			// now, actually do the computation
			for (SpanningGraph i : biconnects) {
				num_bicomps++;
				if (i.isMulticycle()) {
					Debug.debug("--- Is inner multicycle");
					// this is actually a cycle!
					num_cycles++;
					poly.times(reduce_cycle(new FactorPoly(new X(1)), i));
					// if(write_tree) { write_tree_leaf(tid++,i,System.out); }
				} else {
					poly.times(tutte(i, tid++));
				}
			}
		} else {
			Debug.debug("--- DELETE / CONTRACT ---");
			// TREE OUTPUT STUFF
			int lid = tree_id;
			int rid = tree_id + 1;
			tree_id = tree_id + 2; // allocate id's now so I know them!
			// if(write_tree) { write_tree_nonleaf(mid,lid,2,graph,cout); }

			// === 4. PERFORM DELETE / CONTRACT ===

			SpanningGraph g2 = new SpanningGraph(graph);
			Triple<Integer, Integer, Integer> edge = select_edge(graph);

			// System.out.println("---Picked Edge " + edge);
			// now, delete/contract on the edge's endpoints
			graph.removeEdge(edge);
			g2.contractEdge(edge);

			// System.out.println("------delete");
			// System.out.println(graph);
			// System.out.println("------contract");
			// System.out.println(g2);

			// recursively compute the polynomial, starting with delete
			if (edge.third > 1) {
				poly = tutte(graph, lid);
				// System.out.println(poly);
				FactorPoly pp = tutte(g2, rid);
				// System.out.println(pp);
				pp.times(new Y(0, edge.third - 1));
				poly.add(pp);
			} else {
				poly = tutte(graph, lid);
				Debug.debug("--returning");
				poly.add(tutte(g2, rid));
			}
		}

		return poly.timesnew(RF);
	}

	// private FactorPoly reduce_pendant(int p, SpanningGraph graph) {
	// int count = graph.numEdges(p);
	// graph.remove(p);
	//
	// FactorPoly r = new FactorPoly(new X(1));
	// if (count > 1) {
	// r.add(new Y(1, count - 1));
	// }
	// return r;
	// }

	private FactorPoly reduce_tree(FactorPoly X_p, SpanningGraph graph) {
		FactorPoly r = new FactorPoly(new Y(0)); // new polymial "1"

		for (int i : graph.vertices()) { // For each vertex
			for (Map.Entry<Integer, Integer> j : graph.edges(i)) { // For each edge from that vertex
				if (i >= j.getKey()) {// no double ups
					FactorPoly xy = new FactorPoly(X_p);
					if (j.getValue() > 1) {
						xy.add(new Y(1, j.getValue() - 1));
					}
					r.times(xy);
				}
			}
		}

		return r;
	}

	static List<Triple<Integer, Integer, Integer>> line = new ArrayList<Triple<Integer, Integer, Integer>>();

	private FactorPoly reduce_cycle(FactorPoly X_p, SpanningGraph graph) {
		// This is a somewhat icky piece of code for reducing
		// a cycle. it's really a hack at the moment.

		int last = graph.vertices().iterator().next();
		int v = last;
		int s = v;

		do {
			Iterator<Map.Entry<Integer, Integer>> itj = graph.edges(v).iterator();
			Map.Entry<Integer, Integer> j = itj.next();
			if (j.getKey() == last) {
				j = itj.next();
			}
			last = v;
			line.add(new Triple<Integer, Integer, Integer>(v, j.getKey(), j.getValue()));
			v = j.getKey();
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

	int reduce_loops(SpanningGraph graph) {
		int c = 0;
		for (Integer i : graph.vertices()) {
			c += graph.removeAllEdges(i, i);
		}
		return c;
	}

	private  Triple<Integer, Integer, Integer> select_edge(SpanningGraph graph) {
		// assumes this graph is NOT a cycle and NOT a tree
		int best = 0;
		int rcount = 0;
		int rtarget = 0;

		Triple<Integer, Integer, Integer> r = new Triple<Integer, Integer, Integer>(-1, -1, -1);

		int nedges = graph.numEdges();
		rtarget = (int) (((double) nedges * Math.random()) / (1.0 + 1.0));

		for (int i : graph.vertices()) {
			int head = i;

			for (Map.Entry<Integer, Integer> j : graph.edges(i)) {
				int tail = j.getKey();
				int count = j.getValue();

				if (head < tail) { // to avoid duplicates
					int cost = 1;

					if (rcount == rtarget) {
						return new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
					}
					rcount += count;

					if (cost > best) {
						r = new Triple<Integer, Integer, Integer>(head, tail, reduce_multiedges ? count : 1);
						best = cost;
					}
				}
			}
		}

		if (best == 0) {
			throw new RuntimeException("internal failure (select_edge)");
		}

		return r;
	}

}
