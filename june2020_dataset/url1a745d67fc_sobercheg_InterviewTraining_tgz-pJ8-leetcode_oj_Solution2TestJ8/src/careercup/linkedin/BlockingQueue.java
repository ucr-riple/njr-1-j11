package careercup.linkedin;

import java.util.LinkedList;

/**
 * Created by Sobercheg on 12/15/13.
 * http://www.careercup.com/question?id=14622668
 * <p/>
 * Implement a thread-safe Blocking queue in C/C++(POSIX) or Java
 */
public class BlockingQueue<T> {

    private final LinkedList<T> queue;
    private final int limit;

    public BlockingQueue(int limit) {
        this.limit = limit;
        this.queue = new LinkedList<T>();
    }

    public synchronized void enqueue(T element) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.add(element);
    }


    public synchronized T dequeue() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        if (queue.size() == this.limit) {
            notifyAll();
        }
        return queue.getFirst();
    }

}
