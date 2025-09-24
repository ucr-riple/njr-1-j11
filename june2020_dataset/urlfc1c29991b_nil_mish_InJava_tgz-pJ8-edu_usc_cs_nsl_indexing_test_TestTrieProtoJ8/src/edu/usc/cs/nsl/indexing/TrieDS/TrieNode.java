package edu.usc.cs.nsl.indexing.TrieDS;

/*
 * Based on Trie implementation discussed at https://forums.oracle.com/thread/2070706
 */

import java.util.*;

import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TrieNodePB;

/**
 * This class defines the node for a trie data structure.
 * @author nilmish
 *
 */
public class TrieNode {
	private char the_character;
	private TrieNode[] children;
	private TrieNode parent;
	private boolean is_word;
	private boolean is_leaf;
	private int value;
	private List <Reference>referenceList;
	
	public TrieNode(){
		this.is_leaf = true;
		this.is_word = false;
		this.children = new TrieNode[26*2+10+2]; // Could be small or capital letters and the 10 digits + underscore and dollar sign
		this.value = -Integer.MAX_VALUE;
	}
	
	public TrieNode(char character){
		this();
		this.the_character = character;
	}
	
	public TrieNode(char character,TrieNode parent){
		this();
		this.the_character = character;
		this.parent = parent;
	}
	
	/**
	 * Should only be called when deflating a protocol buffer
	 * Also needs parent object to be passed.
	 * @param tpb the Protocol Buffer class for the trie node.
	 * @param parent 
	 */
	
	public TrieNode(TrieNodePB tpb, TrieNode parent){
		this();
		this.is_leaf = tpb.getIsLeaf();
		this.the_character = (char)tpb.getCharacter();
		this.is_word = tpb.getIsWord();
		this.value = tpb.getValue();
		this.parent = parent;
		List<TrieNodePB> childPB = tpb.getChildrenList();
		for(TrieNodePB each_childPB: childPB){
			int child_pos = getChildPos((char)each_childPB.getCharacter());
			this.children[child_pos] = new TrieNode(each_childPB,this);
		}
		List<String> refPB_list = tpb.getReferenceListList();
		TrieNode root = getRoot();
		for(String rpb: refPB_list){
			TrieNode reference = root.addWord(rpb, 0, null, null, true, false);
			//System.out.println("Is reference "+rpb +" a word? "+reference.is_word);
			if(this.referenceList == null)
				this.referenceList = new ArrayList<Reference>();
			this.referenceList.add(new Reference(reference,rpb));
		}
	}
	
	private TrieNode getRoot(){
		if(this.parent == null)
			return this;
		return parent.getRoot();
	}
	
	/**
	 * Returns the value of this trie node if it is a word
	 * else returns the max -ve integer value
	 */
	public int getValue(){
		if(is_word) return value;
		return -Integer.MAX_VALUE;
	}
	
	/**
	 * Sets the value of this trie node only if it is a word
	 * returns the value if it sets the valye 
	 * else returns Max negative 
	 */
	
	public int setValue(int value){
		if(is_word){
			this.value = value;
			return value;
		}
		return -Integer.MAX_VALUE;
	}
	
	/**
	 * Returns true if is_word is set as true
	 */
	public boolean isWord(){
		return is_word;
	}
	
	/**
	 * Sets the is_word with value
	 */
	
	public void setWord(boolean is_word){
		this.is_word = is_word;
		if(!this.is_word)
			this.value = -Integer.MAX_VALUE;
	}
	
	/**
	 * Returns the value of is_leaf
	 */
	public boolean isLeaf(){
		return is_leaf;
	}
	
	/**
	 * Sets the value of is_leaf
	 */
	public void setLeaf(boolean value){
		is_leaf = value;
	}
	
	/**
	 * Function to calculate the position in children array for a particular character.
	 * Only support A-Z, a-z, _ and $
	 * @param the_char
	 * @return
	 */
	private int getChildPos(char the_char){
		int childPos = -1;
		if(the_char >= 'A' && the_char <= 'Z'){
			childPos = the_char - 'A';
		} else if(the_char >= 'a' && the_char <= 'z'){
			childPos = the_char - 'a' + 26;
		}else if(the_char >= '0' && the_char <= '9'){
			childPos = the_char - '0' + 52;
		} else if(the_char == '_'){
			childPos = 62;
		} else if(the_char == '$'){
			childPos = 63;
		}
		return childPos;
	}
	
	/**
	 * Get the node for a particular character
	 * @param the_char
	 * @return
	 */
	
