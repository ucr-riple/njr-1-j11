package com.tombrus.vthundred.test.terminal;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;
import com.tombrus.vthundred.util.*;

import java.io.*;

public class Area {
    public static void main (String[] args) throws InterruptedException, IOException {
        try {
            final UnixTerminal t = new UnixTerminal();
            final TerminalXY terminalSize = t.getTerminalSize();

            final int blockWidth = terminalSize.getX()/4-10;

            StringBuilder bh = new StringBuilder();
            StringBuilder bf = new StringBuilder();
            for (int i = 0; i<blockWidth; i++) {
                bh.append('#');
                bf.append('\u253c');
            }
            final String lineHash = bh.toString();
            final String lineFace = bf.toString();

            t.write(3, 0, "size = "+terminalSize);

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        t.write(5, y, lineHash);
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(3, 1, "### t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        t.write(5, y, lineHash);
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(40, 1, "### t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        for (int x = blockWidth+5; x<2*blockWidth+5; x++) {
                            t.write(x, y, ".");
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(3, 2, "... t = "+(t1-t0));
                }
            });
            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        for (int x = blockWidth+5; x<2*blockWidth+5; x++) {
                            t.write(x, y, ".");
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(40, 2, "... t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        t.write(2*blockWidth+5, y, lineFace);
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(3, 3, "\u253c\u253c\u253c t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        t.write(2*blockWidth+5, y, lineFace);
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(40, 3, "\u253c\u253c\u253c t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        for (int x = 3*blockWidth+5; x<4*blockWidth+5; x++) {
                            t.write(x, y, "\u263b");
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(3, 4, "\u263b\u263b\u263b t = "+(t1-t0));
                }
            });

            t.run(new Runnable() {
                @Override
                public void run () {
                    long t0 = System.currentTimeMillis();
                    for (int y = 5; y<terminalSize.getY()-5; y++) {
                        for (int x = 3*blockWidth+5; x<4*blockWidth+5; x++) {
                            t.write(x, y, "\u263b");
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    t.write(40, 4, "\u263b\u263b\u263b t = "+(t1-t0));
                }
            });

            U.waitForQ(t);
        } catch (Throwable t) {
            DB.error(t);
        }
    }
}
