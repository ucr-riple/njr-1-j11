package leetcode;

import java.util.Stack;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class StackWithMin {
    private Stack<Integer> values = new Stack<Integer>();
    private Stack<Integer> mins = new Stack<Integer>();

    public void push(int value) {
        if (mins.isEmpty() || value <= mins.peek()) {
            mins.push(value);
        }
        values.push(value);
    }

    public int pop() {
        if (values.isEmpty()) throw new IllegalStateException("No more elements");
        if (values.peek().equals(mins.peek())) {
            mins.pop();
        }
        return values.pop();
    }

    public int getMin() {
        if (values.isEmpty()) throw new IllegalStateException("No more elements");
        return mins.peek();
    }
}
