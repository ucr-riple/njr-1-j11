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
package benchmarks.benchmarks.reversecomplement;

import static net.jcores.jre.CoreKeeper.$;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import net.jcores.jre.interfaces.functions.F1;
import benchmarks.benchmarker.Benchmark;
import benchmarks.model.TaskData;
import benchmarks.model.TaskSolver;

/**
 * regexdna task from the "Computer Language Benchmarks Game"
 * 
 * @author Ralf Biedert
 */
public class ReverseComplement extends Benchmark<Void> {

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#data()
     */
    @Override
    public TaskData<Void> data() {
        return new TaskData<Void>();
    }

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#solver()
     */
    @Override
    public Collection<TaskSolver<Void>>
    solver() {
        final Collection<TaskSolver<Void>> rval = new ArrayList<TaskSolver<Void>>();
        
        // Add solver
        rval.add(new TaskSolver<Void>("plain.reverse.st", new F1<Void, Object>() {
            @Override
            public Object f(Void x) {
                InputStream stream = ReverseComplement.class.getResourceAsStream("reverse-input.txt");
                String main = null;
                try {
                    main = SolverVanillaST.main(stream);
                    $("test.plain.txt").file().delete().append(main);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return main;
            }
        }));

        
        return rval;
    }
    

    /* (non-Javadoc)
     * @see benchmarks.benchmarker.Benchmark#name()
     */
    @Override
    public String name() {
        return "Reverse Complement";
    }
}
