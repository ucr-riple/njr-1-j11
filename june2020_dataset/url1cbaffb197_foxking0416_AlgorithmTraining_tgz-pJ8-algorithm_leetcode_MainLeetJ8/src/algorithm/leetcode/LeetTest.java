package algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.security.auth.kerberos.KerberosKey;

import datastructure.binarytree.TreeNode;

class Point {
	      int x;
	      int y;
	      Point() { x = 0; y = 0; }
	      Point(int a, int b) { x = a; y = b; }
	  }


public class LeetTest {

	
	public static boolean isPalindromeNumber(int num){
		int power = 1;
		int length = 1;
		while(num / power >= 10){
			power *= 10;
			++length;
		}
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		int remain = num;
		while(power >= 1){
			int firstNum = remain / power;
			array.add(firstNum);
			remain %= power;
			power /= 10;
		}
		
		for(int i = 0; i < length / 2; ++i){
			if(array.get(i) != array.get(length-1-i))
				return false;
		}
		
		return true;
	}
	
	public static boolean isPalindromeNumber2(int num){
		int power = 1;
		while(num / power >= 10){
			power *= 10;
		}

		while(power >= 1){
			int firstNum = num / power;
			int lastNum = num % 10;
			if(firstNum != lastNum)
				return false;
			
			num = (num % power) / 10;
			power /= 100;
			
		}
		
		return true;
	}
	
	public static int isPalindromeString(String str){
		int maxLength = 1;
		String maxStr = str.substring(0,1);
		
		for(int i = 0; i < str.length()-1; ++i){
			String s1 = expand(str, i, i);
			if(s1.length() > maxLength){
				maxLength = s1.length();
				maxStr = s1;
			}
			String s2 = expand(str, i, i+1);
			if(s2.length() > maxLength){
				maxLength = s2.length();
				maxStr = s2;
			}
			
		}
		return maxLength;

	}
	
	private static String expand(String str, int s, int e){
		String subStr = str.substring(s, e + 1);
		String returnStr = subStr;
		while(s >= 0 && e < str.length() && subStr.charAt(0) == subStr.charAt(subStr.length()-1)){
			returnStr = subStr;
			subStr = str.substring(s, e + 1);
			--s;
			++e;
		}
		
		return returnStr;
	}
	
	public static void reverseWordsSequenceTest(){
		String str = "hello world!";
		System.out.print("Initial word string is \"" + str + "\"\n");
		str = reverseWordsSequence(str);
		System.out.print("Reversed word string is \"" + str + "\"\n");

	}
	
	private static String reverseWordsSequence(String s){
		String reverStr = "";
		int i = s.length()-1;
		
		
		while( i >= 0){
			if(s.charAt(i) == ' '){
				if(i == s.length()-1){
					s = s.substring(0, s.length()-1);
					i = s.length() -1;
				}
				else{
					String word = s.substring(i+1, s.length());
					if(reverStr.isEmpty())
						reverStr = reverStr.concat(word);
					else {
						reverStr = reverStr + " " +word;
					}
					s = s.substring(0, i);
					i = s.length() - 1;
				}
			}
			else {
				--i;
			}
		}
		
		if(!s.isEmpty()){
			if(reverStr.isEmpty())
				reverStr = reverStr.concat(s);
			else
				reverStr = reverStr + " " +s;
		}
		
		return reverStr;
	}
	
	public static int findSingleNumberTest(){
		
		int[] arr = {5,9,3,4,1,5,2,4,7,3,2,9,7};
		int[] arr2 = {5,9,3,4,1,5,2,4,7,3,2,9,7};
		int[] arr3 = {5,9,3,4,1,5,2,4,7,3,2,9,7};
		int number = findSingleNumber(arr);
		int number2 = findSingleNumberSolution2(arr2);
		int number3 = findSingleNumberSolution3(arr3);
		
		return number2;
	}
	
	public static int findSingleNumberPart2Test(){
		
		int[] arr = {5,2,9,7,3,4,9,1,5,3,4,2,4,5,7,3,2,9,7};
		int number = findSingleNumberPart2Solution2(arr);
		
		return number;
	}
	
	private static int findSingleNumber(int[] A) {
        
		Arrays.sort(A);
		
		for(int i = 0; i < A.length; i += 2){
			if(A[i] != A[i+1])
				return A[i];
		}
		return 0;
    }
	
	private static int findSingleNumberSolution2(int[] A) {
		int result = 0;
		
		Hashtable<Integer, Boolean> myHashtable = new Hashtable<>();
		for(int i = 0; i < A.length; ++i){
			if(myHashtable.containsKey(A[i]) != true)
				myHashtable.put(A[i], true);
			else {
				myHashtable.put(A[i], false);
			}
		}
		
		for(Integer i : myHashtable.keySet()){
			if(myHashtable.get(i) == true)
				return i;
		}
		
		return result;
	}
	
	private static int findSingleNumberSolution3(int[] A) {
		int result = 0;
	    for (int i = 0; i< A.length; i++){
	        result ^=A[i];
	    }
	    
	    return result;
	}
	
	private static int findSingleNumberPart2(int[] A){
		Hashtable<Integer, Integer> myHashtable = new Hashtable<>();
		for(int i = 0; i < A.length; ++i){
			if(myHashtable.containsKey(A[i]) != true){
				myHashtable.put(A[i], 1);
			}
			else{
				if(myHashtable.get(A[i]) == 1)
					myHashtable.replace(A[i], 2);
				else 
					myHashtable.replace(A[i], 3);
				
			}
		}
		
		for(Integer i : myHashtable.keySet()){
			if(myHashtable.get(i) == 1)
				return i;
		}
		return 0;
	}
	
	private static int findSingleNumberPart2Solution2(int[] A){
		
		int count[] = new int[32];
		int result = 0;
		
		for(int i = 0; i < 32; ++i){
			for(int j = 0; j < A.length; ++j){
				if((A[j] >> i & 1) == 1){
					count[i]++;
				}
			}
			
			int bit = count[i] % 3 << i;
			result |= bit;
		}
		
		return result;
	}
	
	public static void generatePascalTriangleTest(){
		List<List<Integer>> PascalTriangle = generatePascalTriangleInLoop(6);
		for(int i = 0; i < PascalTriangle.size(); ++i){
			List lineList = PascalTriangle.get(i);
			System.out.print("[");
			for(int j = 0; j < lineList.size()-1; ++j){
				System.out.print(lineList.get(j) + ", ");
			}
			System.out.print(lineList.get(lineList.size()-1));
			System.out.print("]" + "\n");
		}
		
	}
	
	private static List<List<Integer>> generatePascalTriangleRecur(int numRows) {
		 
		List<List<Integer>> lineList = new ArrayList<List<Integer>>();
//		List<Integer> curList = new ArrayList<Integer>();
//		 
//		if(numRows == 1){
//			curList.add(1);
//			lineList.add(curList);
//			return lineList;
//		}
//		else{
//			lineList = generatePascalTriangle(numRows - 1);
//			List<Integer> lastList = lineList.get(lineList.size() - 1);
//			curList.add(1);
//			for(int i = 0; i < lastList.size() - 1; ++i){
//				curList.add(lastList.get(i) + lastList.get(i+1));
//			}
//			curList.add(1);
//			lineList.add(curList);
			return lineList;
//		 }
	}
	
	private static List<List<Integer>> generatePascalTriangleInLoop(int numRows) {
		
		List<List<Integer>> lineList = new ArrayList<List<Integer>>();
		
		for(int i = 0; i < numRows; ++i){
			List<Integer> curList = new ArrayList<Integer>();
			if(i == 0){
				curList.add(1);
				lineList.add(curList);
			}
			else{
				List<Integer> lastList = lineList.get(i-1);
				curList.add(1);
				for(int j = 0; j < lastList.size() - 1; ++j){
					curList.add(lastList.get(j) + lastList.get(j+1));
				}
				curList.add(1);
				lineList.add(curList);
			}
		}
		
		return lineList;
	}
	
	public  static int maxProductSubArrayTest(){
		
		int[] arr = {5,9,3,4,-1,5,2,4,7,-1,3,2,9,7};
		return maximumProductSubArraySolution(arr);
		
	}
	
	private static int maximumProductSubArray(int[] A){
		
		int value = 0;
		int negativeCount = 0;
		int submax = A[0];
		int submin = A[0];
		int realmax = A[0];
		
		for (int i = 1; i < A.length; i++) {
			if(A[i] > 0){
				submax = Math.max(submax * A[i], A[i]);
				submin = Math.min(submin * A[i], A[i]);
				
			}else{
				int temp = submax;
				submax = Math.max(submin * A[i], A[i]);
				submin = Math.min(temp * A[i], A[i]);
			}
			
			
			realmax = Math.max(realmax, submax);
		}
		
		return 0;
	}
	
	private static int maximumProductSubArraySolution(int[] A){
		if (A.length == 0) {
	        return 0;
	    }
		
		int maxherepre = A[0];
	    int minherepre = A[0];
	    int maxsofar = A[0];
	    int maxhere, minhere;
	    
	    for (int i = 1; i < A.length; i++) {
	        maxhere = Math.max(Math.max(maxherepre * A[i], minherepre * A[i]), A[i]);
	        minhere = Math.min(Math.min(maxherepre * A[i], minherepre * A[i]), A[i]);
	        maxsofar = Math.max(maxhere, maxsofar);
	        maxherepre = maxhere;
	        minherepre = minhere;
	    }
	    return maxsofar;
	    
	}
	
	public static String addBinaryTest(String strA, String strB){
		
		int currentBit = 0;
		int nextBit = 0;
		ArrayList<Integer> res = new ArrayList<>();
		
		int index = 0;
		while(index < strA.length() || index < strB.length()){
			int bitA = 0;
			if(index < strA.length())
				bitA = Character.getNumericValue(strA.charAt(strA.length() - 1 - index));
			int bitB = 0;
			if(index < strB.length())
				bitB = Character.getNumericValue(strB.charAt(strB.length() - 1 - index));
			
			int add = bitA + bitB + nextBit;
			currentBit = add & 1;
			nextBit = (add >> 1) & 1;
			
			res.add(currentBit);
			index++;
		}
		
		if(nextBit == 1)
			res.add(nextBit);
		

		String str = "";
		for(int i = 0; i < res.size(); i++){
			str = str.concat(res.get(res.size() - 1 - i).toString());
		}
		return str;
	}

    public static String countAndSay(int n) {
        
    	String result = "";
    	String str = "1";
    	int count = 0;
    	char character = str.charAt(0);
    	for(int j = 1; j < n; ++j){
	    	result = "";
	    	character = str.charAt(0);
	    	for(int i = 0; i < str.length(); ++i){    		
	    			
	    		
	    		if(str.charAt(i) == character){
	    			++count;
	    		}
	    		else{
	    			result = result.concat(String.valueOf(count)).concat(String.valueOf(str.charAt(i-1)));
	    			count = 1;
	    			character = str.charAt(i);
	    		}
	    		
    			if(i == str.length() - 1){
	    			result = result.concat(String.valueOf(count)).concat(String.valueOf(str.charAt(i)));
	    			count = 0;
    			}
	    	}
	    	str = result;

    	}
    	return String.valueOf(str);
    }
    
    public static int lengthOfLastWord(String s) {

    	int indexOfNotSpaceChar = -1;
        for(int i = s.length()-1; i >= 0; i--){
            if(s.charAt(i) != ' '){

            	indexOfNotSpaceChar = i;
            	break;
            }
        }
        s = s.substring(0, indexOfNotSpaceChar + 1);

    	
    	if(s.isEmpty())
    		return 0;
    	
    	int index = s.lastIndexOf(" ");
        return s.length() - index - 1;
    }
    
    public static boolean isPalindrome(String s) {
    	//"A man, a plan, a canal: Panama"
    	if(s.isEmpty() == true)
    		return true;
    	
    	int index = s.length() - 1;
    	
    	while(index >= 0){
    		char c = s.charAt(index);
    		int ascii = (int) c;
    		if(!(ascii >= 65 && ascii <= 90) && !(ascii >= 97 && ascii <= 122) && !(ascii >= 48 && ascii <= 57)){
    			
    			if(index == s.length() - 1){
    				s = s.substring(0, index);
    			}else{
        			String s1 = s.substring(0, index);
        			String s2 = s.substring(index + 1, s.length());
        			s = s1.concat(s2);  				
    			}
    		}
    		--index;
    	}
    	
    	if(s.isEmpty() == true)
    		return true;
    	
    	index = 0;
    	while(index < s.length() / 2){
    		if(( (int)s.charAt(index) != (int)s.charAt(s.length() - 1 - index)) && ( Math.abs( (int)s.charAt(index) - (int)s.charAt(s.length() - 1 - index))) != 32){
    			return false;
    		}
    		index++;
    	}
    	
        return true;
    }
    
    public static boolean isPalindrome2(String s) {
    	if(s.isEmpty() == true)
    		return true;
    	
    	int forwardIndex = 0;
    	int backwardIndex = s.length() - 1;
    	
    	while(forwardIndex < backwardIndex){
    		int forwardC = (int) s.charAt(forwardIndex);
    		int backwardC = (int) s.charAt(backwardIndex);
    		
    		while(!(forwardC >= 65 && forwardC <= 90) && !(forwardC >= 97 && forwardC <= 122) && !(forwardC >= 48 && forwardC <= 57)){
    			forwardIndex++;
    			if(forwardIndex >= s.length())
    				break;
    			forwardC = (int) s.charAt(forwardIndex);
    		}
    		
    		while(!(backwardC >= 65 && backwardC <= 90) && !(backwardC >= 97 && backwardC <= 122) && !(backwardC >= 48 && backwardC <= 57)){
    			backwardIndex--;
    			if(backwardIndex < 0)
    				break;
    			backwardC = (int) s.charAt(backwardIndex);
    		} 		
    		if(forwardIndex >= backwardIndex)
    			break;
    		
    		
    		if(Math.abs(forwardC - backwardC) != 32 && Math.abs(forwardC - backwardC) != 0){
    			return false;
    		}
    		
    		forwardIndex++;
    		backwardIndex--;
    	}
    	
    	return true;
    }

	public static int reverse(int x) {
	    int result = 0;

	    while (x != 0)
	    {
	        int tail = x % 10;
	        int newResult = result * 10 + tail;
	        if ((newResult - tail) / 10 != result)//This line is important, used to judge the overflow
	        	return 0; 
	        
	        result = newResult;
	        x = x / 10;
	    }

	    return result;
        
    }
	
    public static int[] plusOne(int[] digits) {
        
    	int index = digits.length - 1;
    	List<Integer> array = new ArrayList<>();
    	int last = 1;
    	while(index >= 0){
    		int add = digits[index] + last;
    		if(add >= 10){
    			array.add(add - 10);
    			last = 1;
    			if(index == 0)
    				array.add(1);
    		}
    		else{
    			array.add(add);
    			last = 0;
    		}
			--index;
    	}
    	int[] result = new int[array.size()];
    	
    	for(int i = 0 ; i < array.size(); ++i){
    		result[i] = array.get(array.size() - 1 -i );
    		
    	}
    	
    	return result;
    }
    
    public int removeElement(int[] A, int elem) {
    	if(A.length == 0)
    		return 0;
    	
    	
    	int length = A.length;
    	int index = 0;
    	
    	for(int i = 0; i < A.length; i++){
    		if(A[i] == elem){
    			length--;
    		}
    		else{
    			A[index] = A[i];
    			index++;
    		}
    	}
    	return length;
    }

