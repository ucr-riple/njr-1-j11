package com.tombrus.vthundred.terminal.input;

import com.tombrus.vthundred.terminal.input.Key.*;

import java.util.*;

public class SimpleInputPattern implements InputPattern {
    private Key    key;
    private char[] pattern;

    public SimpleInputPattern (Kind kind, char... pattern) {
        this.key     = new Key(kind,pattern.length);
        this.pattern = pattern;
    }

    public Key match (List<Character> seq) {
        int i = 0;
        for (Character c : seq) {
            if (pattern.length <=i) {
                return key;
            }else if (c !=pattern[i]) {
                return null;
            }else {
                i++;
            }
        }
        return pattern.length==i ? key : Key.PARTIAL;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj instanceof SimpleInputPattern) {
            SimpleInputPattern other = (SimpleInputPattern) obj;
            return Arrays.equals(pattern, other.pattern);
        }
        return false;
    }

    @Override
    public int hashCode () {
        int hash = 3;
        hash = 53 *hash +Arrays.hashCode(this.pattern);
        return hash;
    }
}
