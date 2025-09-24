package edu.sandbox.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Lena on 23.04.14.
 */
public class CallableAndFutureTask implements Callable<Integer> {
    int step;
    public CallableAndFutureTask(int i) {
        step = i;
    }

    public Integer call() {
        Integer result;
        for (result = 0; result < 98000; result += step) {
            if (result % 888 == 0)
                System.out.println(step + " - " + result);
        }
        System.out.println(step + " - " + result);
        return result;
    }

    public static void main(String[] args) {

        CallableAndFutureTask callableAndFutureTask1 = new CallableAndFutureTask(1);
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(callableAndFutureTask1);
        Thread thread1 = new Thread(futureTask1);
        CallableAndFutureTask callableAndFutureTask2 = new CallableAndFutureTask(2);
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(callableAndFutureTask2);
        Thread thread2 = new Thread(futureTask2);
        CallableAndFutureTask callableAndFutureTask3 = new CallableAndFutureTask(3);
        FutureTask<Integer> futureTask3 = new FutureTask<Integer>(callableAndFutureTask3);
        Thread thread3 = new Thread(futureTask3);
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            System.out.println("1 " + futureTask1.get());
            System.out.println("2 " + futureTask2.get());
            System.out.println("3 " + futureTask3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
