package MazeGeneration;

public class ArrayStackTester {
	
	public static void main(String[] args) {
		System.out.println("do nothing");
		//defaultCapacityTest();
		//exceptionsTest();
				
	}
	
	public static void defaultCapacityTest() {
		ArrayStack<Integer> s1 = new ArrayStack();
			for (int i = 0; i < 100; i++) {
				s1.push((Integer)i);
				Integer i1 = s1.peek();
				Integer i2 = s1.pop();
				System.out.println("peek: " + i1 + " pop: " + i2);
			}
	}//defaultCapacityTest()
	
	public static void exceptionsTest() {
		ArrayStack<Integer> s1 = new ArrayStack(1);
		for (int i = 0; i < 1; i++) 
			s1.push((Integer)i);
		try {	
			s1.push(new Integer(1));
		} catch (Exception ex) {
			System.out.println("push exception: " + ex);
		} try {
			s1.pop();
			s1.pop();	
		} catch (Exception ex) {
			System.out.println("pop exception: " +ex);
		} try {
			s1.peek();
		} catch (Exception ex) {
			System.out.println("peek exception: " +ex);
		}
	}//exceptionsTest()

}
