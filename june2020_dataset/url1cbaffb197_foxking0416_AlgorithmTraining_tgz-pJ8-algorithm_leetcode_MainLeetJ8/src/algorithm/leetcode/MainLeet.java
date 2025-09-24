package algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.InputMap;

public class MainLeet {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testPalindromeNumber();
		
		testPalindromeString();
		testReverseWordString();
		testFindSingle();
		testFindSinglePart2();
		testGeneratePascalTriangle();
		testMaximumProductSubArray();
		testAddBinary();
		testCountAndSay();
		testLengthOfLastWord();
		testIsPalindrome();
		testPlusOne();
		testMaxProfitI();
		testMaxProfitIII();
		testNumDecodings();
		testMeger();
		testSpiralOrder();
		testGenerateMatrix();
		testCombine();
		testFindMin();
		testLadderLength();
		testValidParentheses();
		testTwoSum();
		testThreeSum();
		testFourSum();
		testGrayCode();
		testMinimumTotal();
		testSortColors();
		testLengthOfLongestSubstring();
		testFindPeakElement();
		testRestoreIpAddresses();
		testMinPathSum();
		testGenerateParenthesis();
		testPermute();
		testSetZeroes();
		testSubsets();
		testSubsetsWithDup();
		testSearchMatrix();
		testSimplifyPath();
		testDivide();
		testUniquePaths();
		testUniquePathsWithObstacles();
		testCanJump();
		testCombinationSum();
		testCombinationSum2();
		testAnagrams();
		testMultiply();
		testPow();
		testSqrt();
		testWordBreak();
		testIntToRoman();
		testSearch();
		testSearch2();
		testRemoveDuplicates();
		testExist();
		testLetterCombinations();
		testSolve();
		testSearchRange();
		testEvalRPN();
		testLongestPalindrome();
		testPartition();
		testNextPermutation();
		testAtoi();
		testRomanToInt();
		testLongestCommonPrefix();
		testConvertToTitle();
		testCompareVersion();
		testConvert();
		testGetRow();
		testJump();
		testMajorityElement();
		testpermuteUnique();
		testGetPermutation();
		testSolveSudoku();
		testIsInterleave();
		testIsMatch();
		testMinWindow();
		testNumDistinct();
		testMinDistance();
		testIsScramble();
		testLongestConsecutive();
		testCandy();
		testLongestValidParentheses();
		testMaxPoints();
		testMaximumGap();
		testTrap();
		testLargestRectangleArea();
		testSolveNQueens();
		testMinCut();
		testReverse();
		testTitleToNumber();
		testTrailingZeroes();
		testFirstMissingPositive();
		testCalculateMinimumHP();
		testLargestNumber();
		testFindRepeatedDnaSequences();
	}
	
	private static void testPalindromeNumber(){
		System.out.print("****Test Palindrome Number****" + "\n");
		int num = 10203;
		
		if(LeetTest.isPalindromeNumber(num))
		System.out.print("Number " + num + " is Palindrome Number");
		else {
			System.out.print("Number " + num + " isn't Palindrome Number" + "\n");
		}
		System.out.print("\n");
	}
	
	private static void testPalindromeString(){
		
		System.out.print("****Test Palindrome String****" + "\n");
		String str = "aabcksvregervdgdfg";
	
		System.out.print("String " + str + " maximum Palindrome length is " + LeetTest.isPalindromeString(str) + "\n");
		System.out.print("\n");
	}
	
	private static void testReverseWordString(){
		System.out.print("****Test Reverse Word****" + "\n");
		LeetTest.reverseWordsSequenceTest();
		System.out.print("\n");
		
	}
	
	private static void testFindSingle(){
		System.out.print("****Test Find Single****" + "\n");
		int answer = LeetTest.findSingleNumberTest();
		System.out.print("The Single Number is " + answer + "\n");
		System.out.print("\n");
	}
	
	private static void testFindSinglePart2(){
		System.out.print("****Test Find Single Part2****" + "\n");
		int answer = LeetTest.findSingleNumberPart2Test();
		System.out.print("The Single Number is " + answer + "\n");
		System.out.print("\n");
	}
	
	private static void testGeneratePascalTriangle(){
		System.out.print("****Test Generate Pascal Triangle****" + "\n");
		LeetTest.generatePascalTriangleTest();
		System.out.print("\n");
	}
	
	private static void testMaximumProductSubArray(){
		System.out.print("****Test Masimun Product SubArray****" + "\n");
		
		System.out.print("The maximum product is " + LeetTest.maxProductSubArrayTest() + "\n");
		System.out.print("\n");
	}
	
	private static void testAddBinary(){
		System.out.print("****Test Add Binary****" + "\n");
		
		System.out.print("The binary result is " + LeetTest.addBinaryTest("1","11") + "\n");
		System.out.print("\n");
	}
	
	private static void testCountAndSay(){
		System.out.print("****Test Count And Say****" + "\n");
		
		System.out.print("The result is " + LeetTest.countAndSay(9) + "\n");
		System.out.print("\n");
	}
	
	private static void testLengthOfLastWord(){
		System.out.print("****Test Length Of Last Word****" + "\n");
		
		System.out.print("The result is " + LeetTest.lengthOfLastWord("HelloWorld") + "\n");
		System.out.print("\n");
	}
	
	private static void testIsPalindrome(){
		System.out.print("****Test Is Palindrome String****" + "\n");
		
		System.out.print("The result is " + LeetTest.isPalindrome2(".,") + "\n");
		System.out.print("\n");
	}
	
	private static void testPlusOne(){
		System.out.print("****Test Plus One****" + "\n");
		
		int[] input = new int[]{0};
		
		int[] result = LeetTest.plusOne(input);
		String str = "";
		for(int i = 0; i < result.length; ++i){
			str = str.concat(String.valueOf(result[0]));
		}
		
		System.out.print("The result is " + str + "\n");
		System.out.print("\n");
	}
	
	private static void testMaxProfitI(){
		System.out.print("****Test Maximum Profit I****" + "\n");
		int[] input = new int[]{3,3,5,0,0,3,1,4};
		
		int profit = LeetTest.maxProfitI(input);
		System.out.print("The maximum profit is " + profit + "\n");
		System.out.print("\n");
	}
	
	private static void testMaxProfitIII(){
		System.out.print("****Test Maximum Profit III****" + "\n");
		int[] input = new int[]{2,1,4,5,2,9,7};
//		int[] input = new int[]{3,3,5,0,0,3,1,4};		
		int profit = LeetTest.maxProfitIIII(input);
		int profit2 = LeetTest.maxProfitV(2, input);
		System.out.print("The maximum profit is " + profit + "\n");
		System.out.print("\n");
	}
	
	private static void testNumDecodings(){
		System.out.print("****Test Number of Decoding****" + "\n");

		int result = LeetTest.numDecodingsIteration("1213");
		System.out.print("The number of decoding is " + result + "\n");
		System.out.print("\n");
	}
	
	private static void testMeger(){
		System.out.print("****Test Merge two sorted array****" + "\n");

		int a[] = new int[9];//{1,3,5,7};
		int b[] = new int[4];//{2,4,6,8};
		a[0]=2;
		a[1]=3;
		a[2]=5;
		a[3]=7;
		b[0]=1;
		b[1]=4;
		b[2]=6;
		b[3]=8;
		
		int result[] = LeetTest.merge(a,4,b,4);
		System.out.print("Array A is: " + "\n");
		for(int i = 0; i < 4; i++){
			System.out.print(a[i] + ", ");
		}
		System.out.print("\n");
		
		System.out.print("Array B is: " + "\n");
		for(int i = 0; i < 4; i++){
			System.out.print(b[i] + ", ");
		}
		System.out.print("\n");
		
		System.out.print("Array result is: " + "\n");
		for(int i = 0; i < 8; i++){
			System.out.print(result[i] + ", ");
		}
		System.out.print("\n");
		//System.out.print("The number of decoding is " + result + "\n");
		System.out.print("\n");
	}
	
	private static void testSpiralOrder(){
		System.out.print("****Test SpiralOrder****" + "\n");
		int matrix[][] = new int[5][5];
		matrix[0] = new int[] {1,2,3,4,5};
		matrix[1] = new int[] {6,7,8,9,10};
		matrix[2] = new int[] {11,12,13,14,15};
		matrix[3] = new int[] {16,17,18,19,20};
		matrix[4] = new int[] {21,22,23,24,25};
		
		LeetTest.spiralOrder(matrix);
	}
	
	private static void testGenerateMatrix(){
		System.out.print("****Test Generate Matrix****" + "\n");
	
		int result[][] = LeetTest.generateMatrix(5);
		for(int i = 0; i < result.length; ++i){
			System.out.print("[");
			for(int j = 0; j < result[0].length; ++j){
				System.out.print(result[i][j] + ", ");
			}
			System.out.print("]");
		}
	}
	
	private static void testCombine(){
		System.out.print("****Test Combine****" + "\n");
		LeetTest.combine(4, 3);
	}
	
	private static void testFindMin(){
		System.out.print("****Test Find Minimum****" + "\n");
		
		int array[]=new int[]{3,1,1,2,2};
		
		System.out.print("Input array is" + "\n");
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i] + ", ");
		}
		int result = LeetTest.findMin(array);
		System.out.print("\n");
		System.out.print("The minimum is " + result +"\n");

	}
	
	private static void testLadderLength(){
		System.out.print("****Test LadderLength****" + "\n");
		
		String start = "hit";
		String end = "cog";
		Set<String> dict = new HashSet<>();
		//dict.add("hog");
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("lot");
		dict.add("log");
		
		int length = LeetTest.ladderLength(start, end, dict);
		System.out.print("The minimum length is " + length +"\n");
	}
	
	private static void testValidParentheses(){
		System.out.print("****Test ValidParentheses****" + "\n");
		boolean result = LeetTest.isValidParentheses("()[]{}");
		System.out.print("The Valid Parentheses is " + result +"\n");
	}
	
	private static void testTwoSum(){
		System.out.print("****Test Two Sum****" + "\n");
		int[] input = new int[]{1,2,3,4,5,6,7,8};
		int[] result = LeetTest.twoSum(input, 15);
		System.out.print("\n");
	}
	
	private static void testThreeSum(){
		System.out.print("****Test Three Sum****" + "\n");
		
		int num[] = new int[]{-1,2,1,4};
		
		LeetTest.threeSumClosest(num, 1);

	}
	
	private static void testThreeSumCloset(){
		System.out.print("****Test Three Sum Closet****" + "\n");
		
		int num[] = new int[]{0,1,1};
		
		LeetTest.threeSum(num);

	}
	
	private static void testFourSum(){
		System.out.print("****Test Four Sum****" + "\n");
		
		int num[] = new int[]{0,0,0,0};
		
		LeetTest.fourSum(num,0);

	}
	
	private static void testGrayCode(){
		System.out.print("****Test Gray Code****" + "\n");
		List<Integer> result = LeetTest.grayCode(5);
		
		for(int i = 0; i < result.size(); ++i){
			System.out.print(result.get(i) + ",");
		}
	}
	
	private static void testMinimumTotal(){
		System.out.print("****Test Minimum Total****" + "\n");
		
		List<Integer> layer1 = new ArrayList<Integer>();
		layer1.add(2);
		
		List<Integer> layer2 = new ArrayList<Integer>();
		layer2.add(3);
		layer2.add(4);
		List<Integer> layer3 = new ArrayList<Integer>();
		layer3.add(6);
		layer3.add(5);
		layer3.add(7);
		List<Integer> layer4 = new ArrayList<Integer>();
		layer4.add(4);
		layer4.add(1);
		layer4.add(8);
		layer4.add(3);
		List<Integer> layer5 = new ArrayList<Integer>();
		layer5.add(4);
		layer5.add(1);
		layer5.add(8);
		layer5.add(3);
		layer5.add(9);

		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		triangle.add(layer1);
		triangle.add(layer2);
		triangle.add(layer3);
		triangle.add(layer4);
		triangle.add(layer5);
		
		int min =LeetTest.minimumTotal(triangle);
		System.out.print("Minumum is " + min);
	}

	private static void testSortColors(){
		System.out.print("****Test Sort Color****" + "\n");
		int[] color = new int[]{0,1,2,0,1,2,0,1,2};
		
		LeetTest.sortColors(color);
		System.out.print("result: ");
		for(int i =0; i < color.length;  ++i){
			System.out.print(color[i] + ",");
		}
		System.out.print("\n");
	}
	
	private static void testLengthOfLongestSubstring(){
		System.out.print("****Test Length Of Longest Substring****" + "\n");
		LeetTest.lengthOfLongestSubstring("wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco");//wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco   hchzvfrkmlnozjk
		System.out.print("\n");
	}
	
	private static void testFindPeakElement(){
		System.out.print("****Test Find Peak Element****" + "\n");
		int[] array = new int[]{1,2};
		
		LeetTest.findPeakElement(array);
		System.out.print("\n");
	}
	
	private static void testRestoreIpAddresses(){
		System.out.print("****Test Find Peak Element****" + "\n");
		String inputString = "010010";
		
		LeetTest.restoreIpAddresses(inputString);
		System.out.print("\n");
		
	}
	
	private static void testMinPathSum(){
		System.out.print("****Test Min Path Sum****" + "\n");
		//minPathSum
		
		System.out.print("\n");
	}
	
	private static void testGenerateParenthesis(){
		System.out.print("****Test Generate Parenthesis****" + "\n");
		List<String> result = LeetTest.generateParenthesis(3);
		
		System.out.print("Result : ");
		for(int i = 0; i < result.size(); i++){
			System.out.print(result.get(i) + " ; ");
			
		}
		
		System.out.print("\n");
	}
	
	private static void testPermute(){
		System.out.print("****Test Permute****" + "\n");
		
		int[] input = new int[]{1,-1,1,2,-1,2,2,-1};
		List<List<Integer>> result = LeetTest.permute(input);
		
		System.out.print("\n");
	}
	
	private static void testpermuteUnique(){
		System.out.print("****Test Permute Unique****" + "\n");
		int[] input = new int[]{1,2,3};
		//List<List<Integer>> result = LeetTest.permuteUnique(input);
		System.out.print("\n");
	}
	
	private static void testSetZeroes(){
		System.out.print("****Test Set Zeroes****" + "\n");
		
		int[][] input = new int[][]{{0,0,0,5},{4,3,1,4},{0,1,1,4},{1,2,1,3},{0,0,1,1}};

		LeetTest.setZeroes(input);
		
		System.out.print("\n");
		
	}
	
	private static void testSubsets(){
		System.out.print("****Test Subsets****" + "\n");
		
		int[] input = new int[]{1,2,3};
		List<List<Integer>> result = LeetTest.subsets(input);
		
		System.out.print("\n");
	}
	
	private static void testSubsetsWithDup(){
		System.out.print("****Test Subsets With Duplicate****" + "\n");
		
		int[] input = new int[]{1,2,2};
		List<List<Integer>> result = LeetTest.subsetsWithDup(input);
		
		System.out.print("\n");
	}
	
	private static void testSearchMatrix(){
		System.out.print("****Test Search Matrix****" + "\n");
		
		//int[][] input = new int[][]{{1,3,5,7}, {10,11,16,20}, {23,30,34,50}};
		int[][] input = new int[][]{{1,1}, {3,3}};
		boolean result = LeetTest.searchMatrix(input, 2);
		
		System.out.print("\n");
	}
	
	private static void testSimplifyPath(){
		System.out.print("****Test Simplify Path****" + "\n");
		String path = "/a/./b/../../c/";
		
		String result = LeetTest.simplifyPath(path);
		
		System.out.print("\n");
	}
	
	private static void testDivide() {
		System.out.print("****Test Divide****" + "\n");
		
		int result = LeetTest.divide(1100540749, -1090366779);
		
		System.out.print("\n");
	}
	
	private static void testUniquePaths(){
		System.out.print("****Test Unique Paths****" + "\n");
		
		int result = LeetTest.uniquePaths(3, 7);
		
		System.out.print("\n");
	}
	
	private static void testUniquePathsWithObstacles(){
		System.out.print("****Test Unique Paths With Obstacles****" + "\n");
		
		int input[][]= new int[][]{{1}};
		int result = LeetTest.uniquePathsWithObstacles(input);
		
		System.out.print("\n");
	}
	
	private static void testCanJump(){
		System.out.print("****Test Can Jump****" + "\n");
		
		int input[] = new int[]{3,2,1,0,4};
		boolean result = LeetTest.canJump(input);
		
		System.out.print("\n");
	}

	private static void testCombinationSum(){
		System.out.print("****Test Combination Sum****" + "\n");
		
		int[] input = new int[]{2,3,6,7};//{7,12,5,10,9,4,6,8};
		LeetTest.combinationSum(input, 7);
		System.out.print("\n");
	}
	
	private static void testCombinationSum2(){
		System.out.print("****Test Combination Sum2****" + "\n");
		
		int[] input = new int[]{10,1,2,7,6,1,5};
		LeetTest.combinationSum2(input, 8);
		System.out.print("\n");
	}
	
	private static void testAnagrams(){
		System.out.print("****Test Can Jump****" + "\n");
		
		String s1 = "abccdde";
		String s2 = "adedccb";
		String[] s = new String[]{"dog", "tcc", "god", "cat", "tca","ogd","odg"};
		LeetTest.isAnagrams(s1,s2);
		LeetTest.anagrams(s);
		System.out.print("\n");
	}
	
	private static void testMultiply() {
		System.out.print("****Test Multiply****" + "\n");
		String s1 = "123";
		String s2 = "5678";
		
		LeetTest.multiply(s1, s2);
		System.out.print("\n");
	}
	
	private static void testPow(){
		System.out.print("****Test Multiply****" + "\n");
		double result =  LeetTest.pow(2.0, 3);
		
		System.out.print("\n");
	}
	
	private static void testSqrt(){
		System.out.print("****Test Sqrt****" + "\n");
		int result =  LeetTest.sqrt(3);
		
		System.out.print("\n");
		
	}

	private static void testWordBreak(){
		System.out.print("****Test Word Break****" + "\n");
		
		String string = "catsanddog";
		Set<String> dict = new HashSet<>();
		dict.add("cat");
		dict.add("cats");
		dict.add("and");
		dict.add("sand");
		dict.add("dog");
		//dict.add("cats");
		boolean result = LeetTest.wordBreak(string, dict);
		List<String> resultList = LeetTest.wordBreakII(string, dict);
		System.out.print("\n");

	}
	
	private static void testIntToRoman(){
		System.out.print("****Test Int To Roman****" + "\n");
		
		String result = LeetTest.intToRoman(999);
		
		System.out.print("\n");
	}
	
	private static void testSearch(){
		System.out.print("****Test Search****" + "\n");
		
		//int[] input = new int[]{15,16,17,18,19,20,1,2,3,4,5,6,7,8,9,10,11,12,13,14}; //answer is 5
		int[] input = new int[]{5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4};//answer is 15
		
		int result = LeetTest.search(input, 21);
		
		System.out.print("\n");
	}
	
	private static void testSearch2(){
		System.out.print("****Test Search2****" + "\n");
		
		//int[] input = new int[]{15,16,17,18,19,20,1,2,3,4,5,6,7,8,9,10,11,12,13,14}; //answer is 5
		//int[] input = new int[]{5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4};//answer is 15
		int[] input = new int[]{1,1,3,1};
		
		boolean result = LeetTest.search2(input, 3);
		
		System.out.print("\n");
	}
	
	private static void testRemoveDuplicates(){
		System.out.print("****Test Remove Duplicates****" + "\n");
		
		int[] input = new int[]{1,1,1,1,3,3};
		int result = LeetTest.removeDuplicates(input);
		
		System.out.print("\n");
	}
	
	private static void testExist(){
		System.out.print("****Test Exist****" + "\n");
		
		char[][] board = new char[][]{{'a','b','c','e'},{'s','f','c','s'},{'a','d','e','e'}};
		//char[][] board = new char[][]{{'c','a','a'},{'a','a','a'},{'b','c','d'}};
		//char[][] board = new char[][]{{'a'}};
		
		String word = "abcb";
		boolean result = LeetTest.exist(board, word);
		
		System.out.print("\n");
	}
	
	private static void testLetterCombinations(){
		System.out.print("****Test Letter Combinations****" + "\n");
		List<String> result = LeetTest.letterCombinations("");
		
		System.out.print("\n");
	}

	private static void testSolve(){
		System.out.print("****Test Solve****" + "\n");
		//char[][] input = new char[][]{{'O'}};
		//char[][] input = new char[][]{{'X','X','X'}, {'X','O','X'}, {'X','X','X'}};
		char[][] input = new char[][]{{'O','X','X','O','X'}, {'X','O','O','X','O'}, {'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
		LeetTest.solve(input);
		
		System.out.print("\n");
	}
	
	private static void testSearchRange(){
		System.out.print("****Test Search Range****" + "\n");
		int[] input = new int[]{5,7,7,8,8,10};
		int[] result = LeetTest.searchRange(input, 8);
		
		System.out.print("\n");
	}

	private static void testEvalRPN(){
		System.out.print("****Test Eval RPN****" + "\n");
		String[] input = new String[]{"3", "-4", "+"};
		int result = LeetTest.evalRPN(input);
		
		System.out.print("\n");
	}
	
	private static void testLongestPalindrome(){
		System.out.print("****Test Longest Palindrome****" + "\n");
		
		String input = "bb";
		String result = LeetTest.longestPalindrome(input);
		
		System.out.print("\n");
	}
	
	private static void testPartition(){
		System.out.print("****Test Partition****" + "\n");
		
		String input = "abcba";
		List<List<String>> result = LeetTest.partition(input);
		
		System.out.print("\n");
	}
	
	private static void testNextPermutation(){
		System.out.print("****Test Next Permutation****" + "\n");
		
		int[] input = new int[]{100,99,98,97,96,95,94,93,92,91,90,89,88,87,86,85,84,83,82,81,80,79,78,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,58,57,56,55,54,53,52,51,50,49,48,47,46,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
		LeetTest.nextPermutation(input);
		
		System.out.print("\n");
	}
	
	private static void testAtoi(){
		System.out.print("****Test atoi****" + "\n");
		
		String input = "-+2";
		
		int result = LeetTest.atoi(input);
		
		System.out.print("\n");
	}
	
	private static void testRomanToInt(){
		System.out.print("****Test Roman To Int****" + "\n");
		
		int result = LeetTest.romanToInt("CMXCIX");
		
		System.out.print("\n");
	}
	
	private static void testLongestCommonPrefix(){
		System.out.print("****Test Longest Common Prefix****" + "\n");
		
		String[] input = new String[]{"abc","abd","ab"};
		
		String result = LeetTest.longestCommonPrefix(input);
		
		System.out.print("\n");
	}
	
	private static void testConvertToTitle(){
		
		System.out.print("****Test Convert To Title****" + "\n");
		
		String result = LeetTest.convertToTitle(29);
		
		System.out.print("\n");
	}
	
	private static void testCompareVersion(){
		System.out.print("****Test Compare ersion****" + "\n");
		
		String version1 ="1";
		String version2 ="1.0.1";
		
		int result = LeetTest.compareVersion(version1, version2);
		
		System.out.print("\n");
		
	}
	
	private static void testConvert(){
		System.out.print("****Test Convert****" + "\n");
		
		String result = LeetTest.convert("abcdefghijklmnopqrstu", 2);
		System.out.print("\n");
	}
	
	private static void testGetRow(){
		System.out.print("****Test Get Row****" + "\n");
		
		List<Integer> result = LeetTest.getRow(1);
		
		System.out.print("\n");
	}
	
	private static void testJump(){
		System.out.print("****Test Get Row****" + "\n");
		int[] input = new int[]{2,3,1,1,4}; 
		
		int result = LeetTest.jump(input);
		
		System.out.print("\n");
	}
	
	private static void testMajorityElement(){
		System.out.print("****Test Majority Element****" + "\n");
		int[] input = new int[]{8,8,7,7,7};
		
		int result = LeetTest.majorityElement(input);
		System.out.print("\n");
	}
	
	private static void testGetPermutation(){
		System.out.print("****Test Get Permutation****" + "\n");
		
		String result = LeetTest.getPermutation(5, 24);
		System.out.print("\n");
	}
	
	private static void testSolveSudoku(){
		System.out.print("****Test Solve Sudoku****" + "\n");
		
		char[][] board = new char[][]{{'.', '.', '9', '7', '4', '8', '.', '.', '.'},
									  {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
									  {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
									  {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
									  {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
									  {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
									  {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
									  {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
									  {'.', '.', '.', '2', '7', '5', '9', '.', '.'}};
		
		LeetTest.solveSudoku(board);
		System.out.print("\n");
		
	}

	private static void testIsInterleave(){
		System.out.print("****Test Is Interleave****" + "\n");
		String s1 = "aabcc";
		String s2 = "dbbca";
		String s3 = "aadbbcbcac";
		boolean result = LeetTest.isInterleave(s1, s2, s3);
		
		System.out.print("\n");
	}
	
	private static void testIsMatch(){
		System.out.print("****Test Is Match****" + "\n");
		
		String s = "aa";
		String p = "a";
		
		boolean result = LeetTest.isMatch(s, p);
		
		System.out.print("\n");
	}
	
	private static void testMinWindow(){
		System.out.print("****Test Min Window****" + "\n");
		String s = "ADOBECODEBANC";
		String t = "ABC";
		
		String result = LeetTest.minWindow(s, t);
		System.out.print("\n");
	}
	
	private static void testNumDistinct(){
		System.out.print("****Test Num Distinct****" + "\n");
		String s = "ADOBECODEBANC";
		String t = "ABC";
		
		int result = LeetTest.numDistinct(s, t);
		
		System.out.print("\n");
	}
	
	private static void testMinDistance(){
		System.out.print("****Test Min Distance****" + "\n");
		
		String s = "ABD";
		String t = "ABC";
		int result = LeetTest.minDistance(s, t);
		System.out.print("\n");
	}
	
	private static void testIsScramble(){
		System.out.print("****Test Min Distance****" + "\n");
		String s1 = "abcdefghijklmnopq";
		String s2 = "efghijklmnopqcadb";
		boolean result = LeetTest.isScramble(s1, s2);
		System.out.print("\n");
	}
	
	private static void testLongestConsecutive(){
		System.out.print("****Test Longest Consecutive****" + "\n");
		
		int[] num = new int[]{4,0,-4,-2,2,5,2,0,-8,-8,-8,-8,-1,7,4,5,5,-4,6,6,-3};
		int result = LeetTest.longestConsecutive(num);
		System.out.print("\n");
	}
	
	private static void testCandy(){
		System.out.print("****Test Candy****" + "\n");
		
		int[] ratings = new int[]{1,2};
		LeetTest.candy(ratings);
		
		System.out.print("\n");
	}
	
	private static void testLongestValidParentheses(){
		System.out.print("****Test LongestValidParentheses****" + "\n");
		String input = "()()"; //(()() 4; ()(() 2;  (()(((()  2
		
		int result = LeetTest.longestValidParentheses(input);
		
		System.out.print("\n");
	}
	
	/*class Point {
	      int x;
	      int y;
	      Point() { x = 0; y = 0; }
	      Point(int a, int b) { x = a; y = b; }
	  }*/
	
	private static void testMaxPoints(){
		System.out.print("****Test Max Points****" + "\n");
		Point p1 = new Point(0,0);
		Point p2 = new Point(-1,-1);
		Point p3 = new Point(2,2);
		
		algorithm.leetcode.Point[] pointCollection = new Point[]{p1,p2,p3};
		
		int result = LeetTest.maxPoints(pointCollection);
		
		System.out.print("\n");
	}
	
	private static void testMaximumGap(){
		System.out.print("****Test Maximum Gap****" + "\n");
		
		int[] num = new int[]{1,1,1,1,1,5,5,5,5,5};
		int result = LeetTest.maximumGap(num);
		
		System.out.print("\n");
	}
	
	private static void testTrap(){
		System.out.print("****Test Trap****" + "\n");
		int[] input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
		int result = LeetTest.trap(input);
		
		System.out.print("\n");
	}
	
	private static void testLargestRectangleArea(){
		System.out.print("****Test Largest Rectangle Area****" + "\n");
		//int[] input = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136};
		//int[] input = new int[]{0,1,2,3,4,5,6,7,8,9,10};
		int[] input = new int[]{2,1,5,6,2,3};
		int result = LeetTest.largestRectangleArea(input);
		int resultFast = LeetTest.largestRectangleAreaFast(input);
		System.out.print("\n");
	}
	
	private static void testSolveNQueens(){
		System.out.print("****Test Solve NQueens****" + "\n");
		List<String[]> result = LeetTest.solveNQueens(5);
		int solutionNum = LeetTest.totalNQueens(5);
		System.out.print("\n");
	}
	
	private static void testMinCut(){
		System.out.print("****Test Min Cut****" + "\n");
		//int result = LeetTest.minCut("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		int result = LeetTest.minCut("bcca");
		System.out.print("\n");
	}
	
	private static void testReverse(){
		System.out.print("****Test Reverse****" + "\n");
		
		int result = LeetTest.reverse(-321);
		
		System.out.print("\n");
	}
	
	private static void testTitleToNumber(){
		System.out.print("****Test Title To Number****" + "\n");
		int result = LeetTest.titleToNumber("AB");
		System.out.print("\n");
	}
	
	private static void testTrailingZeroes(){
		System.out.print("****Test Title To Number****" + "\n");
		int result = LeetTest.trailingZeroes(1808548329);
		System.out.print("\n");
	}
	
	private static void testFirstMissingPositive(){
		System.out.print("****Test First Missing Positive****" + "\n");
		int[] A = new int[]{3,7,-1,1};
		
		int result = LeetTest.firstMissingPositive(A);
		System.out.print("\n");
	}
	
	private static void testCalculateMinimumHP(){
		System.out.print("****Test Calculate Minimum HP****" + "\n");
		int[][] A = new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}};
		
		int result = LeetTest.calculateMinimumHP(A);
		System.out.print("\n");
	}
	
	private static void testLargestNumber(){
		System.out.print("****Test Largest Number****" + "\n");
		int[] A = new int[]{3, 30, 34, 5, 9};
		
		String result = LeetTest.largestNumber(A);
		System.out.print("\n");
	}
	
	private static void testFindRepeatedDnaSequences(){
		System.out.print("****Test Find Repeated Dna Sequences****" + "\n");
		
		
		List<String> result = LeetTest.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT");
		System.out.print("\n");
	}
	
}
