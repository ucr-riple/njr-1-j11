package server;

import com.sun.istack.internal.NotNull;
import daoo.ioc.Task;
import daoo.ioc.TaskExecutor;

import java.util.LinkedList;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class PoolThreadTaskExecutor implements TaskExecutor {
    private final WorkQueue workqueue;

    public PoolThreadTaskExecutor(int n) {
        workqueue = new WorkQueue(n);
    }

    @Override
    public void execute(@NotNull Task task) {
//        ExecutorService executorService = newFixedThreadPool(16);
//        executorService.execute(task);
//        threads.get(executedAmount);
//        ThreadFactory threadFactory = defaultThreadFactory();
        workqueue.execute(task);
    }

    public class WorkQueue {
        private final int nThreads;
        @NotNull
        private final PoolWorker[] threads;
        @NotNull
        private final LinkedList<Runnable> queue;

        public WorkQueue(int nThreads) {
            this.nThreads = nThreads;
            queue = new LinkedList<Runnable>();
            threads = new PoolWorker[nThreads];

            for (int i = 0; i < nThreads; i++) {
                threads[i] = new PoolWorker();
                threads[i].start();
            }
        }

        public void execute(@NotNull Runnable r) {
            synchronized (queue) {
                queue.addLast(r);
                queue.notify();
            }
        }

        private class PoolWorker extends Thread {
            public void run() {
                Runnable r;

                while (true) {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            try {
                                queue.wait();
                            } catch (InterruptedException ignored) {
                            }
                        }

                        r = (Runnable) queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try {
                        r.run();
                    } catch (RuntimeException e) {
                        // You might want to log something here
                    }
                }
            }
        }
    }

}
