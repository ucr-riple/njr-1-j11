package com.ctriposs.bigcache.storage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * This class is called when buffers should be allocated aligned.
 * 
 * It creates a block of memory aligned to memory page size, and limits the amount of buffer each processor can access.
 * 
 * @author zhoumy
 *
 */
public class AlignOffHeapStorage extends OffHeapStorage {

	public AlignOffHeapStorage(int capacity) {
		super(capacity, ByteBuffer.allocateDirect(capacity));
	}

	@Override
	public void close() throws IOException {
		if (!disposed.compareAndSet(false, true))
			return;
		if (byteBuffer == null)
			return;
		try {
			Field cleanerField = byteBuffer.getClass().getDeclaredField("cleaner");
			cleanerField.setAccessible(true);
			Object cleaner = cleanerField.get(byteBuffer);
            if (cleaner != null) {
                Method cleanMethod = cleaner.getClass().getMethod("clean");
                cleanMethod.invoke(cleaner);
            }
		} catch (Exception e) {
			throw new Error(e);
		}
	}
}
