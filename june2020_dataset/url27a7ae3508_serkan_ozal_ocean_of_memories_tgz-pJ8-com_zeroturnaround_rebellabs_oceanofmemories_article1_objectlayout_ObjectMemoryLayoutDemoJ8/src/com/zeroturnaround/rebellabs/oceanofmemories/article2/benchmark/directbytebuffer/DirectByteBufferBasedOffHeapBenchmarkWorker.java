package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.directbytebuffer;

import java.nio.ByteBuffer;
import java.lang.reflect.Method;
import sun.nio.ch.DirectBuffer;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.ByteBufferBasedOffHeapBenchmarkWorker;

public class DirectByteBufferBasedOffHeapBenchmarkWorker extends ByteBufferBasedOffHeapBenchmarkWorker {

    public static final String TYPE = "DIRECT BYTE BUFFER BASED OFFHEAP";

    public DirectByteBufferBasedOffHeapBenchmarkWorker(int elementCount) {
        super(elementCount, TYPE);
    }

    @Override
    protected ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size);
    }

    @Override
    public void finish() {
        try {
            Object cleaner = ((DirectBuffer) bb).cleaner();
            if (cleaner != null) {
                Method cleanMethod = cleaner.getClass().getMethod("clean");
                cleanMethod.invoke(cleaner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
