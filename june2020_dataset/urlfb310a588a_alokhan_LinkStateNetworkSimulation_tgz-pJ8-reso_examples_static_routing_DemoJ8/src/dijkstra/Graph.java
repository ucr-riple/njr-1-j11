package dijkstra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Th graph contains all the routers and their link to each others.
 *
 * @author alo
 */
public class Graph {

    private final List<FibonacciHeapNode> nodes;
    private final List<Edge> edges;

    public Graph(Collection<FibonacciHeapNode> nodes, Collection<Edge> edges) {
        this.nodes = new ArrayList<FibonacciHeapNode>(nodes);
        this.edges = new ArrayList<Edge>(edges);
    }

    public List<FibonacciHeapNode> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
