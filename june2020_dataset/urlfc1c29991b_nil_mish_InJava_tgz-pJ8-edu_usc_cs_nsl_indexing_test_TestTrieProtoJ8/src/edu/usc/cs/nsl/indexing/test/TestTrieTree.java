package edu.usc.cs.nsl.indexing.test;

import java.util.*;

import edu.usc.cs.nsl.indexing.TrieDS.Trie;
import edu.usc.cs.nsl.indexing.TrieDS.TrieNode;
import edu.usc.cs.nsl.indexing.TrieDS.WordValueStore;

public class TestTrieTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Creation and addition.
		 */
		Trie t = new Trie();
		t.addWord("test", 1);
		t.addWord("text", 1);
		t.addWord("test1", 1);
		t.addWord("rest", 1);
		t.addWord("testing", 1);
		
		/*
		 * references
		 */
		TrieNode tn;
		tn = t.addWord("test_best", 34);
		t.addReference("test", tn,"test_best");
		
		/*
		 * Words in the Trie
		 */
		List <WordValueStore>x = t.getWordsWithPrefix("test");
		
		System.out.println("Size:\t"+x.size());
		
		for(WordValueStore m: x){
			System.out.println("Word");
			System.out.println(m.getWord());
			System.out.println("Value");
			System.out.println(m.getValue());
			System.out.println("Reference Size\t"+(m.getReferenceList() == null ? 0:m.getReferenceList().size()));
		}

		/*
		 * Removal 
		 */		


	}

}
