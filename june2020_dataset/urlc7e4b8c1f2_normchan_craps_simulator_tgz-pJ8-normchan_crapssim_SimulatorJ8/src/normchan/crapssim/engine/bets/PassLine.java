package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;

public class PassLine extends PassOrCome{
	public PassLine(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	@Override
	protected void betWon(int amount) {
		if (layout.isNumberEstablished())
			notifyObservers(new BetEvent(BetEvent.EventType.POINT_MADE, "Point of "+getNumber()+" has been rolled."));
		super.betWon(amount);
	}
}
