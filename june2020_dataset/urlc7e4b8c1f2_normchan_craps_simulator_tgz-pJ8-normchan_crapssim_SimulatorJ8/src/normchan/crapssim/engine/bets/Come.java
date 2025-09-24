package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;

public class Come extends PassOrCome {
	protected boolean oddsWorkingOnComeOut = false;
	
	public Come(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	public Come(Layout layout, Player player, int bet,
			boolean oddsWorkingOnComeOut) {
		super(layout, player, bet);
		this.oddsWorkingOnComeOut = oddsWorkingOnComeOut;
	}

	@Override
	protected void betWon() {
		if (!oddsWorkingOnComeOut && !layout.isNumberEstablished() && oddsBet > 0) // Odds are off by default
			super.betWon(mainBet*2 + oddsBet);
		else
			super.betWon();
	}

	@Override
	protected void betLost() {
		if (!oddsWorkingOnComeOut && !layout.isNumberEstablished() && oddsBet > 0) {
			// Odds are not working on come out roll
			notifyObservers(new BetEvent(BetEvent.EventType.LOSS, this+" lost.  Odds are off, returning to player", getMainBet()));
			player.payOff(oddsBet);
		} else
			super.betLost();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+" bet "+modifier()+(number == 0 ? "" : "on "+number+" ")+"worth $"+mainBet+(oddsBet > 0 ? " with $"+oddsBet+" odds" : "");
	}

	private String modifier() {
		if (this.oddsWorkingOnComeOut && !layout.isNumberEstablished())
			return "(odds working) ";
		else 
			return "";
	}
}
