package util;

public class Wait {
	public static void sec(long s) {
		try {
			Thread.currentThread().sleep(s * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}