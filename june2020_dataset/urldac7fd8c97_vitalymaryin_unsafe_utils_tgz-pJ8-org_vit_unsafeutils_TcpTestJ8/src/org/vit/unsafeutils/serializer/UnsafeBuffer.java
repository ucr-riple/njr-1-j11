package org.vit.unsafeutils.serializer;

/*
Copyright 2014 Vitaly Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.util.Arrays;

import static org.vit.unsafeutils.UnsafeAccess.*;

public class UnsafeBuffer {
    private long pos = 0;
    private byte[] buffer;

    public UnsafeBuffer(final byte[] buf) {
        if (null == buf)
            throw new NullPointerException("buffer cannot be null");
        buffer = buf;
    }

    public UnsafeBuffer(final byte[] buffer, int offset) {
        if (null == buffer)
            throw new NullPointerException("buffer cannot be null");
        this.buffer = buffer;
        pos = offset;
    }

    public void setOffset(int offset) {
        this.pos = offset;
    }

    public long getOffset() {
        return pos;
    }

    public void reset() {
        this.pos = 0;
    }

    public byte[] toByteArray() {
        delim();
        return Arrays.copyOf(buffer, (int) pos);
    }

    public void putBoolean(final boolean value) {
        ensureCapacity(MEM_OFFSET);
        unsafe.putBoolean(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += MEM_OFFSET;
    }

    public boolean getBoolean() {
        boolean value = unsafe.getBoolean(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += MEM_OFFSET;
        return value;
    }

    public void putBooleanArray(final boolean[] values) {

        int alignedSize = getAlignedOffset(values.length);
        int bytesToCopy = values.length;

        ensureCapacity(alignedSize);

        putInt(bytesToCopy);
        putInt(alignedSize);
        unsafe.copyMemory(values, BOOLEAN_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += alignedSize;
    }

    public boolean[] getBooleanArray() {
        int arraySize = getInt();
        int alignedPos = getInt();
        boolean[] values = new boolean[arraySize];

        long bytesToCopy = values.length;
        unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                values, BOOLEAN_ARRAY_OFFSET,
                bytesToCopy);
        pos += alignedPos;

        return values;
    }

    public void putInt(final int value) {
        ensureCapacity(MEM_OFFSET);
        unsafe.putInt(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += MEM_OFFSET;
    }

    public int getInt() {
        int value = unsafe.getInt(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += MEM_OFFSET;
        return value;
    }

    public void putIntArray(final int[] values) {

        int alignedSize = getAlignedOffset(values.length * SIZE_OF_INT);
        int bytesToCopy = (int)(values.length * SIZE_OF_INT);

        ensureCapacity(alignedSize);

        putInt(bytesToCopy);
        putInt(alignedSize);

        unsafe.copyMemory(values, INT_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += alignedSize;
    }

    public int[] getIntArray() {
        int bytesToCopy = getInt();
        int alignedPos = getInt();
        int[] values = new int[(int)(bytesToCopy / SIZE_OF_INT)];

        unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                values, INT_ARRAY_OFFSET,
                bytesToCopy);
        pos += alignedPos;

        return values;
    }

    public void putLong(final long value) {
        ensureCapacity(SIZE_OF_LONG);
        unsafe.putLong(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += SIZE_OF_LONG;
    }

    public long getLong() {
        long value = unsafe.getLong(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += SIZE_OF_LONG;

        return value;
    }

    public void putLongArray(final long[] values) {
        putInt(values.length);

        long bytesToCopy = values.length * SIZE_OF_LONG;

        ensureCapacity(bytesToCopy);

        unsafe.copyMemory(values, LONG_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += bytesToCopy;
    }

    public long[] getLongArray() {
        int arraySize = getInt();
        long[] values = new long[arraySize];

        long bytesToCopy = values.length * SIZE_OF_LONG;
        unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                values, LONG_ARRAY_OFFSET,
                bytesToCopy);
        pos += bytesToCopy;

        return values;
    }

    public void putDoubleArray(final double[] values) {
        putInt(values.length);

        long bytesToCopy = values.length * SIZE_OF_DOUBLE;

        ensureCapacity(bytesToCopy);

        unsafe.copyMemory(values, DOUBLE_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += bytesToCopy;
    }

    public double[] getDoubleArray() {
        int arraySize = getInt();
        double[] values = new double[arraySize];

        long bytesToCopy = values.length * SIZE_OF_DOUBLE;
        unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                values, DOUBLE_ARRAY_OFFSET,
                bytesToCopy);
        pos += bytesToCopy;

        return values;
    }


    public void putString(String str) {
        if (str == null)
            str = EMPTY_STRING;

        byte[] byteString = str.getBytes();
        int alignedSize = getAlignedOffset(byteString.length);

        ensureCapacity(alignedSize);

        int bytesToCopy = byteString.length;
        putInt(bytesToCopy);
        putInt(alignedSize);

        unsafe.copyMemory(byteString, BYTE_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += alignedSize;
    }

    public String getString() {
        int arraySize = getInt();
        int alignedPos = getInt();
        if (arraySize > 0){
            byte[] byteStr = new byte[arraySize];
            long bytesToCopy = byteStr.length;
            unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                    byteStr, BYTE_ARRAY_OFFSET,
                    bytesToCopy);
            pos += alignedPos;
            return new String(byteStr);
        }else {
            pos += alignedPos;
            return null;
        }
    }

    public void putStringArray(final String[] values) {
        int size = values.length;
        putInt(size);

        for(int i=0;i<values.length;i++){
            putString(values[i]);
        }
    }

    public String[] getStringArray() {
        int arraySize = getInt();
        String[] values = new String[arraySize];

        for(int i=0;i<arraySize;i++){
            values[i] = getString();
        }
        return values;
    }

    public void putByte(byte value) {
        ensureCapacity(MEM_OFFSET);
        unsafe.putByte(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += MEM_OFFSET;
    }

    public byte getByte() {
        byte value = unsafe.getByte(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += MEM_OFFSET;
        return value;
    }

    public void putByteArray(final byte[] values) {
        int alignedSize = getAlignedOffset(values.length);
        int bytesToCopy = values.length;

        ensureCapacity(alignedSize);

        putInt(bytesToCopy);
        putInt(alignedSize);

        unsafe.copyMemory(values, BYTE_ARRAY_OFFSET,
                buffer, BYTE_ARRAY_OFFSET + pos,
                bytesToCopy);
        pos += alignedSize;
    }

    public byte[] getByteArray() {
        int arraySize = getInt();
        int alignedPos = getInt();
        byte[] values = new byte[arraySize];

        long bytesToCopy = values.length;
        unsafe.copyMemory(buffer, BYTE_ARRAY_OFFSET + pos,
                values, BYTE_ARRAY_OFFSET,
                bytesToCopy);
        pos += alignedPos;

        return values;
    }

    public void putDouble(final double value) {
        ensureCapacity(SIZE_OF_DOUBLE);
        unsafe.putDouble(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += SIZE_OF_DOUBLE;
    }

    public double getDouble() {
        double value = unsafe.getDouble(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += SIZE_OF_DOUBLE;
        return value;
    }

    public void putShort(short value) {
        ensureCapacity(MEM_OFFSET);
        unsafe.putShort(buffer, BYTE_ARRAY_OFFSET + pos, value);
        pos += MEM_OFFSET;
    }

    public short getShort() {
        short value = unsafe.getShort(buffer, BYTE_ARRAY_OFFSET + pos);
        pos += MEM_OFFSET;
        return value;
    }


    private int getAlignedOffset(long unaligned){
        return (int)(unaligned % MEM_OFFSET == 0?unaligned:(unaligned + MEM_OFFSET - (unaligned % MEM_OFFSET)));
    }

    private void ensureCapacity (long next) {
        if (pos >= buffer.length -1 - next)
            resizeBuffer();
    }

    private void resizeBuffer(){
        byte[] tmp = new byte[buffer.length << 1];
        System.arraycopy(buffer, 0, tmp, 0, buffer.length);
        buffer = tmp;
    }

    private void delim() {
        putByteArray(TCP_DELIMITER);
    }
}
