package algorithms;

import org.junit.Assert;
import org.junit.Test;

public class ArrayOperationsTest {

	@Test
	public void testReverseArrayEvenSize(){
		System.out.println(1+2+"3");
		int sourceArray[]={1,2,3,4};
		int expectedArray[]={4,3,2,1};
		int[] reversedArray=ArrayOperations.reverseArray(sourceArray);
		Assert.assertArrayEquals(expectedArray,reversedArray);
	}
	
	@Test
	public void testReverseArrayOddSize(){
		int sourceArray[]={1,2,3};
		int expectedArray[]={3,2,1};
		int[] reversedArray=ArrayOperations.reverseArray(sourceArray);
		Assert.assertArrayEquals(expectedArray,reversedArray);
	}
	
	@Test
	public void testReverseArrayEmpty(){
		int sourceArray[]={};
		int expectedArray[]={};
		int[] reversedArray=ArrayOperations.reverseArray(sourceArray);
		Assert.assertArrayEquals(expectedArray,reversedArray);

	}
}
