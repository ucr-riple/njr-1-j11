package edu.usc.cs.nsl.indexing;

import java.util.Comparator;


/**
 * Wrapper function to store word-score pair
 * @author nilmish
 *
 */
class Entry implements Comparator<Entry>{
	String word;
	int score;
	
	Entry(String word, int score){
		this.word = word;
		this.score = score;
	}
	
	public Entry() {
		// TODO Auto-generated constructor stub
		word = null;
		score = -Integer.MAX_VALUE;
	}

	String getWord(){
		return word;
	}
	
	int getScore(){
		return score;
	}

	/**
	 * Follows a reverse order to have the priority queue in decreasing order.
	 */
	@Override
	public int compare(Entry o1, Entry o2) {
		if(o1.getScore() < o2.getScore())
			return 1;
		if(o1.getScore() > o2.getScore())
			return -1;
		return 0;
	}
	
}