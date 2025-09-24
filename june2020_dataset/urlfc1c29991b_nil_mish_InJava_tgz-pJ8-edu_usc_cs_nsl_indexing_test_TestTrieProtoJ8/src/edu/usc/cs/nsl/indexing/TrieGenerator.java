package edu.usc.cs.nsl.indexing;

import java.io.*;
import java.util.*;

import edu.usc.cs.nsl.indexing.TrieDS.Trie;
import edu.usc.cs.nsl.indexing.TrieDS.TrieNode;

/**
 * This class generates a trie data structure from a tab word/score text file.
 * Each line has a word and a corresponding score (separated by tab).
 * It serializes the trie as a protocol buffer which is written in a file.
 * @author nilmish
 *
 */
public class TrieGenerator {
	private Trie the_trie = null;
	
	
	
	public TrieGenerator(ArrayList<Entry> the_list){
		the_trie = new Trie();
		for(Entry each_entry: the_list){
			String word = each_entry.getWord();
			int score = each_entry.getScore();
			TrieNode mainNode = the_trie.addWord(word, score);
			String []tokens = word.split("_");
			if(tokens.length > 1){
				for(int i = 1; i < tokens.length; i++){
					the_trie.addReference(tokens[i], mainNode, word);
					//System.out.println("token: "+tokens[i]+"is mainNode word? "+mainNode.isWord());
				}
			}			
		}
	}
	
	public byte[] getSerialzedTrie(){
		return the_trie.toProtocolBuffer();
	}
	
	public void writeTriePB(FileOutputStream out) throws IOException{
		the_trie.writeProtocolBuffer(out);
	}
	
	public static void main(String args[]) throws IOException{
		if(args.length < 2){
			System.out.println("Usage: requires the text file with the name and score for input and outfile for the serialized Trie");
			System.exit(0);
		}
		
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String str = br.readLine();
		ArrayList<Entry> the_list = new ArrayList<Entry>();
		while(str != null){
			String []tokens = str.split("\t");
			the_list.add(new Entry(tokens[0],Integer.parseInt(tokens[1])));
			str = br.readLine();
		}
		br.close();
		TrieGenerator tg = new TrieGenerator(the_list);
		FileOutputStream out = new FileOutputStream(args[1]);
		tg.writeTriePB(out);		
	}

}
