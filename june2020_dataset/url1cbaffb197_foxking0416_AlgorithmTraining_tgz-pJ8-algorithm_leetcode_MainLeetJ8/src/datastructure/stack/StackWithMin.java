package datastructure.stack;

import java.util.Stack;

public class StackWithMin extends Stack<Integer> {

	Stack<Integer> stackMin;
	
	public StackWithMin() {
		stackMin = new Stack<Integer>();
		
		// TODO Auto-generated constructor stub
	}
	
	public void push(int value){
		if(stackMin.empty() == true)
			stackMin.push(value);
		else if(value <= stackMin.peek()){
			stackMin.push(value);
		}
		
		super.push(value);
	}
	
	public Integer pop(){
		if(super.peek() == stackMin.peek())
			stackMin.pop();
		
		return super.pop();
	}
	
	public int getMin(){
		return stackMin.peek();
	}
}
