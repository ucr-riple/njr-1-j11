package normchan.crapssim.simulation.strategy;

import java.util.Random;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Buy;

public class BuyStrategy extends PlayerStrategy {
	private static final Random engine = new Random();

	public BuyStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		if (layout.getBuyOn(4) == null) {
			int bet = (engine.nextInt(8) + 1) * 5;
			layout.addBet(new Buy(layout, player, bet, 4));
		} 
		if (layout.getBuyOn(10) == null) {
			int bet = (engine.nextInt(8) + 1) * 5;
			layout.addBet(new Buy(layout, player, bet, 10));
		}
	}
}
