package nz.net.initial3d.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Integer pool, suitable for implementing id recycling. Size of the pool is fixed upon construction.
 *
 * @author Ben Allen
 *
 */
public class IntPool {

	private final Set<Integer> used = new HashSet<Integer>();
	private final Set<Integer> pool = new HashSet<Integer>();
	private final int pool_min, pool_max;
	private int pool_next;

	/**
	 * Construct an IntPool with the specified range.
	 *
	 * @param min_
	 *            Inclusive lower bound
	 * @param max_
	 *            Exclusive upper bound
	 */
	public IntPool(int min_, int max_) {
		if (min_ >= max_) throw new IllegalArgumentException("min_ must be < max_.");
		pool_min = min_;
		pool_next = min_;
		pool_max = max_;
	}

	/**
	 * @return The inclusive lower bound for values that may be returned by <code>alloc()</code> for this pool.
	 */
	public int min() {
		return pool_min;
	}

	/**
	 * @return The exclusive upper bound for values that may be returned by <code>alloc()</code> for this pool.
	 */
	public int max() {
		return pool_max;
	}

	/**
	 * Allocate an integer from the pool, removing it from the pool (and recording it as allocated). Returned
	 * integers have inclusive lower bound <code>min()</code> and exclusive upper bound <code>max()</code>.
	 *
	 * @return An integer from the pool.
	 * @throws IllegalStateException
	 *             If the pool is exhausted.
	 */
	public int alloc() {
		int m = 0;
		if (pool.isEmpty()) {
			// create new int
			if (pool_next < pool_max) {
				m = pool_next++;
			} else {
				throw new IllegalStateException("Integer pool exhausted.");
			}
		} else {
			// get int from pool
			Iterator<Integer> it = pool.iterator();
			m = it.next();
			it.remove();
		}
		used.add(m);
		return m;
	}

	/**
	 * Return an integer to the pool, making it available to be allocated. It is an error to return an integer obtained
	 * from one call to <code>alloc()</code> more than once.
	 *
	 * @param m
	 *            Integer to return to the pool.
	 * @throws IllegalStateException
	 *             If <code>m</code> is not currently allocated.
	 */
	public void free(int m) {
		if (!used.remove(m)) {
			throw new IllegalStateException("Integer '" + m + "' not allocated.");
		}
		pool.add(m);
	}

	/**
	 * Determine if an integer is currently allocated from the pool.
	 *
	 * @param m
	 *            Integer to test.
	 * @return True iff <code>m</code> is allocated.
	 */
	public boolean isAllocated(int m) {
		return used.contains(m);
	}

	/**
	 * Determine if an integer is currently available to be returned by a call to <code>alloc()</code>. An integer is
	 * available if it is in the range of this pool and not currently allocated.
	 *
	 * @param m
	 *            Integer to test.
	 * @return True iff <code>m</code> is available.
	 */
	public boolean isAvailable(int m) {
		return (pool_next <= m && m < pool_max) || pool.contains(m);
	}

	@Override
	public String toString() {
		return String.format("IntPool[used:%d, free:%d, min:%d, next:%d, max:%d]", used.size(), pool.size(), pool_min,
				pool_next, pool_max);
	}

}
