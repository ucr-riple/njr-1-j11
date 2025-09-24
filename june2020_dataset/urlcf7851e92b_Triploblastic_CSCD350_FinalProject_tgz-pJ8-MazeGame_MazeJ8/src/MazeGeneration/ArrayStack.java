package MazeGeneration;

public class ArrayStack<E> implements Stack<E>{
		
		private static final int DEFAULT_CAPACITY = 1000;
		private int capacity;
		private E[] stack;
		private int index = -1;
		
		public ArrayStack() {this(DEFAULT_CAPACITY);}
		
		public ArrayStack(int cap) {
			capacity = cap;
			stack = (E[]) new Object[capacity];
		}
	
		public int size() {return index+1;}
		
		public boolean isEmpty() {return (index<0);}
		
		public void push(E element) throws FullStackException {
			    if (size() == capacity)
			      throw new FullStackException("Stack is full.");
			    stack[++index] = element;
		}
		
		public E peek() throws EmptyStackException {
			if (size() == 0)
				throw new EmptyStackException("Stack is empty");
			return stack[index];		
		}
		
		public E pop() throws EmptyStackException {
			if (size() == 0)
				throw new EmptyStackException("Stack is empty");
			E item = stack[index];
			stack[index--] = null;	
			return item;
		}
		
		public boolean contains(E item) {
			for(E e: stack) {
				if (e.equals(item))
					return true;
			}
			return false;				
		}
		
		public String toString() {
			  String s;
			  s = "[";
			  if (size() > 0) s+= stack[0];
			  if (size() > 1)
		      for (int i = 1; i <= size()-1; i++) 
		    	  s += ", " + stack[i];
			  return s + "]";
		}

}//ArrayStack.class
