package jswang.GoogleInterview.ShortestWordDistance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by willwjs on 10/12/15.
 * This is a follow up of Shortest Word Distance.
 * The only difference is now you are given the list of words
 * and your method will be called repeatedly many times with different parameters.
 * How would you optimize it?

 Design a class which receives a list of words in the constructor,
 and implements a method that takes two words word1 and word2
 and return the shortest distance between these two words in the list.

 For example,
 Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

 Given word1 = “coding”, word2 = “practice”, return 3.
 Given word1 = "makes", word2 = "coding", return 1.
 */
public class Step2Solution {
    public int shortestWordDistance(Map<String, List<Integer>> wordMap, String word1, String word2) {
        List<Integer> indexList1 = wordMap.get(word1);
        List<Integer> indexList2 = wordMap.get(word2);
        int i1 = 0, i2 = 0, min = Integer.MAX_VALUE;
        while (i1 < indexList1.size() && i2 < indexList2.size()) {
            int tmp1 = indexList1.get(i1), tmp2 = indexList2.get(i2);
            min = Math.min(Math.abs(tmp1 - tmp2), min);
            if (tmp1 < tmp2) {
                i1++;
            } else {
                i2++;
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
                List<Integer> indexList = new LinkedList<>();
                indexList.add(i);
                wordMap.put(words[i], indexList);
            }
        }
        return wordMap;
    }

    public static void main(String[] args) {
        Step2Solution s = new Step2Solution();
        Map<String, List<Integer>> wordMap = s.buildWordMap(
                new String[] {"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(s.shortestWordDistance(wordMap, "coding", "makes"));
        System.out.println(s.shortestWordDistance(wordMap, "coding", "practice"));
    }
}
