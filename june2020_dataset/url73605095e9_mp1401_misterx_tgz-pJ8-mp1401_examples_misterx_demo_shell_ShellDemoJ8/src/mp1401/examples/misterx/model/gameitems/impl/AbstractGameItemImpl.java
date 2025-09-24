package mp1401.examples.misterx.model.gameitems.impl;

import java.util.ArrayList;
import java.util.List;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.game.GameImpl;
import mp1401.examples.misterx.model.gameitems.GameItem;
import mp1401.examples.misterx.model.observers.GameItemObserver;
import mp1401.examples.misterx.model.util.Messsages;


public abstract class AbstractGameItemImpl implements GameItem {

	private static final long serialVersionUID = -3635905975313361023L;
	
	private final List<GameItemObserver> observers;
	
	public AbstractGameItemImpl() {
		this.observers = new ArrayList<GameItemObserver>();
	}

	@Override
	public Game getGame() {
		return GameImpl.getInstance();
	}
	
	@Override
	public void printMessage(String message) {
		Messsages.printMessage(message);
	}
	
	@Override
	public void addGameItemObserver(GameItemObserver observer) {
		observers.add(observer);
		
	}
	
	@Override
	public void removeGameItemObserver(GameItemObserver observer) {
		observers.remove(observer);
		
	}
	
	@Override
	public void notifyGameItemObservers() {
		for (GameItemObserver observer : observers) {
			observer.gameItemUpdate();
		}
	}
	
	@Override
	public abstract String toString();

}
