package datastructure.heap;
import java.util.ArrayList;


public class Heap {
	// (index - 1) / 2 = parent index
	// 2 * index + 1 = left child index
	// 2 * index + 2 = right child index
	
	
	
	ArrayList<Integer> heapArrayMin;
	ArrayList<Integer> heapArrayMax;	
	
	public Heap() {
		heapArrayMin = new ArrayList<Integer>();
		heapArrayMax = new ArrayList<Integer>();
	}
	
	
	//Create Min Heap: root node is the smallest
	public void insertToMinHeap(int value){
		heapArrayMin.add(value);
		
		int i = heapArrayMin.size() - 1;
		
		while(i > 0 && heapArrayMin.get(i) < heapArrayMin.get((i-1)/2)){
			int temp = heapArrayMin.get(i);
			heapArrayMin.set(i, heapArrayMin.get((i-1)/2));
			heapArrayMin.set((i-1)/2, temp);
			i = (i-1)/2;
		}
	}
	
	//Create Max Heap: root node is the largest
	public void insertToMaxHeap(int value){
		heapArrayMax.add(value);
		
		int i = heapArrayMax.size() - 1;
		
		while(i > 0 && heapArrayMax.get(i) > heapArrayMax.get((i-1)/2)){
			int temp = heapArrayMax.get(i);
			heapArrayMax.set(i, heapArrayMax.get((i-1)/2));
			heapArrayMax.set((i-1)/2, temp);
			i = (i-1)/2;
		}
	}
	
	//Only remove the top element
	public void removeFromMinHeap(){
		int index = 0;
		int count = heapArrayMin.size();
		
		int temp = heapArrayMin.get(index);
		heapArrayMin.set(index, heapArrayMin.get(count-1));
		heapArrayMin.set(count-1, Integer.MAX_VALUE);
		
		while(2*index+1 < count && ( heapArrayMin.get(index) > heapArrayMin.get(2*index+1) || heapArrayMin.get(index) > heapArrayMin.get(2*index+2))){
			if(heapArrayMin.get(2*index+1) < heapArrayMin.get(2*index+2)){
				//swap index with left child
				temp = heapArrayMin.get(index);
				heapArrayMin.set(index, heapArrayMin.get(2*index+1));
				heapArrayMin.set(2*index+1, temp);
				index = 2*index+1;
			}
			else{
				//swap index with right child
				temp = heapArrayMin.get(index);
				heapArrayMin.set(index, heapArrayMin.get(2*index+2));
				heapArrayMin.set(2*index+2, temp);
				index = 2*index+2;
			}
		}
		
		heapArrayMin.remove(heapArrayMin.size()-1);
	}
	
	
	public void removeFromMaxHeap(){
		int index = 0;
		int count = heapArrayMax.size();
		
		int temp = heapArrayMax.get(index);
		heapArrayMax.set(index, heapArrayMax.get(count-1));
		heapArrayMax.set(count-1, Integer.MIN_VALUE);
		
		while(2*index+1 < count && ( heapArrayMax.get(index) < heapArrayMax.get(2*index+1) || heapArrayMax.get(index) < heapArrayMax.get(2*index+2))){
			if(heapArrayMax.get(2*index+1) > heapArrayMax.get(2*index+2)){
				//swap index with left child
				temp = heapArrayMax.get(index);
				heapArrayMax.set(index, heapArrayMax.get(2*index+1));
				heapArrayMax.set(2*index+1, temp);
				index = 2*index+1;
			}
			else{
				//swap index with right child
				temp = heapArrayMax.get(index);
				heapArrayMax.set(index, heapArrayMax.get(2*index+2));
				heapArrayMax.set(2*index+2, temp);
				index = 2*index+2;
			}
		}
		
		heapArrayMax.remove(heapArrayMax.size()-1);
	}
	
	
	public boolean containsInMinHeap(int value){		
		int level = 0;
		int startIndex = 0;
		int totalElementCount = heapArrayMin.size();
		
		while (startIndex < totalElementCount) {
			int count = 0;
			int node = (int) Math.pow(2, level);
			startIndex = node - 1;
			int endIndex = node + startIndex;
			
			while (startIndex < totalElementCount && startIndex < endIndex) {
				if(heapArrayMin.get(startIndex) == value){
					return true;
				}
				else if(heapArrayMin.get(startIndex) > value){
					++count;
				}
				++startIndex;
			}
			
			if(count == node){
				return false;
			}
			++level; 
		}
		return false;
	}
	
	public boolean containsInMaxHeap(int value){		
		int level = 0;
		int startIndex = 0;
		int totalElementCount = heapArrayMax.size();
		
		while (startIndex < totalElementCount) {
			int count = 0;
			int node = (int) Math.pow(2, level);
			startIndex = node - 1;
			int endIndex = node + startIndex;
			
			while (startIndex < totalElementCount && startIndex < endIndex) {
				if(heapArrayMax.get(startIndex) == value){
					return true;
				}
				else if(heapArrayMax.get(startIndex) < value){
					++count;
				}
				++startIndex;
			}
			
			if(count == node){
				return false;
			}
			++level; 
		}
		return false;
	}
	
	public void printArrayMin(){
		System.out.print("Min heap is ");
		for (Integer value : heapArrayMin) {
			System.out.print(value + ",");
		}
		System.out.print("\n");
	}
	
	public void printArrayMax(){
		System.out.print("Max heap is ");
		for (Integer value : heapArrayMax) {
			System.out.print(value + ",");
		}
		System.out.print("\n");
	}
	
}
