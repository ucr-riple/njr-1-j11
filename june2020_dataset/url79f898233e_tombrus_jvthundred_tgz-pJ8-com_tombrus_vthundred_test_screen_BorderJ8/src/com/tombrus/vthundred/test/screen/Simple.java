package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class Simple {
    private static Screen screen = new ScreenImpl(new UnixTerminal());

    public static void main (String[] args) {
        screen.getNewScreenWriter().write(44, 8, "hello hello  ");
        
        U.waitForQ(screen);
    }
}
