package edu.usc.cs.nsl.indexing.TrieDS;

import java.util.*;

/**
 * Wrapper class to store references to strings with _ in them.
 * @author nilmish
 *
 */

public class WordValueStore {
	private String word;
	private int value;
	private List<Reference> ref;
	
	public WordValueStore(String word,int value, List<Reference> ref){
		this.word = word;
		this.value = value;
		this.ref = ref;
	}
	
	public String getWord(){
		return word;
	}
	
	public int getValue(){
		return value;
	}
	
	public List<Reference> getReferenceList(){
		return ref;
	}
}
