package threads.sleep;

import java.util.concurrent.TimeUnit;

public class LetMeSleep {

	public static void main(String[] args) {
		try {
			System.out.println("I am going to sleep for 5 seconds");
			//Thread.sleep(5000);
			TimeUnit.SECONDS.sleep(5);
			System.out.println("I woke up");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
