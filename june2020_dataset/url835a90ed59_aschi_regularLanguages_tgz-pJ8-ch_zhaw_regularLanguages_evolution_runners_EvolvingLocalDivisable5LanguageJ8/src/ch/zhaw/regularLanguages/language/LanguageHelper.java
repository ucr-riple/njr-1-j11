package ch.zhaw.regularLanguages.language;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;

public class LanguageHelper {
	public static CharArrayWrapper generateSymbolList(String s){
		return new CharArrayWrapper(s.toCharArray());
	}
	
	
	public static ProblemSet<CharArrayWrapper, BooleanWrapper> generateLanguageProblemSet(String regexp, Set<CharArrayWrapper> words){
		List<BooleanWrapper> expectedResultList = new LinkedList<BooleanWrapper>();
		List<CharArrayWrapper> inputList = new LinkedList<CharArrayWrapper>();
		
		for(CharArrayWrapper cw : words){
			inputList.add(cw);
			expectedResultList.add(new BooleanWrapper(new String(cw.getData()).matches(regexp)));
		}
		return new ProblemSet<CharArrayWrapper, BooleanWrapper>(inputList, expectedResultList);
	}
	
	public static Set<CharArrayWrapper> generateAllWords(char[] alphabet, int maxLength){
		Set<CharArrayWrapper> words = new HashSet<CharArrayWrapper>();
		
		//TODO: Doesnt work properly
		
		for(int i = 0; i <= maxLength;i++){
			findWords(alphabet, words, new StringBuffer(), i);
		}
		/*
		def printNCharacterPasswords (prefix, num):
		    if num == 0:
		        print prefix
		        return
		    for letter in ('a', 'b', 'c', 'd', 'e'):
		        printNCharacterPasswords (prefix + letter, num - 1)

		printNCharacterPasswords ("", 2)
		 */
		return words;
	}
	
	public static void findWords(char[] alphabet, Set<CharArrayWrapper> words, StringBuffer prefix, int num) {
	    if (num == 0) {
	        words.add(new CharArrayWrapper(prefix.toString().toCharArray()));
	        return;
	    }
	    for (Character c : alphabet){
	    	findWords(alphabet, words, prefix.append(c), num-1);
	    }
	}
}
