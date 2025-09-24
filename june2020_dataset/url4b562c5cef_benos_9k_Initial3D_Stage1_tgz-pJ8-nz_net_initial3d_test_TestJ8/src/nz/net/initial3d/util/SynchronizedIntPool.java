package nz.net.initial3d.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronized subtype of IntPool. Uses busy waiting to avoid putting the waiting thread to sleep, as the time spent
 * asleep would far exceed the time necessary to wait before the lock was available.
 *
 * @author Ben Allen
 *
 */
public class SynchronizedIntPool extends IntPool {

	private final Lock lock = new ReentrantLock();

	/**
	 * Construct a SynchronizedIntPool with the specified range.
	 *
	 * @param min_
	 *            Inclusive lower bound
	 * @param max_
	 *            Exclusive upper bound
	 */
	public SynchronizedIntPool(int min_, int max_) {
		super(min_, max_);
	}

	@Override
	public int alloc() {
		while (!lock.tryLock());
		try {
			return super.alloc();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void free(int m) {
		while (!lock.tryLock());
		try {
			super.free(m);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isAllocated(int m) {
		while (!lock.tryLock());
		try {
			return super.isAllocated(m);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isAvailable(int m) {
		while (!lock.tryLock());
		try {
			return super.isAvailable(m);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		while (!lock.tryLock());
		try {
			return super.toString();
		} finally {
			lock.unlock();
		}
	}

}
