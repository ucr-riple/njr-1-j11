package careercup.google;

/**
 * Created by Sobercheg on 12/19/13.
 * <p/>
 * http://www.careercup.com/question?id=5154240347504640
 * <p/>
 * Implement a circular queue of integers of user-specified size using a simple array.
 * Provide routines to initialize(), enqueue() and dequeue() the queue. Make it thread safe.
 */
public class CircularQueue {

    private final int capacity;
    private int size;
    private final int[] array;
    private int head;
    private int tail;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
        initialize();
    }

    public synchronized void initialize() {
        head = 0;
        tail = 0;
        size = 0;
    }

    public synchronized void enqueue(int element) {
        if (size >= capacity) throw new IllegalStateException("Queue is full, cannot add more elements");
        array[head] = element;
        advanceHeadPosition();
        size++;
    }

    public synchronized int dequeue() {
        if (size <= 0) throw new IllegalStateException("Queue is empty, cannot get elements");
        int element = array[tail];
        advanceTailPosition();
        size--;
        return element;
    }

    private void advanceHeadPosition() {
        head = (head - 1 + capacity) % capacity;
    }

    private void advanceTailPosition() {
        tail = (tail + 1) % capacity;
    }

    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(2);
        queue.initialize();
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        queue.dequeue();
    }
}
