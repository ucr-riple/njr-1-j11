package careercup.google;

/**
 * Created by Sobercheg on 11/24/13.
 * Based on this: http://www.geeksforgeeks.org/check-whether-a-given-string-is-an-interleaving-of-two-other-given-strings-set-2/
 */
public class InterleavingStringsDP {

    public boolean interleave(String s1, String s2, String s3) {
        int M = s1.length();
        int N = s2.length();
        if (M + N != s3.length()) return false;

        boolean[][] A = new boolean[M + 1][N + 1];

        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= N; j++) {
                if (i == 0 || j == 0) {
                    // empty substrings interleave an empty substring
                    A[i][j] = true;
                } else if (s1.charAt(i - 1) == s3.charAt(i + j - 1) && s2.charAt(j - 1) != s3.charAt(i + j - 1)) {
                    // Current character of C matches with current character of A,
                    // but doesn't match with current character of B
                    A[i][j] = A[i - 1][j];
                } else if (s1.charAt(i - 1) != s3.charAt(i + j - 1) && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                    // Current character of C matches with current character of B,
                    // but doesn't match with current character of A
                    A[i][j] = A[i][j - 1];
                } else if (s1.charAt(i - 1) == s3.charAt(i + j - 1) && s3.charAt(j - 1) == s3.charAt(i + j - 1)) {
                    // Current character of C matches with that of both A and B
                    A[i][j] = (A[i - 1][j] || A[i][j - 1]);
                }
            }
        }
        return A[M][N];
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
                new InterleavingStringsDP().interleave(s1, s2, s3)));
    }
}
