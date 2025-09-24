package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import static nz.net.initial3d.renderer.Type.*;
import sun.misc.Unsafe;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

final class RasterPipe {

	// IDEA: interleaved division for better load distribution
	// block-0 : thread-0
	// block-1 : thread-1
	// block-2 : thread-2
	// block-3 : thread-0 ...and so on
	// if interleaved, can change viewport without finish!

	static final int POINTS = 1;
	static final int LINES = 2;
	static final int TRIANGLES = 3;

	private final WorkerThread[] workers;

	RasterPipe(int threadcount_) {
		workers = new WorkerThread[threadcount_];
		for (int i = 0; i < threadcount_; i++) {
			workers[i] = new WorkerThread();
			workers[i].start();
		}

	}

	@Override
	protected void finalize() {

	}

	/**
	 * This pipe runs asynchronously, so must not access client-side state.
	 *
	 * Renderer state (unsafe representation) at the start of the buffer.
	 *
	 * @param wb
	 */
	void feed(int mode, Buffer wb, long pPrim, int count, Object obj_color0) {
		Job j = new Job(mode, wb, pPrim, count, obj_color0);
		for (WorkerThread w : workers) {
			// acquire for each worker thread
			wb.acquire();
			w.feed(j);
		}
		// main thread is done with it
		wb.release();
	}

	void finish() {
		for (WorkerThread w : workers) {
			w.finish();
		}
	}

	private class Job {

		public final int mode;
		public final Buffer wb;
		public final long pPrim;
		public final int count;
		public final Object obj_color0;

		public Job(int mode_, Buffer wb_, long pPrim_, int count_, Object obj_color0_) {
			mode = mode_;
			wb = wb_;
			pPrim = pPrim_;
			count = count_;
			obj_color0 = obj_color0_;
		}
	}

	private class WorkerThread extends Thread {

		private BlockingQueue<Job> jobs = new ArrayBlockingQueue<Job>(1024);
		private final Object waiter_begin = new Object();
		private final Object waiter_finish = new Object();
		private volatile boolean waiting = true;

		public WorkerThread() {
			setDaemon(true);
			setName("I3D-raster-" + getId());
		}

		public void feed(Job j) {
			while (!jobs.offer(j)) {
				// this hopefully shouldn't happen
				Thread.yield();
			}
			if (waiting) {
				synchronized (waiter_begin) {
					waiter_begin.notify();
				}
			}
		}

		public boolean isWaiting() {
			return waiting;
		}

		public void finish() {
			while (!waiting) {
				synchronized (waiter_finish) {
					try {
						waiter_finish.wait();
					} catch (InterruptedException e) {
						//
					}
				}
			}
		}

		@Override
		public void run() {

			while (true) {
				try {

					Job j = jobs.poll();
					if (j == null) {
						waiting = true;
						synchronized (waiter_finish) {
							waiter_finish.notify();
						}
						while (j == null) {
							synchronized (waiter_begin) {
								waiter_begin.wait();
							}
							j = jobs.poll();
						}
					}
					waiting = false;

					try {
						switch (j.mode) {
						case POINTS:
							rasterisePoints(j);
							break;
						case LINES:
							rasteriseLines(j);
							break;
						case TRIANGLES:
							rasteriseTriangles(j);
							break;
						default:
							throw nope("Bad raster mode.");
						}
					} finally {
						// this thread is done with it
						j.wb.release();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void rasterisePoints(Job j) {

	}

	private void rasteriseLines(Job j) {

	}

	private void rasteriseTriangles(Job j) {

	}

}
