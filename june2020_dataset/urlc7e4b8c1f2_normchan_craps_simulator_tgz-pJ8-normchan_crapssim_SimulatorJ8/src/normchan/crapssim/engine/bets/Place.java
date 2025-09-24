package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.exception.InvalidBetException;

public class Place extends FixedNumberBet {
	public Place(Layout layout, Player player, int bet, int number) {
		super(layout, player, bet, number);
		if (number < 4 || number > 10 || number == 7)
			throw new InvalidBetException("Invalid number for place bet");
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
		int amount = mainBet;
		if (number == 4 || number == 10) {
			amount += mainBet * 9 / 5;
		} else if (number == 5 || number == 9) {
			amount += mainBet * 7 / 5;
		} else {
			amount += mainBet * 7 / 6;
		}
		
		betWon(amount);
	}
}
