/*
 * StringUtilsTest.java
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
package junit.internal;

import net.jcores.jre.utils.internal.Strings;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
    @Test
    public void parseExecTest() {
        String[] r;
        
        r = Strings.parseExec("ls -la /");
        Assert.assertEquals("ls", r[0]);
        Assert.assertEquals("-la", r[1]);
        Assert.assertEquals("/", r[2]);

        r = Strings.parseExec("'/My Dir/x' -s 'Hello World' -v  3");
        Assert.assertEquals("/My Dir/x", r[0]);
        Assert.assertEquals("-s", r[1]);
        Assert.assertEquals("Hello World", r[2]);
        Assert.assertEquals("-v", r[3]);
        Assert.assertEquals("3", r[4]);

        r = Strings.parseExec("say 'He\\'s joking!'");
        Assert.assertEquals("say", r[0]);
        Assert.assertEquals("He's joking!", r[1]);
        
        r = Strings.parseExec(" hello ");
        Assert.assertEquals("hello", r[0]);

        r = Strings.parseExec(" 'hello \\'' probably is'' m 'ean ' \" ");
        Assert.assertEquals("hello '", r[0]);
        Assert.assertEquals("probably", r[1]);
        Assert.assertEquals("is", r[2]);
        Assert.assertEquals("", r[3]);       
        Assert.assertEquals("m", r[4]);
        Assert.assertEquals("ean ", r[5]);
        Assert.assertEquals("\"", r[6]);
        
        r = Strings.parseExec(" x'");
        Assert.assertEquals("x", r[0]);
    }
}
