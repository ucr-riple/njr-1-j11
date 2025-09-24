package careercup.linkedin;

/**
 * Created by Sobercheg on 12/12/13.
 * <p/>
 * http://www.careercup.com/question?id=15206756
 * <p/>
 * A non-empty zero-indexed array A consisting of N integers is given. The leader of this array is the value that occurs in more than half of the elements of A.
 * Write a function
 * int arrLeader(int A[], int N);
 * that, given a non-empty zero-indexed array A consisting of N integers, returns the leader of array A. The function should return −1 if array A does not contain a leader.
 * Assume that:
 * N is an integer within the range [1..1,000,000];
 * each element of array A is an integer within the range [0..2147483647].
 * For example, given array A consisting of ten elements such that:
 * A[0] = 4 A[1] = 2 A[2] = 2
 * A[3] = 3 A[4] = 2 A[5] = 4
 * A[6] = 2 A[7] = 2 A[8] = 6
 * A[9] = 4
 * the function should return −1, because the value that occurs most frequently in the array, 2, occurs 5 times, and 5 is not more than half of 10.
 * Given array A consisting of five elements such that:
 * A[0] = 100 A[1] = 1 A[2] = 1
 * A[3] = 50 A[4] = 1
 * the function should return 1.
 * Complexity:
 * expected worst-case time complexity is O(N);
 * expected worst-case space complexity is O(1), beyond input storage (not counting the storage required for input arguments).
 * Elements of input arrays can be modified.
 * <p/>
 * Solution idea: http://stackoverflow.com/questions/780937/linear-time-voting-algorithm-i-dont-get-it
 */
public class ArrayLeader {

    int arrLeader(int A[]) {
        int counter = 1;
        int value = A[0];

        for (int i = 1; i < A.length; i++) {
            if (value == A[i]) {
                counter++;
            } else {
                counter--;
            }
            if (counter < 0) {
                value = A[i];
                counter = 0;
            }
        }

        counter = 0;
        for (int a : A) {
            if (a == value) counter++;
        }

        return counter > A.length / 2 ? value : -1;
    }

    public static void main(String[] args) {
        System.out.println(new ArrayLeader().arrLeader(new int[]{2, 2, 1, 10, 2, 6, 6, 6, 2, 2, 2, 2}));
        System.out.println(new ArrayLeader().arrLeader(new int[]{2, 2, 1, 10, 2, 6, 4, 6, 5, 2, 2, 2}));
    }
}
