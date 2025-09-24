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

import static net.jcores.jre.CoreKeeper.$;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rb
 */
public class APIv2 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //APIv2 spawn = $(APIv2.class).spawn();
        //Class<APIv2> class1 = $(APIv2.class).get(0);

        //  APIv2 object = $(spawn).get(0);

        //List<String> lines = $("open/").file().lines().list();

        /*
        lines = $(lines).map(new F1<String, String>() {
            public String f(String x) {
                return x.toLowerCase();
            }
        }, Option.DUMMY, Option.TEST(3)).as(CoreFile.class).lines().list();

        $(spawn, object).ifAll(new F0() {
            @Override
            public void f() {
                //
            }
        });
        */

        //if (channel == null || listener == null) return;
        //if (!$(channel, listener).hasAll()) return;

        String x = $("asd").get("QWeq").substring(0);

        /*
        $(F0.class).implementor(F0Impl.class);
        $(F0.class).spawn();
        $(APIv2.class).spawn();
        */
        /*$(APIv2.class).spawned().filter(new F1<APIv2, Boolean>() {
            @Override
            public Boolean f(APIv2 xx) {
                return null;
            }
        }).call("hash").each(F0.class).f();*/

        //APIv2 api = $(APIv2.class).spawned().get(0);

        // Evtl gg. was tauschen? send auf objekt ist schon sehr invasiv / bzw. speziell
        // $(APIv2.class).spawned().send("myChannel", new Object());
       
        //$(APIv2.class).spawned().each(Runnable.class).run();

        //$(api).call("toString");

        $("Everything Successful").log();
        $("logs/mylog.text").file().append("");

        String string = $(args).filter("-x").get("-x:nothing");

        //List<String> list = $("log").file().lines().list();

        Lock l = new ReentrantLock();
        /*$(l).locked(new F0() {
            public void f() {

            }
        });*/
        
        //$(api).as(ExtensionCore.class).yoyo();
    }

}
