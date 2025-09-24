package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Test2 {

	public static void main(String[] args) {

		Foo f = new Foo();
		f.start();

		f.waitToBegin();

		System.out.println("begin");

		for (int i = 0;; i++) {
			Buffer b = Buffer.alloc(1037, 0xF008A4);
			b.putInt(0, i);
			while (!f.feed(b));
		}


	}

	private static class Foo extends Thread {

		private Object waiter = new Object();

		private BlockingQueue<Buffer> q = new ArrayBlockingQueue<Buffer>(16);

		public Foo() {

		}

		public void waitToBegin() {
			synchronized (waiter) {
				try {
					waiter.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public boolean feed(Buffer b) {
			return q.offer(b);
		}

		@Override
		public void run() {

			synchronized (waiter) {
				waiter.notify();
			}

			int count = 0;

			long nano_start = timenanos();

			while (true) {

				try {
					Buffer b = null;
					while (b == null)
						b = q.poll();

					if (b.getTag() == 0xF008A4) {

						count++;

					}

					b.release();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (count == 10000000) {
					long nanos = timenanos() - nano_start;
					System.out.println("poll-and-release (ns): " + (nanos / 10000000));
					count = 0;
					nano_start = timenanos();
				}

			}

		}

	}

}
