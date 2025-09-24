/*
 * API.java
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

import static sandbox.oldversions.net.jcores.v1.Core.$;

import java.util.List;

import net.jcores.jre.interfaces.functions.F0;
import net.jcores.jre.interfaces.functions.F1;
import sandbox.dummys.F0Impl;

/**
 * @author rb
 */
public class APIv1 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        APIv1 spawn = $(APIv1.class).spawn();
        Class<APIv1> class1 = $(APIv1.class).get();
        
        
        APIv1 object = $(spawn).get();
        
        List<String> lines = $("open/").open().lines();

        
        // Sinnlos ... s: lines ist besser 
        for(String s : $(lines).each()) {
            // 
        }

        
        lines = $(lines).map(new F1<String, String>() {
            @Override
            public String f(String x) {
                return x.toLowerCase();
            }
        });

        
        $(spawn, object).ifAll(new F0() {
            @Override
            public void f() {
                //
            }
        });
        
        if(!$(spawn, object).hasAll()) return;
        
        String x = $("asd").get("QWeq");    
        
        $(F0.class).implementor(F0Impl.class);
        $(F0.class).spawn();
        $(APIv1.class).spawn();
        $(APIv1.class).spawned().filter(new F1<APIv1, Boolean>() {
            @Override
            public Boolean f(APIv1 xx) {
                return null;
            }
        });
        
        APIv1 api = $(APIv1.class).spawned().get(0);
        
        // GEHT NICHT:
       // $(API.class).spawned().send("myChannel", new Object());
        
        
        $(api).call("toString");
        
        
    }

}
