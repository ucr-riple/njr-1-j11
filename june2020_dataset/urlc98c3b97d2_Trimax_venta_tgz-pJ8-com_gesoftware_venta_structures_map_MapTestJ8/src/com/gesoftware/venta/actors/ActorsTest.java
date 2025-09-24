package com.gesoftware.venta.actors;

import com.gesoftware.venta.logging.LoggingUtility;

import java.io.Serializable;
import java.util.*;

public final class ActorsTest {
    private final static int c_Port = 5501;

    private static class A implements Serializable {

    }

    private static class B extends A {

    }

    private static class C implements Serializable {

    }

    private static class D extends C {

    }

    private static class UnknownClass implements Serializable {

    }

    private static void startTheater() {
        Theater theater = new Theater(c_Port);
        theater.registerActor(A.class, new IActor<A>() {
            @Override
            public Serializable react(String clientID, A object) {
                LoggingUtility.info("A reactor");
                return new C();
            }
        });

        theater.registerActor(B.class, new IActor<B>() {
            @Override
            public Serializable react(String clientID, B object) {
                LoggingUtility.info("B reactor");
                return new D();
            }
        });

        /* Starting theater*/
        (new Thread(theater)).start();
    }

    private static class Task extends TimerTask {
        private final Puppeteer m_Puppeteer;
        private final Random m_Generator = new Random();

        public Task(final Puppeteer puppeteer) {
            m_Puppeteer = puppeteer;
        }

        @Override
        public void run() {
            if ((m_Generator.nextInt(100)) > 50)
                m_Puppeteer.giveCommand(new A());
            else
                m_Puppeteer.giveCommand(new B());
            if (m_Generator.nextInt(10) == 0)
                m_Puppeteer.giveCommand(new UnknownClass());
        }
    }

    private static void startPuppeteer() {
        final Puppeteer puppeteer = new Puppeteer("localhost", c_Port);
        puppeteer.registerDispatcher(C.class, new IDispatcher<C>() {

            @Override
            public Serializable dispatch(C object) {
                LoggingUtility.info("C answer");
                return null;
            }
        });
        puppeteer.registerDispatcher(D.class, new IDispatcher<D>() {
            @Override
            public Serializable dispatch(D object) {
                LoggingUtility.info("D answer");
                return null;
            }
        });

        (new java.util.Timer("Puppeteer")).schedule(new Task(puppeteer), 0, 1000);
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);

        startTheater();
        startPuppeteer();
    }
}
