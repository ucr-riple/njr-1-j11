package ru.spbau.amanov.drunkard;

import java.util.Timer;

/**
 *  Class Application provides entry point to the programm.
 *
 *  @author  Karim Amanov
 */
public class Application {

    /**
     * Entry point to the programm.
     * @param args  The command line parameters.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.run();

    }
}
