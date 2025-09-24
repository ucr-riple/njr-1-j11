package jswang.AddAndSearchWord;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 5/21/15.
 */
public class WordDictionary {

    private class TrieNode {
        Map<Character, TrieNode> offset;
        boolean isword;

        TrieNode() {
            offset = new HashMap<Character, TrieNode>();
            isword = false;
        }
    }

    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }

    // Adds a word into the data structure.
    public void addWord(String word) {
        TrieNode parent = root;
        for (int i = 0; i < word.length(); ++i) {
            if (parent.offset.containsKey(word.charAt(i))) {
                parent = parent.offset.get(word.charAt(i));
                continue;
            }
            TrieNode tmp = new TrieNode();
            parent.offset.put(word.charAt(i), tmp);
            parent = tmp;
        }
        parent.isword = true;
    }

    // Returns if the word is in the data structure. A word could
    // contain the dot character '.' to represent any one letter.
    public boolean search(String word) {
        return searchHelp(word, root);
    }

    private boolean searchHelp(String subWord, TrieNode parent) {
        if (subWord.length() == 0) {
            if (parent.isword)
                return true;
            return false;
        }
        char c = subWord.charAt(0);
        if (c != '.') {
            if (!parent.offset.containsKey(c))
                return false;
            return searchHelp(subWord.substring(1, subWord.length()), parent.offset.get(c));
        }
        else {
            for (Map.Entry<Character, TrieNode> entry : parent.offset.entrySet()) {
                if (searchHelp(subWord.substring(1, subWord.length()), entry.getValue()))
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("a");
        wordDictionary.addWord("a");
        System.out.println(wordDictionary.search("aa"));
    }
}

// Your WordDictionary object will be instantiated and called as such:
// WordDictionary wordDictionary = new WordDictionary();
// wordDictionary.addWord("word");
// wordDictionary.search("pattern");