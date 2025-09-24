package nz.net.initial3d.test;

import nz.net.initial3d.util.Profiler;

public class TestProfiler {

	public static void main(String[] args) throws Exception {

		Profiler.setResetInterval(1 * 1000 * 1000 * 1000);

		final int SEC_MAIN = Profiler.createSection("Main");
		final int SEC_BG1 = Profiler.createSection("Background-1");
		final int SEC_BG2 = Profiler.createSection("Background-2");

		Profiler.enter(SEC_MAIN);
		Profiler.enter(SEC_MAIN);
		Profiler.enter(SEC_MAIN);
		Profiler.enter(SEC_MAIN);
		for (int i = 0; i < 5; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						Profiler.enter(SEC_BG1);

						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							//
						}

						Profiler.exit(SEC_BG1);

						Profiler.enter(SEC_BG2);

						try {
							Thread.sleep((long) (Math.random() * 200));
						} catch (InterruptedException e) {
							//
						}

						Profiler.exit(SEC_BG2);
					}
				}

			});
			t.setDaemon(true);
			t.start();
		}

		Thread.sleep(10500);

		Profiler.exit(SEC_MAIN);
		Profiler.exit(SEC_MAIN);
		Profiler.exit(SEC_MAIN);

		Thread.sleep(1000);

		Profiler.exit(SEC_MAIN);

		Thread.sleep(1000);
	}

}
