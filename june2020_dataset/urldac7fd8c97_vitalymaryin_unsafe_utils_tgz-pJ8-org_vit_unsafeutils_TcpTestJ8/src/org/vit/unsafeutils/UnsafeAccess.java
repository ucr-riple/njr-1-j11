package org.vit.unsafeutils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
public class UnsafeAccess {
    public static final Unsafe unsafe;
    public static final boolean isAligned;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            String arch = java.security.AccessController.doPrivileged
                    (new sun.security.action.GetPropertyAction("os.arch", ""));
            isAligned = arch.equals("i386") || arch.equals("x86") || arch.equals("amd64")
                    || arch.equals("x86_64");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static final long BYTE_ARRAY_OFFSET = unsafe.arrayBaseOffset(byte[].class);
    public static final long BOOLEAN_ARRAY_OFFSET = unsafe.arrayBaseOffset(boolean[].class);
    public static final long INT_ARRAY_OFFSET = unsafe.arrayBaseOffset(int[].class);
    public static final long LONG_ARRAY_OFFSET = unsafe.arrayBaseOffset(long[].class);
    public static final long DOUBLE_ARRAY_OFFSET = unsafe.arrayBaseOffset(double[].class);

    public static final long SIZE_OF_BOOLEAN = 1L;
    public static final long MEM_OFFSET = 8L;
    public static final long SIZE_OF_BYTE = 1L;
    public static final long SIZE_OF_SHORT = 2L;
    public static final long SIZE_OF_INT = 4L;
    public static final long SIZE_OF_LONG = 8L;
    public static final long SIZE_OF_DOUBLE = 8L;

    public static final String EMPTY_STRING = "";

    public static final int CACHE_LINE_SIZE = 64;
    public static final int PAGE_SIZE = UnsafeAccess.unsafe.pageSize();
    public static final byte[] TCP_DELIMITER = "VitMrTcp".getBytes();
    public static final int TCP_DEFAULT_MESSAGE_SIZE = 1024;
}
