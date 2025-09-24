package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;

public class ComePassStrategy extends PlayerStrategy {
	private final static int MAX_ODDS = -1;
	private final static int ODDS_FOLD_COUNT = MAX_ODDS;

	public ComePassStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			layout.addBet(new PassLine(layout, player, 5));
		} else {
			layout.addBet(new Come(layout, player, 5));

			for (Bet b : layout.getBets()) {
				if (b instanceof PassOrCome) {
					PassOrCome bet = (PassOrCome)b;
					if (bet.isNumberEstablished()) {
						int odds = bet.getMaxOddsBet();
						if (ODDS_FOLD_COUNT != MAX_ODDS)
							odds = bet.getMainBet() * ODDS_FOLD_COUNT;
						bet.updateOddsBet(odds);
					}
				}
			}
		}
	}

}