	public TrieNode getNode(char the_char){
		int childPos = getChildPos(the_char);
		if(childPos > -1)
			return children[childPos];
		return null;
	}
	
	/**
	 * Adds a word at this node. If children are present then traverse till leaf node for which the value is updated.
	 * Assumption: All words have value. If a word does not have value then it cannot be a word.
	 */
	public TrieNode addWord(String word, int value, TrieNode referer, String ref_string, boolean fromRoot, boolean updateValue){
		if(word.length() == 0) return this;
		is_leaf = false;
		char the_char = word.charAt(0);
		int childPos = getChildPos(the_char);
		
		if(children[childPos] == null){
			children[childPos] = new TrieNode(word.charAt(0));
			children[childPos].parent = this;
		}			
		
		if(word.length() > 1){
			return children[childPos].addWord(word.substring(1),value,referer,ref_string,fromRoot,updateValue);
		} else{
			children[childPos].is_word = true;
			if(updateValue){
				children[childPos].value = value;
			}
			else{
				if(children[childPos].referenceList == null){
					children[childPos].referenceList = new ArrayList<Reference>();
				}
				children[childPos].referenceList.add(new Reference(referer, ref_string));
			}
			if(fromRoot){
				return children[childPos];
			}
		}		
		return null;
	}
	
	
	/**
	 * Returns the words and values for the words below this TrieNode. If called on root will return the complete Dictionary.
	 * It also returns the reference object with each word so that if needed they can also be looked at.
	 */
	public void getWords(List<WordValueStore> the_list){
		
		if(is_word){
			the_list.add(new WordValueStore(toString(new StringBuilder()), value, referenceList));
		}
		if(!is_leaf){
			for (int i = 0; i < children.length; i++){
				if(children[i] != null){
					children[i].getWords(the_list);
				}
			}
		}
	}	
	
	private String toString(StringBuilder sb){
		if(parent == null){
			return sb.reverse().toString();
		}
		return parent.toString(sb.append(the_character));
	}
	
	/**
	 * Provides a protocol buffer builder class the trie below this node. 
	 * @return
	 */
	public TrieNodePB toTriePB(){
		TrieNodePB.Builder tp = TrieNodePB.newBuilder();
		tp.setIsLeaf(this.is_leaf);
		tp.setCharacter(this.the_character);
		tp.setValue(this.value);
		tp.setIsWord(this.is_word);
		// Setting parent will create a loop
		for(TrieNode tn: this.children){
			if(tn != null){
				TrieNodePB tpc = tn.toTriePB();
				tp.addChildren(tpc);
			}
		}
		if(this.referenceList != null){
			for(Reference r:this.referenceList){
				tp.addReferenceList(r.getRefString());
			}
		}				
		return tp.build();		
	}
	
	/**
	 * Check in the Trie if the word is already added or not.
	 * This function will return true only when the passed
	 * word has been added explicitly. If will return false
	 * if the word has not been added or if the word is a prefix
	 * for another word.
	 */
	
	public boolean checkWord(String word){
		if(word.length() == 0) return true;
		char the_char = word.charAt(0);
		int childPos = getChildPos(the_char);
		if(this.children[childPos] == null)
			return false;
		if(word.length() == 1 ){
			if(this.children[childPos].is_word)
				return true;
			return false;
		}else{
			return this.children[childPos].checkWord(word.substring(1));
		}

	}

	/**
	 * We return true if the string is null string or if the string is present in the trie 
	 * (it may not have been added but could be a valid prefix).
	 * We return false only if the string is not present in the trie.
	 */
	public boolean removeWord(String word) {
		if(word.length() == 0) return true; 
		char the_char = word.charAt(0);
		int childPos = getChildPos(the_char);
		if(this.children[childPos] == null)
			return false;
		if(word.length() == 1 ){
			if(this.children[childPos].is_word){
				if(this.children[childPos].is_leaf){
					this.children[childPos] = null;
					this.is_leaf = true;
					return true;
				}else{
					this.children[childPos].is_word = false;
					return true;
				}
			}else{
				return true;
			}
		}else{
			if(!this.children[childPos].is_leaf){
				boolean child_result = this.children[childPos].removeWord(word.substring(1));
				if(child_result){
					if(this.children[childPos].is_leaf && (!this.children[childPos].is_word)){ // We need to do cleaning
						this.is_leaf = true;
						this.children[childPos] = null;
						return true;
					}
					else
						return true;
				}else{
					return false;
				}
			}else
				return false;
		}
	}
	
}
