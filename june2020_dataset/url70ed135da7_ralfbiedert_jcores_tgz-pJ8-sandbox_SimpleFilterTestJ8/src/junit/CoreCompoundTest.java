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
import net.jcores.jre.utils.map.Compound;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class CoreCompoundTest {

    /** */
    @SuppressWarnings({ "boxing", "unchecked" })
    @Test
    public void testBasic() {
        Compound c1 = $("a", "b", "c", "d").compound();
        Compound c2 = $("a", "b", "c", "d").compound();
        
        Assert.assertEquals("b", c1.s("a"));
        Assert.assertEquals("b", c2.s("a"));
        Assert.assertEquals("nope", c1.get("b", "nope"));
        
        Compound c3 = $("a", 2.0, "b", 4.0).debug().compound();
        Assert.assertEquals(2.0, c3.d("a"), 0.01);
        Assert.assertEquals(4.0, c3.get("b", Double.class), 0.01);
    }
}
