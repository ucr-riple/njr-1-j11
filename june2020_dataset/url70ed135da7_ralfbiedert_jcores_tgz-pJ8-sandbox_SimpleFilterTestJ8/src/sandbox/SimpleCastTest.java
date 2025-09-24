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

import java.awt.Panel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import sandbox.dummys.DiagnosisChannelID;

/**
 * @author rb
 *
 */
public class SimpleCastTest {
    /**
     * @param args
     */
    @SuppressWarnings("boxing")
    public static void main(String[] args) {
        System.out.println($("a", "b", "c").cast(String.class).compact().size());
        
        Panel pp = null;
        JPanel p = null;
        $(p).add($(pp));
        $(p, p, p);
        $(pp, pp);
        $(pp, p);
        
        $("a", "b").add("c", "d").subtract("b").intersect("b", "d").add("b", "d").unique().print();
        Map<String, Object> map =  $("a", new Object(), "b", new Object()).compound();
        
        Map<String, Number> mm = new HashMap<String, Number>();
        mm.put("blah", 123);
        mm.put("blubb", Math.PI);
        
        $(mm).print();
        $(map).print();
    }
    
    public Class<? extends DiagnosisChannelID<?>>[] observedChannels() {
        return new Class[0];
        /// return $(TestChannel.class, LoggingChannel1.class).unsafearray();
        //CoreObject<Class<? extends DiagnosisChannelID<?>>> xx = $;
    }
}
