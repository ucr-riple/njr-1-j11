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

/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */

package sandbox;

import static net.jcores.jre.CoreKeeper.$;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.jcores.jre.cores.CoreObject;
import net.jcores.jre.cores.CoreString;
import net.jcores.jre.interfaces.functions.F0;
import net.jcores.jre.interfaces.functions.F1;
import sandbox.dummys.F0Impl;
import sandbox.extensions.ExtensionCore;

/**
 * @author rb
 */
public class APIv3 {
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        int size = $("hello", null, "world").compact().size();
        System.out.println(size);

        System.out.println($("hello", "you", "world", null).hasAll());

        $("Hello world").log();

        final CoreString lines = $("test.txt").file().text().split("\n").filter("asd");
        CoreString filter = lines.filter("asd");
        System.out.println(filter.as(CoreString.class).file());

        System.out.println($(" asjl lk saklj dlkasj dlkdj alsdj as").split(" ").size());

        $("documentation/TODO.txt").file().text().split("\n").split(" ").filter(".*ish.*").print();
        $("documentation/TODO.txt").file().text().map(new F1<String, File>() {
            public File f(String x) {
                return new File(x.toLowerCase());
            }
        }).as(CoreObject.class);//.print();

        F0 f[] = new F0[] { new F0Impl(), new F0Impl(), new F0Impl() };
        $(f).each(F0.class).f();

        $(f).as(ExtensionCore.class).yoyo();
        //$(f).as(CoreSerializer.class).store("test.xml");
        //System.out.println($("test.xml").as(CoreSerializer.class).load().size());

        /*
        $(F0.class).implementor(F0Impl.class);
        for (int i = 0; i < 100000; i++) {
           // F0 spawn = $(F0.class).spawn();
            if (i % 1000 == 0) {
                //size = $(F0.class).spawned().size();
                //System.out.println(size);
            }
        }*/

        CoreString c1 = $($(f).list()).as(CoreString.class);
        CoreString c2 = $($("hello", "world").list()).as(CoreString.class);
        System.out.println(c1.size());
        System.out.println(c2.size());
        System.out.println($("hello").as(CoreObject.class).call("toUpperCase").get(0));

        System.out.println("---");
        CoreObject<String> expand = $("", "", "").map(new F1<String, String[]>() {
            public String[] f(String x) {
                return new String[] { "Hello", "owlrd" };
            }
        }).expand(String.class).as(CoreString.class);
        System.out.println(expand.size());

        CoreString e2 = $("", "", "").map(new F1<String, List<String>>() {
            public List<String> f(String x) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add("X");
                arrayList.add("y");
                arrayList.add("z");
                return arrayList;
            }
        }).expand(String.class).as(CoreString.class);
        String join = e2.join(" ");
        System.out.println("'" + join + "'");

        $("debug1.txt", "debug2.txt").file().delete().append(join);

        //System.out.println(array[0]);

        System.out.println("---");

        $.report();
    }
}
