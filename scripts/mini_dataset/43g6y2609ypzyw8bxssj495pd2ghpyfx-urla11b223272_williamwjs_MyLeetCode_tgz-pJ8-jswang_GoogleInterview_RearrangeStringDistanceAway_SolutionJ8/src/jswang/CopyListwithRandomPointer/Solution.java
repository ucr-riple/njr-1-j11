package jswang.CopyListwithRandomPointer;

/**
 * Created by willwjs on 11/18/14.
 * 注意算法，先把两个链表对应元素首尾相连，即A->A'->B->B'，再去理会random，再去复原
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode dummy = new RandomListNode(-1);
        dummy.next = head;
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode tmp = new RandomListNode(cur.label);
            tmp.next = cur.next;
            cur.next = tmp;
            cur = cur.next.next;
        }
        cur = head;
        while (cur != null) {
            if (cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }
        cur = dummy;
        while (cur.next != null) {
            RandomListNode tmp = cur.next;
            cur.next = cur.next.next;
            cur = tmp;
        }
        return dummy.next;
    }
}

class RandomListNode {
    int label;
    RandomListNode next, random;
    RandomListNode(int x) {
        this.label = x;
    }
};