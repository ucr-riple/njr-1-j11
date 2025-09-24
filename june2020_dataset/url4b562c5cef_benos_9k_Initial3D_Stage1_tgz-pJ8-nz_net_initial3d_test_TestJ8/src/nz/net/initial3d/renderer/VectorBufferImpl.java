package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import nz.net.initial3d.*;

import sun.misc.Unsafe;

final class VectorBufferImpl extends VectorBuffer {

	private static final int INIT_CAPACITY = 11;
	private static final Unsafe unsafe = getUnsafe();

	private Buffer buf;
	private int capacity;
	private int count;

	VectorBufferImpl() {
		capacity = INIT_CAPACITY;
		buf = Buffer.alloc(capacity * 32, -1);
		count = 0;
	}

	VectorBufferImpl(VectorBufferImpl other_) {
		capacity = other_.capacity;
		count = other_.count;
		buf = Buffer.alloc(capacity * 32, -1);
		unsafe.copyMemory(other_.getPointer(), buf.getPointer(), count * 32);
	}

	private void ensureCapacity() {
		if (count < capacity) return;
		capacity *= 2;
		Buffer buf2 = Buffer.alloc(capacity * 32, -1);
		unsafe.copyMemory(buf.getPointer(), buf2.getPointer(), count * 32);
		buf.release();
		buf = buf2;
	}

	@Override
	protected void finalize() {
		buf.release();
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public void clear() {
		buf.release();
		capacity = INIT_CAPACITY;
		buf = Buffer.alloc(capacity * 32, -1);
		count = 0;
	}

	@Override
	public void add(double x, double y, double z, double w) {
		ensureCapacity();
		long p = buf.getPointer() + count * 32;
		unsafe.putDouble(p, x);
		unsafe.putDouble(p + 8, y);
		unsafe.putDouble(p + 16, z);
		unsafe.putDouble(p + 24, w);
		count++;
	}

	@Override
	public Vec4 get(int i) {
		if (i < 0 || i >= count) throw new IndexOutOfBoundsException();
		long p = buf.getPointer() + i * 32;
		double x = unsafe.getDouble(p);
		double y = unsafe.getDouble(p + 8);
		double z = unsafe.getDouble(p + 16);
		double w = unsafe.getDouble(p + 24);
		return new Vec4(x, y, z, w);
	}

	long getPointer() {
		return buf.getPointer();
	}

}
