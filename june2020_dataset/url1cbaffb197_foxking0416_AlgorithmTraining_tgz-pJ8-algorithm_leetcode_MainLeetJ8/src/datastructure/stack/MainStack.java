package datastructure.stack;

import java.util.Stack;

public class MainStack {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		testStack();
//		testStoreMinInStack();
		//testCreateMyQueueWithTwoStack();
		testSortStack();
	}
	
	private static void testStoreMinInStack(){
		StackTest stackTest = new StackTest();
		stackTest.storeMinTest();
	}
	
	private static void testCreateMyQueueWithTwoStack(){
		StackTest.createQueueTest();
	}
	
	private static void testSortStack(){
		StackTest.sortStackTest();
	}
	
	private static void testStack(){
		Stack<Integer> stack1 = new Stack<Integer>();
		
		stack1.push(1);
		stack1.push(2);
		stack1.push(3);
		stack1.push(4);
		stack1.push(5);
		
		System.out.print("stack1 get(0) = " + stack1.get(0));
		System.out.print("\n");
		System.out.print("stack1 get(1) = " + stack1.get(1));
		System.out.print("\n");
		System.out.print("stack1 get(2) = " + stack1.get(2));
		System.out.print("\n");
		
		System.out.print("stack1 peek = " + stack1.peek());
		System.out.print("\n");


		
		System.out.print("stack1 pop = " + stack1.pop());
		System.out.print("\n");
		System.out.print("stack1 pop = " + stack1.pop());
		System.out.print("\n");
		System.out.print("stack1 pop = " + stack1.pop());
		System.out.print("\n");
		
		System.out.print("stack1 get(0) = " + stack1.get(0));
		System.out.print("\n");
		System.out.print("stack1 get(1) = " + stack1.get(1));
		System.out.print("\n");

		System.out.print("stack1 peek = " + stack1.peek());
		System.out.print("\n");
		System.out.print("\n");
		
		Stack<Integer> stack2 = new Stack<Integer>();
		stack2.add(1);
		stack2.add(2);
		stack2.add(3);
		stack2.add(4);
		stack2.add(5);
		
		System.out.print("stack2 get(0) = " + stack2.get(0));
		System.out.print("\n");
		System.out.print("stack2 get(1) = " + stack2.get(1));
		System.out.print("\n");
		System.out.print("stack2 get(2) = " + stack2.get(2));
		System.out.print("\n");
		
		System.out.print("stack2 peek = " + stack2.peek());
		System.out.print("\n");


		
		System.out.print("stack2 pop = " + stack2.pop());
		System.out.print("\n");
		System.out.print("stack2 pop = " + stack2.pop());
		System.out.print("\n");
		System.out.print("stack2 pop = " + stack2.pop());
		System.out.print("\n");
		
		System.out.print("stack2 get(0) = " + stack2.get(0));
		System.out.print("\n");
		System.out.print("stack2 get(1) = " + stack2.get(1));
		System.out.print("\n");

		System.out.print("stack2 peek = " + stack2.peek());
		System.out.print("\n");
		System.out.print("\n");
	}
}
