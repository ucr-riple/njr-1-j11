package com.tombrus.vthundred.terminal.input;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.input.Key.*;
import com.tombrus.vthundred.util.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings({"FieldCanBeLocal"})
public class InputDecoder {
    private static final int             TIME_BEFORE_PARTIALS_ARE_RECOGNIZED = 500;

    private        final CharReader      charReader;
    private        final KeyMaker        keyMaker;
    private        final ResizeDeliverer resizeDeliverer;
    private        final KeyDeliverer    keyDeliverer;
    private              TerminalXY      lastTerminalSize;
    private              ResizeHandler   probeSizeHandler;

    public InputDecoder (final Reader source, TerminalType type) {
        charReader      = new CharReader(source);
        keyMaker        = new KeyMaker    (charReader, type.getInputPatterns());
        resizeDeliverer = new ResizeDeliverer(keyMaker);
        keyDeliverer    = new KeyDeliverer(keyMaker                           );
    }

    public void addResizeHandler (ResizeHandler handler) {
        resizeDeliverer.addResizeHandler(handler);
    }

    public void removeResizeHandler (ResizeHandler handler) {
        resizeDeliverer.removeResizeHandler(handler);
    }

    public void addKeyHandler (KeyHandler h) {
        keyDeliverer.addKeyHandler(h);
    }

    public void removeKeyHandler (KeyHandler h) {
        keyDeliverer.removeKeyHandler(h);
    }

    public void setProbeSizeHandler (ResizeHandler handler) {
        if (probeSizeHandler !=null && handler !=null) {
            DB.error("OVERWRITING probeSizeHandler");
        }
        probeSizeHandler = handler;
    }

    private void deliverToProbeSizeListener (TerminalXY newSize) {
        setScreenSize(newSize);
        final ResizeHandler l = probeSizeHandler;
        if (l !=null) {
            l.handleResize(newSize);
        }
    }

    public TerminalXY getScreenSize () {
        return lastTerminalSize;
    }

    public void setScreenSize (TerminalXY xy) {
        lastTerminalSize = xy;
    }

    private class CharReader extends Thread {
        private final Reader                   source;
        private final BlockingQueue<Character> charQueue = new LinkedBlockingQueue<Character>();

        public CharReader (Reader source) {
            super("CharReader");
            this.source = source;
            setDaemon(true);
            start();
        }

        @Override
        public void run () {
            try {
                while (true) {
                    int c = source.read();
                    if (c ==-1) {
                        break;
                    }
                    DB.t("[" +(char) c +"]");
                    charQueue.put((char) c);
                }
            } catch (Exception e) {
                DB.error(new Error("CharReader: ", e));
            }
        }

    }

    private class KeyMaker extends Thread {
        private final BlockingQueue<Key>        keyQueue    = new LinkedBlockingQueue<Key>       ();
        private final BlockingQueue<TerminalXY> resizeQueue = new LinkedBlockingQueue<TerminalXY>();
        private final List<Character>           buffer      = new ArrayList<Character>();
        private final CharReader                charReader;
        private final Collection<InputPattern>  patterns;

        public KeyMaker (CharReader charReader, List<InputPattern> patterns) {
            super("KeyMaker");
            this.charReader = charReader;
            this.patterns   = patterns;
            setDaemon(true);
            start();
        }

        @Override
        public void run () {
            try {
                final BlockingQueue<Character> queue = charReader.charQueue;
                while (charReader.isAlive()) {
                    Character c = buffer.isEmpty() ? queue.take() : queue.poll(TIME_BEFORE_PARTIALS_ARE_RECOGNIZED, TimeUnit.MILLISECONDS);
                    if (c !=null) {
                        buffer.add(c);
                    }
                    match(c ==null);
                }
                synchronized (keyQueue) {
                    final Key eot = new Key(Kind.EOT, 0);
                    while (true) {
                        keyQueue.wait();
                        if (keyQueue.isEmpty()) {
                            keyQueue.add(eot);
                        }
                    }
                }
            } catch (Exception e) {
                DB.error(new Error("KeyMaker: ", e));
            }
        }

        private void match (boolean ignorePartials) {
            boolean keyFound = true;
            while (!buffer.isEmpty() && keyFound) {
                boolean noPartials = true;
                Key     bestKey    = null;

                for (InputPattern p : patterns) {
                    Key k = p.match(buffer);
                    if (k ==Key.PARTIAL) {
                        noPartials = false;
                    }else if (k !=null && (bestKey==null || bestKey.getNumChars()<k.getNumChars())) {
                        bestKey = k;
                    }
                }
                keyFound = noPartials || ignorePartials;
                if (keyFound) {
                    if (bestKey ==null) {
                        bestKey = new Key(buffer.get(0));
                    }
                    buffer.subList(0, bestKey.getNumChars()).clear();
                    DB.t("{" +bestKey +"}");
                    if (bestKey.getKind() ==Kind.CursorLocation) {
                        final TerminalXY loc = bestKey.getCursorLocation();
                        deliverToProbeSizeListener(loc);
                        resizeQueue.add(loc);
                    }else {
                        keyQueue.add(bestKey);
                    }
                }
            }
        }
    }

    private class ResizeDeliverer extends Thread {
        private final KeyMaker            keyMaker;
        private final List<ResizeHandler> resizeHandlers = new ArrayList<ResizeHandler>();

        public ResizeDeliverer (KeyMaker keyMaker) {
            super("ResizeDeliverer");
            this.keyMaker = keyMaker;
            setDaemon(true);
            start();
        }

        @Override
        public void run () {
            try {
                while (keyMaker.isAlive()) {
                    resizeDetected(keyMaker.resizeQueue.take());
                }
            } catch (Exception e) {
                DB.error(new Error("KeyMaker: ", e));
            }
        }

        public void addResizeHandler (ResizeHandler handler) {
            synchronized (resizeHandlers) {
                resizeHandlers.remove(handler);
                resizeHandlers.add(handler);
            }
        }

        public void removeResizeHandler (ResizeHandler handler) {
            synchronized (resizeHandlers) {
                resizeHandlers.remove(handler);
            }
        }

        private void resizeDetected (TerminalXY newSize) {
            List<ResizeHandler> clone;
            synchronized (resizeHandlers) {
                clone = new ArrayList<ResizeHandler>(resizeHandlers);
            }
            for (ResizeHandler h : clone) {
                h.handleResize(newSize);
            }
        }
    }

    private class KeyDeliverer extends Thread {
        private final KeyMaker         keyMaker;
        private final List<KeyHandler> keyHandlers = new ArrayList<KeyHandler>();

        public KeyDeliverer (KeyMaker keyMaker) {
            super("KeyDeliverer");
            this.keyMaker = keyMaker;
            setDaemon(true);
            start();
        }

        @Override
        public void run () {
            try {
                while (keyMaker.isAlive()) {
                    keyDetected(keyMaker.keyQueue.take());
                }
            } catch (Exception e) {
                DB.error(new Error("KeyMaker: ", e));
            }
        }

        public void addKeyHandler (KeyHandler h) {
            synchronized (keyHandlers) {
                keyHandlers.remove(h);
                keyHandlers.add(h);
            }
        }

        public void removeKeyHandler (KeyHandler h) {
            synchronized (keyHandlers) {
                keyHandlers.remove(h);
            }
        }

        private void keyDetected (Key key) {
            List<KeyHandler> clone;
            synchronized (keyHandlers) {
                clone = new ArrayList<KeyHandler>(keyHandlers);
            }
            for (KeyHandler h : clone) {
                h.handleKey(key);
            }
        }
    }
}