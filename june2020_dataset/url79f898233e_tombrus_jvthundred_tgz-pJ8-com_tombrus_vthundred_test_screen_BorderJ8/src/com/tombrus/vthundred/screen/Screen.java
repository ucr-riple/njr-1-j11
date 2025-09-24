package com.tombrus.vthundred.screen;

import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.input.*;

@SuppressWarnings({"UnusedDeclaration"})
public interface Screen {
    Object SET_USER_CURSOR = new Object();

    void         startScreen          (                                    );
    void         stopScreen           (                                    );

    TerminalXY   getScreenSize        (                                    ); //TODO:move to ScreenWriter
    void         setBackgroundColor   (Color         color                 ); //TODO:move to ScreenWriter
    void         run                  (Runnable      runnable              ); //TODO:move to ScreenWriter

    ScreenWriter getNewScreenWriter   (                                    );
    ScreenWriter getNewScreenWriter   (int           x, int y, int w, int h);

    void         addResizeListener    (ResizeHandler handler               );
    void         removeResizeListener (ResizeHandler handler               );

    void         setUserCursor        (TerminalXY    loc                   );
    void         addKeyHandler        (KeyHandler    h                     );
    void         removeKeyHandler     (KeyHandler    h                     );
}
