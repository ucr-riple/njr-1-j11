package jswang.ReverseNodesKGroup;

/**
 * Created by willwjs on 11/18/14.需要多写！！！
 * 比较繁琐，需要考虑k ＝ 1的情况
 * 需要考虑空指针null的情况，各种考虑
 */
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        head = dummy;
        ListNode prev = head.next;
        ListNode cur = prev.next;
        while (cur != null) {
            int count = 2;
            for (; cur.next != null && count < k; ++count)
                cur = cur.next;
            if (count < k) break;
            cur = prev.next;
            for (int i = 1; cur != null && i < k; ++i) {
                prev.next = cur.next;
                cur.next = head.next;
                head.next = cur;
                cur = prev.next;
            }
            head = prev;
            if (head.next != null) prev = head.next;
            else break;
            if (prev.next != null) cur = prev.next;
            else break;
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