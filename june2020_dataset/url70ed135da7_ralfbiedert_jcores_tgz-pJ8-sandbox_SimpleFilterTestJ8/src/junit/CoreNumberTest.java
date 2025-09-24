/*
 * CoreNumberTest.java
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
import net.jcores.jre.cores.CoreNumber;
import net.jcores.jre.cores.CoreObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class CoreNumberTest {

    /** */
    @Test
    public void testMinMax() {
        Assert.assertEquals(0.0, $.range(101).min(), 0.0);
        Assert.assertEquals(100.0, $.range(101).max(), 0.0);
    }

    /** */
    @Test
    public void testSum() {
        int n = 10000;
        Assert.assertEquals(n*(n-1)/2, $.range(n).sum(), 0.0);
    }
    
    
    /** */
    @SuppressWarnings({ "boxing", "unchecked" })
    @Test
    public void testNullNaN() {
        final CoreObject<Object> mixed = $($.create(1, 100), $.create(null, 100), $.create(Double.NaN, 100)).expand(Object.class); 
        final CoreNumber number = mixed.random(1.0).as(CoreNumber.class);
        
        Assert.assertEquals(100, number.sum(), 0.01);
        Assert.assertEquals(1, number.average(), 0.01);
    }
}
