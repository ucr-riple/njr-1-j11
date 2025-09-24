package algorithm.recursion;

public class MainRecursion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fibonacciTest();
//		find2sRTest();
	}
	
	private static void find2sRTest(){
		System.out.print("****Find how many 2 between 0 to n ****" + "\n");
		int index = 77;
		System.out.print("There are " + RecursionTest.count2sR(index) + " 2s betweem 0 to " + index);
	}

	
	private static void fibonacciTest(){
		System.out.print("****Test Fibonacci Generation****" + "\n");
		int index = 6;
		System.out.print("The " + index + " fibonacci number is " + RecursionTest.generateFibonacciNum(index));

	}
}
