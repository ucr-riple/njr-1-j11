package MazeRoomLogic;

import java.util.Observable;

import GameOverLogic.GameOverWinBehavior;
import GameOverLogic.GameOverObserver;
import MazeGame.Player;

public class ExitRoomEnterBehavior extends Observable implements MazeRoomEnterBehavior  {

	public ExitRoomEnterBehavior(){
		this.addObserver(new GameOverObserver());
	}
	
	@Override
	public boolean enter() {
		Player.getInstance().addPoints(10000);
		this.setChanged();
		this.notifyObservers(new GameOverWinBehavior());
		this.clearChanged();
		return true;
	}

}
