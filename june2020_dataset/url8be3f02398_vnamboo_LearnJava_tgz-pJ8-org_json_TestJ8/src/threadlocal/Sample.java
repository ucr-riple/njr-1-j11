package threadlocal;

public class Sample implements Runnable{
	String name;
	public static void main(String[] args) {
		Sample s1=new Sample();
		s1.name="1";
		Thread ts1=new Thread(s1);
		ts1.start();
		
		Sample s2=new Sample();
		s2.name="2";
		Thread ts2=new Thread(s2);
		ts2.start();
		
		
	}
	@Override
	public void run() {		
		System.out.println(name+ " : " +UniqueThreadIdGenerator.getCurrentThreadId());
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name+ " : " +UniqueThreadIdGenerator.getCurrentThreadId());
	}
	 
}
