package crackinginterview.chap2linkedlists;

import common.ListNode;

/**
 * Created by Sobercheg on 12/6/13.
 */
public class KthToLastInList {

    static class MutableInt {
        int value = 0;

        MutableInt(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public ListNode getKthToLastInList(ListNode head, int k, MutableInt index) {
        if (head == null) return null;
        ListNode node = getKthToLastInList(head.next, k, index);
        // but this is wrong!
        index.value = index.value + 1;
        if (index.value == k) {
            return head;
        }

        return node;
    }

    public static void main(String[] args) {
        KthToLastInList kthToLastInList = new KthToLastInList();
        ListNode root = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));
        MutableInt index = new MutableInt(0);
        ListNode kthNode = kthToLastInList.getKthToLastInList(root, 3, index);
        System.out.println(kthNode);
        System.out.println(index);
    }

}
