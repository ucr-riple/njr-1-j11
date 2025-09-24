package edu.usc.cs.nsl.indexing.test;

import java.util.*;

import edu.usc.cs.nsl.indexing.TrieDS.Reference;
import edu.usc.cs.nsl.indexing.TrieDS.TrieNode;
import edu.usc.cs.nsl.indexing.TrieDS.WordValueStore;
public class TestTrieNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TrieNode tn = new TrieNode('a');
		
		/*
		 * Basic testing
		 */
		System.out.println("get value no word set");
		System.out.println(tn.getValue());
		tn.setValue(1);
		System.out.println("After setting value 1 with no word set");
		System.out.println(tn.getValue());
		tn.setWord(true);
		System.out.println("After word set and setting value 2");
		tn.setValue(2);
		System.out.println(tn.getValue());
		tn.setWord(false);
		System.out.println("After word set false and setting value 3");
		tn.setValue(2);
		System.out.println(tn.getValue());

		/*
		 * Testing for addition to trie and references.
		 */
		tn = new TrieNode();
		TrieNode tn2 = tn.addWord("this_t1",5,null,null,true,true);
		tn.addWord("t1", 1, null, null,false,true);
		tn.addWord("t1", 1, tn2, "this_t1",true,false);
		tn.addWord("test2", 2, null, null,false,true);
		tn.addWord("rest1", 3, null, null,false,true);
		tn.addWord("rest2", 4, null, null,false,true);
		System.out.println("New node with words added: test1, test2, rest1, rest2");
				
		System.out.println("Does it have r? \t"+(tn.getNode('r') != null));
		System.out.println("Does it have p? \t"+(tn.getNode('p') != null));
		
		List <WordValueStore>x = new ArrayList<WordValueStore>(); 
		
		tn.getWords(x);

		System.out.println("What words does it have?");

		for(WordValueStore m:x){
			System.out.println("Word:");
			System.out.println(m.getWord());
			System.out.println("Value:");
			System.out.println(m.getValue());
			System.out.println("Reference Size\t"+(m.getReferenceList() == null ? 0:m.getReferenceList().size()));
			if(m.getReferenceList() != null){
				List <Reference> ref_list = m.getReferenceList();
				for(Reference each_ref: ref_list){
					String the_ref_str = each_ref.getRefString();
					TrieNode the_ref_node = each_ref.getReference();
					if(the_ref_node != null && the_ref_node.isWord())
						System.out.println("Ref String:\t"+the_ref_str+"\tValue:"+the_ref_node.getValue());
				}
			}
		}
		
		/*
		 * Testing for deletion and presence.
		 */
		
		System.out.println("Does it have test1?\t"+tn.checkWord("test1"));
		System.out.println("Does it have test2?\t"+tn.checkWord("test2"));
		System.out.println("Remove test1?\t"+tn.removeWord("test1"));
		System.out.println("Remove test2?\t"+tn.removeWord("test2"));
		System.out.println("Does it have test1?\t"+tn.checkWord("test1"));
		System.out.println("Does it still have test2?\t"+tn.checkWord("test2"));
		
	}

}
