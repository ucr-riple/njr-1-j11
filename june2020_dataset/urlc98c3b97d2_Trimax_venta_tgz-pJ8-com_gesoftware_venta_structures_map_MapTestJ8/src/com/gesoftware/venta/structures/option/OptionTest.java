package com.gesoftware.venta.structures.option;

import com.gesoftware.venta.logging.LoggingUtility;

public final class OptionTest {

    private static class A {

    }

    private static class B extends A {

    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);

        final Option<String> option1 = new Option<String>("Hello, world!");

        if (option1.isInstanceOf(String.class))
            LoggingUtility.info("Option 1 has object of String class");

        if (option1.isInstanceOf(Integer.class))
            LoggingUtility.info("Option 1 has object of Integer class");


        final Option<A> option2 = new Option<A>(new B());

        if (option2.isInstanceOf(A.class))
            LoggingUtility.info("Option 2 has object of A");

        if (option2.isInstanceOf(B.class))
            LoggingUtility.info("Option 2 has object of B");


        final B test1 = option2.asInstanceOf(B.class);
        LoggingUtility.info("Object of class B was obtained from option2: " + test1.toString());

        try {
            LoggingUtility.info(option2.asInstanceOf(String.class));
        } catch (final ClassCastException ignored) {
            LoggingUtility.info("Option 2 can't be represented as string");
        }
    }
}
