package edu.usc.cs.nsl.indexing.TrieDS;

/**
 * Wrapper class to store references.
 * @author nilmish
 *
 */

public class Reference {
	private TrieNode reference;
	private String ref_string;
	
	public Reference(TrieNode reference, String ref_string){
		this.reference = reference;
		this.ref_string = ref_string;
	}
	
	
	public TrieNode getReference(){
		return reference;
	}
	
	public String getRefString(){
		return ref_string;
	}
	
}
