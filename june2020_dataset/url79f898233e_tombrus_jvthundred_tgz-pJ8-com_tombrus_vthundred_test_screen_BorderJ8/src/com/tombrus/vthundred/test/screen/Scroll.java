package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class Scroll {

    public static void main (String[] args) {
        Screen screen = new ScreenImpl(new UnixTerminal());

        screen.getNewScreenWriter(0, 0, 60, 20).fill('x');

        final ScreenWriter w = screen.getNewScreenWriter();
        for (int i = 0; i<20; i++) {
            w.write(10, i, "=line-"+i);
        }

        final ScreenWriter ww = screen.getNewScreenWriter(2, 2, 50, 10);
        ww.border(U.getRandomColor());
        ww.write("daar \nwas \nlaatst \neen \nmeisje \nloos \ndat \nwou \ngaan \nvaren \ndat \nwou \n........................................................"                  );
        U.sleep(500);

        U.waitForQ(screen);
    }
}
