package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;

public class DontCome extends Dont {
	protected boolean oddsWorkingOnComeOut = false;

	public DontCome(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	public DontCome(Layout layout, Player player, int bet,
			boolean oddsWorkingOnComeOut) {
		super(layout, player, bet);
		this.oddsWorkingOnComeOut = oddsWorkingOnComeOut;
	}

	@Override
	protected void betWon() {
		if (!layout.isNumberEstablished() && oddsBet > 0) // Odds are off by default
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
}
