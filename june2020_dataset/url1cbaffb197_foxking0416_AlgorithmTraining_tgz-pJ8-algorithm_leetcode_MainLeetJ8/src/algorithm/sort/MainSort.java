package algorithm.sort;


public class MainSort {

	public static void main(String[] args) {

		
		testSorting();

	}
	
	
	private static void testSorting(){
	    int[] array = Sort.generateRandomArray();
		for (int i : array) {
			System.out.print(i);
			System.out.print(", ");
		}
		System.out.print("\n");
		
		
		int arrayToSort1[] = array.clone();
		int arrayToSort2[] = array.clone();
		int arrayToSort3[] = array.clone();
		int arrayToSort4[] = array.clone();
		
		Sort.selectionSort(arrayToSort1);
		System.out.print("Selection Sort Result: ");
		for (int i : arrayToSort1) {
			System.out.print(i);
			System.out.print(", ");
		}
		System.out.print("\n");
		
		Sort.insertionSort(arrayToSort2);
		System.out.print("Insertion Sort Result: ");
		for (int i : arrayToSort2) {
			System.out.print(i);
			System.out.print(", ");
		}
		System.out.print("\n");
		
		Sort.mergeSort(arrayToSort3, 0, arrayToSort3.length-1);
		System.out.print("Merge Sort Result: ");
		for (int i : arrayToSort3) {
			System.out.print(i);
			System.out.print(", ");
		}
		System.out.print("\n");
		
		
		Sort.quickSort(arrayToSort4, 0, arrayToSort4.length-1);
		System.out.print("Quick Sort Result: ");
		for (int i : arrayToSort4) {
			System.out.print(i);
			System.out.print(", ");
		}
		System.out.print("\n");
		
	}

	

	
}
