/*
 * AdapterTest.java
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.data.Data;
import junit.framework.Assert;
import net.jcores.jre.cores.CoreMap;
import net.jcores.jre.cores.CoreNumber;
import net.jcores.jre.cores.CoreObject;
import net.jcores.jre.cores.adapter.CollectionAdapter;
import net.jcores.jre.cores.adapter.MapAdapter;
import net.jcores.jre.interfaces.functions.F1;
import net.jcores.jre.utils.map.MapEntry;

import org.junit.Test;

/**
 * @author Ralf Biedert
 */
public class AdapterTest {

    /** */
    @Test
    public void testCollectionAdapter() {
        final LinkedList<String> linked = new LinkedList<String>(Arrays.asList(Data.sn));
        final CoreObject<String> x = $(linked);

        Assert.assertTrue(x.unsafeadapter() instanceof CollectionAdapter);

        final CoreObject<String> slice = x.slice(40, 60).slice(5, 20).slice(5, 5);
        Assert.assertEquals(5, slice.size());
        Assert.assertEquals("50", slice.get(0));

        final StringBuilder sb = new StringBuilder();
        for (String s : slice) {
            sb.append(s);
        }

        Assert.assertEquals("5051525354", sb.toString());
    }

    /** */
    @Test
    public void testCollectionsParallization() {
        // Repeat the last test a number of times
        for (int c = 0; c < 100; c++) {
            final List<String> linked = new LinkedList<String>(Arrays.asList(Data.sn));
            final CoreObject<String> x = $(linked);
            final CoreNumber as = x.map(new F1<String, Integer>() {
                @Override
                public Integer f(String xx) {
                    return $(xx).I(0);
                }
            }).as(CoreNumber.class);

            Assert.assertEquals(Data.sn.length, as.size());
            Assert.assertEquals(Data.sn.length, as.compact().size());

            final long i = Data.sn.length - 1;
            Assert.assertEquals(i * (i + 1) / 2, (long) as.sum());
        }
    }

    /** */
    @SuppressWarnings("boxing")
    @Test
    public void testMapAdapter() {
        final Map<String, Integer> map = $.map();
        for (String s : Data.sn) {
            map.put(s, $(s).i(0));
        }

        final CoreMap<String, Integer> x = $(map);
        Assert.assertTrue(x.unsafeadapter() instanceof MapAdapter);

        for (MapEntry<String, Integer> e : x) {
            Assert.assertEquals(e.key(), e.value().toString());

        }
    }

}
