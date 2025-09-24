package normchan.crapssim.engine.bets;

import java.util.Observable;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.exception.InvalidBetException;

public abstract class Bet extends Observable {
	protected final Layout layout;
	protected final Player player;
	protected int mainBet;
	protected int price;

	public Bet(Layout layout, Player player, int bet) {
		this.layout = layout;
		this.player = player;
		this.mainBet = bet;
		this.price = calculatePrice(mainBet);
		
		player.deduct(price);
	}
	
	protected void checkBetLimits() {
		if (mainBet < GameManager.MIN_BET)
			throw new InvalidBetException(getClass().getSimpleName()+" bet is less than the minimum bet.");
		else if (mainBet > GameManager.MAX_BET)
			throw new InvalidBetException(getClass().getSimpleName()+" bet is more than the maximum bet.");
	}
	
	protected void betWon() {
		betWon(mainBet * 2);
	}
	
	protected void betWon(int amount) {
		notifyObservers(new BetEvent(BetEvent.EventType.WIN, this+" won.  Adding $"+amount+" to player balance.", amount));
		player.payOff(amount);
	}
	
	protected void betLost() {
		notifyObservers(new BetEvent(BetEvent.EventType.LOSS, this+" lost.", getTotalAmount()));
	}
	
	protected void betPushed() {
		notifyObservers(new BetEvent(BetEvent.EventType.PUSH, this+" pushed."));
		player.payOff(mainBet);
	}
	
	public int getMainBet() {
		return mainBet;
	}
	
	public int getTotalAmount() {
		return price;
	}
	
	protected int calculatePrice(int bet) {
		return bet;
	}

	protected int calculateVig(int amount) {
		int commission = amount / 20;
		if (commission == 0 || ((float)amount / 20) - commission > 0.5) 
			commission++;
		return commission;
	}

	public void retractBet() {
		notifyObservers(new BetEvent(BetEvent.EventType.RETRACT, "Retracting "+getClass().getSimpleName()+" bet."));
		layout.removeBet(this);
		player.payOff(price);
	}	
	
	public void updateBet(int amount) {
		if (mainBet != amount) {
			notifyObservers(new BetEvent(BetEvent.EventType.UPDATE, "Updating "+this+" to $"+amount));
			int newPrice = calculatePrice(amount);
			player.deduct(newPrice - amount);
			this.mainBet = amount;
			this.price = newPrice;
		}
	}
	
	protected void notifyObservers(GameEvent event) {
		setChanged();
		super.notifyObservers(event);
	}
	
	public String toString() {
		return getClass().getSimpleName()+" bet worth $"+mainBet;
	}
	
	public abstract boolean diceRolled();
}
