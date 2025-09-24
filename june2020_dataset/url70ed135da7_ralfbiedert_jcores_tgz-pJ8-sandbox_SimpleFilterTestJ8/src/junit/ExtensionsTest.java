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
import junit.framework.Assert;
import net.jcores.jre.CommonCore;
import net.jcores.jre.cores.CoreString;
import net.jcores.jre.cores.adapter.AbstractAdapter;
import net.jcores.jre.extensions.GlobalExtension;
import net.jcores.jre.extensions.LocalExtension;
import net.jcores.jre.interfaces.functions.F1;

import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class ExtensionsTest {
    /** Test extension for wrapping */
    public static class E extends LocalExtension<String> {
        private static final long serialVersionUID = 5660493453611259815L;

        public E(CommonCore commonCore, AbstractAdapter<String> adapter) {
            super(commonCore, adapter);
        }

        public CoreString lower() {
            return map(new F1<String, String>() {

                @Override
                public String f(String x) {
                    return x.toLowerCase();
                }

            }).as(CoreString.class);
        }
    }
    
    
    /** Test extension for wrapping */
    public static class S extends GlobalExtension {
        String uniqueID = $.sys.uniqueID();
        
        public String random() {
            return this.uniqueID;
        }
    }
   

    /** */
    @Test
    public void testWrapping() {
        Assert.assertEquals("abc", $("A", "B", "C").as(E.class).lower().join());
    }
    
    /** */
    @Test
    public void testSingleton() {
        final String random = $(S.class).random();
        Assert.assertEquals(random, $(S.class).random());
    }

}
