package com.tombrus.vthundred.terminal;

import static com.tombrus.vthundred.terminal.CharProps.Color.*;

@SuppressWarnings({"UnusedDeclaration"})
public final class CharProps {
    public static final CharProps        DEFAULT    = new                    CharProps();
    public static final CharPropsChanger FG_DEFAULT = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==Color.DEFAULT ? o : new CharProps(Color.DEFAULT, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_RED = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==RED ? o : new CharProps(RED, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_GREEN = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==GREEN ? o : new CharProps(GREEN, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_YELLOW = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==YELLOW ? o : new CharProps(YELLOW, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_BLUE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==BLUE ? o : new CharProps(BLUE, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_MAGENTA = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==MAGENTA ? o : new CharProps(MAGENTA, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_CYAN = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==CYAN ? o : new CharProps(CYAN, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_WHITE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==WHITE ? o : new CharProps(WHITE, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger FG_BLACK = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.fg ==BLACK ? o : new CharProps(BLACK, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_DEFAULT = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==Color.DEFAULT ? o : new CharProps(o.fg, Color.DEFAULT, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_RED = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==RED ? o : new CharProps(o.fg, RED, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_GREEN = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==GREEN ? o : new CharProps(o.fg, GREEN, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_YELLOW = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==YELLOW ? o : new CharProps(o.fg, YELLOW, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_BLUE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==BLUE ? o : new CharProps(o.fg, BLUE, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_MAGENTA = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==MAGENTA ? o : new CharProps(o.fg, MAGENTA, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_CYAN = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==CYAN ? o : new CharProps(o.fg, CYAN, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_WHITE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==WHITE ? o : new CharProps(o.fg, WHITE, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BG_BLACK = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bg ==BLACK ? o : new CharProps(o.fg, BLACK, o.bold, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BOLD = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.bold ? o : new CharProps(o.fg, o.bg, true, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BOLD_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.bold ? o : new CharProps(o.fg, o.bg, false, o.dim, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger DIM = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.dim ? o : new CharProps(o.fg, o.bg, o.bold, true, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger DIM_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.dim ? o : new CharProps(o.fg, o.bg, o.bold, false, o.reverse, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger REVERSE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.reverse ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, true, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger REVERSE_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.reverse ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, false, o.underline, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger UNDERLINE = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.underline ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, true, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger UNDERLINE_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.underline ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, false, o.blink, o.hidden);
        }
    };

    public static final CharPropsChanger BLINK = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.blink ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, o.underline, true, o.hidden);
        }
    };

    public static final CharPropsChanger BLINK_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.blink ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, o.underline, false, o.hidden);
        }
    };

    public static final CharPropsChanger HIDDEN = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return o.hidden ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, true);
        }
    };

    public static final CharPropsChanger HIDDEN_OFF = new CharPropsChanger() {
        @Override
        public CharProps change (CharProps o) {
            return !o.hidden ? o : new CharProps(o.fg, o.bg, o.bold, o.dim, o.reverse, o.underline, o.blink, false);
        }
    };

    private static final CharPropsChanger[] CHANGER_FOR_FG = new CharPropsChanger[]{
            FG_BLACK,
            FG_RED,
            FG_GREEN,
            FG_YELLOW,
            FG_BLUE,
            FG_MAGENTA,
            FG_CYAN,
            FG_WHITE,
            FG_DEFAULT,
            FG_DEFAULT,
                       };
    private static final CharPropsChanger[] CHANGER_FOR_BG = new CharPropsChanger[]{
            BG_BLACK,
            BG_RED,
            BG_GREEN,
            BG_YELLOW,
            BG_BLUE,
            BG_MAGENTA,
            BG_CYAN,
            BG_WHITE,
            BG_DEFAULT,
            BG_DEFAULT,
                       };

    public static enum Color {
                                    BLACK(0),
                                    RED(1),
                                    GREEN(2),
                                    YELLOW(3),
                                    BLUE(4),
                                    MAGENTA(5),
                                    CYAN(6),
                                    WHITE(7),
                                    SPARE(8),
                                    DEFAULT(9);

        public  static final int    MAX_COLOR_CODE = 9;

        private              String codeAsString;
        private              int    code;

        private Color (int code) {
            this.code         = code;
            this.codeAsString = Integer.toString(code);
        }

        public int getCode () {
            return code;
        }

        public String getCodeAsString () {
            return codeAsString;
        }

        public CharPropsChanger getFgChanger () {
            return CHANGER_FOR_FG[code];
        }

        public CharPropsChanger getBgChanger () {
            return CHANGER_FOR_BG[code];
        }
    }

    public final Color   fg;        // 3x
    public final Color   bg;        // 3x

    public final boolean bold;      // 1
    public final boolean dim;       // 2
    public final boolean underline; // 4
    public final boolean blink;     // 5
    public final boolean reverse;   // 6
    public final boolean hidden;    // 7

    public CharProps (Color fg, Color bg, boolean bold, boolean dim, boolean reverse, boolean underline, boolean blink, boolean hidden) {
        this.fg        = fg;
        this.bg        = bg;
        this.bold      = bold;
        this.dim       = dim;
        this.reverse   = reverse;
        this.underline = underline;
        this.blink     = blink;
        this.hidden    = hidden;
    }

    public CharProps () {
        this.fg        = Color.DEFAULT;
        this.bg        = Color.DEFAULT;
        this.bold      = false;
        this.dim       = false;
        this.reverse   = false;
        this.underline = false;
        this.blink     = false;
        this.hidden    = false;
    }

    @Override
    public boolean equals (Object o) {
        if (this ==o) {
            return true;
        }
        if (o ==null || getClass() !=o.getClass()) {
            return false;
        }

        final CharProps charProps = (CharProps) o;

        if (blink !=charProps.blink) {
            return false;
        }
        if (bold !=charProps.bold) {
            return false;
        }
        if (dim !=charProps.dim) {
            return false;
        }
        if (hidden !=charProps.hidden) {
            return false;
        }
        if (reverse !=charProps.reverse) {
            return false;
        }
        if (underline !=charProps.underline) {
            return false;
        }
        if (bg !=charProps.bg) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (fg !=charProps.fg) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode () {
        int result = fg !=null ? fg.hashCode() : 0;
        result = 31 *result +(bg!=null  ? bg.hashCode() : 0);
        result = 31 *result +(bold      ? 1             : 0);
        result = 31 *result +(dim       ? 1             : 0);
        result = 31 *result +(underline ? 1             : 0);
        result = 31 *result +(blink     ? 1             : 0);
        result = 31 *result +(reverse   ? 1             : 0);
        result = 31 *result +(hidden    ? 1             : 0);
        return result;
    }
}
