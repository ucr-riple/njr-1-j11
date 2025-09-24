package ch.zhaw.regularLanguages.language;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ch.zhaw.regularLanguages.evolution.problems.ProblemGenerator;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.helpers.Tuple;


public class WordProblemGenerator implements ProblemGenerator<CharArrayWrapper, BooleanWrapper> {
	private char[] alphabet;
	private int maxWordLength;
	private String regexp;
	private Random rnd = new Random();
	
	public WordProblemGenerator(char[] alphabet, int maxWordLength, String regexp){
		this.alphabet = alphabet;
		this.maxWordLength = maxWordLength;
		this.regexp = regexp;
	}
	
	@Override
	public Tuple<CharArrayWrapper, BooleanWrapper> generateProblem() {
		int length = rnd.nextInt(maxWordLength);
		
		char[] rv = new char[length];
		
		for(int i = 0; i < length;i++){
			rv[i] = alphabet[rnd.nextInt(alphabet.length)];
		}
		
		return new Tuple<CharArrayWrapper, BooleanWrapper>(new CharArrayWrapper(rv), new BooleanWrapper(new String(rv).matches(regexp)));
	}
	
	public ProblemSet<CharArrayWrapper, BooleanWrapper> generateProblemSet(int noProblems, boolean includeEmptyString){
		List<BooleanWrapper> expectedResultList = new LinkedList<BooleanWrapper>();
		List<CharArrayWrapper> inputList = new LinkedList<CharArrayWrapper>();
		
		if(includeEmptyString){
			inputList.add(new CharArrayWrapper(new char[0]));
			expectedResultList.add(new BooleanWrapper(new String("").matches(regexp)));
		}
		
		for(int i = 0; i < (includeEmptyString ? noProblems-1 : noProblems);i++){
			Tuple<CharArrayWrapper, BooleanWrapper> p = null;
			//avoid duplicates
			do{
				p = this.generateProblem();
			}while(inputList.contains(p.getFirst()));
			
			inputList.add(p.getFirst());
			expectedResultList.add(p.getSecond());
		}
		
		return new ProblemSet<CharArrayWrapper, BooleanWrapper>(inputList, expectedResultList);
	}
}
