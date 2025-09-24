package quicksort;

import junit.framework.Assert;

import org.junit.Test;

public class BinarySearchTest {

	@Test
	public void testEmptyArray(){
		int[] values={1,3,4,5,6};
		int number=4;
		int expectedLocation=2;
		BinarySearch searcher=new BinarySearch();
		int result=searcher.search(values, number);
		Assert.assertEquals(expectedLocation,result);
		
	}
	
}
