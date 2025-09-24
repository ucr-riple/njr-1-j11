package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.exception.InvalidBetException;

public class Lay extends FixedNumberBet {

	public Lay(Layout layout, Player player, int bet, int number) {
		super(layout, player, bet, number);
		if (number < 4 || number > 10 || number == 7)
			throw new InvalidBetException("Invalid number for lay bet");
		checkBetLimits();
	}

	@Override
	public boolean diceRolled() {
		if (layout.getDice().getTotal() == number) {
			betLost();
			return true;
		} else if (layout.getDice().getTotal() == 7) {
			betWon();
			return true;
		}
		return false;
	}

	@Override
	protected void betWon() {
		betWon(mainBet + getWinAmount() - calculateVig(getWinAmount()));
	}

	private int getWinAmount() {
		if (number == 4 || number == 10) {
			return mainBet * 1 / 2;
		} else if (number == 5 || number == 9) {
			return mainBet * 2 / 3;
		} else {
			return mainBet * 5 / 6;
		}
		
	}

}
