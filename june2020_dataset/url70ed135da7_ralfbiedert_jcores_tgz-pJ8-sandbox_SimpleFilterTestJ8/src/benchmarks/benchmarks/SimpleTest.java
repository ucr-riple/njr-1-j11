/*
 * SimpleTest.java
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
package benchmarks.benchmarks;

import static net.jcores.jre.CoreKeeper.$;

import java.util.ArrayList;
import java.util.Collection;

import junit.data.Data;
import net.jcores.jre.interfaces.functions.F1;
import benchmarks.benchmarker.Benchmark;
import benchmarks.model.TaskData;
import benchmarks.model.TaskSolver;

/**
 * A very simple test.
 * 
 * @author Ralf Biedert
 */
public class SimpleTest extends Benchmark<Data> {

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#data()
     */
    @Override
    public TaskData<Data> data() {
        return new TaskData<Data>();
    }

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#solver()
     */
    @Override
    public Collection<TaskSolver<Data>> solver() {
        final Collection<TaskSolver<Data>> rval = new ArrayList<TaskSolver<Data>>();


        // ADD SOLVER 
        rval.add(new TaskSolver<Data>("s1.overhead.plain", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                int v = 0;
                for(int i = 0; i<10000; i++) {
                    v += x.s1.length;
                }
                return Integer.valueOf(v);
            }
        }));

        // ADD SOLVER 
        rval.add(new TaskSolver<Data>("s1.overhead.jcores", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                int v = 0;
                for(int i = 0; i<10000; i++) {
                    v += $(x.s1).size();
                }
                return Integer.valueOf(v);
            }
        }));


        return rval;
    }

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#name()
     */
    @Override
    public String name() {
        return "Simple Task";
    }
}
