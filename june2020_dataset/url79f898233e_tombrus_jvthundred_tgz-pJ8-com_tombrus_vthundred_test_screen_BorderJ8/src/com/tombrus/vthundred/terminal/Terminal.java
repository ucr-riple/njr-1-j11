package com.tombrus.vthundred.terminal;

import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.input.*;

@SuppressWarnings({"UnusedDeclaration"})
public interface Terminal {
    void       startTerminal       (                        );
    void       stopTerminal        (                        );

    void       run                 (Runnable      r         );

    void       clearScreen         (                        );
    void       clearScreen         (Color         background);
    void       write               (Object...     a         );
    void       flush               (                        );

    TerminalXY getTerminalSize     (                        );
    void       addResizeHandler    (ResizeHandler h         );
    void       removeResizeHandler (ResizeHandler h         );

    void       addKeyHandler       (KeyHandler    h         );
    void       removeKeyHandler    (KeyHandler    h         );
}
