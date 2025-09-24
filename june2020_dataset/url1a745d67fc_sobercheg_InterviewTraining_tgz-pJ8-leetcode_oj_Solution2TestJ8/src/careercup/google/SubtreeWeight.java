package careercup.google;

import java.util.*;

/**
 * Created by Sobercheg on 12/16/13.
 * http://www.careercup.com/question?id=5648527329853440
 * Question: You are given a CSV file with 3 columns -- all integers:
 * <p/>
 * id,parent,weight
 * 10,30,1
 * 30,0,10
 * 20,30,2
 * 50,40,3
 * 40,30,4
 * <p/>
 * 0 is the assumed root node with weight 0
 * <p/>
 * which describes a tree-like structure -- each line is a node, 'parent' refers to 'id' of another node.
 * <p/>
 * Print out, for each node, the total weight of a subtree below this node (by convention, the weight of a subtree for node X includes the own weight of X).
 * <p/>
 * You may assume that the input comes pre-parsed as a sequence of Node objects
 * (substitute the appropriate syntax for java/python/c++):
 * <p/>
 * Node {
 * int id;
 * int parent;
 * int weight;
 * // ... you can add other fields right here, if necessary
 * }
 * <p/>
 * implement the following:
 * public void printSubTreeWeight(List<Node> nodes) {
 * ....}
 */

class Node {
    int id;
    int parent;
    int weight;

    Node(int id, int parent, int weight) {
        this.id = id;
        this.parent = parent;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", parent=" + parent +
                ", weight=" + weight +
                '}';
    }
}

public class SubtreeWeight {

    public void printSubTreeWeight(List<Node> nodes) {
        Map<Integer, List<Node>> childrenMap = new HashMap<Integer, List<Node>>();
        Node root = null;
        for (Node node : nodes) {
            if (node.parent == 0) root = new Node(0, 0, 0);
            if (!childrenMap.containsKey(node.parent)) {
                childrenMap.put(node.parent, new ArrayList<Node>());
            }
            childrenMap.get(node.parent).add(node);
        }

        calculateSubtreeWeight(root, childrenMap);

    }

    private int calculateSubtreeWeight(Node node, Map<Integer, List<Node>> childrenMap) {
        if (node == null) return 0;
        int subtreeWeight = node.weight;

        if (childrenMap.containsKey(node.id)) {
            for (Node child : childrenMap.get(node.id)) {
                subtreeWeight += calculateSubtreeWeight(child, childrenMap);
            }
        }
        System.out.println(String.format("Node %s subtree weight=%d", node, subtreeWeight));
        return subtreeWeight;
    }

    public static void main(String[] args) {
        SubtreeWeight subtreeWeight = new SubtreeWeight();
        List<Node> nodeList = Arrays.asList(
                new Node(2, 1, 1),
                new Node(3, 1, 1),
                new Node(4, 1, 1),
                new Node(1, 0, 1),
                new Node(5, 0, 1)
        );

        subtreeWeight.printSubTreeWeight(nodeList);
    }

}
