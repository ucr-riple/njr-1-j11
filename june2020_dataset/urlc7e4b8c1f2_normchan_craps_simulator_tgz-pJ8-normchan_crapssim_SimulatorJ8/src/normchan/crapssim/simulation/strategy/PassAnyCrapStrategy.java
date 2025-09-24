package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.AnyCraps;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.PassLine;

public class PassAnyCrapStrategy extends PlayerStrategy {

	public PassAnyCrapStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			layout.addBet(new PassLine(layout, player, 10));
			layout.addBet(new AnyCraps(layout, player, 2));
		} else {
			for (Bet b : layout.getBets()) {
				if (b instanceof PassLine) {
					PassLine bet = (PassLine)b;
					int odds = bet.getMaxOddsBet();
					bet.updateOddsBet(odds);
				}
			}
		}
	}

}
