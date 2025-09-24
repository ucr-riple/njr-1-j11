package jswang.PartitionList;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public ListNode partition(ListNode head, int x) {
        if (head == null) return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode nail = head;
        int num = 1;
        while (nail.next != null) {
            nail = nail.next;
            ++num;
        }
        head = dummy;
        for (int i = 0; i < num; ++i) {
            if (head.next.val < x)
                head = head.next;
            else {
                nail.next = head.next;
                nail = nail.next;
                head.next = head.next.next;
                nail.next = null;
            }
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