package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.PassOrCome;

public class ProgressiveRollStrategy5 extends ProgressiveRollStrategy {
	public ProgressiveRollStrategy5(GameManager manager) {
		super(manager, 5);
	}

	@Override
	protected void beforeRoll() {
//		System.out.println("Start balance: "+startBalance+" current balance: "+player.getBalance());
		if (layout.isNumberEstablished() && shouldBetCome(player.getBalance() - startBalance)) {
			addBet(new Come(layout, player, unitSize));
		}
	}
	
	@Override
	protected void handleNumberEstablished(PassOrCome bet) {
		if (lastBets.get(bet.getNumber()) == null) {
			lastBets.put(bet.getNumber(), STARTING_ODDS * bet.getMainBet());
		} else {
			int newBet = lastBets.get(bet.getNumber()) + bet.getMainBet();
			if (newBet <= bet.getMaxOddsBet())
				lastBets.put(bet.getNumber(), newBet);
		}
		bet.updateOddsBet(lastBets.get(bet.getNumber()));
	}
}
