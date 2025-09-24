package com.tombrus.vthundred.test.terminal;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;

public class Chars {
    static Terminal t = new UnixTerminal();

    public static void main (String[] args) throws InterruptedException {
        for (int fg = 0; fg<Color.MAX_COLOR_CODE; fg++) {
            for (int bg = 0; bg<Color.MAX_COLOR_CODE; bg++) {
                CharProps p = new CharProps(Color.values()[fg], Color.values()[bg], false, false, false, false, false, false);
                t.write(5+bg*20,5+fg,p,"###--- "+fg+" "+bg+" ---###");
            }
        }
        for (int v=0; v<8;v++) {
            for (int h=0;h<8;h++) {
                final boolean bold = (v&0x01)!=0;
                final boolean dim = (v&0x02)!=0;
                final boolean reverse = (v&0x04)!=0;
                final boolean underline = (h&0x01)!=0;
                final boolean blink = (h&0x02)!=0;
                final boolean hidden = (h&0x04)!=0;
                CharProps p = new CharProps(Color.DEFAULT, Color.DEFAULT, bold, dim, reverse, underline, blink, hidden);
                t.write(5+v*20, 15+h, p, "###- "+(bold?'B':'b')+(dim ? 'D' : 'd')+(reverse ? 'R' : 'r')+(underline ? 'U' : 'u')+(blink ? 'L' : 'l')+(hidden ? 'H' : 'h')+" -###");
            }
        }

        U.waitForQ(t);
    }
}
