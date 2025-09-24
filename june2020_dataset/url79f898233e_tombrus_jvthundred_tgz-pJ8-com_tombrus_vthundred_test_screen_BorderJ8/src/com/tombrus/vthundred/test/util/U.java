package com.tombrus.vthundred.test.util;

import com.tombrus.vthundred.screen.*;
import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.CharProps.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.terminal.input.Key.*;
import com.tombrus.vthundred.util.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class U {
    public  static final String        SOME_TEXT =
            "Watercress combines admirably with its counterpart apparently in a quarter of butter, adding a tablespoonful of the slices of beef, put them from a dessert-spoonful of carrots; " +
            "then stir in a brown sauce, pour the greater part of chervil. TOMATO PURE Cook the juice of an egg into small pieces, with stewed in each piece your taste. In the middle "       +
            "of vinegar, cayenne to flavor from the end of the level teaspoonful of fresh ones--thyme, parsley, and fry over the hawthorn is a thick enough to a little nutmeg or marjoram "    +
            "with pepper and tails should be strongly flavored with salt, mixed Gruyre cheese. Place it is cooked, take out the grease pretty dish, very good soup-spoonfuls of potatoes "     +
            "and place the dish never fails to the meat and remove the hare in beaten before, so that you buy fish dishes served very slowly so that at the back in four, which makes the "     +
            "top in powdered sugar. If they are tender. Then make a beautiful nature all well browned, put the mixture; sprinkle of half a small glassful of two dessertspoonfuls of pork. "    +
            "It is better still, ham, pour over the biscuits, and serve. CALF'S LIVER Ë LA FURNES Take care to thicken it heat them a crystal dishes. MADELINE CHERRIES Take them in place. "   +
            "Fill an onion, add powdered sugar. If you season with a little tender, remove any seasoning of the edge of hake, and decorate any other with the top a thick slices of milk, "     +
            "adding pepper and trimmings of an infant, then add the flour and crumble them as good pure of best Madeira wine, the liquor you have it through the yolks of the stalks and "     +
            "if they are large ones also. Put aside to start the floury kind. When tender put it hot.";

    private static final AtomicBoolean stop      = new AtomicBoolean();

    public static void sleep (int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            // not used
        }
    }

    public static void waitForQ (Screen s) {
        s.addKeyHandler(new StopScreenHandler(s));
        waitForStop(s);

    }

    public static void waitForQ (final Terminal t) {
        t.addKeyHandler(new StopTerminalHandler(t));
        waitForStop(t);
    }

    public static void waitForStop (Object ts) {
        if (ts instanceof Terminal) {
            ((Terminal) ts).startTerminal();
        }else if (ts instanceof Screen) {
            ((Screen) ts).startScreen();
            ;
        }
        synchronized (stop) {
            while (!stop.get()) {
                try {
                    stop.wait();
                } catch (InterruptedException e) {
                    DB.error(e);
                }
            }
        }
    }

    public static void setStopIfQ (Key key) {
        stop.set(isQuit(key));
        synchronized (stop) {
            stop.notifyAll();
        }
    }

    private static boolean isQuit (Key key) {
        return key.getKind() ==Kind.NormalKey && key.getCharacter() =='q';
    }

    public static Color getRandomColor () {
        return Color.values()[new Random().nextInt(Color.MAX_COLOR_CODE+1)];
    }

    private static class StopTerminalHandler implements KeyHandler {
        private final Terminal t;

        public StopTerminalHandler (Terminal t) {
            this.t = t;
            msg(null);
        }

        @Override
        public void handleKey (Key key) {
            msg(key);
            setStopIfQ(key);
        }

        private void msg (Key key) {
            final TerminalXY size = t.getTerminalSize();
            t.write( 0, size.getY() -1, CharProps.DEFAULT, "   hit 'q' to quit..."       );
            t.write(20, size.getY() -1, "last key = " +(key==null ? "<none>" : key), 0, 0);
        }
    }

    private static class StopScreenHandler implements KeyHandler {
        private final Screen s;

        public StopScreenHandler (Screen s) {
            this.s = s;
            msg(null);
        }

        @Override
        public void handleKey (Key key) {
            msg(key);
            setStopIfQ(key);
        }

        private void msg (Key key) {
            if (key !=null) {
                switch (key.getKind()) {
                case F1:
                    s.setBackgroundColor(Color.DEFAULT);
                    break;
                case F2:
                    s.setBackgroundColor(Color.BLUE);
                    break;
                case F3:
                    s.setBackgroundColor(Color.YELLOW);
                    break;
                case F4:
                    s.setBackgroundColor(Color.RED);
                    break;
                case F5:
                    s.setBackgroundColor(Color.GREEN);
                    break;
                case F6:
                    s.setBackgroundColor(Color.CYAN);
                    break;
                }
            }
            final ScreenWriter w        = s.getNewScreenWriter();
                  TerminalXY   size     = s.getScreenSize     ();
            final int          lastLine = size.getY() -1;
            w.write( 0, lastLine, "hit 'q' to quit...", Screen.SET_USER_CURSOR     );
            w.write(40, lastLine, "(last key = " +(key==null ? "<none>" : key) +")");
        }
    }
}
