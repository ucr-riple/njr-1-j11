package other;

import common.graph.Edge;
import common.graph.Graph;

import java.util.Stack;

/**
 * Created by Sobercheg on 12/3/13.
 */
public class SCC {
    private final Graph graph;
    private int sccNumber;

    public SCC(Graph graph) {
        this.graph = graph;
        calculateSCC();
    }

    private void calculateSCC() {
        // Step 1. Do DfsOrder in G saving visited vertices in post order fashion to a stack
        Stack<Integer> dfsPostorderVertices = new Stack<Integer>();
        boolean[] visited = new boolean[graph.getSize()];
        for (int vertex = 0; vertex < graph.getSize(); vertex++) {
            if (!visited[vertex]) {
                doDfs(graph, dfsPostorderVertices, vertex, visited);
            }
        }

        // Step 2. Reverse all edges in G to obtain G*
        Graph reversedGraph = graph.reverse();

        // Step 3. Do DfsOrder in G* getting start vertices the from the stack.
        visited = new boolean[graph.getSize()];
        for (Integer vertex : dfsPostorderVertices) {
            if (!visited[vertex]) {
                doDfs(reversedGraph, new Stack<Integer>(), vertex, visited);
                sccNumber++;
            }
        }
    }

    private void doDfs(Graph graph, Stack<Integer> dfsPostorderVertices, int vertex, boolean[] marked) {
        marked[vertex] = true;

        for (Edge adj : graph.adjacent(vertex)) {
            if (!marked[adj.getTo()]) {
                doDfs(graph, dfsPostorderVertices, adj.getTo(), marked);
            }
        }

        dfsPostorderVertices.push(vertex);
    }

    public int getSCCNumber() {
        return sccNumber;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(new Edge(0, 1, 1));
        graph.addEdge(new Edge(1, 2, 1));
        graph.addEdge(new Edge(2, 0, 1));
        SCC scc = new SCC(graph);
        System.out.println(scc.getSCCNumber());
    }
}

