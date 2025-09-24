package normchan.crapssim.simulation.strategy;

import java.util.Observable;
import java.util.Observer;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.bets.Buy;
import normchan.crapssim.engine.bets.HardWay;
import normchan.crapssim.engine.bets.Lay;
import normchan.crapssim.engine.bets.PassOrCome;
import normchan.crapssim.engine.bets.Place;
import normchan.crapssim.engine.util.BetNormalizer;

public abstract class PlayerStrategy extends Observable implements Observer {
	protected Player player;
	protected Layout layout;
	
	public PlayerStrategy(Player player, Layout layout) {
		super();
		this.player = player;
		this.layout = layout;
	}
	
	protected void handlePlaceBet(int number, int amount) {
		amount = BetNormalizer.normalizePlaceBet(number, amount);
		PassOrCome poc = layout.getPassOrComeOn(number);
		Place place = layout.getPlaceOn(number);
		
		if (poc != null && place != null) {
			place.retractBet();
		} else if (poc == null) {
			if (place == null) {
//				System.out.println("Making new $"+amount+" place bet on "+number+".");
				layout.addBet(new Place(layout, player, amount, number));
			} else {
				place.updateBet(amount);
			}
		}
	}
	
	protected void handleBuyBet(int number, int amount) {
		amount = BetNormalizer.normalizeBuyBet(number, amount);
		PassOrCome poc = layout.getPassOrComeOn(number);
		Buy buy = layout.getBuyOn(number);
		
		if (poc != null && buy != null) {
			buy.retractBet();
		} else if (poc == null) {
			if (buy == null) {
				layout.addBet(new Buy(layout, player, amount, number));
			} else {
				buy.updateBet(amount);
			}
		}
	}
	
	protected void handleLayBet(int number, int amount) {
		amount = BetNormalizer.normalizeLayBet(number, amount);
		PassOrCome poc = layout.getPassOrComeOn(number);
		Lay lay = layout.getLayOn(number);
		
		if (poc != null && lay != null) {
			lay.retractBet();
		} else if (poc == null) {
			if (lay == null) {
				layout.addBet(new Lay(layout, player, amount, number));
			} else {
				lay.updateBet(amount);
			}
		}
	}
	
	protected void handleHardWayBet(int number, int amount) {
		HardWay existing = layout.getHardWayOn(number);
		if (existing != null) {
			existing.updateBet(amount);
		} else {
			layout.addBet(new HardWay(layout, player, amount, number));
		}
	}
	
	public void update(Observable o, Object arg) {
	}
	
	public abstract void bet();
}
