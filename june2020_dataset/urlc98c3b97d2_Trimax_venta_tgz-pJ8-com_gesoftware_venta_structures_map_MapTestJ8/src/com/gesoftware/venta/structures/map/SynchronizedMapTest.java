package com.gesoftware.venta.structures.map;

import com.gesoftware.venta.logging.LoggingUtility;

import java.util.Random;

public final class SynchronizedMapTest {
    private static final SynchronizedMap<String, Integer> m_Map = new SynchronizedMap<String, Integer>();
    private static final int c_OperationsCount = 100;

    private static void sleep() {
        try {
            Thread.sleep(25);
        } catch (final Exception ignored) {}
    }

    private static final class CountRetriever implements Runnable {
        @Override
        public final void run() {
            for (int i = 0; i < c_OperationsCount; i++) {
                LoggingUtility.info("Map size: " + m_Map.size());
                sleep();
            }
        }
    }

    private static final class Inserter implements Runnable {
        private final Random m_Generator = new Random();

        @Override
        public final void run() {
            for (int i = 0; i < c_OperationsCount; i++) {
                m_Map.put("key" + m_Generator.nextInt(100), m_Generator.nextInt(1000));
                sleep();
            }
        }
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);
        final Random generator = new Random();

        /* One counter */
        new Thread(new CountRetriever()).start();

        /* Five inserters */
        for (int i = 0; i < 5; i++)
            new Thread(new Inserter()).start();

        /* One requestser */
        while (m_Map.size() > 0) {
            final String key    = "key" + generator.nextInt(100);
            final Integer value = m_Map.remove(key);

            LoggingUtility.info("Selected by key " + key + " => " + value + "; Size = " + m_Map.size());
            sleep();
        }
    }
}
