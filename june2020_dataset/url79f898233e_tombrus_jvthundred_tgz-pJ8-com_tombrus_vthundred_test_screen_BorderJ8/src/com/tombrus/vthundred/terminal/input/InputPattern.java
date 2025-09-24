package com.tombrus.vthundred.terminal.input;

import java.util.*;

public interface InputPattern {
    Key match (List<Character> seq);
}
