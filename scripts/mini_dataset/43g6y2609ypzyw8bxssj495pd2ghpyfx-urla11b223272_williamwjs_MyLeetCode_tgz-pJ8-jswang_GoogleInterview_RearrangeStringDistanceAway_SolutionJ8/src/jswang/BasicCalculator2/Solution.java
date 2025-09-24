package jswang.BasicCalculator2;

import java.util.LinkedList;

/**
 * Created by willwjs on 8/15/15.
 */
public class Solution {
    public int calculate(String s) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ' ') continue;
            if (s.charAt(i) == '+') {
                list.addLast(-1);
                continue;
            }
            if (s.charAt(i) == '-') {
                list.addLast(-2);
                continue;
            }
            if (s.charAt(i) == '*') {
                list.addLast(-3);
                continue;
            }
            if (s.charAt(i) == '/') {
                list.addLast(-4);
                continue;
            }
            int j = i;
            ++i;
            while (i != s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                ++i;
            }
            int r = Integer.parseInt(s.substring(j, i--));
            if (list.size() == 0) {list.addLast(r); continue;}
            while (i != s.length() - 1 && s.charAt(i + 1) == ' ') {i++;}
            if (i != s.length() - 1 && (s.charAt(i + 1) == '*' || s.charAt(i + 1) == '/')) {
                int x = list.getLast();
                if (x == -3 || x == -4) {
                    list.removeLast();
                    int l = list.removeLast();
                    if (x == -3) {
                        r = l * r;
                    } else {
                        r = l / r;
                    }
                }
            } else {
                while (list.size() != 0) {
                    int x = list.removeLast();
                    int l = list.removeLast();
                    if (x == -1) {
                        r = l + r;
                    } else if (x == -2) {
                        r = l - r;
                    } else if (x == -3) {
                        r = l * r;
                    } else {
                        r = l / r;
                    }
                }
            }
            list.addLast(r);
        }
        return list.getFirst();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().calculate(" 3+5 / 2 "));
    }
}