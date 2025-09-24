package edu.usc.cs.nsl.indexing.test;

import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

import edu.usc.cs.nsl.indexing.TrieDS.Reference;
import edu.usc.cs.nsl.indexing.TrieDS.Trie;
import edu.usc.cs.nsl.indexing.TrieDS.TrieNode;
import edu.usc.cs.nsl.indexing.TrieDS.WordValueStore;
import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TrieNodePB;
import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TriePB;

public class TestTrieProto {	
	
	public static void main(String []args) throws InvalidProtocolBufferException{
		Trie t = new Trie();
		t.addWord("test", 1);
		t.addWord("text", 1);
		t.addWord("test1", 1);
		t.addWord("rest", 1);
		t.addWord("testing", 1);
		TrieNode tn = t.addWord("best_test", 34);
		t.addReference("test", tn, "best_test");
		
		byte[] x = t.toProtocolBuffer();
		
		Trie new_t = new Trie(TriePB.parseFrom(x));
		List <WordValueStore>y = new_t.getWordsWithPrefix("test");
		
		System.out.println("Size:\t"+y.size());
		
		for(WordValueStore m: y){
			System.out.println("Word");
			System.out.println(m.getWord());
			System.out.println("Value");
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
		
	}	
}
