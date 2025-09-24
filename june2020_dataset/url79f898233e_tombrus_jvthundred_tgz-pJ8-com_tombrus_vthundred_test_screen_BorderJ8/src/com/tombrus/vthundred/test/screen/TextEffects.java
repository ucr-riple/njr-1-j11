package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class TextEffects extends Thread {
    private static Screen screen = new ScreenImpl(new UnixTerminal());
    private static boolean stop;

    public static void main (String[] args) {

        new RollBy(U.SOME_TEXT, 10, 10, 40).start();
        new RollUp("Hello world!", 10, 12).start();

        U.waitForQ(screen);
        stop = true;
        U.sleep(200);
    }

    public static class RollBy extends Thread {
        private final ScreenWriter w   = screen.getNewScreenWriter();
        private final String       t;
        private final int          x;
        private final int          y;
        private final int          len;

        public RollBy (String t, int x, int y, int len) {
            this.t   = t;
            this.x   = x;
            this.y   = y;
            this.len = len;
            setDaemon(true);
        }

        @Override
        public void run () {
            for (int offset = 0; !stop && offset<t.length()-len; offset++) {
                w.write(x, y, t.substring(offset, offset +len));
                U.sleep(100);
            }
        }
    }

    public static class RollUp extends Thread {
        private final ScreenWriter w = screen.getNewScreenWriter();
        private final String       t;
        private final int          x;
        private final int          y;

        public RollUp (String t, int x, int y) {
            this.t = t;
            this.x = x;
            this.y = y;
            setDaemon(true);
        }

        @Override
        public void run () {
            StringBuilder b = new StringBuilder(t);
            for (int i = 0; !stop && i<t.length(); i++) {
                w.write(x, y, b.toString());
                U.sleep(133);
                b.replace(i,i+1," ");
            }
        }
    }
}