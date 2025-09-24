/*
 * SimpleScript.java
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

import net.jcores.jre.cores.CoreObject;
import net.jcores.jre.cores.CoreString;
import net.jcores.jre.interfaces.functions.F1;

/**
 * @author rb
 * 
 */
public class SimpleEval {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CoreString input = $("Evaluated.txt").file().text().string().split("\n");

        CoreObject<String> x0 = x(input, 0);
        CoreObject<String> x1 = x(input, 1);
        CoreObject<String> x2 = x(input, 2);
        CoreObject<String> x3 = x(input, 3);
        CoreObject<String> x4 = x(input, 4);

        for (int a = 0; a < x0.size(); a++) {
            for (int b = 0; b < x1.size(); b++) {
                for (int c = 0; c < x2.size(); c++) {
                    for (int d = 0; d < x3.size(); d++) {
                        for (int e = 0; e < x4.size(); e++) {
                            String s = $(x0.get(a),x1.get(b),x2.get(c),x3.get(d),x4.get(e)).string().join(",") ;
                            if(!input.containssubstr(s))
                            System.out.println(s +": " + input.containssubstr(s));
                            
                        }

                    }

                }

            }

        }

    }

    public static CoreObject<String> x(CoreString s, final int i) {
        return s.map(new F1<String, String>() {
            @Override
            public String f(String x) {
                return x.split(",")[i];
            }
        }).unique();
    }
}
