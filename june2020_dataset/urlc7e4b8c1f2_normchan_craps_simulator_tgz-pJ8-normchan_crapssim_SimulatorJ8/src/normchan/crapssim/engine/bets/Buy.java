package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.exception.InvalidBetException;

public class Buy extends FixedNumberBet {

	public Buy(Layout layout, Player player, int bet, int number) {
		super(layout, player, bet, number);
		if (number < 4 || number > 10 || number == 7)
			throw new InvalidBetException("Invalid number for buy bet");
		checkBetLimits();
	}
	
	@Override
	public boolean diceRolled() {
		if (layout.isNumberEstablished()) {
			if (layout.getDice().getTotal() == number) {
				betWon();
				return true;
			} else if (layout.getDice().getTotal() == 7) {
				betLost();
				return true;
			}
		}
		return false;
	}

	@Override
	protected void betWon() {
		int amount = mainBet - calculateVig(mainBet);
		if (number == 4 || number == 10) {
			amount += mainBet * 2 / 1;
		} else if (number == 5 || number == 9) {
			amount += mainBet * 3 / 2;
		} else {
			amount += mainBet * 6 / 5;
		}
		
		betWon(amount);
	}
}
