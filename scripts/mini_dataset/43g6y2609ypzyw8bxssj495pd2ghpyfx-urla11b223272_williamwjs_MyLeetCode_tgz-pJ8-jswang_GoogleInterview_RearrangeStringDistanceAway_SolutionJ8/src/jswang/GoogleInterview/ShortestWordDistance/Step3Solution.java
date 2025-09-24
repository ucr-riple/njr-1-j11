package jswang.GoogleInterview.ShortestWordDistance;

/**
 * Created by willwjs on 10/12/15.
 * Given a list of words and two words word1 and word2,
 * return the shortest distance between these two words in the list.

 word1 and word2 may be the same and they represent two individual words in the list.

 For example,

 Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

 Given word1 = "makes", word2 = "coding", return 1.
 Given word1 = "makes", word2 = "makes", return 3.
 */
public class Step3Solution {
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int i1 = -1, i2 = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; ++i) {
            if (word1.equals(word2)) {
                if (word1.equals(words[i])) {
                    if (i1 < i2) i1 = i;
                    else i2 = i;
                }
            } else {
                if (word1.equals(words[i])) {
                    i1 = i;
                } else if (word2.equals(words[i])) {
                    i2 = i;
                } else {
                    continue;
                }
            }
            if (i1 != -1 && i2 != -1) {
                min = Math.min(min, Math.abs(i1 - i2));
            }
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(new Step3Solution().shortestWordDistance(
                new String[] {"practice", "makes", "perfect", "coding", "makes"}, "makes", "makes"));
    }
}
