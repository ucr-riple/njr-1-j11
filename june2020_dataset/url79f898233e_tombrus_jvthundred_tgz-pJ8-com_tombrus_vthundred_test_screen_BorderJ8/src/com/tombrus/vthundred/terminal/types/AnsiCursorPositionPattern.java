package com.tombrus.vthundred.terminal.types;

import com.tombrus.vthundred.terminal.input.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class AnsiCursorPositionPattern implements InputPattern {
    public Key match (List<Character> seq) {
        AtomicReference<Key> result = new AtomicReference<Key>();
        int                  i      = 0;
        if (!isChar(seq, AnsiType.ESC, i, result)) {
            return result.get();
        }
        i++;
        if (!isChar(seq, '[', i, result)) {
            return result.get();
        }
        i++;
        int y = 0;
        while (isDigit(seq, i, result)) {
            y = 10 *y +(seq.get(i) -'0');
            i++;
        }
        if (y ==0) {
            y = 1;
        }
        if (!isChar(seq, ';', i, result)) {
            return result.get();
        }
        i++;
        int x = 0;
        while (isDigit(seq, i, result)) {
            x = 10 *x +(seq.get(i) -'0');
            i++;
        }
        if (x ==0) {
            x = 1;
        }
        if (!isChar(seq, 'R', i, result)) {
            return result.get();
        }
        i++;
        return new Key(x, y, i);
    }

    private boolean isChar (List<Character> seq, char c, int at, AtomicReference<Key> result) {
        if (seq.size() <=at) {
            result.set(at ==0 ? null : Key.PARTIAL);
            return false;
        }
        if (seq.get(at) !=c) {
            result.set(null);
            return false;
        }
        return true;
    }

    private boolean isDigit (List<Character> seq, int at, AtomicReference<Key> result) {
        if (seq.size() <=at) {
            result.set(at ==0 ? null : Key.PARTIAL);
            return false;
        }
        if (!Character.isDigit(seq.get(at))) {
            result.set(null);
            return false;
        }
        return true;
    }
}
