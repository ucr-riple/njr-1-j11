package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

import java.util.*;

public class Border {
    private static Screen screen = new ScreenImpl(new UnixTerminal());

    public static void main (String[] args) {
        final Random r    = new Random();
        final int    dx   = 30;
        final int    dy   = 8;
        final int    maxX = screen.getScreenSize().getX() -dx;
        final int    maxY = screen.getScreenSize().getY() -dy;
        for (int i = 0; i<100; i++) {
            Color c;
            do {
                c =  U.getRandomColor();
            } while (c==Color.BLACK || c==Color.SPARE);
            final ScreenWriter w = screen.getNewScreenWriter(r.nextInt(maxX), r.nextInt(maxY), 1+r.nextInt(dx), 1+r.nextInt(dy));
            w.border(c);
            w.fill(c);
            w.write(c.getBgChanger(),c.name());
            U.sleep(20);
        }
        U.waitForQ(screen);
    }
}
