package com.tombrus.vthundred.test.terminal;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;
import com.tombrus.vthundred.util.*;

public class Resize implements ResizeHandler {
    private static Terminal t;

    public static void main (String[] args) throws InterruptedException {
        try {
            t = new UnixTerminal();

            final Resize listener = new Resize();
            t.addResizeHandler(listener);
            listener.handleResize(t.getTerminalSize());

            U.waitForQ(t);
        } catch (Throwable e) {
            DB.error(e);
        }
    }

    public void handleResize (final TerminalXY newSize) {
        t.write(20, 1, "                                      ",
                20, 1, newSize.getX() +"x" +newSize.getY(),
                 0, 0                                           );
    }
}
