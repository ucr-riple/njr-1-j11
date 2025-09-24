package com.tombrus.vthundred.terminal;

import com.tombrus.vthundred.terminal.input.*;

import java.util.*;

public interface TerminalType {
    List<InputPattern> getInputPatterns (                                           );

    char[]             getClearSeq      (                                           );
    char[]             getReportPosSeq  (                                           );

    char[]             getCursorOnSeq   (                                           );
    char[]             getCursorOffSeq  (                                           );

    char[]             getPrivateOnSeq  (                                           );
    char[]             getPrivateOffSeq (                                           );

    char[]             getGraphOnSeq    (                                           );
    char[]             getGraphOffSeq   (                                           );
    char               toGraph          (char      c                                );

    char[]             getMoveCursorSeq (int       fx, int        fy, int tx, int ty);
    char[]             getPropChangeSeq (CharProps old, CharProps next              );
    char[]             getMoveLeftSeq   (int       n                                );
}
