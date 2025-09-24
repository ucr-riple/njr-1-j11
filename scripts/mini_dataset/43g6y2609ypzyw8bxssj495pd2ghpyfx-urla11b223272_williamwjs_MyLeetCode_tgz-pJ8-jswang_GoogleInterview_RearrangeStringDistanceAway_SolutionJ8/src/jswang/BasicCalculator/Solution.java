package jswang.BasicCalculator;

import java.util.LinkedList;

/**
 * Created by willwjs on 8/15/15.
 */
public class Solution {
    public int calculate(String s) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            if (c == '(') {
                list.addLast(-1);
                continue;
            }
            if (c == '+') {
                list.addLast(-2);
                continue;
            }
            if (c == '-') {
                list.addLast(-3);
                continue;
            }
            int r = -1;
            if (c == ')') {
                r = list.removeLast();
                list.removeLast();
            }
            if (c >= '0' && c <= '9') {
                int j = i;
                while (c >= '0' && c <= '9') {
                    i++;
                    if (i == s.length()) break;
                    c = s.charAt(i);
                }
                r = Integer.parseInt(s.substring(j, i));
                --i;
            }
            if (list.size() == 0) {list.addLast(r); continue;}
            int x = list.getLast();
            while (list.size() > 0 && (x == -2 || x == -3)) {
                list.removeLast();
                int l = list.removeLast();
                if (x == -2) r = l + r;
                else r = l - r;
                if (list.size() > 0)
                    x = list.getLast();
            }
            list.addLast(r);
        }
        return list.getFirst();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().calculate("1+1"));
    }
}