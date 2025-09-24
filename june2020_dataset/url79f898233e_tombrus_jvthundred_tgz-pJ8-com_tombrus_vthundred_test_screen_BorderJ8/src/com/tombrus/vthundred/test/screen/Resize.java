package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;
import com.tombrus.vthundred.util.*;

public class Resize {
    private static Screen screen = new ScreenImpl(new UnixTerminal());

    public static void main (String[] args) {
        final Resizer l = new Resizer();
        screen.addResizeListener(l);
        l.handleResize(screen.getScreenSize());

        U.waitForQ(screen);
    }

    private static class Resizer implements ResizeHandler {
        @Override
        public void handleResize (final TerminalXY s) {
            screen.run(new Runnable() {
                @Override
                public void run () {
                    DB.t("\n<Resizer!!>\n");
                    screen.getNewScreenWriter().fill();
                    final ScreenWriter w           = screen.getNewScreenWriter();

                    final String       labelW      = " " +s.getX() +" ";
                    final String       labelH      = " " +s.getY() +" ";

                    final int          yMeasureAtX = 3;
                    final int          xMeasureAtY = 3;

                    w.write(yMeasureAtX, 0, "^");
                    for (int i = 1; i<s.getY()-1; i++) {
                        w.write(yMeasureAtX, i, '|');
                    }
                    w.write(yMeasureAtX,                     s.getY() -1, "V"   );
                    w.write(yMeasureAtX -labelH.length() /2, s.getY() /2, labelH);

                    w.write(0, xMeasureAtY, "<"                                 );
                    for (int i = 1; i<s.getX()-1; i++) {
                        w.write(i, xMeasureAtY, '=');
                    }
                    w.write(s.getX()    -1, xMeasureAtY, ">"                    );
                    w.write(s.getX() /2 -labelW.length() /2, xMeasureAtY, labelW);
                }
            });
        }
    }
}
