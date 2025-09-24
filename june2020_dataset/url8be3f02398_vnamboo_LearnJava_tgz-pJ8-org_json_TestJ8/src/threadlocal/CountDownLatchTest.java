package threadlocal;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	private CountDownLatch initSignal=new CountDownLatch(4);
	
 public static void main(String[] args) throws InterruptedException {
	new CountDownLatchTest().testCountDown();
}
 
 public void testCountDown() throws InterruptedException{
	 initThreads();
	 initSignal.await();
	 System.out.println("All joined");
 }
 
 private void initThreads(){
	 Thread t=new Thread(new TestThread());
	 t.start();
	 t=new Thread(new TestThread());
	 t.start();
	 t=new Thread(new TestThread());
	 t.start();
	 t=new Thread(new TestThread());
	 t.start();
 }
 private class TestThread implements Runnable{

	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());;
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   initSignal.countDown();
	}
	 
 }
 
}
