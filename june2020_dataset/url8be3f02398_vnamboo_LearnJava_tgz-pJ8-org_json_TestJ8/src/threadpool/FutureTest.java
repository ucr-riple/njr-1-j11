package threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exe=Executors.newFixedThreadPool(5);
		System.out.println("ExeCuters created");
		Future<String> task= exe.submit(new Callable<String>(){
			@Override
			public String call() throws RuntimeException {
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "Hello World!";
			}
			
		});
		Thread.sleep(1000);
		System.out.println(task.get());
	}
	
}
