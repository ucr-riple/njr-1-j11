package com.tombrus.vthundred.terminal.types;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.util.*;
import sun.misc.*;

import java.io.*;

public class UnixTerminal extends TerminalImpl {
    private static final boolean HAVE_CONSOLE = System.console() !=null;

    public UnixTerminal () {
        super(new AnsiType(), getReader(), getWriter());
        installDimensionChangeHandler();
    }

    private static Writer getWriter () {
        return HAVE_CONSOLE ? System.console().writer() : new OutputStreamWriter(System.out);
    }

    private static Reader getReader () {
        return HAVE_CONSOLE ? System.console().reader() : new InputStreamReader(System.in);
    }

    @Override
    protected void startActions () throws IOException {
        if (HAVE_CONSOLE) {
            Shell.shell("/bin/stty -icanon -echo min 1 < /dev/tty");
        }
        super.startActions();
    }

    @Override
    protected void stopActions () throws IOException {
        super.stopActions();
        if (HAVE_CONSOLE) {
            Shell.shell("/bin/stty icanon echo < /dev/tty");
        }
    }

    protected boolean isOnConsole () {
        return HAVE_CONSOLE;
    }

    private void installDimensionChangeHandler () {
        try {
            Signal.handle(new Signal("WINCH"), new SignalHandler() {
                public synchronized void handle (Signal signal) {
                    try {
                        probeTerminalSize();
                    } catch (Throwable e) {
                        DB.error(e);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
