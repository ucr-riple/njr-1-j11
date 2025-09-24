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

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import net.jcores.jre.cores.CoreMap;
import net.jcores.jre.utils.map.MapEntry;

/**
 * @author rb
 *
 */
public class SimpleExec {

    static Random rnd = new Random();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        $("ls -la", "ls -la", "ls -la", "ls -la", "ls -la").exec().print();
        // $(".").file().dir().filter(".*java$").string().replace("^(.*)$", "ls $1").exec().print();
        //$(".").file().dir().filter(".*java$").string().exec("ls $1").print();
        
        
        Map<String, Integer> s = $.map();
        s.put("a", 1);
        s.put("b", 2);

        
        Map compound = $("a", 1, "b", 2).compound();
        Object object = compound.get("a");
        System.out.println(object);
        
        
        
        CoreMap<String, Integer> cc = $(s);
        cc.get(4);
        
        
        for (MapEntry<String, Integer> me : cc) {
        }
    }

}
