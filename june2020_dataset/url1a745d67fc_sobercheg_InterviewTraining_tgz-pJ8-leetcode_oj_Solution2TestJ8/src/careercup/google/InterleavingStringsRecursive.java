package careercup.google;

/**
 * Created by Sobercheg on 11/24/13.
 * Partially based on this: http://www.geeksforgeeks.org/check-whether-a-given-string-is-an-interleaving-of-two-other-given-strings-set-2/
 */
public class InterleavingStringsRecursive {

    public boolean interleave(String s1, String s2, String s3, int index1, int index2, int index3) {
        // Base case: all strings are empty
        if (index1 >= s1.length() && index2 >= s2.length() && index3 >= s3.length()) return true;

        // If s3 is empty and any of two strings is not empty
        if (index3 >= s3.length()) return false; // optional

        // Recursion
        boolean interleave = false;
        if (index1 < s1.length() && s1.charAt(index1) == s3.charAt(index3)) {
            interleave = interleave(s1, s2, s3, index1 + 1, index2, index3 + 1);
        }
        if (index2 < s2.length() && s2.charAt(index2) == s3.charAt(index3)) {
            interleave |= interleave(s1, s2, s3, index1, index2 + 1, index3 + 1);
        }
        return interleave;
    }


    public static void main(String[] args) {
        test("aab", "aac", "abaabc");
        test("aab", "aac", "abaabc");
        test("XXY", "XXZ", "XXZXXXY");
        test("XY", "WZ", "WZXY");
        test("XY", "X", "XXY");
        test("YX", "X", "XXY");
        test("XXY", "XXZ", "XXXXZY");
    }

    private static void test(String s1, String s2, String s3) {
        System.out.println(String.format("[%s][%s] [%s]: %b", s1, s2, s3,
                new InterleavingStringsRecursive().interleave(s1, s2, s3, 0, 0, 0)));
    }
}
