package edu.usc.cs.nsl.indexing;

import edu.usc.cs.nsl.indexing.TrieDS.*;
import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TriePB;

import java.io.*;
import java.util.*;

/**
 * Simple query server to take query from console.
 * De-serializes a trie data structure from input file.
 * @author nilmish
 *
 */

public class QueryServer {

	/**
	 * @param args
	 */
	
	private Trie the_trie;
	
	/**
	 * Constructor for the query server which takes as input the location of the file which has the trie protocol buffer.
	 * @param inputFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public QueryServer(String inputFile) throws FileNotFoundException, IOException{
		TriePB tp = TriePB.parseFrom(new FileInputStream(inputFile));
		the_trie = new Trie(tp);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		if(args.length < 1){
			System.out.println("Usage: Please provide the serizlized Trie protocol buffer file");
			System.exit(0);
		}
		
		QueryServer qs = new QueryServer(args[0]);
		BufferedReader the_input = new BufferedReader(new InputStreamReader(System.in));		
		while(true){
			System.out.print("Please enter your query word: ");
			String query = (the_input.readLine()).trim();
//			System.out.println("Input word is \""+query+"\" with length: "+query.length());
			if(query.toLowerCase().equals("quit") || query.toLowerCase().equals("exit"))
				break;
			ArrayDeque<Entry> top_results = qs.getTopResults(query,10);
			if(top_results == null){
				System.out.print("Not a valid query. Please try again");
			}else{
				if(top_results.size() > 0)
					System.out.println("Top results for the query are:");
				else
					System.out.println("There is no matching result for this query");
				for(Entry a_entry:top_results){
					System.out.println(a_entry.getWord()+"\t"+a_entry.getScore());
				}
			}
		}	
	}

	private ArrayDeque<Entry> getTopResults(String query, int num_results) {
		if(query == null) return null;
		if(query.equals("")) return null;
		PriorityQueue<Entry> results = new PriorityQueue<Entry>(10,new Entry());
		List<WordValueStore> ws = the_trie.getWordsWithPrefix(query);
		for(WordValueStore each_ws:ws){
			if(each_ws.getValue() != -Integer.MAX_VALUE)
				results.add(new Entry(each_ws.getWord(), each_ws.getValue()));
			List <Reference> refs = each_ws.getReferenceList();
			if(refs != null){
				for(Reference each_ref: refs){
					if(each_ref.getReference().isWord()){						
						results.add(new Entry(each_ref.getRefString(), each_ref.getReference().getValue()));
					}
				}
			}
		}
		ArrayDeque<Entry> top_results = new ArrayDeque<Entry>();
		for(int i=0; i< num_results && (!results.isEmpty()); i++){
			top_results.addLast(results.remove());
		}
		return top_results;
	}
}
