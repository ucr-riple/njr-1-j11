package other;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sobercheg on 12/8/13.
 */
public class LongestCommonSubstring {

    public Set<String> getLCS(String s1, String s2) {
        int[][] lcs = new int[s1.length() + 1][s2.length() + 1];
        Set<String> longestSet = new HashSet<String>();

        int z = 0;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    lcs[i + 1][j + 1] = lcs[i][j] + 1;
                    if (lcs[i + 1][j + 1] > z) {
                        z = lcs[i + 1][j + 1];
                        longestSet = new HashSet<String>();
                    }
                    if (lcs[i + 1][j + 1] == z) {
                        longestSet.add(s1.substring(i - z + 1, i + 1));
                    }
                }

            }
        }

        return longestSet;
    }

    public static void main(String[] args) {
        System.out.println(new LongestCommonSubstring().getLCS("banana", "pineapple"));
        System.out.println(new LongestCommonSubstring().getLCS("ABABC", "ABCBA"));
    }
}