    public static int maxProfitI(int[] prices) {

    	int profit = 0;
    	int maxProfit = 0;
    	for(int i = 0; i < prices.length-1; ++i){
    		
   
    		profit += prices[i + 1] - prices[i];
    		if(profit < 0)
    			profit = 0;
    		if(profit > maxProfit)
				maxProfit = profit;
    	}
    	
        return maxProfit;
    }
    
    public static int maxProfitII(int[] prices) {
    	
    	int profit = 0;
    	for(int i = 0; i < prices.length-1; ++i){
    		if(prices[i + 1] > prices[i])
    			profit += prices[i + 1] - prices[i];
    		
    	}
        return profit;
    }
    
    public static int maxProfitIII(int[] prices) {

    	int maxLose = 0;

    	int valley = prices[0];
    	int peak = prices[prices.length-1];

    	int historyProfit[] = new int[prices.length];
    	int futureProfit[] = new int[prices.length];
    	int maxProfit = 0;
    	for(int i = 0; i < prices.length; ++i){
    		valley = Math.min(valley, prices[i]);
    		
    		if(i > 0){
    			historyProfit[i] = Math.max(historyProfit[i-1], prices[i]- valley);
    		}
    	}
    	
    	for(int i = prices.length -1; i >= 0; --i ){
    		peak = Math.max(peak, prices[i]);
    		
    		if(i < prices.length -1){
    			futureProfit[i] = Math.max(futureProfit[i+1],peak-prices[i]);
    		}
    		maxProfit = Math.max(maxProfit,historyProfit[i]+futureProfit[i]);
    	}
    	
        return maxLose;
    }
    
    public static int maxProfitIIII(int[] prices) {
    	
        if(prices.length == 0)
            return 0;
    	
    	int maxProfit = 0;

    	int historyProfit[] = new int[prices.length];
    	int futureProfit[] = new int[prices.length];
    	int maxProfitForward = 0;
    	int profitForward = 0;
    	int maxProfitBackward = 0;
    	int profitBackward = 0;
    	for(int i = 0; i < prices.length-1; ++i){
    		
    		   
    		profitForward += prices[i + 1] - prices[i];
    		if(profitForward < 0)
    			profitForward = 0;
    		else if(profitForward > maxProfitForward)
				maxProfitForward = profitForward;
    		
    		historyProfit[i+1] = maxProfitForward;
    	}
    	

    
    	
    	for(int i = prices.length -1; i >= 1; --i ){
    		
    		profitBackward += prices[i] - prices[i-1];
    		
    		if(profitBackward < 0)
    			profitBackward = 0;
    		else if(profitBackward > maxProfitBackward)
    			maxProfitBackward = profitBackward;
    		futureProfit[i-1] = maxProfitBackward;

    	}
    	
    	maxProfit = futureProfit[0] + historyProfit[0];
    	for(int i = 1; i < prices.length; ++i){
    		if(futureProfit[i] + historyProfit[i] > maxProfit)
    			maxProfit = futureProfit[i] + historyProfit[i];
    	}
    	
        return maxProfit;
    }

