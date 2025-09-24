package com.bigfatplayer.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class App 
{
    public int add(int a, int b)
    {
        return a + b;
    }

    public int mul(int a, int b)
    {
        return a * b;
    }

    public double divide(int a, int b) throws IllegalArgumentException {
        if (b == 0) {
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        }

        return a / b;
    }

    public static void main( String[] args )
    {
        Logger logger = LoggerFactory.getLogger(App.class);
        App app = new App();

        int result = app.add(2, 2);
        System.out.format("2 + 2 = %s\n", result);

        Thread serverThread = new Thread(new AppServer());
        Thread clientThread1 = new Thread(new AppClient());
        Thread clientThread2 = new Thread(new AppClient());

        serverThread.start();
        clientThread1.start();
        clientThread2.start();
    }
}
