package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.types.*;

import java.util.*;

public class Twinkle extends Thread {
    private static final Screen   s           = new ScreenImpl(new UnixTerminal());
    private static final Random   r           = new Random();
    private static       boolean  stop;
    private static       Object[] colors      = new Object[]{
            CharProps.FG_BLUE,
            CharProps.FG_CYAN,
            CharProps.FG_DEFAULT,
            CharProps.FG_GREEN,
            CharProps.FG_MAGENTA,
            CharProps.FG_RED,
            CharProps.FG_WHITE,
            CharProps.FG_YELLOW,
                                 };
    private static       char[]   chars       = new char[]  {
            '.', '*', '+', '-'
                                 };
    private static final int      NUM_THREADS = 100;

    public static void main (String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 1; i<NUM_THREADS; i++) {
            threads[i] = new Twinkle();
            threads[i].start();
        }
        s.getNewScreenWriter().write(4,40,"wait for storm to settle...", Screen.SET_USER_CURSOR);
        Thread.sleep(5000);
        stop = true;
        for (int i = 1; i<NUM_THREADS; i++) {
            threads[i].join();
        }
        Thread.sleep(1000);
    }

    public Twinkle () {
        setDaemon(true);
    }

    @Override
    public void run () {
        try {
            final TerminalXY   terminalSize = s.getScreenSize     ();
            final int          x            = r.nextInt(terminalSize.getX());
            final int          y            = r.nextInt(terminalSize.getY());
            final Object       color        = colors[r.nextInt(colors.length)];
            final ScreenWriter w            = s.getNewScreenWriter();
            sleep(r.nextInt(600));
            long d = r.nextInt(500) +100;
            while (!stop) {
                final char c = chars[r.nextInt(chars.length)];
                w.write(x, y, color, c);
                sleep(d);
            }
            w.write(x, y, ' ');
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}