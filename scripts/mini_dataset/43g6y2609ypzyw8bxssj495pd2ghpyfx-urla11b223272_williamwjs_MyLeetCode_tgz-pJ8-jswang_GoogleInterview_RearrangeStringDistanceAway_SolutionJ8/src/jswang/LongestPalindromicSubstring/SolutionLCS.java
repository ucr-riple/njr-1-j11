package jswang.LongestPalindromicSubstring;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by willwjs on 11/22/14.
 */
public class SolutionLCS { //Reverse the string and find the longest common substring
    public String longestPalindrome(String s) {
        String revStr = "";
        for (int i = 0; i < s.length(); ++i) {
            revStr = s.charAt(i) + revStr;
        }

        int[][] state = new int[s.length() + 1][s.length() + 1];
        Mark mark = new Mark();
        Queue<Mark> list = new PriorityQueue<Mark>(s.length());
        for (int i = 0; i < s.length(); ++i) {
            for (int j = 0; j < s.length(); ++j) {
                if (s.charAt(i) == revStr.charAt(j)) {
                    state[i + 1][j + 1] = state[i][j] + 1;
                    if (state[i + 1][j + 1] > mark.max) {
                        mark.max = state[i + 1][j + 1];
                        mark.marki = i;
                        mark.markj = j;
                    }
                }
                else if (state[i][j] > 0) {
                    list.offer(mark);
                    mark = new Mark();
                }
            }
        }
        while (!list.isEmpty()) {
            mark = list.poll();
            if (mark.markj == s.length() - 1 - mark.marki + mark.max - 1)
                break;
        }

        return s.substring(s.length() - 1 - mark.markj, mark.marki + 1);
    }

    private class Mark implements Comparable<Mark> {
        int max, marki, markj;
        Mark () {
            max = 0;
            marki = 0;
            markj = 0;
        }

        @Override
        public int compareTo(Mark o) {
            if (max > o.max) return 1;
            else if (max < o.max) return -1;
            else return 0;
        }
    }

    public static void main (String[] args) {
        SolutionLCS sol = new SolutionLCS();
        String s = "ydccdx";
        System.out.println(sol.longestPalindrome(s));
    }
}
