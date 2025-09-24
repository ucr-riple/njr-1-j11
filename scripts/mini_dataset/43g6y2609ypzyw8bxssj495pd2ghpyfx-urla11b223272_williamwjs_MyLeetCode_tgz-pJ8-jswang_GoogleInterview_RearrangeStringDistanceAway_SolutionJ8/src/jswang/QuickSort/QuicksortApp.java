package jswang.QuickSort;

/**
 * Created by willwjs on 9/6/15.
 */
public class QuicksortApp {
    public static void main(String [] args) {
        Integer a[] = new Integer[] {3,7,6,4,9,1,5,2,12,10};
        Quicksort.sort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
    }
}
