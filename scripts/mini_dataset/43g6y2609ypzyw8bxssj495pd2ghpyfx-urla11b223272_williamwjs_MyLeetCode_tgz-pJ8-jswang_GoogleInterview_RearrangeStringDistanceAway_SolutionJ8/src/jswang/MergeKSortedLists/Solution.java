package jswang.MergeKSortedLists;

import java.util.PriorityQueue;

/**
 * Created by willwjs on 9/24/15.
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((l1, l2) -> l1.val > l2.val ? 1 : -1
            /*new Comparator<ListNode>() {
            @Override
            public int compare(ListNode l1, ListNode l2) {
                if (l1.val < l2.val) {
                    return -1;
                } else if (l1.val > l2.val) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }*/);
        for (ListNode node : lists) {
            if (node == null) continue;
            minHeap.add(node);
        }

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.remove();
            if (node.next != null) {
                minHeap.add(node.next);
            }
            cur.next = node;
            cur = cur.next;
            cur.next = null;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1);
        ListNode tmp = new ListNode(4);
        lists[0].next = tmp;
        lists[1] = new ListNode(2);
        tmp = new ListNode(5);
        lists[1].next = tmp;
        lists[2] = new ListNode(3);
        System.out.println(new Solution().mergeKLists(lists));
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}
