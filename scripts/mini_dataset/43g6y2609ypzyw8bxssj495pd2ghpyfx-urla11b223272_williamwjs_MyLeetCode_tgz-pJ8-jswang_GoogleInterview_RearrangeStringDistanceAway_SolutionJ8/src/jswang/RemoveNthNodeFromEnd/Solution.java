package jswang.RemoveNthNodeFromEnd;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode end = head;
        head = dummy;
        for (int i = 1; i < n; ++i) {
            end = end.next;
        }
        while (end.next != null) {
            head = head.next;
            end = end.next;
        }
        head.next = head.next.next;
        return dummy.next;
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