package jswang.GoogleInterview.ShortestWordDistance;

import java.util.*;

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
public class Step3Solution2 {
    public int shortestWordDistance(Map<String, List<Integer>> wordMap, String word1, String word2) {
        int min = Integer.MAX_VALUE;
        if (word1.equals(word2)) {
            List<Integer> indexList = wordMap.get(word1);
            for (int i = 0; i < indexList.size() - 1; ++i) {
                int i1 = indexList.get(i), i2 = indexList.get(i + 1);
                min = Math.min(Math.abs(i1 - i2), min);
            }
        } else {
            List<Integer> indexList1 = wordMap.get(word1);
            List<Integer> indexList2 = wordMap.get(word2);
            int i1 = 0, i2 = 0;
            while (i1 < indexList1.size() && i2 < indexList2.size()) {
                int tmp1 = indexList1.get(i1), tmp2 = indexList2.get(i2);
                min = Math.min(Math.abs(tmp1 - tmp2), min);
                if (tmp1 < tmp2) {
                    i1++;
                } else {
                    i2++;
                }
            }
        }
        return min;
    }

    public Map<String, List<Integer>> buildWordMap(String[] words) {
        Map<String, List<Integer>> wordMap = new HashMap<>();
        for (int i = 0; i < words.length; ++i) {
            if (wordMap.containsKey(words[i])) {
                wordMap.get(words[i]).add(i);
            } else {
                List<Integer> indexList = new ArrayList<>();
                indexList.add(i);
                wordMap.put(words[i], indexList);
            }
        }
        return wordMap;
    }

    public static void main(String[] args) {
        Step3Solution2 s = new Step3Solution2();
        Map<String, List<Integer>> wordMap = s.buildWordMap(
                new String[] {"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(s.shortestWordDistance(wordMap, "makes", "makes"));
        System.out.println(s.shortestWordDistance(wordMap, "coding", "practice"));
    }
}
