package jswang.AddTwoNumbers;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tmp1 = l1, tmp2 = l2;
        ListNode head = new ListNode(tmp1.val + tmp2.val);
        ListNode nail = head;

        while (tmp1.next != null || tmp2.next != null) {
            if (tmp1.next == null) {
                ListNode tmp;
                if (nail.val > 9) {
                    tmp = new ListNode(1 + tmp2.next.val);
                    nail.val -= 10;
                }
                else
                    tmp = new ListNode(tmp2.next.val);
                tmp2 = tmp2.next;
                nail.next = tmp;
                nail = nail.next;
            }
            else if (tmp2.next == null) {
                ListNode tmp;
                if (nail.val > 9) {
                    tmp = new ListNode(1 + tmp1.next.val);
                    nail.val -= 10;
                }
                else
                    tmp = new ListNode(tmp1.next.val);
                tmp1 = tmp1.next;
                nail.next = tmp;
                nail = nail.next;
            }
            else {
                ListNode tmp;
                if (nail.val > 9) {
                    tmp = new ListNode(1 + tmp1.next.val + tmp2.next.val);
                    nail.val -= 10;
                }
                else
                    tmp = new ListNode(tmp1.next.val + tmp2.next.val);
                tmp1 = tmp1.next;
                tmp2 = tmp2.next;
                nail.next = tmp;
                nail = nail.next;
            }
        }

        if (nail.val > 9) {
            ListNode tmp = new ListNode(1);
            nail.val -= 10;
            nail.next = tmp;
        }

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
