package karlney.tetris.core;

import java.util.List;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-01
 * Time: 20:36
 *
 * @author karl.neyvaldt
 */
public class TetrisGame {

    // http://colinfahey.com/tetris/tetris.html
    private static final int MAX_LEVEL = 10; //level 11 is only for AI training
    private static final int MIN_LEVEL = 1;  //level 0 is only for debugging

    private static final int[] LEVEL_DELAYS = {Integer.MAX_VALUE, 500,450,400,350,300,250,200,150,100,50,0};

    //True when the game is started (False only if the game is created but not yet started)
    private boolean gameStarted;

    //True when the game is paused
    private boolean paused;

    //Current level
    private int level;

    //The players
    private List<Player> players;

    public TetrisGame(int startLevel, List<Player> players) {
        this.level = startLevel;
        this.players = players;
        this.gameStarted = false;
    }

    public void start(){
        if (!gameStarted){
            gameStarted = true;
            for (Player pl:players) {
                pl.start(getDelay());
            }
        }
    }

    public void stop(){
        for (Player pl:players) {
            pl.stop();
        }
    }

    public void increaseLevel(){
        level++;
        if	(level>= MAX_LEVEL)	level= MAX_LEVEL;
        for (Player pl:players) {
            pl.setDelay(getDelay());
            pl.setLevel(level);
        }
    }

    public void decreaseLevel(){
        level--;
        if	(level<MIN_LEVEL) level=MIN_LEVEL;
        for (Player pl:players) {
            pl.setDelay(getDelay());
            pl.setLevel(level);
        }
    }

    public void togglePause(){
        paused = !paused;
        for (Player pl:players) {
            pl.setDelay(getDelay());
        }
    }

    private int getDelay(){
        if	(!paused)	{
            return LEVEL_DELAYS[level];
        }
        return Integer.MAX_VALUE;
    }

    public boolean isPaused() {
        return paused;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean hasEnded() {
        boolean allStopped = true;
        for (Player pl:players){
            allStopped = allStopped && !pl.isRunning();
        }
        return allStopped;
    }

    public int getLevel() {
        return level;
    }

}
