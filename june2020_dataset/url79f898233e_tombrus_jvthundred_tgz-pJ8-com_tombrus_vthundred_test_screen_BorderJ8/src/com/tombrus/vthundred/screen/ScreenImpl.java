package com.tombrus.vthundred.screen;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.util.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ScreenImpl implements Screen {
    private final          ScreenCharacter             SPACE                  = new ScreenCharacter(' ');
    private final          ReentrantLock               lock                   = new ReentrantLock();
    private final          Condition                   refreshNeededCondition = lock.newCondition();
    private       volatile boolean                     running;
    private final          Terminal                    terminal;

    private final          List<ResizeHandler>         resizeHandlers         = new ArrayList<ResizeHandler>();
    private                int                         currentScreenSizeX;
    private                int                         currentScreenSizeY;
    private final          AtomicReference<TerminalXY> newScreenSize          = new AtomicReference<TerminalXY>();
    private                TerminalXY                  userCursor             = new TerminalXY(0, 0);
    private                Color                       backgroundColor        = Color.DEFAULT;
    private                ScreenCharacter[][]         currentScreen;
    private                ScreenCharacter[][]         requestedScreen;
    private                boolean[][]                 dirtyChars;
    private                boolean[]                   dirtyLine;
    private                int[]                       dirtyLowChar;
    private                int[]                       dirtyHighChar;
    private                int                         dirtyLowLine;
    private                int                         dirtyHighLine;
    private                boolean                     dirtySomething;
    private                boolean                     dirtyAll;
    private                Refresher                   refresher              = new Refresher    ();

    public ScreenImpl (Terminal terminal) {
        try {
            this.terminal = terminal;
            TerminalXY currentScreenSize = terminal.getTerminalSize();
            currentScreenSizeX = currentScreenSize.getX();
            currentScreenSizeY = currentScreenSize.getY();
            currentScreen      = new ScreenCharacter[currentScreenSizeY][currentScreenSizeX];
            requestedScreen    = new ScreenCharacter[currentScreenSizeY][currentScreenSizeX];
            dirtyLine          = new boolean[currentScreenSizeY];
            dirtyChars         = new boolean[currentScreenSizeY][currentScreenSizeX];
            dirtyLowChar       = new int[currentScreenSizeY];
            dirtyHighChar      = new int[currentScreenSizeY];
            Arrays.fill(dirtyLowChar, Integer.MAX_VALUE );
            Arrays.fill(dirtyHighChar, Integer.MIN_VALUE);
            dirtyLowLine  = Integer.MAX_VALUE;
            dirtyHighLine = Integer.MIN_VALUE;
            terminal.addResizeHandler(new TerminalResizeHandler());
        } catch (RuntimeException rt) {
            DB.error(rt);
            throw rt;
        }
    }

    @Override
    public TerminalXY getScreenSize () {
        final TerminalXY[] result = new TerminalXY[1];
        run(new Runnable() {
            @Override
            public void run () {
                result[0] = new TerminalXY(currentScreenSizeX, currentScreenSizeY);
            }
        });
        return result[0];
    }

    @Override
    public void startScreen () {
        if (!running) {
            lock.lock();
            try {
                if (!running) {
                    running = true;
                    refresher.start();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    @SuppressWarnings({"UnusedDeclaration"})
    public void stopScreen () {
        lock.lock();
        try {
            if (running) {
                terminal.stopTerminal();
                running = false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setUserCursor (final TerminalXY loc) {
        if (loc ==null) {
            throw new IllegalArgumentException("user cursor location may not be null");
        }
        run(new Runnable() {
            @Override
            public void run () {
                if (!userCursor.equals(loc)) {
                    userCursor = loc;
                    requestRefresh();
                }
            }
        });
    }

    @Override
    public void setBackgroundColor (final Color color) {
        if (color ==null) {
            throw new IllegalArgumentException("background color may not be null");
        }
        run(new Runnable() {
            @Override
            public void run () {
                backgroundColor = color;
                requestRefreshWholeScreen();
            }
        });
    }

    private void fill (final int x, final int y, final int w, final int h, final ScreenCharacter sc) {
        run(new Runnable() {
            @Override
            public void run () {
                int fx = limit(0, x, currentScreenSizeX    );
                int fy = limit(0, y, currentScreenSizeY    );
                int tx = limit(fx, x +w, currentScreenSizeX);
                int ty = limit(fy, y +h, currentScreenSizeY);

                for (int line = fy; line<ty; line++) {
                    Arrays.fill(requestedScreen[line], fx, tx, sc);
                    Arrays.fill(dirtyChars[line], fx, tx, true   );
                    dirtyHighChar[line] = Math.max(dirtyHighChar[line], tx -1);
                    dirtyLowChar[line]  = Math.min(dirtyLowChar[line], fx);
                    dirtyLine[line]     = true;
                }
                dirtyHighLine  = Math.max(dirtyHighLine, ty -1);
                dirtyLowLine   = Math.min(dirtyLowLine, fy);
                dirtySomething = true;

                if ((tx-fx) *(ty-fy) >2 *currentScreenSizeX *currentScreenSizeY) {
                    requestRefreshWholeScreen();
                }else {
                    requestRefresh();
                }
            }
        });
    }

    private void scrollUp (final int x, final int y, final int w, final int h, final int d) {
        run(new Runnable() {
            @Override
            public void run () {
                final int fx        = limit(0, x, currentScreenSizeX    );
                final int fy        = limit(0, y, currentScreenSizeY    );
                final int tx        = limit(fx, x +w, currentScreenSizeX);
                final int ty        = limit(fy, y +h, currentScreenSizeY);

                final int copyWidth = tx -fx;

                if (h <=d) {
                    // nothing to copy, only clear
                    fill(x, y, w, h, null);
                }else {
                    for (int line = fy; line<ty; line++) {
                        final ScreenCharacter[] srcLine = requestedScreen[line+d];
                        final ScreenCharacter[] trgLine = requestedScreen[line];
                        if (line <ty -d) {
                            System.arraycopy(srcLine, fx, trgLine, fx, copyWidth);
                        }else {
                            Arrays.fill(trgLine, fx, tx, null);
                        }
                        Arrays.fill(dirtyChars[line], fx, tx, true);
                        dirtyHighChar[line] = Math.max(dirtyHighChar[line], tx -1);
                        dirtyLowChar[line]  = Math.min(dirtyLowChar[line], fx);
                        dirtyLine[line]     = true;
                    }
                    dirtyHighLine  = Math.max(dirtyHighLine, ty -1);
                    dirtyLowLine   = Math.min(dirtyLowLine, fy);
                    dirtySomething = true;
                }
                requestRefresh();
            }
        });
    }

    private void border (final int x, final int y, final int w, final int h, final Color c) {
        run(new Runnable() {
            @Override
            public void run () {
                final CharProps p  = c.getFgChanger().change(CharProps.DEFAULT);
                final int       fx = limit(0, x, currentScreenSizeX    );
                final int       fy = limit(0, y, currentScreenSizeY    );
                final int       tx = limit(fx, x +w, currentScreenSizeX);
                final int       ty = limit(fy, y +h, currentScreenSizeY);

                setReqChar(fx, fy, AnsiGraphics.SINGLE_LINE_UP_LEFT_CORNER, p);
                for (int xx = fx+1; xx<tx-1; xx++) {
                    setReqChar(xx, fy, AnsiGraphics.SINGLE_LINE_HORIZONTAL, p);
                }
                setReqChar(tx -1, fy, AnsiGraphics.SINGLE_LINE_UP_RIGHT_CORNER, p);
                for (int yy = fy+1; yy<ty-1; yy++) {
                    setReqChar(fx, yy, AnsiGraphics.SINGLE_LINE_VERTICAL, p   );
                    setReqChar(tx -1, yy, AnsiGraphics.SINGLE_LINE_VERTICAL, p);
                }
                setReqChar(fx, ty -1, AnsiGraphics.SINGLE_LINE_LOW_LEFT_CORNER, p);
                for (int xx = fx+1; xx<tx-1; xx++) {
                    setReqChar(xx, ty -1, AnsiGraphics.SINGLE_LINE_HORIZONTAL, p);
                }
                setReqChar(tx -1, ty -1, AnsiGraphics.SINGLE_LINE_LOW_RIGHT_CORNER, p);
                requestRefresh();
            }
        });
    }

    private int limit (int lo, int v, int hi) {
        return v <lo ? lo : v <hi ? v : hi;
    }

    @Override
    public void run (Runnable runnable) {
        lock.lock();
        try {
            startScreen();
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addResizeListener (ResizeHandler handler) {
        synchronized (resizeHandlers) {
            resizeHandlers.remove(handler);
            resizeHandlers.add(handler);
        }
    }

    @Override
    public void removeResizeListener (ResizeHandler handler) {
        synchronized (resizeHandlers) {
            resizeHandlers.remove(handler);
        }
    }

    public void addKeyHandler (KeyHandler h) {
        terminal.addKeyHandler(h);
    }

    public void removeKeyHandler (KeyHandler h) {
        terminal.removeKeyHandler(h);
    }

    @Override
    public ScreenWriter getNewScreenWriter () {
        return new ScreenWriterImpl(0, 0, currentScreenSizeX, currentScreenSizeY);
    }

    @Override
    public ScreenWriter getNewScreenWriter (int x, int y, int w, int h) {
        return new ScreenWriterImpl(x, y, w, h);
    }

    public class ScreenWriterImpl implements ScreenWriter {
        private final int          subX;
        private final int          subY;
        private final int          subW;
        private final int          subH;
        private       TabBehaviour tabBehaviour = TabBehaviour.ALIGN_4;

        private       int          currentX;
        private       int          currentY;
        private       CharProps    currentProps = CharProps.DEFAULT;

        public ScreenWriterImpl (int subX, int subY, int subW, int subH) {
            this.subX = subX;
            this.subY = subY;
            this.subW = subW;
            this.subH = subH;
        }

        @Override
        public void setTabBehaviour (TabBehaviour tabBehaviour) {
            if (tabBehaviour ==null) {
                throw new IllegalArgumentException("tabBehaviour may not be null");
            }
            this.tabBehaviour = tabBehaviour;
        }

        @Override
        public void write (Object... a) {
            lock.lock();
            try {
                startScreen();
                boolean wasDirty = dirtySomething;
                boolean intIsX   = true;
                for (Object o : a) {
                    if (o instanceof Integer) {
                        int xy = (Integer) o;
                        if (intIsX) {
                            currentX = Math.max(0, Math.min(xy, subW -1));
                        }else {
                            currentY = Math.max(0, Math.min(xy, subH -1));
                        }
                        intIsX = !intIsX;
                    }else if (o instanceof TerminalXY) {
                        TerminalXY xy = (TerminalXY) o;
                        currentX = Math.max(0, Math.min(xy.getX(), subW -1));
                        currentY = Math.max(0, Math.min(xy.getY(), subH -1));
                        intIsX   = true;
                    }else if (o instanceof CharProps) {
                        currentProps = (CharProps) o;
                        intIsX       = true;
                    }else if (o instanceof CharPropsChanger) {
                        currentProps = ((CharPropsChanger) o).change(currentProps);
                        intIsX       = true;
                    }else if (o instanceof Color) {
                        currentProps = ((Color) o).getFgChanger().change(currentProps);
                        intIsX       = true;
                    }else if (o ==SET_USER_CURSOR) {
                        setUserCursor(new TerminalXY(subX +currentX, subY +currentY));
                    }else {
                        if (0 <=currentY && currentY <subH) {
                            String str = o ==null ? "null" : o.toString();
                            str = tabBehaviour.replaceTabs(str, currentX);
                            for (char c : str.toCharArray()) {
                                if (c =='\n') {
                                    if (currentY <subH -1) {
                                        currentX = 0;
                                        currentY++;
                                    }else if (subH <=1) {
                                        fill();
                                    }else {
                                        scrollUp();
                                    }
                                }else if (0 <=currentX && currentX <subW) {
                                    setReqChar(subX +currentX, subY +currentY, c, currentProps);
                                    currentX++;
                                }
                            }
                        }
                        intIsX = true;
                    }
                }
                if (!wasDirty && dirtySomething) {
                    requestRefresh();
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void fill () {
            fill(Color.DEFAULT);
        }

        @Override
        public void fill (Color bg) {
            fill(' ', Color.DEFAULT, bg);
        }

        @Override
        public void fill (char c) {
            fill(c, Color.DEFAULT, Color.DEFAULT);
        }

        @Override
        public void fill (char c, Color fg, Color bg) {
            ScreenImpl.this.fill(subX, subY, subW, subH, new ScreenCharacter(c, fg, bg));
            currentX = 0;
            currentY = 0;
        }

        @Override
        public void scrollUp () {
            ScreenImpl.this.scrollUp(subX, subY, subW, subH, 1);
            currentX = 0;
        }

        @Override
        public void border (Color c) {
            ScreenImpl.this.border(subX -1, subY -1, subW +2, subH +2, c);
        }

        @Override
        public TerminalXY getCurrentPos () {
            return new TerminalXY(currentX, currentY);
        }
    }

    private void resizeDetected (TerminalXY newSize) {
        List<ResizeHandler> clone;
        synchronized (resizeHandlers) {
            clone = new ArrayList<ResizeHandler>(resizeHandlers);
        }
        for (ResizeHandler resizeHandler : clone) {
            resizeHandler.handleResize(newSize);
        }
    }

    private void setReqChar (int x, int y, char c, CharProps p) {
        if (y>=0 && y<currentScreenSizeY && x >=0 && x <currentScreenSizeX) {
            ScreenCharacter req = requestedScreen[y][x];
            if (!ScreenCharacter.safeEquals(req, c, p)) {
                // different from requested...
                ScreenCharacter cur = currentScreen[y][x];
                if (!ScreenCharacter.safeEquals(cur, c, p)) {
                    // ...and different from current
                    requestedScreen[y][x] = SPACE.equals(c, p) ? null : new ScreenCharacter(c, p);
                    dirtyChars[y][x]      = true;
                    dirtyLine[y]          = true;
                    dirtyLowChar[y]       = Math.min(dirtyLowChar[y], x);
                    dirtyHighChar[y]      = Math.max(dirtyHighChar[y], x);
                    dirtyLowLine          = Math.min(dirtyLowLine, y   );
                    dirtyHighLine         = Math.max(dirtyHighLine, y   );
                    dirtySomething        = true;
                }else {
                    // ...back to currently displayed char:
                    requestedScreen[y][x] = cur;
                    dirtyChars[y][x]      = false;
                }
            }
        }
    }

    private void refresh () {
        if (running && dirtySomething) {
            terminal.run(new Runnable() {
                @Override
                public void run () {
                    resizeScreenIfNeeded();
                    terminal.write(false);
                    if (dirtyAll) {
                        DB.t("\n<refresh-all>\n");
                        terminal.clearScreen(backgroundColor);
                        for (int y = 0; y<currentScreenSizeY; y++) {
                            final ScreenCharacter[] reqLine = requestedScreen[y];
                            for (int x = 0; x<currentScreenSizeX; x++) {
                                ScreenCharacter req = reqLine[x];
                                if (req !=null) {
                                    terminal.write(x, y, req.getProps(), req.getCharacter());
                                }
                            }
                            System.arraycopy(reqLine, 0, currentScreen[y], 0, currentScreenSizeX);
                            Arrays.fill(dirtyChars[y], false);
                        }
                        Arrays.fill(dirtyLine, false                );
                        Arrays.fill(dirtyLowChar, Integer.MAX_VALUE );
                        Arrays.fill(dirtyHighChar, Integer.MIN_VALUE);
                    }else {
                        DB.t("\n<refresh>\n");
                        for (int y = dirtyLowLine; y<=dirtyHighLine; y++) {
                            final ScreenCharacter[] curLine       = currentScreen[y];
                            final ScreenCharacter[] reqLine       = requestedScreen[y];
                            final boolean[]         dirtyCharLine = dirtyChars[y];
                            for (int x = dirtyLowChar[y]; x<=dirtyHighChar[y]; x++) {
                                if (dirtyCharLine[x]) {
                                    ScreenCharacter req = reqLine[x];
                                    ScreenCharacter cur = curLine[x];
                                    if (req !=null && !req.equals(cur)) {
                                        terminal.write(x, y, req.getProps(), req.getCharacter());
                                    }else if (req ==null && cur !=null) {
                                        terminal.write(x, y, SPACE.getProps(), SPACE.getCharacter());
                                    }
                                }
                                dirtyCharLine[x] = false;
                            }
                            System.arraycopy(reqLine, 0, curLine, 0, currentScreenSizeX);
                            dirtyLine[y]     = false;
                            dirtyLowChar[y]  = Integer.MAX_VALUE;
                            dirtyHighChar[y] = Integer.MIN_VALUE;
                        }
                    }
                    dirtyLowLine   = Integer.MAX_VALUE;
                    dirtyHighLine  = Integer.MIN_VALUE;
                    dirtyAll       = false;
                    dirtySomething = false;
                    terminal.write(userCursor, true);
                }
            });
        }
    }

    private void resizeScreenIfNeeded () {
        TerminalXY newSize = newScreenSize.getAndSet(null);
        if (newSize !=null && (newSize.getX()!=currentScreenSizeX || newSize.getY()!=currentScreenSizeY)) {
            DB.t("\n<screen-resize:["+currentScreenSizeX+","+currentScreenSizeY +"]=>" +newSize +">  ");
            final int                 numLines           = newSize.getY();
            final int                 numChars           = newSize.getX();
            final int                 numCharsToCopy     = Math.min(currentScreenSizeX, numChars);
            final int                 numLinesToCopy     = Math.min(currentScreenSizeY, numLines);

            final ScreenCharacter[][] newRequestedScreen = new ScreenCharacter[numLines][numChars];
            final ScreenCharacter[][] newCurrentScreen   = new ScreenCharacter[numLines][numChars];
            for (int y = 0; y<numLinesToCopy; y++) {
                System.arraycopy(requestedScreen[y], 0, newRequestedScreen[y], 0, numCharsToCopy);
                System.arraycopy(currentScreen[y], 0, newCurrentScreen[y], 0, numCharsToCopy    );
            }
            requestedScreen    = newRequestedScreen;
            currentScreen      = newCurrentScreen;

            currentScreenSizeX = numChars;
            currentScreenSizeY = numLines;

            dirtyChars         = new boolean[numLines][numChars];
            dirtyLine          = new boolean[numLines];
            dirtyLowChar       = new int[numLines];
            dirtyHighChar      = new int[numLines];
            Arrays.fill(dirtyLowChar, Integer.MAX_VALUE );
            Arrays.fill(dirtyHighChar, Integer.MIN_VALUE);
            dirtyLowLine  = Integer.MAX_VALUE;
            dirtyHighLine = Integer.MIN_VALUE;

            requestRefreshWholeScreen(       );

            resizeDetected           (newSize);
        }
    }

    private class TerminalResizeHandler implements ResizeHandler {
        public void handleResize (TerminalXY newSize) {
            lock.lock();
            try {
                newScreenSize.set(newSize);
                requestRefreshWholeScreen();
            } finally {
                lock.unlock();
            }
        }
    }

    private void requestRefresh () {
        dirtySomething = true;
        refreshNeededCondition.signalAll();
    }

    private void requestRefreshWholeScreen () {
        dirtyAll       = true;
        dirtySomething = true;
        refreshNeededCondition.signalAll();
    }

    private class Refresher extends Thread {
        private Refresher () {
            super("Screen-Refresher");
            setDaemon(true);
        }

        @Override
        public void run () {
            lock.lock();
            try {
                if (running && dirtySomething) {
                    refresh();
                }
                while (running) {
                    // wait until there is a refresh needed (this will free the lock!):
                    refreshNeededCondition.await();

                    // wait for a quite period but prevent being locked out
                    int starvationPrevention = 0;
                    while (starvationPrevention >0 && refreshNeededCondition.await(10, TimeUnit.MILLISECONDS)) {
                        starvationPrevention--;
                    }

                    // and refresh the screen
                    refresh();
                }
            } catch (Throwable e) {
                DB.error(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
