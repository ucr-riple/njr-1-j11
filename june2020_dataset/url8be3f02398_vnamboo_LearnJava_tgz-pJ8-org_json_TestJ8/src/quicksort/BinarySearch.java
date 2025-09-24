package quicksort;

public class BinarySearch {
	public int search(int[] values,int number) {
		int left,right,mid;
		left=0;
		right=values.length;
		while(right>left){
			mid=(left+right)/2;
			if(number>values[mid]){
				left=mid+1;				
			}else if (number<values[mid]){
				right=mid-1;
			}else {
				return mid;
			}
		}
		
		return -1;
		
	}
}
