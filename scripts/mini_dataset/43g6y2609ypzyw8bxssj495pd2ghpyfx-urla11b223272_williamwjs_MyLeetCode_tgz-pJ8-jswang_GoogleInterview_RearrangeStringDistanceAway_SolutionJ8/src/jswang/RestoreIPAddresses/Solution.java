package jswang.RestoreIPAddresses;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by willwjs on 9/3/15.
 * 1.s可能越界,但实际只要12位即可
 * 2.注意++n和n++,以及为保留n值,用n+1更好
 * 3.注意"01"不是数,数不能以0开头
 */
public class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> lists = new LinkedList<>();
        if (s.length() > 12) return lists;
        String res = "";
        helper(s, 0, lists, res);
        return lists;
    }

    private void helper(String s, int n, List<String> lists, String res) {
        if (s == null || s.length() == 0) return;
        if (n == 3) {
            if ((s.charAt(0) != '0' && s.length() <= 3 && Integer.parseInt(s) <= 255) || (s.length() == 1 && s.charAt(0) == '0')) {
                res += s;
                lists.add(res);
            }
            return;
        }
        if (s.charAt(0) == '0') {
            helper(s.substring(1, s.length()), n + 1, lists, res + "0.");
        } else {
            for (int i = 1; i <= 3 && i <= s.length(); ++i) {
                if (Integer.parseInt(s.substring(0, i)) <= 255) {
                    helper(s.substring(i, s.length()), n + 1, lists, res + s.substring(0, i) + '.');
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().restoreIpAddresses("0000"));
    }
}