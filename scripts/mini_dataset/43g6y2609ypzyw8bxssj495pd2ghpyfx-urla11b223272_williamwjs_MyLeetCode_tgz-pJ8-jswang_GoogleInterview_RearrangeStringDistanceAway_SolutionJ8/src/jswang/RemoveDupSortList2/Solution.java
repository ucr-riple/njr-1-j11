package jswang.RemoveDupSortList2;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        head = dummy;

        while (head.next != null && head.next.next != null) {
            if (head.next.val == head.next.next.val) {
                ListNode tmp = head.next.next;
                while (tmp.next != null && tmp.val == tmp.next.val)
                    tmp = tmp.next;
                head.next = tmp.next;
            }
            else
                head = head.next;
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