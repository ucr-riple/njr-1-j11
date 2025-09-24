/*
 * BenchmarkResults.java
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
package benchmarks.benchmarker;

import static net.jcores.jre.CoreKeeper.$;

/**
 * Stores the results of a given benchmark run.
 * 
 * @author Ralf Biedert
 */
public class BenchmarkResults {
    /** The actual timing results in µ-seconds */
    final private long[] results;

    /**
     * Results of each run in µ-seconds.
     * 
     * @param results
     */
    public BenchmarkResults(long[] results) {
        this.results = results;
    }

    /**
     * Returns the average of the n last runs.
     * 
     * @param nlast
     * @return . 
     */
    public long average(int nlast) {
        int delta = this.results.length - nlast;
        long avg = 0;

        for (int i = delta; i < this.results.length; i++) {
            avg += this.results[i];
        }

        avg /= this.results.length - delta;

        return avg;
    }

    /**
     * Returns the values for the benchmark
     * 
     * @return .
     */
    public long[] values() {
        return this.results;
    }

    public long median(int i) {
        return $($.box(this.results)).slice(-i, -1).get(0.5).longValue();
    }
}
