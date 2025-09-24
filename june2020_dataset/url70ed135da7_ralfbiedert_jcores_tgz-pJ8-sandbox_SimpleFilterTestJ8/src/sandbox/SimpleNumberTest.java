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
import net.jcores.jre.cores.CoreNumber;

/**
 * @author rb
 * 
 */
public class SimpleNumberTest {
    /**
     * @param args
     */
    @SuppressWarnings("boxing")
    public static void main(String[] args) {
        CoreNumber number = $(2.0, 4, 6, null, 8, 100);

        System.out.println(number.average());
        System.out.println(number.variance());
        
        System.out.println($.range(1, 50000).random(10).as(CoreNumber.class).standarddeviation());
        System.out.println($.range(1, 50).random(6).string().join(" "));
        System.out.println($.range(1, 50).random(1.0).string().join(" "));

        System.out.println($(2, -2, 2, -2).standarddeviation());
        System.out.println($(2, -2, 2, -2).variance());

        
        System.out.println();
        Double d = Double.NaN;
        System.out.println(Math.min(3, Double.NaN));
        System.out.println(d);
        System.out.println(d.intValue());
    }
}
