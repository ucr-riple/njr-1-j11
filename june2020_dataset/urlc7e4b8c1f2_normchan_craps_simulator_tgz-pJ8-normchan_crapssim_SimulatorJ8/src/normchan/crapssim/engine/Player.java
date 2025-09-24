package normchan.crapssim.engine;

import java.util.Observable;

import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.event.SessionEvent;
import normchan.crapssim.engine.exception.BankruptException;
import normchan.crapssim.simulation.strategy.PlayerStrategy;

public class Player extends Observable {
	private int balance;
	private PlayerStrategy strategy;

	public Player(int balance) {
		super();
		this.balance = balance;
	}
	
	public boolean isBroke() {
		return balance <= 0;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void addFunds(int deposit) {
		balance += deposit;
	}
	
	public void makeBet() {
		strategy.bet();
	}
	
	public void deduct(int amount) {
		if (balance - amount < 0)
			throw new BankruptException("Player has insufficient funds.");
		balance -= amount;
		notifyObservers(new GameEvent("Player balance is $"+balance));
	}
	
	public void payOff(int amount) {
		balance += amount;
		notifyObservers(new GameEvent("Player balance is $"+balance));
	}

	public void sendStatusUpdate() {
		if (isBroke()) {
			notifyObservers(new GameEvent("Player is broke!"));
		} else {
			notifyObservers(new GameEvent("Player balance is $"+balance));
		}
	}
	
	public void sessionStart() {
		notifyObservers(new SessionEvent(SessionEvent.EventType.BEGIN, "New session starting..."));
	}
	
	public void sessionComplete() {
		notifyObservers(new SessionEvent(SessionEvent.EventType.END, "Session complete."));

		sendStatusUpdate();
	}
	
	private void notifyObservers(GameEvent event) {
		setChanged();
		super.notifyObservers(event);
	}
	
	public PlayerStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(PlayerStrategy strategy) {
		this.strategy = strategy;
	}
}
