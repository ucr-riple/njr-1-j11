package normchan.crapssim.simulation.strategy;

import java.util.Observable;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Lay;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.SevenOutEvent;

public class ProgressiveHedgeStrategy10 extends ProgressiveRollStrategy10 {
	private Lay hedgeBet = null;
	private boolean hedgeComplete = false;
	private boolean retractHedge = false;
	
	public ProgressiveHedgeStrategy10(GameManager manager) {
		super(manager);
	}

	@Override
	protected void beforeRoll() {
		super.beforeRoll();
		if (!hedgeComplete && hedgeBet == null && layout.isNumberEstablished()) {
			this.hedgeBet = new Lay(layout, player, 40, layout.getNumber() == 4 ? 10 : 4);
			addBet(this.hedgeBet);
		} else if (retractHedge && hedgeBet != null) {
			hedgeBet.retractBet();
			this.hedgeBet = null;
			this.retractHedge = false;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SevenOutEvent) {
			this.hedgeBet = null;
			this.hedgeComplete = false;
		} else if (arg instanceof BetEvent) {
			BetEvent.EventType eventType = ((BetEvent)arg).getType();

			if (o == hedgeBet && (eventType == BetEvent.EventType.WIN || eventType == BetEvent.EventType.LOSS)) {
				this.hedgeBet = null;
				this.hedgeComplete = true;
			} else if (!hedgeComplete && hedgeBet != null && eventType == BetEvent.EventType.WIN) {
				this.retractHedge = true;
				this.hedgeComplete = true;
			}
		}
		super.update(o, arg);
	}

	@Override
	protected int getCurrentWinLoss() {
		return super.getCurrentWinLoss() + (hedgeBet != null ? hedgeBet.getMainBet() : 0);
	}

}
