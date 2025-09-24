package nz.net.initial3d.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import nz.net.initial3d.util.Profiler;
import sun.misc.Unsafe;

/**
 * Provides a means of recycling memory obtained with malloc, because malloc is slow.
 *
 * @author Ben Allen
 *
 */
final class Buffer {

	private static final Unsafe unsafe = Util.getUnsafe();

	@SuppressWarnings("unchecked")
	private static final BlockingQueue<Buffer>[] buf_queues = new BlockingQueue[31];

	private static final int SEC_MALLOC = Profiler.createSection("Buffer-malloc");
	private static final int SEC_FREE = Profiler.createSection("Buffer-free");

	static {
		for (int i = 0; i < buf_queues.length; i++) {
			buf_queues[i] = new ArrayBlockingQueue<Buffer>(2048 >> (i / 3));
			// System.out.println(i + " : " + buf_queues[i].capacity());
		}
	}

	private static int sizeindex(int b) {
		if (b < 1) return 0;
		int s = 0;
		for (b--; b != 0; b >>= 1, s++)
			;
		return s;
	}

	public static Buffer alloc(int bytes) {
		return alloc(bytes, 0);
	}

	public static Buffer alloc(int bytes, int tag) {
		if (bytes < 1) throw new IllegalArgumentException();
		int s = sizeindex(bytes);
		Buffer buf = buf_queues[s].poll();
		if (buf == null) {
			Profiler.enter(SEC_MALLOC);
			try {
				System.out.println("Buffer malloc(): " + (1 << s) + " bytes");
				long p = unsafe.allocateMemory(1 << s);
				buf = new Buffer(s, p);
			} finally {
				Profiler.exit(SEC_MALLOC);
			}
		}
		buf.tag = tag;
		buf.extra = null;
		buf.refcount.set(1);
		return buf;
	}

	private int sidx;
	private AtomicInteger refcount;
	private long pBuffer;

	private Map<String, Object> extra;
	private int tag;

	private Buffer(int sidx_, long pBuffer_) {
		sidx = sidx_;
		pBuffer = pBuffer_;
		refcount = new AtomicInteger(0);
	}

	public int getTag() {
		return tag;
	}

	public int getSize() {
		return 1 << sidx;
	}

	public long getPointer() {
		return pBuffer;
	}

	public void acquire() {
		refcount.incrementAndGet();
	}

	public void release() {
		if (refcount.decrementAndGet() == 0) {
			// return to pool
			tag = 0;
			extra = null;
			if (!buf_queues[sidx].offer(this)) {
				Profiler.enter(SEC_FREE);
				try {
					System.out.println("Buffer free(): " + (1 << sidx) + " bytes.");
					unsafe.freeMemory(pBuffer);
				} finally {
					Profiler.exit(SEC_FREE);
				}
			}
			// System.out.println("Returning " + this + " to pool.");
		}
	}

	public <T> void putExtra(String key, T value) {
		if (extra == null) extra = new HashMap<String, Object>();
		extra.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getExtra(String key) {
		return (T) extra.get(key);
	}

	public int getInt(long q) {
		return unsafe.getInt(pBuffer + q);
	}

	public long getLong(long q) {
		return unsafe.getLong(pBuffer + q);
	}

	public float getFloat(long q) {
		return unsafe.getFloat(pBuffer + q);
	}

	public double getDouble(long q) {
		return unsafe.getDouble(pBuffer + q);
	}

	public void putInt(long q, int val) {
		unsafe.putInt(pBuffer + q, val);
	}

	public void putLong(long q, long val) {
		unsafe.putLong(pBuffer + q, val);
	}

	public void putFloat(long q, float val) {
		unsafe.putFloat(pBuffer + q, val);
	}

	public void putDouble(long q, double val) {
		unsafe.putDouble(pBuffer + q, val);
	}

	@Override
	public String toString() {
		return String.format("Buffer[%d bytes]@%x", getSize(), getPointer());
	}

}
