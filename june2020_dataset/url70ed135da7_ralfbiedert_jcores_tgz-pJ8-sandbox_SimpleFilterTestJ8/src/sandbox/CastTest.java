/*
 * CastTest.java
 * 
 * Copyright (c) 2010, Ralf Biedert All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package sandbox;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import sandbox.dummys.F0Impl;

public class CastTest {
    public static void main(String[] args) throws Exception {
        String ss[] = new String[10];
        Object so[] = new Object[10];

        Object o = new Object();
        String s = "34";

        so[3] = o;
        so[3] = s;

        ss[3] = s;
        // ss[3] = (String) o;

        Object xo[] = (String[]) Array.newInstance(String.class, 10);
        // String xs[] = (String[]) Array.newInstance(Object.class, 10);

        System.out.println(xo);

        Object newInstance = Array.newInstance(int.class, 10);
        System.out.println(newInstance);

        Constructor<CastTest> constructor = CastTest.class.getConstructor(new Class[0]);
        System.out.println(constructor);
        
        //doReflection();
        //doRegular();
        //doConstructor();
        
        //so = ss;
        //so[0] = System.out;
        
        System.out.println(ss[0]);

    }

    public static void doRegular() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            F0Impl a = new F0Impl();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doReflection() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            F0Impl a = (F0Impl) F0Impl.class.newInstance();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doConstructor() throws Exception {
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            Class<? extends PrintStream> class1 = System.err.getClass();
            
            Constructor<F0Impl> constructor = F0Impl.class.getConstructor(null);
            F0Impl newInstance = constructor.newInstance(null);
        }
        System.out.println(System.currentTimeMillis() - start);
    }


}
