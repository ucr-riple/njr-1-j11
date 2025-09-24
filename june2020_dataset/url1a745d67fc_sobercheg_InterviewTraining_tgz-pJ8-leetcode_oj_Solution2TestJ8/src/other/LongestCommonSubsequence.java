package other;

/**
 * Created by sobercheg on 12/8/13.
 * Backtracking solution: http://acmdynamicprogramming.blogspot.com/
 */
public class LongestCommonSubsequence {

    public String getLCSubsequence(String s1, String s2) {
        int[][] lcs = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    int newSub = 1 + lcs[i][j];
                    lcs[i + 1][j + 1] = newSub;

                } else {
                    lcs[i + 1][j + 1] = Math.max(lcs[i][j + 1], lcs[i + 1][j]);
                }

            }
        }
        return backtrack(lcs, s1, s2, s1.length(), s2.length());
    }

    private String backtrack(int[][] lcs, String s1, String s2, int i, int j) {
        if (i == 0 || j == 0)
            return "";
        else if (s1.charAt(i - 1) == s2.charAt(j - 1))
            return backtrack(lcs, s1, s2, i - 1, j - 1) + s1.charAt(i - 1);
        else if (lcs[i][j - 1] > lcs[i - 1][j])
            return backtrack(lcs, s1, s2, i, j - 1);
        else
            return backtrack(lcs, s1, s2, i - 1, j);
    }

    public static void main(String[] args) {
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        System.out.println(longestCommonSubsequence.getLCSubsequence("abaca", "narbrarcr"));
    }
}
