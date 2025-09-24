package jswang.ReorderList;

/**
 * Created by willwjs on 11/20/14.
 * 找到中点拆开倒序后半段再插入
 */
public class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        ListNode slow = head.next, fast = head.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = slow.next;
        slow.next = null;
        slow = dummy.next;
        fast = slow.next;
        while (fast != null) {
            slow.next = fast.next;
            fast.next = dummy.next;
            dummy.next = fast;
            fast = slow.next;
        }
        slow = head;
        dummy = dummy.next;
        while (slow != null && dummy != null) {
            fast = slow.next;
            slow.next = dummy;
            dummy = dummy.next;
            slow.next.next = fast;
            slow = fast;
        }
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