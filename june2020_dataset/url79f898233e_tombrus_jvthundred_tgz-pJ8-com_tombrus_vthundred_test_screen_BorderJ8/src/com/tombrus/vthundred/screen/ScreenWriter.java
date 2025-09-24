package com.tombrus.vthundred.screen;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.CharProps.*;

public interface ScreenWriter {
    void setTabBehaviour (TabBehaviour tabBehaviour         );
    void write           (Object...    a                    );
    void fill            (                                  );
    void fill            (Color        bg                   );
    void fill            (char         c                    );
    void fill            (char         c, Color fg, Color bg);
    void scrollUp        (                                  );
    void border          (Color        fg                   );
    TerminalXY getCurrentPos ();
}
