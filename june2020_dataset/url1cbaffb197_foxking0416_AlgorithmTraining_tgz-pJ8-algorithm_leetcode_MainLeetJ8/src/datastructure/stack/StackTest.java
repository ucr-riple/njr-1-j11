package datastructure.stack;

import java.util.Stack;

public class StackTest {

	
	public void storeMinTest(){
		
		
		StackWithMin stackWithMin = new StackWithMin();
		stackWithMin.push(30);
		stackWithMin.push(11);
		stackWithMin.push(50);
		stackWithMin.push(5);
		stackWithMin.push(40);
		
		System.out.print("Minimum is " + stackWithMin.getMin());
	}
	
	
	
	public static void createQueueTest(){
		
		MyQueue queue = new MyQueue();
		
		queue.push(1);
		System.out.print(queue.peek() + "\n");
		queue.push(2);
		System.out.print(queue.peek() + "\n");
		queue.push(3);
		System.out.print(queue.peek() + "\n");
		queue.push(4);
		System.out.print(queue.peek() + "\n");
		
		
		System.out.print(queue.pop() + "\n");
		System.out.print(queue.pop() + "\n");
		System.out.print(queue.pop() + "\n");
		System.out.print(queue.pop() + "\n");
		
	}
	
	public static void sortStackTest(){
		Stack<Integer> stack = new Stack<>();
		
		stack.push(1);
		stack.push(7);
		stack.push(3);
		stack.push(9);
		stack.push(5);
		
		Stack<Integer> sortedStack = sortStack(stack);
		
		while (sortedStack.isEmpty() == false) {
			System.out.print(sortedStack.pop() + ", ");
		}
		
	}
	
	private static Stack<Integer> sortStack(Stack<Integer> s){
		
		Stack<Integer> sortedStack = new Stack<Integer>();
		
		while(s.isEmpty() == false){
			if(sortedStack.isEmpty() == true)
				sortedStack.push(s.pop());
			else{

				int newValue = s.pop();
				
				while(sortedStack.isEmpty() == false && sortedStack.peek() <= newValue ){
					s.push(sortedStack.pop());
					
				}
				
				sortedStack.push(newValue);
				
				
			}
		}
		
		
		
		return sortedStack;
	}
	
}
