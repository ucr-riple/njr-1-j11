package other;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 11/24/13.
 * <p/>
 * Based on Sedgewick's "Algorithms", p.737
 */
public class TrieST<Value> {

    private static final int R = 256; // radix
    private Node root;

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    public Value get(String key) {
        Node node = get(root, key, 0);
        if (node == null) return null;
        return (Value) node.value;
    }

    private Node get(Node node, String key, int d) {
        if (node == null) return null;
        if (key.length() == d) return node;
        char ch = key.charAt(d);
        return get(node.next[ch], key, d + 1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node node, String key, Value val, int d) {
        if (node == null) node = new Node();
        if (d == key.length()) {
            node.value = val;
            return node;
        }
        char c = key.charAt(d);
        node.next[c] = put(node.next[c], key, val, d + 1);
        return node;
    }


    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> queue = new LinkedList<String>();
        collect(get(root, pre, 0), pre, queue);
        return queue;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    private void collect(Node node, String pre, Queue<String> queue) {
        if (node == null) return;
        if (node.value != null) queue.add(pre);
        for (char c = 0; c < R; c++) {
            collect(node.next[c], pre + c, queue);
        }
    }

    public String longestPrefixOf(String str) {
        int length = search(root, str, 0, 0);
        return str.substring(0, length);
    }

    private int search(Node node, String str, int d, int length) {
        if (node == null) return length;
        if (node.value != null) length = d;
        if (d == str.length()) return length;
        char ch = str.charAt(d);
        return search(node.next[ch], str, d + 1, length);
    }

    public static void main(String[] args) {
        TrieST<Integer> trie = new TrieST<Integer>();
        trie.put("sea", 1);
        trie.put("shell", 2);

        System.out.println("trie.get(sea)=" + trie.get("sea"));
        System.out.println("trie.get(sell)=" + trie.get("sell"));
        System.out.println("trie.get(shell)=" + trie.get("shell"));

        System.out.println("Keys: " + trie.keys());
        System.out.println("Keys [se...]: " + trie.keysWithPrefix("se"));
    }
}
