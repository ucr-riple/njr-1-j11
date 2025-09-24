package threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exe=Executors.newFixedThreadPool(5);
		System.out.println("ExeCuters created");
		exe.execute(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(1000000000);
				} catch (InterruptedException e) {
					System.out.println("Interrupted by shutdown");
				}
			}
			
		});
		Thread.sleep(1000);
		List<Runnable> tasks=exe.shutdownNow();
		System.out.println(tasks.size());
		try {
			exe.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
