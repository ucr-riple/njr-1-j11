package observers;

import modes.GameMode;
import modes.selections.Selections;
import achiever.Achiever;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;

public class ObserverLose extends GameObserver {

    private GameMode myMode;

    @Override
    public void notifyObserverTemplate(Achiever achiever) {
        myMode.stopGame();
        Selections.setGameOver(true);
    }

    public void setGameMode(GameMode mode) {
        myMode = mode;
    }

}
