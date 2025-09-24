package com.tombrus.vthundred.terminal.types;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.terminal.input.Key.*;

import java.util.*;

public class AnsiType implements TerminalType {
    public  static final char               ESC            = 0x1b;

    public  static final List<InputPattern> INPUT_PATTERNS = Arrays.asList(
            new SimpleInputPattern(Kind.ArrowUp, ESC, '[', 'A'      ),
            new SimpleInputPattern(Kind.ArrowDown, ESC, '[', 'B'    ),
            new SimpleInputPattern(Kind.ArrowRight, ESC, '[', 'C'   ),
            new SimpleInputPattern(Kind.ArrowLeft, ESC, '[', 'D'    ),
            new SimpleInputPattern(Kind.Tab, '\t'                   ),
            new SimpleInputPattern(Kind.Enter, '\n'                 ),
            new SimpleInputPattern(Kind.ReverseTab, ESC, '[', 'Z'   ),
            new SimpleInputPattern(Kind.Backspace, (char) 0x7f      ),
            new SimpleInputPattern(Kind.Insert, ESC, '[', '2', '~'  ),
            new SimpleInputPattern(Kind.Delete, ESC, '[', '3', '~'  ),
            new SimpleInputPattern(Kind.Home, ESC, '[', 'H'         ),
            new SimpleInputPattern(Kind.End, ESC, '[', 'F'          ),
            new SimpleInputPattern(Kind.PageUp, ESC, '[', '5', '~'  ),
            new SimpleInputPattern(Kind.PageDown, ESC, '[', '6', '~'),
            new SimpleInputPattern(Kind.F1, ESC, 'O', 'P'           ),
            new SimpleInputPattern(Kind.F2, ESC, 'O', 'Q'           ),
            new SimpleInputPattern(Kind.F3, ESC, 'O', 'R'           ),
            new SimpleInputPattern(Kind.F4, ESC, 'O', 'S'           ),
            new SimpleInputPattern(Kind.F5, ESC, '[', '1', '5', '~' ),
            new SimpleInputPattern(Kind.F6, ESC, '[', '1', '7', '~' ),
            new SimpleInputPattern(Kind.F7, ESC, '[', '1', '8', '~' ),
            new SimpleInputPattern(Kind.F8, ESC, '[', '1', '9', '~' ),
            new SimpleInputPattern(Kind.F9, ESC, '[', '2', '0', '~' ),
            new SimpleInputPattern(Kind.F10, ESC, '[', '2', '1', '~'),
            new SimpleInputPattern(Kind.F11, ESC, '[', '2', '3', '~'),
            new SimpleInputPattern(Kind.F12, ESC, '[', '2', '4', '~'),
            new SimpleInputPattern(Kind.F13, ESC, '[', '2', '5', '~'),
            new SimpleInputPattern(Kind.F14, ESC, '[', '2', '6', '~'),
            new SimpleInputPattern(Kind.F15, ESC, '[', '2', '8', '~'),
            new SimpleInputPattern(Kind.F16, ESC, '[', '2', '9', '~'),
            new SimpleInputPattern(Kind.F17, ESC, '[', '3', '1', '~'),
            new AnsiCursorPositionPattern()
                                                                      );

    private static final char[]             CLEAR          = new char[]  {ESC, '[', '2', 'J'                                             };
    private static final char[]             REPORT_POS     = new char[]  {ESC, '[', '6', 'n'                                             };
    private static final char[]             PRIVATE_ON     = new char[]  {ESC, '[', '?', '1', '0', '4', '9', 'h'                         };
    private static final char[]             PRIVATE_OFF    = new char[]  {ESC, '[', '?', '1', '0', '4', '9', 'l'                         };
    private static final char[]             CURSOR_ON      = new char[]  {ESC, '[', '?', '2', '5', 'h'                                   };
    private static final char[]             CURSOR_OFF     = new char[]  {ESC, '[', '?', '2', '5', 'l'                                   };
    private static final char[]             CURSOR_UP      = new char[]  {ESC, '[', 'A'                                                  };
    private static final char[]             CURSOR_DOWN    = new char[]  {ESC, '[', 'B'                                                  };
    private static final char[]             CURSOR_RIGHT   = new char[]  {ESC, '[', 'C'                                                  };
    private static final char[]             CURSOR_LEFT    = new char[]  {ESC, '[', 'D'                                                  };
    private static final char[]             GRAPH_ON       = new char[]  {ESC, '(', '0'                                                  };
    private static final char[]             GRAPH_OFF      = new char[]  {ESC, '(', 'B'                                                  };
    private static final char[]             MOVE_LEFT_1    = new char[]  {0x08                                                           };
    private static final char[]             MOVE_LEFT_2    = new char[]  {0x08, 0x08                                                     };
    private static final char[]             MOVE_LEFT_3    = new char[]  {0x08, 0x08, 0x08                                               };
    private static final char[]             MOVE_LEFT_4    = new char[]  {0x08, 0x08, 0x08, 0x08                                         };
    private static final char[]             MOVE_LEFT_5    = new char[]  {0x08, 0x08, 0x08, 0x08, 0x08                                   };
    private static final char[][]           MOVE_LEFT      = new char[][]{MOVE_LEFT_1, MOVE_LEFT_2, MOVE_LEFT_3, MOVE_LEFT_4, MOVE_LEFT_5};

