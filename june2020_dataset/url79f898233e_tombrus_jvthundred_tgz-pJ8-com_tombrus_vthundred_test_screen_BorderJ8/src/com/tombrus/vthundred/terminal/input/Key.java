package com.tombrus.vthundred.terminal.input;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.util.*;

public class Key {
    public  static final Key        PARTIAL        = new Key(Kind.Partial, 0);

    private        final Kind       kind;
    private        final int        numChars;
    private        final char       character;
    private        final TerminalXY cursorLocation;

    public enum Kind {
        NormalKey,
        Backspace,
        ArrowLeft,
        ArrowRight,
        ArrowUp,
        ArrowDown,
        Insert,
        Delete,
        Home,
        End,
        PageUp,
        PageDown,
        Tab,
        ReverseTab,
        Enter,
        F1,
        F2,
        F3,
        F4,
        F5,
        F6,
        F7,
        F8,
        F9,
        F10,
        F11,
        F12,
        F13,
        F14,
        F15,
        F16,
        F17,
        CursorLocation,
        EOT,
        Partial,
    }

    public Key (Kind kind, int numChars) {
        this.kind           = kind;
        this.numChars       = numChars;
        this.character      = 0;
        this.cursorLocation = null;
    }

    public Key (char character) {
        this.kind           = Kind.NormalKey;
        this.numChars       = 1;
        this.character      = character;
        this.cursorLocation = null;
    }

    public Key (int x, int y, int numChars) {
        this.kind           = Kind.CursorLocation;
        this.numChars       = numChars;
        this.character      = 0;
        this.cursorLocation = new TerminalXY(x, y);
    }

    public Kind getKind () {
        return kind;
    }

    public int getNumChars () {
        return numChars;
    }

    public char getCharacter () {
        if (kind !=Kind.NormalKey) {
            DB.error("ONLY NORMAL KEYS HAVE A VALID CHARACTER");
        }
        return character;
    }

    public TerminalXY getCursorLocation () {
        if (kind !=Kind.CursorLocation) {
            DB.error("ONLY CUSRORLOCATION KEYS HAVE A VALID CUSRORLOCATION");
        }
        return cursorLocation;
    }

    @Override
    public String toString () {
        switch (getKind()) {
        case NormalKey:
            return String.format("<'%c'=0x%02x>",character,(int)character);
        case CursorLocation:
            return "<"+cursorLocation +":" +numChars +">";
        default:
            return "<"+getKind() +":" +numChars +">";
        }
    }

    @Override
    public boolean equals (Object obj) {
        return obj instanceof TerminalXY && character ==((Key) (obj)).character;
    }

    @Override
    public int hashCode () {
        int hash = 3;
        hash = 73 *hash +this.character;
        return hash;
    }
}
