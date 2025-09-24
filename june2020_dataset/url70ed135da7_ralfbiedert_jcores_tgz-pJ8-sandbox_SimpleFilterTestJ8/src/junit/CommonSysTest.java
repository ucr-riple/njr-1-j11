/*
 * CommonSysTest.java
 * 
 * Copyright (c) 2011, Ralf Biedert All rights reserved.
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

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;
import net.jcores.jre.interfaces.functions.F0;
import net.jcores.jre.options.ID;
import net.jcores.jre.options.KillSwitch;

import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class CommonSysTest {

    /** */
    @Test
    public void testTimes() {
        final AtomicInteger counter = new AtomicInteger();
        final F0 f = new F0() {
            @Override
            public void f() {
                counter.incrementAndGet();
            }
        };
        
        $.sys.oneTime(f, 0);
        $.sys.sleep(100);
        Assert.assertEquals(1, counter.get());
        
        
        counter.set(0);
        $.sys.oneTime(f, 100, KillSwitch.TIMED(50));
        $.sys.sleep(150);
        Assert.assertEquals(0, counter.get());
        

        counter.set(0);
        $.sys.manyTimes(f, 10, KillSwitch.TIMED(500));
        $.sys.sleep(550);
        int i = counter.get();
        Assert.assertTrue(i > 40);
        $.sys.sleep(500);
        Assert.assertEquals(i, counter.get());
    }
    
    /** */
    @Test
    public void testID() {
        String u1 = $.sys.uniqueID();
        String u2 = $.sys.uniqueID();
        String s1 = $.sys.uniqueID(ID.USER);
        String s2 = $.sys.uniqueID(ID.USER);
        // String w1 = $.sys.uniqueID(ID.SYSTEM);
        // String w2 = $.sys.uniqueID(ID.SYSTEM);
        
        Assert.assertNotSame(u1, u2);
        Assert.assertFalse(u1.equals(u2));
        Assert.assertEquals(s1, s2);
        Assert.assertFalse("UNAVAILABLE".equals(s1));
        // Assert.assertEquals(w1, w2);// Fails on Lion ...
        // Assert.assertFalse("UNAVAILABLE".equals(w1)); 
        
        System.out.println(s1);
    }
}
