package normchan.crapssim.simulation.strategy;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.PassOrCome;

public class ProgressiveRollStrategy10 extends ProgressiveRollStrategy {
	private static final int INCREASE_ODDS_FOLD = 10;
	private static final int MAX_ODDS_FOLD = 20;

	public ProgressiveRollStrategy10(GameManager manager) {
		super(manager, 10);
	}

	@Override
	protected void beforeRoll() {
		for (Bet bet : layout.getBets()) {
			if (bet instanceof PassOrCome) {
				PassOrCome poc = (PassOrCome)bet;
				if (poc.isNumberEstablished()) {
					if (getCurrentWinLoss() < INCREASE_ODDS_FOLD * unitSize) {
						poc.updateOddsBet(startingOdds(poc.getNumber()));
					} else if (getCurrentWinLoss() < MAX_ODDS_FOLD * unitSize) {
						poc.updateOddsBet(startingOdds(poc.getNumber()) + unitSize);
					} else {
						poc.updateOddsBet(poc.getMaxOddsBet());
					}
				}
			}
		}

//		System.out.println("Start balance: "+startBalance+" current balance: "+player.getBalance());
		if (layout.isNumberEstablished() && shouldBetCome(getCurrentWinLoss())) {
			addBet(new Come(layout, player, unitSize, true));
		}
	}
	
	private int startingOdds(int number) {
		if (number == 6 || number == 8) return (int)(2.5*unitSize);
		else return 2*unitSize; 
	}
}
