package common.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sobercheg on 12/8/13.
 */
public class DfsOrder {

    private final Graph graph;
    private List<Integer> preorder;
    private List<Integer> postorder;
    private LinkedList<Integer> reversedPostorder;
    private boolean[] visited;
    private boolean[] onStack;
    private boolean hasCycle;

    public DfsOrder(Graph graph) {
        this.graph = graph;
        this.preorder = new ArrayList<Integer>();
        this.postorder = new ArrayList<Integer>();
        this.reversedPostorder = new LinkedList<Integer>();
        this.visited = new boolean[graph.getSize()];
        this.onStack = new boolean[graph.getSize()];

        for (int v = 0; v < graph.getSize(); v++) {
            if (!visited[v])
                dfs(v);
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        onStack[v] = true;
        preorder.add(v);
        for (Edge adjacent : graph.adjacent(v)) {
            if (hasCycle) return;
            int w = adjacent.getTo();
            if (!visited[w]) {
                if (onStack[w]) {
                    hasCycle = true;
                    return;
                }
                dfs(w);
            }
        }
        onStack[v] = false;
        postorder.add(v);
        reversedPostorder.push(v);
    }

    public Iterable<Integer> getPreorder() {
        return preorder;
    }

    public Iterable<Integer> getPostorder() {
        return postorder;
    }

    public Iterable<Integer> getReversedPostorder() {
        return reversedPostorder;
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
