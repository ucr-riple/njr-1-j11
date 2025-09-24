package com.tombrus.vthundred.test.screen;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class Tabs {
    public static void main (String[] args) throws InterruptedException {
        final Screen screen = new ScreenImpl(new UnixTerminal());
        screen.setBackgroundColor(Color.YELLOW);
        screen.run(new Runnable() {
            @Override
            public void run () {
                ScreenWriter w = screen.getNewScreenWriter();
                w.write(0, 0, "Trying out some tabs!");
                w.write(10, 3, "ONE_SPACE");
                w.write(10, 6, "FOUR_SPACES");
                w.write(10, 9, "EIGHT_SPACES");
                w.write(10, 12, "ALIGN_4");
                w.write(10, 15, "ALIGN_8");

                w.setTabBehaviour(TabBehaviour.ONE_SPACE);
                w.write(24, 3, "|\t|\t|\t|\t|");
                w.write(24, 4, "|.|.|.|.|");
                w.setTabBehaviour(TabBehaviour.FOUR_SPACES);
                w.write(24, 6, "|\t|\t|\t|\t|");
                w.write(24, 7, "|....|....|....|....|");
                w.setTabBehaviour(TabBehaviour.EIGHT_SPACES);
                w.write(24, 9, "|\t|\t|\t|\t|");
                w.write(24, 10, "|........|........|........|........|");
                w.setTabBehaviour(TabBehaviour.ALIGN_4);
                w.write(24, 12, "|\t|x\t|xx\t|\t|");
                w.write(24, 13, "|...|x..|xx.|...|");
                w.setTabBehaviour(TabBehaviour.ALIGN_8);
                w.write(24, 15, "|\t|x\t|xx\t|xxx\t|");
                w.write(24, 16, "|.......|x......|xx.....|xxx....|");
            }
        });
        U.sleep(1000);
        screen.setBackgroundColor(Color.GREEN);
        U.waitForQ(screen);
    }
}
