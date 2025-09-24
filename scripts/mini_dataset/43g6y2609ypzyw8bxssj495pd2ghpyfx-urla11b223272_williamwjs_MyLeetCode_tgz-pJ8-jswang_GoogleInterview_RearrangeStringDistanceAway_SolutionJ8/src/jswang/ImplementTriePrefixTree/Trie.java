package jswang.ImplementTriePrefixTree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 5/10/15.
 */

class TrieNode {
    // Initialize your data structure here.
    Map<Character, TrieNode> offspring;
    boolean isWord;

    public TrieNode() {
        offspring = new HashMap<Character, TrieNode>();
        isWord = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        int index = 0;
        TrieNode cur = root;
        while (index < word.length() && cur.offspring.containsKey(word.charAt(index)))
            cur = cur.offspring.get(word.charAt(index++));
        while (index < word.length()) {
            TrieNode tmp = new TrieNode();
            cur.offspring.put(word.charAt(index++), tmp);
            cur = tmp;
        }
        cur.isWord = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); ++i) {
            if (cur.offspring.containsKey(word.charAt(i)))
                cur = cur.offspring.get(word.charAt(i));
            else
                return false;
        }
        return cur.isWord;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); ++i) {
            if (cur.offspring.containsKey(prefix.charAt(i)))
                cur = cur.offspring.get(prefix.charAt(i));
            else
                return false;
        }
        return true;
    }

    public static void main (String[] args) {
        Trie trie = new Trie();
        trie.insert("some");
        trie.insert("somestring");
        System.out.println(trie.search("somes"));
        System.out.println(trie.search("some"));
        System.out.println(trie.startsWith("somes"));
        System.out.println(trie.startsWith("somet"));
        trie.insert("somestring");
        System.out.println(trie.search("somestring"));
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");