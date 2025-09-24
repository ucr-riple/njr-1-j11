/*
 * CoreMapTest.java
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

import java.util.HashMap;
import java.util.Map;

import net.jcores.jre.utils.map.MapEntry;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class CoreMapTest {

    /** */
    @Test
    public void testKeysAndValues() {
        final Map<String, String> m = new HashMap<String, String>();
        m.put("a", "x");
        m.put("b", "y");
        
        Assert.assertEquals("x", $(m).value("a"));
        Assert.assertEquals("y", $(m).value("b"));
        Assert.assertEquals("a", $(m).key("x"));
        Assert.assertEquals("b", $(m).key("y"));
    }
    
    /** */
    //@Test
    public void testMapUtil() {
        MapEntry<String, String> e1 = new MapEntry<String, String>("He" + "llo", "World");
        MapEntry<String, String> e2 = new MapEntry<String, String>("Hello", "Wo" + "rld");
        Assert.assertEquals(e1, e2);
        
        
        final Map<String, String> m = new HashMap<String, String>();
        m.put("a", "x");
        m.put("b", "y");
        Assert.assertEquals($(m), $(m));
    }
}
