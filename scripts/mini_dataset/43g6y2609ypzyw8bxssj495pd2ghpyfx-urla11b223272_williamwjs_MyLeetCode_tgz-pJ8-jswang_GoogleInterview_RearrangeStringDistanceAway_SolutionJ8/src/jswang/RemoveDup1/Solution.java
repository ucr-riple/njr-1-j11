package jswang.RemoveDup1;

/**
 * Created by JiashuoWang on 8/26/14.
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        ListNode cur = head;

        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            }
            else
                cur = cur.next;
        }
        return head;

        /*ListNode tmp = head;
        for (; tmp != null; tmp = tmp.next) {
            while (tmp.next != null && tmp.next.val == tmp.val) {
                tmp.next = tmp.next.next;
            }
        }
        return head;*/
    }
}