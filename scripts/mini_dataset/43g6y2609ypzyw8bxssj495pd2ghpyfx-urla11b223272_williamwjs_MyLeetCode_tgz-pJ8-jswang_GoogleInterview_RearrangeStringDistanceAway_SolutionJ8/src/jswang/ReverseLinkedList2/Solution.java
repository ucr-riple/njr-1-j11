package jswang.ReverseLinkedList2;

/**
 * Created by willwjs on 11/18/14.
 * 算法很巧妙
 * 其中的start的位置和prev的位置始终不变，并且prev指向的都是初始时第m个元素
 * cur每次都是在prev后面一个位置
 * 练习时可以先假定一部分已经呈现倒序，再去决定指针指向顺序
 * 注意最后返回的
 */
public class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode start = dummy;
        for (int i = 1; i < m; ++i)
            start = start.next;
        ListNode prev = start.next;
        ListNode cur = prev.next;
        for (int i = m; i < n; ++i) {
            prev.next = cur.next;
            cur.next = start.next;
            start.next = cur;
            cur = prev.next;
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