    @Override
    public List<InputPattern> getInputPatterns () {
        return INPUT_PATTERNS;
    }

    @Override
    public char[] getPropChangeSeq (CharProps from, CharProps to) {
        StringBuilder b   = new StringBuilder().append(ESC).append('[');
        String        sep = "";
        if (!from.bg.equals(to.bg)) {
            b./*append(sep).*/append("4").append(to.bg.getCodeAsString());
            sep = ";";
        }
        if (!from.fg.equals(to.fg)) {
            b.append(sep).append("3").append(to.fg.getCodeAsString());
            sep = ";";
        }
        if (from.bold !=to.bold) {
            b.append(sep).append(to.bold ? "1" : "21");
            sep = ";";
        }
        if (from.dim !=to.dim) {
            b.append(sep).append(to.dim ? "2" : "22");
            sep = ";";
        }
        if (from.underline !=to.underline) {
            b.append(sep).append(to.underline ? "4" : "24");
            sep = ";";
        }
        if (from.blink !=to.blink) {
            b.append(sep).append(to.blink ? "5" : "25");
            sep = ";";
        }
        if (from.reverse !=to.reverse) {
            b.append(sep).append(to.reverse ? "7" : "27");
            sep = ";";
        }
        if (from.hidden !=to.hidden) {
            b.append(sep).append(to.hidden ? "8" : "28");
            sep = ";";
        }
        b.append('m');
        return sep.isEmpty() ? new char[]{} : b.toString().toCharArray();
    }

    @Override
    public char[] getMoveLeftSeq (int n) {
        return MOVE_LEFT[n];
    }

    @Override
    public char[] getClearSeq () {
        return CLEAR;
    }

    @Override
    public char[] getReportPosSeq () {
        return REPORT_POS;
    }

    @Override
    public char[] getPrivateOnSeq () {
        return PRIVATE_ON;
    }

    @Override
    public char[] getPrivateOffSeq () {
        return PRIVATE_OFF;
    }

    @Override
    public char[] getCursorOnSeq () {
        return CURSOR_ON;
    }

    @Override
    public char[] getCursorOffSeq () {
        return CURSOR_OFF;
    }

    @Override
    public char[] getGraphOnSeq () {
        return GRAPH_ON;
    }

    @Override
    public char[] getGraphOffSeq () {
        return GRAPH_OFF;
    }

    @Override
    public char toGraph (char c) {
        return AnsiGraphics.toGraph(c);
    }

    public char[] getMoveCursorSeq (int xPre, int yPre, int x, int y) {
        if (yPre ==y && x <xPre && xPre -5 <x) {
            return getMoveLeftSeq(xPre -x);
        }else if (xPre ==x) {
            if (y <yPre) {
                //up
                return simpleMove('A', yPre -y);
            }else {
                //down
                return simpleMove('B', y -yPre);
            }
        }else if (yPre ==y) {
            if (xPre <x) {
                //right
                return simpleMove('C', x -xPre);
            }else {
                //left
                return simpleMove('D', xPre -x);
            }
        }else {
            return (""+ESC+'['+(y+1)+';'+(x+1)+'H').toCharArray();
        }
    }

    private char[] simpleMove (char c, int n) {
        if (n ==1) {
            return new char[]{ESC, '[', c};
        }else if (n <=9) {
            return new char[]{ESC, '[', (char) ('0' +n), c};
        }else if (n <=99) {
            return new char[]{ESC, '[', (char) ('0' +n/10), (char) ('0' +n%10), c};
        }else if (n <=999) {
            return new char[]{ESC, '[', (char) ('0' +n/100), (char) ('0' +(n%100)/10), (char) ('0' +n%10), c};
        }else if (n <=9999) {
            return new char[]{ESC, '[', (char) ('0' +n/1000), (char) ('0' +(n%1000)/100), (char) ('0' +(n%100)/10), (char) ('0' +n%10), c};
        }else {
            throw new IllegalArgumentException("number out of range: "+n);
        }
    }
}
