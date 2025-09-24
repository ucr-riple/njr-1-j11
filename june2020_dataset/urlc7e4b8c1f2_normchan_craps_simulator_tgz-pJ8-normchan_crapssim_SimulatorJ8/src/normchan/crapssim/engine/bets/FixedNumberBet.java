package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;

public abstract class FixedNumberBet extends Bet implements NumberBet {
	protected final int number;

	public FixedNumberBet(Layout layout, Player player, int bet, int number) {
		super(layout, player, bet);
		this.number = number;
	}

	public abstract boolean diceRolled();

	public int getNumber() {
		return number;
	}
	
	public String toString() {
		return getClass().getSimpleName()+" bet on "+number+" worth $"+mainBet;
	}
}
