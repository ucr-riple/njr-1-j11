package other;

/**
 * Created by Sobercheg on 12/11/13.
 */
public class BinarySearch {

    public int find(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] < value) {
                low = mid + 1;
            } else if (array[mid] > value) {
                high = mid - 1;
            } else return mid;
        }
        return -1;
    }
}
