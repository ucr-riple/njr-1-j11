/*
 * CoreStringTest.java
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
package junit;

import static net.jcores.jre.CoreKeeper.$;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import net.jcores.jre.interfaces.functions.F0R;
import net.jcores.jre.interfaces.functions.F1V;
import net.jcores.jre.options.KillSwitch;
import net.jcores.jre.utils.Async;
import net.jcores.jre.utils.map.MapUtil;
import net.jcores.jre.utils.map.generators.NewUnsafeInstance;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class CommonCoreTest {


    /** */
    @SuppressWarnings("boxing")
    @Test
    public void testMap() {
        final MapUtil<String, List<Integer>> m1 = $.map();
        m1.generator(new NewUnsafeInstance<String, List<Integer>>(ArrayList.class));

        m1.get("a").add(1);
        Assert.assertEquals(m1.get("a").get(0), Integer.valueOf(1));
    }
    
    

    /** */
    @Test
    public void testClone() {
        final Object o = new Object();
        final Object o2 = $.clone(o);
        
        Assert.assertNull(o2);
        
        // final Number n = Integer.valueOf(123);
        // Assert.assertEquals(n, $.clone(n));
    }

    
    /** */
    @Test
    public void testAsync() {
        final AtomicReference<String> ref = new AtomicReference<String>();
        final F0R<String> f = new F0R<String>() {
            @Override
            public String f() {
                $.sys.sleep(100);
                return "Hello World";
            }
        };
        
        
        $.async(f).onNext(new F1V<String>() {
            @Override
            public void fV(String x) {
                ref.set(x);
            }
        });
        $.sys.sleep(150);
        Assert.assertEquals("Hello World", ref.get());

        
        /*// Not a valid test case since we, by design, sleep for 100ms and don't care if we wake up early.
        ref.set(null);
        $.async(f, KillSwitch.TIMED(50)).onNext(new F1V<String>() {
            @Override
            public void fV(String x) {
                ref.set(x);
            }
        });
        $.sys.sleep(150);
        Assert.assertEquals(null, ref.get());
       */

        ref.set(null);
        $.async(f).onNext(new F1V<String>() {
            @Override
            public void fV(String x) {
                ref.set(x);
            }
        }, KillSwitch.TIMED(50));
        $.sys.sleep(150);
        Assert.assertEquals(null, ref.get());
        
        
        Async<String> async = $.async(f);
        Assert.assertEquals(0, async.available().size());
        $.sys.sleep(150);
        Assert.assertEquals(1, async.available().size());
        Assert.assertEquals(0, async.available().size());
    }

}
