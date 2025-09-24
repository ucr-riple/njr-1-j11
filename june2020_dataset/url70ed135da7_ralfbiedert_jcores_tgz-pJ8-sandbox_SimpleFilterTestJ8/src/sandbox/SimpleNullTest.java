/*
 * SimpleSpeedTests.java
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

import static net.jcores.jre.CoreKeeper.$;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class A {}

class B extends A {}

class C extends B {}

/**
 * @author rb
 * 
 */
public class SimpleNullTest {

    static Random rnd = new Random();

    /**
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("boxing")
    public static void main(String[] args) throws IOException {
        Arrays.deepEquals(null, new Object[] { new Object() });
        System.out.println(Arrays.deepHashCode(null));

        Number n = Math.PI;
        System.out.println(Long.MAX_VALUE);
        Long l = Long.MAX_VALUE;
        System.out.println(l.doubleValue());
        Double d = l.doubleValue();
        Long ll = d.longValue();
        System.out.println(ll);

        System.out.println(n.doubleValue());
        $(3, 4, 5);
        /*
         * Double d[] = new Double[] { null, null, null };
         * 
         * CoreObject<Double> c1 = $(d);
         * System.out.println(c1.size());
         * 
         * CoreObject<B> c2 = c1.map(new F1<Double, B>() {
         * 
         * @Override
         * public B f(Double x) {
         * return null;
         * }
         * });
         * System.out.println(c2.size());
         * 
         * CoreObject<B> c3 = c2.fill(new C());
         * B b = c3.get(1);
         * System.out.println(b);
         */
    }
}
