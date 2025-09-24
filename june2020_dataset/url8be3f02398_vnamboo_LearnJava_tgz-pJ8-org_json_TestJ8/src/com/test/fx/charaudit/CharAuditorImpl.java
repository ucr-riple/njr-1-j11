package com.test.fx.charaudit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.test.fx.test.util.QuickSort;

public class CharAuditorImpl implements CharAuditor {

	/**
	 * return a Map of unique characters found in the input string, along with a
	 * count of their occurrence in the string (order is unimportant)
	 * getFirstNSortedChars  sort the characters
	 */
	@Override
	public Map<Character, Integer> getCharCounts(String input) {
		Map<Character, Integer> charCountMap = new HashMap<Character, Integer>();
		if (null == input || input.length() == 0) {
			return charCountMap;
		}
		char[] sortedChars = sort(input);
		char currentChar = sortedChars[0];
		int charCount = 1;
		for (int index = 1; index < sortedChars.length; index++) {
			char indexChar = sortedChars[index];
			if (currentChar != indexChar) {
				charCountMap.put(new Character(currentChar), charCount);
				charCount = 1;
				currentChar = indexChar;
			} else {
				charCount++;
			}
		}
		charCountMap.put(new Character(currentChar), charCount);
		return charCountMap;
	}

	/**
	 * sort the characters of the input string, and return the first n
	 * characters of the result
	 */
	@Override
	public String getFirstNSortedChars(String input, int topN) {
		if (null == input) {
			return input;
		}
		if(input.length()==0){
			return "";
		}
		char[] sortedChars = sort(input);
		//	Compare the array size and requested size and choose the max
		if(input.length()<topN){
			topN=input.length();
		}
		return new String(sortedChars,0,topN);
		
	}

	/**
	 * return the unique characters of the input string, ordered by prevalence
	 * (most prevalent first, to least prevalent)
	 */
	@Override
	public String getUniqueCharsSortedByPrevalence(String input) {
		Map<Character, Integer> charCountMap =getCharCounts(input);
		Set<CharSet> sortedSet=new TreeSet<CharSet>();
		for(Character entry:charCountMap.keySet()){
			CharSet charEntry=new CharSet(entry, charCountMap.get(entry));
			sortedSet.add(charEntry);
		}
		char[] prevalArray=new char[sortedSet.size()];
		int index=0;
		for(CharSet entry:sortedSet){
			prevalArray[index++]=entry.getCharEntry().charValue();
		}
		return new String(prevalArray);
	}

	/**
	 * Return a string containing all the characters of the input string, sorted
	 * in ascending order
	 */

	@Override
	public String sortChars(String input) {
		return new String(sort(input));
	}

	/**
	 * To sort characters in an string
	 * 
	 * @param input
	 * @return
	 */
	private char[] sort(String input) {
		if (null == input || input.length() == 0) {
			return new char[0];
		}
		QuickSort sorter = new QuickSort();
		char[] charArray = new char[input.length()];
		int arrayStart = 0;
		int arrayLength = input.length();
		input.getChars(arrayStart, arrayLength, charArray, arrayStart);
		sorter.quickSort(0, arrayLength - 1, charArray);
		return charArray;
	}

	private class CharSet implements Comparable<CharSet>{
		private Character charEntry;
		Integer entryCount;
		
		public Character getCharEntry() {
			return charEntry;
		}

		public void setCharEntry(Character charEntry) {
			this.charEntry = charEntry;
		}

		public CharSet(Character charEntry,Integer entryCount){
			this.charEntry=charEntry;
			this.entryCount=entryCount;
		}
		
		@Override
		public int compareTo(CharSet that) {
			if(this.charEntry.equals(that.charEntry)){
				return this.entryCount.compareTo(that.entryCount);	
			}else {
				if(this.entryCount.equals(that.entryCount)){
					return this.charEntry.compareTo(that.charEntry);
				}else {
					return this.entryCount.compareTo(that.entryCount);
				}
			}
			
		}
		
		@Override
		public String toString() {
			return "["+charEntry+"="+entryCount+"]";
		}
		
	}
}
