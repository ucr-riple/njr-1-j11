package jswang.GoogleInterview.AlienDictionary;

import java.util.*;

/**
 * Created by willwjs on 10/11/15.
 * Time Complexity:
 * The first step to create a graph takes O(n + alhpa) time
 * where n is number of given words and alpha is number of characters in given alphabet.
 * The second step is topological sorting.
 * Note that there would be alpha vertices and at-most (n-1) edges in the graph.
 * The time complexity of topological sorting is O(V+E) which is O(n + aplha) here.
 * So overall time complexity is O(n + aplha) + O(n + aplha) which is O(n + aplha).
 */
public class Solution {
    public List<Character> findAlphabet(List<String> dict, int alpha) {
        Map<Character, GraphNode> nodeMap = new HashMap<>(alpha);
        for (int i = 0; i < dict.size() - 1; ++i) {
            String small = dict.get(i), big = dict.get(i + 1);
            for (int k = 0; k < Math.min(small.length(), big.length()); ++k) {
                char cs = small.charAt(k), cb = big.charAt(k);
                if (cs == cb) continue;
                GraphNode s, b;
                if (nodeMap.containsKey(cs)) {
                    s = nodeMap.get(cs);
                } else {
                    s = new GraphNode(cs);
                    nodeMap.put(cs, s);
                }
                if (nodeMap.containsKey(cb)) {
                    b = nodeMap.get(cb);
                } else {
                    b = new GraphNode(cb);
                    nodeMap.put(cb, b);
                }
                b.children.add(s);
                break;
            }
        }

        List<Character> res = new LinkedList<>();
        Set<Character> visited = new HashSet<>();
        for (char cur : nodeMap.keySet()) {
            if (!visited.contains(cur))
                helper(nodeMap, res, visited, cur);
        }
        return res;
    }

    private void helper(Map<Character, GraphNode> nodeMap, List<Character> res, Set<Character> visited, char cur) {
        visited.add(cur);
        GraphNode curNode = nodeMap.get(cur);
        for (GraphNode child : curNode.children) {
            if (!visited.contains(child.c))
                helper(nodeMap, res, visited, child.c);
        }
        res.add(curNode.c);
    }

    public static void main(String[] args) {
        List<String> dict = new LinkedList<>();
        dict.add("caa");
        dict.add("aaa");
        dict.add("aab");
        System.out.println(new Solution().findAlphabet(dict, 3));
    }
}

class GraphNode {
    char c;
    List<GraphNode> children;
    GraphNode(char c) { this.c = c; children = new LinkedList<>(); }
}