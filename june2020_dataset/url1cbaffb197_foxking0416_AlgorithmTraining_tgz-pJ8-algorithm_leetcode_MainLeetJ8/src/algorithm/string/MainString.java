package algorithm.string;

public class MainString {
	public static void main(String[] args) {
		testLCS();
		testTransform();
		testMatching();
		testCrackInterview();
	}
	
	//Find out the longest common subsequence
	private static void testLCS(){
		System.out.print("****LCS Test****" + "\n");
		String strX = "CATCGA";
		String strY = "GTACCGTCA";
		
		LCS lcs = new LCS(strX, strY);
		lcs.computeLCSTable();
		
		String strLCS = lcs.findLCS("", strX.length(), strY.length());
		
		System.out.print("LCS: " + strLCS);
		System.out.print("\n");
		System.out.print("\n");
	}
	
	
	//Test the function that transform the strX to strY
	//The lower cost means these two string are more similar
	private static void testTransform(){

		System.out.print("****Transform Test****" + "\n");
		
		String strX = "ACAAGC";
		String strY = "CCGT";
		
		Transform transform = new Transform(strX, strY);
		int cost = transform.computeTransformTable();
		System.out.print("Cost is " + cost + "\n");
		
		String strTransform = transform.assembleTransformation("", strX.length(), strY.length());
		System.out.print("String after transform: " + strTransform + "\n");
		System.out.print("\n");
	}
	
	
	private static void testMatching(){
		System.out.print("****Matching Test****" + "\n");
		
		String text = "AAAABCAABCAA";
		String pattern = "ABC";
		
		Matching match = new Matching(text, pattern);
		match.createNextStateTable();
		match.findMatch();
		System.out.print("\n");
	}
	
	private static void testCrackInterview(){
		System.out.print("****Cracking Interview Test****" + "\n");
		boolean isUnique =	Test.determineUniqueChar("asbaf");
		System.out.print("Test Unique: " + isUnique + "\n");
		
		boolean isUnique2 =	Test.determineUniqueChar2("aabvf");
		System.out.print("Test Unique2: " + isUnique2 + "\n");
		
		String strReversed = Test.reverseCString("abcde");
		System.out.print("Test Reversed String: " + strReversed + "\n");
	}
	
	
}
