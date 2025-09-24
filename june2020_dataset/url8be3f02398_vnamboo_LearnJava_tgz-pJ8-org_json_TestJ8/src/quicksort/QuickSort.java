package quicksort;

public class QuickSort {
	private int[] numbers;
	private int numberOfValues;

	public void sort(int[] values) {
		if (null == values || values.length == 0) {
			return;
		}
		this.numbers = values;
		this.numberOfValues = numbers.length - 1;
		quickSort(0,this.numberOfValues);
	}

	private void quickSort(int low, int high) {
		int i=low,j=high;
		int pivot=numbers[low+(high-low)/2];
		while (i<j){
			while(numbers[i]<pivot){
				i++;
			}
			while(numbers[j]>pivot){
				j--;
			}
			if(i<=j){
				exchange(i,j);
				i++;
				j--;
			}
		}
		if(low<j){
			quickSort(low, j);
		}
		if(i<high){
			quickSort(i, high);
		}
	}

	private void exchange(int i, int j) {
		int tmp=numbers[i];
		numbers[i]=numbers[j];
		numbers[j]=tmp;
	}
	
	public boolean validate(){
		for(int number=0;number<this.numbers.length-1;number++){
			if(this.numbers[number]>this.numbers[number+1]){
				return false;
			}
		}
		return true;
	}
	
}
