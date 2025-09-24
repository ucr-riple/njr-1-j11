package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.Field;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;

public class MaxStrategy2 extends PlayerStrategy {

	public MaxStrategy2(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			layout.addBet(new PassLine(layout, player, 10));
		} else {
			layout.addBet(new Come(layout, player, 10, true));

			handleBuyBet(4, 10);
			handlePlaceBet(5, 10);
			handlePlaceBet(6, 12);
			handlePlaceBet(8, 12);
			handlePlaceBet(9, 10);
			handleBuyBet(10, 10);
		}
		
		for (Bet b : layout.getBets()) {
			if (b instanceof PassOrCome) {
				PassOrCome bet = (PassOrCome)b;
				if (bet.isNumberEstablished())
					bet.updateOddsBet(bet.getMaxOddsBet());
			}
		}

		layout.addBet(new Field(layout, player, 10));
	}

}
