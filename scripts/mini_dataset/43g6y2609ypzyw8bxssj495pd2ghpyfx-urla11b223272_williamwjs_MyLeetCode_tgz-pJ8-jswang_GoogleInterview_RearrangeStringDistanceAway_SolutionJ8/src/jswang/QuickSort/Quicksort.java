package jswang.QuickSort;

/**
 * Created by willwjs on 9/6/15.
 */
public class Quicksort {
    private static <T extends Comparable<T>>
    int partition(T[] list, int lo, int hi) {
        T pivot = list[lo];
        int left = lo + 1;
        int right = hi;

        while (true) {
            while (left <= right) {
                if (list[left].compareTo(pivot) < 0) {
                    left++;
                } else {
                    break;
                }
            }

            while (right > left) {
                if (list[right].compareTo(pivot) < 0) {
                    break;
                } else {
                    right--;
                }
            }

            if (left >= right) break;

            T tmp = list[left];
            list[left] = list[right];
            list[right] = tmp;
            left++; right--;
        }

        list[lo] = list[left - 1];
        list[left - 1] = pivot;

        return left - 1;
    }

    private static <T extends Comparable<T>>
    void quicksort(T[] list, int lo, int hi) {
        if (hi - lo <= 0) return;
        int splitPoint = partition(list, lo, hi);
        quicksort(list, lo, splitPoint - 1);
        quicksort(list, splitPoint + 1, hi);
    }

    public static <T extends Comparable<T>>
    void sort(T[] list) {
        if (list == null || list.length <= 1) return;
        quicksort(list, 0, list.length - 1);
    }
}
