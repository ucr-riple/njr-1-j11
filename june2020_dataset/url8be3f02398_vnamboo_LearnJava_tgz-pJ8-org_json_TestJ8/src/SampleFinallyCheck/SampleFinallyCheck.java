package SampleFinallyCheck;

public class SampleFinallyCheck {

	public static void main(String[] args) {
		new SampleFinallyCheck().checkFinally();
	}
	
	private int doSomething(int value){
		if(value % 2 ==0 ){
			throw new RuntimeException();
		}else {
			return 1;
		}
	}
	
	public void checkFinally() {
		try {
			doSomething(2);			
		}finally{
			System.out.println("Finally executed");
		}
	}
}
