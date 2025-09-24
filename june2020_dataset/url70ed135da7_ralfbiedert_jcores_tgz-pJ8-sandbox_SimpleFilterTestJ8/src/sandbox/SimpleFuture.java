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

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author rb
 *
 */
public class SimpleFuture {

    static Random rnd = new Random();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<?> future = pool.submit(new Runnable() {
            
            @Override
            public void run() {
                System.out.println("entry");
                while(true) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("a");
        future.cancel(true);
        System.out.println("b");
        pool.shutdownNow();
        
        /*
        Future<String> f1 = null;
        Future<String> f2 = null;
        Future<Integer> f3 = null;
        
        // Playing with futures 
        $(f1).await().get(0);
        $(f1).onNext(new F1<String, Void>() {
            @Override
            public Void f(String x) {
                return null;
            }
        });
        $(f1).obtain(0, 10, TimeUnit.DAYS);
        $(f1).obtain(0);

        
        // How can we become async?
        String heavy[] = {};
        $(heavy).map(new F1<String, Void>() {   // This is sync
            @Override
            public Void f(String x) {
                return null;
            }
        });
        
        
        $(heavy).async(null).getClass();
        /*$.async(null).queue();
        $.async(null).finished();
        $.async(null).oneFinished(f);
        */
    }

}
