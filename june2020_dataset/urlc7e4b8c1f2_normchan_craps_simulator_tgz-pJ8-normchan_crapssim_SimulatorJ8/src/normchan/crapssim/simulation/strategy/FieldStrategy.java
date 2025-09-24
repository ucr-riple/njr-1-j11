package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Field;

public class FieldStrategy extends PlayerStrategy {

	public FieldStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
//		System.out.println("Making new $10 field bet.");
		layout.addBet(new Field(layout, player, 10));
	}

}
