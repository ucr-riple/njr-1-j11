package normchan.crapssim.simulation.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import normchan.crapssim.engine.Dice;
import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.AnyCraps;
import normchan.crapssim.engine.bets.AnySeven;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.PassLine;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.RollCompleteEvent;
import normchan.crapssim.engine.event.SessionEvent;
import normchan.crapssim.simulation.tracker.ResultTracker;

public class ProgressiveComeOutHedgeStrategy10 extends ProgressiveRollStrategy10 {
	private final GameManager gameManager;
	private boolean hedgeNeeded = false;
	private boolean control = false;
	private List<Come> comeBets = null;
	private ResultTracker tracker = null;
	
	public ProgressiveComeOutHedgeStrategy10(GameManager manager) {
		super(manager);
		this.gameManager = manager;
		player.addObserver(this);
	}

	public ProgressiveComeOutHedgeStrategy10(GameManager manager, boolean control) {
		super(manager);
		this.gameManager = manager;
		this.control = control;
		player.addObserver(this);
	}

	@Override
	protected void beforeRoll() {
		super.beforeRoll();
		if (hedgeNeeded && !control) {
			addBet(new AnySeven(layout, player, (int)(0.5*unitSize*comeBets.size())));
			addBet(new AnyCraps(layout, player, comeBets.size()));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof BetEvent) {
			BetEvent.EventType eventType = ((BetEvent)arg).getType();
			if (eventType == BetEvent.EventType.POINT_MADE) {
				// Come out roll after making point
				List<Come> comeBets = getComeBets();
				if (comeBets.size() > 1) {
					this.hedgeNeeded = true;
					this.comeBets = comeBets;
					if (layout.getDice().isTrickDice())
						layout.getDice().toggleTrickDice();
				}
			} else if (hedgeNeeded && o instanceof PassLine && eventType == BetEvent.EventType.NUMBER_ESTABLISHED) {
				this.hedgeNeeded = false;
				this.comeBets = null;
				if (!layout.getDice().isTrickDice())
					layout.getDice().toggleTrickDice();
			}
		} else if (arg instanceof RollCompleteEvent) {
			if (tracker == null && hedgeNeeded) {
				// Start tracking results *after* original passline bet is paid off
				tracker = new ResultTracker(gameManager);
			} else if (tracker != null && !hedgeNeeded) {
				// Finish tracking after the last bets are paid on the point-establishing roll
				tracker.cleanup();
				tracker = null;
			}
		} else if (hedgeNeeded && o instanceof Dice) {
			if (layout.getDice().getTotal() == 7) {
				// Rolling seven wipes out all come bets, so hedge is no longer needed
				this.hedgeNeeded = false;
				this.comeBets = null;
			}
		} else if ((hedgeNeeded || tracker != null) && arg instanceof SessionEvent) {
			// Clean up at the end of the session
			if (((SessionEvent)arg).getType() == SessionEvent.EventType.END) {
				hedgeNeeded = false;
				if (tracker != null) {
					tracker.cleanup();
					tracker = null;
				}
			}
		}
		super.update(o, arg);
	}

	private List<Come> getComeBets() {
		List<Come> comeBets = new ArrayList<Come>();
		for (Bet bet : layout.getBets()) {
			if (bet instanceof Come)
				comeBets.add((Come)bet);
		}
		return comeBets;
	}
}
