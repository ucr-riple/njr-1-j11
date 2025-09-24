package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Dont;
import normchan.crapssim.engine.bets.DontCome;
import normchan.crapssim.engine.bets.DontPass;

public class DontStrategy extends PlayerStrategy {

	public DontStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			layout.addBet(new DontPass(layout, player, 5));
		} else {
			layout.addBet(new DontCome(layout, player, 5));

			for (Bet b : layout.getBets()) {
				if (b instanceof Dont) {
					Dont bet = (Dont)b;
					if (bet.isNumberEstablished()) {
						bet.updateOddsBet(bet.getMaxOddsBet());
					}
				}
			}
		}
	}

}
