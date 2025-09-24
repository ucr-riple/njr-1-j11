package leetcode;

import java.util.Stack;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class QueueWithStacks<T> {

    Stack<T> in = new Stack<T>();
    Stack<T> out = new Stack<T>();

    public void enqueue(T element) {
        while (!out.empty()) {
            in.push(out.pop());
        }
        in.push(element);
    }

    public T dequeue() {
        while (!in.empty()) {
            out.push(in.pop());
        }
        return out.pop();
    }

    public static void main(String[] args) {
        QueueWithStacks<String> queueWithStacks = new QueueWithStacks<String>();
        queueWithStacks.enqueue("First");
        queueWithStacks.enqueue("Second");
        System.out.println(queueWithStacks.dequeue());
        queueWithStacks.enqueue("Third");
        System.out.println(queueWithStacks.dequeue());
        System.out.println(queueWithStacks.dequeue());
    }

}
