package jswang.PalindromePartitioning;

import java.util.*;

/**
 * Created by willwjs on 2/1/15.
 */
public class Solution {
    public void partition(int[] s) {
        Map<Integer, ListNode> map = new HashMap<Integer, ListNode>();
        ListNode dummy = new ListNode(-1), nail = dummy;
        for (int i = 0; i < s.length; ++i) {
            nail.next = new ListNode(s[i]);
            map.put(s[i], nail);
            nail = nail.next;
        }
        nail = map.get(s[4]);
        ListNode tmp = nail.next;
        nail.next = nail.next.next;
        tmp.next = dummy.next;
        dummy.next = tmp;
        System.out.println(map.get(s[4]).next.val);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        sol.partition(new int[] {0, 1, 2, 3, 4, 5, 6,7});
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}