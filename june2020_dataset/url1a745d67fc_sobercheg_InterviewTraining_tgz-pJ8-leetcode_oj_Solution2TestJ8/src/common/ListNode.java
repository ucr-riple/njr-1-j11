package common;

/**
 * Created by Sobercheg on 12/7/13.
 */
public class ListNode {
    public int data;
    public ListNode next;

    public ListNode(int data, ListNode next) {
        this.data = data;
        this.next = next;
    }

    public ListNode(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "value=" + data +
                '}';
    }
}
