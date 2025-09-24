package quicksort;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class QuickSortTest  {
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testNull(){
		QuickSort sorter=new QuickSort();
		sorter.sort(null);
	}
	
	@Test
	public void testWithEmptyArray(){
		QuickSort sorter=new QuickSort();
		sorter.sort(new int[0]);
	}

	@Test
	public void testSampleArray(){
		QuickSort sorter=new QuickSort();
		int[] values = {10,20,5,1,8};
		sorter.sort(values);
		printValues(values);
	}
	
	@Test
	public void testSingleElementArray(){
		QuickSort sorter=new QuickSort();
		int[] values = {10};
		sorter.sort(values);
		printValues(values);
	}
	
	@Test
	public void testEvenSizedArray(){
		QuickSort sorter=new QuickSort();
		int[] values = {2,1};
		sorter.sort(values);
		printValues(values);
	}
	
	@Test
	public void testOddSizedArray(){
		QuickSort sorter=new QuickSort();
		int[] values = {2,1,3};
		sorter.sort(values);
		printValues(values);
	}
	
	@Test
	public void testSpecialArray(){
		QuickSort sorter=new QuickSort();
		int[] values = {10,10,10,5,9,0,24,3,3,3,3,5,6};
		sorter.sort(values);
		if(!sorter.validate()){
			Assert.fail("Cannot be true");
			
		}
		printValues(values);
	}
	
	
	
	private void printValues(int[] values){
		System.out.println("----------------------------");
		for(int item:values){
			System.out.println(item);
		}
		System.out.println("----------------------------");
	}
}
