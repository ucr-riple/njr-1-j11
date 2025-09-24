package normchan.crapssim.simulation.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.SevenOutEvent;

public abstract class ProgressiveRollStrategy extends PlayerStrategy {
	protected static final int STARTING_ODDS = 2;
	protected static final int COME_BET_FOLD = -3;
	protected final int unitSize;
	protected int startBalance = 0;
	protected Map<Integer, Integer> lastBets = new HashMap<Integer, Integer>();

	public ProgressiveRollStrategy(GameManager manager, int unitSize) {
		super(manager.getPlayer(), manager.getLayout());
		this.unitSize = unitSize;
		layout.addObserver(this);
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			addBet(new PassLine(layout, player, unitSize));
		} else {
			handlePlaceBet(6, unitSize);
			handlePlaceBet(8, unitSize);
		}
		beforeRoll();
	}
	
	protected void addBet(Bet bet) {
		layout.addBet(bet);
		bet.addObserver(this);
	}
	
	protected int getCurrentWinLoss() {
		return player.getBalance() - startBalance;
	}
	
	protected boolean shouldBetCome(int winLoss) {
		return winLoss > COME_BET_FOLD*unitSize;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SevenOutEvent) {
//			System.out.println("Sevened out, clearing all bet history.");
			lastBets.clear();
			startBalance = 0;
		} else if (o instanceof PassOrCome) {
			if (((BetEvent)arg).getType() == BetEvent.EventType.NUMBER_ESTABLISHED) {
				if (startBalance == 0) startBalance = player.getBalance() + layout.getPassLine().getMainBet(); 
				handleNumberEstablished((PassOrCome)o);
			}
		}
		super.update(o, arg);
	}
	
	protected void handleNumberEstablished(PassOrCome bet) {
	}

	protected abstract void beforeRoll();
}
