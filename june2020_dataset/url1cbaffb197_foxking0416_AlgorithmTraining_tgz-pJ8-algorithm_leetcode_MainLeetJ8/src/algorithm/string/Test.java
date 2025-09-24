package algorithm.string;

public class Test {

	//Cracking the code interview 1.1
	//Determine if a string has all unique characters.
	//Solution1  O(n)
	public static boolean determineUniqueChar(String str){
		boolean table[] = new boolean[256];
		
		for(int i = 0; i < str.length(); ++i){
			int index = str.charAt(i);
			if(table[index] == true)
				return false;
			table[index] = true;
		}
		return true;
	}
	
	//Cracking the code interview 1.1
	//Determine if a string has all unique characters.
	//Solution2  O(n^2)
	public static boolean determineUniqueChar2(String str){
		
		for(int i = 0; i < str.length(); ++i){
			for(int j = i+1; j < str.length(); ++j){
				char charToCompare = str.charAt(i);
				char charBeCompared = str.charAt(j);
				if(charToCompare == charBeCompared)
					return false;
			}
		}
		return true;
	}
	
	//Cracking the code interview 1.2
	//Reverse the input string. However, this should be implemented in C++
	//Solution2  O(n)
	public static String reverseCString(String str) {
		
		String strReversed = "";
	
		for(int i = str.length()-1; i >= 0; --i){
			Character temp = str.charAt(i);
			strReversed = strReversed.concat(temp.toString());
		}
		strReversed = strReversed.concat(" ");
		
		return strReversed;
	}
	

}
