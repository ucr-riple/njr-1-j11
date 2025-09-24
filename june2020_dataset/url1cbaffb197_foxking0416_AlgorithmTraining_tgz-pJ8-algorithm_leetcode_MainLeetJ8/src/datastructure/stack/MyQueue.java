package datastructure.stack;

import java.util.Stack;


public class MyQueue {
	Stack<Integer> s1 = new Stack<>();
	Stack<Integer> s2 = new Stack<>();
	
	
	public void push(int value) {
		
		while(s2.isEmpty() == false)
			s1.push(s2.pop());
		
		
		s1.push(value);
	}
	
	public int peek(){
		
		while(s1.isEmpty() == false)
			s2.push(s1.pop());
		
		return s2.peek();

	}
	
	public int pop(){
		while(s1.isEmpty() == false)
			s2.push(s1.pop());
		
		return s2.pop();
	}
}
