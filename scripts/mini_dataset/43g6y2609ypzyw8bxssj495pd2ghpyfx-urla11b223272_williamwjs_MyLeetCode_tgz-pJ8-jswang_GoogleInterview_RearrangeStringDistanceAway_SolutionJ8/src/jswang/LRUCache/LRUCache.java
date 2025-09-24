package jswang.LRUCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 11/20/14.
 * Map中记录节点地址，方便直接访问链表中节点
 */
public class LRUCache {
    private int capacity;
    private DoubleListNode head, nail;
    private int listLength;
    private Map<Integer, DoubleListNode> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new DoubleListNode(-1, -1);
        nail = head;
        listLength = 0;
        map = new HashMap<Integer, DoubleListNode>(capacity, 1);
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        DoubleListNode tmp = map.get(key);
        if (tmp != nail) {
            tmp.next.prev = tmp.prev;
            tmp.prev.next = tmp.next;
            nail.next = tmp;
            tmp.prev = nail;
            tmp.next = null;
            nail = tmp;
        }
        return tmp.value;
    }

    public void set(int key, int value) {
        if (!map.containsKey(key)) {
            if (listLength == capacity) {
                map.remove(head.next.key);
                head.next = head.next.next;
                if (head.next != null) head.next.prev = head;
                else nail = head;
                --listLength;
            }
            DoubleListNode tmp = new DoubleListNode(key, value);
            nail.next = tmp;
            tmp.prev = nail;
            nail = tmp;
            map.put(key, tmp);
            ++listLength;
        }
        else {
            DoubleListNode tmp = map.get(key);
            tmp.value = value;
            if (tmp != nail) {
                tmp.next.prev = tmp.prev;
                tmp.prev.next = tmp.next;
                nail.next = tmp;
                tmp.prev = nail;
                tmp.next = null;
                nail = tmp;
            }
        }
    }

    public static void main (String[] args) {
        LRUCache lru = new LRUCache(1);
        lru.set(2,1);
        lru.get(2);
        lru.set(3,2);
        lru.get(2);
        lru.get(3);
    }
}

class DoubleListNode {
    int key, value;
    DoubleListNode prev, next;
    DoubleListNode(int k, int val) {
        key = k;
        value = val;
    }
}