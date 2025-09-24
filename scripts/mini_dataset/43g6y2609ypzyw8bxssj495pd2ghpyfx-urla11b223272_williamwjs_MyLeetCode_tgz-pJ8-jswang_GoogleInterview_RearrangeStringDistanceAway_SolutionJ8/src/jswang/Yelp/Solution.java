package jswang.Yelp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by willwjs on 12/6/14.
 */
public class Solution {
    boolean detectPalin (String s) {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); ++i) {
            if (!set.contains(s.charAt(i))) set.add(s.charAt(i));
            else set.remove(s.charAt(i));
        }
        if (set.size() == 0 || set.size() == 1) return true;
        else return false;
    }

    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        String str = buf.readLine();
        Solution sol = new Solution();
        System.out.println(sol.detectPalin(str));
    }
}
