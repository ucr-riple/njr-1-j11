package normchan.crapssim.engine.event;

import normchan.crapssim.engine.bets.Bet;

public class NewBetEvent extends GameEvent {
	private final Bet bet;

	public NewBetEvent(Bet bet, String message) {
		super(message);
		this.bet = bet;
	}

	public Bet getBet() {
		return bet;
	}

}
