package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.exception.InvalidBetException;
import normchan.crapssim.engine.util.BetNormalizer;

public abstract class PassOrCome extends Bet implements NumberBet {
	protected int oddsBet = 0;
	protected int number = 0;
	
	public PassOrCome(Layout layout, Player player, int bet) {
		super(layout, player, bet);
		checkBetLimits();
	}

	public boolean diceRolled() {
		if (number != 0) {
			int roll = layout.getDice().getTotal();
			if (roll == 7) {
				betLost();
				return true;
			} else if (roll == number) {
				betWon();
				return true;
			}
		} else {
			switch (layout.getDice().getTotal()) {
			case 2:
			case 3:
			case 12:
				betLost();
				return true;
				
			case 7:
			case 11:
				betWon();
				return true;
				
			default:
				this.number = layout.getDice().getTotal();
				notifyObservers(new BetEvent(BetEvent.EventType.NUMBER_ESTABLISHED, "Number established for "+this));
			}
		}
		
		return false;
	}
	
	protected void betWon() {
		int amount = mainBet*2 + oddsBet;
		if (oddsBet > 0) {
			if (number == 4 || number == 10) {
//				notifyObservers(new GameEvent("Odds winnings: "+oddsBet * 2));
				amount += oddsBet * 2;
			} else if (number == 5 || number == 9) {
//				notifyObservers(new GameEvent("Odds winnings: "+((float)oddsBet * 1.5)));
				amount += ((float)oddsBet * 1.5);
			} else {
//				notifyObservers(new GameEvent("Odds winnings: "+((float)oddsBet * 1.2)));
				amount += ((float)oddsBet * 1.2);
			}
		}
		
		betWon(amount);
	}
	
	public void retractBet() {
		if (layout.isNumberEstablished())
			throw new InvalidBetException("Can't retract "+getClass().getSimpleName()+" bet after number is established.");
		
		super.retractBet();
	}
	
	public boolean isNumberEstablished() {
		return number != 0;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getMaxOddsBet() {
		if (number == 4 || number == 10)
			return 3 * mainBet;
		else if (number == 5 || number == 9)
			return 4 * mainBet;
		else if (number == 6 || number == 8)
			return 5 * mainBet;
		else
			return 0;
	}

	public int getOddsBet() {
		return oddsBet;
	}
	
	@Override
	public int getTotalAmount() {
		return mainBet + oddsBet;
	}

	public void updateOddsBet(int oddsBet) {
		oddsBet = BetNormalizer.normalizeOddsBet(number, oddsBet);
		if (number != 0 && this.oddsBet != oddsBet) {
			notifyObservers(new BetEvent(BetEvent.EventType.UPDATE, "Updating "+this+" to odds of $"+oddsBet));
			if (oddsBet > getMaxOddsBet())
				throw new InvalidBetException("Exceeded maximum odds for "+getClass().getSimpleName()+" bet.");
			
			player.deduct(oddsBet - this.oddsBet);
			this.oddsBet = oddsBet;
		}
	}
	
	@Override
	public void updateBet(int amount) {
		if (layout.isNumberEstablished())
			throw new InvalidBetException("Can't update "+getClass().getSimpleName()+" bet with established number.");
		super.updateBet(amount);
	}

	public String toString() {
		return getClass().getSimpleName()+" bet "+(number == 0 ? "" : "on "+number+" ")+"worth $"+mainBet+(oddsBet > 0 ? " with $"+oddsBet+" odds" : "");
	}
}
