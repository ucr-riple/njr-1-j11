package com.tombrus.vthundred.terminal.types;

public class AnsiGraphics {
    //unicode characters:
    public static final char DEGREES                      = 0x00B0;
    public static final char ULCORNER                     = 0x250C;
    public static final char URCORNER                     = 0x2510;
    public static final char LLCORNER                     = 0x2514;
    public static final char LRCORNER                     = 0x2518;
    public static final char HLINE                        = 0x2500;
    public static final char VLINE                        = 0x2502;

    public static final char DIAMOND                      = 0x2666;
    public static final char DOT                          = 0x2022;

    public static final char ARROW_UP                     = 0x2191;
    public static final char ARROW_DOWN                   = 0x2193;
    public static final char ARROW_RIGHT                  = 0x2192;
    public static final char ARROW_LEFT                   = 0x2190;
    public static final char BLOCK_SOLID                  = 0x2588;
    public static final char BLOCK_DENSE                  = 0x2593;
    public static final char BLOCK_MIDDLE                 = 0x2592;
    public static final char BLOCK_SPARSE                 = 0x2591;

    public static final char SINGLE_LINE_HORIZONTAL       = HLINE;
    public static final char DOUBLE_LINE_HORIZONTAL       = 0x2550;
    public static final char SINGLE_LINE_VERTICAL         = VLINE;
    public static final char DOUBLE_LINE_VERTICAL         = 0x2551;

    public static final char SINGLE_LINE_UP_LEFT_CORNER   = ULCORNER;
    public static final char DOUBLE_LINE_UP_LEFT_CORNER   = 0x2554;
    public static final char SINGLE_LINE_UP_RIGHT_CORNER  = URCORNER;
    public static final char DOUBLE_LINE_UP_RIGHT_CORNER  = 0x2557;

    public static final char SINGLE_LINE_LOW_LEFT_CORNER  = LLCORNER;
    public static final char DOUBLE_LINE_LOW_LEFT_CORNER  = 0x255A;
    public static final char SINGLE_LINE_LOW_RIGHT_CORNER = LRCORNER;
    public static final char DOUBLE_LINE_LOW_RIGHT_CORNER = 0x255D;

    public static final char SINGLE_LINE_CROSS            = 0x253C;
    public static final char DOUBLE_LINE_CROSS            = 0x256C;

    public static final char SINGLE_LINE_T_UP             = 0x2534;
    public static final char SINGLE_LINE_T_DOWN           = 0x252C;
    public static final char SINGLE_LINE_T_RIGHT          = 0x251c;
    public static final char SINGLE_LINE_T_LEFT           = 0x2524;

    public static final char SINGLE_LINE_T_DOUBLE_UP      = 0x256B;
    public static final char SINGLE_LINE_T_DOUBLE_DOWN    = 0x2565;
    public static final char SINGLE_LINE_T_DOUBLE_RIGHT   = 0x255E;
    public static final char SINGLE_LINE_T_DOUBLE_LEFT    = 0x2561;

    public static final char DOUBLE_LINE_T_UP             = 0x2569;
    public static final char DOUBLE_LINE_T_DOWN           = 0x2566;
    public static final char DOUBLE_LINE_T_RIGHT          = 0x2560;
    public static final char DOUBLE_LINE_T_LEFT           = 0x2563;

    public static final char DOUBLE_LINE_T_SINGLE_UP      = 0x2567;
    public static final char DOUBLE_LINE_T_SINGLE_DOWN    = 0x2564;
    public static final char DOUBLE_LINE_T_SINGLE_RIGHT   = 0x255F;
    public static final char DOUBLE_LINE_T_SINGLE_LEFT    = 0x2562;

    // ansi graph codes
    public static final char GRAPH_DIAMOND        = '`';
    public static final char GRAPH_GRAY           = 'a';
    public static final char GRAPH_HT             = 'b';
    public static final char GRAPH_FF             = 'c';
    public static final char GRAPH_CR             = 'd';
    public static final char GRAPH_LF             = 'e';
    public static final char GRAPH_DEGREES        = 'f';
    public static final char GRAPH_PLUS_MINUS     = 'g';
    public static final char GRAPH_NL             = 'h';
    public static final char GRAPH_VT             = 'i';
    public static final char GRAPH_LOW_RIGHT      = 'j';
    public static final char GRAPH_TOP_RIGHT      = 'k';
    public static final char GRAPH_TOP_LEFT       = 'l';
    public static final char GRAPH_LOW_LEFT       = 'm';
    public static final char GRAPH_CROSS          = 'n';
    public static final char GRAPH_HOR_LINE_TOP   = 'o';
    public static final char GRAPH_HOR_LINE_HIGH  = 'p';
    public static final char GRAPH_HOR_LINE       = 'q';
    public static final char GRAPH_HOR_LINE_LOW   = 'r';
    public static final char GRAPH_HOR_LINE_BOTOM = 's';
    public static final char GRAPH_RIGHT_T        = 't';
    public static final char GRAPH_LEFT_T         = 'u';
    public static final char GRAPH_TOP_T          = 'v';
    public static final char GRAPH_BOTTOM_T       = 'w';
    public static final char GRAPH_VER_LINE       = 'x';
    public static final char GRAPH_LESS_EQUAL     = 'y';
    public static final char GRAPH_GREATER_EQUAL  = 'z';
    public static final char GRAPH_PI             = '{';
    public static final char GRAPH_NOT_EXUAL      = '|';
    public static final char GRAPH_POUND          = '}';
    public static final char GRAPH_CENTER_DOT     = '~';

