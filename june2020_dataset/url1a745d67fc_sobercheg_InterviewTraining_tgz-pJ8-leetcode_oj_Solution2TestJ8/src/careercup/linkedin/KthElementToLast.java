package careercup.linkedin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 12/15/13.
 * <p/>
 * http://www.careercup.com/question?id=14468873
 * Write a function that would return the 5th element from the tail (or end)
 * of a singly linked list of integers, in one pass, and then provide a set
 * of test cases against that function (please do not use any list manipulation
 * functions that you do not implement yourself).
 */

class ListNode<T> {
    T data;
    ListNode<T> next;

    ListNode(T data) {
        this.data = data;
    }

}

class MyList<T> {
    ListNode<T> root;

    public MyList<T> add(T data) {
        if (root == null) {
            root = new ListNode<T>(data);
        } else {
            ListNode<T> newNode = new ListNode<T>(data);
            newNode.next = root;
            root = newNode;
        }
        return this;
    }

    public ListNode<T> getRoot() {
        return root;
    }
}

public interface KthElementToLast<T> {
    public T getKthElementToLast(MyList<T> list, int k);
}

class KthElementToLastPointers<T> implements KthElementToLast<T> {
    public T getKthElementToLast(MyList<T> list, int k) {
        if (list == null || list.getRoot() == null) throw new IllegalArgumentException("List is null");
        if (k < 0) throw new IllegalArgumentException(String.format("k is negative [%s]", k));
        ListNode<T> fastPointer = list.getRoot();

        for (int i = 0; i < k; i++) {
            if (fastPointer == null)
                throw new IllegalArgumentException(String.format("List length [%s] too short for k=%s", i, k));
            fastPointer = fastPointer.next;
        }

        ListNode<T> slowPointer = list.getRoot();
        while (fastPointer.next != null) {
            fastPointer = fastPointer.next;
            slowPointer = slowPointer.next;
        }

        return slowPointer.data;
    }
}

class KthElementToLastQueue<T> implements KthElementToLast<T> {
    public T getKthElementToLast(MyList<T> list, int k) {
        if (list == null || list.getRoot() == null) throw new IllegalArgumentException("List is null");
        if (k < 0) throw new IllegalArgumentException(String.format("k is negative [%s]", k));
        Queue<T> queue = new LinkedList<T>();
        ListNode<T> node = list.getRoot();
        while (node != null) {
            queue.offer(node.data);
            if (queue.size() > k + 1) {
                queue.poll();
            }
            node = node.next;
        }
        return queue.poll();
    }
}

class KthElementToLastTest {
    public static void main(String[] args) {
        KthElementToLast<Integer> kthElementToLast = new KthElementToLastQueue<Integer>();
        testNullListShouldFail(kthElementToLast);
        testNegativeKShouldFail(kthElementToLast);
        testListShorterThanKShouldFail(kthElementToLast);
        testListLengthEqualsK(kthElementToLast);
        testListLengthMoreThanK(kthElementToLast);
        testKequals0(kthElementToLast);
    }

    private static void testKequals0(KthElementToLast<Integer> kthElementToLast) {
        MyList<Integer> list = new MyList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("K=0", 1, kthElementToLast.getKthElementToLast(list, 0));
    }

    private static void testListLengthMoreThanK(KthElementToLast<Integer> kthElementToLast) {
        MyList<Integer> list = new MyList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("List length >= K", 2, kthElementToLast.getKthElementToLast(list, 1));
    }

    private static void testListLengthEqualsK(KthElementToLast<Integer> kthElementToLast) {
        MyList<Integer> list = new MyList<Integer>();
        list.add(1);
        list.add(2);
        assertEquals("List length equals K", 2, kthElementToLast.getKthElementToLast(list, 1));
    }

    private static void assertEquals(String message, Object expected, Object actual) {
        if (!expected.equals(actual))
            throw new IllegalStateException(String.format("%s: actual=%s; expected=%s", message, actual, expected));
    }

    private static void testListShorterThanKShouldFail(KthElementToLast<Integer> kthElementToLast) {
        MyList<Integer> list = new MyList<Integer>();
        list.add(3);
        try {
            kthElementToLast.getKthElementToLast(list, 2);
            throw new IllegalStateException("Should fail because the list is shorter than K");
        } catch (Exception e) {
            // OK
        }
    }

    private static void testNegativeKShouldFail(KthElementToLast<Integer> kthElementToLast) {
        MyList<Integer> list = new MyList<Integer>();
        list.add(3);
        try {
            kthElementToLast.getKthElementToLast(list, -1);
            throw new IllegalStateException("Should fail because of negative K");
        } catch (Exception e) {
            // OK
        }
    }

    private static void testNullListShouldFail(KthElementToLast<Integer> kthElementToLast) {
        try {
            kthElementToLast.getKthElementToLast(null, 3);
            throw new IllegalStateException("Should fail because of a null list");

        } catch (Exception e) {
            // OK
        }
    }

}
