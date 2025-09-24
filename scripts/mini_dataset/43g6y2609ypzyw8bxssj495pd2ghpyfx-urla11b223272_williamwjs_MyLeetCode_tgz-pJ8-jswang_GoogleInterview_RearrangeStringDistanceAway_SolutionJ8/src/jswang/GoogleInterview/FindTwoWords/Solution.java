package jswang.GoogleInterview.FindTwoWords;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 9/17/15.
 * 给一个字典，找出两个单词满足条件（1）没有相同的字母（2）长度的乘积最大
 * http://www.1point3acres.com/bbs/thread-140228-1-1.html
 * 8.26 Onsite
 */
public class Solution {
    List<String> findWords(List<String> dict) {
        List<Integer> bitMap = new LinkedList<>();
        Collections.sort(dict, (s1, s2) -> s1.length() > s2.length() ? -1 : 1);
        for (String s : dict) {
            int tmp = 0;
            s = s.toLowerCase();
            for (int i = 0; i < s.length(); ++i) {
                tmp |= (1 << (s.charAt(i) - 'a'));
            }
            bitMap.add(tmp);
        }
        int max = 0;
        List<String> res = new LinkedList<>();
        for (int i = 0; i < bitMap.size(); ++i) {
            String op1 = dict.get(i);
            int tmp = bitMap.get(i);
            for (int j = i + 1; j < bitMap.size(); ++j) {
                if ((tmp & bitMap.get(j)) == 0) {
                    String op2 = dict.get(j);
                    if (op1.length() * op2.length() > max) {
                        max = op1.length() * op2.length();
                        res.clear();
                        res.add(op1);
                        res.add(op2);
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("abcde");
        list.add("fghe");
        list.add("fgh");
        list.add("fg");
        System.out.println(new Solution().findWords(list));
    }
}
