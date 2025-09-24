package com.tombrus.vthundred.test.terminal;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class Input {
    private static int currentRow;

    public static void main (String[] args) throws InterruptedException {
        final Terminal t = new UnixTerminal();

        t.addKeyHandler(new KeyHandler() {
            @Override
            public void handleKey (Key key) {
                U.setStopIfQ(key);
                if (currentRow ==0) {
                    t.clearScreen();
                }
                t.write(0, currentRow++, key.toString());
                if (currentRow >=t.getTerminalSize().getY()) {
                    currentRow = 0;
                }
            }
        });
        U.waitForStop(t);
    }
}
