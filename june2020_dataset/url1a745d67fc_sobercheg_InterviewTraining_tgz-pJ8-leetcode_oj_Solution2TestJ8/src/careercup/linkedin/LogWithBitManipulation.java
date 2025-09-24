package careercup.linkedin;

/**
 * Created by Sobercheg on 12/14/13.
 * <p/>
 * http://www.careercup.com/question?id=15062875
 * Implement the integral part logn base 2 with bit manipulations
 */
public class LogWithBitManipulation {

    /**
     * Calculates integral part log2 n using bit manipulation.
     */
    public static int log2(int n) {
        int counter = 0;
        while (n > 0) {
            n >>= 1;
            counter++;
        }

        return counter - 1;
    }

    public static void main(String[] args) {
        System.out.println(log2(16));
        System.out.println(log2(5));
        System.out.println(log2(256));
        System.out.println(log2(255));
        System.out.println(log2(257));
    }
}
