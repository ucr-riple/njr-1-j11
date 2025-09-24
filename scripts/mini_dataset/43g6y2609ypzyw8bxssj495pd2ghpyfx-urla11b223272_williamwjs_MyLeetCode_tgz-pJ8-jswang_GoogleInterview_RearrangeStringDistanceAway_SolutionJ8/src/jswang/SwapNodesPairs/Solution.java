package jswang.SwapNodesPairs;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        head = dummy;
        while (head.next != null && head.next.next != null) {
            ListNode tmp = head.next.next.next;
            head.next.next.next = head.next;
            head.next = head.next.next;
            head.next.next.next = tmp;
            head = head.next.next;
        }
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