    public static int maxProfitV(int k, int[] prices) {
        int len = prices.length;
        if (k >= len / 2) return quickSolve(prices);

        int[][] t = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {
            int tmpMax =  -prices[0];
            for (int j = 1; j < len; j++) {
                t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);
                tmpMax =  Math.max(tmpMax, t[i - 1][j - 1] - prices[j]);
            }
        }
        return t[k][len - 1];
    }


    private static int quickSolve(int[] prices) {
        int len = prices.length;
        int profit = 0;
        for (int i = 1; i < len; i++)
            // as long as there is a price gap, we gain a profit.
            if (prices[i] > prices[i - 1]) 
            	profit += prices[i] - prices[i - 1];
        return profit;
    }
    
    public static int canCompleteCircuit(int[] gas, int[] cost) {
    	
    	int totalGas = 0;
    	int totalCost = 0;
    	int maxCost = 0;
    	int maxCostIndex = 0;
    	
    	for(int i = 0; i < gas.length; ++i){
    		totalGas += gas[i];
    		totalCost += cost[i];
    		
    	    if(cost[i] > maxCost){
    	    	maxCost = cost[i];
    	    	maxCostIndex = i;
    	    }
    	}
    	
    	if(totalCost > totalGas)
    		return -1;
    	
    	return maxCostIndex+1;
    	
    }
    
    public static int numDecodingsRecursion(String s) {
    	
    	int result = 0;
    	if(s.length() >= 1){
    		String s1 = s.substring(0, 1);
    		if(s1.equals("0") == true)
    			return result;
        	String s1Rest = s.substring(1, s.length());
        	if(s1Rest.isEmpty() == false && s1Rest.charAt(0) != '0')
        		result += numDecodingsRecursion(s1Rest);
        	else 
        		result += 1;
    	}
    	if(s.length() >= 2){
    		String s2 = s.substring(0, 2);
        	String s2Rest = s.substring(2, s.length()); 	
        	int s2Int = Integer.parseInt(s2);
        	if(s2Rest.isEmpty() == false && s2Int < 27 && s2Rest.charAt(0) != '0')
        		result += numDecodingsRecursion(s2Rest);
        	else if(s2Rest.isEmpty() == true && s2Int < 27)
        		result += 1;
    	}

        return result;
    }
    
    public static int numDecodingsIteration(String s) {
        if(s.length() == 0 || s.charAt(0) == '0')
    		return 0;
    	int cur_2 = 1;
    	int cur_1 = 1;
    	int cur = 0;
    	
    	for(int i = 1; i < s.length(); i++){
    	    if(s.charAt(i) != '0')
    	        cur += cur_2;
    	    if(s.charAt(i-1) == '1' || (s.charAt(i-1)=='2'&& s.charAt(i)<'7')) 
    	        cur += cur_1;
    	    
    	    cur_1 = cur_2;
    	    cur_2 = cur;
    	    cur = 0;
    	}
    	
    	return cur_2;
    }

    public static int[] merge(int A[], int m, int B[], int n) {
        //從背後merge過來

        if(m == 0){
        	for(int i = 0; i < n; ++i){
        		A[i] = B[i];
        	}
        	return A;
        }
        if(n == 0){
            return A;
        }
        
        int indexA = m - 1;
        int indexB = n-1;
        int index = m + n - 1;
        
        while(index >= 0){
        	if(indexA >= 0 && A[indexA] > B[indexB]){
        		A[index] = A[indexA];
        		indexA--;
        	}
        	else{
        		A[index] = B[indexB];
        		indexB--;
        	}
        	index--;
        	
        	if(indexB < 0)
        		break;
        }
       
        
        return A;
    }
    
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<Integer>();
        if(matrix.length == 0)
            return result;
        int height = matrix.length;
        int width = matrix[0].length;
        int rowBegin = 0;
        int rowEnd = height-1;
        int columnBegin = 0;
        int columnEnd = width-1;
        
        while(rowBegin<=rowEnd && columnBegin <= columnEnd){
	        for(int i = columnBegin; i <= columnEnd; ++i){
	        	result.add(matrix[rowBegin][i]);
	        }
	        rowBegin++;
	        if(rowBegin>rowEnd)
	        	return result;
	        
	        for(int i = rowBegin; i <= rowEnd; ++i){
	        	result.add(matrix[i][columnEnd]);
	        }
	        columnEnd--;
	        if(columnBegin > columnEnd)
	        	return result;
	        
	        for(int i = columnEnd; i >= columnBegin; --i){
	           	result.add(matrix[rowEnd][i]);
	        }
	        rowEnd--;
	        if(rowBegin>rowEnd)
	        	return result;
	        
	        for(int i = rowEnd; i >= rowBegin; --i){
	        	result.add(matrix[i][columnBegin]);
	        }
	        columnBegin++;
	        if(columnBegin > columnEnd)
	        	return result;
        }
        
        return result;
    }
    
    public static int[][] generateMatrix(int n) {
    	int height = n;
        int width = n;
        int rowBegin = 0;
        int rowEnd = height-1;
        int columnBegin = 0;
        int columnEnd = width-1;
        int matrix[][]=new int[n][n];
        int number = 1;
        
        while(rowBegin<=rowEnd && columnBegin <= columnEnd){
	        for(int i = columnBegin; i <= columnEnd; ++i){
	        	matrix[rowBegin][i]= number++;
	        }
	        rowBegin++;
	        if(rowBegin>rowEnd)
	        	return matrix;
	        
	        for(int i = rowBegin; i <= rowEnd; ++i){
	        	matrix[i][columnEnd] = number++;
	        }
	        columnEnd--;
	        if(columnBegin > columnEnd)
	        	return matrix;
	        
	        for(int i = columnEnd; i >= columnBegin; --i){
	           	matrix[rowEnd][i] = number++;
	        }
	        rowEnd--;
	        if(rowBegin>rowEnd)
	        	return matrix;
	        
	        for(int i = rowEnd; i >= rowBegin; --i){
	        	matrix[i][columnBegin] = number++;
	        }
	        columnBegin++;
	        if(columnBegin > columnEnd)
	        	return matrix;
        }
        return matrix;
    }
    
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result;// = new ArrayList<List<Integer>>();
        
        result = combineHelper(n,k,1, new ArrayList<Integer>());
        //C n取K
        return result;
    }
    
    private static List<List<Integer>> combineHelper(int n, int k , int start, ArrayList<Integer> l){
    	
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	if(k == 0){
    		ArrayList<Integer> com = new ArrayList<>(l);
	        result.add(com);
	        return result;
	    }
    	
    	for(int i = start; i <= n; ++i){

 	        l.add(i);
 	        result.addAll(combineHelper(n, k - 1, i + 1, l));
 	        l.remove(l.size()-1);
 	    }
    	
    	return result;
    }
    
    /*private static void combineHelper(int n, int k , int start, List<List<Integer>> result, ArrayList<Integer> l){
    	
    	
    	if(k == 0){
    		ArrayList<Integer> com = new ArrayList<>(l);
	        result.add(com);
	        return;
	    }
    	 for(int i = start; i <= n; ++i){

 	        l.add(i);
 	        combineHelper(n, k - 1, i + 1, result, l);
 	        l.remove(l.size()-1);
 	    }
    }*/
    
    
    public static int findMin(int[] num) {

        
        int startIndex=0;
        int endIndex = num.length-1;
        while (num[endIndex] == num[0] && endIndex > 0) {
        	endIndex--;
		}
        if(num[endIndex] > num[0])
        	return num[0];
        
        while(endIndex - startIndex > 1){
        	int examIndex =(int)Math.ceil((startIndex + endIndex) / 2.0);
        	if(num[examIndex] >= num[startIndex]){
        		startIndex = examIndex;
        	}
        	else{
        		endIndex = examIndex;
        	}
        }
        
        return num[endIndex];
    	
    	
    }

    public static int ladderLength(String start, String end, Set<String> dict) {
        
    	//int[][] diffTable = new int[dict.size()][dict.size()];
    	
    	int diff =ladderLengthHelper(start, end);
    	if(diff == 0)
    		return 0;
    	else if(diff == 1)
    		return 2;
    	
    	
    	Set<String> nextWordCollection = new HashSet<>();
    	Set<String> newDict = new HashSet<>();
    	Iterator iterator = dict.iterator();
    	while(iterator.hasNext()){
    		String element1 = (String) iterator.next();
    		diff =ladderLengthHelper(start, element1);
    		
    		if(diff == 1){
    			nextWordCollection.add(element1);
    		}
    		else if(diff == 2){
    			newDict.add(element1);
    		}
    	}
    	int distance = Integer.MAX_VALUE;
    	Iterator iteratorCollection = nextWordCollection.iterator();
    	while(iteratorCollection.hasNext()){
    		String newStart = (String) iteratorCollection.next();
    		int result = ladderLength(newStart, end, newDict);
    		if(result != 0 && result < distance)
    			distance = result;
    	}
    	if(distance != Integer.MAX_VALUE)
    		return distance +1;
    	else 
    		return 0;
    	
    	
    	/*int result = ladderLengthHelper(start, end);
    	if(result == 1)
    		return 2;
    	else if(result == 2){	
	    	int min = 0;
	    	
	    	if(dict.size() > 0){
	        	Iterator iterator = dict.iterator();
	        	while(iterator.hasNext()){
	        		String element = (String) iterator.next();
	        		
	        		if(ladderLengthHelper(start,element) == 1){
	                	//Set<String> subSet = new HashSet<>();
	                	//subSet.addAll(dict);
	                	//subSet.remove(element);
	                	dict.remove(element);

	        			
	        			
	                	int temp = ladderLength(element, end, dict);
	                	if((min == 0 && temp != 0) || (min != 0 && temp != 0 && temp < min))
	                		min = temp;
	                	
	                	dict.add(element);
	        		}
	        	}
	        	
        		if(min == 0)
        			return 0;
        		else 
        			return min + 1;
	    		
	    	}
	    	else{
	    		return 0;
	    	}
    	}*/
    	
    	//return 0;
    }
    
    public static int ladderLengthLoop(String start, String end, Set<String> dict) {

    	
    	
    	
         return 0;
    }
    
    private static int ladderLengthHelper(String str1, String str2){
    	
    	//if(str1.length() != str2.length())
    	//	return 2;
    	
    	if(str1.equals(str2) == true)
    		return 0;
    	
    	int check = 0;
    	for(int i = 0; i < str1.length(); i++){
    		if(str1.charAt(i) != str2.charAt(i))
    			check++;
    		
    		if(check > 1)//more than one char different
    			return 2;
    	}
    	
    	//if(check == 1)//only one char different
    		return 1;
    	//else 
    	//	return 0;
    }

    public void rotate(int[][] matrix) {
        
    	int start = 0;
    	int end = matrix.length-1;
    	
    	while (end - start >0) {
    		
    		
			for(int i = 0; i < end - start; ++i){
				int temp = matrix[start][start+i];
				matrix[start][start+i] = matrix[end-i][start];
				matrix[end-i][start] = matrix[end][end-i];
				matrix[end][end-i] = matrix[start+i][end];
				matrix[start+i][end] = temp;
			}
			end--;
			start++;
			
		}
    	
    }
    
    public static boolean isValidParentheses(String s) {
    	
    	Stack<Character> stack = new Stack<>(); 
    	
    	for(int i = 0; i < s.length(); ++i){
    		char c = s.charAt(i);
    		if(c == '(' || c== '[' || c== '{')
    			stack.push(c);
    		else if(c == ')' || c== ']' || c== '}'){
    			if(stack.isEmpty() == true)
    				return false;
    			
    			 char storedChar = stack.pop();

     			if(c == ')'){
     				if(storedChar != '(')
     					return false;
     			}
     			else if(c == ']'){
     				if(storedChar != '[')
     					return false;
     			}
     			else if(c == '}'){
     				if(storedChar != '{')
     					return false;
     			}
    		}
    		
    	}
    	
    	if(stack.isEmpty() != true)
    	    return false;
    	else
            return true;
    }
    
    public static int maxSubArray(int[] A) {
        if(A.length == 0)
            return 0;
        
        
        
    	int max = Integer.MIN_VALUE;
    	int sub = 0;
    	for(int i = 0; i < A.length; ++i){
    		sub += A[i];
    		
    		if(sub >= max)
    			max = sub;
    		if(sub < 0)
    			sub = 0;
    		
    	}
    	
        return max;
    }
    
    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[]{1,2};

        
        Map<Integer, Integer> myHashtable = new HashMap<Integer, Integer>();
        
        for(int i = 0; i <numbers.length; ++i){
            int requiredRest = target - numbers[i];
        		
    		if(myHashtable.containsKey(requiredRest) == true){
    			result[0] = myHashtable.get(requiredRest);
    			result[1] = i+1;
    			
    			return result;
        	}
    		myHashtable.put(numbers[i], i+1);
        }
        
        
        return result;
    }
    
    public static List<List<Integer>> threeSum(int[] num) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	
    	if(num.length == 0)
    		return result;
    	
    	Arrays.sort(num);
    	
    	for(int i = 0; i < num.length; ++i){
    		//Prevent duplicate
    		if(i != 0 && num[i] == num[i-1])
    			continue;
    		
    		int number1 = num[i];
    		
    		int j = i+1;
    		int k = num.length -1;
    		while(j < k){
    		
    		
    			int number2 = num[j];
    			int number3 = num[k];
    			
    			if(number1 + number2 + number3 < 0){
    				j++;
    			}
    			else if(number1 + number2 + number3 > 0){
    				k--;
    			}    			
    			else{
    				//Initially, I use this method to remove the duplicate. Then I changed to use the current method which could directly prevent the duplicate.
    				
    				/*boolean flag = true;
    				for(int p = 0; p < result.size(); p++){
    					ArrayList<Integer> oldArray = (ArrayList<Integer>) result.get(p);
    					if(oldArray.get(0) == number1 && oldArray.get(1) == number2 && oldArray.get(2) == number3 ){
    						flag = false;
    						break;
    					}
    				}
    				
    				if(flag == true){
        				ArrayList<Integer> subArray = new ArrayList<>();
        				subArray.add(number1);
        				subArray.add(number2);
        				subArray.add(number3);
        				result.add(subArray);
    				}
    				j++;
    				k--;*/
    				
    				ArrayList<Integer> subArray = new ArrayList<>();
    				subArray.add(number1);
    				subArray.add(number2);
    				subArray.add(number3);
    				result.add(subArray);
    				
    				do{
    					j++;
    				}
    				while(num[j] == num[j-1] && j < k);//Prevent the duplicate
    				
    				do{
    					k--;
    				}
    				while(num[k] == num[k+1] && j < k);//Prevent the duplicate
    				
    			}
    			
    		}
    	}
        
    	return result;
    }

    public static int threeSumClosest(int[] num, int target) {
    	
    	
    	if(num.length < 3)
    		return 0;
    	
    	Arrays.sort(num);
    	int closetSum  = num[0] + num[1] + num[2];
    	int diff = Math.abs(target - closetSum);
    	
    	for(int i = 0; i < num.length; ++i){
    		int number1 = num[i];
    		
    		int j = i+1;
    		int k = num.length -1;
    		while(j < k){
    		
    		
    			int number2 = num[j];
    			int number3 = num[k];
    			
    			if(number1 + number2 + number3 < target){
    				j++;
    				
    				if(Math.abs(target - (number1 + number2 + number3)) < diff){
    					closetSum = number1 + number2 + number3;
    					diff = Math.abs(target - (number1 + number2 + number3));
    				}
    			}
    			else if(number1 + number2 + number3 > target){
    				k--;
       				if(Math.abs(target - (number1 + number2 + number3)) < diff){
    					closetSum = number1 + number2 + number3;
    					diff = Math.abs(target - (number1 + number2 + number3));
    				}
    			}    			
    			else{
    				
    				return target;
    			}
    			
    		}
    	}
        
    	return closetSum;
    }
    
    public static List<List<Integer>> fourSum(int[] num,  int target) {    	
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		if(num.length == 0)
			return result;
		
		Arrays.sort(num);
		
		for(int i = 0; i < num.length; ++i){
    		if(i != 0 && num[i] == num[i-1])
    			continue;
			
			int number0 = num[i];
			
			for(int j = i+1; j < num.length -2 ; ++j){
				if(j != i+1 && num[j] == num[j-1])
				    continue;
				
				int number1 = num[j];
				int k = j+1;
				int l = num.length -1;
				
				while(k < l){
					int number2 = num[k];
					int number3 = num[l];
					
					if(number1 + number2 + number3 < target - number0){
						//k++;
						do{
        					k++;
        				}
        				while(num[k] == num[k-1] && k < l);//Prevent the duplicate
					}
					else if(number1 + number2 + number3 > target - number0){
						//l--;
						do{
        					l--;
        				}
        				while(num[l] == num[l+1] && k < l);//Prevent the duplicate
					}   
					else{
						
						/*boolean flag = true;
						for(int p = 0; p < result.size(); p++){
							ArrayList<Integer> oldArray = (ArrayList<Integer>) result.get(p);
							if(oldArray.get(0) == number0 && oldArray.get(1) == number1 && oldArray.get(2) == number2 && oldArray.get(3) == number3){
								flag = false;
								break;
							}
						}
						
						if(flag == true){
		    				ArrayList<Integer> subArray = new ArrayList<>();
		    				subArray.add(number0);
		    				subArray.add(number1);
		    				subArray.add(number2);
		    				subArray.add(number3);
		    				result.add(subArray);
						}
						k++;
						l--;*/
						
	    				ArrayList<Integer> subArray = new ArrayList<>();
	    				subArray.add(number0);
        				subArray.add(number1);
        				subArray.add(number2);
        				subArray.add(number3);
        				result.add(subArray);
        				
        				do{
        					k++;
        				}
        				while(num[k] == num[k-1] && k < l);//Prevent the duplicate
        				
        				do{
        					l--;
        				}
        				while(num[l] == num[l+1] && k < l);//Prevent the duplicate
					}
				}
				
			}
		}
	    
		return result;
	}
    
    public static List<Integer> grayCode(int n) {
    	List<Integer> result = new ArrayList<Integer>();
    	
    	if(n == 0){
    		result.add(0);
    		return result;
    	}
    	else if(n == 1){
    		result.add(0);
    		result.add(1);
    		return result;
    	}
    	else {
    		result.add(0);
    		result.add(1);
		}
    	
    	int bit = 1;
    	while(bit < n){
    		Double value = Math.pow(2, bit);
    		int size = result.size();
	    	for(int i = 0; i < size; ++i){
	    		result.add((Integer)result.get(size -1 -i) + value.intValue()); 
	    	}
	    	bit++;
    	}
    	return result;
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
    	
    	List<Integer> result = new ArrayList<Integer>();
    	
    	if(triangle.size() == 0)
    		return 0;
    	else 
    		result.add(triangle.get(0).get(0));
    	
    	List<Integer> layerOld = result;
    	List<Integer> layerNew;

    	
    	for(int i = 1; i < triangle.size(); i++){
    		layerNew = new ArrayList<Integer>();
    		
    		for(int j = 0; j < i+1; j++){
    			if(j == 0){
    				layerNew.add( layerOld.get(0) + triangle.get(i).get(0) );
    				
    			}
    			else if(j == i){
    				layerNew.add( layerOld.get(i-1) + triangle.get(i).get(i) );

    			}
    			else{
    				if(layerOld.get(j-1) < layerOld.get(j))
    					layerNew.add( layerOld.get(j-1) + triangle.get(i).get(j) );
    				else {
    					layerNew.add( layerOld.get(j) + triangle.get(i).get(j) );
					}
    				
    			}
    		}
    		layerOld = layerNew;
    	}
    	
    	int min =layerOld.get(0);
    	for(int i = 1; i < layerOld.size(); ++i){
    		if(layerOld.get(i) < min)
    			min = layerOld.get(i);
    	}
    	
    	return min;
    }

    public static int evalRPN(String[] tokens) {
        
    	if(tokens.length == 0)
    		return 0;  	

    	Stack<Integer> stack = new Stack<Integer>();
    	 	
    	for(int index = 0; index < tokens.length; index++){

    		if(tokens[index].equals("+") || tokens[index].equals("-")  || tokens[index].equals("*") || tokens[index].equals("/")){
        		int value = 0;
    			int value2 = stack.pop();
            	int value1 = stack.pop();
    			
				if(tokens[index].equals("+")){
					value = value1 + value2;
        		}
    			else if(tokens[index].equals("-")){
    				value = value1 - value2;
        		}
        		else if(tokens[index].equals("*")){
        			value = value1 * value2;
        		}
        		else if(tokens[index].equals("/")){
        			value = value1 / value2;
        		}
    			
    			stack.push(value);
    		}
    		else{
    			stack.push(Integer.parseInt(tokens[index]));
    		}
    	}
    	
    	return stack.pop();
    }

    public static void sortColors(int[] A) {
        
    	int redStart = 0;
    	int whiteStart = 0;
    	int blueStart = 0;
    	

    	for(int i = 0; i < A.length; i++){
    		if(A[i] == 0){
    			A[blueStart] = 2;
    			A[whiteStart] = 1;
    			A[redStart] = 0;
    			redStart++;
    			whiteStart++;
    			blueStart++;
    		}
    		else if(A[i] == 1){
    			A[blueStart] = 2;
    			A[whiteStart] = 1;
    			blueStart++;
    			whiteStart++;
    		}
    		else if(A[i] == 2){
    			A[blueStart] = 2;
    			blueStart++;
    		}
    		
    	}
    	
    }
    
    public static String longestPalindrome(String s) {
        
    	int max = 0;
    	String maxString = new String();
    	
    	for(int i = 0; i < s.length(); ++i){
    		
    		int oddLeftIndex = i-1;
    		int oddRightIndex = i+1;
    		int evenLeftIndex = i;
    		int evenRightIndex=i+1;
    		
    		int oddMax = 1;
    		int evenMax = 0;
    		while(oddLeftIndex >= 0 && oddRightIndex < s.length() && s.charAt(oddLeftIndex) == s.charAt(oddRightIndex)){
    			oddMax +=2;
    			oddLeftIndex--;
    			oddRightIndex++;
    		}
    		
    		while(evenLeftIndex >= 0 && evenRightIndex < s.length() && s.charAt(evenLeftIndex) == s.charAt(evenRightIndex)){
    			evenMax +=2;
    			evenLeftIndex--;
    			evenRightIndex++;
    		}
    		
    		if(oddMax > max){
    			max = oddMax;
    			maxString = s.substring(oddLeftIndex+1, oddRightIndex);
    		}
    		
    		if(evenMax > max){
    			max = evenMax;
    			maxString = s.substring(evenLeftIndex+1, evenRightIndex);
    		}
    	}
    	
    	return maxString;
    }
    
    public static int lengthOfLongestSubstring(String s) {
    	//Hashtable<Character, Integer> myHashtable = new Hashtable<>();
    	Map<Character, Integer> myHashtable = new HashMap<Character, Integer>();
    	
    	int maxLength = 0;
    	int currentLength = 0;
    	int startIndex = 0;
    	
		for(int i = 0; i < s.length(); ++i){
			if(myHashtable.containsKey(s.charAt(i)) != true){
				myHashtable.put(s.charAt(i), i);
				
				currentLength++;
				if(currentLength > maxLength)
					maxLength = currentLength;
			}
			else {
				
				if(myHashtable.get(s.charAt(i)) >= startIndex){
					currentLength = i - myHashtable.get(s.charAt(i));
					startIndex = myHashtable.get(s.charAt(i));
				}
				else{
					currentLength++;
					if(currentLength > maxLength)
						maxLength = currentLength;
				}

				
				myHashtable.put(s.charAt(i), i);
			}
		}
		
    	
        return maxLength;
    }
    
    public static int findPeakElement(int[] num) {
        if(num.length == 0 || num.length == 1)
            return 0;
        
        
        
        for(int i = 0; i < num.length; ++i){
            
            if(i == 0){
            	if(num[0] > num[1])
            		return 0;
            }
            else if(i == num.length -1){
            	if(num[i] > num[i-1])
            		return num.length-1;
            
            }
            else if(num[i] > num[i-1] && num[i] > num[i+1])
                return i;
        }   
        
        return 0;
    }
    
    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<String>();
        
        for(int i = 1; i < s.length() - 2 && i < 4; ++i){
        	String s1 = s.substring(0, i);
        	String r1 = s.substring(i);
        	
        	
        	
        	for(int j = 1; j < r1.length() - 1 && j < 4; j++){
        		String s2 = r1.substring(0, j);
        		String r2 = r1.substring(j);
        		
        		for(int k = 1; k < r2.length() && k < 4; k++){
        			String s3 = r2.substring(0, k);
        			String r3 = r2.substring(k);
        			
        			if(r3.length() > 3)
        				continue;
        				
    				int v1 = Integer.parseInt(s1);
    				int v2 = Integer.parseInt(s2);
        			int v3 = Integer.parseInt(s3);
        			int v4 = Integer.parseInt(r3);
        			if(s1.length() > 1){
        				if(s1.charAt(0) == '0')
        					continue;
        			}
        			if(s2.length() > 1){
        				if(s2.charAt(0) == '0')
        					continue;
        			}
        			if(s3.length() > 1){
        				if(s3.charAt(0) == '0')
        					continue;
        			}
        			if(r3.length() > 1){
        				if(r3.charAt(0) == '0')
        					continue;
        			}
        			
        			
        			if(v1 < 256 && v2 < 256 && v3 < 256 && v4 < 256)			
        					result.add(s1 + "." + s2 + "." + s3 + "." + r3);
        			
        		}
        	}
        	
        }
        
        return result;
    }

    public static int minPathSum(int[][] grid) {
    	
    	int height = grid.length;
    	int width = grid[0].length;
    	
    	for(int i = 0; i < grid.length; ++i){
    		for(int j = 0; j < grid[0].length; j++){
    			if(i == 0){
    				if(j > 0)
    					grid[0][j] += grid[0][j-1]; 
    			}
    			else{
    				if(j == 0)
    					grid[i][0] += grid[i-1][0];
    				else{
    					grid[i][j] += grid[i-1][j] <= grid[i][j-1] ?  grid[i-1][j] : grid[i][j-1];
    				}
    			}
    		}
    		
    	}
    	
    	
        return grid[height-1][width-1];
    }
    
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        
        String str = "";
        helper(str, result, n, 0, 0);
        
        return result;
    }
    
    private static void helper(String s, List<String> array, int n, int leftParenthesesNum, int rightParenthesesNum){
        
    	if(rightParenthesesNum < leftParenthesesNum){
    		String str = s.concat(")");
    		if(rightParenthesesNum == n-1){
    			array.add(str);
    			return;
    		}
    		else {
				helper(str, array, n, leftParenthesesNum, rightParenthesesNum + 1);
			}
    	}
    	
    	if(leftParenthesesNum < n ){
    		String str = s.concat("(");
    		helper(str, array, n, leftParenthesesNum+1, rightParenthesesNum);
    	}
    	
    	return ;
    }

    public static List<List<Integer>> permute(int[] num) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	
    	Arrays.sort(num);
    	
    	Map<Integer, Boolean> myHashtable = new HashMap<Integer, Boolean>();
    	for(int i = 0; i < num.length; ++i){
    		myHashtable.put(i, true);
    	}
    	
    	List<Integer> integerArray = new ArrayList<Integer>();
    	permuteHelper(result, integerArray, num, myHashtable);
    	return result;
    }
    
    private static void permuteHelper(List<List<Integer>> array, List<Integer> intArray, int[] num, Map<Integer, Boolean> myHashtable){
    	
    	for(int i =0; i < num.length; i++){
    	    if(i != 0 && num[i-1] == num[i])//prevent duplicate
    	        continue;
    	    
    		if(myHashtable.get(i) == true){
    			myHashtable.put(i, false);
    			intArray.add(num[i]);
    			
    			if(intArray.size() == num.length){
    				
					List<Integer> integerArray = new ArrayList<Integer>(intArray) ;
    				array.add(integerArray);
    			}
    			else{
        			permuteHelper(array, intArray, num, myHashtable);
    			}
    			

    			myHashtable.put(i, true);
    			intArray.remove(intArray.size()-1);
    		}
    	}
    }
   
    public static List<List<Integer>> permuteUnique(int[] num) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	
    	Arrays.sort(num);
    	
    	Map<Integer, Boolean> myHashtable = new HashMap<Integer, Boolean>();
    	for(int i = 0; i < num.length; ++i){
    		myHashtable.put(i, true);
    	}
    	
    	List<Integer> integerArray = new ArrayList<Integer>();
    	permuteUniqueHelper(result, integerArray, num, myHashtable);
    	return result;
    }
    
    private static void permuteUniqueHelper(List<List<Integer>> array, List<Integer> intArray, int[] num, Map<Integer, Boolean> myHashtable){
    	
    	for(int i =0; i < num.length; i++){
    	    if(i != 0 && num[i-1] == num[i] && myHashtable.get(i-1) == false)//prevent duplicate
    	        continue;
    	    
    		if(myHashtable.get(i) == true){
    			myHashtable.put(i, false);
    			intArray.add(num[i]);
    			
    			if(intArray.size() == num.length){
    				
					List<Integer> integerArray = new ArrayList<Integer>(intArray) ;
    				array.add(integerArray);
    			}
    			else{
        			permuteHelper(array, intArray, num, myHashtable);
    			}
    			

    			myHashtable.put(i, true);
    			intArray.remove(intArray.size()-1);
    		}
    	}
    }
    
    public static void setZeroes(int[][] matrix) {
        
    	
    	boolean firstColumnHasZero = false;
    	boolean firstRowHasZero = false;
    	
    	for(int i = 0; i < matrix[0].length; ++i){
    		if(matrix[0][i] == 0){
    			firstRowHasZero = true;
    			break;
    		}
    	}
    	
    	for(int i = 0; i < matrix.length; ++i){
    		if(matrix[i][0] == 0){
    			firstColumnHasZero = true;
    			break;
    		}
    	}
    	
    	
    	for(int i = 1; i < matrix.length; ++i){
    		for(int j = 1; j < matrix[0].length; ++j){
    			if(matrix[i][j] == 0){
    				matrix[i][0] = 0;
    				matrix[0][j] = 0;
    			}
    		}
    	}
    	
    	for(int i = 1; i < matrix[0].length; ++i){
    		if(matrix[0][i] == 0){
    			for(int j = 1; j < matrix.length; ++j){
    				matrix[j][i] = 0;
    			}
    		}
    	}
    	
    	for(int i = 1; i < matrix.length; ++i){
    		if(matrix[i][0] == 0){
    			for(int j = 1; j < matrix[0].length; ++j){
    				matrix[i][j] = 0;
    			}
    		}
    	}
    	
    	if(firstColumnHasZero == true){
    		for(int i = 0; i < matrix.length; ++i){
    			matrix[i][0] = 0;
    		}
    	}
    	
    	if(firstRowHasZero == true){
    		for(int i = 0; i < matrix[0].length; ++i){
    			matrix[0][i] = 0;
    		}
    	}
    }

    public static List<List<Integer>> subsets(int[] S) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        
        Arrays.sort(S);
    	
        //List<Integer> integerArray = new ArrayList<Integer>();
    	result.add(new ArrayList<Integer>());
    	result.addAll( subsetsHelper(new ArrayList<Integer>(), S, 0));
    	
    	return result;
    }

    private static List<List<Integer>> subsetsHelper(List<Integer> intArray, int[] num, int start){
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	
    	for(int i = start; i < num.length; i++){

			List<Integer> integerArray = new ArrayList<Integer>(intArray) ;
			integerArray.add(num[i]);
			result.add(integerArray);
			
			if(intArray.size() != num.length){
				result.addAll(subsetsHelper(integerArray, num, i+1));
			}
    			
		}
    	return result;
    }
    
    
    public static List<List<Integer>> subsetsWithDup(int[] num) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();

	 	
    	Arrays.sort(num);
    	List<Integer> subset = new ArrayList<Integer>();
    	result.add(subset);
    	result.addAll(subsetsWithDupHelper(num, 0, subset));
    	
    	return result;
    }
    
    private static List<List<Integer>> subsetsWithDupHelper(int[] num, int startIndex, List<Integer> subset) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
	 	for(int i = startIndex; i < num.length; i++){
	 		if(i != startIndex && num[i] == num[i-1])
	 			continue;
	 		
	 		List<Integer> newSubsets = new ArrayList<Integer>(subset);
	 		newSubsets.add(num[i]);
	
	 		result.add(newSubsets);
	 		result.addAll(subsetsWithDupHelper(num, i+1, newSubsets));
			
	 	}
    	
    	return result;
    }
    
    /*private static void subsetsWithDupHelper(List<List<Integer>> array, int[] num, int startIndex, List<Integer> subset) {

    	
	 	for(int i = startIndex; i < num.length; i++){
	 		List<Integer> newSubsets = new ArrayList<Integer>(subset);
	 		newSubsets.add(num[i]);

			//determine if this is duplicate
			boolean flag = false;
			for(int j = 1; j < array.size(); j++){
				flag = true;
				List<Integer> oldSubsets = array.get(j);
				
				if(oldSubsets.size() == newSubsets.size()){
					for(int k = 0; k < oldSubsets.size(); ++k){
						if(newSubsets.get(k) != oldSubsets.get(k)){
							flag = false;
							break;
						}
					}
				}
				else {
					flag = false;
				}
				
				if(flag == true)
					break;
			}
			
			//not duplicate
			if(flag != true){
				
				array.add(newSubsets);
				subsetsWithDupHelper(array, num, i+1, newSubsets);
			}
	 	}
    	
    	return;
    }*/
    
    public static boolean searchMatrix(int[][] matrix, int target) {
        
    	int width = matrix[0].length;
    	int height = matrix.length;
    	
    	if(target < matrix[0][0] || target > matrix[height-1][width-1])
    		return false;
    	
    	//Treat it as a sorted array
    	int start = 0;
		int end = width * height - 1;
		
		while (end - start > 1){
            int mid = (start + end) / 2;
            
            if(matrix[mid / width][mid % width] == target)
            	return true;
            else if (matrix[mid / width][mid % width] < target)
            	start = mid;
            else 
            	end = mid;
        }
        return matrix[start / width][start % width] == target || matrix[end / width][end % width] == target;
		
        /*while (start < end){
            int mid = (start + end - 1) / 2;
            
            if(matrix[mid / width][mid % width] == target)
            	return true;
            else if (matrix[mid / width][mid % width] < target)
            	start = mid + 1;
            else 
            	end = mid;
        }
        return matrix[start / width][start % width] == target;*/
    	
    }
    
    
    public static int searchInsert(int[] A, int target) {
    	int length = A.length;

    	if(target < A[0])
    		return 0;
    	if(target > A[length -1])
    		return length;

    	
    	int start = 0;
		int end = length - 1;
        while (end - start > 1){
            int mid = (start + end) / 2;
            
            if(A[mid] == target)
            	return mid;
            else if (A[mid] < target)
            	start = mid;
            else 
            	end = mid;
        }
        if(A[start] == target)
        	return start;
        else 
        	return end;

    }
    
    public static String simplifyPath(String path) {
        int index = 0;
        
        while(index < path.length()){
        	
        	if(path.charAt(index) == '/'){
            	
        		if(path.length() - index >=4 && (path.charAt(index+1) == '.' && path.charAt(index+2) == '.' && path.charAt(index+3) == '/')){
        			path = path.substring(index+3);
        			index--;
        		}
        		else if(index != 0 && index != path.length()-1){
            		path = path.substring(index);
            		index = -1;		
            	}
            	else if(index != 0 && index == path.length()-1){
            		path = path.substring(0, index);
            		break;
            	}
        	}
        	index++;
        }
        return path;
    }
    
    public static int divide(int dividend, int divisor) {
    	
    	if(divisor == 0)
    		return Integer.MAX_VALUE;
    	if(dividend == 0) 	
    		return 0;	
    		
    	boolean flag = true;
    		
    	if(dividend > 0 && divisor > 0){
    	    dividend = -dividend;
    	    divisor = -divisor;
    	}
    	else if(dividend < 0 && divisor > 0){
    	    divisor = -divisor;
    	    flag = false;
    	}else if (dividend > 0 && divisor < 0){
    		dividend = -dividend;
    	    flag = false;
    	}
    	
    	int divisorBackup = divisor;
    	
    	int accuCount = 0;
    	while(dividend - divisor <= 0){
    		
	    	int count = 1;
	    	while(dividend - divisor <= 0){
	    		accuCount += count;
	    		if(flag == true && accuCount >= Integer.MAX_VALUE)
	    		    return Integer.MAX_VALUE;
	    		
	    		dividend -= divisor;
		    	if(dividend == 0)
		    		break;
	    		
	    		if(divisor > Integer.MIN_VALUE / 2){
	    			divisor += divisor;
	    			count += count;
	    		}
	    		
	    	}
	    	if(dividend == 0)
	    		break;
	    	divisor = divisorBackup;
    	}
    	
    	if(flag == false)
    	    accuCount = -accuCount;
    	
        return accuCount;
    }
    
    public static int maxArea(int[] height) {
    	
    	int start = 0;
    	int end = height.length -1;
    	
    	int maxArea = 0;
    	
    	while(start < end){
    		maxArea = Math.max(maxArea, (end - start) * Math.min(height[start], height[end]));
    		
    		if(height[end] > height[start])
    			start++;
    		else {
				end--;
			}
    	}
    	
    	return maxArea;
    }
    
    public static int uniquePaths(int m, int n) {
        int [] array = new int[n];
		array[0] = 1;
    	
    	for(int i = 0; i < m; ++i){
    		for(int j = 1; j < n; j++){
    			if(i == 0){
    				array[j] = 1; 
    			}
    			else{
    				array[j] += array[j-1];
    			}
    		}
    		
    	}
        
        return array[n-1];
    }
    
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
    	
    	int width = obstacleGrid[0].length;
    	int height = obstacleGrid.length;
    	
    	int [] array = new int[width];
    	
    	if(obstacleGrid[0][0] == 1)
    		return 0;
		array[0] = 1;
    	
		for(int j = 1; j < width; j++){
			if(obstacleGrid[0][j] != 1 && array[j-1] != 0)
				array[j] = 1;
			else 
				array[j] = 0;
		}
		
    	for(int i = 1; i < height; ++i){
    		for(int j = 0; j < width; j++){

    			
				if(j == 0){
					if(obstacleGrid[i][j] != 1 && array[j] != 0)
						array[j] = 1;
					else 
						array[j] = 0;
				}
				else{
					if(obstacleGrid[i][j] != 1)
						array[j] = array[j] + array[j-1];
					else
						array[j] = 0;
				}
    		}
    		
    	}
    	
    	return array[width-1];
    }

    public static boolean canJump(int[] A) {
    	
    	//從最後一個index往前判斷A {2,3,1,1,4}
    	//要到達A[4]的位置表示需要A[3] >= 1 或A[2] >= 2 或A[1] >= 3或 A[0] >= 4 
    	//因為A[3]有符合條件  所以接下來判斷有誰可以到達A[3] 
    	//相同的 到達A[3]的條件是 A[2] >= 1 或 A[1] >= 2 或 A[0] >= 3
    	//因為A[2]符合到達A[3]的條件  所以相同的接著判斷有誰到的了A[2]
    	//到達A[2]需要 A[1] >= 1 或 A[0] >= 2
    	//以下類推
    	if(A.length == 1)
    	    return true;
    	
    	int index = 0;//index and currentIndex皆是從後面數過來的index
    	int currentIndex = 1;
    	int length = A.length;
    	
    	while(length - 1 - index - currentIndex >= 0){

    		if(length - 1 - index - currentIndex == 0){
    			if(A[length - 1 - index - currentIndex] >= currentIndex)
    				return true;
    			else 
    				return false;
    		}
    		if(A[length - 1 - index - currentIndex] >= currentIndex){
    			
    			index += currentIndex;
    			currentIndex = 1;
    		}
    		else{
    			currentIndex++;
    		}
    	}

    	return false;
        
    }
    
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        List<Integer> combination = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumHelper(result, combination, candidates, target, 0);
        
        return result;
    }
    
    private static void combinationSumHelper(List<List<Integer>> array, List<Integer> combination, int[] candidates, int target, int index){
    	

    	for(int i = index; i < candidates.length; i++){
    		
    		if(i != index && candidates[i] == candidates[i-1])
    		    continue;
    		
    		List<Integer> newCombination = new ArrayList<Integer>(combination);
    		newCombination.add(candidates[i]);
    		
    		if(candidates[i] == target){
    			
    			//Check combination duplicate
    			/*boolean flag = false;
    			for(int j = 0; j < array.size(); j++){
    				List<Integer> oldCombination = array.get(j);
    				
    				if(oldCombination.size() == newCombination.size()){
    					flag = true;
    					for(int k = 0; k < oldCombination.size(); k++){
    						if(newCombination.get(k) != oldCombination.get(k))
    							flag = false;
    					}
    				}
    				
    				if(flag == true)//duplicate
    					break;
    				
    			}
    			
    			if(flag == false)//not duplicate
    				array.add(newCombination);*/
    			
    			array.add(newCombination);
    		}
    		else if(candidates[i] < target){
    			combinationSumHelper(array, newCombination, candidates, target - candidates[i], i);
    		}
    	}
    }
    
    public static List<List<Integer>> combinationSum2(int[] num, int target) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	
        List<Integer> combination = new ArrayList<>();
        
        Arrays.sort(num);
        
        combinationSum2Helper(result, combination, num, target, 0);
    	
    	return result;
    }
    
    private static void combinationSum2Helper(List<List<Integer>> array, List<Integer> combination, int[] candidates, int target, int index){
    	
    	for(int i = index; i < candidates.length; i++){
    		if(i != index && candidates[i] == candidates[i-1])
    		    continue;
    		
    		List<Integer> newCombination = new ArrayList<Integer>(combination);
    		newCombination.add(candidates[i]);
    		
    		if(candidates[i] == target){
    			
    			//Check combination duplicate
    			/*boolean flag = false;
    			for(int j = 0; j < array.size(); j++){
    				List<Integer> oldCombination = array.get(j);
    				
    				if(oldCombination.size() == newCombination.size()){
    					flag = true;
    					for(int k = 0; k < oldCombination.size(); k++){
    						if(newCombination.get(k) != oldCombination.get(k))
    							flag = false;
    					}
    				}
    				
    				if(flag == true)//duplicate
    					break;
    				
    			}
    			
    			if(flag == false)//not duplicate*/
    				array.add(newCombination);
    			
    			//因為已經sort過  所以接下來的值都會超過
    			break;
    		}
    		else if(candidates[i] < target){
    			combinationSum2Helper(array, newCombination, candidates, target - candidates[i], i+1);
    		}
    		else {
    			//因為已經sort過  所以接下來的值都會超過
				break;
			}
    	}
    }
    
    public static List<String> anagrams(String[] strs) {
        List<String> result = new ArrayList<String>();
        
        Map<String, Integer> myHashtable = new HashMap<String, Integer>();
        
        for(int i =0; i < strs.length; ++i){
        	//把字串裡的字母排序
        	char[] array= strs[i].toCharArray();
        	Arrays.sort(array);
        	String newStr = new String(array);
        	
        	if(myHashtable.containsKey(newStr) == false ){
        		myHashtable.put(newStr, i);
        	}
        	else{
        		//不是第3個以上的anagrams 所以只須存第i個anagram
        		if(myHashtable.get(newStr) == -1){ // -1 is used to be a flag to mean that it has stored the first anagrams string
        			result.add(strs[i]);
        		}
        		else{//存第1及第2個anagrams
        			result.add(strs[myHashtable.get(newStr)]);
        			result.add(strs[i]);
        			myHashtable.put(newStr, -1);
        		}
        	}
        	
        }
        
        return result;
    }
    
    public static boolean isAnagrams(String s1, String s2){
    	
    	if(s1.length() != s2.length())
    		return false;
    	
    	Map<Character, Integer> myHashtable = new HashMap<Character, Integer>();
    	
    	for(int i = 0; i < s1.length(); i++){
    		if(myHashtable.containsKey(s1.charAt(i)) == true){
    			int oldValue = myHashtable.get(s1.charAt(i));
    			if(oldValue == -1)
    				myHashtable.remove(s1.charAt(i));
    			else
    				myHashtable.put(s1.charAt(i), oldValue +1);
    		}
    		else{
    			myHashtable.put(s1.charAt(i), 1);
    		} 
    		
    		if(myHashtable.containsKey(s2.charAt(i)) == true){
    			int oldValue = myHashtable.get(s2.charAt(i));
    			if(oldValue == 1)
    				myHashtable.remove(s2.charAt(i));
    			else
    				myHashtable.put(s2.charAt(i), oldValue - 1);
    		}
    		else{
    			myHashtable.put(s2.charAt(i), -1);
    		} 
    	}

    	    	
    	return myHashtable.isEmpty();
    }

    public static  String multiply(String num1, String num2) {                  //    OOOOO
        String result = new String();                                           //x    OOOO
        																		//---------
    																			//    XXXXX
        if(num1.length() < num2.length()){										//   XXXXX
        	String temp = num1;													//  XXXXX
        	num1 = num2;														// XXXXX
        	num2 = temp;
        	
        }
        
        if(num2.length() == 1 || Integer.parseInt(num2) == 0)
        	return "0";
        
    	int accu = 0;
    	for(int i = 0; i < num1.length() + num2.length() - 1; i++){
    		
    		int sum = 0;
    		if(i < num2.length()){                                         //XXXX
	    		for(int j = 0; j <= i; j++){                               //XXX
	    			                                                       //XX
		    		char c1 = num1.charAt(num1.length() - 1 - (i-j));      //X
		    		char c2 = num2.charAt(num2.length() - 1 - j);
		    		
		    		int v1 = Integer.parseInt(String.valueOf(c1));
		    		int v2 = Integer.parseInt(String.valueOf(c2));
		    		
		    		sum += v1 * v2;
		    		
	    		}
    		}
    		else if(num1.length() -1 - i <= 0){                                           //   X
	    		for(int j = 0; j < num1.length() + num2.length() - 1 - i; j++){           //  XX
		    		char c1 = num1.charAt(j);                                             // XXX
		    		char c2 = num2.charAt( num1.length() + num2.length() - 2 -i -j);      //XXXX
		    		
		    		int v1 = Integer.parseInt(String.valueOf(c1));
		    		int v2 = Integer.parseInt(String.valueOf(c2));
		    		
		    		sum += v1 * v2;

	    		}
    		}
    		else{
	    		for(int j = 0; j < num2.length(); j++){                         //XX
		    		char c1 = num1.charAt(num1.length() - 1 - (i-j));           //XX
                                                                             	//XX
		    		char c2 = num2.charAt(num2.length() - 1 - j);               //XX
		    		
		    		int v1 = Integer.parseInt(String.valueOf(c1));
		    		int v2 = Integer.parseInt(String.valueOf(c2));
		    		
		    		sum += v1 * v2;
		    		
	    		}
    			
    		}
    		
    		
    		sum += accu;
    		int value = sum % 10;
    		accu = sum / 10;
    		result = result.concat(String.valueOf(value));
    	}
    	
    	if(accu != 0)
    		result = result.concat(String.valueOf(accu));
    	
    	result = new StringBuilder(result).reverse().toString();
    	
    	return result;
    }
    
    public static double pow(double x, int n) {
    	
    	if(n == 0)
    		return 1;
    	
    	double result;
    	
    	double divide = pow(x, Math.abs(n)/2);
    	
    	if(Math.abs(n) % 2 == 0){
    		result = divide * divide;
    	}
    	else{
    		result = x * divide * divide;
    	}
    	
    	if(n<0)
    		result = 1 / result;
    	
        return result;
    }
    
    public static int sqrt(int x) {
        
    	//Using binary search
    	
    	if(x < 2)
    		return x;
    	
    	int start = 1;
    	int end = x;
    	
    	while(end - start > 1){
    		int mid = start + (end - start) / 2;//不使用(start + end) / 2是為了避免 overflow

    		
    		if(mid > x / mid){
    			end = mid;
    		}
    		else if(mid < x / mid){
    			start = mid;
    		}
    		else {
				return mid;
			}
    	}
    	
    	
    	return start;
    }

    public static boolean wordBreak(String s, Set<String> dict) {

    	boolean[] table = new boolean[s.length() + 1];
    	table[s.length()] = true;
    	
    	for(int i = s.length() - 1; i >= 0; i--){
    		table[i] = false;
    		for(int j = i + 1; j <= s.length(); j++ ){
    	
	    		String sub = s.substring(i, j);

	    		if(dict.contains(sub) && table[j] == true){
	    			table[i] = true;
	    			break;
	    		}
    		}
    	}
    	return table[0]; 
    }
    
    public static List<String> wordBreakII(String s, Set<String> dict) {
    	List<String> result = wordBreakIIHelperRecursion(s, "", dict);
    	
    	Map<Integer, List<String>> myHashMap = new HashMap<>();
    	//myHashMap.put(s.length(), "")

    	
    	for(int i = s.length() - 1; i >= 0; i--){
    		List<String> resultSub = new ArrayList<>();

    		for(int j = i + 1; j <= s.length(); j++ ){
    	
	    		String sub = s.substring(i, j);

	    		if(dict.contains(sub)){
	    			if(myHashMap.containsKey(j) == true){
		    			List<String> oldResultList = myHashMap.get(j);
		    			for(int k = 0; k < oldResultList.size(); k++){
		    				String oldResult = oldResultList.get(k);
		    				oldResult = sub.concat(" ").concat(oldResult);
		    				resultSub.add(oldResult);
		    			}
	    			}
	    			else if(j == s.length()){
	    				resultSub.add(sub);
	    			}
	    		}
    		}
    		if(resultSub.isEmpty() == false)
    			myHashMap.put(i, resultSub);
    	}
    	
    	if(myHashMap.containsKey(0))
    		return myHashMap.get(0);
    	else {
			return new ArrayList<String>();
		}
    	//return result;
    }
    
    private static List<String> wordBreakIIHelperRecursion(String s, String strWithBreak, Set<String> dict) {
    	List<String> result = new ArrayList<>();
    	
    	for(int i = 1; i <= s.length(); i++){
    		String sub = s.substring(0, i);
    		String restString = s.substring(i);
    		if(dict.contains(sub)){
    			String newStrWithBreak;
    			if(strWithBreak.isEmpty())
    				newStrWithBreak = sub;
    			else
    				newStrWithBreak = strWithBreak.concat(" ").concat(sub);
    			
    			if(restString.isEmpty() == true){
    				result.add(newStrWithBreak);
    			}
    			else{
    				result.addAll(wordBreakIIHelperRecursion(restString, newStrWithBreak, dict));
    			}
    			
    		}
    	}
    	
    	return result;
    }
    
    private static List<String> wordBreakIIHelperDP(String s, String strWithBreak, Set<String> dict) {
    	List<String> result = new ArrayList<>();
    	
    	
    	
    	for(int i = 1; i <= s.length(); i++){
    		String sub = s.substring(0, i);
    		String restString = s.substring(i);
    		if(dict.contains(sub)){
    			String newStrWithBreak;
    			if(strWithBreak.isEmpty())
    				newStrWithBreak = sub;
    			else
    				newStrWithBreak = strWithBreak.concat(" ").concat(sub);
    			
    			if(restString.isEmpty() == true){
    				result.add(newStrWithBreak);
    			}
    			else{
    				result.addAll(wordBreakIIHelperRecursion(restString, newStrWithBreak, dict));
    			}
    			
    		}
    	}
    	
    	return result;
    }
    
    public static String intToRoman(int num) {
    	String result = new String();
    	int power = 1;
    	
    	while(num > 0){
    		int value = num % 10;
    		String str = intToRomanHelper(value, power);
    		result = str.concat(result);
    		num = num / 10;
    		power++;
    	}
    	
    	return result;
    }
    
    
    private static String intToRomanHelper(int value, int power){
    	String str1 = new String();
    	String str2 = new String();
    	String str3 = new String();
    	String result = new String();
    	if(power == 1){
    		str1 = "I";
    		str2 = "V";
    		str3 = "X";
    	}
    	else if(power == 2){
    		str1 = "X";
    		str2 = "L";
    		str3 = "C";
    	}
    	else if(power == 3){
    		str1 = "C";
    		str2 = "D";
    		str3 = "M";
    	}
    	else if(power == 4){
    		str1 = "M";
    		str2 = "V";
    		str3 = "A";
    	}
    	
    	switch (value) {
		case 0:
			result = "";
			break;
		case 1:
			result = str1;
			break;
			
		case 2:
			result = str1.concat(str1);
			break;
			
		case 3:
			result = str1.concat(str1).concat(str1);
			break;
			
		case 4:
			result = str1.concat(str2);
			break;

		case 5:
			result = str2;
			break;
			
		case 6:
			result = str2.concat(str1);
			break;
			
		case 7:
			result = str2.concat(str1).concat(str1);
			break;
			
		case 8:
			result = str2.concat(str1).concat(str1).concat(str1);
			break;
			
		case 9:
			result = str1.concat(str3);
			break;
			
		default:
			break;
		}
    	
    	
    	return result;	
    }

    
    public static int search(int[] A, int target) {
        
    	int start = 0;
    	int end = A.length-1;
    	
    	while(end - start > 1){
    		int mid = (start + end)/2;
    		
    		if(target == A[mid])
    			return mid;
    		else if(target > A[mid]){
    			if(A[end] < A[mid]){//pivot is located between mid and end
    				start = mid;
    			}
    			else{
    				if(target > A[end]){//target is located between start and mid
    					end = mid;
    				}
    				else{
    					start = mid;
    				}
    			}
    		}
    		else if(target < A[mid]){
    			if(A[mid] < A[start]){ //pivot is located between start and mid
    				end = mid;
    			}
    			else{
    				if(target < A[start]){//target is located between mid and end
    					start = mid;
    				}
    				else{
    					end = mid;
    				}
    			}
    		}
    	}
    	
    	if(A[start] == target)
    		return start;
    	else if(A[end] == target)
    		return end;
    	else 
    		return -1;
    }

    
    
    public static boolean search2(int[] A, int target) {
    	int start = 0;
    	int end = A.length-1;
    	
    	while(end - start > 1){
    		
    		if(A[start] == target || A[end] == target)
    	        return true;
    	    if(A[start] == A[end]){
    	        start++;
    	        end--;
    	        continue;
    	    }
    		
    		int mid = (start + end)/2;
    		
    		if(target == A[mid])
    			return true;
    		else if(target > A[mid]){
    			if(A[end] < A[mid]){//pivot is located between mid and end
    				start = mid;
    			}
    			else if(A[end] > A[mid]){
    				if(target > A[end]){//target is located between start and mid
    					end = mid;
    				}
    				else{
    					start = mid;
    				}
    			}
    			else{
    				end = mid;
    			}
    		}
    		else if(target < A[mid]){
    			if(A[mid] < A[start]){ //pivot is located between start and mid
    				end = mid;
    			}
    			else if(A[mid] > A[start]){
    				if(target < A[start]){//target is located between mid and end
    					start = mid;
    				}
    				else{
    					end = mid;
    				}
    			}
    			else{
    				start = mid;
    			}
    		}
    	}
    	
    	if(A[start] == target || A[end] == target)
    		return true;
    	else 
    		return false;
    	
       
    }

    
    
    public static int removeDuplicates(int[] A) {
        
    	if(A.length == 0)
    		return 0;
    	
    	int temp = A[0];
    	int length = A.length;
    	int index = 1;
    	
    	for(int i = 1; i < A.length; i++){
    		if(A[i] == temp){
    			length--;
    		}
    		else{
    			temp = A[i];
    			
    			A[index] = temp;
    			index++;
    		}
    	}
    	return length;
    }
    
    
    public static boolean exist(char[][] board, String word) {
    	for(int y = 0; y < board.length; y++){
    		for(int x = 0; x < board[0].length; x++){
    			
    			boolean result = existHelper(board, word, x, y);
    			if(result == true)
    				return true;
    		}
    	}
    	return false;
    }
    
    
    private static boolean existHelper(char[][]board, String word, int x, int y){
    	

    	if(board[y][x] != word.charAt(0))
    		return false;
    	else{
    		String newWord = word.substring(1);
    		if(newWord.length() == 0)
    			return true;
    		
    		board[y][x] = ' ';
    		if(x > 0){
    			boolean result = existHelper(board, newWord, x-1, y);
        		if(result == true)
        			return true;
    		}
    		
    		if(x < board[0].length-1){
    			boolean result = existHelper(board, newWord, x+1, y);
        		if(result == true)
        			return true;
    		}
    		
    		if(y > 0){
    			boolean result = existHelper(board, newWord, x, y-1);
        		if(result == true)
        			return true;
    		}
    		
    		if(y < board.length-1){
    			boolean result = existHelper(board, newWord, x, y+1);
        		if(result == true)
        			return true;
    		}
    		board[y][x] = word.charAt(0);
    	}

    	return false;
    }
 

    
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<String>();

        if(digits.length() == 0){
        	result.add("");
        	return result;
        }
        result = letterCombinationsHelper(digits, "");
        
        
        return result;
    }
    
    
    private static String numToLetter(char num){
    	String result = new String();
    	
    	switch(num){
	    	case '2':
	    		result = "abc";
	    		break;
	    	case '3':
	    		result = "def";
	    		break;
	    	case '4': 
	    		result = "ghi";
	    		break;
	    	case '5':
	    		result = "jkl";
	    		break;
	    	case '6':
	    		result = "mno";
	    		break;
	    	case '7':
	    		result = "pqrs";
	    		break;
	    	case '8':
	    		result = "tuv";
	    		break;
	    	case '9':
	    		result = "wxyz";
	    		break;
			default:
    			break;
    	
    	}
    	return result;
    }
    
    
    private static List<String> letterCombinationsHelper(String digits, String letter) {
        List<String> result = new ArrayList<String>();
        
        char c = digits.charAt(0);
        
        String str = numToLetter(c);
        for(int i =0; i <str.length(); i++){
        	
        	String newLetterComb = letter.concat(String.valueOf(str.charAt(i)));
        	
        	if(digits.length() > 1){
        		String restDigits = digits.substring(1);
        		List<String> subResult = letterCombinationsHelper(restDigits, newLetterComb);
        		result.addAll(subResult);
        	}
        	else{
        		result.add(newLetterComb);
        	}
        	
        }

        return result;
    }

    
    public static void solve(char[][] board) {
    	if(board.length == 0)
            return;
    	
    	Stack<int[]> stack = new Stack<>();
    	boolean[][] flag = new boolean[board.length][board[0].length];    	
    	for(int i = 0; i < board[0].length; i++){
    		for(int j = 0; j < board.length; j++){
    			if(i == 0 || i == board[0].length-1 || j == 0 || j == board.length-1){
    				if(board[j][i] == 'O' && flag[j][i] == false){
    					int x = i;
    					int y = j;
    					
    					flag[y][x] = true;
    					
    					int[] coord = new int[]{y, x};
    					stack.push(coord);
    					//solveHelper(board, flag, i, j);
    					
    					while(stack.empty() != true){
    						int[] currentCoord = stack.pop();
    						y = currentCoord[0];
    						x = currentCoord[1];
    						
	    			    	if(x > 0 && board[y][x-1] == 'O' && flag[y][x-1] == false){
	    			    		flag[y][x-1] = true;
	    			    		int[] newCoord = new int[]{y, x-1};
	    			    		stack.push(newCoord);
	    			    	}
	    			    	
	    			    	if(x < board[0].length-1 && board[y][x+1] == 'O' && flag[y][x+1] == false){
	    			    		flag[y][x+1] = true;
	    			    		int[] newCoord = new int[]{y, x+1};
	    			    		stack.push(newCoord);
	    			    	}
	    			    	
	    			    	if(y > 0 && board[y-1][x] == 'O' && flag[y-1][x] == false){
	    			    		flag[y-1][x] = true;
	    			    		int[] newCoord = new int[]{y-1, x};
	    			    		stack.push(newCoord);
	    			    	}
	    			    	
	    			    	if(y < board.length -1 && board[y+1][x] == 'O' && flag[y+1][x] == false){
	    			    		flag[y+1][x] = true;
	    			    		int[] newCoord = new int[]{y+1, x};
	    			    		stack.push(newCoord);
	    			    	}
    					}
    					
    					
    				}
    			}
    		}
    	}
    	
    	for(int i = 0; i < board[0].length; i++){
    		for(int j = 0; j < board.length; j++){
    			if(board[j][i] == 'O' && flag[j][i] == false){
    				board[j][i] = 'X';

				}
    		}
    	}
    	
    	boolean stop = true;
    	
    }
    
    
    private static void solveHelper(char[][] board, boolean[][] flag, int x, int y){
    	if(x > 0 && board[y][x-1] == 'O' && flag[y][x-1] == false){
    		flag[y][x-1] = true;
    		solveHelper(board, flag, x-1, y);
    	}
    	
    	if(x < board[0].length-1 && board[y][x+1] == 'O' && flag[y][x+1] == false){
    		flag[y][x+1] = true;
    		solveHelper(board, flag, x+1, y);
    	}
    	
    	if(y > 0 && board[y-1][x] == 'O' && flag[y-1][x] == false){
    		flag[y-1][x] = true;
    		solveHelper(board, flag, x, y-1);
    	}
    	
    	if(y < board.length -1 && board[y+1][x] == 'O' && flag[y+1][x] == false){
    		flag[y+1][x] = true;
    		solveHelper(board, flag, x, y+1);
    	}
    	
    }
    
    
    public static int[] searchRange(int[] A, int target) {
        
    	
    	
    	int[] result = new int[2];
        
    	if(A.length == 0){
    		A[0] = -1;
    		A[1] = -1;
    		return result;
    	}
    	
        int startStart = 0;
        int startEnd = A.length-1;
        int endStart = 0;
        int endEnd = A.length-1;

        boolean startFlag = true;
        boolean endFlag = true;
        while((startFlag == true && startEnd - startStart > 1 ) || (endFlag == true && endEnd - endStart > 1) ){
        	int startMid = (startStart + startEnd)/2;
        	int endMid = (endStart + endEnd)/2;
        	
        	if(startFlag == true){
	        	if(A[startMid] == target){
	        		if(startMid == 0 || A[startMid-1] < target){
	        			startFlag = false;
	        			result[0] = startMid;
	        		}
	        		else{
	        			startEnd = startMid;
	        		}
	        	}
	        	else if(A[startMid] > target){
	        		startEnd = startMid;
	        	}
	        	else if(A[startMid] < target){
	        		startStart = startMid;
	        	}
        	}
        	
        	if(endFlag == true){
	        	if(A[endMid] == target){
	        		if(endMid == A.length-1 || A[endMid+1] > target){
	        			endFlag = false;
	        			result[1] = endMid;
	        		}
	        		else{
	        			endStart = endMid;
	        		}
	        	}
	        	else if(A[endMid] > target){
	        		endEnd = endMid;
	        	}
	        	else if(A[endMid] < target){
	        		endStart = endMid;
	        	}
        	}
        }
        
        if(endFlag == true){
        	if(A[endEnd] == target)
        		result[1] = endEnd;
        	else if(A[endStart] == target)
        		result[1] = endStart;
        	else {
				result[1] = -1;
			}
        }
        if(startFlag == true){
        	if(A[startStart] == target)
        		result[0] = startStart;
        	else if(A[startEnd] == target)
        		result[0] = startEnd;
        	else {
				result[0] = -1;
			}
        }
        
        return result;
    }

    public static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<List<String>>();
        
        if(s.length() == 0)
        	return result;
        
        List<String> subCollection = new ArrayList<>();
        partitionHelper(result, subCollection, s);
        return result;
    }
    
    private static void partitionHelper(List<List<String>> array, List<String> subCollection, String s){
    	
    	for(int i = 1; i <= s.length(); i++){
    		String subString = s.substring(0, i);
    		
    		if(i == 1 || isPalindrome(subString) == true){ //i ==1, the single character must be a Palindrome
    			List<String> newSubCollection = new ArrayList<>(subCollection);
    			newSubCollection.add(subString);
    			
    			String restString = s.substring(i);
    			if(restString.length() != 0){
    				partitionHelper(array, newSubCollection, restString);
    			}
    			else{
    				array.add(newSubCollection);
    			}	
    		}
    	}	
    }
    
    public static int minCut(String s) {
    	
    	//This is genius
    	 int n = s.length();
         int[] cut = new int[n+1];  // number of cuts for the first k characters
         for (int i = 0; i <= n; i++) 
        	 cut[i] = i-1;
         
         for (int i = 0; i < n; i++) {


             for (int j = 0; i-j >= 0 && i+j < n && s.charAt(i-j) == s.charAt(i+j) ; j++){ // odd length palindrome 	 
                 cut[i+j+1] = Math.min(cut[i+j+1], 1+cut[i-j]);
             }
             for (int j = 1; i-j+1 >= 0 && i+j < n && s.charAt(i-j+1) == s.charAt(i+j); j++){ // even length palindrome  	 
                 cut[i+j+1] = Math.min(cut[i+j+1],1+cut[i-j+1]);
             }
         }
         return cut[n];
         
         
    	
    	/*int[] table = new int[s.length() + 1];
    	table[s.length()] = -1;
    	
    	int lastPalDivideIndex = s.length();
    	
    	for(int i = s.length() - 1; i >= 0; i--){
    		int min = Integer.MAX_VALUE;
    		
    		String sub = s.substring(i, s.length());
    		
    		if(minCutHelper_isPalindrome(sub)){
    			lastPalDivideIndex = i;
				min = 0;
    		}
    		else{
	    		for(int j = 0 ; lastPalDivideIndex - j > i ; j++ ){
	    	
		    		sub = s.substring(i, lastPalDivideIndex - j);
		    		if(minCutHelper_isPalindrome(sub)){
	    				if(table[lastPalDivideIndex - j] + 1 < min){
		    				min = table[lastPalDivideIndex - j] + 1;
		    			}
		    				
		    		}
	    		}
    		}
    		table[i] = min;
    	}
    	return table[0]; */
    	
    }
    
    private static boolean minCutHelper_isPalindrome(String s) {

    	if(s.isEmpty() == true)
    		return true;

    	
    	int index = 0;
    	while(index < s.length() / 2){
    		if(s.charAt(index) != s.charAt(s.length() - 1 - index)){
    			return false;
    		}
    		index++;
    	}
    	
        return true;
    }
    
    
    public static void nextPermutation(int[] num) {
        
    	for(int i = num.length-2; i >= 0; i--){
    		for(int j = num.length-1; j > i; j--){
    			if(num[j] > num[i]){
    				int temp = num[i];
    				
    				num[i] = num[j];
    				Stack<Integer> stack = new Stack<>(); 
    				
    				for(int k = i +1; k < num.length; k++){
    					if(k != j){
	    					stack.push(num[k]);
    					}
    				}
    				
    				boolean flag = false;
    				for(int k = i +1; k < num.length; k++){
    					if(stack.isEmpty() == true)
    						num[k] = temp;
    					else{
	    					if(temp< stack.peek() && flag == false){
	    						num[k] = temp;
	    						flag = true;
	    					}
	    					else
	    						num[k] = stack.pop();
    					}
    				}
    				
    				return;
    			}

    		}
    	}
    	
    	int startIndex = 0;
    	int endIndex = num.length-1;
    	
    	while(endIndex - startIndex > 0){
    		
    		int temp = num[startIndex];
    		num[startIndex] = num[endIndex];
    		num[endIndex] = temp;
    		
    		startIndex++;
    		endIndex--;
    	}
    	
    }
    
    
    public static int atoi(String str) {
        
    	return 0;
    }
    
    
    public static int romanToInt(String s) {
    	
    	if(s.length() == 0)
    		return 0;
    	
    	int totalValue = 0;
    	int lastDigitValue = 0;
    	for(int i = 0; i < s.length(); i++){
    		char c = s.charAt(i);
    		int value = romanToIntHelper(c);
    		totalValue += value;
    		if(i != 0 && value > lastDigitValue){

    			totalValue -= 2* lastDigitValue;
	    	}

    		lastDigitValue = value;
    	}
    	
        return totalValue;
    }
    
    
    private static int romanToIntHelper(char c){
    	switch(c){
	    	case 'I':
	    		return 1;
	    	case 'V':
	    		return 5;
	    	case 'X':
	    		return 10;
	    	case 'L':
	    		return 50;
	    	case 'C':
	    		return 100;
	    	case 'D':
	    		return 500;
	    	case 'M':
	    		return 1000;
	    		
	    	default:
    			return 0;
    	}
    }

    
    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0)
            return "";
        String longestString = "";
        String beComparedString = "";
        for(int i = 0; i <= strs[0].length(); i++){
        	beComparedString = strs[0].substring(0, i);

        	for(int j = 1; j < strs.length; j++){
        		
        		if(strs[j].length() < i){
        			return longestString;
        		}
        		else{
	        		String compareString = strs[j].substring(0, i);
	        		if(compareString.equals(beComparedString) == false){
	        			
	        			return longestString;

	        		}
        		}
        		
        	}
        	longestString = strs[0].substring(0, i);
        }
 
        return longestString;
    }
    
	
    public static boolean isValidSudoku(char[][] board) {
    	
    	HashMap<Character, Boolean> myHashMap = new HashMap<Character, Boolean>();
    	
    	for(int i = 0; i < 9; i++){
    		myHashMap.clear();
    		for(int j = 0; j < 9; j++){
    			char c = board[i][j];
    			if(c != '.'){
    				if(myHashMap.containsKey(c) == true){
    					return false;
    				}
    				else{
    					myHashMap.put(c, true);
    				}
    			}
    			
    		}
    	}
    	
    	for(int j = 0; j < 9; j++){
    		myHashMap.clear();
    		for(int i = 0; i < 9; i++){
    			char c = board[i][j];
    			if(c != '.'){
    				if(myHashMap.containsKey(c) == true){
    					return false;
    				}
    				else{
    					myHashMap.put(c, true);
    				}
    			}
    		}
    	}
    	
    	for(int m = 0; m < 3; m++){
        	for(int n = 0; n < 3; n++){
        		myHashMap.clear();
        		for(int i = m*3; i < m*3+3; i++){
            		for(int j = n*3; j < n*3+3; j++){
              			char c = board[i][j];
            			if(c != '.'){
            				if(myHashMap.containsKey(c) == true){
            					return false;
            				}
            				else{
            					myHashMap.put(c, true);
            				}
            			}
            		}
        		}
        	}
    	}
    	

    	
    	
        return true;
    }

    
    public static String convertToTitle(int n) {
        String result = new String();
    	
    	while(n > 0){
    		int restInt = (n-1) % 26;
    		String str = Character.toString((char)(restInt+65));
    		result = str.concat(result);
    		
    		n = (n-1)/26;
    	}
    	
    	return result;
    }

    
    public static int strStr(String haystack, String needle) {
    	if(needle.length() > haystack.length())
        	return -1;
        	
        if(haystack.length() == 0 && needle.length() == 0)
        	return 0;
        
        for(int i = 0; i <= haystack.length()-needle.length(); i++){
        	String subHaystack = haystack.substring(i, i + needle.length());
        	
        	if(subHaystack.equals(needle) == true)
        		return i;
        	
        }
    	
    	return -1;
    }
    
    
    public static int compareVersion(String version1, String version2) {
        
    	int index1 = 0;
    	int index2 = 0;
    	
    	boolean version1Flag = true;
    	boolean version2Flag = true;
    	
    	while(version1.length() != 0 || version2.length() != 0){
	    	
    		int value1;
    		int value2;
    		if(version1Flag == true){
	    		while(index1 < version1.length() && version1.charAt(index1) != '.'){
		    		index1++;
		    	}
		    	String str1 = version1.substring(0, index1);
		    	value1 = Integer.parseInt(str1);
    		}
    		else {
				value1 = 0;
			}
    		if(version2Flag == true){
		    	while(index2 < version2.length() && version2.charAt(index2) != '.'){
		    		index2++;
		    	}
		    	String str2 = version2.substring(0, index2);
		    	value2 = Integer.parseInt(str2);
    		}
    		else{
    			value2 = 0;
    		}
    			

	    	if(value1 > value2)
	    		return 1;
	    	else if(value1 < value2)
	    		return -1;
	    	else{
	    		
	    		if(version1Flag == true){
		    		if(index1 == version1.length()){
		    			version1Flag = false;
		    		}
		    		else{
		    			version1 = version1.substring(index1+1);
		    			index1 = 0;
		    		}
	    		}
	    		
	    		if(version2Flag == true){
		    		if(index2 == version2.length()){
		    			version2Flag = false;
		    		}
		    		else{
		    			version2 = version2.substring(index2+1);
		    			index2 = 0;
		    		}
	    		}

	    		if(version1Flag == false && version2Flag == false)
	    			return 0;

	    	}
    	}
    	return 0;
    }

    
    public static String convert(String s, int nRows) {
    	String result = new String();
    	if(nRows == 0)
    		return result;
    	if(nRows == 1)
    		return s;
    	
    	for(int i = 0; i < nRows; i++){
    		String rowString = new String();
    		if(i == 0 || i == nRows-1){
    			
    			for(int j = i; j < s.length(); j += 2*(nRows-1)){
    				rowString = rowString.concat(String.valueOf(s.charAt(j)));
    			}
    		}
    		else{
    			
    			int j = i;
    			boolean oddEven = true;
    			while(j < s.length()){
    				rowString = rowString.concat(String.valueOf(s.charAt(j)));
    				
    				if(oddEven == true){
    					j += 2*(nRows-1-i);
    					
    					oddEven = false;
    				}
    				else{
    					j += 2*i;
    					
    					oddEven = true;
    				}
    			}
    		}
    		
    		result = result.concat(rowString);
    	}
    	
    	
        return result;
    }

    
    public static List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<Integer>();
        if(rowIndex<0)
        	return result;
        
        if(rowIndex == 0){
        	result.add(1);
        	return result;
        }
        
        int[] array = new int[rowIndex];
        array[0] = 1;
        for(int i = 1; i <= rowIndex; i++){
        	int tempStore=1;
        	for(int j = 0; j <= i; j++){
        		if(j == 0){
        			
        			if(i == rowIndex){
        				result.add(1);
        			}
        			else{
        				array[j] = 1;
        			}

        		}
        		else if(j == i){
        			
        			if(i == rowIndex){	
        				result.add(1);
        			}
        			else{
        				array[j-1] = tempStore;
        				array[j] = 1;
        			}
        		}
        		else{
        			
        			if(i == rowIndex){
        				result.add(array[j] + array[j-1]);
        				
        			}
        			else{
        			
	        			int temptemp = tempStore;
	        			tempStore = array[j] + array[j-1];
	        			
	        			array[j-1] = temptemp;
        			}
        			
        		}
        	}
        }
        
        
        return result;
    }

	
    public static int jump(int[] A) {
    	
    	int count = 0;
    	int index = 0;
    	int endIndex = A.length-1;
    	
    	while(endIndex > 0){
	    	if(A[index] >= endIndex - index){
	    		endIndex = index;
	    		index = 0;
	    		count++;
	    	}
	    	else{
	    		index++;
	    	}
    	}
    	
        return count;
    }

    
    public static int majorityElement(int[] num) {
        
        if(num.length == 1)
            return num[0];
    	HashMap<Integer, Integer> myHashMap = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < num.length; i++){
    		if(myHashMap.containsKey(num[i]) == true){
    			int count = myHashMap.get(num[i]);
    			if(count+1 > num.length/2)
    				return num[i];
    			else
    				myHashMap.put(num[i], count+1);
    		}
    		else {
    			myHashMap.put(num[i], 1);
			}
    	}
    	
    	
    	return 0;
    }

    
    public static String getPermutation(int n, int k){
    	String result = new String();
    	int count = 1;
    	int[] array = new int[n];
    	for(int i = 0; i < n; i++){//create array for 1~n
    		array[i] = i+1;
    		
    		count = count * (i+1); 
    	}
    	
    	//從array[0]開始  看array[0]應該要塞哪個數  塞完之後把剩餘的array向右填滿 12345 先變成 32_45 把2右移填滿 變成 3_245再把一開始的1補進去 變成31245 接著再看array[1]要改成哪個數
    	for(int index = 0 ; index < n; index++){
    		count = count / (n-index);//make count become (n-1)!, (n-2)!, (n-3)!......
    		int v = (k-1) / count;
    		k= k - v * count;
    		if(v != 0){
	    		int temp = array[index];
	    		array[index] = array[index + v];
	    		int shift = 0;
	    		while(v-1-shift > 0){
	    			array[index + v -shift] = array[index + v -shift -1];
	    			shift++;
	    		}
	    		array[index+1] = temp;
    		}
    		
    		if(k == 0)
    			break;
    	}
    	for(int i = 0 ; i < n; i++){
    		result = result.concat(String.valueOf(array[i]));
    	}

    	return result;
    }
    
    
    
    public static void solveSudoku(char[][] board) {
        
    	solveSudokuHelper(board, 0);

    }
    
    
	private static boolean solveSudokuHelper(char[][] board, int pos){
    	
    	if(pos > 80)
    		return true;
    	
    	int i = pos /9;
    	int j = pos %9;
    	
    	//遇到空格就先填一個目前不會有衝突的數(從1開始到9檢查)  之後遇到有衝突就replace成下一個順位的

		if(board[i][j] == '.'){
			for(int c = 1; c <= 9; c++){
				if(isRowValid((char)(c + 48), board, i) == true && isColumnValid((char)(c + 48), board, j) == true && isSquareValid((char)(c + 48), board, i, j) == true){
					board[i][j] = (char)(c+48);
					
					if(solveSudokuHelper(board, pos+1) == true)
						return true;
					else{
						board[i][j] = '.';
					}
				}
			}
			return false;
		}
		else{
			if(solveSudokuHelper(board, pos+1) == true)
				return true;
			else{
				return false;
			}
		}
    }
    
    
    private static boolean isRowValid(char c, char[][] board, int row){
    	for(int i = 0; i < 9; i++){
    		char charInRow = board[row][i];
    		if(charInRow == c)
    			return false;
    	}
    	
    	return true;
    }
    
    
    private static boolean isColumnValid(char c, char[][] board, int column){
    	for(int i = 0; i < 9; i++){
    		char charInColumn = board[i][column];
    		if(charInColumn == c)
    			return false;
    	}
    	
    	return true;
    }
    
    
    private static boolean isSquareValid(char c, char[][] board, int row, int column){

    	int m = row / 3;
    	int n = column /3;
    	
		for(int i = m*3; i < m*3+3; i++){
    		for(int j = n*3; j < n*3+3; j++){
        		char charInSquare = board[i][j];
        		if(charInSquare == c)
        			return false;
    		}
		}
    	return true;
    }

    
    public static boolean isInterleave(String s1, String s2, String s3) {
    	
    	Map<Integer, Boolean> myHashtable = new HashMap<Integer, Boolean>();
    	
    	
    	char c3 = s3.charAt(0);
    	for(int i = 0; i < s1.length(); i++){
    		char c1 = s1.charAt(i);
    		if(c3 == c1){
    			boolean result = isInterleaveHelper(s1, s2, s3, i+1, 0, 1, myHashtable);
    			if(result == true)
    				return true;
    		}
    	}
    	
    	for(int i = 0; i < s2.length(); i++){
    		char c2 = s2.charAt(i);
    		if(c3 == c2){
    			boolean result = isInterleaveHelper(s1, s2,s3, 0,i+1, 1, myHashtable);
    			if(result == true)
    				return true;
    		}
    	}
    	
    	return false;
    }
    
    
    public static boolean isInterleaveHelper(String s1, String s2, String s3, int index1, int index2, int index3, Map<Integer, Boolean> myHashtable ) {
    	
    	if(myHashtable.containsKey(index1 * s3.length() + index2) == true)
    		return false;
    	

		if(index1 == s1.length()){
			s2 = s2.substring(index2);
			s3 = s3.substring(index3);
			return s2.equals(s3);
		}
		else if(index2 == s2.length()){
			s1 = s1.substring(index1);
			s3 = s3.substring(index3);
			return s1.equals(s3);
		}
    		
		
    	
		
		char c1 = s1.charAt(index1);
		char c2 = s2.charAt(index2);
		char c3 = s3.charAt(index3);
		
		if(c1 == c3 && c2 != c3){
			return isInterleaveHelper(s1, s2, s3, index1+1, index2, index3+1, myHashtable);
		}
		else if(c1 != c3 && c2 == c3){
			return isInterleaveHelper(s1, s2, s3, index1, index2+1, index3+1, myHashtable);
		}
		else if(c1 == c3 && c2 == c3){
			
			boolean interleave1 = isInterleaveHelper(s1, s2, s3, index1+1, index2, index3+1, myHashtable);
			if(interleave1 == true)
				return true;
			
			boolean interleave2 = isInterleaveHelper(s1, s2, s3, index1, index2+1, index3+1, myHashtable);
			if(interleave2 == true)
				return true;
			
		}
    	
		myHashtable.put(index1 * s3.length() + index2, true);
    	return false;
    }

    
    public static boolean isMatch(String s, String p) {
    	
    	
    	
    	
        return true;
    }
    
    
    public static boolean isMatchHelper(String s, String p, int indexS, int indexP) {
    	
    	if(indexS == s.length()){
    		return indexP == p.length();
    	}
    	
		char cs = s.charAt(indexS);
		char cp = p.charAt(indexP);
		
		if(cp != '.' && cp != '*'){
			if(cs != cp)
				return false;
			else{
				indexP++;
				indexS++;
			}
		}
		
		if(cp == '.'){
			indexP++;
			indexS++;
		}    		
		else if(cp == '*'){
			
		}
    		
    	return true;
    }
    
    
    public static String minWindow(String S, String T) {
    	
        if (S.length()==0||T.length()==0||S.length()<T.length()) return "";

        int left=T.length(),start=-1,end=S.length();

        Deque<Integer> queue= new LinkedList<Integer>();

        Map<Character,Integer> map= new HashMap<Character,Integer>();

        for (int i=0;i<T.length();i++){
            char c= T.charAt(i);
            map.put(c,map.containsKey(c)?map.get(c)+1:1);
        }

        for (int i =0;i<S.length();i++){
            char c= S.charAt(i);
            if (!map.containsKey(c))
                continue;

            int n = map.get(c);
            map.put(c,n-1);
            queue.add(i);
            if (n>0) left--;

            char head = S.charAt(queue.peek());
            while(map.get(head)<0){
                queue.poll();
                map.put(head,map.get(head)+1);
                head=S.charAt(queue.peek());
            }

            if (left==0){
                int new_length=queue.peekLast()-queue.peek()+1;
                if (new_length<end-start) {
                    start=queue.peek();
                    end=queue.peekLast()+1;
                } 
            }
        }
        if (left==0)  return S.substring(start,end);
        else return "";
    	
    	
    	
        /*int indexStart = -1;
        int indexEnd = 0;
        int totalTargetLength = T.length();
        
        Map<Character, Integer> myHashMap = new HashMap<>();
        
        for(int i = 0; i < T.length(); i++){
        	if(myHashMap.containsKey(T.charAt(i))){
        		int count = myHashMap.get(T.charAt(i));
        		myHashMap.put(T.charAt(i), count+1);
        	}
        	else{
        		myHashMap.put(T.charAt(i), 1);
        	}
        }
        
        for(int i = 0; i < S.length(); i++){
        	char c = S.charAt(i);
        	if(myHashMap.containsKey(c) == true){
        		indexStart = i;
        		int count = myHashMap.get(c);
        		myHashMap.put(c, count-1);
        		totalTargetLength--;
        		break;
        	}
        }
        if(indexStart == -1)
        	return "";
        
        indexEnd = indexStart+1;
        while(indexEnd < S.length()){
        	char c = S.charAt(indexEnd);
        	if(myHashMap.containsKey(c) == true){
        		int count = myHashMap.get(c);
        		if(count > 0){
        			totalTargetLength--;
        		}
        		myHashMap.put(c, count-1);
        		
        	}
        	
        	if(totalTargetLength == 0){
        		break;
        	}
        	
        	indexEnd++;
        }
        
        if(totalTargetLength != 0)
        	return "";
        
        while(indexEnd < S.length()){
        	char s = S.charAt(indexStart);
        	if(myHashMap.containsKey(s) == true){
        		int count = myHashMap.get(s);
        		myHashMap.put(s, count+1);
        	}
        	
        	
        	
        	
        	indexStart++;
        	indexEnd++;
        	char e = S.charAt(indexEnd);
        	
        }
    	
    	return "";*/
    }
    
    
    public static int numDistinct(String S, String T) {
        return 0;
    }
    
    
    public static int minDistance(String word1, String word2) {
    	
    	//  [5] 1
    	//  [4] d
    	//  [3] r
    	//i [2] o
    	//  [1] w
    	//  [0] #
    	//        #  w  o  r  d  2
    	//       [0][1][2][3][4][5]
    	//              j
    	int[] array = new int[word2.length() + 1];
    	
    	for(int i = 0; i <= word1.length(); i++){
    		int lastRowValue = 0;
    		for(int j = 0; j <= word2.length(); j++){
    			
    			
    			if(i == 0){
    				array[j] = j;
    			}
    			else{
	    			if(j == 0){
	    				lastRowValue = array[j];
	    				array[j] = i;
	    			}
	    			else{
	    				if(word1.charAt(i-1) == word2.charAt(j-1) ){
	    					int temp = array[j];
	    					array[j] = lastRowValue;
	    					lastRowValue = temp;
	    				}
	    				else{
	    					int temp = array[j];
	    					array[j] = Math.min(Math.min(array[j], array[j-1]), lastRowValue) + 1;
	    					lastRowValue = temp;
	    				}
	    			}
	    			
    			}
    		}
    	}
    	
    	return array[word2.length()];
    }
    
    
    
    public static boolean isScramble(String s1, String s2) {
    	
    	if(s1.length() != s2.length())
    		return false;
    	if(s1.equals(s2))
    		return true;
    	
    	//加速  重要
    	char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        if(Arrays.equals(c1, c2) != true) 
        	return false;
    	
    	
    	for(int i = 1; i < s1.length(); i++){
    		String subS1Front = s1.substring(0, i);
    		String subS1FrontCouple = s1.substring(i);
    		
    		
    		String subS2Front = s2.substring(0, i);
    		String subS2FrontCouple = s2.substring(i);
    		
    		String subS2BackCouple = s2.substring(0, s2.length() - i);
    		String subS2Back = s2.substring(s2.length() -i);
    		
    		if(isScramble(subS1Front, subS2Front) == true && isScramble(subS1FrontCouple, subS2FrontCouple) == true)
    			return true;
    		
    		if(isScramble(subS1Front, subS2Back) == true && isScramble(subS1FrontCouple, subS2BackCouple) == true)
    			return true;
    		
    	}
    	
    	
        return false;
    }
   
    
    public static int maximumGap(int[] num) {
   	
    	
    	if(num.length < 2)
    		return 0;
    	if(num.length == 2)
    		return Math.abs(num[0] - num[1]);
    	
    	int min = num[0];
    	int max = num[0];
    	for(int i = 0; i < num.length; i++){
    		if(num[i] < min)
    			min = num[i];
    		if(num[i] > max)
    			max = num[i];
    	}
    	
    	int[] minInBoxes = new int[num.length-1];
    	int[] maxInBoxes = new int[num.length-1];
    	
    	Arrays.fill(minInBoxes, Integer.MAX_VALUE);
    	Arrays.fill(maxInBoxes, Integer.MIN_VALUE);
    	

    	int div = (int)Math.ceil((double)(max - min)/(num.length - 1));
    	
    	for(int i = 0; i < num.length; i++){
    		if(num[i] == min || num[i] == max)
    			continue;
    		
    		int boxIndex = (num[i] - min) / div;
    		
    		minInBoxes[boxIndex] = Math.min(minInBoxes[boxIndex], num[i]);
    		maxInBoxes[boxIndex] = Math.max(maxInBoxes[boxIndex], num[i]);
    		
    	}
    	
    	int maxGap = Integer.MIN_VALUE;
    	int preBoxMax = min;
    	//go through every box, there are num.length-1 boxes
    	for(int i = 0; i < num.length-1; i++){
    		if (minInBoxes[i] == Integer.MAX_VALUE)
                continue;// empty bucket
    		
    		if(minInBoxes[i] - preBoxMax > maxGap)
    			maxGap = minInBoxes[i] - preBoxMax;
    		
    		preBoxMax = maxInBoxes[i];
    	}
    	
    	maxGap = Math.max(maxGap, max - preBoxMax); // updata the final max value gap
    	return maxGap;
    }
    
    
    public static int candy(int[] ratings) {
        
    	if(ratings.length == 0)
    		return 0;
    	if(ratings.length == 1)
    		return 1;
    	
    	int totalCandy = 1;
    	int lastCandy = 1;
    	int descendingLength = 1;
    	int maxHeight = 1;

       		
    	
    	for(int i = 1; i < ratings.length; i++){
    		if(ratings[i] > ratings[i-1]){//slope up
    			lastCandy++;
    			totalCandy += lastCandy;
    			descendingLength = 1;
    			maxHeight = lastCandy;
    		}
    		else if(ratings[i] < ratings[i-1]){

    			if(descendingLength == maxHeight){
    				descendingLength++;
    			}
    			
    			totalCandy += descendingLength;
    			descendingLength++;
    			lastCandy = 1;
        		

    		}
    		else if(ratings[i] == ratings[i-1]){
				lastCandy = 1; 
				totalCandy += lastCandy;

				descendingLength =1;
				maxHeight = lastCandy;
    		}
    		
    	}
    	

    	
    		
    	
    	
    	return totalCandy;
    }
    
    
    public static int longestConsecutive(int[] num) {
        
    	if(num.length == 0)
    		return 0;
    	
    	Map<Integer, Integer> myHashMap = new HashMap<Integer, Integer>();
    	int max = 1;
    	for(int i = 0; i < num.length; i++){
    		if(myHashMap.containsKey(num[i]) == true){
    			continue;
    			
    		}
    		else if(myHashMap.containsKey(num[i]+1) == true && myHashMap.containsKey(num[i]-1) == true){
    			int leftConsecutiveCount = myHashMap.get(num[i] -1);
    			int rightConsecutiveCount = myHashMap.get(num[i] +1);
    			myHashMap.put(num[i] - leftConsecutiveCount, leftConsecutiveCount + rightConsecutiveCount + 1);
    			myHashMap.put(num[i] + rightConsecutiveCount, leftConsecutiveCount + rightConsecutiveCount + 1);
    			myHashMap.put(num[i], leftConsecutiveCount + rightConsecutiveCount + 1);
    			if(leftConsecutiveCount + rightConsecutiveCount + 1 > max)
    				max = leftConsecutiveCount + rightConsecutiveCount + 1;
    			
    		}
    		else if(myHashMap.containsKey(num[i]+1) == true && myHashMap.containsKey(num[i]-1) == false){
    			int rightConsecutiveCount = myHashMap.get(num[i] +1);
    			myHashMap.put(num[i], rightConsecutiveCount + 1);
    			myHashMap.put(num[i] + rightConsecutiveCount, rightConsecutiveCount + 1);
    			if(rightConsecutiveCount + 1 > max)
    				max = rightConsecutiveCount + 1;
    		}
    		else if(myHashMap.containsKey(num[i]+1) == false && myHashMap.containsKey(num[i]-1) == true){
    			int leftConsecutiveCount = myHashMap.get(num[i] -1);
    			myHashMap.put(num[i], leftConsecutiveCount + 1);
    			myHashMap.put(num[i] - leftConsecutiveCount, leftConsecutiveCount + 1);
    			if(leftConsecutiveCount + 1 > max)
    				max = leftConsecutiveCount + 1;
    		}
    		else if(myHashMap.containsKey(num[i]+1) == false && myHashMap.containsKey(num[i]-1) == false){
    			myHashMap.put(num[i], 1);
    		}
    	}
    	
    	return max;
    }

    
    public static int longestValidParentheses(String s) {
        
    	
    	Stack<Integer> stack = new Stack<Integer>(); //positions of '('
        int maxLen = 0; //longest valid parentheses

        for (int i = 0; i < s.length(); ++i) { // for every character in s
            if (s.charAt(i) == '(') {
                stack.push(i);
            }
            else { //it's ')'
                if (!stack.empty() && s.charAt(stack.peek()) == '(') {
                    stack.pop(); //encountered a valid '()'

                    int lastPos = -1;
                    if (!stack.empty())
                        lastPos = stack.peek();

                    int curLen = i - lastPos;

                    maxLen = Math.max(maxLen, curLen);
                } else
                    stack.push(i);
            }
        }
        return maxLen;
    }
    
    
    public static int maxPoints(Point[] points) {
    	
    	if(points.length < 3)
    		return points.length;
    	
    	int overlap = 0;
    	int pointsOnLine = 0;
    	int maxPointsOnLine = 0;
    	
    	for(int i = 0; i < points.length; i++){
    		for(int j = i+1; j < points.length; j++){
    			pointsOnLine = 1;
    			int x = points[j].x - points[i].x;
    			int y = points[i].y - points[j].y;
    			
    			if(x == 0 && y == 0){
    				overlap++;
    			}
    			else{
    				pointsOnLine++;
    				
	    			for(int k = j+1; k < points.length; k++){
	    				
	    				int dx = points[k].x - points[i].x;
	    				int dy = points[k].y - points[i].y;
	    				
	    				if(dy * x + dx * y == 0)
	    					pointsOnLine++;
	    			}
	    			
	    			
    			}
    			if(pointsOnLine + overlap > maxPointsOnLine)
    				maxPointsOnLine = pointsOnLine + overlap;
    			
    		}
    		overlap = 0;
    		
    	}
    	
    	return maxPointsOnLine;
    }

    
    public static int trap(int[] A) {
        
    	if(A.length < 3)
    		return 0;
    	
    	int[] trapPillarIndex = new int[A.length];
    	trapPillarIndex[0] = 0;
    	
    	for(int i = 1;i < A.length; i++){
    		
    		int currentHighestIndex = i-1;
    		int preHigherIndex = i-1;
    		while(A[i] > A[preHigherIndex] && preHigherIndex > 0){
    			preHigherIndex--;
    			
    			if(A[preHigherIndex] > A[currentHighestIndex])
    				currentHighestIndex = preHigherIndex;
    			
    		}
    		trapPillarIndex[i] = currentHighestIndex;
    		//boolean stop = true;
    	}
    	
    	
    	int[] trapWaterArray = new int[A.length];
    	
    	
    	for(int i = 1;i < A.length; i++){
    		int trapWater = (i - trapPillarIndex[i] - 1) * Math.min(A[trapPillarIndex[i]], A[i]);
    		for(int j = trapPillarIndex[i] + 1; j < i; j++){
    			trapWater -= A[j];
    			trapWaterArray[j] = 0;
    		}
    		trapWaterArray[i] = trapWater;
    	}
    	
    	int totalTrapWater = 0;
    	for(int i = 1;i < A.length; i++){
    		totalTrapWater += trapWaterArray[i];
    	}
    	
    	return totalTrapWater;
    }
    
    
    public static int largestRectangleArea(int[] height) {
        
    	if(height.length == 0)
    		return 0;
    	
    	int maxArea = 0;
    	
    	int startIndex = height.length;
    	for(int i = 0; i < height.length; i++){
    		if(height[i] != 0){
    			maxArea = height[i];
    			startIndex = i;
    			break;
    		}
    	}
    	
    	for(int i = startIndex; i < height.length; i++){
    		

    		int heightValue = height[i];
    			
    		int k = 0;
    		for(int j = i; j >= 0; j--){
    			
    			
    			if(j == i){			
	    			k = i;
	    			while(k >= 0 && height[k] >= heightValue){
	    				k--;
	    				
	    			}
	    			int area = (i - k) * heightValue;
	    			if(area>maxArea)
	    				maxArea = area;
    			}
    			else if(height[j] < heightValue){
    				

    				heightValue = height[j];
        			if(heightValue <= maxArea / (i+1))//表示就算一路通到[0] 面積也不夠大
        				break;
        			
        			

	    			while(k >= 0 && height[k] >= heightValue){
	    				k--;
	    				
	    			}
	    			int area = (i - k) * heightValue;
	    			if(area>maxArea)
	    				maxArea = area;
    				
    			}
    		}
    		
    	}
    	
    	
    	return maxArea;
    }

    
    public static int largestRectangleAreaFast(int[] height){
    	int max_area = 0; // Initalize max area
        int tp;  // To store top of stack
        int area_with_top; 
    	
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        while(i < height.length){
        	if (stack.empty() || height[stack.peek()] <= height[i]){
        		stack.push(i);
                i++;
        	}
        	else{
        		tp = stack.pop();  // store the top index
                // Calculate the area with height[tp] stack as smallest bar
        		if(stack.empty() == true)
        			area_with_top = height[tp] * i;
        		else
        			area_with_top = height[tp] * (i - stack.peek() - 1);
     
                // update max area, if needed
                if (max_area < area_with_top)
                    max_area = area_with_top;
        	}
        }
        
        while (stack.empty() == false)
        {
            tp = stack.pop();
            if(stack.empty() == true)
            	area_with_top = height[tp] * i;
            else	
            	area_with_top = height[tp] * (i - stack.peek() - 1);
     
            if (max_area < area_with_top)
                max_area = area_with_top;
        }
     
        return max_area;
        
    }
    
    public static List<String[]> solveNQueens(int n) {
    	
    	
    	List<String[]> result = new ArrayList<String[]>();
    	if(n == 1){
    	    String[] resultStr = new String[1];
    	    resultStr[0] = "Q";
    	    result.add(resultStr);
    	    return result;
    	}
    	
    	if(n < 4)
    		return result;
    	
    	
    	char[][] board = new char[n][n];
    	for(int i = 0; i < n; i++){
    		for(int j = 0; j < n ; j++){
    			board[i][j] = '.';
    		}
    	}
    	
    	solveNQueensHelper(result, n, board, 0);
    	return result;
    }

    
    private static void solveNQueensHelper(List<String[]> result, int n, char[][] board, int posY){

    	for(int j = 0; j < n; j++){
    		boolean validFlag = true;
    		if(validFlag == true){   //Check for vertical
    	    	for(int i = posY-1; i >= 0; i--){
    	    		if(board[i][j] == 'Q'){
    	    			validFlag = false;
    	    			break;
    	    		}
    	    	}
        	}
    		
    		
    		if(validFlag == true){   //Check for 斜線
    			for(int i = 1; i <= posY; i++){
    				if((j+i < n && board[posY -i][j + i] == 'Q') || (j-i >= 0 && board[posY -i][j - i] == 'Q')){
    	    			validFlag = false;
    	    			break;
    	    		}
    			}
    		}
    		
    		if(validFlag == true){
    			board[posY][j] = 'Q';
    			
    			if(posY == n-1){
    				String[] solution = new String[n];
    				for(int s = 0; s < n; s++){
    					String str = new String();
    					for(int c = 0; c < n; c++){
    						str = str.concat(String.valueOf(board[s][c]));
    					}
    					solution[s] = str;
    				}
    				
    				result.add(solution);
    			}
    			else{
    				solveNQueensHelper(result, n, board, posY+1);
    			}		
    		}

    		board[posY][j] = '.';

    	}
    }
    
    public static int totalNQueens(int n) {
    	
    	char[][] board = new char[n][n];
    	for(int i = 0; i < n; i++){
    		for(int j = 0; j < n ; j++){
    			board[i][j] = '.';
    		}
    	}
    	int result = totalNQueensHelper(n, 0, board, 0);
    	return result;
    }
    
    private static int totalNQueensHelper(int n, int solutionNum,  char[][] board, int posY){

    	for(int j = 0; j < n; j++){
    		boolean validFlag = true;
    		if(validFlag == true){   //Check for vertical
    	    	for(int i = posY-1; i >= 0; i--){
    	    		if(board[i][j] == 'Q'){
    	    			validFlag = false;
    	    			break;
    	    		}
    	    	}
        	}
    		
    		
    		if(validFlag == true){   //Check for 斜線
    			for(int i = 1; i <= posY; i++){
    				if((j+i < n && board[posY -i][j + i] == 'Q') || (j-i >= 0 && board[posY -i][j - i] == 'Q')){
    	    			validFlag = false;
    	    			break;
    	    		}
    			}
    		}
    		
    		if(validFlag == true){
    			board[posY][j] = 'Q';
    			
    			if(posY == n-1){
    				solutionNum += 1;//result.add(solution);
    			}
    			else{
    				solutionNum = totalNQueensHelper(n, solutionNum, board, posY+1);
    			}		
    		}

    		board[posY][j] = '.';

    	}
    	
    	return solutionNum;
    }

    public static int titleToNumber(String s) {
        
    	int length = s.length();
    	int totalNum = 0;
    	
    	for(int i = 0; i < length; i++){
    		int num = (int)s.charAt(i) -64 ;
    		totalNum += num * Math.pow(26, length - i - 1);
    	}
    	
    	return totalNum;
    }
    
    public static int trailingZeroes(int n) {
        
    	
    	int totalZeros = 0;
    	
    	int five = 5;
    	while(n / five > 0){
    		totalZeros += n / five;
    		n /= 5;
    	}
    	
    	//Does not work because of the overflow  five *= 5;
    	/* 
    	while(n / five > 0){
    		totalZeros += n / five;
    		five *=5;
    	}
    	*/
    	return totalZeros;
    }

    public static int firstMissingPositive(int[] A) {
    	
    	for(int i = 0; i < A.length; i++){
    		int num = A[i];
    		
    		while (num <= A.length && num > 0 && A[num - 1] != num){
                A[i] = A[num-1];
                A[num-1] = num;
                
                num = A[i];
            }
    	}
    	
    	for (int i = 0; i < A.length; ++i){
            if (A[i] != i + 1){
                return i + 1;
            }
        }
        return A.length + 1;
    }
    
    public static int calculateMinimumHP(int[][] dungeon) {
        
        int height = dungeon.length;
        int width = dungeon[0].length;
        
        int[][] minHP = new int[height][width];
        
        for(int i = height - 1; i >= 0; i--){
            for(int j = width - 1; j >= 0; j--){
                if(i == height - 1 && j == width - 1){
                    minHP[i][j] = Math.max(1, 1 - dungeon[i][j]);
                }
                else if(j == width -1){
                    minHP[i][j] = Math.max(1, minHP[i+1][j] - dungeon[i][j]);
                }
                else if(i == height -1){
                    minHP[i][j] = Math.max(1, minHP[i][j+1] - dungeon[i][j]);
                }
                else{
                    minHP[i][j] = Math.max(1, Math.min(minHP[i+1][j], minHP[i][j+1]) - dungeon[i][j]);
                }
                
            }
        }
        
        return minHP[0][0];
    }
    
    public static String largestNumber(int[] num) {
        if(num==null || num.length==0)
            return "";
        String[] Snum = new String[num.length];
        for(int i=0;i<num.length;i++)
            Snum[i] = num[i]+"";

        Comparator<String> comp = new Comparator<String>(){
            @Override
            public int compare(String str1, String str2){
                String s1 = str1+str2;
                String s2 = str2+str1;
                return s1.compareTo(s2);
            }
        };

        Arrays.sort(Snum,comp);
        if(Snum[Snum.length-1].charAt(0)=='0')
            return "0";

        String result = "";
        for(int i = 0; i < Snum.length; i++){
        	result = result.concat(Snum[Snum.length - 1 - i]);
        }

        return result;

    }
    
    public static List<String> findRepeatedDnaSequences(String s) {

    	Set<Integer> words = new HashSet<>();
        Set<Integer> doubleWords = new HashSet<>();
        List<String> result = new ArrayList<>();

        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('A', 0);
        map.put('C', 1);
        map.put('G', 2);
        map.put('T', 3);
        
    
        for(int i = 0; i+10 <= s.length(); i++){
        	
        	int value = 0;
        	for(int j = i; j < i+10; j++){
        		value = value << 2;
        		value += map.get(s.charAt(j));
        		
        	}
        	
            /*if(!words.add(value) && doubleWords.add(value)) {
                result.add(s.substring(i, i + 10));
            }*/
        	if(!words.add(value)){
        		if(doubleWords.add(value)){
                    result.add(s.substring(i, i + 10));
                }
                
            }

        }
        return result;
  
    }
}
