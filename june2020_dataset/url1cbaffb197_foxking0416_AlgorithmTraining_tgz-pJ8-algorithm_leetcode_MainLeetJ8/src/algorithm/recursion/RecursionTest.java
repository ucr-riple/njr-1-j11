package algorithm.recursion;

import javax.swing.text.Position;

public class RecursionTest {

	
	
	public static int generateFibonacciNum(int index){
		
		
		int k = (-1 >> 31);
		System.out.print("k = " + k);
		
		if(index == 0)
			return 0;
		else if(index == 1)
			return 1;
		else{
			return generateFibonacciNum(index-1) + generateFibonacciNum(index-2);
		}
	}
	
	public static int count2sR(int num){
		
		if(num <= 10){
			if(num >= 2)
				return 1;
		}
		
		int power = 1;
		int numOf2 = 0;
		while(num / power > 10){
			power *= 10;
		}
		
		int first = num / power;
		numOf2 += first * 2 * power;
		
		int rest = num % power;
		
		while(rest / 10 > 1){
			power /= 10;
			first = rest / power;
			numOf2 += first * 2 * power;
			
			rest = rest % power;
		}
		
		if(rest >= 2)
			++numOf2;
		
//		System.out.print("power = " + power);
		
		return numOf2;
	}
}
