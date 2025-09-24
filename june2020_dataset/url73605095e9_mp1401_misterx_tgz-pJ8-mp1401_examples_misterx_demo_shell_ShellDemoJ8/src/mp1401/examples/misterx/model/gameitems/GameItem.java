package mp1401.examples.misterx.model.gameitems;

import java.io.Serializable;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.observers.GameItemObservable;


public interface GameItem extends Serializable, GameItemObservable {

	public Game getGame();
	
	public void printMessage(String message);
}
