package jswang.LinkedListCycle;

/**
 * Created by willwjs on 11/19/14.
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head.next;
        while (slow.next != null && fast.next != null && fast.next.next != null && slow != fast) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (slow == fast) return true;
        else return false;

        /*//空间复杂度高
        Set<ListNode> set = new HashSet<ListNode>();
        ListNode cur = head;
        while (cur != null && !set.contains(cur)) {
            set.add(cur);
            cur = cur.next;
        }
        if (cur == null) return false;
        else return true;
        */
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