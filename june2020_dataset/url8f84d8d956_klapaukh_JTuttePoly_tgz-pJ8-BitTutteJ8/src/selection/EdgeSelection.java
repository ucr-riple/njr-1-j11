package selection;
import graph.Graph;
import util.Triple;


public interface  EdgeSelection {
	public Triple<Integer, Integer, Integer> select_edge(Graph graph, boolean reduce) ;
}

