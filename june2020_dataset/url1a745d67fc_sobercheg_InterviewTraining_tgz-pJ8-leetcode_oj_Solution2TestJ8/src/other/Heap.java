package other;

/**
 * Created by Sobercheg on 11/24/13.
 */
public class Heap<T extends Comparable<? extends T>> {

    private T[] values = (T[]) new Comparable[100];
    private int current = 0;
    private final Type type;

    static enum Type {
        MIN, MAX
    }

    public Heap(Type type) {
        this.type = type;
    }

    public void offer(T value) {
        values[current] = value;
        siftUp(current);
        current++;
    }

    public T poll() {
        if (current == 0) return null;
        T value = values[0];
        current--;
        values[0] = values[current];
        bubbleDown(0);
        return value;
    }

    public T peek() {
        return values[0];
    }

    public void build(T[] values) {
        current = values.length;
        System.arraycopy(values, 0, this.values, 0, values.length);
        for (int i = values.length / 2; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    private void bubbleDown(int index) {
        // for MIN heaps swap parent with its min child if parent > child
        int largest = index;
        if (left(index) < current && shouldSwap(values[largest], values[left(index)])) {
            largest = left(index);
        }
        if (right(index) < current && shouldSwap(values[largest], values[right(index)])) {
            largest = right(index);
        }
        if (largest != index) {
            swap(largest, index);
            bubbleDown(largest);
        }

    }

    private void siftUp(int index) {
        if (index == 0) return;
        if (shouldSwap(values[parent(index)], values[index])) {
            swap(index, parent(index));
            siftUp(parent(index));
        }
    }

    private void swap(int i1, int i2) {
        T tmp = values[i1];
        values[i1] = values[i2];
        values[i2] = tmp;
    }


    private <T extends Comparable> boolean shouldSwap(T parent, T child) {
        return type == Type.MAX ? parent.compareTo(child) < 0 : parent.compareTo(child) > 0;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return index * 2 + 2;
    }

    public static void main(String[] args) {
        Heap<Integer> maxHeap = new Heap<Integer>(Type.MAX);
        maxHeap.offer(1);
        System.out.println(maxHeap.peek());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());

        maxHeap.offer(1);
        maxHeap.offer(2);
        maxHeap.offer(3);
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println();

        maxHeap.build(new Integer[]{6, 1, 2, 8, -9});
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());

    }
}
