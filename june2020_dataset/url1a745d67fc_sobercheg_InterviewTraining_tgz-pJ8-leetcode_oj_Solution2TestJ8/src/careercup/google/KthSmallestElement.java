package careercup.google;

/**
 * Created by Sobercheg on 10/26/13.
 */
public class KthSmallestElement {

    public static int kthOrderStatistics(int[] A, int from, int to, int k) {
        if (from >= to) return A[from];
        int partitionIndex = from;
        int pivotIndex = partition(A, from, to, partitionIndex);
        if (pivotIndex == k) return A[k];
        if (k < pivotIndex) return kthOrderStatistics(A, from, pivotIndex - 1, k);
        else return kthOrderStatistics(A, pivotIndex + 1, to, k - pivotIndex);

    }

    private static int partition(int[] A, int from, int to, int index) {
        int pivot = A[index];
        swap(A, to, index);
        int storeIndex = from;
        for (int i = from; i < to; i++) {
            if (A[i] < pivot) {
                swap(A, i, storeIndex);
                storeIndex++;
            }
        }
        swap(A, to, storeIndex);
        return storeIndex;
    }

    private static void swap(int[] A, int from, int to) {
        int tmp = A[from];
        A[from] = A[to];
        A[to] = tmp;
    }

    public static void main(String[] args) {
        System.out.println(kthOrderStatistics(new int[]{6, 3, 4, 9}, 0, 3, 0));
    }

}
