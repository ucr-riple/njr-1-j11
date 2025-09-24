package common.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sobercheg on 12/8/13.
 */
public class Graph {

    private final int size;
    private List<Edge>[] adj;

    public Graph(int size) {
        this.size = size;
        this.adj = (List<Edge>[]) new List[size];
        for (int i = 0; i < size; i++) {
            this.adj[i] = new ArrayList<Edge>();
        }
    }

    public Collection<Edge> adjacent(int v) {
        return adj[v];
    }

    public void addEdge(Edge edge) {
        adj[edge.getFrom()].add(edge);
    }

    public void addEdge(int from, int to, double weight) {
        adj[from].add(new Edge(from, to, weight));
    }

    public Iterable<Edge> getAllEdges() {
        List<Edge> allEdges = new ArrayList<Edge>();
        for (int v = 0; v < size; v++) {
            allEdges.addAll(adjacent(v));
        }
        return allEdges;
    }

    public int getSize() {
        return size;
    }

    public Graph reverse() {
        Graph reversed = new Graph(size);
        for (Edge edge : getAllEdges()) {
            reversed.addEdge(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }
        return reversed;
    }
}
