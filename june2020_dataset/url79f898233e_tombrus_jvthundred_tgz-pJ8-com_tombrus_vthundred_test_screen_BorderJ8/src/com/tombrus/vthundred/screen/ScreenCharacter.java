package com.tombrus.vthundred.screen;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.CharProps.*;

public final class ScreenCharacter {
    private final char      character;
    private final CharProps props;

    public ScreenCharacter (char character) {
        this(character, CharProps.DEFAULT);
    }

    public ScreenCharacter (char character, CharProps props) {
        this.character = character;
        this.props = props;
    }

    public ScreenCharacter (char character, Color fg, Color bg) {
        this(character, fg.getFgChanger().change(bg.getBgChanger().change(CharProps.DEFAULT)));
    }

    public char getCharacter () {
        return character;
    }

    public CharProps getProps () {
        return props;
    }

    public boolean equals (char c, CharProps p) {
        return character==c && props.equals(p);
    }

    public static boolean safeEquals (ScreenCharacter a, char bc, CharProps bp) {
        return a!=null && a.equals(bc, bp);
    }

    @Override
    public boolean equals (Object o) {
        if (this ==o) {
            return true;
        }
        if (o ==null || getClass() !=o.getClass()) {
            return false;
        }

        final ScreenCharacter that = (ScreenCharacter) o;

        if (character !=that.character) {
            return false;
        }
        if (props !=null ? !props.equals(that.props) : that.props !=null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode () {
        int result = (int) character;
        result = 31 *result +(props!=null ? props.hashCode() : 0);
        return result;
    }
}
