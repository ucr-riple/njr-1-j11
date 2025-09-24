package selection;
import graph.Graph;
import util.Pair;
import util.Triple;

public class RandomEdge implements EdgeSelection{
	public Triple<Integer, Integer, Integer> select_edge(Graph graph, boolean reduce_multiedges) {
		// assumes this graph is NOT a cycle and NOT a tree
		int best = 0;
		int rcount = 0;
		int rtarget = 0;

		Triple<Integer, Integer, Integer> r = new Triple<Integer, Integer, Integer>(-1, -1, -1);

		int nedges = graph.numEdges();
		rtarget = (int) (((double) nedges * Math.random()) / (1.0 + 1.0));

		for (int i : graph.vertices()) {
			int head = i;

			for (Pair<Integer, Integer> j : graph.edges(i)) {
				int tail = j.first();
				int count = j.second();

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
