package normchan.crapssim.simulation.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.bets.Bet;
import normchan.crapssim.engine.bets.Come;
import normchan.crapssim.engine.bets.Dont;
import normchan.crapssim.engine.bets.DontPass;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.event.SevenOutEvent;

public class OptimalStrategy2 extends PlayerStrategy {

	private static final int STARTING_ODDS = 1;
	private Map<Integer, Integer> lastBets = new HashMap<Integer, Integer>();

	public OptimalStrategy2(GameManager manager) {
		super(manager.getPlayer(), manager.getLayout());
		layout.addObserver(this);
	}

	@Override
	public void bet() {
		if (!layout.isNumberEstablished()) {
			addBet(new DontPass(layout, player, 5));
		} else {
			addBet(new Come(layout, player, 5));
		}
	}
	
	private void addBet(Bet bet) {
		layout.addBet(bet);
		bet.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SevenOutEvent) {
//			System.out.println("Sevened out, clearing all bet history.");
			lastBets.clear();
		} else if (o instanceof PassOrCome) {
			if (((BetEvent)arg).getType() == BetEvent.EventType.NUMBER_ESTABLISHED) {
				PassOrCome bet = (PassOrCome)o;
				if (lastBets.get(bet.getNumber()) == null) {
					lastBets.put(bet.getNumber(), STARTING_ODDS * bet.getMainBet());
				} else {
					int newBet = lastBets.get(bet.getNumber()) + bet.getMainBet();
					if (newBet <= bet.getMaxOddsBet())
						lastBets.put(bet.getNumber(), newBet);
				}
				bet.updateOddsBet(lastBets.get(bet.getNumber()));
			}
//			System.out.println("\nOptimalStrategy2 observes bet event for "+o+" of type "+((BetEvent)arg).getType()+".\n");
		} else if (o instanceof Dont) {
			if (((BetEvent)arg).getType() == BetEvent.EventType.NUMBER_ESTABLISHED) {
				Dont bet = (Dont)o;
				if (lastBets.get(bet.getNumber()) == null) {
					lastBets.put(bet.getNumber(), bet.getOddsBetMultiple(STARTING_ODDS));
				} else {
					int newBet = lastBets.get(bet.getNumber()) + bet.getMainBet();
					if (newBet <= bet.getMaxOddsBet())
						lastBets.put(bet.getNumber(), newBet);
				}
				bet.updateOddsBet(lastBets.get(bet.getNumber()));
			}
//			System.out.println("\nOptimalStrategy2 observes bet event for "+o+" of type "+((BetEvent)arg).getType()+".\n");
		}
	}

}
