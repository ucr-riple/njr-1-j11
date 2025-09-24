package normchan.crapssim.engine;

import java.util.Date;
import java.util.Observable;
import java.util.Random;

import normchan.crapssim.engine.event.GameEvent;

public class Dice extends Observable {
	private final Random randomizer;
	protected int die1 = 1;
	protected int die2 = 1;
	
	public Dice() {
		randomizer = new Random(new Date().getTime());
	}
	
	public void roll() {
		shakeDice();
		announceRoll();
	}
	
	protected void shakeDice() {
		die1 = randomizer.nextInt(6) + 1;
		die2 = randomizer.nextInt(6) + 1;
	}
	
	protected void announceRoll() {
		setChanged();
		notifyObservers(new GameEvent("The roll is "+getTotal()+(isHardWay() ? " (the hard way)" : "")));
	}
	
	public int getTotal() {
		return die1 + die2;
	}
	
	public int getDie1() {
		return die1;
	}
	
	public int getDie2() {
		return die2;
	}
	
	public boolean isHardWay() {
		if (getTotal() == 2 || getTotal() == 12)
			return false;
		return (die1 == die2);
	}
	
	public boolean toggleTrickDice() {
		return false;
	}
	
	public boolean isTrickDice() {
		return false;
	}
}
