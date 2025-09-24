package jswang.RotateList;

/**
 * Created by willwjs on 11/18/14.
 * 有些特殊情况需要注意，详情见下面的注释
 */
public class Solution {
    public ListNode rotateRight(ListNode head, int n) {
        if (head == null) return head;
        ListNode cur = head;
        int num = 1;
        while (cur.next != null) {
            cur = cur.next;
            ++num;
        }
        n %= num; //注意n可能会大于链表长度
        if (num == n) return head; //注意这种特殊情况
        cur.next = head;
        cur = head;
        for (int i = 1; i < num - n; ++i) {
            cur = cur.next;
        }
        head = cur.next;
        cur.next = null;
        return head;
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