package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

import static com.tombrus.vthundred.terminal.CharProps.*;

public class Clear {
    private static Screen screen = new ScreenImpl(new UnixTerminal());

    public static void main (String[] args) {
        for (int i = 0; i<5; i++) {
            drawText();
            U.sleep(300);
            screen.getNewScreenWriter().fill();
            U.sleep(300);
        }
        drawText();

        U.waitForQ(screen);
    }

    private static void drawText () {
        screen.run(new Runnable() {
            @Override
            public void run () {
                final ScreenWriter w = screen.getNewScreenWriter();
                w.write(10, 1, FG_DEFAULT, BG_DEFAULT, "Hello World");
                w.write(11, 2, FG_BLACK, BG_WHITE, "Hello World"          );
                w.write(12, 3, FG_WHITE, BG_BLACK, "Hello World"          );
                w.write(13, 4, FG_BLACK, BG_WHITE, BOLD, "Hello World"    );
                w.write(14, 5, FG_WHITE, BG_BLACK, BOLD, "Hello World"    );
                w.write(15, 6, FG_DEFAULT, BG_DEFAULT, BOLD, "Hello World");
                w.write(16, 7, FG_DEFAULT, BG_DEFAULT, BOLD, "Hello World");

                w.write(10, 10, FG_BLUE, BG_DEFAULT, "Hello World"        );
                w.write(11, 11, FG_BLUE, BG_WHITE, "Hello World"          );
                w.write(12, 12, FG_BLUE, BG_BLACK, "Hello World"          );
                w.write(13, 13, FG_BLUE, BG_MAGENTA, "Hello World"        );
                w.write(14, 14, FG_GREEN, BG_DEFAULT, "Hello World"       );
                w.write(15, 15, FG_GREEN, BG_WHITE, "Hello World"         );
                w.write(16, 16, FG_GREEN, BG_BLACK, "Hello World"         );
                w.write(17, 17, FG_GREEN, BG_MAGENTA, "Hello World"       );
                w.write(10, 20, FG_BLUE, BG_DEFAULT, BOLD, "Hello World"  );
                w.write(11, 21, FG_BLUE, BG_WHITE, BOLD, "Hello World"    );
                w.write(12, 22, FG_BLUE, BG_BLACK, BOLD, "Hello World"    );
                w.write(13, 23, FG_BLUE, BG_MAGENTA, BOLD, "Hello World"  );
                w.write(14, 24, FG_GREEN, BG_DEFAULT, BOLD, "Hello World" );
                w.write(15, 25, FG_GREEN, BG_WHITE, BOLD, "Hello World"   );
                w.write(16, 26, FG_GREEN, BG_BLACK, BOLD, "Hello World"   );
                w.write(17, 27, FG_CYAN, BG_BLUE, BOLD, "Hello World"     );
            }
        });
    }
}
