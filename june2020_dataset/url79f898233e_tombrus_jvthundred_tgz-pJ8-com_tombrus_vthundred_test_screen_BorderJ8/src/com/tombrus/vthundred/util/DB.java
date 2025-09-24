package com.tombrus.vthundred.util;

import java.util.*;

public class DB {
    public static final boolean         TRACING = Boolean.getBoolean("TRACING");

    public static final List<Throwable> errors  = new ArrayList<Throwable>();
    public static final List<String>    traces  = new ArrayList<String>   ();

    static {
        final Thread hook = new Thread() {
            @Override
            public void run () {
                try {
                    sleep(500);
                    DB.report();
                } catch (Throwable e) {
                    System.err.println("ERROR DURING SHUTDOWN HOOK: " +e);
                    e.printStackTrace();
                }
            }
        };
        hook.setName("DB-hook");
        Runtime.getRuntime().addShutdownHook(hook);
    }

    public static synchronized void error (Throwable t) {
        errors.add(t);
        t("[EXCEPTION-" +errors.size() +"]");
    }

    public static synchronized void error (String s) {
        error(new Error(s));
    }

    public static void t (char[] seq) {
        if (TRACING) {
            t(new String(seq));
        }
    }

    public static synchronized void t (String s) {
        if (TRACING) {
            StringBuilder b = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c ==0x1b) {
                    b.append('E');
                } else if (c=='\n') {
                    b.append(c);
                } else if (c==' ') {
                    b.append('_');
                }else if (c <0x20) {
                    b.append(String.format("\\u%02x", (int) c));
                }else if (c <0x7F) {
                    b.append(c);
                }else if (c <0x100) {
                    b.append(String.format("\\u%02x", (int) c));
                }else {
                    b.append(String.format("\\u%04x", (int) c));
                }
            }
            traces.add(b.toString());
        }
    }

    public static synchronized boolean hasErrors () {
        return !errors.isEmpty();
    }

    public static synchronized boolean hasTraces () {
        return !traces.isEmpty();
    }

    public static synchronized void report () {
        if (hasErrors()) {
            for (Throwable t : errors) {
                System.err.println("===============================================");
                t.printStackTrace();
            }
            System.err.println("===============================================");
        }
        if (hasTraces()) {
            System.err.println("===============================================");
            for (String s : traces) {
                System.err.print(s.replace((char) 0x1b, '@'));
            }
            System.err.println(                                                 );
            System.err.println("===============================================");
        }
    }
}
