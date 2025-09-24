package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;

public class HardWaysStrategy extends PlayerStrategy {

	public HardWaysStrategy(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
	}

	@Override
	public void bet() {
		handleHardWayBet(6, 5);
		handleHardWayBet(8, 5);
		handleHardWayBet(4, 5);
		handleHardWayBet(10, 5);
	}

}
