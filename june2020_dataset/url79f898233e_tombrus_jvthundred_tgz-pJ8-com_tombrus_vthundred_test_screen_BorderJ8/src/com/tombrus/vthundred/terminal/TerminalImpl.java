package com.tombrus.vthundred.terminal;

import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.util.*;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class TerminalImpl implements Terminal {
    private   static final          int           BUFFER_SIZE         = 25 *1024;
    private   static final          TerminalXY    DEFAULT_SCREEN_SIZE = new TerminalXY(80, 24);

    private          final          TerminalType  type;
    protected        final          InputDecoder  inputDecoder;
    private          final          GraphWriter   graphWriter;

    private          final          ReentrantLock lock                = new ReentrantLock();
    private                         Thread        watchDog;
    private                volatile boolean       running;

    private                         int           currentX;
    private                         int           currentY;
    private                         CharProps     currentProps        = CharProps.DEFAULT;
    private                         boolean       currentCursorOn     = true;

    private                         int           requestedX;
    private                         int           requestedY;
    private                         CharProps     requestedProps      = CharProps.DEFAULT;
    private                         boolean       requestedCursorOn   = true;

    public TerminalImpl (TerminalType type, Reader in, Writer out) {
        try {
            this.type         = type;
            this.inputDecoder = new InputDecoder(in, type);
            this.graphWriter  = new GraphWriter(type, new BufferedWriter(out, BUFFER_SIZE));

            Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        } catch (RuntimeException rt) {
            DB.error(rt);
            throw rt;
        }
    }

    @Override
    public void write (Object... a) {
        try {
            lockWithStartIfNeeded();
            boolean intIsX = true;
            for (Object o : a) {
                if (running) {
                    if (o instanceof Integer) {
                        int xy = (Integer) o;
                        if (intIsX) {
                            requestedX = xy;
                        }else {
                            requestedY = xy;
                        }
                        intIsX = !intIsX;
                    }else if (o instanceof Boolean) {
                        requestedCursorOn = (Boolean) o;
                    }else if (o instanceof TerminalXY) {
                        TerminalXY xy = (TerminalXY) o;
                        requestedX = xy.getX();
                        requestedY = xy.getY();
                        intIsX     = true;
                    }else if (o instanceof CharProps) {
                        requestedProps = (CharProps) o;
                        intIsX         = true;
                    }else if (o instanceof CharPropsChanger) {
                        requestedProps = ((CharPropsChanger) o).change(requestedProps);
                        intIsX         = true;
                    }else {
                        String str = o ==null ? "null" : o.toString();
                        effectuateRequestedPosAndProps();
                        DB.t(str);
                        graphWriter.write(str);
                        //TODO: take care of not so simple chars (CR/LF/FF/TAB) and writing past the edge of the screen (wrap?)!
                        currentX   += str.length();
                        requestedX += str.length();
                        intIsX     =  true;
                    }
                }
            }
        } catch (IOException e) {
            DB.error(e);
        } finally {
            unlockWithFlushIfLast();
        }
    }

    private void writeControlSeq (char[] seq) throws IOException {
        DB.t(seq);
        graphWriter.writeControlSeq(seq);
    }

    @Override
    public void flush () {
        try {
            lockWithStartIfNeeded();
            effectuateRequestedPosAndProps();
            graphWriter.flush();
            DB.t("<flushed>\n");
        } catch (IOException e) {
            DB.error(e);
        } finally {
            lock.unlock();
        }
    }

    public void run (Runnable r) {
        try {
            lockWithStartIfNeeded();
            r.run();
        } finally {
            unlockWithFlushIfLast();
        }
    }

    public void lockWithStartIfNeeded () {
        lock.lock();
        startTerminal();
    }

    public void unlockWithFlushIfLast () {
        if (lock.getHoldCount() ==1) {
            flush();
        }
        lock.unlock();
    }

    public void startTerminal () {
        if (!running) {
            lock.lock();
            if (!running) {
                running = true;
                try {
                    DB.t("\n<start>\n"     );
                    startActions();
                    DB.t("\n<start-done>\n");
                } catch (IOException e) {
                    DB.error(e);
                } finally {
                    unlockWithFlushIfLast();
                }
            }
        }
    }

    public void stopTerminal () {
        if (running) {
            lock.lock();
            if (running) {
                try {
                    stopActions();
                } catch (IOException e) {
                    DB.error(e);
                } finally {
                    running = false;
                    unlockWithFlushIfLast();
                }
            }
        }
    }

    public TerminalXY getTerminalSize () {
        try {
            lockWithStartIfNeeded();
            return inputDecoder.getScreenSize();
        } finally {
            lock.unlock();
        }
    }

    public void clearScreen () {
        clearScreen(Color.DEFAULT);
    }

    public void clearScreen (Color background) {
        try {
            lockWithStartIfNeeded();
            final CharProps prev = requestedProps;
            requestedProps = background.getBgChanger().change(requestedProps);
            effectuateProps();
            writeControlSeq(type.getClearSeq());
            requestedProps = prev;
        } catch (IOException e) {
            DB.error(e);
        } finally {
            unlockWithFlushIfLast();
        }
    }

    private void effectuateRequestedPosAndProps () throws IOException {
        if (requestedCursorOn !=currentCursorOn) {
            if (requestedCursorOn) {
                cursorOn();
            }else {
                cursorOff();
            }
            currentCursorOn = requestedCursorOn;
        }
        if (requestedX !=currentX || requestedY !=currentY) {
            writeControlSeq(type.getMoveCursorSeq(currentX, currentY, requestedX, requestedY));
            currentX = requestedX;
            currentY = requestedY;
        }
        effectuateProps();
    }

    private void effectuateProps () throws IOException {
        if (!requestedProps.equals(currentProps)) {
            writeControlSeq(type.getPropChangeSeq(currentProps, requestedProps));
            currentProps = requestedProps;
        }
    }

    private void enterPrivateMode () throws IOException {
        writeControlSeq(type.getPrivateOnSeq());
    }

    private void exitPrivateMode () throws IOException {
        writeControlSeq(type.getPrivateOffSeq());
    }

    private void cursorOn () throws IOException {
        writeControlSeq(type.getCursorOnSeq());
    }

    private void cursorOff () throws IOException {
        writeControlSeq(type.getCursorOffSeq());
    }

    private void reportPosition () throws IOException {
        effectuateRequestedPosAndProps();
        writeControlSeq(type.getReportPosSeq());
    }

    protected void startActions () throws IOException {
        watchDog = new Thread() {
            @Override
            public void run () {
                while (watchDog ==this) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        DB.error("watchdog hit an interrupt");
                    }
                    if (DB.hasErrors()) {
                        System.exit(999);
                    }
                }
            }
        };
        watchDog.setDaemon(true);
        watchDog.start();
        enterPrivateMode();
        clearScreen();
        write(0, 0);
        probeTerminalSize();
    }

    protected void stopActions () throws IOException {
        requestedCursorOn = true;
        requestedProps    = CharProps.DEFAULT;
        requestedX        = 0;
        requestedY        = 0;
        effectuateRequestedPosAndProps();
        graphWriter.graphOff();
        exitPrivateMode();
        flush();
        watchDog = null;
        inputDecoder.setScreenSize(null);
    }

    public void addResizeHandler (ResizeHandler handler) {
        inputDecoder.addResizeHandler(handler);
    }

    public void removeResizeHandler (ResizeHandler handler) {
        inputDecoder.removeResizeHandler(handler);
    }

    public void addKeyHandler (KeyHandler h) {
        inputDecoder.addKeyHandler(h);
    }

    public void removeKeyHandler (KeyHandler h) {
        inputDecoder.removeKeyHandler(h);
    }

    protected void probeTerminalSize () {
        try {
            lockWithStartIfNeeded();
            DB.t("\n<probeTerminalSize>\n");
            final CountDownLatch latch   = new                 CountDownLatch(1);
            final ResizeHandler  handler = new ResizeHandler() {
                public void handleResize (TerminalXY newSize) {
                    //DB.t("\n<latch downed>\n");
                    latch.countDown();
                }
            };
            inputDecoder.setProbeSizeHandler(handler);
            write(5000, 5000);
            reportPosition();
            write(   0, 0   );
            flush();
            if (!isOnConsole()) {
                inputDecoder.setScreenSize(DEFAULT_SCREEN_SIZE);
                DB.t("\n<no console, no answer expected, defaulting to " +DEFAULT_SCREEN_SIZE +">\n");
            }else {
                try {
                    if (!latch.await(1000, TimeUnit.MILLISECONDS)) {
                        DB.error("terminal did not respond with its size in time");
                    }
                } catch (InterruptedException e) {
                    DB.error(new Error("wait for terminal size was interrupted", e));
                }
                if (inputDecoder.getScreenSize() ==null) {
                    DB.t("\n<probeTerminalSize:no size, setting to default>\n");
                    inputDecoder.setScreenSize(DEFAULT_SCREEN_SIZE);
                }
                DB.t("\n<probeTerminalSize:done:" +inputDecoder.getScreenSize() +">\n");
            }
        } catch (IOException e) {
            DB.error(e);
        } finally {
            //noinspection NullableProblems
            inputDecoder.setProbeSizeHandler(null);
            lock.unlock();
        }
    }

    protected boolean isOnConsole () {
        return false;
    }

    private class ShutdownHook extends Thread {
        public ShutdownHook () {
            super("Terminal-hook");
        }

        @Override
        public void run () {
            try {
                DB.t("\nSHUTDOWN-HOOK-ACTIVATED\n");
                if (running) {
                    graphWriter.flush();
                    stopActions();
                    graphWriter.flush();
                }
            } catch (Throwable e) {
                DB.error(e);
            }
        }
    }
}
