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
package benchmarks.benchmarks.mappinglooping;

import static net.jcores.jre.CoreKeeper.$;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import junit.data.Data;
import net.jcores.jre.interfaces.functions.F1;
import benchmarks.benchmarker.Benchmark;
import benchmarks.model.TaskData;
import benchmarks.model.TaskSolver;

/**
 * regexdna task from the "Computer Language Benchmarks Game"
 * 
 * @author Ralf Biedert
 */
public class ComplexLoopingStringArray extends Benchmark<Data> {

    /*
     * (non-Javadoc)
     * 
     * @see benchmarks.benchmarker.Benchmark#data()
     */
    @Override
    public TaskData<Data> data() {
        return new TaskData<Data>();
    }

    public static String ff(String x) {
        String rval = x;
        for (int i = 0; i < 100; i++) {
            if (i%2 == 0) {
                rval = rval.toLowerCase();
            } else {
                rval = rval.toUpperCase();
            }
        }

        return rval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see benchmarks.benchmarker.Benchmark#solver()
     */
    @Override
    public Collection<TaskSolver<Data>> solver() {
        final Collection<TaskSolver<Data>> rval = new ArrayList<TaskSolver<Data>>();

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s1.lowercase.plain", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    for (int i = 0; i < x.s1.length; i++) {
                        v.addAndGet(ff(x.s1[i]).hashCode());
                    }
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s1.lowercase.forEach", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    $(x.s1).forEach(new F1<String, Void>() {
                        @Override
                        public Void f(String xx) {
                            v.addAndGet(ff(xx).hashCode());
                            return null;
                        }
                    });
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s1.lowercase.map", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    $(x.s1).map(new F1<String, Void>() {
                        @Override
                        public Void f(String xx) {
                            v.addAndGet(ff(xx).hashCode());
                            return null;
                        }
                    });
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s5.lowercase.plain", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    for (int i = 0; i < x.s5.length; i++) {
                        v.addAndGet(ff(x.s5[i]).hashCode());
                    }
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s5.lowercase.forEach", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    $(x.s5).forEach(new F1<String, Void>() {
                        @Override
                        public Void f(String xx) {
                            v.addAndGet(ff(xx).hashCode());
                            return null;
                        }
                    });
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("s5.lowercase.map", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int jj = 0; jj < 1000; jj++) {
                    $(x.s5).map(new F1<String, Void>() {
                        @Override
                        public Void f(String xx) {
                            v.addAndGet(ff(xx).hashCode());
                            return null;
                        }
                    });
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("sn.lowercase.plain", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                for (int i = 0; i < x.sn.length; i++) {
                    v.addAndGet(ff(x.sn[i]).hashCode());
                }
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("sn.lowercase.forEach", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                $(x.sn).forEach(new F1<String, Void>() {
                    @Override
                    public Void f(String xx) {
                        v.addAndGet(ff(xx).hashCode());
                        return null;
                    }
                });
                return Integer.valueOf(v.get());
            }
        }));

        // ADD SOLVER
        rval.add(new TaskSolver<Data>("sn.lowercase.map", new F1<Data, Object>() {
            @SuppressWarnings("static-access")
            @Override
            public Object f(Data x) {
                final AtomicInteger v = new AtomicInteger();
                $(x.sn).map(new F1<String, Void>() {
                    @Override
                    public Void f(String xx) {
                        v.addAndGet(ff(xx).hashCode());
                        return null;
                    }
                });
                return Integer.valueOf(v.get());
            }
        }));
        return rval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see benchmarks.benchmarker.Benchmark#name()
     */
    @Override
    public String name() {
        return "Mapping and Looping (Complex Operations)";
    }
}
