package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.event.BetEvent;
import normchan.crapssim.engine.exception.InvalidBetException;

public class Dont extends Bet implements NumberBet {
	protected int oddsBet = 0;
	protected int number = 0;

	public Dont(Layout layout, Player player, int bet) {
		super(layout, player, bet);
		checkBetLimits();
	}

	@Override
	public boolean diceRolled() {
		if (number != 0) {
			int roll = layout.getDice().getTotal();
			if (roll == 7) {
				betWon();
				return true;
			} else if (roll == number) {
				betLost();
				return true;
			}
		} else {
			switch (layout.getDice().getTotal()) {
			case 2:
			case 3:
				betWon();
				return true;

			case 7:
			case 11:
				betLost();
				return true;
				
			case 12:
				betPushed();
				return true;
				
			default:
				this.number = layout.getDice().getTotal();
			}
		}
		
		return false;
	}
	
	protected void betWon() {
		int amount = mainBet*2 + oddsBet;
		if (oddsBet > 0) {
			if (number == 4 || number == 10) {
				amount += oddsBet / 2;
			} else if (number == 5 || number == 9) {
				amount += ((float)oddsBet * 2 / 3);
			} else {
				amount += ((float)oddsBet * 5 / 6);
			}
		}
		
		betWon(amount);
	}
	
	public boolean isNumberEstablished() {
		return number != 0;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getOddsBetMultiple(int foldCount) {
		if (number == 4 || number == 10)
			return foldCount * mainBet * 2;
		else if (number == 5 || number == 9)
			return (int)(foldCount * Math.ceil((double)mainBet / 2) * 3);
		else if (number == 6 || number == 8)
			return (int)(foldCount * Math.ceil((double)mainBet / 5) * 6);
		else
			return 0;
	}
	
	public int getMaxOddsBet() {
		if (number == 4 || number == 10)
			return 6 * mainBet;
		else if (number == 5 || number == 9)
			return 6 * mainBet;
		else if (number == 6 || number == 8)
			return 6 * mainBet;
		else
			return 0;
	}

	public int getOddsBet() {
		return oddsBet;
	}

	public void updateOddsBet(int oddsBet) {
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
