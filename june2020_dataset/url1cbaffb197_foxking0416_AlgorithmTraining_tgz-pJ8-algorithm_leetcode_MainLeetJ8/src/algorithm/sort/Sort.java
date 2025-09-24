package algorithm.sort;

public class Sort {

	public static int[] generateRandomArray(){
		int randomArray[] = new int[10];
		
		for (int i = 0; i < randomArray.length ; i++){
			randomArray[i] =  (int)(Math.random() * 100);
		}
		
		return randomArray;
	}
	
	public static void selectionSort(final int[] array){
		
		for(int i = 0; i < array.length - 1; i++){
			int smallest = array[i];
			int smallestIndex = i;
			for(int j = i+1; j < array.length; j++){
				if(array[j] < smallest){
					smallest = array[j];
					smallestIndex = j;
				}
			}
			
			int temp = array[i];
			array[i] = array[smallestIndex];
			array[smallestIndex] = temp;
		}
	}
	
	public static void insertionSort(final int[] array){
		for(int i = 0; i < array.length; i++){
			int key = array[i];
			int j = i-1;
			while(j >= 0 && array[j] > key){
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
	}
	
	//Merge Sort
	public static void mergeSort(final int[] array, int p, int r){
		if(p>=r)
			return;
		
		
		int q = (p+r)/2;
		
		mergeSort(array, p, q);
		
		mergeSort(array, q+1, r);
		
		merge(array, p, q, r);
		
		
		
	}
	
	//Called by Merge Sort
	private static void merge(final int[] array, int p, int q, int r){
		int arrayFront[] = new int[q-p+2];//include q
		int arrayBack[] = new int[r-q + 1]; //not include q
		
		for(int i = 0 ; i < q - p + 1; i++){ // copy from p to q, include p and q
			arrayFront[i] = array[i + p];
		}
		for(int i = 0; i < r - q; i++){ // copy form q+1 to r (include q+1 and r)
			arrayBack[i] = array[i + q + 1];
		}
		
		arrayFront[arrayFront.length - 1] = Integer.MAX_VALUE;
		arrayBack[arrayBack.length - 1] = Integer.MAX_VALUE;
		
//		System.out.print("\n");
//		System.out.print("p = " + p + " q = " + q + " r = " + r);
//		System.out.print("\n");
//		for (int i : array) {
//			System.out.print(i);
//			System.out.print(", ");
//		}
//		System.out.print("\n");
		
		for(int k = p, i = 0, j = 0 ; k <= r ;k++){
			if(arrayFront[i] <= arrayBack[j]){
				array[k] = arrayFront[i];
				i++;
			}
			else{
				array[k] = arrayBack[j];
				j++;
			}
		}
//		for (int i : array) {
//			System.out.print(i);
//			System.out.print(", ");
//		}
//		System.out.print("\n");
	}
	
	
	public static void quickSort(final int[] array, int p, int r){
		if(p >= r)
			return;
		
		int q = partition(array, p, r);
		
		quickSort(array, p, q-1);
		
		quickSort(array, q+1, r);
		
		
	}
	
	//Called by Quick Sort
	private static int partition(final int[] array, int p, int r){
		int q = p;
		
		for(int u = p; u < r; u++){
			if(array[u] <= array[r]){
				int temp = array[q];//swap array[q] with array[u]
				array[q] = array[u];
				array[u] = temp;
				q++;
			}
		}
		
		
		int temp = array[q];
		array[q] = array[r];
		array[r] = temp;
		
		return q;
	}
}
