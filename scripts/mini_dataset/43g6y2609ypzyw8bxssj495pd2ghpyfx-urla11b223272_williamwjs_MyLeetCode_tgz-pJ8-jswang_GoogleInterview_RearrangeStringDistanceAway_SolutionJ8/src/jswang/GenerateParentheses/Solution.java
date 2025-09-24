package jswang.GenerateParentheses;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 1/14/15.
 */
public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> list = new LinkedList<String>();
        String s = "";
        generateParenthesises(list, s, n, n);
        return list;
    }

    private void generateParenthesises(List<String> list, String s, int left, int right) {
        if (left == right && right == 0) {
            list.add(s);
        }
        if (left < 0 || right < 0 || left > right) {
            return;
        }
        else if (left == right) {
            generateParenthesises(list, s + "(", left - 1, right);
        }
        else {
            generateParenthesises(list, s + "(", left - 1, right);
            generateParenthesises(list, s + ")", left, right - 1);
        }
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        List<String> list = sol.generateParenthesis(4);
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + "    ");
        }
    }
}