    public static char toGraph (int c) {
        int lo = c       &0xff;
        int hi = (c >>8) &0xff;
        return GRAPHS[hi] ==null ? 0 : GRAPHS[hi][lo];
    }

    private static final char[][] GRAPHS = new char[256][];

    static {                                                //see http://paulbourke.net/dataformats/ascii/
        enter(ARROW_DOWN, 'V'                              );
        enter(ARROW_LEFT, '<'                              );
        enter(ARROW_RIGHT, '>'                             );
        enter(ARROW_UP, '^'                                );
        enter(BLOCK_DENSE, GRAPH_GRAY                      );
        enter(BLOCK_MIDDLE, GRAPH_GRAY                     );
        enter(BLOCK_SOLID, GRAPH_GRAY                      );
        enter(BLOCK_SPARSE, GRAPH_GRAY                     );
        enter(DIAMOND, GRAPH_DIAMOND                       );
        enter(DOT, GRAPH_CENTER_DOT                        );
        enter(DOUBLE_LINE_CROSS, GRAPH_CROSS               );
        enter(DOUBLE_LINE_HORIZONTAL, GRAPH_HOR_LINE       );
        enter(DOUBLE_LINE_LOW_LEFT_CORNER, GRAPH_LOW_LEFT  );
        enter(DOUBLE_LINE_LOW_RIGHT_CORNER, GRAPH_LOW_RIGHT);
        enter(DOUBLE_LINE_T_DOWN, GRAPH_BOTTOM_T           );
        enter(DOUBLE_LINE_T_LEFT, GRAPH_LEFT_T             );
        enter(DOUBLE_LINE_T_RIGHT, GRAPH_RIGHT_T           );
        enter(DOUBLE_LINE_T_SINGLE_DOWN, GRAPH_BOTTOM_T    );
        enter(DOUBLE_LINE_T_SINGLE_LEFT, GRAPH_LEFT_T      );
        enter(DOUBLE_LINE_T_SINGLE_RIGHT, GRAPH_RIGHT_T    );
        enter(DOUBLE_LINE_T_SINGLE_UP, GRAPH_TOP_T         );
        enter(DOUBLE_LINE_T_UP, GRAPH_TOP_T                );
        enter(DOUBLE_LINE_UP_LEFT_CORNER, GRAPH_TOP_LEFT   );
        enter(DOUBLE_LINE_UP_RIGHT_CORNER, GRAPH_TOP_RIGHT );
        enter(DOUBLE_LINE_VERTICAL, GRAPH_VER_LINE         );
        enter(SINGLE_LINE_CROSS, GRAPH_CROSS               );
        enter(SINGLE_LINE_HORIZONTAL, GRAPH_HOR_LINE       );
        enter(SINGLE_LINE_LOW_LEFT_CORNER, GRAPH_LOW_LEFT  );
        enter(SINGLE_LINE_LOW_RIGHT_CORNER, GRAPH_LOW_RIGHT);
        enter(SINGLE_LINE_T_DOUBLE_DOWN, GRAPH_BOTTOM_T    );
        enter(SINGLE_LINE_T_DOUBLE_LEFT, GRAPH_LEFT_T      );
        enter(SINGLE_LINE_T_DOUBLE_RIGHT, GRAPH_RIGHT_T    );
        enter(SINGLE_LINE_T_DOUBLE_UP, GRAPH_TOP_T         );
        enter(SINGLE_LINE_T_DOWN, GRAPH_BOTTOM_T           );
        enter(SINGLE_LINE_T_LEFT, GRAPH_LEFT_T             );
        enter(SINGLE_LINE_T_RIGHT, GRAPH_RIGHT_T           );
        enter(SINGLE_LINE_T_UP, GRAPH_TOP_T                );
        enter(SINGLE_LINE_UP_LEFT_CORNER, GRAPH_TOP_LEFT   );
        enter(SINGLE_LINE_UP_RIGHT_CORNER, GRAPH_TOP_RIGHT );
        enter(SINGLE_LINE_VERTICAL, GRAPH_VER_LINE         );
//        enter(, GRAPH_HT                                   );
//        enter(, GRAPH_FF                                   );
//        enter(, GRAPH_CR                                   );
//        enter(, GRAPH_LF                                   );
        enter(DEGREES, GRAPH_DEGREES);
//        enter(, GRAPH_PLUS_MINUS                           );
//        enter(, GRAPH_NL                                   );
//        enter(, GRAPH_VT                                   );
//        enter(, GRAPH_HOR_LINE_TOP                         );
//        enter(, GRAPH_HOR_LINE_HIGH                        );
//        enter(, GRAPH_HOR_LINE_LOW                         );
//        enter(, GRAPH_HOR_LINE_BOTOM                       );
//        enter(, GRAPH_LESS_EQUAL                           );
//        enter(, GRAPH_GREATER_EQUAL                        );
//        enter(, GRAPH_PI                                   );
//        enter(, GRAPH_NOT_EXUAL                            );
//        enter(, GRAPH_POUND                                );
    }

    private static void enter (char g, char c) {
        int lo = g       &0xff;
        int hi = (g >>8) &0xff;
        if (GRAPHS[hi] ==null) {
            GRAPHS[hi] = new char[256];
        }
        if (GRAPHS[hi][lo] !=0 && GRAPHS[hi][lo] !=c) {
            throw new IllegalArgumentException(String.format("double define of graph character: 0x%04x => %c and %c", (int) g, GRAPHS[hi][lo], c));
        }
        GRAPHS[hi][lo] = c;
    }
}
