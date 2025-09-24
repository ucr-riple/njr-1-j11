package server;

import com.sun.istack.internal.NotNull;
import daoo.ioc.Task;
import daoo.ioc.TaskExecutor;

import java.util.LinkedList;
import java.util.List;

public class PoolThreadTaskExecutor2 implements TaskExecutor {
    private final Thread[] threads;
    private int nextAvailable;
    private final LinkedList<Task> tasks = new LinkedList<>();
    private final int poolSize;

    public PoolThreadTaskExecutor2(int poolSize) {
        this.poolSize = poolSize;
        threads = new Thread[poolSize];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = buildThread();
        }
    }

    public PoolThreadTaskExecutor2() {
        this(8);
    }


    @Override
    public void execute(@NotNull Task task) {
        tasks.add(task);
        threads[nextAvailable].start();
        threads[nextAvailable] = buildThread();
        nextAvailable = (nextAvailable + 1) % poolSize;
    }

    private Thread buildThread() {
        return new Thread() {
            @Override
            public void run() {
                tasks.removeLast().run();
                System.out.println("Running in thread: " + getName() + " - NextAvailable: " + nextAvailable);
            }
        };
    }
}
